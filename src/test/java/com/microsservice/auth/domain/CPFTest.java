package com.microsservice.auth.domain;

import com.microsservice.auth.domain.exceptions.InvalidCpfException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPFTest {

    @Test
    void shouldCreateValidCpf() {
        String validCpf = "123.456.789-09";
        CPF cpf = CPF.create(validCpf);
        assertNotNull(cpf);
        assertEquals("12345678909", cpf.getValue());
    }

    @Test
    void shouldCreateValidCpfWithoutFormatting() {
        String validCpf = "12345678909";
        CPF cpf = CPF.create(validCpf);
        assertNotNull(cpf);
        assertEquals("12345678909", cpf.getValue());
    }

    @Test
    void shouldThrowInvalidCpfExceptionWhenCpfIsNull() {
        assertThrows(InvalidCpfException.class, () -> {
            CPF.create(null);
        });
    }

    @Test
    void shouldThrowInvalidCpfExceptionWhenCpfIsBlank() {
        assertThrows(InvalidCpfException.class, () -> {
            CPF.create("");
        });
        assertThrows(InvalidCpfException.class, () -> {
            CPF.create("   ");
        });
    }

    @Test
    void shouldThrowInvalidCpfExceptionWhenCpfHasWrongLength() {
        assertThrows(InvalidCpfException.class, () -> {
            CPF.create("123456789");
        });
        assertThrows(InvalidCpfException.class, () -> {
            CPF.create("123456789012");
        });
    }

    @Test
    void shouldThrowInvalidCpfExceptionWhenCpfIsInvalid() {
        assertThrows(InvalidCpfException.class, () -> {
            CPF.create("11111111111");
        });
        assertThrows(InvalidCpfException.class, () -> {
            CPF.create("12345678900");
        });
    }

    @Test
    void shouldReturnCorrectToString() {
        CPF cpf = CPF.create("12345678909");
        assertEquals("12345678909", cpf.toString());
    }

    @Test
    void shouldBeEqualWhenSameValue() {
        CPF cpf1 = CPF.create("12345678909");
        CPF cpf2 = CPF.create("123.456.789-09");
        assertEquals(cpf1, cpf2);
    }

    @Test
    void shouldHaveSameHashCodeWhenEqual() {
        CPF cpf1 = CPF.create("12345678909");
        CPF cpf2 = CPF.create("123.456.789-09");
        assertEquals(cpf1.hashCode(), cpf2.hashCode());
    }
}
