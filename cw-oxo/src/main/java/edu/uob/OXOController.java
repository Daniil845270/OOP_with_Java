package edu.uob;

import java.io.Serial;
import java.io.Serializable;

public class OXOController implements Serializable {
    @Serial private static final long serialVersionUID = 1;
    OXOModel gameModel;

    public OXOController(OXOModel model) {
        gameModel = model;
    }

    public void handleIncomingCommand(String command) throws OXOMoveException {
        //When updating the game state, you should set the 'current player' to be the player whose turn it is next. This is to ensure that the view shows graphically the player who is about to take a turn.

        //set what player is currently the turn
        int playerNumber = gameModel.getCurrentPlayerNumber();
        OXOPlayer currPlayer = gameModel.getPlayerByNumber(playerNumber);

        //translate the row letter into number
        //need to figure out what/how to treat the invalid input
        int rowNum;
        if (command.charAt(0) == 'a' || command.charAt(0) == 'A') {
            rowNum = 0;
        } else if (command.charAt(0) == 'b' || command.charAt(0) == 'B') {
            rowNum = 1;
        } else if (command.charAt(0) == 'c' || command.charAt(0) == 'C') {
            rowNum = 2;
        } else {
            rowNum = -1;
        }

        //this sets the cell owner, given that it is not occupied already
        //have to figure out how to deal with invalid input, but can do it later
        if (gameModel.getCellOwner(rowNum, command.charAt(1) - '0' - 1) == null){
            gameModel.setCellOwner(rowNum, command.charAt(1) - '0' - 1, currPlayer);

            //switch the turn to the next player
            if (playerNumber == gameModel.getNumberOfPlayers() - 1) {
                gameModel.setCurrentPlayerNumber(0);
            } else {
                playerNumber++;
                gameModel.setCurrentPlayerNumber(playerNumber);
            }
        } else {
            System.out.println("Cell owner is already occupied");
        }
    }
    public void addRow() {
        gameModel.addRow();
    }
    public void removeRow() {
        gameModel.removeRow();
    }
    public void addColumn() {
        gameModel.addColumn();
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
        //optional: print to the console of the reinitialised board
        System.out.println("Board is now reset!");
    }
}
