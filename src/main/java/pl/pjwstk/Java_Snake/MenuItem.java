package pl.pjwstk.Java_Snake;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuItem extends StackPane {
    MenuItem(String name, Runnable action) {

        Rectangle bg = new Rectangle(250,50);

        Rectangle line = new Rectangle(5,50);
        line.widthProperty().bind(
                Bindings.when(hoverProperty())
                        .then(8).otherwise(5)
        );
        line.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.DARKGREEN).otherwise(Color.GRAY)
        );

        Text text = new Text(name);
        text.setFont(Font.font(22.0));
        text.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.WHITE).otherwise(Color.GRAY)
        );

        setOnMouseClicked(e -> action.run());

        setOnMousePressed(e -> bg.setFill(Color.DARKGRAY));

        setOnMouseReleased(e -> bg.setFill(Color.BLACK));

        setAlignment(Pos.CENTER_LEFT);

        HBox box = new HBox(15, line, text);
        box.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(bg, box);
    }
}
