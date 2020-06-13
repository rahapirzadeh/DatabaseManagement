package com.company;

import java.sql.*;
import java.util.Scanner;

public class database{
    public Connection conn = null;
    private String driverLocation = "com.mysql.jdbc.Driver";
    private String conn_url = "jdbc:mysql://35.203.153.75:3306/lab3";
    private String conn_user = "raha";
    private String conn_pass = "yes2017ok";

    public database() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName(driverLocation);
            conn = DriverManager.getConnection(conn_url, conn_user, conn_pass);
            System.out.println("Successfully Connected to the Database");
        }
        catch (Exception e) {
            System.out.println("Error Connecting to Database");
            e.printStackTrace();
        }
    }

    public void promptSelect() {
        boolean loop = true;
        menu m = new menu();
        database patientdb = new database();

        while (loop) {
            m.displayMenu();
            int x = getInt("Please Enter a Menu Option: ");

            switch (x) {
                case (0): //export to csv file
                    patientdb.exportCSV();
                    break;

                case (1): //Print all patients
                    patientdb.displayPatients();
                    break;

                case (2): //Add a patient
                    patientdb.addpatient();
                    break;

                case (3): //Update patient disease
                    patientdb.updatedisease();
                    break;

                case (4): //Update patient doctor
                    patientdb.updatepatientdoc();
                    break;

                case (5): //Update patient disease and doctor
                    patientdb.updatepatient();
                    break;

                case (6): //Delete patient
                    patientdb.deletepatient();
                    break;

                case (7): //Search patient by disease
                    patientdb.searchdisease();
                    break;

                case (8): //Search patient by doctor
                    patientdb.searchdoctor();
                    break;

                case (9): //Quit
                    System.out.println(" ---- Exiting the Program ----");
                    loop = false;
                    break;

                default:
                    System.out.println("Invalid Input");
            }
        }
    }

    private static int getInt(String input) {
        while (true) {
            String temp = getString(input);
            try {
                return Integer.parseInt(temp);
            }
            catch(Exception e) {
                System.out.println("Please enter a Menu Option ");
            }
        }
    }

    private static String getString(String input) {
        System.out.print(input);
        Scanner temp = new Scanner(System.in);
        return temp.nextLine();
    }

    private static void displayResults(ResultSet rs) {
        try {
            while(rs.next()) {
                int ssn = rs.getInt("ssn");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String disease = rs.getString("disease");
                String drlastname = rs.getString("drlastname");

                System.out.print("SSN: " + ssn + "\t\t\t\t" +
                        " First Name: " + firstname +  "\t\t\t\t" +
                        " Last Name: " + lastname +  "\t\t\t\t" +
                        " Disease: " + disease +  "\t\t\t" +
                        " Dr.Name: " + drlastname + "\n");
            }
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void exportCSV() {
        try{
            System.out.println(" ---- Export to CSV file ---- ");

            PreparedStatement p = conn.prepareStatement("SELECT * FROM patient " +
                "into OUTFILE 'final_patient.csv'" +
                "FIELDS TERMINATED BY ','");

            System.out.println(" ----done ---- ");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void displayPatients() {
        try {
            System.out.println(" ---- Displaying All Patients ---- ");
            PreparedStatement p = conn.prepareStatement("SELECT * FROM patient");
            ResultSet rs = p.executeQuery();
            displayResults(rs);
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void addpatient(){
        try {
            System.out.println(" ---- Adding New Patient ---- ");

            int ssn = getInt("Enter Patient SSN: ");
            String firstname = getString("Enter First Name: ");
            String lastname = getString("Enter Last Name: ");
            String disease = getString("Enter Disease: ");
            String drlastname = getString("Enter Doctor Name: ");

            PreparedStatement p = conn.prepareStatement("INSERT INTO patient(firstname, lastname, disease,ssn, drlastname) VALUES (?,?,?,?,?);");
            p.setString(1, firstname);
            p.setString(2, lastname);
            p.setString(3, disease);
            p.setInt(4, ssn);
            p.setString(5, drlastname);
            p.executeUpdate();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO pdisease(disease,ssn, drlastname) VALUES (?,?,?);");
            ps.setString(1, disease);
            ps.setInt(2, ssn);
            ps.setString(3, drlastname);
            ps.executeUpdate();
            System.out.println(" ---- Patient Added ---- ");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void updatedisease(){
        try {
            System.out.println(" ---- Updating Patient Disease ---- ");

            int ssn = getInt("Enter Patient SSN to Update Info: ");
            String newdisease = getString("Enter Updated Patient Disease: ");

            PreparedStatement p = conn.prepareStatement("UPDATE patient SET disease = ? WHERE ssn = ?;");
            p.setString(1, newdisease);
            p.setInt(2, ssn);
            p.executeUpdate();
            PreparedStatement ps = conn.prepareStatement("UPDATE pdisease SET disease = ? WHERE ssn = ?;");
            ps.setString(1, newdisease);
            ps.setInt(2, ssn);
            ps.executeUpdate();
            System.out.println(" ---- Patient Updated ---- ");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void updatepatientdoc(){
        try {
            System.out.println(" ---- Updating Patient Doctor ---- ");

            int ssn = getInt("Enter Patient SSN to Update Info: ");
            String newdr = getString("Enter Updated Patient Doctor: ");

            PreparedStatement p = conn.prepareStatement("UPDATE patient SET drlastname = ? WHERE ssn = ?;");
            p.setString(1, newdr);
            p.setInt(2, ssn);
            p.executeUpdate();

            PreparedStatement ps = conn.prepareStatement("UPDATE pdisease SET drlastname = ? WHERE ssn = ?;");
            ps.setString(1, newdr);
            ps.setInt(2, ssn);
            ps.executeUpdate();
            System.out.println(" ---- Patient Doctor Updated ---- ");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void updatepatient() {
        try {
            System.out.println(" ---- Updating Patient Disease and Doctor ---- ");

            int ssn = getInt("Enter Patient SSN to Update Info: ");
            String newdisease = getString("Enter Updated Patient Disease: ");
            String newdr = getString("Enter Updated Patient Doctor: ");

            PreparedStatement p = conn.prepareStatement("UPDATE patient SET disease = ?, drlastname = ? WHERE ssn = ?;");
            p.setString(1, newdisease);
            p.setString(2, newdr);
            p.setInt(3, ssn);
            p.executeUpdate();

            PreparedStatement ps = conn.prepareStatement("UPDATE pdisease SET disease = ?, drlastname = ? WHERE ssn = ?;");
            ps.setString(1, newdisease);
            ps.setString(2, newdr);
            ps.setInt(3, ssn);
            ps.executeUpdate();
            System.out.println(" ---- Patient Doctor and Disease Updated ---- ");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void deletepatient() {
        try {
            System.out.println(" ---- Deleting Patient ---- ");

            int ssn = getInt("Enter Patient SSN to Delete: ");
            PreparedStatement p = conn.prepareStatement("DELETE FROM patient WHERE ssn = ?");
            p.setInt(1, ssn);
            p.executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM pdisease WHERE ssn = ?");
            ps.setInt(1, ssn);
            ps.executeUpdate();
            System.out.println(" ---- Patient Deleted ---- ");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void searchdisease(){
        try {
            System.out.println(" ---- Searching for Patients by Disease ---- ");

            String diseaseSearch = getString("Enter Disease to Search: ");
            PreparedStatement p = conn.prepareStatement("SELECT * FROM patient WHERE disease = ? ");
            p.setString(1, diseaseSearch);
            ResultSet rs = p.executeQuery();
            displayResults(rs);
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void searchdoctor(){
        try {
            System.out.println(" ---- Searching for Patient by Doctor ---- ");

            String drsearch = getString("Enter Doctor Name to Search: ");
            PreparedStatement p = conn.prepareStatement("SELECT * FROM patient WHERE drlastname = ? ");
            p.setString(1, drsearch);
            ResultSet rs = p.executeQuery();
            displayResults(rs);
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
