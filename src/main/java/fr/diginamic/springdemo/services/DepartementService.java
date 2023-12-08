package fr.diginamic.springdemo.services;

import fr.diginamic.springdemo.entites.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {
    @PersistenceContext
    private EntityManager em;

    public List<Departement> extractDepartements() {
        // extrait et retourne les Departements qui sont en base.
        TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d", Departement.class);
        return query.getResultList();
    }

    public Departement extractDepartement(int idDepartement) {
        // extrait le Departement dont l’id est passé en paramètre.
        return em.find(Departement.class, idDepartement);
    }

    public Departement extractDepartement(String nom) {
        // extrait le Departement dont le nom est passé en paramètre
        TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }

    public boolean existDepartementByCode(String code) {
        // extrait le Departement dont le nom est passé en paramètre
        TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d WHERE d.code = :code GROUP BY d.code", Departement.class);
        query.setParameter("code", code);
        return query.getResultList().isEmpty();

    }
    public Departement extractDepartementByCode(String code) {
        // extrait le Departement dont le nom est passé en paramètre
        TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d WHERE d.code = :code GROUP BY d.code", Departement.class);
        query.setParameter("code", code);
        return query.getSingleResult();

    }


    @Transactional
    public List<Departement> insertDepartement(Departement nvDepartement) {
        // insère un nouveau Departement en base et retourne la liste des Departements après insertion
        em.persist(nvDepartement);
        return extractDepartements();
    }

    @Transactional
    public List<Departement> modifierDepartement(int idDepartement, Departement DepartementModifiee) {
        // modifie le Departement dont l’identifiant est passé en paramètre.
        // Les nouvelles données sont portées par l’instance DepartementModifiee.
        // La méthode retourne la liste des Departements après modification
        Departement DepartementFromDB = em.find(Departement.class, idDepartement);
        if (DepartementFromDB != null) {
            DepartementFromDB.setNom(DepartementModifiee.getNom());
            DepartementFromDB.setCode(DepartementModifiee.getCode());
        }
        return extractDepartements();
    }

    @Transactional
    public List<Departement> supprimerDepartement(int idDepartement) {
        // supprime le Departement dont ‘id est passé en paramètre
        // et retourne la liste des Departements après suppression.
        Departement DepartementFromDB = em.find(Departement.class, idDepartement);
        if (DepartementFromDB != null) {
            em.remove(DepartementFromDB);
        }
        return extractDepartements();
    }

}


