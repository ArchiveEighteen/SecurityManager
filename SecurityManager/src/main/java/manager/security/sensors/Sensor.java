package manager.security.sensors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Sensor implements Flow.Publisher<SensorNotification> {
    protected UUID id;
    protected AtomicBoolean status;
    protected SensorType type;
    protected UUID floorId;
    protected UUID roomId;
    protected List<Flow.Subscriber<? super SensorNotification>> subscribers = new ArrayList<>();

    public Sensor(SensorType type, UUID floorId, UUID roomId) {
        id = UUID.randomUUID();
        this.type = type;
        this.floorId = floorId;
        this.roomId = roomId;
        status = new AtomicBoolean(false);
    }

    protected Sensor(SensorType type, UUID id, UUID floorId, UUID roomId, boolean status) {
        this.id = id;
        this.type = type;
        this.floorId = floorId;
        this.roomId = roomId;
        this.status = new AtomicBoolean(status);
    }

    public AtomicBoolean getStatus(){
        return status;
    }
    public UUID getId(){
        return id;
    }
    public List<Flow.Subscriber<? super SensorNotification>> getSubscribers() {
        return Collections.unmodifiableList(subscribers);
    }
    public SensorType getType() {
        return type;
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
