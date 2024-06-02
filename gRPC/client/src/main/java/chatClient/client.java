package chatClient;

import java.util.Scanner;
import java.util.Iterator;

import grpc.stub.ChatGrpc;
import grpc.stub.ChatOuterClass.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class client {
	
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        ChatGrpc.ChatBlockingStub stub = ChatGrpc.newBlockingStub(channel);
        ChatGrpc.ChatStub stubStream = ChatGrpc.newStub(channel);
        Scanner scanner = new Scanner(System.in);
        String username;
        int responseCode;
       
        System.out.print("enter name to proceed: ");
        do {         
            username = scanner.nextLine();
            try{
            registrationM request = registrationM.newBuilder().setUsername(username).build();
            ServerResponse response = stub.registration(request);
            responseCode = response.getCode();}
            catch(Exception e){
                System.out.println("Server is not available");
                System.exit(0);
                return;
            }
        } while (responseCode != 100);
        
        System.out.println("Welcome "+username);
        
        
        Empty request = Empty.newBuilder().build();
        Iterator<connectedUser> users = stub.showUsers(request);
        connectedUser userTEMP;
        System.out.println("list of connected users: ");
        while (users.hasNext()) {
        userTEMP = users.next();
        System.out.println(userTEMP.getUser());
        }
        System.out.println();
        System.out.println("list of  commands:\n");
        System.out.print("To send a message: ");
        System.out.println("[name of receiver]:[message]");
        System.out.print("To disconnect from the chat: ");
        System.out.println("exit");

       

        StreamObserver<content> obs = stubStream.chat(new StreamObserver<content>() {
            @Override
            public void onNext(content value) {
                System.out.printf("[%s to %s ]: %s\n", value.getSource(), value.getDestination(), value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t.getMessage());
                System.out.println("Server error ");
                System.exit(0);
            }

            @Override
            public void onCompleted() {
                System.out.println("Server disconnected");
                channel.shutdown();
                System.exit(0);
                
            }
        });
        System.out.println("");
        content message = content.newBuilder().setMessage(username).build();
        obs.onNext(message);
   

        while (true) {

            String command = scanner.nextLine();
            String[] command_args = command.split(":");
            if (command_args.length != 2 && !command.equals("exit")) {
                System.out.println("Invalid command");
                continue;
            }
            else if (command.equals("exit")) {
                obs.onCompleted();
            } else {
                message = content.newBuilder().setDestination(command_args[0]).setSource(username)
                        .setMessage(command_args[1]).build();
                obs.onNext(message);
            }
        }
    }
    }

