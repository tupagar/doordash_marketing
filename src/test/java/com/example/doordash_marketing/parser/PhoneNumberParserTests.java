package com.example.doordash_marketing.parser;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;

import com.example.doordash_marketing.IO.PhonesRawInputDTO;

public class PhoneNumberParserTests {

    @Test
    public void testParserValidInput() {
        assertTrue(PhoneNumberParser
                .parseInput(new PhonesRawInputDTO(
                        "(Home) 415-415-4155 (Cell) 415-123-4567   (home) 111-222-1111"))
                .size() == 3);
    }

    @Test
    public void testParserEmptyInput() {
        assertTrue(PhoneNumberParser.parseInput(new PhonesRawInputDTO("")).size() == 0);
    }

    @Test
    public void testParserNullInput() {
        assertTrue(PhoneNumberParser.parseInput(new PhonesRawInputDTO(null)).size() == 0);
    }

    @Test
    public void testParserInValidPhoneTypeInput() {
        assertTrue(PhoneNumberParser
                .parseInput(new PhonesRawInputDTO(" (xxx 111-222-1111"))
                .size() == 0);
    }

    @Test
    public void testParserInValidPhoneNumberInput() {
        assertTrue(PhoneNumberParser
                .parseInput(new PhonesRawInputDTO(" (xxx)"))
                .size() == 0);
    }
}
