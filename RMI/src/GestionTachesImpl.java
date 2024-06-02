import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class GestionTachesImpl extends UnicastRemoteObject implements GestionTaches {
    private List<String> listeTaches;

    public GestionTachesImpl() throws RemoteException {
        super();
        this.listeTaches = new ArrayList<>();
        // Ajouter des tâches initiales
        listeTaches.add("Faire les courses");
        listeTaches.add("Appeler le client");
        listeTaches.add("Répondre aux e-mails");
    }

    @Override
    public void ajouterTache(String tache) throws RemoteException {
        listeTaches.add(tache);
    }

    @Override
    public void supprimerTache(int index) throws RemoteException {
        if (index >= 0 && index < listeTaches.size()) {
            listeTaches.remove(index);
        } else {
            throw new RemoteException("Index de tâche invalide");
        }
    }

    @Override
    public List<String> getListeTaches() throws RemoteException {
        return new ArrayList<>(listeTaches);
    }
}
