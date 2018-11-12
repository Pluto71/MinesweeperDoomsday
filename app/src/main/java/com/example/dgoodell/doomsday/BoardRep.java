package com.example.dgoodell.doomsday;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by dgoodell on 9/29/16.
 */
public class BoardRep{

    private static BoardRep instance = null;
    private Box[][] model;
    private Box[] boxArray;

    public static BoardRep getInstance() {
        if (instance == null) {
            instance = new BoardRep();
        }
        return instance;
    }

    // Creates 1D array of Box objects and for each difficulty makes a certain number with bombs
    // Shuffles array to make it random
    public void createBoxArrays(int dim){
        int nSq = dim*dim;

        if (boxArray == null){
            boxArray = new Box[nSq];
        }

        if(dim==4){
            for(int i=0; i<3; i++){
                boxArray[i] = new Box();
                boxArray[i].setHasBomb();
            } for(int j=3; j<16; j++){
                boxArray[j] = new Box();
            }
        } else if(dim==5){
            for(int i=0; i<5; i++){
                boxArray[i] = new Box();
                boxArray[i].setHasBomb();
            } for(int j=5; j<25; j++){
                boxArray[j] = new Box();
            }
        } else if (dim==6) {
            for (int i = 0; i < 7; i++) {
                boxArray[i] = new Box();
                boxArray[i].setHasBomb();
            }
            for (int j = 7; j < 36; j++) {
                boxArray[j] = new Box();
            }
        }

        model = new Box[dim][dim];

    }

    public void shuffleArray(){
        Collections.shuffle(Arrays.asList(boxArray));
    }

    // Puts array of Box objects into 2D array
    public void setBoardRep(int dim){
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){
                int index = (i*dim)+j;
                model[i][j] = boxArray[index];
                model[i][j].setCoordinates(i, j);
            }
        }
    }

    public Box getBoxAtIndex(int x, int y){
        return model[x][y];
    }


    public void check(Box box, int dim){
        checkPerimeter(box, dim);

        if(box.getCounter() == 0){
            searchRec(box, dim);
        }

    }

    // Checks the perimeter of clicked box, counting the number of adjacent bombs
    public void checkPerimeter(Box box, int dim) {

        box.setSearched();

        int counter = box.getCounter();

        if (box.getX() > 0) {
            if ((model[box.getX() - 1][box.getY()]).hasBomb()) {
                counter++;
            }
        } if (box.getX() > 0 && box.getY() > 0) {
            if ((model[box.getX() - 1][box.getY() - 1]).hasBomb()) {
                counter++;
            }
        } if (box.getX() > 0 && box.getY() < (dim - 1)) {
            if ((model[box.getX() - 1][box.getY() + 1]).hasBomb()) {
                counter++;
            }
        } if (box.getX() < (dim - 1)) {
            if ((model[box.getX() + 1][box.getY()]).hasBomb()) {
                counter++;
            }
        } if (box.getX() < (dim - 1) && box.getY() > 0) {
            if ((model[box.getX() + 1][box.getY() - 1]).hasBomb()) {
                counter++;
            }
        } if (box.getX() < (dim - 1) && box.getY() < (dim - 1)) {
            if ((model[box.getX() + 1][box.getY() + 1]).hasBomb()) {
                counter++;
            }
        } if (box.getY() > 0) {
            if ((model[box.getX()][box.getY() - 1]).hasBomb()) {
                counter++;
            }
        } if (box.getY() < (dim - 1)) {
            if ((model[box.getX()][box.getY() + 1]).hasBomb()) {
                counter++;
            }
        }
        box.setCounter(counter);
    }

    // Search around box if it hasn't been searched and it isn't flagged.
    // Currently out of service.
    public void searchRec(Box box, int dim) {
        if (box.getX() > 0) {
            if (!(model[box.getX() - 1][box.getY()].isSearched()) &&
                    !model[box.getX() - 1][box.getY()].hasFlag()) {
                check(model[box.getX() - 1][box.getY()], dim);
            }
        } if (box.getX() < (dim - 1)) {
            if (!(model[box.getX() + 1][box.getY()].isSearched()) &&
                    !model[box.getX() + 1][box.getY()].hasFlag()) {
                check(model[box.getX() + 1][box.getY()], dim);
            }
        } if (box.getY() > 0) {
            if (!(model[box.getX()][box.getY() - 1].isSearched()) &&
                    !model[box.getX()][box.getY() - 1].hasFlag()) {
                check(model[box.getX()][box.getY() - 1], dim);
            }
        } if (box.getY() < (dim - 1)) {
            if (!(model[box.getX()][box.getY() + 1].isSearched()) &&
                    !model[box.getX()][box.getY() + 1].hasFlag()) {
                check(model[box.getX()][box.getY() + 1], dim);
            }
        } if (box.getX() > 0 && box.getY() > 0) {
            if (!(model[box.getX() - 1][box.getY() - 1]).isSearched() &&
                    !model[box.getX() - 1][box.getY() - 1].hasFlag()) {
                checkPerimeter(model[box.getX() - 1][box.getY() - 1], dim);
            }
        } if (box.getX() > 0 && box.getY() < (dim - 1)) {
            if (!(model[box.getX() - 1][box.getY() + 1]).isSearched() &&
                    !model[box.getX() - 1][box.getY() + 1].hasFlag()) {
                checkPerimeter(model[box.getX() - 1][box.getY() + 1], dim);
            }
        } if (box.getX() < (dim - 1) && box.getY() > 0) {
            if (!(model[box.getX() + 1][box.getY() - 1]).isSearched() &&
                    !model[box.getX() + 1][box.getY() - 1].hasFlag()) {
                checkPerimeter(model[box.getX() + 1][box.getY() - 1], dim);
            }
        } if (box.getX() < (dim - 1) && box.getY() < (dim - 1)) {
            if (!(model[box.getX() + 1][box.getY() + 1]).isSearched() &&
                    !model[box.getX() + 1][box.getY() + 1].hasFlag()) {
                checkPerimeter(model[box.getX() + 1][box.getY() + 1], dim);
            }
        }
    }

}
