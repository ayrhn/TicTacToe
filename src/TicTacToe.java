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
    private Parent createContent() {
        Pane root = new Pane();
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
                break;
            }
        }
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
