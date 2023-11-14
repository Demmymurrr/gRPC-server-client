package ru.demmy.grpc.server.service;

import io.grpc.stub.StreamObserver;
import ru.demmy.grpc.MessageServiceGrpc;
import ru.demmy.grpc.MessageServiceOuterClass;

import java.time.LocalDateTime;

public class MessageServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {


    @Override
    public void sendMessage(MessageServiceOuterClass.MessageRequest request,
                            StreamObserver<MessageServiceOuterClass.MessageResponse> responseObserver) {

        System.out.println(request);

        MessageServiceOuterClass.MessageResponse response = MessageServiceOuterClass.MessageResponse
                .newBuilder()
                .setStatus(true)
                .setMessage("Greate message! " + request.getPayload())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void sendMessageAndGetStreamResponse(MessageServiceOuterClass.MessageRequest request, StreamObserver<MessageServiceOuterClass.MessageResponse> responseObserver) {

        System.out.println(request);

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignore) {}

            MessageServiceOuterClass.MessageResponse response = MessageServiceOuterClass
                    .MessageResponse.newBuilder()
                    .setStatus(true)
                    .setMessage("Time: " + LocalDateTime.now().toString())
                    .build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }
}
