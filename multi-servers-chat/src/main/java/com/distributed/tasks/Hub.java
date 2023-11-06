package com.distributed.tasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Hub {
    private static final int PORT = 8080;
    private ArrayList<Integer> serverPorts;
    private ServerSocket hubServerSocket;
    private Socket incomingClient;
    private PrintWriter outputWriter;

    public Hub() {
        this.serverPorts = new ArrayList<>();
    }

    // define starter method to start the server and wait for clients to join
    public void startServer() {
        try {
            // open serverSocket on specific port
            hubServerSocket = new ServerSocket(PORT);
            System.out.println("Server listen on port: " + PORT);

            // wait for client(s) to join the server
            while (true) {
                // once the server accept client => add it to client list the server is serving
                incomingClient = hubServerSocket.accept();
                serverPorts.add(incomingClient.getPort());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                hubServerSocket.close();
                outputWriter.close();
                incomingClient.close();
            } catch (Exception e) {
                System.out.println("cant relese");
            }
        }
    }

    // send any incoming message from any of the connected client to all clients
    public void broadcast(String message) {
        // loop on each client in the list,
        // write for each of them the new message that the server has received

        String name = message.split(" ")[0];
        message = message.substring(name.length() + 1, message.length());

        for (int port : serverPorts) {
            try {
                // connect to the port, and send a message
                Socket socket = new Socket("localhost", port);
                outputWriter = new PrintWriter(socket.getOutputStream(), true);
                outputWriter.println(message);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    outputWriter.close();
                } catch (Exception e) {
                    System.out.println("cant relese");
                }
            }
        }
    }
}
