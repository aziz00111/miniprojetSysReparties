import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        System.out.println("Connecté au serveur de chat...");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        // Thread to read messages from server
        Thread readThread = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readThread.start();

        // Get username from user
        String username = consoleReader.readLine();
        out.println(username);

        // Read input from user and send to server
        String userInput;
        while ((userInput = consoleReader.readLine()) != null) {
            out.println(userInput);
        }

        // Clean up
        readThread.join();
        socket.close();
    }
}
