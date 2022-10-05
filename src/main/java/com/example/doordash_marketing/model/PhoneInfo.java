package com.example.doordash_marketing.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "PHONE_INFO",
        uniqueConstraints= @UniqueConstraint(columnNames={"PHONE_NUMBER", "PHONE_TYPE"}))
@NoArgsConstructor
public class PhoneInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "phoneNumber", column = @Column(name = "PHONE_NUMBER", updatable = false)),
            @AttributeOverride( name = "phoneType", column = @Column(name = "PHONE_TYPE", updatable = false))
    })
    private Phone phone;

    private Long occurrences;

    public PhoneInfo(Phone phone) {
        this.phone = phone;
        this.occurrences = 1l;
    }

    public void incrementOccurrences() {
        synchronized (this) {
            this.occurrences += 1;
        }
    }
}
