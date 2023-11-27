package fr.diginamic.springdemo.controleurs;

import fr.diginamic.springdemo.entites.Ville;
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
        villes.add(new Ville(1, "Nice", 343000));
        villes.add(new Ville(2, "Carcassonne", 47800));
        villes.add(new Ville(3, "Narbonne", 53400));
        villes.add(new Ville(4, "Lyon", 484000));
        villes.add(new Ville(5, "Foix", 9700));
        villes.add(new Ville(6, "Pau", 77200));
    }

    @GetMapping
    public List<Ville> getVilles() {
        // méthode GET qui permet de retrouver toutes les villes
        return villes;
    }

    @GetMapping("/search")
    public Ville getVille(@RequestParam String nomVille) {
        // méthode GET qui permet de retrouver une ville à partir de son nom
        for (Ville v : villes) {
            if (v.getNom().equals(nomVille)) {
                return v;
            }
        }
        return null;
    }

    @GetMapping("/{id}")
    public Ville getVilleById(@PathVariable int id) {
        // méthode GET qui permet de retrouver une ville à partir de son id
        for (Ville v : villes) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    @PutMapping
    public ResponseEntity<String> insererVille(@RequestBody Ville nvVille) {
        // méthode PUT qui ne crée une ville que si l’identifiant n'existe pas déjà
        if (nvVille.getId() == 0) {
            return ResponseEntity.badRequest().body("Identifiant incorrect");
        }
        for (Ville v : villes) {
            if (v.getId() == nvVille.getId()) {
                return ResponseEntity.badRequest().body("Ville existe déjà");
            }
        }
        villes.add(nvVille);
        return ResponseEntity.ok("Ville ajoutée avec succes");

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
    public ResponseEntity<String> modifierVilleById(@PathVariable int id, @RequestBody Ville nvVille) {
        // méthode POST qui modifie une ville à partir de son id
        if (id <= 0) {
            return ResponseEntity.badRequest().body("Identifiant incorrect");
        }
        for (Ville v : villes) {
            System.out.println("ici");
            if (v.getId() == id) {
                v.setNom(nvVille.getNom());
                v.setNbHabitants(nvVille.getNbHabitants());
                return ResponseEntity.ok("Ville modifiée avec succes");
            }
        }
        return ResponseEntity.badRequest().body("Ville id=" + id + " non trouvée");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteVilleById(@PathVariable int id, @RequestBody Ville nvVille) {
        // méthode DELETE qui supprime une ville à partir de son id
        if (nvVille.getId() == 0) {
            return ResponseEntity.badRequest().body("Identifiant incorrect");
        }
        Iterator<Ville> iter = villes.iterator();
        while (iter.hasNext()) {
            Ville ville = iter.next();
            if (ville.getId() == id) {
                iter.remove();
                return ResponseEntity.ok("Ville supprimée avec succes");
            }
        }
        return ResponseEntity.badRequest().body("Ville id=" + id + " non trouvée");
    }
}
