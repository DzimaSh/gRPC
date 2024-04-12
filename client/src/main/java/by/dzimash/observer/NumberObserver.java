package by.dzimash.observer;

import by.dzimash.service.NumberResponse;
import io.grpc.stub.StreamObserver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Slf4j
public class NumberObserver implements StreamObserver<NumberResponse> {

    private final AtomicInteger currentValue = new AtomicInteger(0);

    @Override
    public void onNext(NumberResponse response) {
        int lastNumberFromServer = response.getNumber();
        int current = currentValue.addAndGet(lastNumberFromServer + 1);

        log.info("currentValue: " + current);
        log.info("number from server: " + lastNumberFromServer);
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
