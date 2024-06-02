import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GestionTaches extends Remote {
    void ajouterTache(String tache) throws RemoteException;
    void supprimerTache(int index) throws RemoteException;
    List<String> getListeTaches() throws RemoteException;
}
