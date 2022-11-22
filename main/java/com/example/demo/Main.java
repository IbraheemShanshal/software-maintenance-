package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

import java.util.Optional;
import java.util.Scanner;

public class Main extends Application {
    static final int WIDTH = 700;
    static final int HEIGHT = 700;
    private Group gameRoot = new Group();

    private Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, Color.rgb(189, 177, 92));
    private static Scanner input= new Scanner(System.in);

    /***
     * a setter methhod for the scene
     * @param gameScene to specify that the scene is this class scene
     */
    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    /***
     * a setter method for the group gameRoot
     * @param gameRoot to specify that the group is this class group
     */
    public void setGameRoot(Group gameRoot) {
        this.gameRoot = gameRoot;
    }

    /***
     *this method intiat the primary stage, it uses 5 scenes with thier roots: menuScene, accountScene, endGameScene, RankScene, GameScene
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group endgameRoot = new Group();
        Scene endGameScene = new Scene(endgameRoot, WIDTH, HEIGHT, Color.rgb(250, 20, 100, 0.2));
        //Group rankRoot = new Group();
        //Scene rankScene = new Scene(rankRoot, WIDTH, HEIGHT, Color.rgb(250, 50, 120, 0.3));
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(120, 100, 100), CornerRadii.EMPTY, Insets.EMPTY); //empty means no corner
        Background background = new Background(background_fill);
        
        Group gameRoot = new Group();
        setGameRoot(gameRoot);

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);

        Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, Color.rgb(189, 177, 92));
        setGameScene(gameScene);
        primaryStage.setScene(scene);
        GameScene game = new GameScene();
        game.game(gameScene, gameRoot, primaryStage, endGameScene, endgameRoot);

        primaryStage.show();
    }

    /***
     * this is the main method of this program
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
