public abstract class Payment {
    protected double amount;

    public Payment(double amount) {
        this.amount = amount;
    }

    abstract public void processPayment();

    public void paymentDetails() {
        System.out.println("Processing Payment of $ " + amount);
    }
}

