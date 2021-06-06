package pl.pjwstk.Java_Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main extends Application {
    // variable
    static Random rand = new Random();
    static float difficulty = 1;
    static float speed = 5*difficulty;
    static int foodcolor = 0;
    static int foodcolor2 = 0;
    static int width = 20;
    static int height = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int foodX2 = 0;
    static int foodY2 = 0;
    static int cornersize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;

    public enum Dir {
        left, right, up, down
    }

    public void start(Stage primaryStage) {
        try {
            Food();

            VBox root = new VBox();
            Canvas c = new Canvas(width * cornersize, height * cornersize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / (speed)) {
                        lastTick = now;
                        tick(gc);
                    }
                }

            }.start();

            Scene scene = new Scene(root, width * cornersize, height * cornersize);

            // control
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP && direction != Dir.down) {
                    direction = Dir.up;
                }
                if (key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT && direction != Dir.right) {
                    direction = Dir.left;
                }
                if (key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN && direction != Dir.up) {
                    direction = Dir.down;
                }
                if (key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT && direction != Dir.left) {
                    direction = Dir.right;
                }
                if (key.getCode() == KeyCode.R) {
                    newFood();
                    snake.get(0).x = 10;
                    snake.get(0).y = 10;
                    gameOver = false;
                    if(snake.size() > 3){
                        //for (int i = 0; i<snake.size(); i++){
                        //    snake.remove(snake.size()-1);
                        //}
                        do{
                            snake.remove(snake.size()-1);
                            speed-=difficulty;
                        }
                        while(speed>5*difficulty+difficulty);
                    }
                    direction = Dir.left;
                    primaryStage.close();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }

            });

            // add start snake parts
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // tick
    public static void tick(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 125, 250);
            gc.fillText("Press R to RESET", 75, 300);
            return;
        }

        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 0) {
                    gameOver = true;
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y > height) {
                    gameOver = true;
                }
                break;
            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 0) {
                    gameOver = true;
                }
                break;
            case right:
                snake.get(0).x++;
                if (snake.get(0).x > width) {
                    gameOver = true;
                }
                break;

        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                break;
            }
        }

        // fill
        // background
        gc.setFill(Color.web("#9ac503"));
        gc.fillRect(0, 0, width * cornersize, height * cornersize);

        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + ((speed - (6*difficulty))), 10, 30);
        gc.fillText((speed)+" :Speed", 350, 30);

        // random foodcolor
        Color cc = Color.WHITE;
        Color cc2 = Color.WHITE;

        switch (foodcolor) {
            case 0:
                cc = Color.PURPLE;
                break;
            case 1:
                cc = Color.LIGHTBLUE;
                break;
            case 2:
                cc = Color.YELLOW;
                break;
            case 3:
                cc = Color.PINK;
                break;
            case 4:
                cc = Color.ORANGE;
                break;
        }
        switch (foodcolor2) {
            case 0:
                cc2 = Color.PURPLE;
                break;
            case 1:
                cc2 = Color.LIGHTBLUE;
                break;
            case 2:
                cc2 = Color.YELLOW;
                break;
            case 3:
                cc2 = Color.PINK;
                break;
            case 4:
                cc2 = Color.ORANGE;
                break;
        }

        // eat food
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            if(cc == Color.PURPLE){
                //speed-=5;
            }
            else if(cc == Color.LIGHTBLUE){
                //speed-=5;
            }
            else if(cc == Color.YELLOW){
                //speed-=5;
            }
            else if(cc == Color.PINK){
                //speed-=5;
            }
            if(cc == Color.ORANGE){
                //speed-=5;
            }
            snake.add(new Corner(-1, -1));
            Food();
        }
        if (foodX2 == snake.get(0).x && foodY2 == snake.get(0).y) {
            if(cc2 == Color.PURPLE){
                //speed-=5;
            }
            else if(cc2 == Color.LIGHTBLUE){
                //speed-=5;
            }
            else if(cc2 == Color.YELLOW){
                //speed-=5;
            }
            else if(cc2 == Color.PINK){
                //speed-=5;
            }
            if(cc2 == Color.ORANGE){
                //speed-=5;
            }
            snake.add(new Corner(-1, -1));
            Food();
        }

        gc.setFill(cc);
        gc.fillOval(foodX * cornersize, foodY * cornersize, cornersize, cornersize);
        gc.setFill(cc2);
        gc.fillOval(foodX2 * cornersize, foodY2 * cornersize, cornersize, cornersize);

        // snake
        for (Corner c : snake) {
            gc.setFill(Color.BLACK);
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 1, cornersize - 1);
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 2, cornersize - 2);

        }

    }

    // food
    public static void newFood(){
        do{
            foodcolor = rand.nextInt(5);
            foodcolor2 = rand.nextInt(5);
        }
        while(foodcolor == foodcolor2);

        foodX = rand.nextInt(width);
        foodY = rand.nextInt(height);
        foodX2 = rand.nextInt(width);
        foodY2 = rand.nextInt(height);
    }
    public static void Food() {
        start: while (true) {

            newFood();

            for (Corner c : snake) {
                if (c.x == foodX && c.y == foodY || c.x == foodX2 && c.y == foodY2) {
                    continue start;
                }
            }

            speed+=1*difficulty;
            break;

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
