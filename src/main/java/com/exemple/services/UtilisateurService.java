/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exemple.services;

import com.exemple.DAO.UtilisateurDAO;
import com.exemple.entites.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class UtilisateurService {

    // Injection du DAO
    @EJB
    private UtilisateurDAO utilisateurDAO;

    public boolean inscrire(Utilisateur utilisateur, String motDePasseClair) {
        try {
            // Logique métier : Sécurisation du mot de passe
            String hash = BCrypt.hashpw(motDePasseClair, BCrypt.gensalt());
            utilisateur.setPassword(hash);
            
            // Appel au DAO pour l'ordre SQL INSERT
            utilisateurDAO.creer(utilisateur);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Utilisateur authentifier(String email, String motDePasseClair) {
        // Appel au DAO pour l'ordre SQL SELECT
        Utilisateur u = utilisateurDAO.trouverParEmail(email);

        // Logique métier : Vérification du hash
        if (u != null && BCrypt.checkpw(motDePasseClair, u.getPassword())) {
            return u; // Authentification réussie
        }
        return null; // Échec
    }
}