package com.yt.bluej.services;

import com.yt.bluej.models.Constants;
import com.yt.bluej.models.DeviceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.bluetooth.*;
import java.util.*;

@Service
public class BluetoothService {
    private final Logger log = LoggerFactory.getLogger(BluetoothService.class);

    public Vector<DeviceModel> discover() throws Exception {

        final Object inquiryCompletedEvent = new Object();
        Vector<DeviceModel> devicesDiscovered = new Vector<>();
        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                DeviceModel device = new DeviceModel(btDevice);
                devicesDiscovered.addElement(device);
                log.info("Device found: {}", device);
            }

            public void inquiryCompleted(int discType) {
                synchronized (inquiryCompletedEvent) {
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized (inquiryCompletedEvent) {
            try {
                boolean isOn = LocalDevice.isPowerOn();
                boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
                if (isOn && started) {
                    log.info(Constants.WAITING_FOR_DEVICE);
                    inquiryCompletedEvent.wait();
                    log.info(devicesDiscovered.size() + " device(s) found");
                } else {
                    throw new Exception(Constants.DEVICE_NOT_STARTED);
                }
            } catch (BluetoothStateException | InterruptedException e) {
                throw new Exception(Constants.DEVICE_NOT_DETECTED);
            }
        }
        return devicesDiscovered;
    }
}
