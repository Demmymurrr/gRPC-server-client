package ru.demmy.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.demmy.grpc.server.service.MessageServiceImpl;

import java.io.IOException;


public class ServerApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(8100)
                .addService(new MessageServiceImpl())
                .build();

        server.start();
        System.out.println("Server is running!");
        server.awaitTermination();
    }

}
