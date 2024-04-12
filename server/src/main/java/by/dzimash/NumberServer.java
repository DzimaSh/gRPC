package by.dzimash;

import by.dzimash.service.NumberService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class NumberServer {

    private final NumberService numberService = new NumberService();

    public static void main(String[] args) {
        try {
            new NumberServer().start();
        } catch (IOException | InterruptedException  e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(numberService)
                .build();

        server.start();
        server.awaitTermination();
    }
}
