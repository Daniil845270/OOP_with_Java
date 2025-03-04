package edu.uob;

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
    private String query;
    //    String query = "  INSERT  INTO  people   VALUES(  'Simon Lock'  ,35, 'simon@bristol.ac.uk' , 1.8  ) ; ";
    private String[] specialCharacters = {"(",")",",",";"};
    private ArrayList<String> tokens;
    private DBCommandHandler cmdExecuter;


    public DBParser(String command) throws IOException {
        query = command;
        tokens = new ArrayList<String>();
        setup();
        cmdExecuter = new DBCommandHandler();
        decideOnCommand();
    }

    private void setup() {
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
        System.out.println("'The incoming command is'");
        for(int i=0; i<tokens.size(); i++) System.out.println(tokens.get(i));
        System.out.println("'The end of incoming command'");

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
    private void decideOnCommand() throws IOException {
        if (tokens.get(0).equalsIgnoreCase("create")) {
            if (tokens.get(1).equalsIgnoreCase("database")) {
                // does tokens.get(2) even exist?
                System.out.println("Creating a database with a name '" + tokens.get(2) + "'");
                cmdExecuter.createDatabase(tokens.get(2).toLowerCase());
            }
        } else if (tokens.get(0).equalsIgnoreCase("use")) {
            cmdExecuter.useDatabase(tokens.get(1).toLowerCase());
        }
    }
}
