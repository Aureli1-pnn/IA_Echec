package Piece;

import Jeu.Couleur;

public class Pion extends Piece{

    // Attributs
    private static final String nom = "Pion";
    private static final int valeur = 1;

    // Constructeur
    public Pion(Couleur couleur){ super(couleur);}

    // Getters
    @Override
    public String getNomPiece(){ return nom;}
    @Override
    public int getValeurPiece(){ return valeur;}

    // Méthode de clonage
    @Override
    public Object clone(){
        return new Pion(this.couleur_piece);
    }
}
