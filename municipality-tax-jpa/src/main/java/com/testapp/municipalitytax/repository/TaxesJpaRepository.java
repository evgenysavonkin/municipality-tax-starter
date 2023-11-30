package com.testapp.municipalitytax.repository;

import com.testapp.municipalitytax.entity.TaxEntity;
import java.util.UUID;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxesJpaRepository
    extends CrudRepository<TaxEntity, UUID>, QuerydslPredicateExecutor<TaxEntity> {}
