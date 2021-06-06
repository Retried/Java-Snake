module pl.pjwstk.Java_Snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.pjwstk.Java_Snake to javafx.fxml;
    exports pl.pjwstk.Java_Snake;
}