module lms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens lms.controllers to javafx.fxml;
    opens lms.models to javafx.base;
    opens lms to javafx.fxml;
    exports lms;
}
