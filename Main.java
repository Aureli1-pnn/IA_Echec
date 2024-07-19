import IntelligenceArtificielle.Constante;
import Jeu.Jeu;
import Joueur.Joueur;

public class Main {
    public static void main(String[] args) {

        // Décommenter pour créer un fichier Jar compatible avec Arena

        Joueur ia = new Joueur("STAR", true);
        Joueur arena = new Joueur("Arena", false);
        Jeu jeu = new Jeu();
        jeu.lancerPartie(ia, arena);

        // Décommenter pour se faire affronter MiniMax et Alpha-bêta
        /*
        Jeu jeu = new Jeu();
        jeu.miniMaxVsAlphaBeta();
         */
    }
}