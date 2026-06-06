/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exemple.services;

import com.exemple.DAO.TacheDAO;
import com.exemple.entites.Tache;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class TacheService {

    @EJB
    private TacheDAO tacheDAO;

    public void creerTache(Tache tache) {
        tacheDAO.sauvegarder(tache);
    }

    public List<Tache> recupererTachesProjet(Long projetId) {
        return tacheDAO.listerParProjet(projetId);
    }

    public void mettreAJourTache(Tache tache) {
        tacheDAO.modifier(tache);
    }

    public void supprimerTache(Long id) {
        tacheDAO.supprimer(id);
    }
}