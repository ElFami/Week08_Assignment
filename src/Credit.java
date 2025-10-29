public abstract class Credit extends Payment {

    private int installment;
    private int maxInstallmentAmount;

    public Credit(Item item, int maxInstallmentAmount) {
        super(item);
        this.maxInstallmentAmount = maxInstallmentAmount;
        this.installment = 0;
    }

    public int getInstallment() {
        return installment;
    }

    public int getMaxInstallmentAmount() {
        return maxInstallmentAmount;
    }

    @Override
    public int pay() {
        int paymentAmount = getItem().getPrice() / maxInstallmentAmount;

        if (isPaidOff) {
            return 0;
        }

        installment++;

        if (installment >= maxInstallmentAmount) {
            isPaidOff = true;
        }

        return paymentAmount;
    }

    @Override
    public int getRemainingAmount() {
        if (isPaidOff) {
            return 0;
        }

        int installmentValue = getItem().getPrice() / maxInstallmentAmount;
        int remainingInstallments = maxInstallmentAmount - installment;

        return remainingInstallments * installmentValue;
    }
}
