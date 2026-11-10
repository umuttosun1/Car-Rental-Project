package com.swiftwheels.model;

/** Business rules for the rental price shown on the contract page and stored with the rental. */
public final class RentalPricing {

    /** Flat price of the optional "kasko + sigorta" package, in $. */
    public static final int INSURANCE_FEE = 20;

    private RentalPricing() {
    }

    /**
     * @param dailyPrice   the vehicle's daily price ($)
     * @param days         rental length in days (always &gt;= 1)
     * @param withInsurance whether the customer ticked "kasko + sigorta"
     * @return total fee in $
     */
    public static int calculateFee(int dailyPrice, int days, boolean withInsurance) {
        int fee = dailyPrice * days;
        if (withInsurance) {
            fee += INSURANCE_FEE;
        }
        return fee;
    }
}
