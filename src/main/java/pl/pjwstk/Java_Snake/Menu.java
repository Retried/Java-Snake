package pl.pjwstk.Java_Snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Objects;
import pl.pjwstk.Java_Snake.Game;


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

        HBox box2 = new HBox(10,
                new MenuItem("EASY", () -> {
                    Game.difficulty = 0.5F;
                    stage.close();
                    c.start(stage);
                } ),
                new MenuItem("NORMAL",() -> {
                    Game.difficulty = 1;
                    stage.close();
                    c.start(stage);
                }),
                new MenuItem("HARD", () -> {
                    Game.difficulty = 2;
                    stage.close();
                    c.start(stage);
                })
        );

        HBox box = new HBox(10,
                new MenuItem("PLAY", () -> {
                    root.getChildren().addAll(
                            new ImageView(bgImage2),
                            box2
                    );
                } ),
                new MenuItem("FRUIT INFO",() -> {}),
                new MenuItem("EXIT", Platform::exit)
        );

        box.setBackground(new Background(
                new BackgroundFill(Color.web("black",0.6),null, null)
        ));
        box.setTranslateX(250);
        box.setTranslateY(600);
        box2.setTranslateX(250);
        box2.setTranslateY(300);

        root.getChildren().addAll(
                new ImageView(bgImage),
                box
        );

        return root;
    }
}
