package com.company;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.*;


public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Connection connect = SQLDatabaseConnection.getMySqlConnection();


        read_csv inputTable = new read_csv();
        inputTable.FileReader();
        database dataBase=new database();
        dataBase.PrintTables();

        connect.close();

    }

}
