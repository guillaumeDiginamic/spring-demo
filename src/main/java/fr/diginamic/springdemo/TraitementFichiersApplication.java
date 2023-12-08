package fr.diginamic.springdemo;

import fr.diginamic.springdemo.daos.VilleRepository;
import fr.diginamic.springdemo.entites.Departement;
import fr.diginamic.springdemo.entites.Ville;
import fr.diginamic.springdemo.services.DepartementService;
import fr.diginamic.springdemo.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static java.nio.file.Files.readAllLines;


@SpringBootApplication
public class TraitementFichiersApplication implements CommandLineRunner {
    @Autowired
    private VilleService villeService;
    @Autowired
    private DepartementService departementService;
    @Autowired
    private VilleRepository villeRepository;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TraitementFichiersApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        //récupération du fichier dans les ressources
        URL resource = getClass().getClassLoader().getResource("recensement.csv");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            Path pathFile = Paths.get(Objects.requireNonNull(resource).toURI());

            System.out.println("File " + pathFile + "exists : " + Files.exists(pathFile)
                    + " regularFile : " + Files.isRegularFile(pathFile)
                    + " directory : " + Files.isDirectory(pathFile));

            // Ouverture d'un stream vers le fichier
            List<String> lignes = readAllLines(pathFile);
            lignes.remove(0);
            for (String ligne : lignes) {
                String[] elements = ligne.split(";");

                //Récupèration du code du département
                Departement nvDepartement = new Departement();
                nvDepartement.setCode(elements[2]);

                // Si le département n'existe pas, on l'insère dans departement
                if (departementService.existDepartementByCode(nvDepartement.getCode())) {
                    System.out.println("département " + nvDepartement.getCode() + " n'exite pas, on l'insere");
                    departementService.insertDepartement(nvDepartement);
                }
                // Récupèration du departement ajouté
                Departement departement;
                departement = departementService.extractDepartementByCode(nvDepartement.getCode());

                //Récupèration des informations de la ville
                Ville nvVille = new Ville();
                nvVille.setNom(elements[6]);
                nvVille.setNbHabitants(Integer.parseInt(elements[9].replaceAll(" ", "")));
                // ajout de l'id du departement
                nvVille.setDepartement(departement);

                // Si la ville n'existe pas, on l'insère dans ville
                if (villeRepository.findByNom(nvVille.getNom()) == null) {
                    villeService.insertVille(nvVille);
                }
            }
        }
    }
}

