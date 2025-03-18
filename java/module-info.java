module aoim.zad4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens aoim.zad4 to javafx.fxml, org.hibernate.orm.core;
    exports aoim.zad4;
}