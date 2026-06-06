package com.exemple.controller;

import com.exemple.entites.Projet;
import com.exemple.services.ProjetService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped; // Passé en SessionScoped pour maintenir l'état d'édition
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped // Obligatoire pour mémoriser quelle ligne est en train d'être modifiée
public class ProjetBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private ProjetService projetService;

    @Inject
    private AuthBean authBean;

    private Projet nouveauProjet = new Projet();
    private List<Projet> mesProjets;

    // Map pour suivre quelles lignes sont en mode édition (Id du projet -> Vrai/Faux)
    private Map<Long, Boolean> modeEditionMap = new HashMap<>();

    @PostConstruct
    public void init() {
        rafraichirListe();
    }

    public void rafraichirListe() {
        if (authBean.isLoggedIn()) {
            mesProjets = projetService.recupererProjetsUtilisateur(authBean.getUserSession().getId());
        }
    }

    public String enregistrer() {
        nouveauProjet.setUtilisateur(authBean.getUserSession());
        projetService.creerProjet(nouveauProjet);
        nouveauProjet = new Projet(); // Vider le formulaire
        rafraichirListe();
        return "dashboard?faces-redirect=true";
    }

    // --- NOUVELLES FONCTIONNALITÉS ---

    /**
     * Active le mode édition pour une ligne spécifique
     */
    public void activerEdition(Projet projet) {
        modeEditionMap.put(projet.getId(), true);
    }

    /**
     * Sauvegarde les modifications de la ligne et ferme le mode édition
     */
    public void sauvegarderModification(Projet projet) {
        projetService.mettreAJourProjet(projet);
        modeEditionMap.put(projet.getId(), false); // Désactiver le mode édition
        rafraichirListe();
    }

    /**
     * Annule la modification en cours et recharge les données initiales
     */
    public void annulerEdition(Projet projet) {
        modeEditionMap.put(projet.getId(), false);
        rafraichirListe(); // Annule les saisies en rechargeant depuis la BDD
    }

    /**
     * Supprime définitivement un projet
     */
    public void supprimer(Long id) {
        projetService.supprimerProjet(id);
        rafraichirListe();
    }

    // Permet à la vue JSF de savoir si cette ligne précise est en mode édition
    public Map<Long, Boolean> getModeEditionMap() {
        return modeEditionMap;
    }

    // Getters et Setters standards
    public Projet getNouveauProjet() { return nouveauProjet; }
    public void setNouveauProjet(Projet nouveauProjet) { this.nouveauProjet = nouveauProjet; }
    public List<Projet> getMesProjets() { return mesProjets; }
}