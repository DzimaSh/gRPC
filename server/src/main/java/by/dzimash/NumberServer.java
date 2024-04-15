package by.dzimash;

import by.dzimash.service.NumberService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class NumberServer {

    private final NumberService numberService = new NumberService();

    public static void main(String[] args) {
        try {
            new NumberServer().start();
        } catch (Exception e) {
            log.error("An error occurred while starting the server: ", e);
        }
    }

    public void start() throws IOException, InterruptedException {
        log.info("Starting the server...");
        final Server server = ServerBuilder.forPort(8080)
                .addService(numberService)
                .build();

        server.start();
        log.info("Server started, listening on " + server.getPort());


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Received shutdown request, stopping the server...");
            server.shutdown();
            log.info("Server stopped.");
        }));

        server.awaitTermination();
    }
}
