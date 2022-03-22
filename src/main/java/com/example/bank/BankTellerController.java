package com.example.bank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class BankTellerController {

    AccountDatabase bankDatabase = new AccountDatabase();

    @FXML
    private Button open, close, deposit, withdraw, printAllAccounts, printAllAccountsByType, calculateInterestAndFees, applyInterestsAndFees;

    @FXML
    private TextField ocFirstName, ocLastName, dwFirstName, dwLastName, amount;

    @FXML
    private DatePicker ocDOB, wdDOB;

    @FXML
    private RadioButton ocChecking, ocCollegeChecking, ocSavings, ocMoneyMarket, dwChecking, dwCollegeChecking, dwSavings, dwMoneyMarket, newBrunswick, newark, camden;

    @FXML
    private CheckBox loyal;

    @FXML
    private TextArea messageArea;

    /*protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/

    @FXML
    protected void onPrintAllAccountsButtonClick(ActionEvent event) {

        bankDatabase.print();
    }

    @FXML
    protected void onPrintAllAccountsByTypeButtonClick(ActionEvent event) {
        bankDatabase.printByAccountType(); }

    @FXML
    protected void onCalculateInterestsAndFeesButtonClick(ActionEvent event) {
        bankDatabase.updateBalance(); }

    @FXML
    protected void onApplyInterestsAndFeesButtonClick(ActionEvent event) {
        bankDatabase.printFeeAndInterest(); }

    @FXML
    protected void onOpenButtonClick(ActionEvent event) {

    }

    @FXML
    protected void onCloseButtonClick(ActionEvent event) {

    }

    @FXML
    protected void onDepositButtonClick(ActionEvent event) {

    }

    @FXML
    protected void onWithdrawButtonClick(ActionEvent event) {

    }
}