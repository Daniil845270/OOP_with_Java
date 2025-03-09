package edu.uob;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// Stuff that I thought of, but considered to implement later
//1) The incoming command is not check for bein malformed like at all
//    - (it can be an empty string,
//    - a bunch of jiberrish,
//    - valid key words, but indifferent order,
//none of which is handled at the moment)

public class DBParser {
    public String query; // that is an ununcapsulated stuff
    //    String query = "  INSERT  INTO  people   VALUES(  'Simon Lock'  ,35, 'simon@bristol.ac.uk' , 1.8  ) ; ";
    private String[] specialCharacters = {"(",")",",",";"};
    private ArrayList<String> tokens;
    private DBCommandHandler cmdExecuter;
    private int tokensNum;


//    public DBParser(String command) throws IOException {
//        query = command;
//        tokens = new ArrayList<String>();
//        setup();
//        cmdExecuter = new DBCommandHandler();
//        decideOnCommand();
//    }

    public DBParser() {
//        query = command;
//        setup();
        cmdExecuter = new DBCommandHandler();
//        decideOnCommand();
    }

    public void acceptCommand(String command) throws IOException {
//        System.out.println(command);
        tokens = new ArrayList<String>();
        query = command;
//        System.out.println(query);
        setup();
        decideOnCommand();
    }



    public void setup() throws IOException {
        // Split the query on single quotes (to separate out query text from string literals)
        String[] fragments = query.split("'");
        for (int i=0; i<fragments.length; i++) {
            // Every other fragment is a string literal, so just add it straight to "result" token list
            if (i%2 != 0) tokens.add("'" + fragments[i] + "'");
                // If it's not a string literal, it must be query text (which needs further processing)
            else {
                // Tokenise the fragment into an array of strings - this is the "clever" bit !
                String[] nextBatchOfTokens = tokenise(fragments[i]);
                // Then copy all the tokens into the "result" list (needs a bit of conversion)
                tokens.addAll(Arrays.asList(nextBatchOfTokens));
            }
        }

        // Finally, loop through the result array list, printing out each token a line at a time
//        System.out.println("--------->The incoming command is");
        tokensNum = tokens.size();
        if (tokensNum < 2) {
            throw new IOException("Command has less than 2 tokens");
        }
        if (!tokens.get(tokensNum - 1).equals(";")) {
            throw new IOException("The command does not end with ';'");
        }
        tokens.set(0, tokens.get(0).toLowerCase());

//        for(int i=0; i<tokensNum; i++) {
////            tokens.get(i).toLowerCase();
////            tokens.set(i, tokens.get(i).toLowerCase());
//            System.out.println(tokens.get(i));
//        }
//        System.out.println("<---------The end of incoming command");
    }

    private String[] tokenise(String input) {
        // Add in some extra padding spaces either side of the "special characters"...
        // so we can be SURE that they are separated by AT LEAST one space (possibly more)
        for(int i=0; i<specialCharacters.length ;i++) {
            input = input.replace(specialCharacters[i], " " + specialCharacters[i] + " ");
        }
        // Remove any double spaces (the previous padding activity might have introduced some of these)
        while (input.contains("  ")) input = input.replace("  ", " "); // Replace two spaces by one
        // Remove any whitespace from the beginning and the end that might have been introduced
        input = input.trim();
        // Finally split on the space char (since there will now ALWAYS be a SINGLE space between tokens)
        return input.split(" ");
    }


    // again, this function does not check for malformed commands
    // on thing to keep in mind is that you have to get rid of redundant code -> .equalsIgnoreCase shouldn't even be there
    // the first letter is always

    private void decideOnCommand() throws IOException {
        if (tokens.get(0).equals("create")) {
//            System.out.println("was here");
            if (tokensNum < 4) {
                throw new IOException("Create command has less than 4 tokens"); // shortest command is "CREATE " "DATABASE " [DatabaseName] ";"
            }
            for (int i=1; i<3; i++) tokens.set(i, tokens.get(i).toLowerCase()); // put database/table and its name to lowercase, but if the command is  "CREATE " "TABLE " [TableName] "(" <AttributeList> ")",  the attibute list must not be put to lowercase (because an attribute is a name of a column)
            if (tokens.get(1).equals("database")) {
                if ((!tokens.get(3).equals(";")) || (tokensNum > 4)) {
                    throw new IOException("Create database command does not end with ';'");
                }
                cmdExecuter.createDatabase(tokens.get(2));
            } else if (tokens.get(1).equals("table")) {
//                if ((!tokens.get(3).equals(";")) || (!tokens.get(3).equals("(")) || (!tokens.get(tokensNum - 2).equals(")"))) { //don't really know how to make this right, think later
//                    throw new IOException("Create table command is malformed");
//                }
//                System.out.println("Table is about to be created");
//                ArrayList<String> table = new ArrayList<>(tokens.subList(2, tokens.size()));
                ArrayList<String> table = new ArrayList<>();
                table.add(tokens.get(2)); // adding the name to the string
                for (String attribute : tokens.subList(4, tokensNum - 1)) {
//                    System.out.println("I am going to put " + attribute);
                    if (!attribute.equals(",")) {
                        table.add(attribute);
                    }
                }
//                System.out.println(table);
                cmdExecuter.createTable(table);
            }
        } else if (tokens.get(0).equals("use")) {
            cmdExecuter.useDatabase(tokens.get(1).toLowerCase());
        } else if (tokens.get(0).equals("insert")) {
            if  (!insertMalformedChecker()) {
                throw new IOException("decideOnCommand: insert command malformed");
            } else {
                String tableName = tokens.get(2).toLowerCase();
                ArrayList<String> attributes = new ArrayList<>();
                for (String token : tokens.subList(5, tokensNum - 2)) { // there may not be any attributes, but that's fine for now
                    if (!token.equals(",")) {
                        attributes.add(token);
                    }
                }
                if (attributes.isEmpty()) throw new IOException("decideOnCommand: no attributes to insert into table");
//                System.out.println("tableName: " + tableName);
//                System.out.println("attributes: " + attributes);
                cmdExecuter.insertCommand(tableName, attributes);
            }
        }
        else {
            throw new IOException(tokens.get(0) + " is not yet implemented");
        }
    }

    private boolean insertMalformedChecker() {
        return (tokens.get(1).equalsIgnoreCase("into")
                && tokens.get(3).equalsIgnoreCase("VALUES")
                && tokens.get(4).equalsIgnoreCase("("))
                && (!tokens.get(5).equalsIgnoreCase(")")) //there must be at least 1 attribute (at least it think that should be the case)
                && (tokens.get(tokensNum - 2).equalsIgnoreCase(")")); //-2 because the last token is ;
    }
}
