package fr.diginamic.springdemo.controleurs;

import fr.diginamic.springdemo.entites.Ville;
import fr.diginamic.springdemo.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {
    private List<Ville> villes = new ArrayList<>();

    public VilleControleur() {
    }

    @Autowired
    private VilleService villeService;


    @GetMapping
    public List<Ville> getVilles() {
        // méthode GET qui permet de retrouver toutes les villes
        return villeService.extractVilles();
    }

    @GetMapping("/search")
    public Ville getVille(@RequestParam String nomVille) {
        // méthode GET qui permet de retrouver une ville à partir de son nom
        return villeService.extractVille(nomVille);
    }

    @GetMapping("/{id}")
    public Ville getVilleById(@PathVariable int id) {
        // méthode GET qui permet de retrouver une ville à partir de son id
        return villeService.extractVille(id);
    }

    @GetMapping("/departement/{nomDep}/plusgrandeville")
    public Ville getplusGrandevilleDepartement(@PathVariable String nomDep) {
        // méthode GET qui permet Lister les n plus grandes villes d’un département
        return villeService.plusGrandevilleDepartement(nomDep);
    }

    @GetMapping("/departement/{nomDep}/villeDepartementPopulation")
    public List<Ville> getVilleDepartementPopulation(@PathVariable String nomDep, @RequestParam int min, @RequestParam int max) {
        // méthode GET qui permet Lister les n plus grandes villes d’un département
        return villeService.villeDepartementPopulation(nomDep, min, max);
    }

    @PutMapping
    public List<Ville> insererVille(@RequestBody Ville nvVille) {
        // méthode PUT qui ne crée une ville que si l’identifiant n'existe pas déjà
        return villeService.insertVille(nvVille);
    }

    @PostMapping("/modif/nom/{nomVille}")
    public ResponseEntity<String> modifierVilleByNom(@PathVariable String nomVille, @RequestBody Ville nvVille) {
        // méthode POST qui modifie une ville à partir de son nom
        if (nomVille.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Identifiant incorrect");
        }
        for (Ville v : villes) {
            if (v.getNom().equals(nomVille)) {
                v.setNom(nvVille.getNom());
                v.setNbHabitants(nvVille.getNbHabitants());
                return ResponseEntity.ok("Ville modifiée avec succes");
            }
        }
        return ResponseEntity.badRequest().body("Ville " + nomVille + " non trouvée");
    }

    @PostMapping("/modif/{id}")
    public List<Ville> modifierVilleById(@PathVariable int id, @RequestBody Ville nvVille) {
        // méthode POST qui modifie une ville à partir de son id
        return villeService.modifierVille(id, nvVille);
    }

    @DeleteMapping("delete/{id}")
    public List<Ville> deleteVilleById(@PathVariable int id) {
        // méthode DELETE qui supprime une ville à partir de son id
        return villeService.supprimerVille(id);
    }
}
