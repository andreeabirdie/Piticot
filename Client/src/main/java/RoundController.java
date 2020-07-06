import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.mapping.Collection;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RoundController extends UnicastRemoteObject implements IObserver, Serializable {
    private IServices server;
    private String username;
    private Stage stage;
    private Integer gameID;
    @FXML
    Text player1;
    @FXML
    Text player2;
    @FXML
    Text player3;
    @FXML
    Text configTxt;
    @FXML
    Text dice;
    @FXML Text currentPlayer;
    @FXML Text winnerTxt;
    @FXML
    Button diceBtn;


    public RoundController() throws RemoteException {
    }


    public void setService(IServices server) {
        this.server = server;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void enableStart() throws RemoteException {
    }

    @Override
    public void disableStart() throws RemoteException {

    }

    @Override
    public void move(Integer id, Integer n, String currPlayer, String currentConfiguration, List<String> players) throws RemoteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentPlayer.setText("current player: " + currPlayer);
                configTxt.setText("config: " + currentConfiguration);
                player1.setText("player1: A. " + players.get(0));
                player2.setText("player2: B. " + players.get(1));
                player3.setText("player3: C. " + players.get(2));

                if (currPlayer.equals(username)) {
                    diceBtn.setDisable(false);
                } else diceBtn.setDisable(true);
                dice.setText("dice: " + n);
            }
        });
        this.gameID = id;
    }

    @Override
    public void stop(Integer id, Integer n, String currentConfiguration, List<String> usernames) throws RemoteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                configTxt.setText("config: " + currentConfiguration);
                dice.setText("dice: " + n);
                diceBtn.setDisable(true);
                String winner = "Castigator: ";
                for (int i = currentConfiguration.length() - 1; i >= 0; i--) {
                    if(currentConfiguration.charAt(i) == 'A') {
                        winner += usernames.get(0);
                        break;
                    }
                    if(currentConfiguration.charAt(i) == 'B') {
                        winner += usernames.get(1);
                        break;
                    }
                    if(currentConfiguration.charAt(i) == 'C') {
                        winner += usernames.get(2);
                        break;
                    }
                }
                winnerTxt.setText(winner);
            }
        });
    }

    @FXML
    public void rollDice() {
        Random rand = new Random();
        List<Integer> diceT = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        Collections.shuffle(diceT);
        Integer n = diceT.get(0);


        server.rollDice(gameID, n);

    }


}