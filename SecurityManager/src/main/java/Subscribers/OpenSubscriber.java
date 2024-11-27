package Subscribers;

import Windows.MainWindow;
import manager.security.sensors.SensorNotification;

import java.util.concurrent.Flow;

public class OpenSubscriber implements Flow.Subscriber<SensorNotification>{
    private final String subscriberName;
    private MainWindow mainW;
    public OpenSubscriber(String subscriberName, MainWindow window) {
        this.subscriberName = subscriberName;
        this.mainW = window;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println(subscriberName + " subscribed.");
    }

    @Override
    public void onNext(SensorNotification notification) {
        mainW.setColorForOpen(notification);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println(subscriberName + " encountered an error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println(subscriberName + " updates complete.");
    }
}