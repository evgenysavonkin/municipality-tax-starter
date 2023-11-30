package com.testapp.municipalitytax.domain;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


public interface TaxesRepository {

  MunicipalityTax save(MunicipalityTax municipalityTax);

  int update(MunicipalityTax municipalityTax);

  List<MunicipalityTax> findByMunicipalityAndDate(String municipality, LocalDate date);

  List<MunicipalityTax> getAllMunicipalityTaxes();
}
