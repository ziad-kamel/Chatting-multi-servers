package com.distributed.tasks;

public class Server2 {
    public static void main(String[] args) {
        Server server2 = new Server(8082);
        server2.startServer();
    }
}
