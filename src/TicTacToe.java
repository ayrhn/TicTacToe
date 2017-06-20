import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

/**
 * Created by Raihan on 20/06/2017.
 */
public class TicTacToe extends Application {

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(600, 600);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);
                root.getChildren().add(tile);
            }
        }

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private class Tile extends StackPane {
        public Tile() {
            Rectangle border = new Rectangle(200, 200); //Gives a 3x3 square
            border.setFill(null);
            border.setStroke(Color.BLACK);
            setAlignment(Pos.CENTER);
            getChildren().addAll(border);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
