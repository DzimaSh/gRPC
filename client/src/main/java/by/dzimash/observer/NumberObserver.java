package by.dzimash.observer;

import by.dzimash.service.NumberResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
public class NumberObserver implements StreamObserver<NumberResponse> {

    private final AtomicInteger currentValue;

    @Override
    public void onNext(NumberResponse response) {
        int lastNumberFromServer = response.getNumber();
        log.info("number from server: {}", lastNumberFromServer);

        log.info("currentValue: {}", currentValue.addAndGet(lastNumberFromServer));
    }

    @Override
    public void onError(Throwable t) {
        log.error(t.getMessage());
    }

    @Override
    public void onCompleted() {
        log.info("Stream completed");
    }
}
