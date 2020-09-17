package com.yt.bluej.models;

import javax.bluetooth.RemoteDevice;
import java.io.IOException;

public class DeviceModel {
    private String name;
    private boolean trustedDevice;
    private boolean authenticated;
    private boolean encrypted;
    private String bluetoothAddress;

    public DeviceModel(RemoteDevice device) {
        this.trustedDevice = device.isTrustedDevice();
        this.authenticated = device.isAuthenticated();
        this.encrypted = device.isEncrypted();
        this.bluetoothAddress = device.getBluetoothAddress();
        try {
            this.name = device.getFriendlyName(false);
        } catch (IOException ignored) {
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTrustedDevice() {
        return trustedDevice;
    }

    public void setTrustedDevice(boolean trustedDevice) {
        this.trustedDevice = trustedDevice;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    @Override
    public String toString() {
        return "DeviceModel{" +
                "name='" + name + '\'' +
                ", trustedDevice=" + trustedDevice +
                ", authenticated=" + authenticated +
                ", encrypted=" + encrypted +
                ", bluetoothAddress='" + bluetoothAddress + '\'' +
                '}';
    }
}
