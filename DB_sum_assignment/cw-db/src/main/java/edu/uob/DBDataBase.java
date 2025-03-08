package edu.uob;

import java.io.*;
import java.util.HashMap;

public class DBDataBase {

    private File[] tableList;
    private HashMap<String, DBTable> tableMap; //what I want to be populated with is the fileName:Table objects

    public DBDataBase(File currDBFolder) throws IOException {
        tableList = currDBFolder.listFiles(); // this is going to be the list
        tableMap = new HashMap<>();
        for (File table : tableList) { //Dereference of 'tableList' should not produce 'NullPointerException', because I ensured that it exists in the function where I call this method
            System.out.println("Creating a table: " + table.getName());
            DBTable inMemTable = new DBTable();
            inMemTable.readTableFromStorage(table);
            tableMap.put(table.getName(), inMemTable);
        }
//        System.out.println("In memory representation of the database exists, and its items are" + tableMap);
    }

    public void printDatabase() {
        tableMap.forEach((key, value) -> {
            System.out.println(key + " => " + value);
        });
    }

    public void addTable(String tableName, DBTable tableObject) {
        tableMap.put(tableName, tableObject);
    }

    public boolean isDBempty() {
        return tableMap.isEmpty();
    }

    public void printTableMap() {
        tableMap.forEach((key, value) -> {value.printTable();
            System.out.println("here is a tble");});
    }

    //need to adapt a selectTable form handleIncomingCommand method
}
