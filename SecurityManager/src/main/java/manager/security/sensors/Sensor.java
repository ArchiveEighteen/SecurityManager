package manager.security.sensors;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Sensor implements Flow.Publisher<Boolean> {
    protected final int id;
    protected boolean isTriggered;
    protected final AtomicBoolean status = new AtomicBoolean(false);
    protected int floorId;
    protected int roomId;
    protected Flow.Subscriber<? super Boolean> subscriber;

    public Sensor(int id){
        this.id = id;
    }

    public void switchActivation(){}
    public AtomicBoolean getStatus(){
        return status;
    }
    public int getId(){
        return id;
    }

    public void trigger(){
        status.set(true);
    }
    public void reset(){
        status.set(false);
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Boolean> subscriber) {
        this.subscriber = subscriber;
        subscriber.onSubscribe(new Flow.Subscription() {
            @Override
            public void request(long n) {

            }

            @Override
            public void cancel() {

            }
        });
    }


}
