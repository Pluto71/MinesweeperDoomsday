package com.example.dgoodell.doomsday;

/**
 * Created by dgoodell on 9/29/16.
 */
public class Box{

    private boolean hasBomb;
    private boolean isSearched;
    private int[] coordinates;
    private int counter;
    private boolean gameOver;

    public Box() {

        hasBomb = false;
        isSearched = false;
        coordinates = new int[2];
        gameOver = false;

        // The number of bombs in the perimeter of the box
        // If the number is -1, the box has not been checked,
        // if the number is 0, there are no bombs on the box's perimeter,
        // if the number is between 1 & 8, there are the corresponding number of bombs on the
        // box's perimeter,
        // if the number is greater than 8, then there is a flag on the box.
        counter = -1;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver() {
        gameOver = true;
    }

    // Used to draw the correct image on the board in BoardView.
    public int getCounter(){
        return counter;
    }

    public void setCounter(int numberOfBombs){
        counter = numberOfBombs;
    }


    public int getX() {
        return coordinates[0];
    }

    public int getY() {
        return coordinates[1];
    }

    public void setCoordinates(int x, int y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }

    public boolean hasFlag() {
        if(counter > 8){
            return true;
        } else return false;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void setSearched() {
        isSearched = true;
        // Increase counter from -1 to 0
        counter++;
    }

    public boolean isSearched() {
        return isSearched;
    }

    public void changeHasFlag() {
        if(counter == 9){
            counter = counter - 10;
        } else {
            counter = counter + 10;
        }
    }

    public void setHasBomb() {
        hasBomb = true;
    }

}
