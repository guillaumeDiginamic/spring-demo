package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entites.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VilleService {
    @PersistenceContext
    private EntityManager em;

    public List<Ville> extractVilles() {
        // extrait et retourne les villes qui sont en base.
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v", Ville.class);
        return query.getResultList();
    }

    public Ville extractVille(int idVille) {
        // extrait la ville dont l’id est passé en paramètre.
        return em.find(Ville.class, idVille);
    }

    public Ville extractVille(String nom) {
        // extrait la ville dont le nom est passé en paramètre
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.nom = :nom", Ville.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }

    public Ville plusGrandevilleDepartement(String nomDep) {
        // extrait la plus grande ville d’un département
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.nom = :nom order by v.nbHabitants desc", Ville.class);
        query.setParameter("nom", nomDep);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return query.getResultList().get(0);
    }

    public List<Ville> villeDepartementPopulation(String nomDep, int minHabitant, int maxHabitant) {
        // extrait les villes ayant une population comprise entre un min et un max
        // et qui appartiennent à un département donné.
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.nom = :nom order by v.nbHabitants desc", Ville.class);
        query.setParameter("nom", nomDep);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        List<Ville> villes = new ArrayList<>();
        List<Ville> villesFound = new ArrayList<>();
        villes = query.getResultList();

        for (Ville ville : villes) {
            if (ville.getNbHabitants() > minHabitant && ville.getNbHabitants() < maxHabitant) {
                villesFound.add(ville);
            }
        }
        return villesFound;
    }

    @Transactional
    public List<Ville> insertVille(Ville nvVille) {
        // insère une nouvelle ville en base et retourne la liste des villes après insertion
        em.persist(nvVille);
        return extractVilles();
    }

    @Transactional
    public List<Ville> modifierVille(int idVille, Ville villeModifiee) {
        // modifie la ville dont l’identifiant est passé en paramètre.
        // Les nouvelles données sont portées par l’instance villeModifiee.
        // La méthode retourne la liste des villes après modification
        Ville villeFromDB = em.find(Ville.class, idVille);
        if (villeFromDB != null) {
            villeFromDB.setNom(villeModifiee.getNom());
            villeFromDB.setNbHabitants(villeModifiee.getNbHabitants());
        }
        return extractVilles();
    }

    @Transactional
    public List<Ville> supprimerVille(int idVille) {
        // supprime la ville dont ‘id est passé en paramètre
        // et retourne la liste des villes après suppression.
        Ville villeFromDB = em.find(Ville.class, idVille);
        if (villeFromDB != null) {
            em.remove(villeFromDB);
        }
        return extractVilles();
    }

}


