package com.miku.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BaseDao {
    private  static final String DRIVER = "com.mysql.jdbc.Driver";
    private  static final String URL = "jdbc:mysql://localhost:3306/mystock_x";
    private  static final String USER = "root";
    private  static final String PWD = "123456";

    public Connection openCon() throws Exception{
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL,USER,PWD);
    }

    //增删改共用方法
    protected int alterTable(String sql) {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement(sql);
        ) {
            i = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
