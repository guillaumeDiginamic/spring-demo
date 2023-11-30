package fr.diginamic.springdemo.controleurs;

import fr.diginamic.springdemo.daos.VilleRepository;
import fr.diginamic.springdemo.entites.Ville;
import fr.diginamic.springdemo.exceptions.AnomalieException;
import fr.diginamic.springdemo.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v2/villes")
public class VilleRepoControleur {


    private final List<Ville> villes = new ArrayList<>();
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private VilleService villeService;


    @GetMapping
    public Iterable<Ville> getVilles() {
        // méthode GET qui permet de retrouver toutes les villes
        return villeRepository.findAll();
    }

    @GetMapping("/search")
    public Ville getVille(@RequestParam String nomVille) {
        // méthode GET qui permet de retrouver une ville à partir de son nom
        return villeRepository.findByNom(nomVille);
    }

    @GetMapping("/{id}")
    public Ville getVilleById(@PathVariable int id) {
        // méthode GET qui permet de retrouver une ville à partir de son id
        return villeRepository.findById(id).get();
    }

    @GetMapping("/departement/{nomDep}/plusgrandeville")
    public Ville getplusGrandevilleDepartement(@PathVariable String nomDep) {
        // méthode GET qui permet Lister les n plus grandes villes d’un département
        return villeRepository.findPlusGrandeVilleDepartement(nomDep);
    }

    @GetMapping("/departement/{nomDep}/villedepartementpopulation")
    public Iterable<Ville> getVilleDepartementPopulation(@PathVariable String nomDep, @RequestParam Integer minHab, @RequestParam Integer maxHab) {
        // méthode GET qui permet Lister les n plus grandes villes d’un département
        return villeRepository.findVilleDepartementPopulation(nomDep, minHab, maxHab);
    }

    @PutMapping
    public Iterable<Ville> insererVille(@RequestBody Ville nvVille) throws AnomalieException {
        // méthode PUT qui ne crée une ville que si l’identifiant n'existe pas déjà

        if (nvVille.getNbHabitants() < 10) {
            throw new AnomalieException("Le nombre d'habitants doit être supèrieur à 10");
        }
        if (nvVille.getNom().length() < 2) {
            throw new AnomalieException("La ville doit avoir un nom contenant au moins 2 lettres");
        }
        if (villeRepository.existsByNomAndCodeDepartement(nvVille.getNom(), nvVille.getDepartement().getCode())) {
            throw new AnomalieException("La ville existe déjà dans le département");
        }
        villeRepository.save(nvVille);
        Iterable<Ville> villes = villeRepository.findAll();
        return villes;

    }

    @PostMapping("/modif/nom/{nomVille}")
    public Ville modifierVilleByNom(@PathVariable String nomVille, @RequestBody Ville modVille) throws AnomalieException {
        // méthode POST qui modifie une ville à partir de son nom
        if (modVille.getNbHabitants() < 10) {
            throw new AnomalieException("Le nombre d'habitants doit être supèrieur à 10");
        }
        if (modVille.getNom().length() < 2) {
            throw new AnomalieException("La ville doit avoir un nom contenant au moins 2 lettres");
        }
        if (villeRepository.existsByNomAndCodeDepartement(modVille.getNom(), modVille.getDepartement().getCode())) {
            throw new AnomalieException("La ville existe déjà dans le département");
        }
        Ville villeAModifie = villeRepository.findByNom(nomVille);
        if (villeAModifie != null) {
            villeAModifie.setNom(modVille.getNom());
            villeAModifie.setNbHabitants(modVille.getNbHabitants());
            villeAModifie.setDepartement(modVille.getDepartement());
        }
        return villeAModifie;
    }

    @PostMapping("/modif/{id}")
    public Iterable<Ville> modifierVilleById(@PathVariable int id, @RequestBody Ville modVille) throws AnomalieException {
        // méthode POST qui modifie une ville à partir de son id
        if (modVille.getNbHabitants() < 10) {
            throw new AnomalieException("Le nombre d'habitants doit être supèrieur à 10");
        }
        if (modVille.getNom().length() < 2) {
            throw new AnomalieException("La ville doit avoir un nom contenant au moins 2 lettres");
        }
        if (villeRepository.existsByNomAndCodeDepartement(modVille.getNom(), modVille.getDepartement().getCode())) {
            throw new AnomalieException("La ville existe déjà dans le département");
        }

        Ville villeAModifie = villeRepository.findById(id).get();
        if (villeAModifie != null) {
            villeAModifie.setNom(modVille.getNom());
            villeAModifie.setNbHabitants(modVille.getNbHabitants());
            villeAModifie.setDepartement(modVille.getDepartement());
        }
        return villeRepository.findAll();
    }

    @DeleteMapping("delete/{id}")
    public Iterable<Ville> deleteVilleById(@PathVariable int id) {
        // méthode DELETE qui supprime une ville à partir de son id
        villeRepository.deleteById(id);

        Iterable<Ville> villes = villeRepository.findAll();
        return villes;
    }
}
