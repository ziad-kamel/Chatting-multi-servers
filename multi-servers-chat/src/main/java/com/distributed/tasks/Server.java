package com.distributed.tasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private int PORT = 8080;
    private ArrayList<Socket> clientSockets;
    private ServerSocket serverSocket;
    private Socket incomingClient;
    private PrintWriter outputWriter;
    private ArrayList<String> data;

    public Server(int port) {
        this.PORT = port;
        this.clientSockets = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    // define starter method to start the server and wait for clients to join
    public void startServer() {
        try {
            // open serverSocket on specific port
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server listen on port: " + PORT);

            // wait for client(s) to join the server
            while (true) {
                // once the server accept client => add it to client list the server is serving
                incomingClient = serverSocket.accept();
                clientSockets.add(incomingClient);

                // open a communication thread between the incoming thread and server
                ClientServerThread clientServerThread = new ClientServerThread(this, incomingClient);
                clientServerThread.start();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                serverSocket.close();
                outputWriter.close();
                incomingClient.close();
                clientSockets.iterator().next().close();
            } catch (Exception e) {
                System.out.println("cant relese");
            }
        }
    }

    // send any incoming message from any of the connected client to all clients
    public void broadcast(String message) {

        // splite the message to know the sender name, get the actual message that hold
        // the operation need to preform
        String name = message.split(" ")[0];
        message = message.substring(name.length() + 1, message.length());
        CRUD(message, name);

        // loop on each client in the list,
        // write for each of them the new message that the server has received
        for (Socket clientSocket : clientSockets) {
            try {
                if (message.startsWith("add") || message.startsWith("edit") || message.startsWith("del")) {
                    outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                    outputWriter.println(data.toString());
                } else {
                    outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                    outputWriter.println(name + " " + message);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // define a sync method to preform the CRUD operations => [simulation for real
    // database operations for the message in the system]
    private synchronized ArrayList<String> CRUD(String message, String name) {
        // define a method variable
        // index => will hold the index of the entry need to make a CRUD operation on it
        // ,newEntry => will hold the user's value from the message
        int index;
        String newEntry;

        switch (message.split(" ")[0]) {
            case "edit":
                // we need to update a data from the data Array we defined before [this array
                // hold all the entries like a small database for the app]

                // the user have to specify the index of the entriy needed to update
                index = Integer.parseInt(message.split(" ")[1]);
                // split the message and get the user's new entry
                newEntry = message.split(" ")[2];
                // remove the old entry from the data list [e.g. our database] to empty place
                // for the new entry to replace it
                data.remove(index);
                // insert the new entry.
                data.add(index, newEntry);
                System.out.println(name + " has edited " + data.get(index) + " to " + newEntry);
                break;

            case "add":
                // we need to add new entry for the data list

                // split the message to get the user's data to be added
                newEntry = message.split(" ")[1];
                // add the new entry to the data list
                data.add(newEntry);
                System.out.println(name + " has added " + newEntry);
                break;

            case "del":
                // we need to remove an entry from data list

                // get the index of the entry in tha data list [user specify the index of it]
                index = Integer.parseInt(message.split(" ")[1]);
                // remove the entry from the list
                data.remove(index);
                System.out.println(name + " has deleted " + data.get(index));
                break;

            default:
                break;
        }

        return this.data;
    }
}
