package com.testapp.municipalitytax.entity;

import java.time.LocalDate;
import java.util.UUID;

public class TaxEntity {

  public TaxEntity(
      UUID id, String municipality, Double tax, LocalDate startDate, LocalDate endDate) {}

  public TaxEntity(TaxEntity taxEntity, String municipality) {}

}
