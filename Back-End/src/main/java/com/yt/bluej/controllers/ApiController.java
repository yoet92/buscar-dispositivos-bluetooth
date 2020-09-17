package com.yt.bluej.controllers;


import com.yt.bluej.models.DeviceModel;
import com.yt.bluej.models.ResponseModel;
import com.yt.bluej.services.BluetoothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Vector;

@RestController
public class ApiController {

    @Autowired
    BluetoothService bluetoothService;

    @CrossOrigin
    @GetMapping(value = "/search")
    public ResponseEntity<ResponseModel<Vector<DeviceModel>>> getAllDevices() {
        ResponseModel<Vector<DeviceModel>> response = new ResponseModel<>();
        try {
            response.setResponse(this.bluetoothService.discover());
        } catch (Exception e) {
            response.putError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
