package Piece;

import Jeu.Couleur;

public class Cavalier extends Piece{

    // Attributs
    private static final String nom = "Cavalier";
    private static final int valeur = 3;

    // Constructeur
    public Cavalier(Couleur couleur){ super(couleur);}

    // Getters
    @Override
    public String getNomPiece(){ return nom;}
    @Override
    public int getValeurPiece(){ return valeur;}

    // Méthode de clonage
    @Override
    public Object clone(){
        return new Cavalier(this.couleur_piece);
    }
}
