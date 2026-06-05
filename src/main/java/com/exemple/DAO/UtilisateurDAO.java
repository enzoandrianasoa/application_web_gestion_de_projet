/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exemple.DAO;

import com.exemple.entites.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import java.util.List;

@Stateless
public class UtilisateurDAO {

    // L'injection de l'EntityManager lié à ton fichier persistence.xml
    @PersistenceContext(unitName = "gestionProjetPU")
    private EntityManager em;

    /**
     * Traduction SQL : INSERT INTO utilisateur (...) VALUES (...)
     */
    public void creer(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }

    /**
     * Traduction SQL : SELECT * FROM utilisateur WHERE email = ?
     */
    public Utilisateur trouverParEmail(String email) {
        try {
            return em.createNamedQuery("Utilisateur.findByEmail", Utilisateur.class)
                     .setParameter("email", email)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retourne null si aucun utilisateur n'a cet email
        }
    }

    /**
     * Traduction SQL : SELECT * FROM utilisateur WHERE id = ?
     */
    public Utilisateur trouverParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    /**
     * Traduction SQL : SELECT * FROM utilisateur
     */
    public List<Utilisateur> listerTout() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    /**
     * Traduction SQL : UPDATE utilisateur SET ... WHERE id = ?
     */
    public Utilisateur modifier(Utilisateur utilisateur) {
        return em.merge(utilisateur);
    }

    /**
     * Traduction SQL : DELETE FROM utilisateur WHERE id = ?
     */
    public void supprimer(Long id) {
        Utilisateur u = trouverParId(id);
        if (u != null) {
            em.remove(u);
        }
    }
}
