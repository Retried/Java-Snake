package pl.pjwstk.Java_Snake;

import javafx.scene.paint.Color;

public class Switch {
    public static void main() {
        switch (Food.foodcolor) {
            case 0:
                Tick.cc = Color.RED;
                break;
            case 1:
                Tick.cc = Color.BLUE;
                break;
            case 2:
                Tick.cc = Color.GOLD;
                break;
            case 3:
                Tick.cc = Color.BLACK;
                break;
            case 4:
                Tick.cc = Color.PURPLE;
                break;
        }
        switch (Food.foodcolor2) {
            case 0:
                Tick.cc2 = Color.RED;
                break;
            case 1:
                Tick.cc2 = Color.BLUE;
                break;
            case 2:
                Tick.cc2 = Color.GOLD;
                break;
            case 3:
                Tick.cc2 = Color.BLACK;
                break;
            case 4:
                Tick.cc2 = Color.PURPLE;
                break;
        }
    }
}
