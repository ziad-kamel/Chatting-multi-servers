package com.distributed.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

  private Socket socket;
  private BufferedReader recieve;
  private PrintWriter send;
  private String username;

  public Client(String host, int port, String username) throws IOException {
    socket = new Socket(host, port);
    recieve = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    send = new PrintWriter(socket.getOutputStream(), true);
    this.username = username;
  }

  public void sendMessage(String message) {
    send.println(username + ": " + message);
  }

  public void receiveMessages() {
    new Thread() {
      public void run() {
        while (true) {
          try {
            String message = recieve.readLine();
            if (!message.startsWith(username))
              System.out.println(message);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      };
    }.start();
  }
}
