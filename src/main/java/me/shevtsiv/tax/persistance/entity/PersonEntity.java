package me.shevtsiv.tax.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double money;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PropertyEntity> propertyEntity = new ArrayList<>();
    private boolean isEmployed;

    public PersonEntity(String name) {
        this.name = name;
    }
}
