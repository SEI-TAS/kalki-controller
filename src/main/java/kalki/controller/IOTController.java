package kalki.controller;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.listeners.InsertHandler;
import edu.cmu.sei.ttg.kalki.listeners.InsertListener;
import edu.cmu.sei.ttg.kalki.models.*;

import javax.management.timer.Timer;
import javax.net.ssl.HandshakeCompletedEvent;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class IOTController implements InsertHandler {

    DeviceManager deviceManager = new DeviceManager();

    public static void main(String[] args) {
        IOTController main = new IOTController();
        initializeDatabase();
        main.initListeners(main);
        Postgres.insertGroup(new Group(24, "tester"));
        try {
            DeviceType phle = Postgres.findDeviceType(4).thenApplyAsync(
                    deviceType->{return deviceType;}).toCompletableFuture().get();
            Device test_device = new Device("phle00",
                    "light", 4, 24, "0.0.0.0", 5, 5);
            Device test_device0 = new Device("wemo00",
                    "light", 3, 24, "0.0.0.0", 5, 5);
            test_device.insert();
            TimeUnit.SECONDS.sleep(3);
            test_device0.insert();
            TimeUnit.SECONDS.sleep(3);
            DeviceStatus status = new DeviceStatus(test_device.getId());
            DeviceStatus status0 = new DeviceStatus(test_device0.getId());
            status.insert();
            TimeUnit.SECONDS.sleep(3);
            status0.insert();
            TimeUnit.SECONDS.sleep(2);
            Postgres.insertAlert(new Alert("alert", status.getId(), 5));
            TimeUnit.SECONDS.sleep(4);
            Postgres.insertAlert(new Alert("alert1", status.getId(), 6));
            TimeUnit.SECONDS.sleep(4);
            Postgres.insertAlert(new Alert("alert2", status.getId(), 4));
            TimeUnit.SECONDS.sleep(4);
            Postgres.insertAlert(new Alert("alert3", status0.getId(), 6));
            TimeUnit.SECONDS.sleep(4);
            System.exit(0);
        }
        catch (ExecutionException | InterruptedException e){
            System.out.println("We messed up in main");
        }
    }

    @Override
    public void handleNewInsertion(int id) {
        System.out.println("Here in handler");
        try {
            Alert receivedAlert = Postgres.findAlert(id).thenApplyAsync(alert->{return alert;}).toCompletableFuture().get();
            if(receivedAlert == null){
                System.out.println("alert not found");
            }
            else {
                System.out.println("alert found");
                Device foundDevice = Postgres.findDeviceByAlert(receivedAlert);
                String deviceName = foundDevice.getName();
                int deviceID = foundDevice.getType().getId();
                Thread opThread;
                switch(deviceID){
                    case 1:
                        System.out.println("pushing new state to DLC: " + deviceName);
                        deviceManager.pushNewDLC(deviceName);
                        DLC dlc = deviceManager.queryForDLC(deviceName);
                        dlc.setEvent(1);
                        opThread = new Thread(dlc);
                        break;
                    case 2:
                        System.out.println("pushing new state to UNTS: " + deviceName);
                        deviceManager.pushNewUNTS(deviceName);
                        UNTS unts = deviceManager.queryForUNTS(deviceName);
                        unts.setEvent(1);
                        opThread = new Thread(unts);
                        break;
                    case 3:
                        System.out.println("pushing new state to WEMO: " + deviceName);
                        deviceManager.pushNewWEMO(deviceName);
                        WEMO wemo = deviceManager.queryForWEMO(deviceName);
                        wemo.setEvent(1);
                        opThread = new Thread(wemo);
                        break;
                    case 4:
                        System.out.println("pushing new state to PHLE: " + deviceName);
                        deviceManager.pushNewPHLE(deviceName);
                        PHLE phle = deviceManager.queryForPHLE(deviceName);
                        phle.setEvent(1);
                        opThread = new Thread(phle);
                        break;
                     default:
                         opThread = new Thread(new StateMachine("empty"));
                         break;
                    }
                    opThread.start();
            }
        }
        catch (InterruptedException | ExecutionException e){
            System.out.println("Caught exception Interrupted or Execution");
        }
    }

    private static void initializeDatabase(){
        Postgres.initialize("0.0.0.0", "5432", "kalkidb" , "kalkiuser", "kalkipass");
        Postgres.resetDatabase();
    }

    public IOTController(){

    }

    private void initListeners(InsertHandler handler){
        InsertListener.startUpListener("alerthistoryinsert", handler);
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            System.out.println("caught exception in init listeners");
        }
    }

}
