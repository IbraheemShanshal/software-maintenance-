package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

class GameScene {
    private static int HEIGHT = 700;
    private static int numberOfCells = 4;
    private final static int distanceBetweenCells = 10;
    private static double LENGTH = (HEIGHT - ((numberOfCells +1) * distanceBetweenCells)) / (double) numberOfCells;
    private TextMaker textMaker = TextMaker.getSingleInstance();
    private static Cell[][] cells = new Cell[numberOfCells][numberOfCells];
    private Group root;
    private long score = 0;

    movementControl movementControl= new movementControl();

    GameScene(){

    }
    /**
     *a setter method for number of cells in a row or column
     * @param number
     */
    /*static void setN(int number) {
        numberOfCells = number;
        LENGTH = (HEIGHT - ((numberOfCells + 1) * distanceBetweenCells)) / (double) numberOfCells;
    }*/

    /**
     *
     * @return
     */
    static double getLENGTH() {
        return LENGTH;
    }

    /**
     *
     * @param turn
     */
    private void randomFillNumber(int turn) {

        Cell[][] emptyCells = new Cell[numberOfCells][numberOfCells];
        int a = 0;
        int b = 0;
        int aForBound=0,bForBound=0;
        outer:
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = 0; j < numberOfCells; j++) {
                if (cells[i][j].getNumber() == 0) {
                    emptyCells[a][b] = cells[i][j];
                    if (b < numberOfCells-1) {
                        bForBound=b;
                        b++;

                    } else {
                        aForBound=a;
                        a++;
                        b = 0;
                        if(a==numberOfCells)
                            break outer;
                    }
                }
            }
        }



        Text text;
        Random random = new Random();
        boolean putTwo = true;
        if (random.nextInt() % 2 == 0)
            putTwo = false;
        int xCell, yCell;
            xCell = random.nextInt(aForBound+1);
            yCell = random.nextInt(bForBound+1);
        if (putTwo) {
            text = textMaker.madeText("2", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
            emptyCells[xCell][yCell].setTextClass(text);
            root.getChildren().add(text);
            emptyCells[xCell][yCell].setColorByNumber(2);
        } else {
            text = textMaker.madeText("4", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
            emptyCells[xCell][yCell].setTextClass(text);
            root.getChildren().add(text);
            emptyCells[xCell][yCell].setColorByNumber(4);
        }
    }

    private int  haveEmptyCell() {
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = 0; j < numberOfCells; j++) {
                if (cells[i][j].getNumber() == 0)
                    return 1;
                if(cells[i][j].getNumber() == 2048)
                    return 0;
            }
        }
        return -1;
    }

    private boolean haveSameNumberNearly(int i, int j) {
        if (i < numberOfCells - 1 && j < numberOfCells - 1) {
            if (cells[i + 1][j].getNumber() == cells[i][j].getNumber())
                return true;
            if (cells[i][j + 1].getNumber() == cells[i][j].getNumber())
                return true;
        }
        return false;
    }

    private boolean canNotMove() {
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = 0; j < numberOfCells; j++) {
                if (haveSameNumberNearly(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void sumCellNumbersToScore() {
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = 0; j < numberOfCells; j++) {
                score += cells[i][j].getNumber();
            }
        }
    }

    void game(Scene gameScene, Group root, Stage primaryStage, Scene endGameScene, Group endGameRoot) {
        this.root = root;
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = 0; j < numberOfCells; j++) {
                cells[i][j] = new Cell((j) * LENGTH + (j + 1) * distanceBetweenCells,
                        (i) * LENGTH + (i + 1) * distanceBetweenCells, LENGTH, root);
            }

        }

        Text text = new Text();
        root.getChildren().add(text);
        text.setText("SCORE :");
        text.setFont(Font.font(30));
        text.relocate(750, 100);
        Text scoreText = new Text();
        root.getChildren().addAll(scoreText);
        scoreText.relocate(750, 150);
        scoreText.setFont(Font.font(20));
        scoreText.setText("0");

        randomFillNumber(1);
        randomFillNumber(1);


        Controller controller = new Controller();

        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, key ->{
                Platform.runLater(() -> {
                    int haveEmptyCell;
                    if (key.getCode() == KeyCode.DOWN) {
                        movementControl.moveDown();
                    } else if (key.getCode() == KeyCode.UP) {
                        movementControl.moveUp();
                    } else if (key.getCode() == KeyCode.LEFT) {
                        movementControl.moveLeft();
                    } else if (key.getCode() == KeyCode.RIGHT) {
                        movementControl.moveRight();
                    } else if (key.getCode()==KeyCode.ESCAPE) {
                        try {
                            controller.switchToPauseMenu();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    /**show Hoor**/
                    else{
                        throw new RuntimeException("wrong key");
                    }

                    GameScene.this.sumCellNumbersToScore();
                    scoreText.setText(score + "");
                    haveEmptyCell = GameScene.this.haveEmptyCell();
                    if (haveEmptyCell == -1) {
                        if (GameScene.this.canNotMove()) {
                            primaryStage.setScene(endGameScene);

                            EndGame.getInstance().endGameShow(endGameScene, endGameRoot, primaryStage, score);
                            root.getChildren().clear();
                            score = 0;
                        }
                    } else if(haveEmptyCell == 1)
                        GameScene.this.randomFillNumber(2);
                });
            });
    }

    public static Cell[][] getCells() {
        return cells;
    }
    public static int getNumberOfCells() {
        return numberOfCells;
    }
}
