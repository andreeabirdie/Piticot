import java.io.IOException;

public interface IServices {
    boolean login(String username, String password, IObserver client) throws Exception;
    void logout(String username, IObserver client) throws Exception;
    void startGame() throws IOException;
    void changeClient(String username, IObserver client);
    void rollDice(Integer gameID, Integer n);
}
