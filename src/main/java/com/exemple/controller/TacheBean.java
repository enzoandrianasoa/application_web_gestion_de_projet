/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exemple.controller;

import com.exemple.entites.Projet;
import com.exemple.entites.Tache;
import com.exemple.services.TacheService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.ejb.EJB;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class TacheBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private TacheService tacheService;

    private Projet projetSelectionne;
    private Tache nouvelleTache = new Tache();
    private List<Tache> tachesDuProjet;

    /**
     * Méthode appelée depuis le tableau des projets pour ouvrir l'espace des tâches
     */
    public String voirTaches(Projet projet) {
        if (projet == null) {
            return "dashboard?faces-redirect=true";
        }
        this.projetSelectionne = projet;
        rafraichirListe();
        return "tache?faces-redirect=true"; // Mis au singulier pour correspondre au fichier
    }

    public void rafraichirListe() {
        if (projetSelectionne != null) {
            tachesDuProjet = tacheService.recupererTachesProjet(projetSelectionne.getId());
        }
    }

    public void enregistrer() {
        nouvelleTache.setProjet(projetSelectionne);
        tacheService.creerTache(nouvelleTache);
        nouvelleTache = new Tache(); // Réinitialise le formulaire
        rafraichirListe();
    }

    public void changerStatut(Tache tache, String nouveauStatut) {
    // JSF a déjà mis à jour l'objet 'tache' avec le nouveau statut sélectionné dans le menu.
    // On l'envoie directement au service pour mise à jour en BDD.
    tacheService.mettreAJourTache(tache);
    rafraichirListe(); 
    }   
    public void supprimer(Long id) {
        tacheService.supprimerTache(id);
        rafraichirListe();
    }

    // Getters et Setters
    public Projet getProjetSelectionne() { return projetSelectionne; }
    public Tache getNouvelleTache() { return nouvelleTache; }
    public void setNouvelleTache(Tache nouvelleTache) { this.nouvelleTache = nouvelleTache; }
    public List<Tache> getTachesDuProjet() { return tachesDuProjet; }
}
