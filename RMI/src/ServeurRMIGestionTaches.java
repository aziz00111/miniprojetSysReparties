import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServeurRMIGestionTaches {
    public static void main(String[] args) {
        try {
            // Création du registre RMI sur le port 1099
            LocateRegistry.createRegistry(1099);

            // Création de l'instance du service
            GestionTaches gestionTaches = new GestionTachesImpl();

            // Liaison de l'instance avec le nom dans le registre RMI
            Naming.rebind("//localhost/GestionTaches", gestionTaches);

            System.out.println("Serveur RMI prêt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
