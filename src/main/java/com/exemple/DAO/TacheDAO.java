/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exemple.DAO;

import com.exemple.entites.Tache;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TacheDAO {

    @PersistenceContext(unitName = "gestionProjetPU")
    private EntityManager em;

    public void sauvegarder(Tache tache) {
        em.persist(tache);
    }

    public List<Tache> listerParProjet(Long projetId) {
        return em.createNamedQuery("Tache.findByProjet", Tache.class)
                 .setParameter("projetId", projetId)
                 .getResultList();
    }

    public void modifier(Tache tache) {
        em.merge(tache);
    }

    public void supprimer(Long id) {
        Tache tache = em.find(Tache.class, id);
        if (tache != null) {
            em.remove(tache);
        }
    }
}