package Jeu;

import Mouvement.*;
import Piece.*;

import java.util.Objects;

public class Echiquier {

    // Attribut
    private final Case[][] plateau;

    // Constructeur
    public Echiquier(){
        // Initialisation du plateau
        this.plateau = new Case[8][8];

        // Création des cases
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.plateau[i][j] = new Case();
            }
        }

        // Placement des Pions
        for (int i = 0; i < 8; i++) {
            this.plateau[1][i].ajouterPiece(new Pion(Couleur.Blanc));
            this.plateau[6][i].ajouterPiece(new Pion(Couleur.Noir));
        }

        // Placement des Tours
        this.plateau[0][0].ajouterPiece(new Tour(Couleur.Blanc));
        this.plateau[0][7].ajouterPiece(new Tour(Couleur.Blanc));
        this.plateau[7][0].ajouterPiece(new Tour(Couleur.Noir));
        this.plateau[7][7].ajouterPiece(new Tour(Couleur.Noir));

        // Placement des Cavaliers
        this.plateau[0][1].ajouterPiece(new Cavalier(Couleur.Blanc));
        this.plateau[0][6].ajouterPiece(new Cavalier(Couleur.Blanc));
        this.plateau[7][1].ajouterPiece(new Cavalier(Couleur.Noir));
        this.plateau[7][6].ajouterPiece(new Cavalier(Couleur.Noir));

        // Placement des Fous
        this.plateau[0][2].ajouterPiece(new Fou(Couleur.Blanc));
        this.plateau[0][5].ajouterPiece(new Fou(Couleur.Blanc));
        this.plateau[7][2].ajouterPiece(new Fou(Couleur.Noir));
        this.plateau[7][5].ajouterPiece(new Fou(Couleur.Noir));

        // Placement des Reines
        this.plateau[0][3].ajouterPiece(new Reine(Couleur.Blanc));
        this.plateau[7][3].ajouterPiece(new Reine(Couleur.Noir));

        // Placement des Rois
        this.plateau[0][4].ajouterPiece(new Roi(Couleur.Blanc));
        this.plateau[7][4].ajouterPiece(new Roi(Couleur.Noir));

    }

    // Getters
    public Case[][] getPlateau(){ return this.plateau;}
    public Case getCase(int ligne, int colonne){ return this.plateau[ligne][colonne];}

    // Méthode
    public void reinitialiserEchiquier(){
        // Vide les cases
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.plateau[i][j].viderCase();
            }
        }

        // Placement des Pions
        for (int i = 0; i < 8; i++) {
            this.plateau[1][i].ajouterPiece(new Pion(Couleur.Blanc));
            this.plateau[6][i].ajouterPiece(new Pion(Couleur.Noir));
        }

        // Placement des Tours
        this.plateau[0][0].ajouterPiece(new Tour(Couleur.Blanc));
        this.plateau[0][7].ajouterPiece(new Tour(Couleur.Blanc));
        this.plateau[7][0].ajouterPiece(new Tour(Couleur.Noir));
        this.plateau[7][7].ajouterPiece(new Tour(Couleur.Noir));

        // Placement des Cavaliers
        this.plateau[0][1].ajouterPiece(new Cavalier(Couleur.Blanc));
        this.plateau[0][6].ajouterPiece(new Cavalier(Couleur.Blanc));
        this.plateau[7][1].ajouterPiece(new Cavalier(Couleur.Noir));
        this.plateau[7][6].ajouterPiece(new Cavalier(Couleur.Noir));

        // Placement des Fous
        this.plateau[0][2].ajouterPiece(new Fou(Couleur.Blanc));
        this.plateau[0][5].ajouterPiece(new Fou(Couleur.Blanc));
        this.plateau[7][2].ajouterPiece(new Fou(Couleur.Noir));
        this.plateau[7][5].ajouterPiece(new Fou(Couleur.Noir));

        // Placement des Reines
        this.plateau[0][3].ajouterPiece(new Reine(Couleur.Blanc));
        this.plateau[7][3].ajouterPiece(new Reine(Couleur.Noir));

        // Placement des Rois
        this.plateau[0][4].ajouterPiece(new Roi(Couleur.Blanc));
        this.plateau[7][4].ajouterPiece(new Roi(Couleur.Noir));

    }
    public void deplacerPiece(Mouvement mv, boolean mouvementReel){
        try{
            // Récupération du type de mouvement
            String type_mouvement = mv.getNom();

            switch(type_mouvement){
                case "Deplacement": this.effectuerDeplacement((Deplacement)mv, mouvementReel);
                    break;
                case "Roque": this.effectuerRoque((Roque)mv, mouvementReel);
                    break;
                case "Promotion": this.effectuerPromotion((Promotion)mv, mouvementReel);
                    break;
                default:
                    throw new IllegalStateException("Valeur inatendue: " + type_mouvement);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(mv.toString());
        }
    }

    public void effectuerDeplacement(Deplacement mv, boolean mouvementReel){
        // Détermine si le mouvement est réel ou simulé
        if(mouvementReel){
            this.plateau[mv.getAncienneLigne()][mv.getAncienneColonne()].getPiece().setRoquePossible(false);
        }
        // Cas d'une capture
        if (this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].getOccupe()) {
            this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].viderCase();
        }
        // Déplacement de la pièce mouvante
        this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].ajouterPiece(
                this.plateau[mv.getAncienneLigne()][mv.getAncienneColonne()].getPiece());
        // Vide l'ancienne case
        this.plateau[mv.getAncienneLigne()][mv.getAncienneColonne()].viderCase();
    }

    public void effectuerRoque(Roque rq, boolean mouvementReel){

        // Récupération de la couleur du roque ou du roque inversé
        Couleur couleur = rq.getCouleur();

        // Cas d'une inversion de roque
        if(rq.getInversion()){
            // Inversion petit roque
            if(Objects.equals(rq.getTypeDeRoque(), "Petit")){
                if(couleur == Couleur.Blanc){
                    this.plateau[0][6].getPiece().setRoquePossible(true);
                    Deplacement mouvementRoi  = new Deplacement(6, 4, 0, 0, false);
                    Deplacement mouvementTour = new Deplacement(5, 7, 0, 0, false);
                    deplacerPiece(mouvementRoi, mouvementReel);
                    deplacerPiece(mouvementTour, mouvementReel);
                }else{
                    this.plateau[7][6].getPiece().setRoquePossible(true);
                    Deplacement mouvementRoi  = new Deplacement(6, 4, 7, 7, false);
                    Deplacement mouvementTour = new Deplacement(5, 7, 7, 7, false);
                    deplacerPiece(mouvementRoi, mouvementReel);
                    deplacerPiece(mouvementTour, mouvementReel);
                }
            }
            else{
                if(couleur == Couleur.Blanc){
                    this.plateau[0][2].getPiece().setRoquePossible(true);
                    Deplacement mouvementRoi  = new Deplacement(2, 4, 0, 0, false);
                    Deplacement mouvementTour = new Deplacement(3, 0, 0, 0, false);
                    deplacerPiece(mouvementRoi, mouvementReel);
                    deplacerPiece(mouvementTour, mouvementReel);
                }else{
                    this.plateau[7][2].getPiece().setRoquePossible(true);
                    Deplacement mouvementRoi  = new Deplacement(2, 4, 7, 7, false);
                    Deplacement mouvementTour = new Deplacement(3, 0, 7, 7, false);
                    deplacerPiece(mouvementRoi, mouvementReel);
                    deplacerPiece(mouvementTour, mouvementReel);
                }
            }
        }
        // Cas d'un roque
        else{
            if(Objects.equals(rq.getTypeDeRoque(), "Petit")){
                if(couleur == Couleur.Blanc){
                    this.plateau[0][4].getPiece().setRoquePossible(false);
                    Deplacement mouvementRoi  = new Deplacement(4, 6, 0, 0, false);
                    Deplacement mouvementTour = new Deplacement(7, 5, 0, 0, false);
                    deplacerPiece(mouvementRoi, mouvementReel);
                    deplacerPiece(mouvementTour, mouvementReel);
                }else{
                    this.plateau[7][4].getPiece().setRoquePossible(false);
                    Deplacement mouvementRoi  = new Deplacement(4, 6, 7, 7, false);
                    Deplacement mouvementTour = new Deplacement(7, 5, 7, 7, false);
                    deplacerPiece(mouvementRoi,mouvementReel);
                    deplacerPiece(mouvementTour, mouvementReel);
                }
            }
            else{
                if(couleur == Couleur.Blanc){
                    this.plateau[0][4].getPiece().setRoquePossible(false);
                    Deplacement mouvementRoi  = new Deplacement(4, 2, 0, 0, false);
                    Deplacement mouvementTour = new Deplacement(0, 3, 0, 0, false);
                    deplacerPiece(mouvementRoi, mouvementReel);
                    deplacerPiece(mouvementTour, mouvementReel);
                }else{
                    this.plateau[7][4].getPiece().setRoquePossible(false);
                    Deplacement mouvementRoi  = new Deplacement(4, 2, 7, 7, false);
                    Deplacement mouvementTour = new Deplacement(0, 3, 7, 7, false);
                    deplacerPiece(mouvementRoi, mouvementReel);
                    deplacerPiece(mouvementTour, mouvementReel);
                }
            }
        }
    }

    public void effectuerPromotion(Promotion mv, boolean mouvementReel){
        // Détermine si le mouvement est réel ou simulé
        if (mouvementReel) {
            this.plateau[mv.getAncienneLigne()][mv.getAncienneColonne()].getPiece().setRoquePossible(false);
        }
        // Cas d'une capture
        if (this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].getOccupe()) {
            this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].viderCase();
        }
        // Récupére la couleur de la pièce à promouvoir
        Couleur couleur = mv.getCouleur();
        // Cas d'une inversion de promotion
        if(mv.getInversion()){
            this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].ajouterPiece(new Pion(couleur));
        }
        // Cas d'une promotion
        else{
            // Effectue la promotion
            switch(mv.getTypePromotion()){
                case 'q': this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].ajouterPiece(new Reine(couleur));
                    break;
                case 'r': this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].ajouterPiece(new Tour(couleur));
                    break;
                case 'b': this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].ajouterPiece(new Fou(couleur));
                    break;
                case 'k': this.plateau[mv.getNouvelleLigne()][mv.getNouvelleColonne()].ajouterPiece(new Cavalier(couleur));
                    break;
            }
        }
        // Vide l'ancienne case
        this.plateau[mv.getAncienneLigne()][mv.getAncienneColonne()].viderCase();
    }

    // Méthode de clonage
    @Override
    public Object clone(){
        Echiquier copie = new Echiquier();
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(this.plateau[i][j].getOccupe()){
                    copie.getPlateau()[i][j].ajouterPiece((Piece)this.plateau[i][j].getPiece().clone());
                }else{
                    copie.getPlateau()[i][j].viderCase();
                }
            }
        }
        return copie;
    }

    // Affiche l'échiquier
    public void montrerEchiquier(){
        System.out.println("   A  B  C  D  E  F  G  H");
        for (int i = 7; i >= 0; i--) {
            System.out.print(i+1);
            System.out.print("| ");
            for (int j = 0; j < 8; j++) {
                char p;
                char c;
                if(this.plateau[i][j].getOccupe()){
                    switch (this.plateau[i][j].getPiece().getNomPiece()){
                        case "Pion": p = 'P';
                            break;
                        case "Tour": p = 'T';
                            break;
                        case "Cavalier": p = 'C';
                            break;
                        case "Reine": p = 'Q';
                            break;
                        case "Roi": p = 'K';
                            break;
                        case "Fou": p = 'F';
                            break;
                        default: p = ' ';
                            break;
                    }
                    switch (this.plateau[i][j].getPiece().getCouleurPiece()){
                        case Blanc: c = 'B';
                            break;
                        case Noir: c = 'N';
                            break;
                        default: c = 'E';
                            break;
                    }
                }else{
                    p = ' ';
                    c = ' ';
                }
                System.out.print(p);
                System.out.print(c);
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.print(i+1);
            System.out.println();
        }
        System.out.println("   A  B  C  D  E  F  G  H");
    }
}
