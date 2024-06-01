module org.apkmem.aplikasimembership {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens org.apkmem.aplikasimembership to javafx.fxml;
    exports org.apkmem.aplikasimembership;
    exports org.apkmem.aplikasimembership.util;
    opens org.apkmem.aplikasimembership.util to javafx.fxml;
    exports org.apkmem.aplikasimembership.data;
    opens org.apkmem.aplikasimembership.data to javafx.fxml;
}