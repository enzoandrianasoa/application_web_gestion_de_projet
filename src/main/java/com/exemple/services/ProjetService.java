/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exemple.services;

import com.exemple.DAO.ProjetDAO;
import com.exemple.entites.Projet;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class ProjetService {

    @EJB
    private ProjetDAO projetDAO;

    public void creerProjet(Projet projet) {
        projetDAO.sauvegarder(projet);
    }

    public List<Projet> recupererProjetsUtilisateur(Long utilisateurId) {
        return projetDAO.listerParUtilisateur(utilisateurId);
    }
    
    public void mettreAJourProjet(Projet projet) {
    projetDAO.modifier(projet);
    }

    public void supprimerProjet(Long id) {
        projetDAO.supprimer(id);
    }
}