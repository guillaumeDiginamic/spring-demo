package fr.diginamic.springdemo.controleurs;

import fr.diginamic.springdemo.entites.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {
    @GetMapping
    public List<Ville> getVilles() {
        List<Ville> villes = new ArrayList<>();
        villes.add(new Ville("Nice",343000));
        villes.add(new Ville("Carcassonne",47800));
        villes.add(new Ville("Narbonne",53400));
        villes.add(new Ville("Lyon",484000));
        villes.add(new Ville("Foix",9700));
        villes.add(new Ville("Pau",77200));

        return villes;
    }
}
