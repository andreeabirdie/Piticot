import domain.Game;
import domain.Round;
import repository.IGameRepository;
import repository.IRoundRepository;
import repository.IUserRepository;
import repository.RoundRepository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Service implements IServices {
    private IUserRepository userRepository;
    private IGameRepository gameRepository;
    private IRoundRepository roundRepository;
    private Map<String, IObserver> loggedClients;
    private Map<String, String> players = new HashMap<>();
    private final int defaultThreadsNo = 5;
    List<String> usernames = new ArrayList<>();;
    Integer currentRound;


    public Service(IUserRepository userRepository, IGameRepository gameRepository, IRoundRepository roundRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public boolean login(String username, String password, IObserver client) throws Exception {
        boolean valid = userRepository.findOne(username, password);
        if (valid) {
            if (loggedClients.get(username) != null) {
                throw new Exception("User is already Logged in!");
            }
            loggedClients.put(username, client);
            System.out.println("Client " + username + " connected");
            if(loggedClients.size() == 3) notifyStart(username);
        }
        return valid;
    }

    private void notifyStart(String username) {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);

        executor.execute(() -> {
            try {
                System.out.println("notifying start...");
                if (loggedClients.size() == 3)
                    loggedClients.get(username).enableStart();
                else if (loggedClients.size() < 3)
                    loggedClients.get(username).disableStart();
            } catch (Exception e) {
                System.out.println("error notifying player...");
            }
        });

        executor.shutdown();
    }

    @Override
    public void startGame() throws IOException {
        String fileName = "D:\\MPP\\practice\\practic\\Services\\src\\main\\resources\\gameID";
        FileInputStream fis=new FileInputStream(fileName);
        Scanner sc=new Scanner(fis);
        Integer id = Integer.parseInt(sc.nextLine()) +1;
        sc.close();
        System.out.println(id);
        FileOutputStream outputStream = new FileOutputStream(fileName);
        byte[] strToBytes = id.toString().getBytes();
        outputStream.write(strToBytes);
        outputStream.close();

        List<String> config = new ArrayList<>(Arrays.asList("W", "W", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_"));
        Collections.shuffle(config);
        String configuration = "";
        for(String s : config){
            configuration += s;
        }

        currentRound = -1;

        usernames = new ArrayList<>();
        for(String u: loggedClients.keySet()){
            usernames.add(u);
        }
        Collections.shuffle(usernames);
        players.put(usernames.get(0), "A");
        players.put(usernames.get(1), "B");
        players.put(usernames.get(2), "C");

        Game g = new Game(id, configuration, configuration, usernames.get(0), usernames.get(1), usernames.get(2), usernames.get(0));
        gameRepository.add(g);
        startRound(id, 0);
    }

    @Override
    public void changeClient(String username, IObserver client) {
        loggedClients.replace(username, client);
    }

    @Override
    public void rollDice(Integer gameID, Integer n) {
        startRound(gameID, n);
    }

    private void startRound(Integer id, Integer n) {
        Game g = gameRepository.findOne(id);

        List<String> config = Arrays.asList(g.getCurrentConfiguration().split(""));
        Integer position = -1;
        for(int i =0; i< config.size(); i++){
            if(config.get(i).equals(players.get(g.getCurrentPlayer()))) {
                config.set(i, "_");
                position = i;
            }
        }

        position += n;
        System.out.println(position);
        while(position >= 0 && !config.get(position).equals("_")){
            position -= 1;
        }
        if(position != -1)
            config.set(position, players.get(g.getCurrentPlayer()));

        String configuration = "";
        for(String s : config){
            configuration += s;
        }

        currentRound += 1;
        Round r = new Round(id, currentRound, g.getCurrentPlayer(), configuration, n);
        roundRepository.add(r);

        String curr = g.getCurrentPlayer();
        if(curr.equals(usernames.get(2))) {
            curr = usernames.get(0);
        }
        else if(curr.equals(usernames.get(1))){
            curr = usernames.get(2);
        }
            else if(curr.equals(usernames.get(0))) curr = usernames.get(1);
        g.setCurrentPlayer(curr);

        g.setCurrentConfiguration(configuration);
        gameRepository.update(g);

        if(currentRound == 9)
            stopGame(g, n);

        sendMove(g, n);
    }

    private void stopGame(Game g, Integer n) {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        executor.execute(()->{
            try{
                for(String username : loggedClients.keySet()) {
                    loggedClients.get(username).stop(g.getId(), n, g.getCurrentConfiguration(), usernames);
                    System.out.println("sending move to " + username);
                }
            }catch (Exception e){
                System.out.println("error notifying player..." + e.getMessage());
            }
        });

        executor.shutdown();
    }

    private void sendMove(Game g, Integer n) {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        executor.execute(()->{
            try{
                for(String username : loggedClients.keySet()) {
                    loggedClients.get(username).move(g.getId(), n,  g.getCurrentPlayer(), g.getCurrentConfiguration(), usernames);
                    System.out.println("sending move to " + username);
                }
            }catch (Exception e){
                System.out.println("error notifying player..." + e.getMessage());
            }
        });

        executor.shutdown();
    }


    @Override
    public void logout(String username, IObserver client) throws Exception {
        IObserver localClient = loggedClients.remove(username);
        if (localClient == null)
            throw new Exception("User " + username + " is not logged in");
        System.out.println("Client " + username + " disconnected");
        if(loggedClients.size() < 3) notifyStart(username);
    }
}

