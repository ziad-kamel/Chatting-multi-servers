package com.distributed.tasks;

import java.io.IOException;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Client Name: ");
        String name = scanner.nextLine();

        Client client1 = new Client("localhost", 8081, name);

        client1.receiveMessages();

        System.out.println("Start your conversation");
        System.out.println("========================v");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                scanner.close();
                break;
            }
            client1.sendMessage(input);
        }
    }
}
