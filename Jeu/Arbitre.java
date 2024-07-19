package Jeu;

import Mouvement.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Arbitre {

    // Méthodes
    public List<Mouvement> mouvementsPossible(Echiquier echiquier, Couleur couleur){

        List<Mouvement> list = new ArrayList<>();

        // Parcours toutes les cases du plateau
        for (int ligne = 0; ligne < 8; ligne++){
            for (int colonne = 0; colonne < 8; colonne++){
                if(echiquier.getCase(ligne, colonne).getOccupe()){
                    // Si la pièce est de la couleur du joueur qui doit jouer alors on détermine son type
                    if(echiquier.getCase(ligne, colonne).getPiece().getCouleurPiece() == couleur){
                        // Cas d'un pion
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Pion")){
                            list.addAll(mouvementPossiblePion(echiquier, ligne, colonne, couleur));
                        }
                        // Cas d'une tour ou d'une reine
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Tour")
                                || Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Reine")){
                            list.addAll(mouvementPossibleTourReine(echiquier, ligne, colonne, couleur));
                        }
                        // Cas d'un fou ou d'une reine
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Fou")
                                || Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Reine")){
                            list.addAll(mouvementPossibleFouReine(echiquier, ligne, colonne, couleur));
                        }
                        // Cas d'un cavalier
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Cavalier")){
                            list.addAll(mouvementPossibleCavalier(echiquier, ligne, colonne, couleur));
                        }
                        // Cas d'un roi
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Roi")){
                            list.addAll(mouvementPossibleRoi(echiquier, ligne, colonne, couleur));
                        }
                    }
                }
            }
        }

        return list;
    }

    public List<Mouvement> mouvementPossiblePion(Echiquier echiquier, int ligne, int colonne, Couleur couleur){

        List<Mouvement> list = new ArrayList<>();

        if(couleur == Couleur.Blanc){
            // Avance d'un pas
            if(ligne < 7 && !echiquier.getCase(ligne+1, colonne).getOccupe()){
                if(ligne == 6){
                    list.add(new Promotion(colonne, colonne, ligne, 7, false, false, couleur, 'q'));
                }else{
                    list.add(new Deplacement(colonne, colonne, ligne, ligne+1, false));
                }
            }
            // Capture en haut à droite
            if(ligne < 7 && colonne < 7 && echiquier.getCase(ligne+1, colonne+1).getOccupe()){
                if(echiquier.getCase(ligne+1, colonne+1).getPiece().getCouleurPiece() == Couleur.Noir){
                    if(ligne == 6){
                        list.add(new Promotion(colonne, colonne+1, ligne, 7, true, false, couleur, 'q'));
                    }else{
                        list.add(new Deplacement(colonne, colonne+1, ligne, ligne+1, true));
                    }
                }
            }
            // Capture en haut à gauche
            if(ligne < 7 && colonne > 0 && echiquier.getCase(ligne+1,colonne-1).getOccupe()){
                if(echiquier.getCase(ligne+1,colonne-1).getPiece().getCouleurPiece() == Couleur.Noir) {
                    if(ligne == 6){
                        list.add(new Promotion(colonne, colonne - 1, ligne, 7, true, false, couleur, 'q'));
                    }else {
                        list.add(new Deplacement(colonne, colonne - 1, ligne, ligne + 1, true));
                    }
                }
            }
            // Cas d'un pion en position de départ
            if(ligne == 1){
                if(!echiquier.getCase(3,colonne).getOccupe() && !echiquier.getCase(2,colonne).getOccupe()){
                    list.add(new Deplacement(colonne, colonne, 1, 3, false));
                }
            }
        }
        else{
            // Descend d'un pas
            if(ligne > 0 && !echiquier.getCase(ligne-1,colonne).getOccupe()){
                if(ligne == 1){
                    list.add(new Promotion(colonne, colonne, ligne, 0, false, false, couleur, 'q'));
                }else{
                    list.add(new Deplacement(colonne, colonne, ligne, ligne-1, false));
                }
            }
            // Capture en bas à droite
            if(ligne > 0 && colonne < 7 && echiquier.getCase(ligne-1,colonne+1).getOccupe()){
                if(echiquier.getCase(ligne-1,colonne+1).getPiece().getCouleurPiece() == Couleur.Blanc){
                    if(ligne == 1){
                        list.add(new Promotion(colonne, colonne + 1, ligne, 0, true, false, couleur, 'q'));
                    }else {
                        list.add(new Deplacement(colonne, colonne + 1, ligne, ligne - 1, true));
                    }
                }
            }
            // Capture en bas à gauche
            if(ligne > 0 && colonne > 0 && echiquier.getCase(ligne-1,colonne-1).getOccupe()){
                if(echiquier.getCase(ligne-1,colonne-1).getPiece().getCouleurPiece() == Couleur.Blanc) {
                    if(ligne == 1){
                        list.add(new Promotion(colonne, colonne - 1, ligne, 0, true, false, couleur, 'q'));
                    }else{
                        list.add(new Deplacement(colonne, colonne - 1, ligne, ligne - 1, true));
                    }
                }
            }
            // Cas d'un pion noir en position de départ
            if(ligne == 6){
                if(!echiquier.getCase(4,colonne).getOccupe() && !echiquier.getCase(5,colonne).getOccupe()){
                    list.add(new Deplacement(colonne, colonne, 6, 4, false));
                }
            }
        }

        return list;
    }

    public List<Mouvement> mouvementPossibleTourReine(Echiquier echiquier, int ligne, int colonne, Couleur couleur){

        List<Mouvement> list = new ArrayList<>();
        boolean sortirBoucle = false;

        // Déplacement horizontal à gauche

        for (int k = colonne-1; k >= 0 && !sortirBoucle; k--){
            if(!echiquier.getCase(ligne,k).getOccupe()){
                list.add(new Deplacement(colonne, k, ligne, ligne, false));
            }
            else{
                if(echiquier.getCase(ligne,k).getPiece().getCouleurPiece() != couleur){
                    list.add(new Deplacement(colonne, k, ligne, ligne, true));
                }
                sortirBoucle=true; // On ne peut pas aller plus loin à gauche
            }
        }
        sortirBoucle=false;
        // Déplacement horizontal à droite
        for (int k = colonne+1; k < 8 && !sortirBoucle; k++){
            if(!echiquier.getCase(ligne,k).getOccupe()){
                list.add(new Deplacement(colonne, k, ligne, ligne, false));
            }
            else{
                if(echiquier.getCase(ligne,k).getPiece().getCouleurPiece() != couleur) {
                    list.add(new Deplacement(colonne, k, ligne, ligne, true));
                }
                sortirBoucle=true; // On ne peut pas aller plus loin à droite
            }
        }
        sortirBoucle=false;
        // Déplacement vertical en haut
        for (int k = ligne+1; k < 8 && !sortirBoucle; k++){
            if(!echiquier.getCase(k,colonne).getOccupe()){
                list.add(new Deplacement(colonne, colonne, ligne, k, false));
            }
            else{
                if(echiquier.getCase(k,colonne).getPiece().getCouleurPiece() != couleur){
                    list.add(new Deplacement(colonne, colonne, ligne, k, true));
                }
                sortirBoucle=true; // On ne peut pas aller plus loin en haut
            }
        }
        sortirBoucle=false;
        // Déplacement vertical en bas
        for (int k = ligne-1; k >= 0 && !sortirBoucle; k--){
            if(!echiquier.getCase(k,colonne).getOccupe()){
                list.add(new Deplacement(colonne, colonne, ligne, k, false));
            }
            else{
                if(echiquier.getCase(k,colonne).getPiece().getCouleurPiece() != couleur){
                    list.add(new Deplacement(colonne, colonne, ligne, k, true));
                }
                sortirBoucle=true; // On ne peut pas aller plus loin en haut
            }
        }

        return list;
    }

    public List<Mouvement> mouvementPossibleFouReine(Echiquier echiquier, int ligne, int colonne, Couleur couleur){

        List<Mouvement> list = new ArrayList<>();
        boolean sortirBoucle = false;

        // Déplacement en diagonale en haut à gauche
        for (int k = 1; k < 8 && !sortirBoucle; k++){
            if(ligne+k < 8 && colonne-k >= 0){
                if(!echiquier.getCase(ligne+k, colonne-k).getOccupe()){
                    list.add(new Deplacement(colonne, colonne-k, ligne, ligne+k, false));
                }
                else{
                    if(echiquier.getCase(ligne+k, colonne-k).getPiece().getCouleurPiece() != couleur){
                        list.add(new Deplacement(colonne, colonne-k, ligne, ligne+k, true));
                    }
                    sortirBoucle=true; // On ne peut pas aller plus loin en haut à gauche
                }
            }
            else{ sortirBoucle=true;}
        }
        sortirBoucle=false;
        // Déplacement en diagonale en haut à droite
        for (int k = 1; k < 8 && !sortirBoucle; k++){
            if(ligne+k < 8 && colonne+k < 8){
                if(!echiquier.getCase(ligne+k, colonne+k).getOccupe()){
                    list.add(new Deplacement(colonne, colonne+k, ligne, ligne+k, false));
                }
                else{
                    if(echiquier.getCase(ligne+k, colonne+k).getPiece().getCouleurPiece() != couleur){
                        list.add(new Deplacement(colonne, colonne+k, ligne, ligne+k, true));
                    }
                    sortirBoucle=true; // On ne peut pas aller plus loin en haut à droite
                }
            }
            else{ sortirBoucle=true;}
        }
        sortirBoucle=false;
        // Déplacement en diagonale en bas à droite
        for (int k = 1; k < 8 && !sortirBoucle; k++){
            if(ligne-k >= 0 && colonne+k < 8){
                if(!echiquier.getCase(ligne-k, colonne+k).getOccupe()){
                    list.add(new Deplacement(colonne, colonne+k, ligne, ligne-k, false));
                }
                else{
                    if(echiquier.getCase(ligne-k, colonne+k).getPiece().getCouleurPiece() != couleur){
                        list.add(new Deplacement(colonne, colonne+k, ligne, ligne-k, true));
                    }
                    sortirBoucle=true; // On ne peut pas aller plus loin en bas à droite
                }
            }
            else{ sortirBoucle=true;}
        }
        sortirBoucle=false;
        // Déplacement en diagonale en bas à gauche
        for (int k = 1; k < 8 && !sortirBoucle; k++){
            if(ligne-k >= 0 && colonne-k >= 0){
                if(!echiquier.getCase(ligne-k, colonne-k).getOccupe()){
                    list.add(new Deplacement(colonne, colonne-k, ligne, ligne-k, false));
                }
                else{
                    if(echiquier.getCase(ligne-k, colonne-k).getPiece().getCouleurPiece() != couleur){
                        list.add(new Deplacement(colonne, colonne-k, ligne, ligne-k, true));
                    }
                    sortirBoucle=true; // On ne peut pas aller plus loin en bas à gauche
                }
            }
            else{ sortirBoucle=true;}
        }

        return list;
    }

    public List<Mouvement> mouvementPossibleCavalier(Echiquier echiquier, int ligne, int colonne, Couleur couleur){

        List<Mouvement> list = new ArrayList<>();

        if(ligne >= 2 && colonne >= 1){
            if(!echiquier.getCase(ligne-2, colonne-1).getOccupe()){
                list.add(new Deplacement(colonne, colonne-1, ligne, ligne-2, false));
            }else {
                if (echiquier.getCase(ligne - 2, colonne - 1).getPiece().getCouleurPiece() != couleur) {
                    list.add(new Deplacement(colonne, colonne - 1, ligne, ligne - 2, true));
                }
            }
        }
        if(ligne >= 2 && colonne < 7){
            if(!echiquier.getCase(ligne-2, colonne+1).getOccupe()){
                list.add(new Deplacement(colonne, colonne+1, ligne, ligne-2, false));
            }else {
                if (echiquier.getCase(ligne - 2, colonne + 1).getPiece().getCouleurPiece() != couleur) {
                    list.add(new Deplacement(colonne, colonne + 1, ligne, ligne - 2, true));
                }
            }
        }
        if(ligne >= 1 && colonne < 6){
            if(!echiquier.getCase(ligne-1, colonne+2).getOccupe()){
                list.add(new Deplacement(colonne, colonne+2, ligne, ligne-1, false));
            }else {
                if (echiquier.getCase(ligne - 1, colonne + 2).getPiece().getCouleurPiece() != couleur) {
                    list.add(new Deplacement(colonne, colonne + 2, ligne, ligne - 1, true));
                }
            }
        }
        if(ligne >= 1 && colonne >= 2){
            if(!echiquier.getCase(ligne-1, colonne-2).getOccupe()){
                list.add(new Deplacement(colonne, colonne-2, ligne, ligne-1, false));
            }else {
                if (echiquier.getCase(ligne - 1, colonne - 2).getPiece().getCouleurPiece() != couleur) {
                    list.add(new Deplacement(colonne, colonne - 2, ligne, ligne - 1, true));
                }
            }
        }
        if(ligne < 7 && colonne >= 2){
            if(!echiquier.getCase(ligne+1, colonne-2).getOccupe()){
                list.add(new Deplacement(colonne, colonne-2, ligne, ligne+1, false));
            }else {
                if (echiquier.getCase(ligne + 1, colonne - 2).getPiece().getCouleurPiece() != couleur) {
                    list.add(new Deplacement(colonne, colonne - 2, ligne, ligne + 1, true));
                }
            }
        }
        if(ligne < 7 && colonne < 6){
            if(!echiquier.getCase(ligne+1, colonne+2).getOccupe()){
                list.add(new Deplacement(colonne, colonne+2, ligne, ligne+1, false));
            }else {
                if (echiquier.getCase(ligne + 1, colonne + 2).getPiece().getCouleurPiece() != couleur) {
                    list.add(new Deplacement(colonne, colonne + 2, ligne, ligne + 1, true));
                }
            }
        }
        if(ligne < 6 && colonne >= 1){
            if(!echiquier.getCase(ligne+2, colonne-1).getOccupe()){
                list.add(new Deplacement(colonne, colonne-1, ligne, ligne+2, false));
            }else {
                if (echiquier.getCase(ligne + 2, colonne - 1).getPiece().getCouleurPiece() != couleur) {
                    list.add(new Deplacement(colonne, colonne - 1, ligne, ligne + 2, true));
                }
            }
        }
        if(ligne < 6 && colonne < 7){
            if(!echiquier.getCase(ligne+2, colonne+1).getOccupe()){
                list.add(new Deplacement(colonne, colonne+1, ligne, ligne+2, false));
            }else {
                if (echiquier.getCase(ligne + 2, colonne + 1).getPiece().getCouleurPiece() != couleur) {
                    list.add(new Deplacement(colonne, colonne + 1, ligne, ligne + 2, true));
                }
            }
        }

        return list;
    }

    public List<Mouvement> mouvementPossibleRoi(Echiquier echiquier, int ligne, int colonne, Couleur couleur){

        List<Mouvement> list = new ArrayList<>();

        // Petit Roque
        if(couleur == Couleur.Blanc){
            if(ligne == 0 && colonne == 4){
                if(echiquier.getCase(ligne, colonne).getPiece().getRoquePossible() && !this.verifierEchec(echiquier, Couleur.Blanc)){
                    if(echiquier.getCase(0,7).getOccupe()
                            && !echiquier.getCase(0,5).getOccupe()
                            && !echiquier.getCase(0,6).getOccupe()){
                        if(Objects.equals(echiquier.getCase(0,7).getPiece().getNomPiece(), "Tour")
                                && echiquier.getCase(0,7).getPiece().getRoquePossible()){
                            Deplacement deplacement1 = new Deplacement(4, 5, 0, 0, false);
                            echiquier.deplacerPiece(deplacement1, false);
                            if(!this.verifierEchec(echiquier, Couleur.Blanc)){
                                Deplacement deplacement2 = new Deplacement(5, 6, 0, 0, false);
                                echiquier.deplacerPiece(deplacement2, false);
                                if(!this.verifierEchec(echiquier, Couleur.Blanc)){
                                    list.add(new Roque(Couleur.Blanc, true, false));
                                }
                                echiquier.deplacerPiece(deplacement2.mouvementInverse(), false);
                            }
                            echiquier.deplacerPiece(deplacement1.mouvementInverse(), false);
                        }
                    }
                }
            }
        }
        else{
            if(ligne == 7 && colonne == 4){
                if(echiquier.getCase(ligne, colonne).getPiece().getRoquePossible() && !this.verifierEchec(echiquier, Couleur.Noir)){
                    if(echiquier.getCase(7,7).getOccupe()
                            && !echiquier.getCase(7,5).getOccupe()
                            && !echiquier.getCase(7,6).getOccupe()){
                        if(Objects.equals(echiquier.getCase(7,7).getPiece().getNomPiece(), "Tour")
                                && echiquier.getCase(7,7).getPiece().getRoquePossible()){
                            Deplacement deplacement1 = new Deplacement(4, 5, 7, 7, false);
                            echiquier.deplacerPiece(deplacement1, false);
                            if(!this.verifierEchec(echiquier, Couleur.Noir)){
                                Deplacement deplacement2 = new Deplacement(5, 6, 7, 7, false);
                                echiquier.deplacerPiece(deplacement2, false);
                                if(!this.verifierEchec(echiquier, Couleur.Noir)){
                                    list.add(new Roque(Couleur.Noir, true, false));
                                }
                                echiquier.deplacerPiece(deplacement2.mouvementInverse(), false);
                            }
                            echiquier.deplacerPiece(deplacement1.mouvementInverse(), false);
                        }
                    }
                }
            }
        }
        // Grand Roque
        if(couleur == Couleur.Blanc){
            if(ligne == 0 && colonne == 4){
                if(echiquier.getCase(ligne, colonne).getPiece().getRoquePossible() && !this.verifierEchec(echiquier, Couleur.Blanc)){
                    if(echiquier.getCase(0,0).getOccupe()
                            && !echiquier.getCase(0,1).getOccupe()
                            && !echiquier.getCase(0,2).getOccupe()
                            && !echiquier.getCase(0,3).getOccupe()){
                        if(Objects.equals(echiquier.getCase(0,0).getPiece().getNomPiece(), "Tour")
                                && echiquier.getCase(0,0).getPiece().getRoquePossible()){
                            Deplacement deplacement11 = new Deplacement(4, 3, 0, 0, false);
                            echiquier.deplacerPiece(deplacement11, false);
                            if(!this.verifierEchec(echiquier, Couleur.Blanc)){
                                Deplacement deplacement12 = new Deplacement(3, 2, 0, 0, false);
                                echiquier.deplacerPiece(deplacement12, false);
                                if(!this.verifierEchec(echiquier, Couleur.Blanc)){
                                    list.add(new Roque(Couleur.Blanc, false, false));
                                }
                                echiquier.deplacerPiece(deplacement12.mouvementInverse(), false);
                            }
                            echiquier.deplacerPiece(deplacement11.mouvementInverse(), false);
                        }
                    }
                }
            }
        }
        else{
            if(ligne == 7 && colonne == 4){
                if(echiquier.getCase(ligne, colonne).getPiece().getRoquePossible() && !this.verifierEchec(echiquier, Couleur.Noir)){
                    if(echiquier.getCase(7,0).getOccupe()
                            && !echiquier.getCase(7,1).getOccupe()
                            && !echiquier.getCase(7,2).getOccupe()
                            && !echiquier.getCase(7,3).getOccupe()){
                        if(Objects.equals(echiquier.getCase(7,0).getPiece().getNomPiece(), "Tour")
                                && echiquier.getCase(7,0).getPiece().getRoquePossible()){
                            Deplacement deplacement11 = new Deplacement(4, 3, 7, 7, false);
                            echiquier.deplacerPiece(deplacement11, false);
                            if(!this.verifierEchec(echiquier, Couleur.Noir)){
                                Deplacement deplacement12 = new Deplacement(3, 2, 7, 7, false);
                                echiquier.deplacerPiece(deplacement12, false);
                                if(!this.verifierEchec(echiquier, Couleur.Noir)){
                                    list.add(new Roque(Couleur.Noir, false, false));
                                }
                                echiquier.deplacerPiece(deplacement12.mouvementInverse(), false);
                            }
                            echiquier.deplacerPiece(deplacement11.mouvementInverse(), false);
                        }
                    }
                }
            }
        }

        if(ligne > 0){
            // Descend
            if(!echiquier.getCase(ligne-1, colonne).getOccupe()){
                list.add(new Deplacement(colonne, colonne, ligne, ligne-1, false));
            }else {
                if (echiquier.getCase(ligne - 1, colonne).getPiece().getCouleurPiece() != couleur){
                    list.add(new Deplacement(colonne, colonne, ligne, ligne-1, true));
                }
            }
            // Descend à gauche
            if(colonne > 0){
                if(!echiquier.getCase(ligne-1, colonne-1).getOccupe()){
                    list.add(new Deplacement(colonne, colonne-1, ligne, ligne-1, false));
                }else {
                    if (echiquier.getCase(ligne - 1, colonne-1).getPiece().getCouleurPiece() != couleur){
                        list.add(new Deplacement(colonne, colonne-1, ligne, ligne-1, true));
                    }
                }
            }
            // Descend à droite
            if(colonne < 7){
                if(!echiquier.getCase(ligne-1, colonne+1).getOccupe()){
                    list.add(new Deplacement(colonne, colonne+1, ligne, ligne-1, false));
                }else {
                    if (echiquier.getCase(ligne - 1, colonne+1).getPiece().getCouleurPiece() != couleur){
                        list.add(new Deplacement(colonne, colonne+1, ligne, ligne-1, true));
                    }
                }
            }
        }
        if(ligne < 7){
            // Monte
            if(!echiquier.getCase(ligne+1, colonne).getOccupe()){
                list.add(new Deplacement(colonne, colonne, ligne, ligne+1, false));
            }else {
                if (echiquier.getCase(ligne+1, colonne).getPiece().getCouleurPiece() != couleur){
                    list.add(new Deplacement(colonne, colonne, ligne, ligne+1, true));
                }
            }
            // Monte à gauche
            if(colonne > 0){
                if(!echiquier.getCase(ligne+1, colonne-1).getOccupe()){
                    list.add(new Deplacement(colonne, colonne-1, ligne, ligne+1, false));
                }else {
                    if (echiquier.getCase(ligne+1, colonne-1).getPiece().getCouleurPiece() != couleur){
                        list.add(new Deplacement(colonne, colonne-1, ligne, ligne+1, true));
                    }
                }
            }
            // Monte à droite
            if(colonne < 7){
                if(!echiquier.getCase(ligne+1, colonne+1).getOccupe()){
                    list.add(new Deplacement(colonne, colonne+1, ligne, ligne+1, false));
                }else {
                    if (echiquier.getCase(ligne + 1, colonne+1).getPiece().getCouleurPiece() != couleur){
                        list.add(new Deplacement(colonne, colonne+1, ligne, ligne+1, true));
                    }
                }
            }
        }
        // Droite
        if(colonne < 7){
            if(!echiquier.getCase(ligne, colonne+1).getOccupe()){
                list.add(new Deplacement(colonne, colonne+1, ligne, ligne, false));
            }else {
                if (echiquier.getCase(ligne, colonne+1).getPiece().getCouleurPiece() != couleur){
                    list.add(new Deplacement(colonne, colonne+1, ligne, ligne, true));
                }
            }
        }
        // Gauche
        if(colonne > 0){
            if(!echiquier.getCase(ligne, colonne-1).getOccupe()){
                list.add(new Deplacement(colonne, colonne-1, ligne, ligne, false));
            }else {
                if (echiquier.getCase(ligne, colonne-1).getPiece().getCouleurPiece() != couleur){
                    list.add(new Deplacement(colonne, colonne-1, ligne, ligne, true));
                }
            }
        }

        return list;
    }

    public boolean verifierEchec(Echiquier echiquier, Couleur couleur){

        int ligneRoi = 0, colonneRoi = 0;
        boolean sortirBoucle = false;

        // Récupération de la position du roi pour vérification des éventuelles échecs
        for (int i = 0; i < 8 && !sortirBoucle; i++) {
            for (int j = 0; j < 8 && !sortirBoucle; j++) {
                if(echiquier.getCase(i, j).getOccupe()){
                    if(echiquier.getCase(i, j).getPiece().getCouleurPiece() == couleur){
                        if(Objects.equals(echiquier.getCase(i, j).getPiece().getNomPiece(), "Roi")){
                            ligneRoi = i;
                            colonneRoi = j;
                            sortirBoucle=true;
                        }
                    }
                }
            }
        }
        // Vérification des deux cases en diagonales
        if(couleur == Couleur.Blanc){
            if(ligneRoi < 6){
                if(colonneRoi > 0){
                    if(echiquier.getCase(ligneRoi+1, colonneRoi-1).getOccupe()){
                        if(echiquier.getCase(ligneRoi+1, colonneRoi-1).getPiece().getCouleurPiece() == Couleur.Noir){
                            String nom_piece = echiquier.getCase(ligneRoi+1, colonneRoi-1).getPiece().getNomPiece();
                            if(Objects.equals(nom_piece, "Pion")
                                    || Objects.equals(nom_piece, "Reine")
                                    || Objects.equals(nom_piece, "Fou")
                                    || Objects.equals(nom_piece, "Roi")){
                                return true;
                            }
                        }
                    }
                }
                if(colonneRoi < 7){
                    if(echiquier.getCase(ligneRoi+1, colonneRoi+1).getOccupe()){
                        if(echiquier.getCase(ligneRoi+1, colonneRoi+1).getPiece().getCouleurPiece() == Couleur.Noir){
                            String nom_piece = echiquier.getCase(ligneRoi+1, colonneRoi+1).getPiece().getNomPiece();
                            if(Objects.equals(nom_piece, "Pion")
                                    || Objects.equals(nom_piece, "Reine")
                                    || Objects.equals(nom_piece, "Fou")
                                    || Objects.equals(nom_piece, "Roi")){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else {
            if (ligneRoi > 1) {
                if (colonneRoi > 0) {
                    if (echiquier.getCase(ligneRoi - 1, colonneRoi - 1).getOccupe()) {
                        if (echiquier.getCase(ligneRoi - 1, colonneRoi - 1).getPiece().getCouleurPiece() == Couleur.Blanc) {
                            String nom_piece = echiquier.getCase(ligneRoi - 1, colonneRoi - 1).getPiece().getNomPiece();
                            if (Objects.equals(nom_piece, "Pion")
                                    || Objects.equals(nom_piece, "Reine")
                                    || Objects.equals(nom_piece, "Fou")
                                    || Objects.equals(nom_piece, "Roi")) {
                                return true;
                            }
                        }
                    }
                }
                if (colonneRoi < 7) {
                    if (echiquier.getCase(ligneRoi - 1, colonneRoi + 1).getOccupe()) {
                        if (echiquier.getCase(ligneRoi - 1, colonneRoi + 1).getPiece().getCouleurPiece() == Couleur.Blanc) {
                            String nom_piece = echiquier.getCase(ligneRoi - 1, colonneRoi + 1).getPiece().getNomPiece();
                            if (Objects.equals(nom_piece, "Pion")
                                    || Objects.equals(nom_piece, "Reine")
                                    || Objects.equals(nom_piece, "Fou")
                                    || Objects.equals(nom_piece, "Roi")) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        sortirBoucle=false;
        // Vérification en haut
        for (int i = ligneRoi+1; i < 8 && !sortirBoucle; i++) {
            if(echiquier.getCase(i, colonneRoi).getOccupe()){
                if(echiquier.getCase(i, colonneRoi).getPiece().getCouleurPiece() != couleur){
                    if(i == ligneRoi+1
                            && Objects.equals(echiquier.getCase(i, colonneRoi).getPiece().getNomPiece(), "Roi")){
                        return true;
                    }
                    if(Objects.equals(echiquier.getCase(i, colonneRoi).getPiece().getNomPiece(), "Tour")
                            || Objects.equals(echiquier.getCase(i, colonneRoi).getPiece().getNomPiece(), "Reine")){
                        return true;
                    }
                }else{
                    sortirBoucle=true;
                }
            }
        }
        sortirBoucle=false;
        // Vérification en bas
        for (int i = ligneRoi-1; i >= 0 && !sortirBoucle; i--) {
            if(echiquier.getCase(i, colonneRoi).getOccupe()){
                if(echiquier.getCase(i, colonneRoi).getPiece().getCouleurPiece() != couleur){
                    if(i == ligneRoi-1
                            && Objects.equals(echiquier.getCase(i, colonneRoi).getPiece().getNomPiece(), "Roi")){
                        return true;
                    }
                    if(Objects.equals(echiquier.getCase(i, colonneRoi).getPiece().getNomPiece(), "Tour")
                            || Objects.equals(echiquier.getCase(i, colonneRoi).getPiece().getNomPiece(), "Reine")){
                        return true;
                    }
                }else{
                    sortirBoucle=true;
                }
            }
        }
        sortirBoucle=false;
        // Vérification à droite
        for (int i = colonneRoi+1; i < 8 && !sortirBoucle; i++) {
            if(echiquier.getCase(ligneRoi, i).getOccupe()){
                if(echiquier.getCase(ligneRoi, i).getPiece().getCouleurPiece() != couleur){
                    if(i == colonneRoi+1
                            && Objects.equals(echiquier.getCase(ligneRoi, i).getPiece().getNomPiece(), "Roi")){
                        return true;
                    }
                    if(Objects.equals(echiquier.getCase(ligneRoi, i).getPiece().getNomPiece(), "Tour")
                            || Objects.equals(echiquier.getCase(ligneRoi, i).getPiece().getNomPiece(), "Reine")){
                        return true;
                    }
                }else{
                    sortirBoucle=true;
                }
            }
        }
        sortirBoucle=false;
        // Vérification à gauche
        for (int i = colonneRoi-1; i >= 0 && !sortirBoucle; i--) {
            if(echiquier.getCase(ligneRoi, i).getOccupe()){
                if(echiquier.getCase(ligneRoi, i).getPiece().getCouleurPiece() != couleur){
                    if(i == colonneRoi-1
                            && Objects.equals(echiquier.getCase(ligneRoi, i).getPiece().getNomPiece(), "Roi")){
                        return true;
                    }
                    if(Objects.equals(echiquier.getCase(ligneRoi, i).getPiece().getNomPiece(), "Tour")
                            || Objects.equals(echiquier.getCase(ligneRoi, i).getPiece().getNomPiece(), "Reine")){
                        return true;
                    }
                }else{
                    sortirBoucle=true;
                }
            }
        }
        sortirBoucle=false;
        // Vérification en bas à gauche
        for (int i = 1; i < 8 && !sortirBoucle; i++) {
            if(colonneRoi-i >= 0 && ligneRoi-i >= 0){
                if(echiquier.getCase(ligneRoi-i, colonneRoi-i).getOccupe()){
                    if(echiquier.getCase(ligneRoi-i, colonneRoi-i).getPiece().getCouleurPiece() != couleur){
                        if(i == 1
                                && Objects.equals(echiquier.getCase(ligneRoi-1, colonneRoi-1).getPiece().getNomPiece(), "Roi")){
                            return true;
                        }
                        if(Objects.equals(echiquier.getCase(ligneRoi-i, colonneRoi-i).getPiece().getNomPiece(), "Reine")
                                || Objects.equals(echiquier.getCase(ligneRoi-i, colonneRoi-i).getPiece().getNomPiece(), "Fou")){
                            return true;
                        }
                    }
                    else{
                        sortirBoucle=true;
                    }
                }
            }
            else{
                sortirBoucle=true;
            }
        }
        sortirBoucle=false;
        // Vérification en bas à droite
        for (int i = 1; i < 8 && !sortirBoucle; i++) {
            if(colonneRoi+i < 8 && ligneRoi-i >= 0){
                if(echiquier.getCase(ligneRoi-i, colonneRoi+i).getOccupe()){
                    if(echiquier.getCase(ligneRoi-i, colonneRoi+i).getPiece().getCouleurPiece() != couleur){
                        if(i == 1
                                && Objects.equals(echiquier.getCase(ligneRoi-1, colonneRoi+1).getPiece().getNomPiece(), "Roi")){
                            return true;
                        }
                        if(Objects.equals(echiquier.getCase(ligneRoi-i, colonneRoi+i).getPiece().getNomPiece(), "Reine")
                                || Objects.equals(echiquier.getCase(ligneRoi-i, colonneRoi+i).getPiece().getNomPiece(), "Fou")){
                            return true;
                        }
                    }
                    else{
                        sortirBoucle=true;
                    }
                }
            }
            else{
                sortirBoucle=true;
            }
        }
        sortirBoucle=false;
        // Vérification en haut à droite
        for (int i = 1; i < 8 && !sortirBoucle; i++) {
            if(colonneRoi+i < 8 && ligneRoi+i < 8){
                if(echiquier.getCase(ligneRoi+i, colonneRoi+i).getOccupe()){
                    if(echiquier.getCase(ligneRoi+i, colonneRoi+i).getPiece().getCouleurPiece() != couleur){
                        if(i == 1
                                && Objects.equals(echiquier.getCase(ligneRoi+1, colonneRoi+1).getPiece().getNomPiece(), "Roi")){
                            return true;
                        }
                        if(Objects.equals(echiquier.getCase(ligneRoi+i, colonneRoi+i).getPiece().getNomPiece(), "Reine")
                                || Objects.equals(echiquier.getCase(ligneRoi+i, colonneRoi+i).getPiece().getNomPiece(), "Fou")){
                            return true;
                        }
                    }
                    else{
                        sortirBoucle=true;
                    }
                }
            }
            else{
                sortirBoucle=true;
            }
        }
        sortirBoucle=false;
        // Vérification en haut à gauche
        for (int i = 1; i < 8 && !sortirBoucle; i++) {
            if(colonneRoi-i >= 0 && ligneRoi+i < 8){
                if(echiquier.getCase(ligneRoi+i, colonneRoi-i).getOccupe()){
                    if(echiquier.getCase(ligneRoi+i, colonneRoi-i).getPiece().getCouleurPiece() != couleur){
                        if(i == 1
                                && Objects.equals(echiquier.getCase(ligneRoi+1, colonneRoi-1).getPiece().getNomPiece(), "Roi")){
                            return true;
                        }
                        if(Objects.equals(echiquier.getCase(ligneRoi+i, colonneRoi-i).getPiece().getNomPiece(), "Reine")
                                || Objects.equals(echiquier.getCase(ligneRoi+i, colonneRoi-i).getPiece().getNomPiece(), "Fou")){
                            return true;
                        }
                    }
                    else{
                        sortirBoucle=true;
                    }
                }
            }
            else{
                sortirBoucle=true;
            }
        }
        // Vérification cavalier
        if(ligneRoi >= 2 && colonneRoi >= 1){
            if(echiquier.getCase(ligneRoi-2, colonneRoi-1).getOccupe()
                    && echiquier.getCase(ligneRoi-2, colonneRoi-1).getPiece().getCouleurPiece() != couleur
                    && Objects.equals(echiquier.getCase(ligneRoi-2, colonneRoi-1).getPiece().getNomPiece(), "Cavalier")){
                return true;
            }
        }
        if(ligneRoi >= 2 && colonneRoi < 7){
            if(echiquier.getCase(ligneRoi-2, colonneRoi+1).getOccupe()
                    && echiquier.getCase(ligneRoi-2, colonneRoi+1).getPiece().getCouleurPiece() != couleur
                    && Objects.equals(echiquier.getCase(ligneRoi-2, colonneRoi+1).getPiece().getNomPiece(), "Cavalier")){
                return true;
            }
        }
        if(ligneRoi >= 1 && colonneRoi < 6){
            if(echiquier.getCase(ligneRoi-1, colonneRoi+2).getOccupe()
                    && echiquier.getCase(ligneRoi-1, colonneRoi+2).getPiece().getCouleurPiece() != couleur
                    && Objects.equals(echiquier.getCase(ligneRoi-1, colonneRoi+2).getPiece().getNomPiece(), "Cavalier")){
                return true;
            }
        }
        if(ligneRoi >= 1 && colonneRoi >= 2){
            if(echiquier.getCase(ligneRoi-1, colonneRoi-2).getOccupe()
                    && echiquier.getCase(ligneRoi-1, colonneRoi-2).getPiece().getCouleurPiece() != couleur
                    && Objects.equals(echiquier.getCase(ligneRoi-1, colonneRoi-2).getPiece().getNomPiece(), "Cavalier")){
                return true;
            }
        }
        if(ligneRoi < 7 && colonneRoi >= 2){
            if(echiquier.getCase(ligneRoi+1, colonneRoi-2).getOccupe()
                    && echiquier.getCase(ligneRoi+1, colonneRoi-2).getPiece().getCouleurPiece() != couleur
                    && Objects.equals(echiquier.getCase(ligneRoi+1, colonneRoi-2).getPiece().getNomPiece(), "Cavalier")){
                return true;
            }
        }
        if(ligneRoi < 7 && colonneRoi < 6){
            if(echiquier.getCase(ligneRoi+1, colonneRoi+2).getOccupe()
                    && echiquier.getCase(ligneRoi+1, colonneRoi+2).getPiece().getCouleurPiece() != couleur
                    && Objects.equals(echiquier.getCase(ligneRoi+1, colonneRoi+2).getPiece().getNomPiece(), "Cavalier")){
                return true;
            }
        }
        if(ligneRoi < 6 && colonneRoi >= 1){
            if(echiquier.getCase(ligneRoi+2, colonneRoi-1).getOccupe()
                    && echiquier.getCase(ligneRoi+2, colonneRoi-1).getPiece().getCouleurPiece() != couleur
                    && Objects.equals(echiquier.getCase(ligneRoi+2, colonneRoi-1).getPiece().getNomPiece(), "Cavalier")){
                return true;
            }
        }
        if(ligneRoi < 6 && colonneRoi < 7){
            if(echiquier.getCase(ligneRoi+2, colonneRoi+1).getOccupe()
                    && echiquier.getCase(ligneRoi+2, colonneRoi+1).getPiece().getCouleurPiece() != couleur
                    && Objects.equals(echiquier.getCase(ligneRoi+2, colonneRoi+1).getPiece().getNomPiece(), "Cavalier")){
                return true;
            }
        }
        return false;
    }

    public int calculValeurPieceBlanche(Echiquier echiquier){
        int valeur_blanc = 0;
        int valeur_noir = 0;
        try{
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if(echiquier.getCase(i,j).getOccupe()){
                        if(echiquier.getCase(i,j).getPiece().getCouleurPiece() == Couleur.Blanc){
                            valeur_blanc += echiquier.getCase(i,j).getPiece().getValeurPiece();
                        }else{
                            valeur_noir += echiquier.getCase(i,j).getPiece().getValeurPiece();
                        }
                    }
                }
            }

            return valeur_blanc-valeur_noir;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int nombreCoupPossible(Couleur couleur, Echiquier echiquier){

        int nombre_coup_possible = 0;

        // Parcours toutes les cases du plateau
        for (int ligne = 0; ligne < 8; ligne++){
            for (int colonne = 0; colonne < 8; colonne++){
                if(echiquier.getCase(ligne, colonne).getOccupe()){
                    // Si la piÃ¨ce est de la couleur du joueur qui doit jouer alors on dÃ©termine son type
                    if(echiquier.getCase(ligne, colonne).getPiece().getCouleurPiece() == couleur){
                        // Cas d'un pion
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Pion")){
                            nombre_coup_possible += nombreCoupPossiblePion(ligne, colonne, couleur, echiquier);
                        }
                        // Cas d'une tour ou d'une reine
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Tour")
                                || Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Reine")){
                            nombre_coup_possible += nombreCoupPossibleTourReine(ligne, colonne, couleur, echiquier);
                        }
                        // Cas d'un fou ou d'une reine
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Fou")
                                || Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Reine")){
                            nombre_coup_possible += nombreCoupPossibleFouReine(ligne, colonne, couleur, echiquier);
                        }
                        // Cas d'un cavalier
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Cavalier")){
                            nombre_coup_possible += nombreCoupPossibleCavalier(ligne, colonne, couleur, echiquier);
                        }
                        // Cas d'un roi
                        if(Objects.equals(echiquier.getCase(ligne, colonne).getPiece().getNomPiece(), "Roi")){
                            nombre_coup_possible += nombreCoupPossibleRoi(ligne, colonne, couleur, echiquier);
                        }
                    }
                }
            }
        }

        return nombre_coup_possible;
    }

    public int nombreCoupPossiblePion(int ligne, int colonne, Couleur couleur, Echiquier echiquier){

        int nombre_coup_possible = 0;

        if(couleur == Couleur.Blanc){
            // Avance d'un pas
            if(ligne < 7 && !echiquier.getCase(ligne+1, colonne).getOccupe()){
                nombre_coup_possible++;
            }
            // Capture en haut Ã  droite
            if(ligne < 7 && colonne < 7 && echiquier.getCase(ligne+1, colonne+1).getOccupe()){
                if(echiquier.getCase(ligne+1, colonne+1).getPiece().getCouleurPiece() == Couleur.Noir){
                    nombre_coup_possible++;
                }
            }
            // Capture en haut Ã  gauche
            if(ligne < 7 && colonne > 0 && echiquier.getCase(ligne+1, colonne-1).getOccupe()){
                if(echiquier.getCase(ligne+1, colonne-1).getPiece().getCouleurPiece() == Couleur.Noir) {
                    nombre_coup_possible++;
                }
            }
            // Cas d'un pion en position de dÃ©part
            if(ligne == 1){
                if(!echiquier.getCase(3, colonne).getOccupe()){
                    nombre_coup_possible++;
                }
            }
        }
        else{
            // Descend d'un pas
            if(ligne > 0 && !echiquier.getCase(ligne-1, colonne).getOccupe()){
                nombre_coup_possible++;
            }
            // Capture en bas Ã  droite
            if(ligne > 0 && colonne < 7 && echiquier.getCase(ligne-1, colonne+1).getOccupe()){
                if(echiquier.getCase(ligne-1, colonne+1).getPiece().getCouleurPiece() == Couleur.Blanc){
                    nombre_coup_possible++;
                }
            }
            // Capture en bas Ã  gauche
            if(ligne > 0 && colonne > 0 && echiquier.getCase(ligne-1, colonne-1).getOccupe()){
                if(echiquier.getCase(ligne-1, colonne-1).getPiece().getCouleurPiece() == Couleur.Blanc) {
                    nombre_coup_possible++;
                }
            }
            // Cas d'un pion noir en position de dÃ©part
            if(ligne == 6){
                if(!echiquier.getCase(4, colonne).getOccupe()){
                    nombre_coup_possible++;
                }
            }
        }

        return nombre_coup_possible;
    }

    public int nombreCoupPossibleTourReine(int ligne, int colonne, Couleur couleur, Echiquier echiquier){

        int nombre_coup_possible = 0;
        boolean sortirBoucle = false;

        // DÃ©placement horizontal Ã  gauche

        for (int k = colonne-1; k >= 0 && !sortirBoucle; k--){
            if(!echiquier.getCase(ligne, k).getOccupe()){
                nombre_coup_possible++;
            }
            else{
                if(echiquier.getCase(ligne, k).getPiece().getCouleurPiece() != couleur){
                    nombre_coup_possible++;
                }
                sortirBoucle=true; // On ne peut pas aller plus loin Ã  gauche
            }
        }
        sortirBoucle=false;
        // DÃ©placement horizontal Ã  droite
        for (int k = colonne+1; k < 8 && !sortirBoucle; k++){
            if(!echiquier.getCase(ligne, k).getOccupe()){
                nombre_coup_possible++;
            }
            else{
                if(echiquier.getCase(ligne, k).getPiece().getCouleurPiece() != couleur) {
                    nombre_coup_possible++;
                }
                sortirBoucle=true; // On ne peut pas aller plus loin Ã  droite
            }
        }
        sortirBoucle=false;
        // DÃ©placement vertical en haut
        for (int k = ligne+1; k < 8 && !sortirBoucle; k++){
            if(!echiquier.getCase(k, colonne).getOccupe()){
                nombre_coup_possible++;
            }
            else{
                if(echiquier.getCase(k, colonne).getPiece().getCouleurPiece() != couleur){
                    nombre_coup_possible++;
                }
                sortirBoucle=true; // On ne peut pas aller plus loin en haut
            }
        }
        sortirBoucle=false;
        // DÃ©placement vertical en bas
        for (int k = ligne-1; k >= 0 && !sortirBoucle; k--){
            if(!echiquier.getCase(k, colonne).getOccupe()){
                nombre_coup_possible++;
            }
            else{
                if(echiquier.getCase(k, colonne).getPiece().getCouleurPiece() != couleur){
                    nombre_coup_possible++;
                }
                sortirBoucle=true; // On ne peut pas aller plus loin en haut
            }
        }

        return nombre_coup_possible;
    }

    public int nombreCoupPossibleFouReine(int ligne, int colonne, Couleur couleur, Echiquier echiquier){

        int nombre_coup_possible = 0;
        boolean sortirBoucle = false;

        // DÃ©placement en diagonale en haut Ã  gauche
        for (int k = 1; k < 8 && !sortirBoucle; k++){
            if(ligne+k < 8 && colonne-k >= 0){
                if(!echiquier.getCase(ligne+k, colonne-k).getOccupe()){
                    nombre_coup_possible++;
                }
                else{
                    if(echiquier.getCase(ligne+k, colonne-k).getPiece().getCouleurPiece() != couleur){
                        nombre_coup_possible++;
                    }
                    sortirBoucle=true; // On ne peut pas aller plus loin en haut Ã  gauche
                }
            }
            else{ sortirBoucle=true;}
        }
        sortirBoucle=false;
        // DÃ©placement en diagonale en haut Ã  droite
        for (int k = 1; k < 8 && !sortirBoucle; k++){
            if(ligne+k < 8 && colonne+k < 8){
                if(!echiquier.getCase(ligne+k, colonne+k).getOccupe()){
                    nombre_coup_possible++;
                }
                else{
                    if(echiquier.getCase(ligne+k, colonne+k).getPiece().getCouleurPiece() != couleur){
                        nombre_coup_possible++;
                    }
                    sortirBoucle=true; // On ne peut pas aller plus loin en haut Ã  droite
                }
            }
            else{ sortirBoucle=true;}
        }
        sortirBoucle=false;
        // DÃ©placement en diagonale en bas Ã  droite
        for (int k = 1; k < 8 && !sortirBoucle; k++){
            if(ligne-k >= 0 && colonne+k < 8){
                if(!echiquier.getCase(ligne-k, colonne+k).getOccupe()){
                    nombre_coup_possible++;
                }
                else{
                    if(echiquier.getCase(ligne-k, colonne+k).getPiece().getCouleurPiece() != couleur){
                        nombre_coup_possible++;
                    }
                    sortirBoucle=true; // On ne peut pas aller plus loin en bas Ã  droite
                }
            }
            else{ sortirBoucle=true;}
        }
        sortirBoucle=false;
        // DÃ©placement en diagonale en bas Ã  gauche
        for (int k = 1; k < 8 && !sortirBoucle; k++){
            if(ligne-k >= 0 && colonne-k >= 0){
                if(!echiquier.getCase(ligne-k, colonne-k).getOccupe()){
                    nombre_coup_possible++;
                }
                else{
                    if(echiquier.getCase(ligne-k, colonne-k).getPiece().getCouleurPiece() != couleur){
                        nombre_coup_possible++;
                    }
                    sortirBoucle=true; // On ne peut pas aller plus loin en bas Ã  gauche
                }
            }
            else{ sortirBoucle=true;}
        }

        return nombre_coup_possible;
    }

    public int nombreCoupPossibleCavalier(int ligne, int colonne, Couleur couleur, Echiquier echiquier){

        int nombre_coup_possible = 0;

        if(ligne >= 2 && colonne >= 1){
            if(!echiquier.getCase(ligne-2, colonne-1).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne - 2, colonne - 1).getPiece().getCouleurPiece() != couleur) {
                    nombre_coup_possible++;
                }
            }
        }
        if(ligne >= 2 && colonne < 7){
            if(!echiquier.getCase(ligne-2, colonne+1).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne - 2, colonne + 1).getPiece().getCouleurPiece() != couleur) {
                    nombre_coup_possible++;
                }
            }
        }
        if(ligne >= 1 && colonne < 6){
            if(!echiquier.getCase(ligne-1, colonne+2).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne - 1, colonne + 2).getPiece().getCouleurPiece() != couleur) {
                    nombre_coup_possible++;
                }
            }
        }
        if(ligne >= 1 && colonne >= 2){
            if(!echiquier.getCase(ligne-1, colonne-2).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne - 1, colonne - 2).getPiece().getCouleurPiece() != couleur) {
                    nombre_coup_possible++;
                }
            }
        }
        if(ligne < 7 && colonne >= 2){
            if(!echiquier.getCase(ligne+1, colonne-2).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne + 1, colonne - 2).getPiece().getCouleurPiece() != couleur) {
                    nombre_coup_possible++;
                }
            }
        }
        if(ligne < 7 && colonne < 6){
            if(!echiquier.getCase(ligne+1, colonne+2).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne + 1, colonne + 2).getPiece().getCouleurPiece() != couleur) {
                    nombre_coup_possible++;
                }
            }
        }
        if(ligne < 6 && colonne >= 1){
            if(!echiquier.getCase(ligne+2, colonne-1).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne + 2, colonne - 1).getPiece().getCouleurPiece() != couleur) {
                    nombre_coup_possible++;
                }
            }
        }
        if(ligne < 6 && colonne < 7){
            if(!echiquier.getCase(ligne+2, colonne+1).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne + 2, colonne + 1).getPiece().getCouleurPiece() != couleur) {
                    nombre_coup_possible++;
                }
            }
        }

        return nombre_coup_possible;
    }

    public int nombreCoupPossibleRoi(int ligne, int colonne, Couleur couleur, Echiquier echiquier){

        int nombre_coup_possible = 0;

        // Petit Roque
        if(couleur == Couleur.Blanc){
            if(ligne == 0 && colonne == 4){
                if(echiquier.getCase(ligne, colonne).getPiece().getRoquePossible() && !this.verifierEchec(echiquier, Couleur.Blanc)){
                    if(echiquier.getCase(0,7).getOccupe()
                            && !echiquier.getCase(0,5).getOccupe()
                            && !echiquier.getCase(0,6).getOccupe()){
                        if(Objects.equals(echiquier.getCase(0,7).getPiece().getNomPiece(), "Tour")
                                && echiquier.getCase(0,7).getPiece().getRoquePossible()){
                            Deplacement deplacement1 = new Deplacement(4, 5, 0, 0, false);
                            echiquier.deplacerPiece(deplacement1, false);
                            if(!this.verifierEchec(echiquier, Couleur.Blanc)){
                                Deplacement deplacement2 = new Deplacement(5, 6, 0, 0, false);
                                echiquier.deplacerPiece(deplacement2, false);
                                if(!this.verifierEchec(echiquier, Couleur.Blanc)){
                                    nombre_coup_possible++;
                                }
                                echiquier.deplacerPiece(deplacement2.mouvementInverse(), false);
                            }
                            echiquier.deplacerPiece(deplacement1.mouvementInverse(), false);
                        }
                    }
                }
            }
        }
        else{
            if(ligne == 7 && colonne == 4){
                if(echiquier.getCase(ligne, colonne).getPiece().getRoquePossible() && !this.verifierEchec(echiquier, Couleur.Noir)){
                    if(echiquier.getCase(7,7).getOccupe()
                            && !echiquier.getCase(7,5).getOccupe()
                            && !echiquier.getCase(7,6).getOccupe()){
                        if(Objects.equals(echiquier.getCase(7,7).getPiece().getNomPiece(), "Tour")
                                && echiquier.getCase(7,7).getPiece().getRoquePossible()){
                            Deplacement deplacement1 = new Deplacement(4, 5, 7, 7, false);
                            echiquier.deplacerPiece(deplacement1, false);
                            if(!this.verifierEchec(echiquier, Couleur.Noir)){
                                Deplacement deplacement2 = new Deplacement(5, 6, 7, 7, false);
                                echiquier.deplacerPiece(deplacement2, false);
                                if(!this.verifierEchec(echiquier, Couleur.Noir)){
                                    nombre_coup_possible++;
                                }
                                echiquier.deplacerPiece(deplacement2.mouvementInverse(), false);
                            }
                            echiquier.deplacerPiece(deplacement1.mouvementInverse(), false);
                        }
                    }
                }
            }
        }
        // Grand Roque
        if(couleur == Couleur.Blanc){
            if(ligne == 0 && colonne == 4){
                if(echiquier.getCase(ligne, colonne).getPiece().getRoquePossible() && !this.verifierEchec(echiquier, Couleur.Blanc)){
                    if(echiquier.getCase(0,0).getOccupe()
                            && !echiquier.getCase(0,1).getOccupe()
                            && !echiquier.getCase(0,2).getOccupe()
                            && !echiquier.getCase(0,3).getOccupe()){
                        if(Objects.equals(echiquier.getCase(0,0).getPiece().getNomPiece(), "Tour")
                                && echiquier.getCase(0,0).getPiece().getRoquePossible()){
                            Deplacement deplacement11 = new Deplacement(4, 3, 0, 0, false);
                            echiquier.deplacerPiece(deplacement11, false);
                            if(!this.verifierEchec(echiquier, Couleur.Blanc)){
                                Deplacement deplacement12 = new Deplacement(3, 2, 0, 0, false);
                                echiquier.deplacerPiece(deplacement12, false);
                                if(!this.verifierEchec(echiquier, Couleur.Blanc)){
                                    nombre_coup_possible++;
                                }
                                echiquier.deplacerPiece(deplacement12.mouvementInverse(), false);
                            }
                            echiquier.deplacerPiece(deplacement11.mouvementInverse(), false);
                        }
                    }
                }
            }
        }
        else{
            if(ligne == 7 && colonne == 4){
                if(echiquier.getCase(ligne, colonne).getPiece().getRoquePossible() && !this.verifierEchec(echiquier, Couleur.Noir)){
                    if(echiquier.getCase(7,0).getOccupe()
                            && !echiquier.getCase(7,1).getOccupe()
                            && !echiquier.getCase(7,2).getOccupe()
                            && !echiquier.getCase(7,3).getOccupe()){
                        if(Objects.equals(echiquier.getCase(7,0).getPiece().getNomPiece(), "Tour")
                                && echiquier.getCase(7,0).getPiece().getRoquePossible()){
                            Deplacement deplacement11 = new Deplacement(4, 3, 7, 7, false);
                            echiquier.deplacerPiece(deplacement11, false);
                            if(!this.verifierEchec(echiquier, Couleur.Noir)){
                                Deplacement deplacement12 = new Deplacement(3, 2, 7, 7, false);
                                echiquier.deplacerPiece(deplacement12, false);
                                if(!this.verifierEchec(echiquier, Couleur.Noir)){
                                    nombre_coup_possible++;
                                }
                                echiquier.deplacerPiece(deplacement12.mouvementInverse(), false);
                            }
                            echiquier.deplacerPiece(deplacement11.mouvementInverse(), false);
                        }
                    }
                }
            }
        }

        if(ligne > 0){
            // Descend
            if(!echiquier.getCase(ligne-1, colonne).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne - 1, colonne).getPiece().getCouleurPiece() != couleur){
                    nombre_coup_possible++;
                }
            }
            // Descend Ã  gauche
            if(colonne > 0){
                if(!echiquier.getCase(ligne-1, colonne-1).getOccupe()){
                    nombre_coup_possible++;
                }else {
                    if (echiquier.getCase(ligne - 1, colonne-1).getPiece().getCouleurPiece() != couleur){
                        nombre_coup_possible++;
                    }
                }
            }
            // Descend Ã  droite
            if(colonne < 7){
                if(!echiquier.getCase(ligne-1, colonne+1).getOccupe()){
                    nombre_coup_possible++;
                }else {
                    if (echiquier.getCase(ligne - 1, colonne+1).getPiece().getCouleurPiece() != couleur){
                        nombre_coup_possible++;
                    }
                }
            }
        }
        if(ligne < 7){
            // Monte
            if(!echiquier.getCase(ligne+1, colonne).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne + 1, colonne).getPiece().getCouleurPiece() != couleur){
                    nombre_coup_possible++;
                }
            }
            // Monte Ã  gauche
            if(colonne > 0){
                if(!echiquier.getCase(ligne+1, colonne-1).getOccupe()){
                    nombre_coup_possible++;
                }else {
                    if (echiquier.getCase(ligne + 1, colonne-1).getPiece().getCouleurPiece() != couleur){
                        nombre_coup_possible++;
                    }
                }
            }
            // Monte Ã  droite
            if(colonne < 7){
                if(!echiquier.getCase(ligne+1, colonne+1).getOccupe()){
                    nombre_coup_possible++;
                }else {
                    if (echiquier.getCase(ligne + 1, colonne+1).getPiece().getCouleurPiece() != couleur){
                        nombre_coup_possible++;
                    }
                }
            }
        }
        // Droite
        if(colonne < 7){
            if(!echiquier.getCase(ligne, colonne+1).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne, colonne+1).getPiece().getCouleurPiece() != couleur){
                    nombre_coup_possible++;
                }
            }
        }
        // Gauche
        if(colonne > 0){
            if(!echiquier.getCase(ligne, colonne-1).getOccupe()){
                nombre_coup_possible++;
            }else {
                if (echiquier.getCase(ligne, colonne-1).getPiece().getCouleurPiece() != couleur){
                    nombre_coup_possible++;
                }
            }
        }

        return nombre_coup_possible;
    }

}