package com.example.demo;

public class movementControl {

    //private  int numberOfCells = game.getNumberOfCells();
    private Cell[][] cells = GameScene.getCells();
    private static int numberOfCells = GameScene.getNumberOfCells();

    movementControl(){

    }

    public void moveLeft() {
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = 1; j < numberOfCells; j++) {
                moveHorizontally(i, j, passDestination(i, j, 'l'), -1);
            }
            for (int j = 0; j < numberOfCells; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    public void moveRight() {
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = numberOfCells - 1; j >= 0; j--) {
                moveHorizontally(i, j, passDestination(i, j, 'r'), 1);
            }
            for (int j = 0; j < numberOfCells; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    public void moveUp() {
        for (int j = 0; j < numberOfCells; j++) {
            for (int i = 1; i < numberOfCells; i++) {
                moveVertically(i, j, passDestination(i, j, 'u'), -1);
            }
            for (int i = 0; i < numberOfCells; i++) {
                cells[i][j].setModify(false);
            }
        }

    }

    public void moveDown() {
        for (int j = 0; j < numberOfCells; j++) {
            for (int i = numberOfCells - 1; i >= 0; i--) {
                moveVertically(i, j, passDestination(i, j, 'd'), 1);
            }
            for (int i = 0; i < numberOfCells; i++) {
                cells[i][j].setModify(false);
            }
        }

    }
    private void moveHorizontally(int i, int j, int des, int sign) {
        if (isValidDesH(i, j, des, sign)) {
            cells[i][j].adder(cells[i][des + sign]);
            cells[i][des].setModify(true);
        } else if (des != j) {
            cells[i][j].changeCell(cells[i][des]);
        }
    }

    private boolean isValidDesV(int i, int j, int des, int sign) {
        if (des + sign < numberOfCells && des + sign >= 0)
            if (cells[des + sign][j].getNumber() == cells[i][j].getNumber() && !cells[des + sign][j].getModify()
                    && cells[des + sign][j].getNumber() != 0) {
                return true;
            }
        return false;
    }

    private void moveVertically(int i, int j, int des, int sign) {
        if (isValidDesV(i, j, des, sign)) {
            cells[i][j].adder(cells[des + sign][j]);
            cells[des][j].setModify(true);
        } else if (des != i) {
            cells[i][j].changeCell(cells[des][j]);
        }
    }
    private int passDestination(int i, int j, char direct) {
        int coordinate = j;
        if (direct == 'l') {
            for (int k = j - 1; k >= 0; k--) {
                if (cells[i][k].getNumber() != 0) {
                    coordinate = k + 1;
                    break;
                } else if (k == 0) {
                    coordinate = 0;
                }
            }
            return coordinate;
        }
        coordinate = j;
        if (direct == 'r') {
            for (int k = j + 1; k <= numberOfCells - 1; k++) {
                if (cells[i][k].getNumber() != 0) {
                    coordinate = k - 1;
                    break;
                } else if (k == numberOfCells - 1) {
                    coordinate = numberOfCells - 1;
                }
            }
            return coordinate;
        }
        coordinate = i;
        if (direct == 'd') {
            for (int k = i + 1; k <= numberOfCells - 1; k++) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k - 1;
                    break;

                } else if (k == numberOfCells - 1) {
                    coordinate = numberOfCells - 1;
                }
            }
            return coordinate;
        }
        coordinate = i;
        if (direct == 'u') {
            for (int k = i - 1; k >= 0; k--) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k + 1;
                    break;
                } else if (k == 0) {
                    coordinate = 0;
                }
            }
            return coordinate;
        }
        return -1;
    }
    private boolean isValidDesH(int i, int j, int des, int sign) {
        if (des + sign < numberOfCells && des + sign >= 0) {
            if (cells[i][des + sign].getNumber() == cells[i][j].getNumber() && !cells[i][des + sign].getModify()
                    && cells[i][des + sign].getNumber() != 0) {
                return true;
            }
        }
        return false;
    }


}
