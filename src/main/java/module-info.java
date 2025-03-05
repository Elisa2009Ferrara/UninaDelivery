module uninaDelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.uninadelivery to javafx.fxml;
    opens com.uninadelivery.controller to javafx.fxml;

    exports com.uninadelivery;
    exports com.uninadelivery.controller;
}