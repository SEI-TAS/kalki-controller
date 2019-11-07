package edu.cmu.sei.ttg.kalki.controller;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.logging.Level;

public abstract class TestIOTController {

    private IOTController controller;
    private static boolean hasRun = false;

    @BeforeAll
    public static void createTestDB() {
        if (!hasRun) {
            String rootPassword = "kalkipass";  //based on run script
            String dbHost = "localhost";        //based on run script
            String dbPort = "5432";             //based on run script
            String dbName = "kalkidb_test";
            String dbUser = "kalkiuser_test";
            String dbPass = "kalkipass";

            try {
                // Recreate DB and user.
                Postgres.removeDatabase(rootPassword, dbName);
                Postgres.removeUser(rootPassword, dbUser);
                Postgres.createUserIfNotExists(rootPassword, dbUser, dbPass);
                Postgres.createDBIfNotExists(rootPassword, dbName, dbUser);

                //initialize test DB
                Postgres.initialize(dbHost, dbPort, dbName, dbUser, dbPass);
                Postgres.setLoggingLevel(Level.OFF);
            } catch (Exception e) {
                System.out.println(e);
            }
            hasRun = true;
        }
    }

    @BeforeEach
    public void reset() {
        Postgres.resetDatabase();

    }

    protected int insertData(int deviceType, int state, String alertType) {
        controller = null;

        Device d = new Device("Test Device", "device", Postgres.findDeviceType(deviceType), "ip", 1, 1);
        d.insert();

        if(state > 1) {
            DeviceSecurityState dss = new DeviceSecurityState(d.getId(), state);
            dss.insert();
        }

        AlertType at = new AlertType(alertType, "test alert", "test");
        at.insert();


        controller = new IOTController();
        controller.initListeners(controller);

        wait(1);

        Alert alert = new Alert(d.getId(), at.getName(), at.getId());
        alert.insert();

        wait(1);

        return d.getId();
    }

    protected void wait(int time){
        try {
            TimeUnit.SECONDS.sleep(time);
        }
        catch (InterruptedException e){
            System.out.println("Error in wait");
        }
    }

}
