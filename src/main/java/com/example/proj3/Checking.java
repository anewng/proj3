package bank;

import java.text.DecimalFormat;

/**
 The Checking class is used to create, manipulate, and access Checking Account objects.
 It is a subclass of Account.
 Checking Accounts can be compared to each other by different attributes, withdrawn from, and deposited to.
 An account's monthly interest, fees, and type can be accessed by this class.
 An account can also be converted to a string.
 @author Annie Wang, Jasmine Flanders
 */
public class Checking extends Account{
    private static final double CHECKING_INTEREST = .001 / 12;
    private static final double CHECKING_FEE = 25;
    private static final String CHECKING_TYPE = "Checking";
    private static final int MIN_CHECKING_BALANCE = 1000;

    /**
     Constructor creates a Checking Account object.
     @param newHolder the profile of the account holder.
     @param isClosed boolean value representing whether an account is open(false)/closed(true)
     @param newBalance the balance of the account.
     */
    public Checking(Profile newHolder, boolean isClosed, double newBalance){
        super(newHolder, isClosed, newBalance);
    }

    /**
     Returns an account's monthly interest.
     @return double the value of the interest.
     */
    public double monthlyInterest() {return CHECKING_INTEREST * balance;}

    /**
     Returns an account's monthly fee.
     @return double the value of the fee.
     */
    public double fee(){
        if (balance >= MIN_CHECKING_BALANCE) {
            return WAIVED_FEE;
        }
        return CHECKING_FEE;
    }

    /**
     Returns an account's type.
     @return string representing the type.
     */
    public String getType(){
        return CHECKING_TYPE;
    }

    /**
     Converts an account to a string, with type, profile, balance, and status.
     @return string representation of account.
     */
    @Override
    public String toString(){
        DecimalFormat d = new DecimalFormat("'$'#,##0.00");
        String returnString = getType() + "::" + holder.toString() + "::Balance " + d.format(balance);
        if (closed) {
            returnString += "::CLOSED";
        }
        return returnString;
    }

}
