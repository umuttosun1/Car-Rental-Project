package com.swiftwheels.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHasherTest {

    @Test
    void verifiesCorrectPasswordAndRejectsWrongOne() {
        String hash = PasswordHasher.hash("Admin123!");
        assertTrue(PasswordHasher.verify("Admin123!", hash));
        assertFalse(PasswordHasher.verify("admin123!", hash));
        assertFalse(PasswordHasher.verify("", hash));
    }

    @Test
    void samePasswordGetsDifferentSalt() {
        assertNotEquals(PasswordHasher.hash("secret"), PasswordHasher.hash("secret"));
    }

    @Test
    void recognisesLegacyPlainTextValues() {
        assertTrue(PasswordHasher.isHash(PasswordHasher.hash("x")));
        assertFalse(PasswordHasher.isHash("123456"));
        assertFalse(PasswordHasher.verify("123456", "123456"), "plain text must never verify as a hash");
    }

    @Test
    void malformedHashesDoNotThrow() {
        assertFalse(PasswordHasher.verify("a", "pbkdf2_sha256$notanumber$xx$yy"));
        assertFalse(PasswordHasher.verify("a", "pbkdf2_sha256$1000$***$***"));
        assertFalse(PasswordHasher.verify("a", null));
    }
}
