package pl.pjwstk.Java_Snake;

import java.util.Random;

public class Food {

    static Random rand = new Random();
    static int foodcolor;
    static int foodcolor2;
    static int foodX = 0;
    static int foodY = 0;
    static int foodX2 = 0;
    static int foodY2 = 0;

    public static void newFood(){

        do{
            foodcolor = rand.nextInt(5);
            foodcolor2 = rand.nextInt(5);
        }
        while(foodcolor == foodcolor2);

        foodX = rand.nextInt(Game.width);
        foodY = rand.nextInt(Game.height);
        foodX2 = rand.nextInt(Game.width);
        foodY2 = rand.nextInt(Game.height);
    }
    public static void genFood() {
        newFood();
        start: while (true) {

            for (Corner c : Game.snake) {
                if (c.x == foodX && c.y == foodY || c.x == foodX2 && c.y == foodY2) {
                    continue start;
                }
            }

            Game.speed+=1*Game.difficulty;
            break;

        }
    }
}
