import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Created by Raihan on 20/06/2017.
 * TicTacToe Game using JavaFX
 */
public class TicTacToe extends Application {

    private boolean playable = true;
    private boolean turnX = true;

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

    private void checkState() {
        
    }

    private class Tile extends StackPane {

        private Text text = new Text();


        public Tile() {
            Rectangle border = new Rectangle(200, 200); //Gives a 3x3 square
            border.setFill(null);
            border.setStroke(Color.BLACK);
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            text.setFont(Font.font(72));

            setOnMouseClicked(event -> {
                if (!playable) {
                    return;
                }
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX) {
                        return;
                    }
                    drawX();
                    turnX = false;
                    checkState();
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX) {
                        return;
                    }
                    drawO();
                    turnX = true;
                    checkState();
                }
            });
        }

        private void drawX() { // For Crosses
            text.setText("X");
        }

        private void drawO() { // For Naughts
            text.setText("O");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
