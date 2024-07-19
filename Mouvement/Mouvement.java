package Mouvement;

import Jeu.Couleur;

public abstract class Mouvement {

    // Attribut
    protected final boolean capture;

    // Constructeur
    public Mouvement(boolean capture){ this.capture = capture;}

    // Getter
    public boolean getCapture(){ return this.capture;}

    // Méthode abstraite
    public abstract Mouvement mouvementInverse();
    public abstract String getNom();
    public abstract String toString();

}
