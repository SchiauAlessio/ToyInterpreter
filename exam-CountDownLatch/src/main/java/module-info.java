module com.exam.exam{
    requires javafx.controls;
    requires javafx.fxml;

    //requires org.kordamp.bootstrapfx.core;

    exports com.toy.interpreter.View;
    opens com.toy.interpreter.View to javafx.fxml;
    exports com.toy.interpreter;
    opens com.toy.interpreter to javafx.fxml;
}