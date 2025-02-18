package edu.uob;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

import edu.uob.OXOMoveException.*;

public class OXOController implements Serializable {
    @Serial private static final long serialVersionUID = 1;
    OXOModel gameModel;

    public OXOController(OXOModel model) {
        gameModel = model;
    }


    public void handleIncomingCommand(String command) throws OXOMoveException {

        if (gameModel.getWinner() == null && (!gameModel.isGameDrawn())) {

            if (command.length() != 2){
                throw new InvalidIdentifierLengthException(command.length());
            }

            //create the test cases, delete when done
            //System.out.println("sendCommandToController(\"" + command + "\");");

            //set what player is currently the turn
            int playerNumber = gameModel.getCurrentPlayerNumber();
            OXOPlayer currPlayer = gameModel.getPlayerByNumber(playerNumber);

            int rowNum = convertFirstCommandLetter(command);
            int colNum = command.charAt(1) - '0' - 1;

            setCellOwner(rowNum, colNum, playerNumber, currPlayer);
            gameModel.setGameOngoing(true);
            checkHorizVertDiagWin(rowNum, colNum);
            checkDraw();
        }
    }

    public int convertFirstCommandLetter(String command) throws OXOMoveException {
        //translate the row letter into number
        //need to figure out what/how to treat the invalid input
        //this program is going to return
        //also contains check for the outside boundaries for the commands
        int rowNum;

        if (command.charAt(0) == 'a' || command.charAt(0) == 'A') {
            rowNum = 0;
        } else if (command.charAt(0) == 'b' || command.charAt(0) == 'B') {
            rowNum = 1;
        } else if (command.charAt(0) == 'c' || command.charAt(0) == 'C') {
            rowNum = 2;
        } else if (command.charAt(0) == 'd' || command.charAt(0) == 'D') {
            rowNum = 3;
        } else if (command.charAt(0) == 'e' || command.charAt(0) == 'E') {
            rowNum = 4;
        } else if (command.charAt(0) == 'f' || command.charAt(0) == 'F') {
            rowNum = 5;
        } else if (command.charAt(0) == 'g' || command.charAt(0) == 'G') {
            rowNum = 6;
        } else if (command.charAt(0) == 'h' || command.charAt(0) == 'H') {
            rowNum = 7;
        } else if (command.charAt(0) == 'i' || command.charAt(0) == 'I') {
            rowNum = 8;
        } else { // that is not the best way to write this, but if it works, it works
            throw new InvalidIdentifierCharacterException(OXOMoveException.RowOrColumn.ROW, command.charAt(0));
        }

        if (rowNum >= gameModel.getNumberOfRows()) {
//            throw new InvalidIdentifierCharacterException(OXOMoveException.RowOrColumn.ROW, command.charAt(0));
            throw new OutsideCellRangeException(OXOMoveException.RowOrColumn.ROW, rowNum + 1);
        }

        System.out.println((command.charAt(1) - '0' - 1));
        if ((command.charAt(1) - '0' - 1) < 0 || (command.charAt(1) - '0' - 1) > 8) {
            throw new InvalidIdentifierCharacterException(RowOrColumn.COLUMN, (command.charAt(1) ));
        }

        if ((command.charAt(1) - '0' - 1) >= gameModel.getNumberOfColumns()) {
            throw new OutsideCellRangeException(RowOrColumn.COLUMN, (command.charAt(1) - '0'));
        }

        return rowNum;
    }

    public void setCellOwner(int rowNum, int colNum, int playerNumber, OXOPlayer currPlayer) throws OXOMoveException  {
        //this sets the cell owner, given that it is not occupied already
        //have to figure out how to deal with invalid input, but can do it later
        if (gameModel.getCellOwner(rowNum, colNum) == null) {
//                System.out.println(rowNum + " " + colNum);
            gameModel.setCellOwner(rowNum, colNum, currPlayer);

            //switch the turn to the next player
            if (playerNumber == gameModel.getNumberOfPlayers() - 1) {
                gameModel.setCurrentPlayerNumber(0);
            } else {
                playerNumber++;
                gameModel.setCurrentPlayerNumber(playerNumber);
            }
        } else {
            throw new CellAlreadyTakenException(rowNum + 1, colNum + 1);
        }
    }

    public void checkHorizVertDiagWin(int rowNum, int colNum) {
        //game detection part of the method
        //horizontal checking - check every possibility on the row
        //the logic is a bit fucked up and the algorithm is hugely inefficient, but it should work

        gameModel.setWinCounter(0);
        gameModel.setWinCandidate(null);
        for (int col = 0; col < gameModel.getNumberOfColumns(); col++) {
            winDetector(rowNum, col);
        }

        gameModel.setWinCounter(0);
        gameModel.setWinCandidate(null);
        for (int row = 0; row < gameModel.getNumberOfRows(); row++) {
            winDetector(row, colNum);
        }

        //top left diagonal check
        int lrow = rowNum;
        int lcol = colNum;
        while ((lrow > 0) && (lcol > 0)) { lrow--; lcol--; }
        gameModel.setWinCounter(0);
        gameModel.setWinCandidate(null);
        for (; ((lrow < gameModel.getNumberOfRows()) && (lcol < gameModel.getNumberOfColumns())); lrow++, lcol++) {
            winDetector(lrow, lcol);
        }

        //bottom left diagonal check, test this
        int rrow = rowNum;
        int rcol = colNum;
        while ((rrow < gameModel.getNumberOfRows() - 1) && (rcol > 0)) { rrow++; rcol--; }
        gameModel.setWinCounter(0);
        gameModel.setWinCandidate(null);
        for (; ((rrow > -1) && (rcol < gameModel.getNumberOfColumns())); rrow--, rcol++) {
            winDetector(rrow, rcol);
        }
    }

    public void winDetector(int row, int col) {
        if (gameModel.getCellOwner(row, col) != null && gameModel.getWinCandidate() == null) {
            gameModel.setWinCandidate(gameModel.getCellOwner(row, col));
            gameModel.setWinCounter(1);
        }
        else if (gameModel.getCellOwner(row, col) != null) {
            if (gameModel.getCellOwner(row, col) == gameModel.getWinCandidate()) {
                gameModel.setWinCounter(gameModel.getWinCounter() + 1);
                if (gameModel.getWinCounter() == gameModel.getWinThreshold()) {
                    gameModel.setWinner(gameModel.getWinCandidate());
                }
            }
            else if (gameModel.getCellOwner(row, col) != gameModel.getWinCandidate() && gameModel.getCellOwner(row, col) != null) {
                gameModel.setWinCandidate(gameModel.getCellOwner(row, col));
                gameModel.setWinCounter(1);
            }
        }
        else if (gameModel.getCellOwner(row, col) == null) {
                gameModel.setWinCandidate(null);
                gameModel.setWinCounter(0);
        }
    }


    public void checkDraw() {
        boolean drawn = true;
        for (int i = 0; i < gameModel.getNumberOfRows(); i++) {
            for (int j = 0; j < gameModel.getNumberOfColumns(); j++) {
                if (gameModel.getCellOwner(i, j) == null){
                    drawn = false;
                }
            }
        }
        if (drawn) {
            gameModel.setGameDrawn(true);
        }
    }

    public void addRow() {
        if (gameModel.getWinner() == null) {
            gameModel.addRow();
            gameModel.setGameDrawn(false);
        }
    }
    public void removeRow() { //implement a checker that prevents removing rows or columns that are not null
        gameModel.removeRow();
    }
    public void addColumn() {
        if (gameModel.getWinner() == null){
            gameModel.addColumn();
            gameModel.setGameDrawn(false);
        }
    }
    public void removeColumn() {
        gameModel.removeColumn();
    }
    public void increaseWinThreshold() {
        int currWinThreshold = gameModel.getWinThreshold();
        if (currWinThreshold < (gameModel.getNumberOfColumns())
                && currWinThreshold < (gameModel.getNumberOfRows())
                && gameModel.getWinner() == null) {
            gameModel.setWinThreshold(currWinThreshold + 1);
            System.out.println("Win Threshold Increased");
        }
        System.out.println("Win threshold: " + gameModel.getWinThreshold());
    }
    public void decreaseWinThreshold() {
        int currWinThreshold = gameModel.getWinThreshold();
        if (gameModel.getWinThreshold() > 3 && !gameModel.isGameOngoing()) {
            gameModel.setWinThreshold(currWinThreshold - 1);
            System.out.println("Win Threshold Decreased");
        }
        System.out.println("Win threshold: " + gameModel.getWinThreshold());
    }

    public void reset() {
        //set the player to the first player
        gameModel.setCurrentPlayerNumber(0);
        //iterate through the 2D array and set the owner of each cell to null
        int rows = gameModel.getNumberOfRows();
        int cols = gameModel.getNumberOfColumns();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gameModel.setCellOwner(i, j, null);
            }
        }

        //reset the winner and the player
        gameModel.setWinner(null);
        gameModel.setCurrentPlayerNumber(0);
        gameModel.setGameDrawn(false); //if it was true
        gameModel.setGameOngoing(false);

        //optional: print to the console of the reinitialised board
        System.out.println("Board is now reset!");
    }
}