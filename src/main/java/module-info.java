module com.example.proj3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proj3 to javafx.fxml;
    exports com.example.proj3;
}