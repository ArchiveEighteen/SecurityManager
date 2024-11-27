package manager;

import manager.security.sensors.Sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulator {
    private final SecurityManager securityManager;
    private final List<Thread> threads = new ArrayList<>();
    private final int threadsCount;
    private AtomicBoolean running = new AtomicBoolean(false);
    private final long minimumTimeGap = 500;
    private final long maximumTimeGap = 1000;

    // Constructor with SecurityManager reference
    public Simulator(SecurityManager securityManager) {
        if (securityManager == null) {
            throw new IllegalArgumentException("SecurityManager cannot be null");
        }
        this.securityManager = securityManager;

        List<Sensor> sensors = new ArrayList<>();
        for (Floor floor: securityManager.getFloors()) {
            for (Room room: floor.getRooms()) {
                sensors.addAll(room.getSensors());
            }
        }

        Random random = new Random();
        Sensor tmpSensor;

        //Shuffle the sensors, why not?:)
        for(int i = 0, randIndex; i < sensors.size(); i++) {
            randIndex = random.nextInt(sensors.size() - 1);
            tmpSensor = sensors.get(i);
            sensors.set(i, sensors.get(randIndex));
            sensors.set(randIndex, tmpSensor);
        }

        threadsCount = Math.max(1, (int) (Math.log(sensors.size()) / Math.log(4)));
        final int sensorsForThreadCount = (sensors.size()/threadsCount);

        //Threads from i=0 to  i=threadCount-2
        for (int i = 0; i < threadsCount - 1; i++) {
            final int index = i;
            threads.add(new Thread() {
                @Override
                public void run() {
                    System.out.println("Simulation thread #" + (index + 1) + " threads");

                    //Get a part of the list of sensors to be triggered
                    List<Sensor> mySensors = new ArrayList<>();
                    int beginIndex = index * sensorsForThreadCount;
                    int boundIndex = (index + 1) * sensorsForThreadCount;

                    for(int j = 0; j < boundIndex; j++)
                        mySensors.add(sensors.get(beginIndex + j));

                    try {
                        simulateSensors(mySensors);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        //The last thread, i=threadCount-1
        threads.add(new Thread() {
            @Override
            public void run() {
                System.out.println("Simulation thread #" + threadsCount + " threads");

                //Get a part of the list of sensors to be triggered
                List<Sensor> mySensors = new ArrayList<>();
                int beginIndex = (threadsCount - 1) * sensorsForThreadCount;
                int boundIndex = sensors.size();

                for(int j = 0; j < boundIndex; j++)
                    mySensors.add(sensors.get(beginIndex + j));

                try {
                    simulateSensors(mySensors);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void simulateSensors(List<Sensor> mySensors) throws InterruptedException {
        List<Thread> myThreads = new ArrayList<>();
        final HashMap<Sensor, Long> sensorSleepTime = new HashMap<>();

        //Execute a trigger for a sensor and die
        for (Sensor sensor: mySensors) {
            myThreads.add(new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(sensorSleepTime.get(sensor));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    switch(sensor.getType())
                    {
                        case OpenSensor:
                            ((manager.security.sensors.OpenSensor) sensor).detectBreach();
                            break;
                        case MotionSensor:
                            ((manager.security.sensors.MotionSensor) sensor).detectMovement();
                            break;
                        case TemperatureSensor:
                            double max = ((manager.security.sensors.TemperatureSensor) sensor).getMaxTemperature();
                            double min = ((manager.security.sensors.TemperatureSensor) sensor).getMinTemperature();
                            double randTemperature = min - ((max-min)/2.0) + (Math.random() * (2*(max-min)));
                            ((manager.security.sensors.TemperatureSensor) sensor).changeTemperature(randTemperature);
                            break;
                    }
                }
            });
        }

        while(running.get()) {
            //Create a timetable
            for(Sensor sensor: mySensors) {
                long randTime = minimumTimeGap + (long)(Math.random() * (maximumTimeGap-minimumTimeGap));
                sensorSleepTime.put(sensor, randTime);
            }

            //Ensure there's at least two simultanious triggers (sleepTime = 0)
            final List<Boolean> hasSimultanious = new ArrayList<>();
            hasSimultanious.add(false);
            while(!hasSimultanious.get(0)) {
                sensorSleepTime.forEach((sensor, time) -> {
                    if (!hasSimultanious.get(0) && Math.random() >= 0.5){
                        time = (long)0;
                        hasSimultanious.set(0,true);
                    }
                });
            }

            //Execute the triggers
            for(Thread thread: myThreads) {
                  thread.start();
                  thread.join();
            }

        }


    }

    // Start the simulation (start all threads)
    public void startSimulation() {
        if (running.get()) {
            throw new IllegalStateException("The simulation has already been started");
        }
        if (threads.isEmpty()) {
            throw new IllegalStateException("No sensor threads added to the simulation");
        }
        running.set(true);

        // Start all sensor threads
        for (Thread thread : threads)
            thread.start();

    }

    // Stop the simulation (join all threads)
    public void stopSimulation() {
        running.set(false);
    }

    // Getter for SecurityManager (for threads to access it)
    public SecurityManager getSecurityManager() {
        return securityManager;
    }
}
