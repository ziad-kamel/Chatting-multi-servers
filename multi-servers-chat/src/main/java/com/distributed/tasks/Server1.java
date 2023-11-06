package com.distributed.tasks;

public class Server1 {
    public static void main(String[] args) {
        Server server1 = new Server(8081);
        server1.startServer();
    }
}
