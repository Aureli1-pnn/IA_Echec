package Piece;

import Jeu.Couleur;

public class Tour extends Piece{

    // Attributs
    private static final String nom = "Tour";
    private static final int valeur = 5;

    // Constructeur
    public Tour(Couleur couleur){ super(couleur);}

    // Getters
    @Override
    public String getNomPiece(){ return nom;}
    @Override
    public int getValeurPiece(){ return valeur;}

    // Méthode de clonage
    @Override
    public Object clone(){
        return new Tour(this.couleur_piece);
    }
}
