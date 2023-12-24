package Mortgage.Calc;

public class Calculations {
    private double principle;
    private int amortization;
    private double interest;
    public static final int minAmort = 20;
    public static final int maxAmort = 30;
    public static final int maxInt = 50;
    public static final double epsilon = 0.001D;
    private static final int monthYear = 12;

    //constructors to set value to private attributes
    public Calculations() {
        this.principle = 0.0D;
        this.interest = 0.0D;
        this.amortization = minAmort;
    }
    //methods which return attributes
    public String getPrinciple() {
        return String.format("%f", this.principle);
    }

    public String getAmortization() {
        return String.format("%d", this.amortization);
    }

    public String getInterest() {
        return String.format("%f", this.interest);
    }

    //methods which set attributes; makes sure they're within acceptable range
    public void setPrinciple(String p) {
        double result;
        try {
            result = Double.parseDouble(p);
        } catch (Exception var5) {
            throw new RuntimeException("Principle not numeric!");
        }

        if (result <= 0.0D) {
            throw new RuntimeException("Principle not positive!");
        } else {
            this.principle = result;
        }
    }

    public void setAmortization(String a) {
        int result;
        try {
            result = Integer.parseInt(a);
        } catch (Exception var4) {
            throw new RuntimeException("Amortization not numeric!");
        }

        if (result >= minAmort && result <= maxAmort) {
            this.amortization = result;
        } else {
            throw new RuntimeException("Amortization out of range!");
        }
    }

    public void setInterest(String i) {
        double result;
        try {
            result = Double.parseDouble(i);
        } catch (Exception var5) {
            throw new RuntimeException("Interest not numeric!");
        }

        if (!(result < 0.0D) && !(result > maxInt)) {
            this.interest = result;
        } else {
            throw new RuntimeException("Interest out of range!");
        }
    }

    //methods which print and compute payments
    public String computePayment(String format) {
        return String.format(format, this.computePayment());
    }

    private double computePayment() {
        double p = this.principle;
        int n = this.amortization * monthYear;
        double r = this.interest / 100.0D / 12.0D;
        double result;
        if (this.interest <= epsilon) {
            result = p / (double)n;
        } else {
            double numerator = r * p;
            double denominator = Math.pow(1.0D + r, (double)n);
            denominator = 1.0D / denominator;
            denominator = 1.0D - denominator;
            result = numerator / denominator;
        }

        return result;
    }

    //method and prints and computes principle remaining after n amount of years
    public String outstandingAfter(int years, String format) {
        return String.format(format, this.outstandingAfter(years));
    }

    private double outstandingAfter(int years) {
        double p = this.principle;
        double r = this.interest / 100.0D / 12.0D;
        double result;
        if (this.interest <= epsilon) {
            result = p - (double)(years * 12) * this.computePayment();
        } else {
            result = this.computePayment() / r - p;
            result *= Math.pow(1.0D + r, (double)(12 * years)) - 1.0D;
            result = p - result;
        }

        if (Math.abs(result) < epsilon) {
            result = 0.0D;
        }

        return result;
    }

    //toString method which depicts calculations
    public String toString() {
        return "Calculations instance with principle=" + this.getPrinciple() + " amortization=" + this.getAmortization() + " interest=" + this.getInterest();
    }
}

