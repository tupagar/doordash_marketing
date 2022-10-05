package com.example.doordash_marketing.parser;

import com.example.doordash_marketing.IO.PhonesRawInputDTO;
import com.example.doordash_marketing.model.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberParser {

    public static List<Phone> parseInput(PhonesRawInputDTO input) {
        List<Phone> parsedData = new ArrayList<>();
        String inputStr = input.getRawPhoneNumbers();
        if (inputStr == null || inputStr.isBlank()) {
            return parsedData;
        }
        inputStr = inputStr.trim();
        StringBuilder typeBuffer = null;
        StringBuilder phoneNumberBuffer = null;
        boolean readingNumber = false;
        for (char ch : inputStr.toCharArray()) {
            if (ch == ' ') {
                continue;
            }else if (ch == '(') {
                if (!isEmpty(typeBuffer) && !isEmpty(phoneNumberBuffer)) {
                    parsedData.add(new Phone(phoneNumberBuffer.toString(), typeBuffer.toString()));
                }
                typeBuffer = new StringBuilder();
                phoneNumberBuffer = new StringBuilder();
                readingNumber = false;
            } else if (ch == ')') {
                readingNumber = true;
            } else if (ch >= '0' && ch <= '9') {
                if (readingNumber) {
                    phoneNumberBuffer.append(ch);
                } else {
                    typeBuffer = null;
                }
            } else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                if (readingNumber) {
                    readingNumber = false;
                    phoneNumberBuffer = null;
                } else if (typeBuffer != null) {
                    typeBuffer.append(ch);
                }
            }
        }
        if (!isEmpty(typeBuffer) && !isEmpty(phoneNumberBuffer)) {
            parsedData.add(new Phone(phoneNumberBuffer.toString(), typeBuffer.toString()));
        }
        return parsedData;
    }

    private static boolean isEmpty(StringBuilder sb) {
        return sb == null || sb.length() == 0;
    }
}
