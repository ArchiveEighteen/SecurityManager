package manager.security.sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Sensor implements Flow.Publisher<SensorNotification> {
    protected final UUID id = UUID.randomUUID();
    protected final AtomicBoolean status = new AtomicBoolean(false);
    protected UUID floorId;
    protected UUID roomId;
    protected List<Flow.Subscriber<? super SensorNotification>> subscribers = new ArrayList<>();

    public Sensor(UUID floorId, UUID roomId) {
        this.floorId = floorId;
        this.roomId = roomId;
    }

    public AtomicBoolean getStatus(){
        return status;
    }
    public UUID getId(){
        return id;
    }

    protected void trigger(){
        status.set(true);
        for (Flow.Subscriber<? super SensorNotification> subscriber : subscribers) {
            subscriber.onNext(new SensorNotification(id, status));
        }
    }
    protected void reset(){
        status.set(false);
        for (Flow.Subscriber<? super SensorNotification> subscriber : subscribers) {
            subscriber.onNext(new SensorNotification(id, status));
        }
    }

    @Override
    public void subscribe(Flow.Subscriber<? super SensorNotification> subscriber) {
        subscribers.add(subscriber);
        subscriber.onSubscribe(new Flow.Subscription() {
            @Override
            public void request(long n) {

            }

            @Override
            public void cancel() {
                subscribers.remove(subscriber);
            }
        });
    }
}
