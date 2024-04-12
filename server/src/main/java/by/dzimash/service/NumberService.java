package by.dzimash.service;

import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;

public class NumberService extends NumberServiceGrpc.NumberServiceImplBase implements BindableService {
    @Override
    public void getNumbers(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
        int firstValue = request.getFirstValue();
        int lastValue = request.getLastValue();

        for (int i = firstValue; i <= lastValue; i++) {
            NumberResponse response = NumberResponse.newBuilder()
                    .setNumber(i)
                    .build();

            responseObserver.onNext(response);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();
    }
}
