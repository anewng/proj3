package bank;

import java.text.DecimalFormat;

/**
 The MoneyMarket class is used to create, manipulate, and access MoneyMarket Account objects.
 It is a subclass of the Account class and extends the Savings class.
 MoneyMarket Accounts can be compared to each other by different attributes, withdrawn from, and deposited to.
 An account's monthly interest, fees, and type can be accessed by this class.
 An account can also be converted to a string.
 @author Annie Wang, Jasmine Flanders
 */
public class MoneyMarket extends Savings{
    protected int withdrawalCount = 0;

    private static final double MM_INTEREST = .008 / 12;
    private static final double LOYAL_MM_INTEREST = .0095 / 12;
    private static final double MM_FEE = 10;
    private static final String MM_TYPE = "Money Market";
    private static final int MIN_MM_BALANCE = 2500;
    private static final int MAX_WITHDRAWAL = 3;

    /**
     Constructor creates a MoneyMarket Account object.
     @param newHolder the profile of the account holder.
     @param isClosed boolean value representing whether an account is open(false)/closed(true)
     @param newBalance the balance of the account.
     @param isLoyal int representing whether an account is loyal(1)/non-loyal(0)
     */
    public MoneyMarket(Profile newHolder, boolean isClosed, double newBalance, int isLoyal) {
        super(newHolder, isClosed, newBalance, isLoyal);
        loyal = 1;
    }

    /**
     Returns an account's monthly interest.
     @return double the value of the interest.
     */
    @Override
    public double monthlyInterest(){
        if (balance < MIN_MM_BALANCE) {
            loyal = 0;
        } else {
            loyal = 1;
        }
        if (loyal == 1){
            return LOYAL_MM_INTEREST * balance;
        }
        return MM_INTEREST * balance;
    }

    /**
     Returns an account's monthly fee.
     @return double the value of the fee.
     */
    @Override
    public double fee(){
        if (balance >= MIN_MM_BALANCE && withdrawalCount < MAX_WITHDRAWAL) {
            return WAIVED_FEE;
        }
        return MM_FEE;
    }

    /**
     Returns an account's type.
     @return string representing the type.
     */
    @Override
    public String getType(){
        return MM_TYPE;
    } //return the account type (class name)

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
        returnString += "::withdrawl: " + withdrawalCount;
        return returnString;
    }

}
