package com.example.bank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class BankTellerController {

    private AccountDatabase bankDatabase;

    @FXML
    private Button printAllAccounts;

    @FXML
    private Button printAllAccountsByType;

    @FXML
    private Button calculateInterestAndFees;

    @FXML
    private Button applyInterestsAndFees;

    /*protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/

    @FXML
    protected void onPrintAllAccountsButtonClick(ActionEvent event) {
        bankDatabase.print();
    }

    @FXML
    protected void onPrintAllAccountsByTypeButtonClick(ActionEvent event) {

    }

    @FXML
    protected void onCalculateInterestsAndFeesButtonClick(ActionEvent event) {

    }

    @FXML
    protected void onApplyInterestsAndFeesButtonClick(ActionEvent event) {

    }
}