package com.microsservice.auth.domain;

import com.microsservice.auth.domain.exceptions.InvalidCpfException;

import java.util.Objects;

public class CPF {

    private final String value;

    private CPF(String value) {
        this.value = value;
    }

    public static CPF create(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new InvalidCpfException("CPF cannot be null or blank");
        }

        String cleanedCpf = cpf.replaceAll("[^0-9]", "");

        if (cleanedCpf.length() != 11) {
            throw new InvalidCpfException("CPF must have 11 digits");
        }

        if (!isValidCpf(cleanedCpf)) {
            throw new InvalidCpfException("Invalid CPF");
        }

        return new CPF(cleanedCpf);
    }

    private static boolean isValidCpf(String cpf) {
        // Check if all digits are the same
        if (cpf.chars().allMatch(c -> c == cpf.charAt(0))) {
            return false;
        }

        // Calculate first check digit
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstCheck = 11 - (sum % 11);
        if (firstCheck >= 10) firstCheck = 0;

        if (firstCheck != (cpf.charAt(9) - '0')) {
            return false;
        }

        // Calculate second check digit
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondCheck = 11 - (sum % 11);
        if (secondCheck >= 10) secondCheck = 0;

        return secondCheck == (cpf.charAt(10) - '0');
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPF cpf = (CPF) o;
        return Objects.equals(value, cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
