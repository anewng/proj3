package bank;

import java.text.DecimalFormat;

/**
 The CollegeChecking class is used to create, manipulate, and access College Checking Account objects.
 It is a subclass of the Account class and extends the Checking class.
 College Checking Accounts can be compared to each other by different attributes, withdrawn from, and deposited to.
 An account's monthly interest, fees, and type can be accessed by this class.
 An account can also be converted to a string.
 @author Annie Wang, Jasmine Flanders
 */
public class CollegeChecking extends Checking{
    protected int collegeCode;

    private static final double C_CHECKING_INTEREST = .0025 / 12;
    private static final double C_CHECKING_FEE = 0;
    private static final String C_CHECKING_TYPE = "College Checking";
    private static final int NEW_BRUNSWICK = 0;
    private static final int NEWARK = 1;
    private static final int CAMDEN = 2;

    /**
     Constructor creates a CollegeChecking Account object.
     @param newHolder the profile of the account holder.
     @param isClosed boolean value representing whether an account is open(false)/closed(true)
     @param newBalance the balance of the account.
     @param newCollegeCode an int representing the code corresponding to a college campus.
     */
    public CollegeChecking(Profile newHolder, boolean isClosed, double newBalance, int newCollegeCode){
        super(newHolder, isClosed, newBalance);
        collegeCode = newCollegeCode;
    }

    /**
     Returns an account's monthly interest.
     @return double the value of the interest.
     */
    @Override
    public double monthlyInterest(){
        return C_CHECKING_INTEREST * balance;
    }

    /**
     Returns an account's monthly fee.
     @return double the value of the fee.
     */
    @Override
    public double fee(){
        return C_CHECKING_FEE;
    } //return the monthly fee

    /**
     Returns an account's type.
     @return string representing the type.
     */
    @Override
    public String getType(){
        return C_CHECKING_TYPE;
    } //return the account type (class name)

    /**
     Converts an account to a string, with type, profile, balance, status, and associated campus.
     @return string representation of account.
     */
    @Override
    public String toString(){
        String campus = "";
        String returnString = "";
        DecimalFormat d = new DecimalFormat("'$'#,##0.00");
        if(collegeCode == NEW_BRUNSWICK){
            campus = "NEW_BRUNSWICK";
        }else if(collegeCode == NEWARK){
            campus = "NEWARK";
        }else if(collegeCode == CAMDEN){
            campus = "CAMDEN";
        }
        returnString += getType() + "::" + holder.toString() + "::Balance " + d.format(balance);
        if (closed) {
            returnString += "::CLOSED";
        }
        returnString += "::" + campus;
        return returnString;
    }

    /**
     Returns an account's college code.
     @return int the college code.
     */
    public int getCollegeCode() {
        return collegeCode;
    }
}
