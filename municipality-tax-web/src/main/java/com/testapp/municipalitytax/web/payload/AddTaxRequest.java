package com.testapp.municipalitytax.web.payload;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record AddTaxRequest(String municipality, Double tax, String startDate, String schedule) implements UUIDGeneratable{
    @Override
    public UUID generateUUID() {
        String combinedValues = municipality + tax + startDate + schedule;
        byte[] bytes = combinedValues.getBytes(StandardCharsets.UTF_8);
        return UUID.nameUUIDFromBytes(bytes);
    }
}
