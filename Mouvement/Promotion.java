package Mouvement;

import Jeu.Couleur;

public class Promotion extends Deplacement{

    // Attributs
    private static final String nom = "Promotion";
    private final char type_promotion;
    private final boolean inversion;
    private final Couleur couleur;

    // Constructeur
    public Promotion(int ac, int nc, int al, int nl, boolean capture, boolean inversion, Couleur couleur, char type) {
        super(ac, nc, al, nl, capture);
        this.inversion = inversion;
        this.couleur = couleur;
        if(type == 'q' || type == 'r' || type == 'k' || type == 'b'){
            this.type_promotion = type;
        }else{
            this.type_promotion = 'q';
        }
    }

    // Getters
    @Override
    public String getNom(){ return nom;}
    public char getTypePromotion(){ return this.type_promotion;}
    public boolean getInversion(){ return this.inversion;}
    public Couleur getCouleur(){ return this.couleur;}

    // Méthode à Override
    @Override
    public String toString() {
        int al = this.getAncienneLigne()+1;
        int nl = this.getNouvelleLigne()+1;
        return (char)('a' + getAncienneColonne()) + "" + al +
                (char)('a' + getNouvelleColonne()) + "" + nl + this.type_promotion;
    }
}
