package Jeu;

import Piece.*;

public class Case {

    // Attributs
    private boolean occupe;
    private Piece piece;

    // Constructeur
    public Case(){
        this.occupe = false;
        this.piece = null;
    }

    // Getters
    public boolean getOccupe(){ return this.occupe;}
    public Piece getPiece(){ return this.piece;}

    // Méthodes
    public void viderCase(){
        this.piece = null;
        this.occupe = false;
    }
    public void ajouterPiece(Piece p){
        this.piece = p;
        this.occupe = true;
    }
}
