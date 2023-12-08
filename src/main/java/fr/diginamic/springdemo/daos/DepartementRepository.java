package fr.diginamic.springdemo.daos;

import fr.diginamic.springdemo.entites.Departement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DepartementRepository extends CrudRepository<Departement, Integer> {

}

