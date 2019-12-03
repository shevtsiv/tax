package me.shevtsiv.tax.persistance;

import me.shevtsiv.tax.persistance.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByName(String personName);
}
