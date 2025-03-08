package edu.uob;

import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;

public class DBTable {

    private ArrayList<ArrayList<String>> table;
    private long lineCnt;

    public DBTable() {
        //figure out what to write here
        table = new ArrayList<>();
        lineCnt = 0;
    }

    public void readTableFromStorage(File tableFile) throws IOException { // this was copied from the constructor, needs testing whether it still holds true
        if (tableFile.exists()){
            BufferedReader lineCntReader = new BufferedReader(new FileReader(tableFile));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(tableFile));
            lineCnt = lineCntReader.lines().count();
            for (int lineNumber = 0; lineNumber < lineCnt; lineNumber++) {
                String[] line = bufferedReader.readLine().split("\\t");
//                System.out.println(Arrays.toString(line));
//                System.out.println(bufferedReader.readLine());
                table.add(new ArrayList<>(Arrays.asList(line))); //check that this is actually correct
            }
            lineCntReader.close();
            bufferedReader.close();
//            for (int lineNumber = 0; lineNumber < lineCnt; lineNumber++) {
//                System.out.println(Arrays.toString(table.get(lineNumber).toArray()));
//            }
        } else {
            throw new IOException("readTableFromStorage exception: Table file does not exist");
        }
    }

    public void fillAttributesIntoArraylists(ArrayList<String> attributes) throws IOException {
//        System.out.println(attributes);
//        System.out.println(table);
        table.add(new ArrayList<>());
        for (String attribute : attributes) {
            table.get(0).add(attribute);
        }
    }

    public void printTable() {
        for (ArrayList<String> row : table) {
            System.out.println(Arrays.toString(row.toArray()));
        }
        // need to write this for sanity check & will be used later in the return of the array when manipulating tables
    }

    public void writeTableToStorage(File tableFile) throws IOException {
        //need to check the boxes regarding throwing exceptions when the program gets into an undesirable state
        if (tableFile.exists()){
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tableFile));
            for (ArrayList<String> row : table) {
                String tabSeparatedRow = String.join("\t", row);
                bufferedWriter.write(tabSeparatedRow);
            }
            bufferedWriter.close();
        } else {
            throw new IOException("writeTableToStorage exception: Table file does not exist");
        }

    }
}