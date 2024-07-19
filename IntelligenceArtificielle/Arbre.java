package IntelligenceArtificielle;

import Jeu.Couleur;
import Jeu.Echiquier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arbre {

    // Attributs
    private final Noeud origin;

    // Constructeur
    public Arbre(Couleur couleur_ia, Echiquier echiquier, boolean alpha_beta_version){
        this.origin = new Noeud(couleur_ia, (Echiquier)echiquier.clone(), alpha_beta_version);
    }

    // Méthodes
    public Noeud rechercheMeilleurNoeud(boolean alpha_beta_version){
        // Création d'une liste qui contiendra tous les meilleurs noeuds possible
        List<Noeud> list = new ArrayList<>();
        // Obtient la valeur du/des meilleurs noeuds Max
        int max;
        if(alpha_beta_version){
            max = findMax();
        }
        else{
            max = miniMax(this.origin);
        }
        // Parcours les noeuds fils d'origin pour trouver les meilleurs
        for (Noeud fils: this.origin.getNoeudFils()) {
            if(fils.getEvaluation() >= max){
                list.add(fils);
            }
        }
        if(list.size() > 0){
            // Retourne l'un des meilleurs mouvements possibles
            int mouvement_aleatoire = new Random().nextInt(list.size());
            return list.get(mouvement_aleatoire);
        }
        else{
            // S'il n'y a aucun mouvement possible alors on est échec et mat
            System.out.println("Echec et mat :(");
            return null;
        }
    }


    public int miniMax(Noeud noeud){
        if(noeud.getEstUneFeuille()){
            return noeud.getEvaluation();
        }
        int value;
        if(noeud.getProfondeur()%2==0){
            value = Integer.MIN_VALUE;
            for (Noeud noeud_fils: noeud.getNoeudFils()) {
                value = Math.max(value, miniMax(noeud_fils));
            }
        }else{
            value = Integer.MAX_VALUE;
            for (Noeud noeud_fils: noeud.getNoeudFils()) {
                value = Math.min(value, miniMax(noeud_fils));
            }
        }
        noeud.setEvaluation(value);
        return value;
    }
    public int findMax(){
        int max = Integer.MIN_VALUE;
        for (Noeud fils: this.origin.getNoeudFils()) {
            if(fils.getEvaluation()>max){
                max = fils.getEvaluation();
            }
        }

        return max;
    }
}
