package edu.uob;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;


// absolutely need to write the try catch statements when trying to find and open the files, this code is fragile

public class DBCommandHandler {
    private String storageFolderPath; //same thing as in the server class
    private File currDBFolder;
    private File currDBTable;
    private DBDataBase currDatabase;

    public DBCommandHandler() {
        storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        currDBFolder = null;
    }

    public void selectADatabase(String databaseName) throws IOException { //what if the database is not there or a command is malformed?//find out what to do with it later
        currDBFolder = null;
        File[] dbList = new File(storageFolderPath).listFiles();
        for (File dataBase : dbList) {
            if (dataBase.getName().equals(databaseName.toLowerCase())) {
                currDBFolder = dataBase;
                System.out.println("Found database " + currDBFolder.getName());
            }
        }
        currDatabase = new DBDataBase(currDBFolder);
    }

    // this method needs to check whether the database that I am creating doesn't already exist
    public void createDatabase(String databaseName) throws IOException {
        String newDBName = storageFolderPath + File.separator + databaseName;
        File newDB = new File(newDBName);
        if (newDB.exists()) {
            System.out.println("Database already exists");
        } else {
            if (!newDB.mkdir()){
                System.out.println("Unable to create database");
                throw new IOException("Unable to create database");
            }
            System.out.println("Database created");
        }
    }

    public void useDatabase(String databaseName) throws IOException {
        // first of all, need to check if the database that I want to use actually exists
        String database = storageFolderPath + File.separator + databaseName;
        File dbToUse = new File(database);
        if (!dbToUse.exists()) {
//            System.out.println("Database file not found: " + database);
            throw new IOException("Database file not found: " + database);
        }
        //ok, so the database does exist, now I need to create the in-memory representation of that database
        currDatabase = new DBDataBase(dbToUse);
        System.out.println("Database selected");
        currDatabase.printDatabase();
    }
}









//
//    public String getSelectedDatabase() {
//        return currDBFolder.getName();
//    }
//
//    // this is a very bad piece of code, need to make it more robust
//    public void selectATable(String tableName) throws IOException {
//        for (File table : currDBFolder.listFiles()) { //Dereference of 'currDBFolder.listFiles()' may produce 'NullPointerException' -> need to throw an exception for that
//            String candidateTable = table.getName().toLowerCase();
//            if (candidateTable.indexOf(".") > 0) {
//                candidateTable = candidateTable.substring(0, candidateTable.indexOf("."));
//            }
//            if (candidateTable.equals(tableName.toLowerCase())) {
//                currDBTable = table;
////                System.out.println("Found table " + currDBTable.getName() + " " + candidateTable);
//            }
//        }
//    }
//
//    public String getSelectedTable() {
//        return currDBTable.getName();
//    }
//
//    public void printSelectedTable() throws IOException {
//        if (currDBTable.exists()){
//            BufferedReader lineCntReader = new BufferedReader(new FileReader(currDBTable));
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(currDBTable));
//            long lineCnt = lineCntReader.lines().count();
//            for (int lineNumber = 0; lineNumber < lineCnt; lineNumber++) {
//                String[] line = bufferedReader.readLine().split("\\t");
//                System.out.println(Arrays.toString(line));
////                System.out.println(bufferedReader.readLine());
//            }
//            lineCntReader.close();
//            bufferedReader.close();
//        }
//    }