package Piece;

import Jeu.Couleur;

public class Reine extends Piece{

    // Attributs
    private static final String nom = "Reine";
    private static final int valeur = 9;

    // Constructeur
    public Reine(Couleur couleur){ super(couleur);}

    // Getters
    @Override
    public String getNomPiece(){ return nom;}
    @Override
    public int getValeurPiece(){ return valeur;}

    // Méthode de clonage
    @Override
    public Object clone(){
        return new Reine(this.couleur_piece);
    }
}
