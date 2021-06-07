package pl.pjwstk.Java_Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import javafx.scene.text.Font;

public class Game extends Application {

    static Random rand = new Random();
    static float difficulty;
    static float speed;
    static int foodcolor = 0;
    static int foodcolor2 = 0;
    static int width = 40;
    static int height = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int foodX2 = 0;
    static int foodY2 = 0;
    static int cornersize = 30;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;

    public enum Dir {
        left, right, up, down
    }

    public void start(Stage stage) {
        try {
            speed = 5*difficulty;
            Food();

            VBox vbox = new VBox();
            HBox root2 = new HBox(vbox);
            root2.setAlignment(Pos.CENTER);
            Canvas c = new Canvas(width * cornersize, height * cornersize);
            Canvas x = new Canvas(width * cornersize, 50);
            GraphicsContext gc = c.getGraphicsContext2D();
            GraphicsContext text = x.getGraphicsContext2D();
            vbox.getChildren().addAll(x,c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc,text);
                        return;
                    }

                    if (now - lastTick > 1000000000 / (speed/1.5F)) {
                        lastTick = now;
                        tick(gc,text);
                    }
                }

            }.start();

            Scene scene2 = new Scene(root2, 1280, 720);
            scene2.setFill(Color.LIGHTGRAY);

            // control
            scene2.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
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
                    snake.get(0).x = 20;
                    snake.get(0).y = 10;
                    gameOver = false;
                    if(snake.size() > 3){
                        do{
                            snake.remove(snake.size()-1);
                            speed-=difficulty;
                        }
                        while(speed>5*difficulty+difficulty);
                    }
                    direction = Dir.left;
                }

            });

            // add start snake parts
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

    // tick
    public static void tick(GraphicsContext gc, GraphicsContext x) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 475, 300);
            gc.fillText("Press R to RESET", 425, 350);
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
        x.setFill(Color.LIGHTGRAY);
        x.fillRect(0, 0, width * cornersize, height * cornersize);

        // score
        x.setFill(Color.BLACK);
        x.setFont(new Font("", 30));
        x.fillText("Score: " + 10*Math.round(speed - (6*difficulty)), 0, 30);
        x.fillText(10*Math.round(speed)+" :Speed", 1084-((int)(Math.log10(speed)+1)*16), 30);

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
            /*if(cc == Color.PURPLE){
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
            }*/
            snake.add(new Corner(-1, -1));
            Food();
        }
        if (foodX2 == snake.get(0).x && foodY2 == snake.get(0).y) {
            /*if(cc2 == Color.PURPLE){
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
            }*/
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
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize, cornersize );
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 1, cornersize - 1);

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
}
