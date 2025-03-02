package edu.uob;

import java.io.*;
import java.util.HashMap;

public class DBDataBase {

    private File[] tableList;
    private HashMap<String, DBTable> tableMap; //what I want to be populated with is the fileName:Table objects

    public DBDataBase(File currDBFolder) throws IOException {
        tableList = currDBFolder.listFiles(); // this is going to be the list
        tableMap = new HashMap<>();
        for (File table : tableList) {
            System.out.println("Creating a table: " + table.getName());
            tableMap.put(table.getName(), new DBTable(table));
        }
    }
}
