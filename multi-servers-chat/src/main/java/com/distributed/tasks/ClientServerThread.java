package com.distributed.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientServerThread extends Thread {
    private Server server;
    private Socket client;
    private BufferedReader inputReader;

    public ClientServerThread(Server server, Socket client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            // instantiate the inputReader to hold the client message send to server
            inputReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // wait and listen for client message
            while (true) {
                // once the client send a new message => save it and broadcast it to all
                // connected client
                String clientMessage = inputReader.readLine();
                server.broadcast(clientMessage);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                inputReader.close();
                client.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
