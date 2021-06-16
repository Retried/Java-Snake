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

    public static void tick(GraphicsContext field, GraphicsContext text) {
        if (win) {
            field.setFill(Color.RED);
            field.setFont(new Font("", 50));
            field.fillText("You won!", 475, 300);
            field.fillText("Press R to RESET", 425, 350);
            return;
        }
        else if (gameOver) {
            field.setFill(Color.RED);
            field.setFont(new Font("", 50));
            field.fillText("GAME OVER", 475, 300);
            field.fillText("Press R to RESET", 425, 350);
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

        field.setFill(Color.web("#9ac503"));
        field.fillRect(0, 0, Game.width * Game.blocksize, Game.height * Game.blocksize);
        text.setFill(Color.LIGHTGRAY);
        text.fillRect(0, 0, Game.width * Game.blocksize, Game.height * Game.blocksize);

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

        field.setFill(Color.BLACK);
        field.fillOval(Food.foodX * Game.blocksize, Food.foodY * Game.blocksize, Game.blocksize, Game.blocksize);
        field.setFill(cc);
        field.fillOval((Food.foodX * Game.blocksize)+1, (Food.foodY * Game.blocksize)+1, Game.blocksize-2, Game.blocksize-2);

        field.setFill(Color.BLACK);
        field.fillOval(Food.foodX2 * Game.blocksize, Food.foodY2 * Game.blocksize, Game.blocksize, Game.blocksize);
        field.setFill(cc2);
        field.fillOval((Food.foodX2 * Game.blocksize)+1, (Food.foodY2 * Game.blocksize)+1, Game.blocksize-2, Game.blocksize-2);

        for (int i = 0; i < Game.snake.size(); i++) {
            field.setFill(Color.BLACK);
            if (i==0){
                field.fillRoundRect(Game.snake.get(i).x * Game.blocksize, Game.snake.get(i).y * Game.blocksize, Game.blocksize, Game.blocksize,10,10);
                field.setFill(Color.DARKGREEN);
                field.fillRoundRect(Game.snake.get(i).x * Game.blocksize, Game.snake.get(i).y * Game.blocksize, Game.blocksize - 1, Game.blocksize - 1,10,10);
            }
            else {
                field.fillRect(Game.snake.get(i).x * Game.blocksize, Game.snake.get(i).y * Game.blocksize, Game.blocksize, Game.blocksize);
                field.setFill(Color.GREEN);
                field.fillRect(Game.snake.get(i).x * Game.blocksize, Game.snake.get(i).y * Game.blocksize, Game.blocksize - 1, Game.blocksize - 1);
            }
        }
    }
}
