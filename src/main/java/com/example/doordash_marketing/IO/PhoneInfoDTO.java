package com.example.doordash_marketing.IO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PhoneInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("phone_type")
    private String phoneType;

    private Long occurrences;

}
