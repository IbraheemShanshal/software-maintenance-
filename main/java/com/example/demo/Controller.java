package com.example.demo;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Group;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.ButtonType;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.paint.Color;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.util.Optional;


public class Controller {
    static final int WIDTH = 900;
    static final int HEIGHT = 900;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Group gameRoot = new Group();
    @FXML
    private Button start;
    @FXML
    private Button quit;

    GameScene game = new GameScene();

    public void switchToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage =(Stage)((Node)event.getSource()).getScene().getWindow() ;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToGameScene(ActionEvent event) throws IOException {
        Group endgameRoot = new Group();
        Scene endGameScene = new Scene(endgameRoot, WIDTH, HEIGHT, Color.rgb(250, 20, 100, 0.2));
        Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, Color.rgb(189, 177, 92));
        stage =(Stage)((Node)event.getSource()).getScene().getWindow() ;
        stage.setScene(gameScene);
        game.game(gameScene, gameRoot, stage, endGameScene, endgameRoot);
        stage.show();

    }
    public static void setMain(Main mainApp) {
        // this.mainApp = mainApp;

        // pass the main app to the drawerContentController:
        Controller.setMain(mainApp);
    }
    public void handle(ActionEvent event) { //CHANGED FROM MOUSE EVENT
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Dialog");
        alert.setHeaderText("Quit from this page");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
    }


}
