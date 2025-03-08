package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleDBTests {

    private DBServer server;

    // Create a new server _before_ every @Test
    @BeforeEach
    public void setup() {
        server = new DBServer();
    }

    // Random name generator - useful for testing "bare earth" queries (i.e. where tables don't previously exist)
    private String generateRandomName() {
        String randomName = "";
        for(int i=0; i<10 ;i++) randomName += (char)( 97 + (Math.random() * 25.0));
        return randomName;
    }

    private String sendCommandToServer(String command) {
        // Try to send a command to the server - this call will timeout if it takes too long (in case the server enters an infinite loop)
        return assertTimeoutPreemptively(Duration.ofMillis(1000), () -> { return server.handleCommand(command);},
        "Server took too long to respond (probably stuck in an infinite loop)");
    }

    @Test
    public void testCreateTableWithAttributes() {
        String randomName = generateRandomName();
        DBParser parser = new DBParser();
        try {
            parser.acceptCommand("USE secondtestdbfolder;"); // select a database
        } catch (IOException e) {
            assertEquals("Database file not found", e.getMessage());
            System.out.println("Test 7 passed");
        }
        try {
            parser.acceptCommand("create table newlyCreatedTable row1 row2 row3;");
//            parser.acceptCommand("USE newlyCreatedTestTable;");
        } catch (IOException e) {
//            System.out.println(e.getMessage());
        }
    }

//    @Test
//    public void testCreateDatabaseOrTableInapropriateCommand() {
//        String randomName = generateRandomName();
//        DBParser parser = new DBParser();
//        try {
//            parser.acceptCommand("invalidCommand");
//        } catch (IOException e) {
//            assertEquals("Command has less than 2 tokens", e.getMessage());
//            System.out.println("Test 1 passed");
//        }
//        try {
//            parser.acceptCommand("There is no semi-colon in the end of the command");
//        } catch (IOException e) {
//            assertEquals("The command does not end with ';'", e.getMessage());
//            System.out.println("Test 1.5 passed");
//        }
//        try {
//            parser.acceptCommand("");
//        } catch (IOException e) { // this one doesn't work for some reason, find out later
//            assertEquals("Command has less than 2 tokens", e.getMessage());
//            System.out.println("Test 2 passed");
//        }
//        try {
//            parser.acceptCommand("CREATE DATABASE fIRSTtestdbfolder;");
//        } catch (IOException e) {
//            assertEquals("Database already exists", e.getMessage());
//            System.out.println("Test 3 passed");
//        }
//        try {
//            parser.acceptCommand("CREATE DATABASE newDataBase helloworld;");
//        } catch (IOException e) {
//            assertEquals("Create database command does not end with ';'", e.getMessage());
//            System.out.println("Test 4 passed");
//        }
//        try {
//            parser.acceptCommand("CREATE DATABASE newDataBase; helloworld;");
//        } catch (IOException e) {
//            assertEquals("Create database command does not end with ';'", e.getMessage());
//            System.out.println("Test 5 passed");
//        }
//        try {
//            parser.acceptCommand("CREATE DATABASE " + randomName + ";"); // this one should not produce an exception -> it should create a database
//        } catch (IOException e) {
////            System.out.println(e.getMessage());
//        }
//        try {
//            parser.acceptCommand("create table " + randomName + ";");
//        } catch (IOException e) {
//            assertEquals("Database not selected", e.getMessage());
//            System.out.println("Test 6 passed");
//        }
//        try {
//            parser.acceptCommand("USE " + randomName + ";"); // select a database
//        } catch (IOException e) {
////            System.out.println(e.getMessage());
//        }
//        try {
//            parser.acceptCommand("USE nonExistentDatabase;"); // select a database
//        } catch (IOException e) {
//            assertEquals("Database file not found", e.getMessage());
//            System.out.println("Test 7 passed");
//        }
//        try {
//            parser.acceptCommand("create table newlyCreatedTable;");
//            parser.acceptCommand("USE " + randomName + ";");
//        } catch (IOException e) {
////            System.out.println(e.getMessage());
//        }
//
////        System.out.println("/////////////////////////////////////////////////////");
////
////        try {
////            parser.acceptCommand("USE firsttestDBfolder;");
////
////        } catch (IOException e) {
////
////        }
//    }

/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//    // A basic test that creates a database, creates a table, inserts some test data, then queries it.
//    // It then checks the response to see that a couple of the entries in the table are returned as expected
//    @Test
//    public void testBasicCreateAndQuery() {
//        String randomName = generateRandomName();
//        DBParser parser = new DBParser();
//        try {
//            parser.acceptCommand("invalidCommand");
//        } catch (IOException e) {
//            assertEquals("Command has less than 2 tokens", e.getMessage());
//        }
//        sendCommandToServer("CREATE DATABASE " + randomName + ";");
//        assertThrows(IOException.class, () -> {sendCommandToServer("USE " + randomName + ";");});
//        sendCommandToServer("USE " + randomName + ";");
//        sendCommandToServer("CREATE DATABASE " + randomName + ";");

//        sendCommandToServer("USE " + randomName + ";");

        /// /////////////////////////////////////////
//        sendCommandToServer("USE secondtestdbfolder;");

//        try {
//            DBParser dbParser = new DBParser("command");
//        } catch (IOException e) {
//            assertEquals("Command has less than 2 tokens", e.getMessage());
//        }
//        try {
//            DBParser dbParser = new DBParser("");
//        } catch (IOException e) {
//            assertEquals("Command has less than 2 tokens", e.getMessage());
//        }
//        // initial test script of create database and table functions
//        try {
//            DBParser dbParser = new DBParser("CREATE DATABASE firsttestDBfolder ;");
//        } catch (IOException e) {
//            assertEquals("Database or table already exists", e.getMessage());
//        }
//        try {
//            DBParser dbParser = new DBParser("use firsttestDBfolder ;");
//
//        } catch (IOException e) {
//            assertEquals("Database or table already exists", e.getMessage());
//        }


        //remember to refactor this when done with the exception shit
//        try {
//            server.handleCommand("use firsttestDBfolder ;");
//        } catch (IOException e) {
//            assertEquals("Database or table already exists", e.getMessage());
//        }




//    }
//
//    // A basic test that creates a database, creates a table, inserts some test data, then queries it.
//    // It then checks the response to see that a couple of the entries in the table are returned as expected
//    @Test
//    public void testBasicCreateAndQuery() {
//        String randomName = generateRandomName();
//        sendCommandToServer("CREATE DATABASE " + randomName + ";");
//        sendCommandToServer("USE " + randomName + ";");
//        sendCommandToServer("CREATE TABLE marks (name, mark, pass);");
//        sendCommandToServer("INSERT INTO marks VALUES ('Simon', 65, TRUE);");
//        sendCommandToServer("INSERT INTO marks VALUES ('Sion', 55, TRUE);");
//        sendCommandToServer("INSERT INTO marks VALUES ('Rob', 35, FALSE);");
//        sendCommandToServer("INSERT INTO marks VALUES ('Chris', 20, FALSE);");
//        String response = sendCommandToServer("SELECT * FROM marks;");
//        assertTrue(response.contains("[OK]"), "A valid query was made, however an [OK] tag was not returned");
//        assertFalse(response.contains("[ERROR]"), "A valid query was made, however an [ERROR] tag was returned");
//        assertTrue(response.contains("Simon"), "An attempt was made to add Simon to the table, but they were not returned by SELECT *");
//        assertTrue(response.contains("Chris"), "An attempt was made to add Chris to the table, but they were not returned by SELECT *");
//    }
//
//    // A test to make sure that querying returns a valid ID (this test also implicitly checks the "==" condition)
//    // (these IDs are used to create relations between tables, so it is essential that suitable IDs are being generated and returned !)
//    @Test
//    public void testQueryID() {
//        String randomName = generateRandomName();
//        sendCommandToServer("CREATE DATABASE " + randomName + ";");
//        sendCommandToServer("USE " + randomName + ";");
//        sendCommandToServer("CREATE TABLE marks (name, mark, pass);");
//        sendCommandToServer("INSERT INTO marks VALUES ('Simon', 65, TRUE);");
//        String response = sendCommandToServer("SELECT id FROM marks WHERE name == 'Simon';");
//        // Convert multi-lined responses into just a single line
//        String singleLine = response.replace("\n"," ").trim();
//        // Split the line on the space character
//        String[] tokens = singleLine.split(" ");
//        // Check that the very last token is a number (which should be the ID of the entry)
//        String lastToken = tokens[tokens.length-1];
//        try {
//            Integer.parseInt(lastToken);
//        } catch (NumberFormatException nfe) {
//            fail("The last token returned by `SELECT id FROM marks WHERE name == 'Simon';` should have been an integer ID, but was " + lastToken);
//        }
//    }
//
//    // A test to make sure that databases can be reopened after server restart
//    @Test
//    public void testTablePersistsAfterRestart() {
//        String randomName = generateRandomName();
//        sendCommandToServer("CREATE DATABASE " + randomName + ";");
//        sendCommandToServer("USE " + randomName + ";");
//        sendCommandToServer("CREATE TABLE marks (name, mark, pass);");
//        sendCommandToServer("INSERT INTO marks VALUES ('Simon', 65, TRUE);");
//        // Create a new server object
//        server = new DBServer();
//        sendCommandToServer("USE " + randomName + ";");
//        String response = sendCommandToServer("SELECT * FROM marks;");
//        assertTrue(response.contains("Simon"), "Simon was added to a table and the server restarted - but Simon was not returned by SELECT *");
//    }
//
//    // Test to make sure that the [ERROR] tag is returned in the case of an error (and NOT the [OK] tag)
//    @Test
//    public void testForErrorTag() {
//        String randomName = generateRandomName();
//        sendCommandToServer("CREATE DATABASE " + randomName + ";");
//        sendCommandToServer("USE " + randomName + ";");
//        sendCommandToServer("CREATE TABLE marks (name, mark, pass);");
//        sendCommandToServer("INSERT INTO marks VALUES ('Simon', 65, TRUE);");
//        String response = sendCommandToServer("SELECT * FROM libraryfines;");
//        assertTrue(response.contains("[ERROR]"), "An attempt was made to access a non-existent table, however an [ERROR] tag was not returned");
//        assertFalse(response.contains("[OK]"), "An attempt was made to access a non-existent table, however an [OK] tag was returned");
//    }

}
