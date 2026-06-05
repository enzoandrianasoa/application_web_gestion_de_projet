/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exemple.controller;

import com.exemple.entites.Utilisateur;
import com.exemple.services.UtilisateurService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private UtilisateurService utilisateurService;

    // Propriétés reliées à l'interface de connexion
    private String email;
    private String password;

    // Propriété reliée à l'interface d'inscription
    private Utilisateur utilisateur = new Utilisateur();

    // Conteneur de l'utilisateur authentifié
    private Utilisateur userSession;

    /**
     * Action déclenchée par le bouton "Se connecter"
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Le contrôleur appelle le service (qui passe par le DAO et l'EntityManager)
        userSession = utilisateurService.authentifier(email, password);

        if (userSession != null) {
            // Authentification validée -> Redirection vers l'espace sécurisé
            return "dashboard?faces-redirect=true";
        } else {
            // Échec -> Pas de session accordée, message d'erreur
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Email ou mot de passe incorrect.", null));
            return null; // Reste sur la page de connexion
        }
    }

    /**
     * Action déclenchée par le bouton "S'inscrire"
     */
    public String register() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean succes = utilisateurService.inscrire(utilisateur, password);

        if (succes) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Inscription réussie ! Vous pouvez maintenant vous connecter.", null));
            utilisateur = new Utilisateur(); // Réinitialisation de l'objet pour vider le formulaire
            return "connexion?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Une erreur est survenue. Cet email est probablement déjà pris.", null));
            return null;
        }
    }

    /**
     * Action de déconnexion
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "connexion?faces-redirect=true";
    }

    // Vérification rapide pour l'affichage conditionnel dans la vue
    public boolean isLoggedIn() {
        return userSession != null;
    }

    // Getters et Setters obligatoires pour JSF
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public Utilisateur getUserSession() { return userSession; }
}