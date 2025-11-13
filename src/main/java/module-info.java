module com.projetoa3.projetoa3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;

    opens com.projetoa3.projetoa3 to javafx.fxml;
    exports com.projetoa3.projetoa3;
}