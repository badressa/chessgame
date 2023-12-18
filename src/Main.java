import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Game game = new Game();

        GridPane gridPane = game.init();

        //get game width height
        Scene scene = new Scene(gridPane ,600, 600);
        primaryStage.setTitle(game.title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}









