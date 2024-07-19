package Piece;

import Jeu.Couleur;

public class Fou extends Piece{

    // Attributs
    private static final String nom = "Fou";
    private static final int valeur = 3;

    // Constructeur
    public Fou(Couleur couleur){ super(couleur);}

    // Getters
    @Override
    public String getNomPiece(){ return nom;}
    @Override
    public int getValeurPiece(){ return valeur;}

    // Méthode de clonage
    @Override
    public Object clone(){
        return new Fou(this.couleur_piece);
    }
}
