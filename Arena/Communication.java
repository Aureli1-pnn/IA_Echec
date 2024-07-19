package Arena;

import IntelligenceArtificielle.*;
import Jeu.Couleur;
import Jeu.Echiquier;
import Mouvement.*;

public class Communication {

    // Attribut
    private static final String ENGINENAME="STAR";

    // Méthodes
    public static void inputUCI() {
        System.out.println("id name "+ENGINENAME);
        System.out.println("id author AURELIEN");
        System.out.println("uciok");
    }
    public static void inputSetOption(String inputString) {
        // Ajouter gestion de modification d'option
    }
    public static void inputIsReady() {
        System.out.println("readyok");
    }
    public static Mouvement inputPosition(String input, Echiquier echiquier, Couleur couleur) {
        input=input.substring(9).concat(" ");
        if (input.contains("moves")) {
            input=input.substring(input.indexOf("moves")+6);
            input = getDernierMouvement(input);
            // Petit roque
            if(input.equals("e1g1") || input.equals("e8g8")){
                Roque rq = new Roque(couleur, true, false);
                echiquier.deplacerPiece(rq, true);
                return rq;
            }
            // Grand roque
            else if(input.equals("e1c1") || input.equals("e8c8")){
                Roque rq = new Roque(couleur, false, false);
                echiquier.deplacerPiece(rq, true);
                return rq;
            }
            // sinon
            else{
                Deplacement dp = stringToDeplacement(input, couleur);
                echiquier.deplacerPiece(dp, true);
                return dp;
            }
        }
        return null;
    }
    public static Mouvement inputGo(Couleur couleur_ia, Echiquier echiquier, boolean alpha_beta_version) {
        Arbre arbre = new Arbre(couleur_ia, echiquier, alpha_beta_version);
        long start = System.currentTimeMillis();
        Noeud meilleur_noeud = arbre.rechercheMeilleurNoeud(alpha_beta_version);
        long end = System.currentTimeMillis();
        Mouvement mouv_determine = meilleur_noeud.getMouvementEffectue();
        System.out.println("Temps : " + (end-start));
        echiquier.deplacerPiece(mouv_determine, true);
        if(mouv_determine.getNom().equals("Promotion")){
            System.out.println("bestmove " + promotionToString((Promotion) mouv_determine));
        }else if(mouv_determine.getNom().equals("Deplacement")){
            System.out.println("bestmove " + deplacementToString((Deplacement) mouv_determine));
        }else if(mouv_determine.getNom().equals("Roque")){
            System.out.println("bestmove " + roqueToString((Roque) mouv_determine, couleur_ia));
        }

        return mouv_determine;
    }
    public static String promotionToString(Promotion pr) {
        String mouvement="";
        mouvement += positionToColonne(pr.getAncienneColonne());
        mouvement += positionToLigne(pr.getAncienneLigne());
        mouvement += positionToColonne(pr.getNouvelleColonne());
        mouvement += positionToLigne(pr.getNouvelleLigne());
        mouvement += pr.getTypePromotion();

        return mouvement;
    }
    public static String deplacementToString(Deplacement dp) {
        String mouvement="";
        mouvement += positionToColonne(dp.getAncienneColonne());
        mouvement += positionToLigne(dp.getAncienneLigne());
        mouvement += positionToColonne(dp.getNouvelleColonne());
        mouvement += positionToLigne(dp.getNouvelleLigne());

        return mouvement;
    }
    public static String roqueToString(Roque rq, Couleur couleur_ia) {
        String mouvement = "e";
        char ligne = couleur_ia == Couleur.Blanc ? '1' : '8';
        char colonne = rq.getTypeDeRoque().equals("Petit") ? 'g' : 'c';

        return mouvement + ligne + colonne + ligne;
    }
    public static void inputQuit() {
        System.exit(0);
    }
    public static void inputPrint() {

    }
    public static String getDernierMouvement(String input) {
        String[] parts = input.split(" ");

        return parts[parts.length - 1];
    }
    public static Couleur getCouleur(String input) {
        if (input.contains("moves")) {
            return Couleur.Noir;
        }else{
            return Couleur.Blanc;
        }
    }
    public static Deplacement stringToDeplacement(String input, Couleur couleur){
        int ac = colonneToPosition(input.charAt(0));
        int nc = colonneToPosition(input.charAt(2));
        int al = ligneToPosition(input.charAt(1));
        int nl = ligneToPosition(input.charAt(3));
        if(input.length() >= 5){
            return new Promotion(ac, nc, al, nl, false, false, couleur, input.charAt(4));
        }else{
            return new Deplacement(ac, nc, al, nl, false);
        }
    }
    public static int colonneToPosition(char c){
        switch(c){
            case 'b': return 1;
            case 'c': return 2;
            case 'd': return 3;
            case 'e': return 4;
            case 'f': return 5;
            case 'g': return 6;
            case 'h': return 7;
            default: return 0;
        }
    }
    public static int ligneToPosition(char c){ return (c - '0') - 1;}
    public static char positionToColonne(int position){
        switch(position){
            case 1: return 'b';
            case 2: return 'c';
            case 3: return 'd';
            case 4: return 'e';
            case 5: return 'f';
            case 6: return 'g';
            case 7: return 'h';
            default: return 'a';
        }
    }
    public static char positionToLigne(int position){ return (char)(position+'1');}

}
