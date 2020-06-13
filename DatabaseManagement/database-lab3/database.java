package com.company;
import org.apache.commons.csv.CSVRecord;

import java.sql.*;

public class database {
    static Connection connect = SQLDatabaseConnection.getMySqlConnection();


    public static void Person(String firstname, String lastname, String prefix, String id) throws SQLException {
        String sql1="INSERT INTO Person(firstname,lastname,prefix,id) VALUES(?,?,?,?)";
        PreparedStatement InsertTable= connect.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);


        //PreparedStatement InsertTable = connect.prepareStatement("INSERT INTO Person(firstname,lastname,prefix,id) VALUES(?,?,?,?);");
        InsertTable.setString(1, firstname);
        InsertTable.setString(2, lastname);
        InsertTable.setString(3, prefix);
        InsertTable.setString(4, id);

        InsertTable.executeUpdate();
    }


    public static void address(String firstname, String lastname, String address) throws SQLException{

        PreparedStatement InsertTable = connect.prepareStatement("INSERT INTO address(firstname, lastname, address)" +
                "VALUES(?,?,?)");

        InsertTable.setString(1, firstname);
        InsertTable.setString(2, lastname);
        InsertTable.setString(3, address);

        InsertTable.executeUpdate();


    }


    public static void home(String address, String phonenumber) throws SQLException{
        PreparedStatement InsertTable = connect.prepareStatement("INSERT INTO home(address, phonenumber) VALUES(?,?)");
        InsertTable.setString(1, address);
        InsertTable.setString(2, phonenumber);

        InsertTable.executeUpdate();
    }


        public static void work(String firstname, String lastname, String job, String company, String workphonenumber) throws SQLException{
            PreparedStatement InsertTable = connect.prepareStatement("INSERT INTO work(firstname,lastname,job,company,workphonenumber)" +
                    "VALUES(?,?,?,?,?)");
            InsertTable.setString(1, firstname);
            InsertTable.setString(2, lastname);
            InsertTable.setString(3, job);
            InsertTable.setString(4, company);
            InsertTable.setString(5, workphonenumber);

            InsertTable.executeUpdate();
    }

    //Credit Card number and company.
    public static void creditInfo(String creditcard, String creditcardprovider) throws SQLException{
        PreparedStatement InsertTable = connect.prepareStatement("INSERT INTO creditInfo(CreditCard, CreditCardProvider)" +
                "VALUES(?,?)");
        InsertTable.setString(1, creditcard);
        InsertTable.setString(2, creditcardprovider);

        InsertTable.executeUpdate();


    }
    public void PrintTables() throws SQLException {

        PreparedStatement testperson = connect.prepareStatement("SELECT * FROM Person");
        ResultSet resultsPerson = testperson.executeQuery();
        SQLDatabaseConnection.print(resultsPerson);

        testperson.close();
    }

}

