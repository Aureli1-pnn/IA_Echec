package IntelligenceArtificielle;

import Jeu.Arbitre;
import Jeu.Couleur;
import Jeu.Echiquier;
import Mouvement.*;
import Piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Noeud {

    // Attributs
    private final int profondeur;
    private final Couleur couleur_ia;
    private final Mouvement mouvement_effectue;
    private final Arbitre arbitre;
    private int evaluation = 0;
    private List<Noeud> noeud_fils;
    private boolean est_une_feuille = true;

    // Constructeur Noeud pour elagage alpha beta
    public Noeud(int profondeur, Couleur couleur, Mouvement mv, Echiquier echiquier, int alpha, int beta) {
        // Copie des paramètres
        this.profondeur = profondeur;
        this.couleur_ia = couleur;
        this.mouvement_effectue = mv;
        this.arbitre = new Arbitre();
        // Si le noeud est une feuille
        if (this.profondeur >= Constante.PROFONDEUR_MAX_ALPHA_BETA) {
            // Alors on calcul sa fonction d'évaluation et on ne va pas plus loin
            fonctionEvaluation(echiquier);
            this.noeud_fils = null;
            this.est_une_feuille = true;
        }
        // Sinon
        else {
            // Création d'une liste regroupant tous les mouvements possibles à partir de l'état de l'échiquier
            List<Mouvement> list;
            // Le noeud est de type Allié
            if (this.profondeur % 2 == 0) {
                list = arbitre.mouvementsPossible(echiquier, this.couleur_ia);
            }
            // Le noeud est de type ennemie
            else {
                if (this.couleur_ia == Couleur.Blanc) {
                    list = arbitre.mouvementsPossible(echiquier, Couleur.Noir);
                } else {
                    list = arbitre.mouvementsPossible(echiquier, Couleur.Blanc);
                }
            }
            // S'il y a des mouvements possibles
            if (list.size() > 0) {
                // Alors on créer les noeud fils correspondant à ces mouvements
                creerNoeudFils(list, echiquier, alpha, beta);
            } else {
                // Sinon on s'arrête là et on calcul l'évaluation
                if (this.getProfondeur() % 2 == 0) {
                    // IA échec et mat
                    if (arbitre.verifierEchec(echiquier, couleur_ia)) {
                        this.evaluation = (Integer.MIN_VALUE);
                    }
                    // IA pat
                    else {
                        this.evaluation = (0);
                    }
                } else {
                    // Adversaire échec et mat
                    if (arbitre.verifierEchec(echiquier, couleur_ia)) {
                        this.evaluation = (Integer.MAX_VALUE);
                    }
                    // Adversaire pat
                    else {
                        this.evaluation = (Integer.MIN_VALUE);
                    }
                }
                this.noeud_fils = null;
                this.est_une_feuille = true;
            }
        }
    }

    // Constructeur Noeud pour algorithme minimax
    public Noeud(int profondeur, Couleur couleur, Mouvement mv, Echiquier echiquier) {
        // Copie des paramètres
        this.profondeur = profondeur;
        this.couleur_ia = couleur;
        this.mouvement_effectue = mv;
        this.arbitre = new Arbitre();
        // Si le noeud est une feuille
        if (this.profondeur >= Constante.PROFONDEUR_MAX_MINIMAX) {
            // Alors on calcul sa fonction d'évaluation et on ne va pas plus loin
            fonctionEvaluation(echiquier);
            this.noeud_fils = null;
            this.est_une_feuille = true;
        }
        // Sinon
        else {
            // Création d'une liste regroupant tous les mouvements possibles à partir de l'état de l'échiquier
            List<Mouvement> list;
            // Le noeud est de type Allié
            if (this.profondeur % 2 == 0) {
                list = arbitre.mouvementsPossible(echiquier, this.couleur_ia);
            }
            // Le noeud est de type ennemie
            else {
                if (this.couleur_ia == Couleur.Blanc) {
                    list = arbitre.mouvementsPossible(echiquier, Couleur.Noir);
                } else {
                    list = arbitre.mouvementsPossible(echiquier, Couleur.Blanc);
                }
            }
            // S'il y a des mouvements possibles
            if (list.size() > 0) {
                // Alors on créer les noeud fils correspondant à ces mouvements
                creerNoeudFilsVersionSansElagage(list, echiquier);
            } else {
                // Sinon on s'arrête là et on calcul l'évaluation
                if (this.getProfondeur() % 2 == 0) {
                    // IA échec et mat
                    if (arbitre.verifierEchec(echiquier, couleur_ia)) {
                        this.evaluation = (Integer.MIN_VALUE);
                    }
                    // IA pat
                    else {
                        this.evaluation = (0);
                    }
                } else {
                    // Adversaire échec et mat
                    if (arbitre.verifierEchec(echiquier, couleur_ia)) {
                        this.evaluation = (Integer.MAX_VALUE);
                    }
                    // Adversaire pat
                    else {
                        this.evaluation = (Integer.MIN_VALUE);
                    }
                }
                this.noeud_fils = null;
                this.est_une_feuille = true;
            }
        }
    }

    // Constructeur Noeud D'origine de l'arbre
    public Noeud(Couleur couleur_ia, Echiquier echiquier, boolean alpha_beta_version){
        this.profondeur = 0;
        this.couleur_ia = couleur_ia;
        this.noeud_fils = new ArrayList<Noeud>();
        this.arbitre = new Arbitre();
        this.mouvement_effectue = null;
        if(alpha_beta_version){
            creerNoeudFils(arbitre.mouvementsPossible(echiquier, couleur_ia), echiquier, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }else{
            creerNoeudFilsVersionSansElagage(arbitre.mouvementsPossible(echiquier, couleur_ia), echiquier);
        }
    }
    // Getters
    public int getProfondeur() {
        return this.profondeur;
    }

    public Mouvement getMouvementEffectue() {
        return this.mouvement_effectue;
    }

    public List<Noeud> getNoeudFils() {
        return this.noeud_fils;
    }

    public int getEvaluation() {
        return this.evaluation;
    }
    public boolean getEstUneFeuille(){
        return this.est_une_feuille;
    }

    // Méthodes
    public void fonctionEvaluation(Echiquier echiquier) {
        if(this.couleur_ia == Couleur.Blanc){
            this.evaluation = arbitre.calculValeurPieceBlanche(echiquier)*10 +
                    arbitre.nombreCoupPossible(this.couleur_ia, echiquier) -
                    arbitre.nombreCoupPossible(Couleur.Noir, echiquier);
        }else{
            this.evaluation = -(arbitre.calculValeurPieceBlanche(echiquier)*10) +
                    arbitre.nombreCoupPossible(this.couleur_ia, echiquier) -
                    arbitre.nombreCoupPossible(Couleur.Blanc, echiquier);
        }
    }

    /*
        Fonction de création des noeuds fils se basant sur l'élagage alpha beta
        Cette technique a pour but de ne pas développer les branches d'arbres
        qui ne donneront rien et ainsi accélérer le code
    */
    public void creerNoeudFils(List<Mouvement> list, Echiquier echiquier, int alpha, int beta) {
        // Création de la liste de noeud fils
        this.noeud_fils = new ArrayList<Noeud>();

        // Création de variable utiles
        Couleur couleur_noeud;
        String type_mouvement;
        Piece piece_capturee = null;
        boolean exit = false;
        // Noeud de type Max
        if (this.getProfondeur() % 2 == 0) {
            this.evaluation = Integer.MIN_VALUE;
            couleur_noeud = this.couleur_ia;
        }
        // Noeud de type Min
        else {
            this.evaluation = Integer.MAX_VALUE;
            if (this.couleur_ia == Couleur.Blanc) {
                couleur_noeud = Couleur.Noir;
            } else {
                couleur_noeud = Couleur.Blanc;
            }
        }

        // Création des noeuds fils
        for (Mouvement mouv : list) {
            if (!exit) {
                // Récupération du type de mouvement
                type_mouvement = mouv.getNom();

                // Récupère l'éventuel pièce capturée pour la remettre sur l'échiquier au moment de l'annulation du mouvement
                if (Objects.equals(type_mouvement, "Deplacement") || Objects.equals(type_mouvement, "Promotion")) {
                    if (mouv.getCapture()) {
                        Deplacement dp = (Deplacement) mouv;
                        piece_capturee = echiquier.getCase(dp.getNouvelleLigne(), dp.getNouvelleColonne()).getPiece();
                    }
                }

                // Effectue le mouvement
                echiquier.deplacerPiece(mouv, false);

                // Vérifie si le mouvement n'entraîne pas d'échec pour son propre Roi
                if (!this.arbitre.verifierEchec(echiquier, couleur_noeud)) {
                    // S'il n'y a pas d'échec alors on peut ajouter un nouveau noeud fils
                    Noeud nouveau_noeud_fils = new Noeud(this.profondeur + 1, couleur_noeud, mouv, echiquier, alpha, beta);
                    this.noeud_fils.add(nouveau_noeud_fils);
                    this.est_une_feuille = false;
                    // Noeud de type MAX
                    if (this.getProfondeur() % 2 == 0) {
                        // Si le noeud nouvellement créé a une évaluation supérieure à tous ses prédécesseurs
                        if (nouveau_noeud_fils.getEvaluation() > this.evaluation) {
                            // Alors son noeud père prend sa valeur
                            this.evaluation = nouveau_noeud_fils.getEvaluation();
                        }
                        // Coupure beta la suite de la branche ne sera pas explorée
                        if (nouveau_noeud_fils.getEvaluation() >= beta) {
                            exit = true;
                        } else {
                            alpha = Math.max(alpha, nouveau_noeud_fils.getEvaluation());
                        }
                    }
                    // Noeud de type MIN
                    else {
                        // Si le noeud nouvellement créé a une évaluation inférieure à tous ses prédécesseurs
                        if (nouveau_noeud_fils.getEvaluation() < this.evaluation) {
                            // Alors son noeud père prend sa valeur
                            this.evaluation = nouveau_noeud_fils.getEvaluation();
                        }
                        // Coupure alpha la suite de la branche ne sera pas explorée
                        if (alpha >= nouveau_noeud_fils.getEvaluation()) {
                            exit = true;
                        } else {
                            beta = Math.min(beta, nouveau_noeud_fils.getEvaluation());
                        }
                    }
                }

                // Annulation du mouvement
                echiquier.deplacerPiece(mouv.mouvementInverse(), false);
                if (Objects.equals(type_mouvement, "Deplacement") || Objects.equals(type_mouvement, "Promotion")) {
                    if (mouv.getCapture()) {
                        // Si le mouvement a entraîné une capture alors on replace la pièce
                        Deplacement dp = (Deplacement) mouv;
                        echiquier.getCase(dp.getNouvelleLigne(), dp.getNouvelleColonne()).ajouterPiece(piece_capturee);
                    }
                }
            }
        }
    }
     /*
        Même principe mais sans le principe d'élagage alpha-bêta
    */
    public void creerNoeudFilsVersionSansElagage(List<Mouvement> list, Echiquier echiquier) {
         // Création de la liste de noeud fils
         this.noeud_fils = new ArrayList<Noeud>();

         // Création de variable utiles
         Couleur couleur_noeud;
         String type_mouvement;
         Piece piece_capturee = null;

         // Noeud Allié
         if (this.getProfondeur() % 2 == 0) {
             couleur_noeud = this.couleur_ia;
         }
         // Noeud Ennemie
         else {
             if (this.couleur_ia == Couleur.Blanc) {
                 couleur_noeud = Couleur.Noir;
             } else {
                 couleur_noeud = Couleur.Blanc;
             }
         }

         // Création des noeuds fils
         for (Mouvement mouv : list) {
             // Récupération du type de mouvement
             type_mouvement = mouv.getNom();

             // Récupère l'éventuel pièce capturée pour la remettre sur l'échiquier au moment de l'annulation du mouvement
             if (Objects.equals(type_mouvement, "Deplacement") || Objects.equals(type_mouvement, "Promotion")) {
                 if (mouv.getCapture()) {
                     Deplacement dp = (Deplacement) mouv;
                     piece_capturee = echiquier.getCase(dp.getNouvelleLigne(), dp.getNouvelleColonne()).getPiece();
                 }
             }

             // Effectue le mouvement
             echiquier.deplacerPiece(mouv, false);

             // Vérifie si le mouvement n'entraîne pas d'échec pour son propre Roi
             if (!this.arbitre.verifierEchec(echiquier, couleur_noeud)) {
                 // S'il n'y a pas d'échec alors on peut ajouter un nouveau noeud fils
                 Noeud nouveau_noeud_fils = new Noeud(this.profondeur + 1, couleur_noeud, mouv, echiquier);
                 this.noeud_fils.add(nouveau_noeud_fils);
                 this.est_une_feuille = false;
             }

             // Annulation du mouvement
             echiquier.deplacerPiece(mouv.mouvementInverse(), false);
             if (Objects.equals(type_mouvement, "Deplacement") || Objects.equals(type_mouvement, "Promotion")) {
                 if (mouv.getCapture()) {
                     // Si le mouvement a entraîné une capture alors on replace la pièce
                     Deplacement dp = (Deplacement) mouv;
                     echiquier.getCase(dp.getNouvelleLigne(), dp.getNouvelleColonne()).ajouterPiece(piece_capturee);
                 }
             }
         }
     }

    // Setter
    public void setEvaluation(int nouvelle_valeur){ this.evaluation = nouvelle_valeur;}
}