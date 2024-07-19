package Piece;

import Jeu.Couleur;

public abstract class Piece {

    // Attributs
    private boolean roque_possible;
    protected Couleur couleur_piece;

    // Constructeur
    public Piece(Couleur couleur) {
        this.roque_possible = true;
        this.couleur_piece = couleur;
    }

    // Getters
    public boolean getRoquePossible(){ return this.roque_possible;}
    public Couleur getCouleurPiece(){ return this.couleur_piece;}

    // Méthodes abstraites
    public abstract String getNomPiece();
    public abstract int getValeurPiece();

    // Setter
    public void setRoquePossible(boolean nouvelle_valeur){
        this.roque_possible = nouvelle_valeur;
    }

    // Méthode de clonage
    @Override
    public Object clone(){
        try{
            return super.clone();
        }
        catch(CloneNotSupportedException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
