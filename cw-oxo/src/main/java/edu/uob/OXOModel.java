package edu.uob;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class OXOModel implements Serializable {
    @Serial private static final long serialVersionUID = 1;
//    private OXOPlayer[][] cells;
    private ArrayList<ArrayList<OXOPlayer>> cells;
    private OXOPlayer[] players;
    private int currentPlayerNumber;
    private OXOPlayer winner;
    private boolean gameDrawn;
    private int winThreshold;
    private boolean isOngoing;
    private OXOPlayer currWiningCandidate;
    private int currWinCnt;

    public OXOModel(int numberOfRows, int numberOfColumns, int winThresh) {
        winThreshold = winThresh;
//        cells = new OXOPlayer[numberOfRows][numberOfColumns];
        cells = new ArrayList<>(numberOfRows);
        for (int i = 0; i < numberOfRows; i++) {
            ArrayList<OXOPlayer> rowList = new ArrayList<>(numberOfColumns);
            for (int c = 0; c < numberOfColumns; c++) {
                rowList.add(null);
            }
            cells.add(rowList);
        }
        players = new OXOPlayer[2];
        isOngoing = false; // find out if you need to set it to false or it is not needed
        currWiningCandidate = null;
        currWinCnt = 0;
    }

    public int getNumberOfPlayers() {
        return players.length;
    }

    public void addPlayer(OXOPlayer player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                return;
            }
        }
    }

    public OXOPlayer getPlayerByNumber(int number) {
        return players[number];
    }

    public OXOPlayer getWinner() {
        return winner;
    }

    public void setWinner(OXOPlayer player) {
        winner = player;
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int playerNumber) {
        currentPlayerNumber = playerNumber;
    }

    public int getNumberOfRows() {
        return cells.size();
    }

    public int getNumberOfColumns() {
        return cells.get(0).size();
    }

//    public OXOPlayer getCellOwner(int rowNumber, int colNumber) {
//        return cells[rowNumber][colNumber];
//    }
//
//    public void setCellOwner(int rowNumber, int colNumber, OXOPlayer player) {
//        cells[rowNumber][colNumber] = player;
//    }

    public OXOPlayer getCellOwner(int rowNumber, int colNumber) {
        return cells.get(rowNumber).get(colNumber);
    }

    public void setCellOwner(int rowNumber, int colNumber, OXOPlayer player) {
        cells.get(rowNumber).set(colNumber, player);
    }

    public void setWinThreshold(int winThresh) {
        winThreshold = winThresh;
    }

    public int getWinThreshold() {
        return winThreshold;
    }

    public void setGameDrawn(boolean isDrawn) {
        gameDrawn = isDrawn;
    }

    public boolean isGameDrawn() {
        return gameDrawn;
    }


    //Don’t Remove Rows/Columns That Contain Moves - may implement later
    public void addRow() {
        if (getNumberOfRows() < 9) {
            ArrayList<OXOPlayer> newList = new ArrayList<>(getNumberOfColumns());
            for (int c = 0; c < getNumberOfColumns(); c++) {
                newList.add(c, null);
            }
            cells.add(newList);
        } else {
            System.out.println("Too many rows");
        }
    }
    public void addColumn() {
        if (getNumberOfColumns() < 9) {
            for (int r = 0; r < getNumberOfRows(); r++) {
                cells.get(r).add(null);
            }
        } else {
            System.out.println("Too many columns");
        }
    }
    public void removeRow(){
        if (getNumberOfRows() > 3){
            cells.remove(getNumberOfRows() - 1);
        } else {
            System.out.println("Too few rows");
        }
    }
    public void removeColumn(){
        if (getNumberOfColumns() > 3){
            for (int r = 0; r < getNumberOfRows(); r++) {
                cells.get(r).remove(getNumberOfColumns() - 1);
            }
        } else {
            System.out.println("Too few columns");
        }

    }

    public boolean isGameOngoing() {
        return isOngoing;
    }

    public void setGameOngoing(boolean ongoingFlag) {
        isOngoing = ongoingFlag;
    }

    public OXOPlayer getWinCandidate() {
        return currWiningCandidate;
    }

    public void setWinCandidate(OXOPlayer winner) {
        currWiningCandidate = winner;
    }

    public int getWinCounter() {
        return currWinCnt;
    }

    public void setWinCounter(int cnt) {
        currWinCnt = cnt;
    }
}
