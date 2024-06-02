import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class ClientRMIGestionTaches {
    public static void main(String[] args) {
        try {
            // Récupération de la référence distante du service depuis le registre RMI
            GestionTaches gestionTaches = (GestionTaches) Naming.lookup("//localhost/GestionTaches");

            // Interface utilisateur simple en ligne de commande
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1. Ajouter une tâche");
                System.out.println("2. Supprimer une tâche");
                System.out.println("3. Afficher la liste des tâches");
                System.out.println("4. Quitter");
                System.out.print("Choix : ");
                int choix = scanner.nextInt();
                System.out.println();
                scanner.nextLine(); // pour consommer la nouvelle ligne

                switch (choix) {
                    case 1:
                        System.out.print("Entrez la nouvelle tâche : ");
                        String nouvelleTache = scanner.nextLine();
                        gestionTaches.ajouterTache(nouvelleTache);
                        break;
                    case 2:
                        System.out.print("Entrez l'index de la tâche à supprimer : ");
                        int index = scanner.nextInt();
                        gestionTaches.supprimerTache(index);
                        break;
                    case 3:
                        List<String> taches = gestionTaches.getListeTaches();
                        System.out.println("Liste des tâches : ");
                        for (int i = 0; i < taches.size(); i++) {
                            System.out.println((i) + ". " + taches.get(i));
                        }
                        System.out.println();
                        break;
                    case 4:
                        System.out.println("Au revoir !");
                        System.exit(0);
                    default:
                        System.out.println("Choix invalide !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
