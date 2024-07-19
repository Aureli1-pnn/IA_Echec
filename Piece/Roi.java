package Piece;

import Jeu.Couleur;

public class Roi extends Piece{

    // Attributs
    private static final String nom = "Roi";
    private static final int valeur = 0;

    // Constructeur
    public Roi(Couleur couleur){ super(couleur);}

    // Getters
    @Override
    public String getNomPiece(){ return nom;}
    @Override
    public int getValeurPiece(){ return valeur;}

    // Méthode de clonage
    @Override
    public Object clone(){
        return new Roi(this.couleur_piece);
    }
}