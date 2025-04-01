package com.example.demo.hospital.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Hospital {
    private String name;
    private String address;
    private String state;
    private String latitude;
    private String longitude;

    public Hospital(String name, String address, String state, String latitude, String longitude) {
        this.name = name;
        this.address = address;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static void init() throws IOException, SQLException, ClassNotFoundException {
        String filePath = "02_03_01_P_hospitals.xlsx";
        try (Connection conn = db_connection("root", "1234");
             FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {

                    // DB 저장
                    String sql = "INSERT INTO hospital_info (name, address, state, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, row.getCell(0).getStringCellValue());
                        ps.setString(2, row.getCell(1).getStringCellValue());
                        ps.setString(3, row.getCell(2).getStringCellValue());
                        ps.setString(4, row.getCell(3).getStringCellValue());
                        ps.setString(5, row.getCell(4).getStringCellValue());
                        ps.executeUpdate();
                    }
                }
            }
        }
    }

    public static Connection db_connection(String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String DBURL = "jdbc:mysql://localhost:3306/hospitals";
        return DriverManager.getConnection(DBURL, user, password);
    }
}
