package pl.pjwstk.Java_Snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Tick {

    public static Color cc;
    public static Color cc2;
    static boolean gameOver = false;
    static boolean win = false;
    static int bonus = 0;

    public static void tick(GraphicsContext gc, GraphicsContext text) {
        if (win) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("You won!", 475, 300);
            gc.fillText("Press R to RESET", 425, 350);
            return;
        }
        else if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 475, 300);
            gc.fillText("Press R to RESET", 425, 350);
            return;
        }

        for (int i = Game.snake.size() - 1; i >= 1; i--) {
            Game.snake.get(i).x = Game.snake.get(i - 1).x;
            Game.snake.get(i).y = Game.snake.get(i - 1).y;
        }

        switch (Game.direction) {
            case up:
                Game.snake.get(0).y--;
                if (Game.snake.get(0).y < 0) {
                    gameOver = true;
                }
                break;
            case down:
                Game.snake.get(0).y++;
                if (Game.snake.get(0).y > Game.height) {
                    gameOver = true;
                }
                break;
            case left:
                Game.snake.get(0).x--;
                if (Game.snake.get(0).x < 0) {
                    gameOver = true;
                }
                break;
            case right:
                Game.snake.get(0).x++;
                if (Game.snake.get(0).x > Game.width) {
                    gameOver = true;
                }
                break;

        }

        for (int i = 1; i < Game.snake.size(); i++) {
            if (Game.snake.get(0).x == Game.snake.get(i).x && Game.snake.get(0).y == Game.snake.get(i).y) {
                gameOver = true;
                break;
            }
        }

        gc.setFill(Color.web("#9ac503"));
        gc.fillRect(0, 0, Game.width * Game.cornersize, Game.height * Game.cornersize);
        text.setFill(Color.LIGHTGRAY);
        text.fillRect(0, 0, Game.width * Game.cornersize, Game.height * Game.cornersize);

        text.setFill(Color.BLACK);
        text.setFont(new Font("", 30));
        text.fillText("Score: " + 10*Math.round(Game.speed - (6*Game.difficulty) + (bonus*Game.difficulty)), 0, 30);
        text.fillText(10*Math.round(Game.speed)+" :Speed", 1084-((int)(Math.log10(Game.speed)+1)*16), 30);

        Switch.main();

        if (Food.foodX == Game.snake.get(0).x && Food.foodY == Game.snake.get(0).y || Food.foodX2 == Game.snake.get(0).x && Food.foodY2 == Game.snake.get(0).y){
            Color x;
            if (Food.foodX == Game.snake.get(0).x && Food.foodY == Game.snake.get(0).y) {
                x = cc;
            }
            else {
                x = cc2;
            }
            if(x == Color.RED){
                if (Game.snake.size() > 1) {
                    Game.snake.remove(Game.snake.size()-1);
                    Game.size -= 1;
                }
                else{
                    gameOver = true;
                }
            }
            else if(x == Color.BLUE){
                Game.speed+=1*Game.difficulty;
                Game.snake.add(new Corner(-1, -1));
            }
            else if(x == Color.GOLD){
                bonus++;
                Game.snake.add(new Corner(-1, -1));
            }
            else if(x == Color.BLACK){
                Game.speed-=2*Game.difficulty;
                bonus+=2;
                Game.snake.add(new Corner(-1, -1));
            }
            else if(x == Color.PURPLE){
                Game.snake.add(new Corner(-1, -1));
                Game.snake.add(new Corner(-1, -1));
                Game.size += 1;
            }
            Game.size += 1;
            if(Game.snake.size() == 800)
                win = true;
            Food.genFood();
        }

        Switch.main();

        gc.setFill(Color.BLACK);
        gc.fillOval(Food.foodX * Game.cornersize, Food.foodY * Game.cornersize, Game.cornersize, Game.cornersize);
        gc.setFill(cc);
        gc.fillOval((Food.foodX * Game.cornersize)+1, (Food.foodY * Game.cornersize)+1, Game.cornersize-2, Game.cornersize-2);

        gc.setFill(Color.BLACK);
        gc.fillOval(Food.foodX2 * Game.cornersize, Food.foodY2 * Game.cornersize, Game.cornersize, Game.cornersize);
        gc.setFill(cc2);
        gc.fillOval((Food.foodX2 * Game.cornersize)+1, (Food.foodY2 * Game.cornersize)+1, Game.cornersize-2, Game.cornersize-2);

        for (int i = 0; i < Game.snake.size(); i++) {
            gc.setFill(Color.BLACK);
            if (i==0){
                gc.fillRoundRect(Game.snake.get(i).x * Game.cornersize, Game.snake.get(i).y * Game.cornersize, Game.cornersize, Game.cornersize,10,10);
                gc.setFill(Color.DARKGREEN);
                gc.fillRoundRect(Game.snake.get(i).x * Game.cornersize, Game.snake.get(i).y * Game.cornersize, Game.cornersize - 1, Game.cornersize - 1,10,10);
            }
            else {
                gc.fillRect(Game.snake.get(i).x * Game.cornersize, Game.snake.get(i).y * Game.cornersize, Game.cornersize, Game.cornersize);
                gc.setFill(Color.GREEN);
                gc.fillRect(Game.snake.get(i).x * Game.cornersize, Game.snake.get(i).y * Game.cornersize, Game.cornersize - 1, Game.cornersize - 1);
            }
        }
    }
}
