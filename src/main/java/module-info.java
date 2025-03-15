module uninaDelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.base;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;


    exports com.uninadelivery;
    exports com.uninadelivery.controller;
    opens com.uninadelivery to javafx.fxml;
    opens com.uninadelivery.controller to javafx.fxml;
}