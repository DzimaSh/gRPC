package by.dzimash.service;

import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberService extends NumberServiceGrpc.NumberServiceImplBase implements BindableService {
    @Override
    public void getNumbers(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
        int firstValue = request.getFirstValue();
        int lastValue = request.getLastValue();

        log.info("Received request for numbers from " + firstValue + " to " + lastValue);

        for (int i = firstValue; i <= lastValue; i++) {
            NumberResponse response = NumberResponse.newBuilder()
                    .setNumber(i)
                    .build();

            responseObserver.onNext(response);
            log.info("Sent number: " + i);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("An error occurred: ", e);
            }
        }

        responseObserver.onCompleted();
        log.info("Completed sending numbers");
    }
}
