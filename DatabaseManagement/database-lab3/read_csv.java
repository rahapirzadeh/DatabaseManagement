package com.company;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.sql.Connection;
import java.sql.SQLException;

public class read_csv {

    Connection connect = SQLDatabaseConnection.getMySqlConnection();
    //private static final String csvFilePath = "/Users/Rah/IdeaProjects/lab3/src/main/java/com/company/Patient.csv";
   private final String csvFilePath = "/Users/Rah/PycharmProjects/lab3/foobar.csv";
    database database = new database();


    public String firstname;
    public String lastname;
    public String prefix;
    public String id;
    public String address;
    public String phoneNumber;
    public CSVRecord job;
    public String company;
    public String workphonenumber;
    public String creditCard;
    public String creditProvider;



    public void FileReader() throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim())
        ) {

            for (CSVRecord csvRecord:csvParser.getRecords()) {

                String firstname = csvRecord.get(0);
                String lastname = csvRecord.get(1);
                String prefix = csvRecord.get(2);
                String id = csvRecord.get(3);
                String address = csvRecord.get(4);
                String phoneNumber = csvRecord.get(5);
                String job = csvRecord.get(6);
                String company = csvRecord.get(7);
                String workphonenumber = csvRecord.get(8);
                String creditCard = csvRecord.get(9);
                String creditProvider = csvRecord.get(10);


                com.company.database.Person(firstname, lastname, prefix, id);

                com.company.database.address(firstname, lastname,address);

                com.company.database.home(address, phoneNumber);

                com.company.database.work(firstname, lastname, job, company, workphonenumber);

                com.company.database.creditInfo(creditCard, creditProvider);
                /*


                //Prints out records that are being inputed into a table.
                System.out.println("---------------");
                System.out.println("Name : " + prefix + firstname + lastname);
                System.out.println("ID : " + id);
                System.out.println("Address : " + address);
                System.out.println("Phone Number : " + phoneNumber);
                System.out.println("Job : " + job);
                System.out.println("Company : " + company);
                System.out.println("Work Phone Number : " + workphonenumber);
                System.out.println("Credit Card : " + creditCard);
                System.out.println("Credit Card Provider : " + creditProvider);
                System.out.println("---------------\n\n");*/
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}


