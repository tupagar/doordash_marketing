package com.example.doordash_marketing.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Phone implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String phoneType;

    public Phone(String phoneNumber, String phoneType) {
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType.toLowerCase();
    }
}
