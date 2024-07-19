package Mouvement;

import Jeu.Couleur;

import java.util.Objects;

public class Roque extends Mouvement{

    // Attributs
    private static final String nom = "Roque";
    private final String type_roque;
    private final boolean inversion;
    private final Couleur couleur;

    // Constructeur
    public Roque(Couleur couleur, boolean est_petit_roque, boolean inversion){
        super(false);
        this.type_roque = est_petit_roque ? "Petit" : "Grand";
        this.inversion = inversion;
        this.couleur = couleur;
    }

    // Getters
    @Override
    public String getNom(){ return nom;}
    public String getTypeDeRoque(){ return this.type_roque;}
    public boolean getInversion(){ return this.inversion;}
    public Couleur getCouleur(){ return this.couleur;}

    // Méthode à Override
    @Override
    public Mouvement mouvementInverse(){
        if(Objects.equals(this.type_roque, "Petit")){
            return new Roque(this.couleur, true, !this.inversion);
        }
        return new Roque(this.couleur, false, !this.inversion);
    }
    @Override
    public String toString() {
        if(this.inversion){
            return "Inverser " + this.type_roque + " Roque";
        }
        return this.type_roque + " Roque";
    }
}
