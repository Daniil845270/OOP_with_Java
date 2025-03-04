package edu.uob;

import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;

public class DBTable {

    private ArrayList<ArrayList<String>> table;
    private long lineCnt;

    public DBTable(File tableFile) throws IOException {
        //figure out what to write here
        table = new ArrayList<>();
        lineCnt = 0;

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
        }
    }
}