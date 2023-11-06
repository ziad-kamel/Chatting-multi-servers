package com.distributed.tasks;

import java.io.IOException;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Client Name: ");
        String name = scanner.nextLine();

        Client client2 = new Client("localhost", 8080, name);

        client2.receiveMessages();

        System.out.println("Start your conversation");
        System.out.println("========================v");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                scanner.close();
                break;
            }
            client2.sendMessage(input);
        }
    }
}
