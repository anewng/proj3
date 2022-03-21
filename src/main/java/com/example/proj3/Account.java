package bank;

import java.text.DecimalFormat;

/**
 The Account class is used to create, manipulate, and access Account objects.
 Accounts can be compared to each other by different attributes, withdrawn from, and deposited to.
 An account's monthly interest, fees, and type can be accessed by this class.
 An account can also be converted to a string.
 @author Annie Wang, Jasmine Flanders
 */
public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    protected static final int WAIVED_FEE = 0;
    private static final int FIRST_TWO_LETTERS = 2;

    /**
     Constructor creates an Account object.
     @param newHolder the profile of the account holder.
     @param isClosed boolean value representing whether an account is open(false)/closed(true)
     @param newBalance the balance of the account.
     */
    public Account(Profile newHolder, boolean isClosed, double newBalance){
        holder = newHolder;
        closed = isClosed;
        balance = newBalance;
    }

    /**
     Returns an account's monthly interest.
     @return double the value of the interest.
     */
    public abstract double monthlyInterest();

    /**
     Returns an account's monthly fee.
     @return double the value of the fee.
     */
    public abstract double fee();

    /**
     Returns an account's type.
     @return string representing the type.
     */
    public abstract String getType();

    /**
     Compares two accounts.
     @param obj the account object the account is being compared to.
     @return true if the accounts are equal, else return false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            if ( ((account instanceof Checking && this instanceof Checking)
                && (account instanceof CollegeChecking && this instanceof CollegeChecking ||
                    !(account instanceof CollegeChecking) && !(this instanceof CollegeChecking)))
            || ((account instanceof Savings && this instanceof Savings)
                    && (account instanceof MoneyMarket && this instanceof MoneyMarket ||
                    !(account instanceof MoneyMarket) && !(this instanceof MoneyMarket)))){
                if (this.holder.equals(account.holder) == 0
                        && (this.closed == account.closed)
                        && (this.balance == account.balance)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     Converts an account to a string, with type, profile, and balance.
     @return string representation of account.
     */
    @Override
    public String toString() {
        String returnString = "";
        DecimalFormat d = new DecimalFormat("'$'#,##0.00");
        if (this instanceof Checking) {
            Checking checkingAcct = (Checking) this;
            returnString += checkingAcct.getType() + "::" + holder.toString() + "::Balance " + d.format(balance);
        } else if (this instanceof Savings) {
            Savings savingsAcct = (Savings) this;
            returnString += savingsAcct.getType() + "::" + holder.toString() + "::Balance " + d.format(balance);
        }
        return returnString;
    }

    /**
     Withdraws money from an account, updating balance.
     @param amount the amount being withdrawn.
     */
    public void withdraw(double amount) {
        this.balance -= amount;
        if (this instanceof MoneyMarket && this.balance < 2500) {
            MoneyMarket account = (MoneyMarket) this;
            account.loyal = 0;
        }
    }

    /**
     Deposits money in an account, updating balance.
     @param amount the amount being deposited.
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     Compares two accounts by profile, type and open/closed status.
     @param obj the account object the account is being compared to.
     @return true if the accounts' profile, type, and status are equal, else return false.
     */
    public boolean equalsProfileTypeClosed(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            if ( ((account instanceof Checking && this instanceof Checking)
                    && (account instanceof CollegeChecking && this instanceof CollegeChecking ||
                    !(account instanceof CollegeChecking) && !(this instanceof CollegeChecking)))
                    || ((account instanceof Savings && this instanceof Savings)
                    && (account instanceof MoneyMarket && this instanceof MoneyMarket ||
                    !(account instanceof MoneyMarket) && !(this instanceof MoneyMarket)))){
                if (this.holder.equals(account.holder) == 0
                        && (this.closed == account.closed)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     Compares two accounts by profile and type.
     @param obj the account object the account is being compared to.
     @return true if the accounts' profile and type are equal, else return false.
     */
    public boolean equalsProfileType(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            if( ((account instanceof Checking && this instanceof Checking)
                    && (account instanceof CollegeChecking && this instanceof CollegeChecking ||
                    !(account instanceof CollegeChecking) && !(this instanceof CollegeChecking)))
                    || ((account instanceof Savings && this instanceof Savings)
                    && (account instanceof MoneyMarket && this instanceof MoneyMarket ||
                    !(account instanceof MoneyMarket) && !(this instanceof MoneyMarket)))){
                if (this.holder.equals(account.holder) == 0){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     Compares two accounts by alphabetical order of account type.
     @param account the account being compared to.
     @return 0 if account type is the same, -1 if less than, 1 if greater than (alphabetically)
     */
    public int alphabetizeAccountType(Account account) {
        char thisAccountType[] = this.getType().toCharArray();
        char accountType[] = account.getType().toCharArray();
        if (this.getType().compareTo(account.getType()) == 0){
            return 0;
        } else {
            for (int i = 0; i < FIRST_TWO_LETTERS; i++) {
                if (thisAccountType[i] == accountType[i]) {
                    continue;
                } else if(thisAccountType[i] < accountType[i]){
                    return -1;
                } else {
                    return 1;
                }
            }
        }
        return 0;
    }
}