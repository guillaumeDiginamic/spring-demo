package fr.diginamic.springdemo.controleurs;

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
    public List<Departement> insererDepartement(@RequestBody Departement nvDepartement) {
        // méthode PUT qui ne crée une Departement que si l’identifiant n'existe pas déjà
       return DepartementService.insertDepartement(nvDepartement);


    }
    @PostMapping("/modif/nom/{nomDepartement}")
    public ResponseEntity<String> modifierDepartementByNom(@PathVariable String nomDepartement, @RequestBody Departement nvDepartement) {
        // méthode POST qui modifie une Departement à partir de son nom
        if (nomDepartement.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Identifiant incorrect");
        }
        for (Departement v : departements) {
            if (v.getNom().equals(nomDepartement)) {
                v.setNom(nvDepartement.getNom());
                v.setCode(nvDepartement.getCode());
                return ResponseEntity.ok("Departement modifiée avec succes");
            }
        }
        return ResponseEntity.badRequest().body("Departement " + nomDepartement + " non trouvée");
    }

    @PostMapping("/modif/{id}")
    public List<Departement> modifierDepartementById(@PathVariable int id, @RequestBody Departement nvDepartement) {
        // méthode POST qui modifie une Departement à partir de son id
        /*if (id <= 0) {
            return ResponseEntity.badRequest().body("Identifiant incorrect");
        }*/
        return DepartementService.modifierDepartement(id,nvDepartement);
        //return ResponseEntity.badRequest().body("Departement id=" + id + " non trouvée");
    }

    @DeleteMapping("delete/{id}")
    public List<Departement> deleteDepartementById(@PathVariable int id) {
        // méthode DELETE qui supprime une Departement à partir de son id
        /*if (nvDepartement.getId() == 0) {
            return ResponseEntity.badRequest().body("Identifiant incorrect");
        }*/
        return DepartementService.supprimerDepartement(id);
       // return ResponseEntity.badRequest().body("Departement id=" + id + " non trouvée");
    }
}
