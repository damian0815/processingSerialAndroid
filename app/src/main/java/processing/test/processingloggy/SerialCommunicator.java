package processing.test.processingloggy;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.util.List;

/**
 * Created by damian on 08/08/15.
 */
public class SerialCommunicator {

    public SerialCommunicator(Log log) {
        this.log = log;
    }

    public void OpenConnection(Activity activity) {
        // Find all available drivers from attached devices.
        UsbManager manager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            log.append("no drivers\n");
            return;
        }

        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);

        log.append("opening connectiong to " + driver.toString() + "\n");

        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            log.append("cannot open connection\n");
            return;
        }


        // Read some data! Most have just one port (port 0).
        port = driver.getPorts().get(0);
        try {
            port.open(connection);
            port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
        } catch (IOException e) {
            // Deal with error.
            log.append("error opening port: " + e.toString());
        }

        log.append("opened");
    }

    public int WriteBytes(byte[] buffer) {

        if (port == null ) {
            return 0;
        }

        try {
            log.append("about to write");
            int written = port.write(buffer, 500);
            log.append("wrote " + written + " bytes");
            return written;
        } catch (IOException e) {
            log.append("error writing: " + e.toString());
            return 0;
        }

    }

    public int ReadBytes(byte[] inBuffer) {

        if (port == null) {
            return 0;
        }

        try {
            return port.read(inBuffer, 5000);
        } catch (IOException e) {
            log.append("error reading: " + e.toString());
            return 0;
        }
    }

    public void WriteByte(byte byteToWrite) {
        try {

            byte[] buffer = { byteToWrite };
            port.write(buffer, 5000);

        } catch (IOException e) {
            log.append("error writing: " + e.toString());

        }
    }

    // returns -1 if read fails
    public int ReadByte() {

        if (port == null) {
            return -1;
        }

        try {
            int totalBytesRead = 0;
            byte buffer[] = new byte[2];
            int numBytesRead = port.read(buffer, 5000);
            //log.append(new String(buffer));

            if (numBytesRead == 0) {
                return -1;
            }

            return buffer[0];
        } catch (IOException e) {
            log.append("error reading: " + e.toString());
            return -1;
        }

    }

    public void CloseConnection() {
        try {
            port.close();
        } catch (IOException e) {
            log.append("error while closing port: " + e.toString());
        }
    }


    private Log log;

    UsbSerialPort port;

}
