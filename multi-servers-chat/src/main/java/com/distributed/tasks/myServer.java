package com.distributed.tasks;

public class myServer {
    public static void main(String[] args) {

        Server myServer = new Server(8081);
        myServer.startServer();

        Server server2 = new Server(8082);
        server2.startServer();

        Server server3 = new Server(8083);
        server3.startServer();

    }
}
