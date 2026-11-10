package com.swiftwheels.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidationAndPricingTest {

    @Test
    void acceptsValidRegistration() {
        assertNull(Customer.validate("Umut", "Tosun", "05437457478", "umut@gazi.edu.tr", "secret1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a@b.com", "first.last+tag@uni.edu.tr", "x@sub.domain.org"})
    void acceptsRealWorldEmails(String mail) {
        // the old check required ".com", so ".edu.tr" addresses were rejected
        assertNull(Customer.validate("A", "B", "05001112233", mail, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"no-at-sign.com", "a@b", "a b@c.com", "@c.com"})
    void rejectsInvalidEmails(String mail) {
        assertNotNull(Customer.validate("A", "B", "05001112233", mail, null));
    }

    @Test
    void rejectsEmptyFieldsAndBadPhones() {
        assertNotNull(Customer.validate("", "B", "05001112233", "a@b.com", "secret1"));
        assertNotNull(Customer.validate("A", "B", "05", "a@b.com", "secret1"), "empty phone mask leaves only '05'");
        assertNotNull(Customer.validate("A", "B", "02121112233", "a@b.com", "secret1"));
    }

    @Test
    void nullPasswordMeansUnchangedButShortPasswordIsRejected() {
        assertNull(Customer.validate("A", "B", "05001112233", "a@b.com", null));
        assertNotNull(Customer.validate("A", "B", "05001112233", "a@b.com", "123"));
    }

    @Test
    void feeIsDailyPriceTimesDaysPlusOptionalFlatInsurance() {
        assertEquals(120, RentalPricing.calculateFee(40, 3, false));
        assertEquals(140, RentalPricing.calculateFee(40, 3, true));
        assertEquals(40 + RentalPricing.INSURANCE_FEE, RentalPricing.calculateFee(40, 1, true));
    }
}
