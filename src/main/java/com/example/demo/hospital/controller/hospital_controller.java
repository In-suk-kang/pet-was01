package com.example.demo.hospital.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.demo.hospital.service.Hospital.init;

@RestController
public class hospital_controller {
    @RequestMapping("/hospitals")
    public String hospitals() throws SQLException, IOException, ClassNotFoundException {
        init();
        return "Saved DB";
    }
}
