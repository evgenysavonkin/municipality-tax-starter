package com.testapp.municipalitytax.service;

import com.testapp.municipalitytax.domain.TaxesRepository;
import com.testapp.municipalitytax.web.TaxesService;
import com.testapp.municipalitytax.web.payload.*;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class MunicipalityTaxService implements TaxesService {

  private final TaxesRepository taxesRepository;
  private final ConversionService conversionService;

  @Autowired
  public MunicipalityTaxService(TaxesRepository taxesRepository,
                                ConversionService conversionService) {
    this.taxesRepository = taxesRepository;
    this.conversionService = conversionService;
  }

  @Override
  public UUIDResponse addTax(AddTaxRequest addTaxRequest) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void updateTax(UUID taxId, UpdateTaxRequest updateTaxRequest) {
    throw new UnsupportedOperationException();
  }

  @Override
  public TaxResponse findTax(String municipality, String date) {
    throw new UnsupportedOperationException();
  }

  @Override
  public TaxListResponse getAllMunicipalityTaxes() {
    throw new UnsupportedOperationException();
  }
}
