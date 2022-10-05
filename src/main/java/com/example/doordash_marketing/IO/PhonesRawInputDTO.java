package com.example.doordash_marketing.IO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhonesRawInputDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Valid
    @JsonProperty("raw_phone_numbers")
    private String rawPhoneNumbers;

}
