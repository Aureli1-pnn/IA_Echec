package Jeu;

import IntelligenceArtificielle.Arbre;
import IntelligenceArtificielle.Constante;
import IntelligenceArtificielle.Noeud;
import Joueur.Joueur;
import Mouvement.Mouvement;

import java.util.Random;
import java.util.Scanner;

import static Arena.Communication.*;

public class Jeu {

    // Attributs
    private final Echiquier echiquier;
    private Joueur joueur_un;
    private Joueur joueur_deux;

    // Constructeur
    public Jeu(){
        this.echiquier = new Echiquier();
        this.joueur_un = null;
        this.joueur_deux = null;
    }

    // Méthodes
    public void lancerPartie(Joueur joueur_un, Joueur joueur_deux){
        Scanner input = new Scanner(System.in);
        this.joueur_un = joueur_un;
        this.joueur_deux = joueur_deux;
        boolean debut = true;

        while (true) {
            String inputString = input.nextLine();
            if ("uci".equals(inputString)) {
                inputUCI();
            } else if (inputString.startsWith("setoption")) {
                inputSetOption(inputString);
            } else if ("isready".equals(inputString)) {
                inputIsReady();
            } else if ("ucinewgame".equals(inputString)) {
                debut = true;
                this.echiquier.reinitialiserEchiquier();
            } else if (inputString.startsWith("position")) {
                if(debut){
                    determineCouleurJoueur(inputString);
                    debut = false;
                }
                if(this.joueur_un.getEstUneIa()){
                    this.joueur_deux.ajouterMouvement(inputPosition(inputString, this.echiquier, this.joueur_deux.getCouleur()));
                }else{
                    this.joueur_un.ajouterMouvement(inputPosition(inputString, this.echiquier, this.joueur_un.getCouleur()));
                }
            } else if (inputString.startsWith("go")) {
                if(this.joueur_un.getEstUneIa()){
                    this.joueur_un.ajouterMouvement(inputGo(this.joueur_un.getCouleur(), this.echiquier, Constante.ALPHA_BETA_VERSION));
                }else{
                    this.joueur_deux.ajouterMouvement(inputGo(this.joueur_deux.getCouleur(), this.echiquier, Constante.ALPHA_BETA_VERSION));
                }
            } else if (inputString.equals("quit")) {
                inputQuit();
            } else if ("print".equals(inputString)) {
                inputPrint();
            }
            echiquier.montrerEchiquier();
        }
    }

    public void determineCouleurJoueur(String input){
        Couleur couleur_ia;
        Couleur couleur_opposant;

        couleur_ia = getCouleur(input);
        if(couleur_ia == Couleur.Blanc){
            couleur_opposant = Couleur.Noir;
        }
        else{
            couleur_opposant = Couleur.Blanc;
        }

        if(this.joueur_un.getEstUneIa()){
            this.joueur_un.setCouleurJoueur(couleur_ia);
            this.joueur_deux.setCouleurJoueur(couleur_opposant);
        }
        else{
            this.joueur_un.setCouleurJoueur(couleur_opposant);
            this.joueur_deux.setCouleurJoueur(couleur_ia);
        }
    }

    public void miniMaxVsAlphaBeta(){
        Random rand = new Random();
        Couleur couleur_mini_max;
        Couleur couleur_alpha_beta;
        long start, end;
        boolean finPartie = false;
        if(rand.nextInt(2) == 0){
            couleur_mini_max = Couleur.Blanc;
            couleur_alpha_beta = Couleur.Noir;
        }else{
            couleur_mini_max = Couleur.Noir;
            couleur_alpha_beta = Couleur.Blanc;
        }
        this.joueur_un = new Joueur("MiniMax", true);
        this.joueur_deux = new Joueur("AlphaBeta", true);
        int nombreTour=0;
        while(!finPartie){
            if(couleur_mini_max == Couleur.Blanc){
                start = System.currentTimeMillis();
                Arbre arbre_blanc = new Arbre(couleur_mini_max, echiquier, false);
                Noeud meilleur_noeud_blanc = arbre_blanc.rechercheMeilleurNoeud(false);
                end = System.currentTimeMillis();
                if(meilleur_noeud_blanc == null){
                    finPartie = true;
                    System.out.println("Vainqueur : Alpha-Beta");
                }else{
                    System.out.println("Temps Mini Max : " + (end-start));
                    Mouvement mouv_determine_blanc = meilleur_noeud_blanc.getMouvementEffectue();
                    System.out.println("Mouvement trouvé : " + mouv_determine_blanc.toString());
                    echiquier.deplacerPiece(mouv_determine_blanc, true);
                    System.out.println("----------ALPHA-BETA--------");
                    echiquier.montrerEchiquier();
                    System.out.println("------------MINIMAX---------\n");
                }
                if(!finPartie){
                    start = System.currentTimeMillis();
                    Arbre arbre_noir = new Arbre(couleur_alpha_beta, echiquier, true);
                    Noeud meilleur_noeud_noir = arbre_noir.rechercheMeilleurNoeud(true);
                    end = System.currentTimeMillis();
                    if(meilleur_noeud_noir == null){
                        finPartie = true;
                        System.out.println("Vainqueur : MiniMax");
                    }else{
                        System.out.println("Temps Alpha-beta : " + (end-start));
                        Mouvement mouv_determine_noir = meilleur_noeud_noir.getMouvementEffectue();
                        System.out.println("Mouvement trouvé : " + mouv_determine_noir.toString());
                        echiquier.deplacerPiece(mouv_determine_noir, true);
                        System.out.println("----------ALPHA-BETA--------");
                        echiquier.montrerEchiquier();
                        System.out.println("------------MINIMAX---------\n");
                    }
                }
            }else{
                start = System.currentTimeMillis();
                Arbre arbre_blanc = new Arbre(couleur_alpha_beta, echiquier, true);
                Noeud meilleur_noeud_blanc = arbre_blanc.rechercheMeilleurNoeud(true);
                end = System.currentTimeMillis();
                if(meilleur_noeud_blanc == null){
                    finPartie = true;
                    System.out.println("Vainqueur : MiniMax");
                }else{
                    System.out.println("Temps Alpha-beta : " + (end-start));
                    Mouvement mouv_determine_blanc = meilleur_noeud_blanc.getMouvementEffectue();
                    System.out.println("Mouvement trouvé : " + mouv_determine_blanc.toString());
                    echiquier.deplacerPiece(mouv_determine_blanc, true);
                    System.out.println("------------MINIMAX---------");
                    echiquier.montrerEchiquier();
                    System.out.println("----------ALPHA-BETA--------\n");
                }
                if(!finPartie){
                    start = System.currentTimeMillis();
                    Arbre arbre_noir = new Arbre(couleur_mini_max, echiquier, false);
                    Noeud meilleur_noeud_noir = arbre_noir.rechercheMeilleurNoeud(false);
                    end = System.currentTimeMillis();
                    if(meilleur_noeud_noir == null){
                        finPartie = true;
                        System.out.println("Vainqueur : Alpha-Beta");
                    }else{
                        System.out.println("Temps Mini Max : " + (end-start));
                        Mouvement mouv_determine_noir = meilleur_noeud_noir.getMouvementEffectue();
                        System.out.println("Mouvement trouvé : " + mouv_determine_noir.toString());
                        echiquier.deplacerPiece(mouv_determine_noir, true);
                        System.out.println("------------MINIMAX---------");
                        echiquier.montrerEchiquier();
                        System.out.println("----------ALPHA-BETA--------\n");
                    }
                }
            }
            nombreTour++;
        }
        System.out.println("Nombre de tour : " + nombreTour);
    }
}