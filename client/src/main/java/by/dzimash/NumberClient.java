package by.dzimash;

import by.dzimash.observer.NumberObserver;
import by.dzimash.service.NumberRequest;
import by.dzimash.service.NumberServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberClient {

    private final NumberObserver numberObserver = new NumberObserver();

    public static void main(String[] args) {
        try {
            new NumberClient().start();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    private void start() throws InterruptedException {
        log.info("Starting the client...");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        NumberServiceGrpc.NumberServiceStub stub = NumberServiceGrpc.newStub(channel);

        NumberRequest request = NumberRequest.newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build();

        log.info("Sending request to server...");
        stub.getNumbers(request, numberObserver);

        int currentValue;
        for (int i = 0; i < 50; i++) {
            currentValue = numberObserver.getCurrentValue().incrementAndGet();
            log.info("currentValue: " + currentValue);
            Thread.sleep(1000);
        }

        log.info("Shutting down the client...");
        channel.shutdown();
    }
}
