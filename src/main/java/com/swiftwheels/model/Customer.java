package com.swiftwheels.model;

import java.util.regex.Pattern;

/** A logged-in user. Never carries the password. */
public record Customer(int id, String name, String surname, String phone, String mail, boolean admin) {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern PHONE = Pattern.compile("^05\\d{9}$");

    /**
     * Checks registration / profile input.
     *
     * @param password may be {@code null} when the password is not being changed
     * @return an error message, or {@code null} when everything is valid
     */
    public static String validate(String name, String surname, String phone, String mail, String password) {
        if (isBlank(name) || isBlank(surname) || isBlank(phone) || isBlank(mail)) {
            return "Hiçbir bölüm boş bırakılmamalı!";
        }
        if (name.length() > 40 || surname.length() > 40) {
            return "Ad ve soyad en fazla 40 karakter olabilir.";
        }
        if (!PHONE.matcher(phone).matches()) {
            return "Telefon numarası 05XX XXX XX XX biçiminde olmalı.";
        }
        if (mail.length() > 100 || !EMAIL.matcher(mail).matches()) {
            return "Geçersiz e-posta adresi.";
        }
        if (password != null) {
            return PasswordPolicy.validate(password);
        }
        return null;
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
