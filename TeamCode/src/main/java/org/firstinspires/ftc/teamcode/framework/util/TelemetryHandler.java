package org.firstinspires.ftc.teamcode.framework.util;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetryHandler { //No reason for a singleton Cian
    private static Telemetry telemetry;
    public static void setTelemetry(Telemetry t) {
        telemetry=t;
    }
    public static Telemetry getTelemetry() {
        return telemetry;
    }
}
