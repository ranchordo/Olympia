package org.firstinspires.ftc.teamcode.mechanisms.mechanismhandlers;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MechanismEngine {

    //Singleton instance creation
    private static MechanismEngine engineInstance;
    public static MechanismEngine getInstance() {
        if (engineInstance == null) {
            engineInstance = new MechanismEngine();
        }
        return engineInstance;
    }

    public void refreshInstance() {
        rawMechanismMap.clear();
        localHardwareMap = null;
    }

    //Mechanism table handling
    private Map<Class, Object> rawMechanismMap = new HashMap<Class, Object>();
    public <T extends Mechanism> T getMechanism(Class<T> mechanismKey) {

        try {
            if (!getInstance().rawMechanismMap.containsKey(mechanismKey)) {
                Mechanism obj = mechanismKey.newInstance();
                getInstance().rawMechanismMap.put(mechanismKey, obj);
                obj.init(localHardwareMap);
            }
            return (T) getInstance().rawMechanismMap.get(mechanismKey);
        } catch (Exception e) {
            //Future Telemetry Post for "Cannot Instantiate"
        }

        return null;
    }

    //Mechanism Initialization Handling
    private HardwareMap localHardwareMap;
    public void setHardwareMap(HardwareMap hwmap) {
        this.localHardwareMap = hwmap;
    }
}

