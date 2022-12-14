package com.flag.flagapp.request;

import com.flag.flagapp.dto.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RequestToRestcountries {

    @Value("${URL}")
    private String FIRST_PART_URL;

    @Value("${LAST_PART}")
    private String LAST_PART_URL;

    RestTemplate restTemplate = new RestTemplate();

    @Cacheable("main_request")
    public List<Country> getCountryList(final String countiesCodes) {
        final String URL = FIRST_PART_URL + countiesCodes + LAST_PART_URL;
        final var responseEntity = restTemplate.getForEntity(URL, Country[].class);
        return Arrays
                .stream(Objects.requireNonNull(responseEntity.getBody()))
                .collect(Collectors.toList());
    }
}
