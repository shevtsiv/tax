package me.shevtsiv.tax.persistance;

import me.shevtsiv.tax.persistance.entity.TaxEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends CrudRepository<TaxEntity, String> {
}
