package com.example.bank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 The BankTellerController class dictates the function of the GUI.
 @author Annie Wang, Jasmine Flanders
 */
public class BankTellerController {
    private static final int NEW_BRUNSWICK = 0;
    private static final int NEWARK = 1;
    private static final int CAMDEN = 2;
    private static final int UNLOYAL = 0;
    private static final int LOYAL = 1;
    private static final int CODES_NOT_APPLICABLE = -2;
    private static final int CODES_APPLICABLE_AND_ERROR = -1;
    private static final int ZERO_BALANCE = 0;
    private static final int ZERO_ACCOUNTS = 0;
    private static final int FILLER_CODE_VALUE = -10;
    private static final int MIN_MM_BALANCE = 2500;
    private static final int INDEX_OUT_OF_BOUNDS = -1;

    @FXML
    private RadioButton newBrunswick, newark, camden;

    @FXML
    private TextField ocFirstName, ocLastName, ocAmount, dwFirstName, dwLastName, dwAmount, ocDOB, dwDOB;

    @FXML
    private ToggleGroup acctType, acctTypeDW, campusType;

    @FXML
    private CheckBox loyal;

    @FXML
    private TextArea messageArea;

    AccountDatabase bankDatabase = new AccountDatabase();

    /**
     Opens an account in the database.
     @param event the method is executed when the user clicks the Open button on the GUI.
     */
    @FXML
    protected void onOpenButtonClick(ActionEvent event) {
        String first = ocFirstName.getText(), last = ocLastName.getText(), dob = ocDOB.getText();

        if(ocFirstName.getText().isBlank() || ocLastName.getText().isBlank() || ocDOB.getText().isBlank()
                || ocAmount.getText().isBlank()){
            messageArea.appendText("Missing data for opening an account.\n");
            return;
        }
        if(acctType.getSelectedToggle() == null){
            messageArea.appendText("Missing data for opening an account.\n");
            return;
        }

        RadioButton selectedRadioButton = (RadioButton) acctType.getSelectedToggle();
        String accountType = selectedRadioButton.getText();

        int codes;
        codes = setCodes(accountType);
        if(codes == CODES_APPLICABLE_AND_ERROR) return;

        double balanceDouble;
        try {
            balanceDouble = Double.parseDouble(ocAmount.getText());
        } catch (RuntimeException e){
            messageArea.appendText("Not a valid amount.\n");
            return;
        }

        Date birth = new Date(dob);
        Profile newProfile = new Profile(first, last, birth);
        Account newAccount = createNewAccount(newProfile, balanceDouble, codes, accountType);
        if (openReturnErrorStatements(newAccount, bankDatabase)){
            return;
        }
        openAccountLastStep(bankDatabase, newAccount);
    }

    /**
     Returns relevant error statements for an invalid open command.
     @param newAccount the account being referenced by the open command.
     @param bankDatabase the database containing the accounts.
     @return true if there is an error, else return false.
     */
    public boolean openReturnErrorStatements(Account newAccount, AccountDatabase bankDatabase) {
        if (!newAccount.holder.getDob().isValid() || newAccount.holder.getDob().isFutureDate() ) {
            messageArea.appendText("Date of birth invalid.\n");
        } else if (newAccount.balance <= ZERO_BALANCE) {
            messageArea.appendText("Initial deposit cannot be 0 or negative.\n");
        } else if (newAccount.getType().equals("Checking") && bankDatabase.findCCProfile(newAccount)) {
            messageArea.appendText(newAccount.holder.toString() + " same account(type) is in the database.\n");
        } else if (newAccount.getType().equals("College Checking") && bankDatabase.findCProfile(newAccount)) {
            messageArea.appendText(newAccount.holder.toString() + " same account(type) is in the database.\n");
        } else if (bankDatabase.isInDatabase(newAccount)) {
            messageArea.appendText(newAccount.holder.toString() + " same account(type) is in the database.\n");
        } else if (newAccount.getType().equals("Money Market") && newAccount.balance < MIN_MM_BALANCE) {
            messageArea.appendText("Minimum of $2500 to open a MoneyMarket account.\n");
        } else {
            return false;
        }
        return true;
    }

    /**
     Determines code usage based on account.
     @param accountType the type of account.
     @return CODES_NOT_APPLICABLE if account is not College Checking or Savings, returns CODES_APPLICABLE_AND_ERROR
     if account is College Checking or Savings, but missing necessary campus code/loyalty data, returns LOYAL if
     account is loyal Savings account and returns UNLOYAL if account is not loyal Savings account.
     */
    protected int setCodes(String accountType){
        if(accountType.equals("College Checking") && campusType.getSelectedToggle() == null){
            messageArea.appendText("Missing data for opening an account.\n");
            return CODES_APPLICABLE_AND_ERROR;
        }

        if(accountType.equals("College Checking")){
            RadioButton selectedRadioButton = (RadioButton) campusType.getSelectedToggle();
            String codeString = selectedRadioButton.getText();
            if(codeString.equals("New Brunswick")){
                return NEW_BRUNSWICK;
            }else if(codeString.equals("Newark")){
                return NEWARK;
            }else if(codeString.equals("Camden")){
                return CAMDEN;
            }
        }else if(accountType.equals("Savings")){
            if(loyal.isSelected()){
                return LOYAL;
            }
            return UNLOYAL;
        }
        return CODES_NOT_APPLICABLE;
    }

    /**
     Closes an account in the database.
     @param event the method is executed when the user clicks the Close button on the GUI.
     */
    @FXML
    protected void onCloseButtonClick(ActionEvent event) {
        String first = ocFirstName.getText(), last = ocLastName.getText(), dob = ocDOB.getText();

        if(ocFirstName.getText().isBlank() || ocLastName.getText().isBlank() || ocDOB.getText().isBlank()){
            messageArea.appendText("Missing data for closing an account.\n");
            return;
        }
        if(acctType.getSelectedToggle() == null){
            messageArea.appendText("Missing data for closing an account.\n");
            return;
        }

        RadioButton selectedRadioButton = (RadioButton) acctType.getSelectedToggle();
        String accountType = selectedRadioButton.getText();

        int codes = FILLER_CODE_VALUE;

        double balanceDouble = ZERO_BALANCE;

        Date birth = new Date(dob);
        Profile newProfile = new Profile(first, last, birth);
        Account newAccount = createNewAccount(newProfile, balanceDouble, codes, accountType);

        Account closeAcc = bankDatabase.findByProfileType(newAccount);
        if (closeAcc.closed) {
            messageArea.appendText("Account closed already.\n");
        } else {
            bankDatabase.close(closeAcc);
            messageArea.appendText("Account closed.\n");
        }
    }

    /**
     Deposits money into an account.
     @param event the method is executed when the user clicks the Deposit button on the GUI.
     */
    @FXML
    protected void onDepositButtonClick(ActionEvent event) {
        String first = dwFirstName.getText(), last = dwLastName.getText(), dob = dwDOB.getText();

        if(dwFirstName.getText().isBlank() || dwLastName.getText().isBlank() || dwDOB.getText().isBlank()
                || ocDOB.getText().isBlank()){
            messageArea.appendText("Missing account data.\n");
            return;
        }
        if(acctTypeDW.getSelectedToggle() == null){
            messageArea.appendText("Missing account type.\n");
            return;
        }

        RadioButton selectedRadioButton = (RadioButton) acctTypeDW.getSelectedToggle();
        String accountType = selectedRadioButton.getText();

        int codes = FILLER_CODE_VALUE;

        double balanceDouble;
        try {
            balanceDouble = Double.parseDouble(dwAmount.getText());
        } catch (RuntimeException e){
            messageArea.appendText("Not a valid amount.\n");
            return;
        }

        if (balanceDouble <= ZERO_BALANCE){
            messageArea.appendText("Deposit - amount cannot be 0 or negative.\n");
            return;
        }

        Date birth = new Date(dob);
        Profile newProfile = new Profile(first, last, birth);

        Account newAccount = createNewAccount(newProfile, balanceDouble, codes, accountType);
        depositBalanceLastStep(bankDatabase, newAccount);
    }

    /**
     Withdraws money from an account.
     @param event the method is executed when the user clicks the Withdraw button on the GUI.
     */
    @FXML
    protected void onWithdrawButtonClick(ActionEvent event) {
        String first = dwFirstName.getText(), last = dwLastName.getText(), dob = dwDOB.getText();

        if(dwFirstName.getText().isBlank() || dwLastName.getText().isBlank() || dwDOB.getText().isBlank()
                || dwAmount.getText().isBlank()){
            messageArea.appendText("Missing account data.\n");
            return;
        }
        if(acctTypeDW.getSelectedToggle() == null){
            messageArea.appendText("Missing account type.\n");
            return;
        }

        RadioButton selectedRadioButton = (RadioButton) acctTypeDW.getSelectedToggle();
        String accountType = selectedRadioButton.getText();

        int codes = FILLER_CODE_VALUE;

        double balanceDouble;
        try {
            balanceDouble = Double.parseDouble(dwAmount.getText());
        } catch (RuntimeException e){
            messageArea.appendText("Not a valid amount.\n");
            return;
        }

        if (balanceDouble <= ZERO_BALANCE){
            messageArea.appendText("Withdraw - amount cannot be 0 or negative.\n");
            return;
        }

        Date birth = new Date(dob);
        Profile newProfile = new Profile(first, last, birth);

        Account newAccount = createNewAccount(newProfile, balanceDouble, codes, accountType);
        withdrawBalanceLastStep(bankDatabase, newAccount);
    }


    /**
     Prints the accounts in the database in their current order.
     @param event the method is executed when the user clicks the Print All Accounts button on the GUI.
     */
    @FXML
    protected void onPrintAllAccountsButtonClick(ActionEvent event) {
        if (bankDatabase.getNumAcct() == ZERO_ACCOUNTS){
            messageArea.appendText("Account Database is empty!\n");
        } else {
            messageArea.appendText("\n*list of accounts in the database*\n");
            messageArea.appendText(bankDatabase.print());
            messageArea.appendText("*end of list*\n\n");
        }
    }

    /**
     Prints the accounts in the database by order of type.
     @param event the method is executed when the user clicks the Print All Accounts By Types button on the GUI.
     */
    @FXML
    protected void onPrintAllAccountsByTypeButtonClick(ActionEvent event) {
        if (bankDatabase.getNumAcct() == ZERO_ACCOUNTS){
            messageArea.appendText("Account Database is empty!\n");
        } else {
            messageArea.appendText("\n*list of accounts by account type.\n");
            messageArea.appendText(bankDatabase.printByAccountType());
            messageArea.appendText("*end of list.\n\n");
        }
    }

    /**
     Prints the accounts in the database in their current order with their calculated fees/interests.
     @param event the method is executed when the user clicks the Calculate Interest and Fees button on the GUI.
     */
    @FXML
    protected void onCalculateInterestsAndFeesButtonClick(ActionEvent event) {
        if (bankDatabase.getNumAcct() == ZERO_ACCOUNTS){
            messageArea.appendText("Account Database is empty!\n");
        } else {
            messageArea.appendText("\n*list of accounts with fee and monthly interest");
            messageArea.appendText(bankDatabase.printFeeAndInterest());
            messageArea.appendText("*end of list.\n\n");
        }
    }

    /**
     Prints the accounts in the database in their current order with updated balances based on fees/interests.
     @param event the method is executed when the user clicks the Apply Interests and Fees button on the GUI.
     */
    @FXML
    protected void onApplyInterestsAndFeesButtonClick(ActionEvent event) {
        bankDatabase.updateBalance();
        if (bankDatabase.getNumAcct() == ZERO_ACCOUNTS){
            messageArea.appendText("Account Database is empty!\n");
        } else {
            messageArea.appendText("\n*list of accounts with updated balance");
            messageArea.appendText(bankDatabase.print());
            messageArea.appendText("*end of list.\n\n");
        }
    }

    /**
     Creates an account from values parsed from a user open command.
     @param newProfile the profile for the new account.
     @param balanceDouble the balance for the new account.
     @param codes the code to be used for loyalty/campus codes depending on account type.
     @param accountType the type of account.
     @return newAccount the new account.
     */
    public Account createNewAccount(Profile newProfile, double balanceDouble,
                                    int codes, String accountType){
        Account newAccount = new Checking(newProfile, true, balanceDouble);
        if(accountType.equals("Checking")){
            newAccount = new Checking(newProfile, false, balanceDouble);
        } else if (accountType.equals("College Checking")) {
            newAccount = new CollegeChecking(newProfile, false,
                    balanceDouble, codes);
        } else if (accountType.equals("Savings")) {
            newAccount = new Savings(newProfile, false,
                    balanceDouble, codes);
        } else if (accountType.equals("Money Market")) {
            newAccount = new MoneyMarket(newProfile, false, balanceDouble, 1);
        }
        return newAccount;
    }

    /**
     Performs the last step of opening an account by opening/reopening the account and displaying relevant messages.
     @param bankDatabase the database for which an account is being opened.
     @param newAccount the account being opened.
     */
    public void openAccountLastStep(AccountDatabase bankDatabase, Account newAccount){
        int index = bankDatabase.findClosedAccount(newAccount);
        if (index == INDEX_OUT_OF_BOUNDS){
            bankDatabase.open(newAccount);
            messageArea.appendText("Account opened.\n");
        } else {
            bankDatabase.reopen(newAccount, index);
            messageArea.appendText("Account reopened.\n");
        }
    }

    /**
     Performs the last step of making a deposit to an account or determining that the
     account does not exist in the database and displaying relevant messages.
     @param bankDatabase the database holding the account to which a deposit is being made.
     @param newAccount the account being deposited to.
     */
    public void depositBalanceLastStep(AccountDatabase bankDatabase, Account newAccount){
        Account depositAccount = bankDatabase.findByProfileType(newAccount);
        if (depositAccount == null){
            messageArea.appendText(newAccount.holder.toString() + " " + newAccount.getType()
                    + " is not in the database.\n");
        } else {
            depositAccount.deposit(newAccount.balance);
            bankDatabase.deposit(depositAccount);
            messageArea.appendText("Deposit - balance updated.\n");
        }
    }

    /**
     Performs the last step of making a withdrawal from an account if there is sufficient funds
     or determining that the account does not exist in the database and displaying relevant messages.
     @param bankDatabase the database holding the account from which a withdrawal is being made.
     @param newAccount the account being deposited to.
     */
    public void withdrawBalanceLastStep(AccountDatabase bankDatabase, Account newAccount){
        Account withdrawAccount = bankDatabase.findByProfileType(newAccount);
        if(withdrawAccount == null){
            messageArea.appendText(newAccount.holder.toString() + " " + newAccount.getType()
                    + " is not in the database.\n");
        } else {
            if (newAccount.balance > withdrawAccount.balance){
                messageArea.appendText("Withdraw - insufficient fund.\n");
            } else {
                withdrawAccount.withdraw(newAccount.balance);
                bankDatabase.withdraw(withdrawAccount);
                messageArea.appendText("Withdraw - balance updated.\n");
            }
        }
    }

    /**
     Disables/de-selects loyalty checkbox when College Checking account is selected.
     @param event the method is executed when the user clicks the College Checking button on the GUI.
     */
    @FXML
    protected void onCollegeCheckingButtonClick(ActionEvent event) {
        newBrunswick.setDisable(false);
        newark.setDisable(false);
        camden.setDisable(false);
        loyal.setDisable(true);
        loyal.setSelected(false);
    }

    /**
     Disables/de-selects campus selection and loyalty when Checking account is selected.
     @param event the method is executed when the user clicks the Checking button on the GUI.
     */
    @FXML
    protected void onCheckingButtonClick(ActionEvent event) {
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        loyal.setDisable(true);
        newBrunswick.setSelected(false);
        newark.setSelected(false);
        camden.setSelected(false);
        loyal.setSelected(false);
    }

    /**
     Disables/de-selects campus selection when Money Market account is selected.
     @param event the method is executed when the user clicks the Money Market button on the GUI.
     */
    @FXML
    protected void onMoneyMarketButtonClick(ActionEvent event) {
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        loyal.setDisable(false);
        newBrunswick.setSelected(false);
        newark.setSelected(false);
        camden.setSelected(false);
    }

    /**
     Disables/de-selects campus selection when Money Market account is selected.
     @param event the method is executed when the user clicks the Money Market button on the GUI.
     */
    @FXML
    protected void onSavingsButtonClick(ActionEvent event) {
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        loyal.setDisable(false);
        newBrunswick.setSelected(false);
        newark.setSelected(false);
        camden.setSelected(false);
    }
}