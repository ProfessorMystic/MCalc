public class MPro {
    private double principle;
    private int amortization;
    private double interest;
    public static final int AMORT_MIN = 20;
    public static final int AMORT_MAX = 30;
    public static final int INTEREST_MAX = 50;
    public static final double EPSILON = 0.001D;
    private static final int MONTH_PER_YEAR = 12;

    public MPro(String p, String a, String i) {
        this.setPrinciple(p);
        this.setAmortization(a);
        this.setInterest(i);
    }

    public MPro(String p, String i) {
        this.setPrinciple(p);
        this.amortization = 30;
        this.setInterest(i);
    }

    public MPro() {
        this.principle = 0.0D;
        this.interest = 0.0D;
        this.amortization = 20;
    }

    public String getPrinciple() {
        return String.format("%f", this.principle);
    }

    public String getAmortization() {
        return String.format("%d", this.amortization);
    }

    public String getInterest() {
        return String.format("%f", this.interest);
    }

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

        if (result >= 20 && result <= 30) {
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

        if (!(result < 0.0D) && !(result > 50.0D)) {
            this.interest = result;
        } else {
            throw new RuntimeException("Interest out of range!");
        }
    }

    public String computePayment(String format) {
        return String.format(format, this.computePayment());
    }

    private double computePayment() {
        double p = this.principle;
        int n = this.amortization * 12;
        double r = this.interest / 100.0D / 12.0D;
        double result;
        if (this.interest <= 0.001D) {
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

    public String outstandingAfter(int years, String format) {
        return String.format(format, this.outstandingAfter(years));
    }

    private double outstandingAfter(int years) {
        double p = this.principle;
        double r = this.interest / 100.0D / 12.0D;
        double result;
        if (this.interest <= 0.001D) {
            result = p - (double)(years * 12) * this.computePayment();
        } else {
            result = this.computePayment() / r - p;
            result *= Math.pow(1.0D + r, (double)(12 * years)) - 1.0D;
            result = p - result;
        }

        if (Math.abs(result) < 0.001D) {
            result = 0.0D;
        }

        return result;
    }

    public String toString() {
        return "MPro instance with principle=" + this.getPrinciple() + " amortization=" + this.getAmortization() + " interest=" + this.getInterest();
    }

    public boolean equals(MPro other) {
        return Math.abs(this.principle - other.principle) <= 0.001D && this.amortization == other.amortization && Math.abs(this.interest - other.interest) <= 0.001D;
    }
}
