package edu.uob;

import java.io.Serial;
import java.io.Serializable;
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
            checkHorizVertWin(rowNum, colNum);
            checkDiagWin(rowNum, colNum);
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

        if ((command.charAt(1) - '0' - 1) < 0 || (command.charAt(1) - '0' - 1) > 9) {
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

    public void checkHorizVertWin(int rowNum, int colNum) {
        //game detection part of the method
        //horizontal checking - check every possibility on the row
        //the logic is a bit fucked up and the algorithm is hugely inefficient, but it should work
        for (int col = 0; col < gameModel.getNumberOfColumns() - 2; col++) {
            if (gameModel.getCellOwner(rowNum, col) != null &&
                    (gameModel.getCellOwner(rowNum, col) == gameModel.getCellOwner(rowNum, col + 1)) &&
                    (gameModel.getCellOwner(rowNum, col) == gameModel.getCellOwner(rowNum, col + 2))) {
                gameModel.setWinner(gameModel.getCellOwner(rowNum, colNum));
            }
        }
        //vertical checking
        for (int row = 0; row < gameModel.getNumberOfRows() - 2; row++) {
            if (gameModel.getCellOwner(row, colNum) != null &&
                    (gameModel.getCellOwner(row, colNum) == gameModel.getCellOwner(row + 1, colNum)) &&
                    (gameModel.getCellOwner(row, colNum) == gameModel.getCellOwner(row + 2, colNum))) {
                gameModel.setWinner(gameModel.getCellOwner(row, colNum));
            }
        }
    }

    public void checkDiagWin(int rowNum, int colNum) {
        //diagonal checking
        //top left diagonal check
        int lrow = rowNum;
        int lcol = colNum;
        while ((lrow > 0) && (lcol > 0)) {
            lrow--; lcol--;
        }
        for (; ((lrow < gameModel.getNumberOfRows() - 2) && (lcol < gameModel.getNumberOfColumns() - 2)); lrow++, lcol++) {
//                System.out.println(lrow);
//                System.out.println(lcol);
            if (gameModel.getCellOwner(lrow, lcol) != null &&
                    (gameModel.getCellOwner(lrow, lcol) == gameModel.getCellOwner(lrow + 1, lcol + 1)) &&
                    (gameModel.getCellOwner(lrow, lcol) == gameModel.getCellOwner(lrow + 2, lcol + 2))) {
                gameModel.setWinner(gameModel.getCellOwner(lrow, lcol));
            }
        }
        //bottom left diagonal check, test this
        int rrow = rowNum;
        int rcol = colNum;
        while ((rrow < gameModel.getNumberOfRows() - 1) && (rcol > 0)) { //this was a bug logically
            rrow++; rcol--;
        }
        for (; ((rrow > 1) && (rcol < gameModel.getNumberOfColumns() - 2)); rrow--, rcol++) {
            if (gameModel.getCellOwner(rrow, rcol) != null &&
                    (gameModel.getCellOwner(rrow, rcol) == gameModel.getCellOwner(rrow - 1, rcol + 1)) &&
                    (gameModel.getCellOwner(rrow, rcol) == gameModel.getCellOwner(rrow - 2, rcol + 2))) {
                gameModel.setWinner(gameModel.getCellOwner(rrow, rcol));
            }
        }
    }

    public void checkDraw() {
        //implement draw check, need to test this
        //i guess it works, but it wasn't explicit
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
    public void increaseWinThreshold() {}
    public void decreaseWinThreshold() {}
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

        //optional: print to the console of the reinitialised board
        System.out.println("Board is now reset!");
    }
}