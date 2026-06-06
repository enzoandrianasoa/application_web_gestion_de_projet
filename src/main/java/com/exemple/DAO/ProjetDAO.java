/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exemple.DAO;

import com.exemple.entites.Projet;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProjetDAO {

    @PersistenceContext(unitName = "gestionProjetPU")
    private EntityManager em;

    public void sauvegarder(Projet projet) {
        em.persist(projet);
    }

    public List<Projet> listerParUtilisateur(Long utilisateurId) {
        return em.createNamedQuery("Projet.findByUtilisateur", Projet.class)
                 .setParameter("userId", utilisateurId)
                 .getResultList();
    }
    public Projet modifier(Projet projet) {
    return em.merge(projet);
    }

    public void supprimer(Long id) {
        Projet projet = em.find(Projet.class, id);
        if (projet != null) {
            em.remove(projet);
        }
    }
}
