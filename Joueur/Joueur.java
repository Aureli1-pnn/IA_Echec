package Joueur;

import Jeu.Couleur;
import Mouvement.Mouvement;

import java.util.ArrayList;
import java.util.List;

public class Joueur {

    // Attribut
    private final String nom;
    private final boolean est_une_ia;
    private Couleur couleur_joueur;
    private final List<Mouvement> list_mouvement;

    // Constructeur
    public Joueur(String nom, boolean ia){
        this.nom = nom;
        this.est_une_ia = ia;
        this.couleur_joueur = Couleur.Blanc;
        this.list_mouvement = new ArrayList<>();
    }

    // Getters
    public String getNom(){ return this.nom;}
    public boolean getEstUneIa(){ return this.est_une_ia;}
    public Couleur getCouleur(){ return this.couleur_joueur;}
    public List<Mouvement> getListMouvement(){ return this.list_mouvement;}

    // Méthode
    public void reinitialiserJoueur(Couleur couleur){
        this.couleur_joueur = couleur;
        this.list_mouvement.clear();
    }

    // Setters
    public void setCouleurJoueur(Couleur couleur){ this.couleur_joueur = couleur;}
    public void ajouterMouvement(Mouvement mouvement){ this.list_mouvement.add(mouvement);}
}
