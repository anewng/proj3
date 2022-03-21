package bank;

import java.text.DecimalFormat;

/**
 The Savings class is used to create, manipulate, and access Savings Account objects.
 It is a subclass of Account.
 Savings Accounts can be compared to each other by different attributes, withdrawn from, and deposited to.
 An account's monthly interest, fees, and type can be accessed by this class.
 An account can also be converted to a string.
 @author Annie Wang, Jasmine Flanders
 */
public class Savings extends Account{
    protected int loyal;

    private static final double SAVINGS_INTEREST = .003 / 12;
    private static final double LOYAL_SAVINGS_INTEREST = .0045 / 12;
    private static final double SAVINGS_FEE = 6;
    private static final String SAVINGS_TYPE = "Savings";
    private static final int MIN_SAVINGS_BALANCE = 300;

    /**
     Constructor creates a Savings Account object.
     @param newHolder the profile of the account holder.
     @param isClosed boolean value representing whether an account is open(false)/closed(true)
     @param newBalance the balance of the account.
     @param isLoyal int representing whether an account is loyal(1)/non-loyal(0)
     */
    public Savings(Profile newHolder, boolean isClosed, double newBalance, int isLoyal){
        super(newHolder, isClosed, newBalance);
        loyal = isLoyal;
    }

    /**
     Returns an account's monthly interest.
     @return double the value of the interest.
     */
    public double monthlyInterest(){
        if (loyal == 1){
            return LOYAL_SAVINGS_INTEREST * balance;
        }
        return SAVINGS_INTEREST * balance;
    }

    /**
     Returns an account's monthly fee.
     @return double the value of the fee.
     */
    public double fee(){
        if (balance >= MIN_SAVINGS_BALANCE) {
            return WAIVED_FEE;
        }
        return SAVINGS_FEE;
    }

    /**
     Returns an account's type.
     @return string representing the type.
     */
    public String getType(){
        return SAVINGS_TYPE;
    }

    /**
     Converts an account to a string, with type, profile, balance, and status.
     @return string representation of account.
     */
    @Override
    public String toString(){
        DecimalFormat d = new DecimalFormat("'$'#,##0.00");
        String returnString = getType() + "::" + holder.toString() + "::Balance " + d.format(balance);
        if (loyal == 1){
            returnString += "::Loyal";
        } else if (closed) {
            returnString += "::CLOSED";
        }
        return returnString;
    }

}
