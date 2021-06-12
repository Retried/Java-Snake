package pl.pjwstk.Java_Snake;

public class Reset {
    public static void reset() {
        Food.newFood();
        Game.snake.get(0).x = 20;
        Game.snake.get(0).y = 10;
        Tick.bonus = 0;
        Tick.gameOver = false;

        Game.snake.clear();

        do{
            Game.snake.add(new Corner(Game.width / 2, Game.height / 2));
        }
        while(Game.snake.size()!=3);

        Game.speed = 6*Game.difficulty;
        Game.direction = Game.Dir.left;

    }
}
