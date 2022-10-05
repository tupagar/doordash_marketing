package com.example.doordash_marketing.service;

import com.example.doordash_marketing.IO.PhoneInfoDTO;
import com.example.doordash_marketing.IO.PhonesRawInputDTO;
import com.example.doordash_marketing.model.Phone;
import com.example.doordash_marketing.model.PhoneInfo;
import com.example.doordash_marketing.parser.PhoneNumberParser;
import com.example.doordash_marketing.repository.PhoneInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PhoneInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneInfoService.class);

    @Autowired
    PhoneInfoRepository phoneInfoRepository;
    private static List<String> allowedType = new ArrayList<>();
    static {
        allowedType.add("home");
        allowedType.add("cell");
    }

    /**
     * This method parses converts input raw data into sanitised valid phone-type data and persists it in the db
     * Ignores the invalid entries from the input.
     * @return updated list of phone-type entries with count of occurrences
     * */
    public List<PhoneInfoDTO> saveData(PhonesRawInputDTO input) {
        LOGGER.debug("Inside saveData method.");
        List<Phone> phones = PhoneNumberParser.parseInput(input);
        for (Phone phone : phones) {
            if (validate(phone)) {
                this.addOrUpdatePhone(phone);
            }
        }
        return this.getAll();
    }

    private boolean validate(Phone phone) {
        return phone.getPhoneNumber().length() == 10 && allowedType.contains(phone.getPhoneType());
    }

    /**
     * This method adds new entry for a unique pair of phone-type in db
     * or updates and increments the count of occurrences of existing entry.
     * To avoid invalid concurrent modifications, using Transactional Isolation level of REPEATABLE_READ
     * */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addOrUpdatePhone(Phone phone) {
        LOGGER.info("Adding phone number - {}", phone);
        PhoneInfo phoneInfo = phoneInfoRepository.findByPhone(phone);
        if (phoneInfo != null) {
            LOGGER.info("Phone already exists, increment its occurrence count and save");
            phoneInfo.incrementOccurrences();
        } else {
            LOGGER.info("Phone doesn't exist, create new entry");
            phoneInfoRepository.save(new PhoneInfo(phone));
        }
    }

    /**
     * This method retrieves all phone number entries.
     * */
    public List<PhoneInfoDTO> getAll() {
        LOGGER.info("Retrieving all phone info data");
        Iterable<PhoneInfo> phonesInfo = phoneInfoRepository.findAll();
        List<PhoneInfoDTO> result = new ArrayList<>();
        phonesInfo.forEach(phoneInfo -> {
            result.add(
                    new PhoneInfoDTO(phoneInfo.getId(),
                            phoneInfo.getPhone().getPhoneNumber(),
                            phoneInfo.getPhone().getPhoneType(),
                            phoneInfo.getOccurrences())
            );
        });
        return result;
    }
}
