package com.example.doordash_marketing.controller;

import com.example.doordash_marketing.IO.PhoneInfoDTO;
import com.example.doordash_marketing.IO.PhonesRawInputDTO;
import com.example.doordash_marketing.service.PhoneInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MarketingController {

    @Autowired
    private PhoneInfoService phoneInfoService;

    @PostMapping(path = "/phone_numbers", produces = "application/json")
    public List<PhoneInfoDTO> uploadPhoneNumbers(@RequestBody @Valid PhonesRawInputDTO input) {
        return phoneInfoService.saveData(input);
    }
}
