package fr.diginamic.springdemo.controleurs;

import fr.diginamic.springdemo.exceptions.AnomalieException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.diginamic.springdemo.entites.Departement;
import fr.diginamic.springdemo.services.DepartementService;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/departement")
public class DepartementControleur {
    private List<Departement> departements = new ArrayList<>();

    public DepartementControleur() {
    }

    @Autowired
    private DepartementService DepartementService;


    @GetMapping
    public List<Departement> getDepartements() {
        // méthode GET qui permet de retrouver toutes les Departements
        return DepartementService.extractDepartements();
    }

    @GetMapping("/search")
    public Departement getDepartement(@RequestParam String nomDepartement) {
        // méthode GET qui permet de retrouver une Departement à partir de son nom
        return DepartementService.extractDepartement(nomDepartement);
    }

    @GetMapping("/{id}")
    public Departement getDepartementById(@PathVariable int id) {
        // méthode GET qui permet de retrouver une Departement à partir de son id
        return DepartementService.extractDepartement(id);
    }

    @PutMapping
    public List<Departement> insererDepartement(@RequestBody Departement nvDepartement) throws AnomalieException {
        // méthode PUT qui ne crée une Departement que si l’identifiant n'existe pas déjà
        if (nvDepartement.getCode().length() < 2) {
            throw new AnomalieException("Le code département doit obligatoire 2 caractères");
        }
        return DepartementService.insertDepartement(nvDepartement);


    }

    @PostMapping("/modif/nom/{nomDepartement}")
    public ResponseEntity<String> modifierDepartementByNom(@PathVariable String nomDepartement, @RequestBody Departement modDepartement) throws AnomalieException {
        // méthode POST qui modifie une Departement à partir de son nom
        if (nomDepartement.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Identifiant incorrect");
        }
        if (modDepartement.getCode().length() < 2) {
            throw new AnomalieException("Le code département doit obligatoire 2 caractères");
        }

        for (Departement v : departements) {
            if (v.getNom().equals(nomDepartement)) {
                v.setNom(modDepartement.getNom());
                v.setCode(modDepartement.getCode());
                return ResponseEntity.ok("Departement modifiée avec succes");
            }
        }
        return ResponseEntity.badRequest().body("Departement " + nomDepartement + " non trouvée");
    }

    @PostMapping("/modif/{id}")
    public List<Departement> modifierDepartementById(@PathVariable int id, @RequestBody Departement modDepartement) throws AnomalieException {
        // méthode POST qui modifie une Departement à partir de son id
        if (modDepartement.getCode().length() < 2) {
            throw new AnomalieException("Le code département doit obligatoire 2 caractères");
        }
        return DepartementService.modifierDepartement(id, modDepartement);

    }

    @DeleteMapping("delete/{id}")
    public List<Departement> deleteDepartementById(@PathVariable int id) {
        // méthode DELETE qui supprime une Departement à partir de son id
        return DepartementService.supprimerDepartement(id);

    }
}
