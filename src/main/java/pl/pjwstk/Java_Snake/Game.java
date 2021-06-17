package pl.pjwstk.Java_Snake;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Game extends Application {

    static float difficulty;
    static float speed;
    static int width = 40;
    static int height = 20;
    static int blocksize = 30;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static int size = 0;

    public enum Dir {
        left, right, up, down
    }

    public void start(Stage stage) {
        try {
            speed = 5*difficulty;
            Food.genFood();

            VBox vbox = new VBox();
            HBox root2 = new HBox(vbox);
            root2.setAlignment(Pos.CENTER);
            Canvas x = new Canvas(width * blocksize, height * blocksize);
            Canvas y = new Canvas(width * blocksize, 50);
            GraphicsContext field = x.getGraphicsContext2D();
            GraphicsContext text = y.getGraphicsContext2D();
            vbox.getChildren().addAll(y,x);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        Tick.tick(field,text);
                        return;
                    }

                    if (now - lastTick > 1000000000 / (5*difficulty)+(speed*0.1F)) {
                        lastTick = now;
                        Tick.tick(field,text);
                    }
                }

            }.start();

            Scene scene2 = new Scene(root2, 1280, 720);
            scene2.setFill(Color.LIGHTGRAY);

            scene2.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if ((key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) && direction != Dir.down) {
                    direction = Dir.up;
                }
                if ((key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT) && direction != Dir.right) {
                    direction = Dir.left;
                }
                if ((key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN) && direction != Dir.up) {
                    direction = Dir.down;
                }
                if ((key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) && direction != Dir.left) {
                    direction = Dir.right;
                }
                if (key.getCode() == KeyCode.R) {
                    Reset.reset();
                }

            });

            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            stage.setScene(scene2);
            stage.setTitle("SNAKE GAME");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
