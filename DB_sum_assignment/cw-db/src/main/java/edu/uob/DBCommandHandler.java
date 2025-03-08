package edu.uob;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;


// absolutely need to write the try catch statements when trying to find and open the files, this code is fragile

public class DBCommandHandler {
    private String storageFolderPath; //same thing as in the server class

    private String currDBName;
    private DBDataBase currInMemDatabase;

    public DBCommandHandler() {
        storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
//        currDBFolder = null;
        currDBName = null;
    }

    public void createDatabase(String newDBName) throws IOException {

        String newDBPath = storageFolderPath + File.separator + newDBName;
        File newDB = new File(newDBPath);
        if (newDB.exists()) {
            throw new IOException("Database already exists");
        } else {
            if (!newDB.mkdir()){ // this line create a directory. But if it doesn't for some reason, then it throws an error
                System.out.println("!!!!!!!!!!!!!!!!Unable to create database!!!!!!!!!!!!!!");
                throw new IOException("!!!!!!!!!!!!!!!!Unable to create database!!!!!!!!!!!!!!"); // no idea how to test this, but i don't think I need
            }
        }
    }

    public void createTable(ArrayList<String> newTableName) throws IOException {
//        System.out.println("Was here");
//        System.out.println(currDBName);
        if (currDBName == null) throw new IOException("Database not selected");
        String DBorFolderPath = storageFolderPath + File.separator + currDBName;
        String newTablePath = DBorFolderPath + File.separator + newTableName.get(0);
        File newTable = new File(newTablePath + ".tab");
//        System.out.println("Was here");
        if (newTable.exists()) {
//            System.out.println("Was here");
            throw new IOException("Table already exists"); //this one is not tested, but I am pretty sure there are not bugs
        } else {
            if (!newTable.createNewFile()){
                System.out.println("!!!!!!!!!!!!!!!!Unable to create table!!!!!!!!!!!!!!");
                throw new IOException("!!!!!!!!!!!!!!!!Unable to create table!!!!!!!!!!!!!!"); // no idea how to test this, but i don't think I need
            }
            DBTable inMemTable = new DBTable();
            if (!newTableName.get(1).equals(";")) {
                ArrayList<String> attributes = new ArrayList<>(newTableName.subList(1, newTableName.size() - 1));
                System.out.println("attributes: " + attributes);
                inMemTable.insertFirstLine(attributes);
                inMemTable.writeTableToStorage(newTable);
                System.out.println("Contents of the new table: ");
                inMemTable.printTable();
            }
            currInMemDatabase.addTable(newTableName.get(0) + ".tab", inMemTable);
//            currInMemDatabase.printDatabase();
        }
    }

    public void useDatabase(String databaseName) throws IOException {
        // first of all, need to check if the database that I want to use actually exists
        String database = storageFolderPath + File.separator + databaseName;
        File dbToUse = new File(database);
        if (!dbToUse.exists()) {
//            System.out.println("Database file not found: " + database);
            throw new IOException("Database file not found");
        }
        //ok, so the database does exist, now I need to create the in-memory representation of that database

        currInMemDatabase = new DBDataBase(dbToUse);

        //this part is not necessarily needed but it's good to have for sanity check
        if (currInMemDatabase.isDBempty()){
            System.out.println("Database is empty");
        } else {
            currInMemDatabase.printTableMap();
        }
        currDBName = databaseName;
//        System.out.println("Database selected: " + currInMemDatabase);
//        currInMemDatabase.printDatabase();
    }

    public void insertCommand(String tableName, ArrayList<String> attributes) throws IOException {
        if (currDBName == null) throw new IOException("Database not selected");
//        currInMemDatabase.printDatabase();
//        System.out.println("table i'm trying to find (without .tab) is " + tableName);
        if (!currInMemDatabase.containsTable(tableName + ".tab")) { // this may be a bad way to handle the problem of names having/not having extension
            throw new IOException("selectCommand: Table to insert files to not found");
        } else {
//            throw new IOException("selectCommand: Table found");
            DBTable selectedTable = currInMemDatabase.getTable(tableName + ".tab");
            if (!selectedTable.isEmpty()) {
                if (selectedTable.size() - 1 != attributes.size()) {
                    throw new IOException("selectCommand: attribute size mismatches with current table width");
                }
                selectedTable.insertLineIntoTable(attributes);
            } else {
                selectedTable.insertFirstLine(attributes);
            }

            String sep = File.separator;
            String ssdTablePath = storageFolderPath + sep + currDBName + sep + tableName + ".tab";
            File ssdTable = new File(ssdTablePath);
            if (!ssdTable.exists()) {
                throw new IOException("selectCommand: Table to insert files to not found");
            } else {
                selectedTable.writeTableToStorage(ssdTable);
            }

        }


        // I have just realised that I do not need this, because I am working with the in-memory representation of the database, that was created after the use command

    }
}