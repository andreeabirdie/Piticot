import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IObserver extends Remote {
    void enableStart() throws RemoteException;
    void disableStart() throws RemoteException;
    void move(Integer id, Integer n, String currentPlayer, String currentConfiguration, List<String> players) throws RemoteException;
    void stop(Integer id, Integer n, String currentConfiguration, List<String> usernames) throws RemoteException;
}
