package ru.demmy.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.demmy.grpc.MessageServiceGrpc;
import ru.demmy.grpc.MessageServiceOuterClass;

import java.util.Iterator;

public class ClientApplication {

    public static void main(String[] args) {
        // default message
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8100")
                .usePlaintext().build();

        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc
                .newBlockingStub(channel);

        MessageServiceOuterClass.MessageRequest request = MessageServiceOuterClass
                .MessageRequest.newBuilder()
                .setHeader("Supper message")
                .setPayload("Hello everyone!")
                .build();

        MessageServiceOuterClass.MessageResponse response = stub.sendMessage(request);
        System.out.println(response);

        // message with stream response
        Iterator<MessageServiceOuterClass.MessageResponse> messageResponseIterator = stub.sendMessageAndGetStreamResponse(request);

        while (messageResponseIterator.hasNext()) {
            System.out.println(messageResponseIterator.next());
        }

        channel.shutdownNow();
    }

}
