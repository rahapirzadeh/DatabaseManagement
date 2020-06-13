package com.company;
import java.sql.*;

public class SQLDatabaseConnection {
    private Connection mySqlConnection = null;

    public static void print(ResultSet results) throws SQLException {
        ResultSetMetaData data = results.getMetaData();
        int numberOfColumns = data.getColumnCount();

        for (int i = 1; i <= numberOfColumns; ++i) {
            if (i > 1)
                System.out.print(" ");
            String columnName = data.getColumnName(i);
            System.out.print(columnName);
        }
        System.out.println();

        while (results.next()) {
            for (int i = 1; i <= numberOfColumns; i++) {
                if (i > 1)
                    System.out.print(" ");
                String columnValue = results.getString(i);
                System.out.print(columnValue);
            }
            System.out.println();
        }
    }

    public static Connection getMySqlConnection(){
        Connection mysqlConnection = null;
        try{
            String driverLoc = "com.mysql.jdbc.Driver";
            String connectionUrl = "jdbc:mysql://35.203.153.75:3306/lab3";
            String uName = "raha";
            String pass = "yes2017ok";


            Class.forName(driverLoc);

            mysqlConnection = DriverManager.getConnection(connectionUrl, uName, pass);
            System.out.println("Success!");

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return mysqlConnection;
    }

}
