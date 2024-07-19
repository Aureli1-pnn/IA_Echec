package Mouvement;

import Jeu.Couleur;

public class Deplacement extends Mouvement{

    // Attributs
    private static final String nom = "Deplacement";
    protected final int ancienne_ligne;
    protected final int ancienne_colonne;
    protected final int nouvelle_ligne;
    protected final int nouvelle_colonne;

    // Constructeur
    public Deplacement(int ac, int nc, int al, int nl, boolean capture){
        super(capture);
        this.ancienne_colonne = ac;
        this.nouvelle_colonne = nc;
        this.ancienne_ligne = al;
        this.nouvelle_ligne = nl;
    }

    // Getters
    @Override
    public String getNom(){ return nom;}
    public int getAncienneColonne(){ return this.ancienne_colonne;}
    public int getNouvelleColonne(){ return this.nouvelle_colonne;}
    public int getAncienneLigne(){ return this.ancienne_ligne;}
    public int getNouvelleLigne(){ return this.nouvelle_ligne;}

    // Méthodes à Override
    @Override
    public Mouvement mouvementInverse(){
        return new Deplacement(this.nouvelle_colonne,
                this.ancienne_colonne,
                this.nouvelle_ligne,
                this.ancienne_ligne,
                false);
    }
    @Override
    public String toString() {
        int al = this.ancienne_ligne+1;
        int nl = this.nouvelle_ligne+1;
        return (char)('a' + ancienne_colonne) + "" + al +
                (char)('a' + nouvelle_colonne) + "" + nl;
    }
}
