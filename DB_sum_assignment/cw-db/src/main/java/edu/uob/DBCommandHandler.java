package edu.uob;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;


public class DBCommandHandler {
    private String storageFolderPath; //same thing as in the server class
    private File currDBFolder;
    private File currDBTable;

    public DBCommandHandler() {
        storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
    }

    public void selectADatabase(String databaseName) throws IOException { //what if the database is not there or a command is malformed?
        boolean found = false; //find out what to do with it later
        File[] dbList = new File(storageFolderPath).listFiles();
        for (File dataBase : dbList) {
            if (dataBase.getName().toLowerCase().equals(databaseName.toLowerCase())) {
                currDBFolder = dataBase;
                found = true;
                System.out.println("Found database " + currDBFolder.getName());
            }
        }
    }

    public String getSelectedDatabase() {
        return currDBFolder.getName();
    }

    // this is a very bad piece of code, need to make it more robust
    public void selectATable(String tableName) throws IOException {
        for (File table : currDBFolder.listFiles()) { //Dereference of 'currDBFolder.listFiles()' may produce 'NullPointerException' -> need to throw an exception for that
            String candidateTable = table.getName().toLowerCase();
            if (candidateTable.indexOf(".") > 0) {
                candidateTable = candidateTable.substring(0, candidateTable.indexOf("."));
            }
            if (candidateTable.equals(tableName.toLowerCase())) {
                currDBTable = table;
//                System.out.println("Found table " + currDBTable.getName() + " " + candidateTable);
            }
        }
    }

    public String getSelectedTable() {
        return currDBTable.getName();
    }

    public void printSelectedTable() throws IOException {
        if (currDBTable.exists()){
            BufferedReader lineCntReader = new BufferedReader(new FileReader(currDBTable));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(currDBTable));
            long lineCnt = lineCntReader.lines().count();
            for (int lineNumber = 0; lineNumber < lineCnt; lineNumber++) {
                System.out.println(bufferedReader.readLine());
            }
            lineCntReader.close();
            bufferedReader.close();
        }
    }
}


//
//
//    // should have a constructor that takes in the command from handleCommand()
//    // e.g.
//    private String cmdStr;
//    private ArrayList<String> cmdList = new ArrayList<>(); //figure out later what to do with it
//    private DBMap dbMap;
//    private String storageFolderPath;
//
//    public DBCommandHandler(String command) {
//        // initialse a var
//        cmdStr = command;
//        // call a method from the parser class that would initialise a parsed command
//
//        dbMap = new DBMap();
//        //check that this is right
//        storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
//    }
//
//    public void readSelectedDatabase(String dbName) {
//        //1) takes in a name of the database,
//        //2 searches it through the files of the databses directory
//        // 3)finds the database that is being requested
//        // 4) populates the dbMap variable with the names of the tables:objects of the tables
//        File[] dbList = new File(storageFolderPath).listFiles();
//        if (dbList != null) {
//            for (File f : dbList) {
//                if (f.getName().equals(dbName)) {
//                    readFoundDatabase(f);
//                }
//            }
//        }
//    }
//
//    public void readFoundDatabase(File dbFile) {
//        for (File tableFile : dbFile.listFiles()) {
//            dbMap.addTable(tableFile);
//        }
//    }
//
//    //I should probably wh
//}