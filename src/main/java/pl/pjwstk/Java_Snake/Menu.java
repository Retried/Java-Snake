package pl.pjwstk.Java_Snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Objects;


public class Menu extends Application {

    @Override
    public void start(Stage stage){
        stage.setScene(new Scene(createContent(stage)));
        stage.setTitle("SNAKE GAME");
        stage.setResizable(false);
        stage.show();
    }

    private Parent createContent(Stage stage){
        Pane root = new Pane();
        root.setPrefSize(1280, 720);

        Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("menu.jpg")).toExternalForm(),
        1280,720,
        false,true
        );
        Image bgImage2 = new Image(Objects.requireNonNull(getClass().getResource("blank.jpg")).toExternalForm(),
        1280,720,
        false,true
        );

        Game c = new Game();

        HBox difficulty = new HBox(10,
                new MenuItem("EASY", () -> {
                    Game.difficulty = 0.5F;
                    c.start(stage);
                } ),
                new MenuItem("NORMAL",() -> {
                    Game.difficulty = 1;
                    c.start(stage);
                }),
                new MenuItem("HARD", () -> {
                    Game.difficulty = 2;
                    c.start(stage);
                })
        );

        HBox box = new HBox(100,
                new MenuItem("PLAY", () -> root.getChildren().addAll(
                        new ImageView(bgImage2),
                        difficulty
                )),
                new MenuItem("EXIT", Platform::exit)
        );

        box.setTranslateX(325);
        box.setTranslateY(600);
        difficulty.setTranslateX(250);
        difficulty.setTranslateY(300);

        root.getChildren().addAll(
                new ImageView(bgImage),
                box
        );

        return root;
    }
}
