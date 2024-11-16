package manager.security.sensors;

import java.util.UUID;

public class TemperatureSensor extends Sensor {
    private double currentTemperature;
    private double maxTemperature = 35.0; // TODO: remove initialization after completing constructor
    private double minTemperature = 10.0; // TODO: remove initialization after completing constructor

    public void changeTemperature(double temperature) {
        if(currentTemperature > maxTemperature && temperature < maxTemperature) {
            reset();
            // TODO: Log normalization
        }else if(currentTemperature < minTemperature && temperature > minTemperature) {
            reset();
            // TODO: Log normalization
        }
        currentTemperature = temperature;
        if(currentTemperature > maxTemperature) {
            trigger();
            // TODO: Decrease temperature and log
        }else if(currentTemperature < minTemperature) {
            trigger();
            // TODO: Increase temperature and log
        }
    }
    public TemperatureSensor(UUID floorId, UUID roomId) {
        super(floorId, roomId);

        // TODO: Set min and max temperature. Get values from file that contains standards
    }
}
