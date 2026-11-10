package com.swiftwheels.model;

/**
 * Rules a new password must satisfy (registration and password change).
 */
public final class PasswordPolicy {

    public static final int MIN_LENGTH = 6;
    public static final int MAX_LENGTH = 64;

    private PasswordPolicy() {
    }

    /**
     * @return an error message to show the user, or {@code null} if the password is acceptable
     */
    public static String validate(String password) {
        if (password.length() < MIN_LENGTH) {
            return "Şifre en az " + MIN_LENGTH + " karakter olmalı.";
        }
        if (password.length() > MAX_LENGTH) {
            return "Şifre en fazla " + MAX_LENGTH + " karakter olabilir.";
        }
        return null;
    }
}
