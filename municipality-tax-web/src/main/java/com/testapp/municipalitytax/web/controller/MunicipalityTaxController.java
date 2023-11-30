package com.testapp.municipalitytax.web.controller;

import com.testapp.municipalitytax.web.TaxesService;
import com.testapp.municipalitytax.web.payload.*;
import com.testapp.municipalitytax.web.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/taxes")
@Validated
public class MunicipalityTaxController {

    private final TaxesService taxesService;

    @Autowired
    public MunicipalityTaxController(TaxesService taxesService) {
        this.taxesService = taxesService;
    }

    /**
     * Adds new municipality tax record
     *
     * @param addTaxRequest body municipality is case-sensitive schedule is case-insensitive date is
     *                      accepted in format yyyy.mm.dd tax is between 0 and 1
     * @return UUID of created record
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    UUIDResponse addMunicipalityTax(@RequestBody AddTaxRequest addTaxRequest) {
        if (addTaxRequest == null) {
            throw new TaxRequestIsNullException();
        }

        if (!ScheduleDateValidator.isTaxCorrect(addTaxRequest.tax())) {
            throw new TaxRequestIncorrectTaxFieldException();
        }

        if (ScheduleDateValidator.isDateCorrect(addTaxRequest.startDate())) {
            taxesService.addTax(addTaxRequest);
            return new UUIDResponse(addTaxRequest.generateUUID());
        }

        String newStartDate = ScheduleDateValidator.getDateFormat(addTaxRequest.startDate());
        AddTaxRequest corrTaxRequest = new AddTaxRequest(addTaxRequest.municipality(), addTaxRequest.tax(), newStartDate, addTaxRequest.schedule());
        taxesService.addTax(corrTaxRequest);
        return new UUIDResponse(corrTaxRequest.generateUUID());
    }

    /**
     * Edit municipality tax values by id
     *
     * @param taxId            UUID
     * @param updateTaxRequest body schedule is case-insensitive date is accepted in format yyyy.mm.dd
     *                         tax is between 0 and 1
     * @return UUID of created record
     */
    @PutMapping(value = "/{taxId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UUIDResponse updateMunicipalityTax(
            @PathVariable("taxId") UUID taxId, @RequestBody UpdateTaxRequest updateTaxRequest) {
        if (updateTaxRequest == null) {
            throw new TaxRequestIsNullException();
        }

        if (!ScheduleDateValidator.isTaxCorrect(updateTaxRequest.tax())) {
            throw new TaxRequestIncorrectTaxFieldException();
        }

        if (ScheduleDateValidator.isDateCorrect(updateTaxRequest.startDate())) {
            taxesService.updateTax(taxId, updateTaxRequest);
            return new UUIDResponse(updateTaxRequest.generateUUID());
        }

        String newStartDate = ScheduleDateValidator.getDateFormat(updateTaxRequest.startDate());
        UpdateTaxRequest corrUpdateTax = new UpdateTaxRequest(updateTaxRequest.tax(), newStartDate, updateTaxRequest.schedule());
        taxesService.updateTax(taxId, corrUpdateTax);
        return new UUIDResponse(updateTaxRequest.generateUUID());
    }

    /**
     * Find municipality tax record by municipality and date
     *
     * @param municipality case-sensitive
     * @param date         accepted in format yyyy.mm.dd
     * @return TaxResponse list of taxes applied with chosen municipality and date
     */
    @GetMapping(value = "/{municipality}/{date}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    TaxResponse findMunicipalityTax(
            @PathVariable("municipality") String municipality, @PathVariable("date") String date) {
        if (municipality == null || municipality.isEmpty() && date == null || date.isEmpty()) {
            throw new MunicipalityTaxFindException();
        }
        String corrDate = ScheduleDateValidator.getDateFormat(date);
        return taxesService.findTax(municipality, corrDate);
    }

    /**
     * Find all municipality taxes
     *
     * @return TaxResponse list of all taxes in all municipalities
     */
    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    TaxListResponse getAllMunicipalityTaxes() {
        return taxesService.getAllMunicipalityTaxes();
    }

    @ExceptionHandler
    private ResponseEntity<TaxRequestErrorResponse> handleNullTax(TaxRequestIsNullException e) {
        TaxRequestErrorResponse response = new TaxRequestErrorResponse(
                "Tax request is null",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<TaxRequestErrorResponse> handleIncorrectTaxField(TaxRequestIncorrectTaxFieldException e) {
        TaxRequestErrorResponse response = new TaxRequestErrorResponse(
                "Tax parameter should be between 0 and 1",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<MunicipalityErrorResponse> handleIncorrectMunicipalityCredentials(MunicipalityTaxFindException e) {
        MunicipalityErrorResponse response = new MunicipalityErrorResponse(
                "Params municipality and date are null or empty",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
