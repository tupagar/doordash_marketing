package com.example.doordash_marketing.repository;

import com.example.doordash_marketing.model.Phone;
import com.example.doordash_marketing.model.PhoneInfo;
import org.springframework.data.repository.CrudRepository;

public interface PhoneInfoRepository extends CrudRepository<PhoneInfo, Long> {
    PhoneInfo findByPhone(Phone phone);
}
