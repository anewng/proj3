package com.example.bank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 The BankTellerMain class runs the project by implementing the start method.
 It acts as a driver for the program.
 @author Annie Wang, Jasmine Flanders
 */
public class BankTellerMain extends Application {

    private static final int WIDTH_AND_HEIGHT = 600;

    /**
     The start method loads the fxml file for the GUI and creates the stage to be displayed.
     @param stage the stage being launched.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), WIDTH_AND_HEIGHT, WIDTH_AND_HEIGHT);
        stage.setTitle("Bank");
        stage.setScene(scene);
        stage.show();
    }

    /**
     Main method for BankTellerMain.
     Launches the program.
     */
    public static void main(String[] args) {
        launch();
    }
}