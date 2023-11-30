package fr.diginamic.springdemo.daos;

import fr.diginamic.springdemo.entites.Ville;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VilleRepository extends CrudRepository<Ville, Integer> {
    Ville findByNom(String nom);
//Le nom de la ville doit être unique pour un département donné.
@Query("SELECT COUNT(v) > 0 FROM Ville v WHERE v.nom = :nom AND v.departement.code = :code")
boolean existsByNomAndCodeDepartement(String nom, String code);

// Plus grande ville d’un département
@Query("SELECT v FROM Ville v WHERE v.departement.nom = :nom order by v.nbHabitants desc limit 1")
Ville findPlusGrandeVilleDepartement(String nom);

//Villes ayant une population comprise entre un min et un max et qui appartiennent à un département donné.
@Query("SELECT v FROM Ville v " +
        "WHERE v.departement.nom = :nom and v.nbHabitants > :minHabitants and v.nbHabitants < :maxHabitants " +
        "order by v.nbHabitants desc")
Iterable<Ville> findVilleDepartementPopulation(String nom, Integer minHabitants, Integer maxHabitants);

}

