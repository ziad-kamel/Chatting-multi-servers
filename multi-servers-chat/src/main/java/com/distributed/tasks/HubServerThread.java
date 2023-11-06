package com.distributed.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HubServerThread extends Thread {
    private Hub hub;
    private Socket server;
    private BufferedReader inputReader;

    public HubServerThread(Hub hub, Socket server) {
        this.hub = hub;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            // instantiate the inputReader to hold the server message send to server
            inputReader = new BufferedReader(new InputStreamReader(server.getInputStream()));

            // wait and listen for server message
            while (true) {
                // once the server send a new message => save it and broadcast it to all
                // connected server
                String serverMessage = inputReader.readLine();
                hub.broadcast("HUB: " + "add hi");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                inputReader.close();
                server.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}