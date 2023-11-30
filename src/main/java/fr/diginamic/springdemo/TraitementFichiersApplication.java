package fr.diginamic.springdemo;

import fr.diginamic.springdemo.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@SpringBootApplication
public class TraitementFichiersApplication implements CommandLineRunner {
	@Autowired
	private VilleService villeService;
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(TraitementFichiersApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	@Override public void run(String... args) throws Exception {
		/*	Path currentDirectoryPath = FileSystems.getDefault().getRootDirectories().;
		System.out.println("########"+currentDirectoryPath);
	Path pathFile = Paths.get("../resources/data/recensement.csv");
		List<String> lines = Files.readAllLines(pathFile, StandardCharsets.UTF_8);
		for (String line: lines) {
			System.out.println(line);
		}*/
	}
}
