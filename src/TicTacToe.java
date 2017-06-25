import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;


/**
 * Created by Raihan on 20/06/2017.
 * TicTacToe Game using JavaFX
 */
public class TicTacToe extends Application {

    private boolean playable = true;
    private boolean turnX = true;
    private ArrayList<Combos> combos = new ArrayList<>();
    private Tile[][] board = new Tile[3][3];
    private Pane root = new Pane();

    private Parent createContent() {

        root.setPrefSize(600, 600);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);
                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }

        for (int y = 0; y < 3; y++) { //horizontal
            combos.add(new Combos(board[0][y], board[1][y], board[2][y]));
        }

        for (int x = 0; x < 3; x++) { //vertical
            combos.add(new Combos(board[x][0], board[x][1], board[x][2]));
        }

        for (int z = 0; z < 3; z++) { // diagonal
            combos.add(new Combos(board[0][0], board[1][1], board[2][2]));
            combos.add(new Combos(board[2][0], board[1][1], board[0][2]));
        }
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void checkState() { //Checks if someone has won or not
        for (Combos combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                endGame(combo);
                break;
            }
        }
    }

    private void endGame(Combos combo) { //Creates a cross
        //TODO: Reset game
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCentreX());
        line.setStartY(combo.tiles[0].getCentreY());
        line.setEndX(combo.tiles[0].getCentreY());
        line.setEndY(combo.tiles[0].getCentreX());

        root.getChildren().add(line);

        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5),
                new KeyValue(line.endXProperty(), combo.tiles[2].getCentreX()),
                new KeyValue(line.endYProperty(), combo.tiles[2].getCentreY())));
        tl.play();
        
    }

    private class Combos {
        private Tile[] tiles;
        public Combos(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty()) {
                return false;
            }
            return tiles[0].getValue().equals(tiles[1].getValue()) && tiles[0].getValue().equals(tiles[2].getValue());
        }
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

                    if (turnX) {
                        drawX();
                        turnX = false;
                        checkState();
                    }
                    else {
                        drawO();
                        turnX = true;
                        checkState();
                    }
                }
            });
        }

        public double getCentreX() {
            return getTranslateX() + 100;
        }

        public double getCentreY() {
            return getTranslateY() + 100;
        }

        public String getValue() {
            return text.getText();
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
