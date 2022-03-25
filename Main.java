import java.io.*;
import java.util.*;
import java.lang.*;

public class Main{
    // Utilitaires
    public static String saisie_chaine (){
        try {
            BufferedReader buff = new BufferedReader
                (new InputStreamReader(System.in));
            String chaine=buff.readLine();
            return chaine;
        }
        catch(IOException e) {
            System.out.println(" impossible de travailler" +e);
            return null;
        }
    }

    public static int saisie_entier (){
        String chaine = saisie_chaine();
        while(!isInteger(chaine)){
            chaine = saisie_chaine();
        }
        int num = Integer.parseInt(chaine);
        return num;

    }

    public static boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            System.out.print("Veuillez entrer un chiffre entier ! ");
            return false;
        }
    }


    //Initialisation de la partie
    // Choix du niveau de difficulté
    // Choix de la taille du plateau
    public static int difficulty_choice(){
        while(true){
            System.out.println("Quelle substance voulez vous injecter aux cellules ? Tapez 1 pour un immuno-supresseur, 2 pour un placebo, et 3 pour un vaccin");
            String choice = saisie_chaine();
            switch(choice){
                case "1" : return 1;
                case "2" : return 2;
                case "3" : return 3;
                default : System.out.println ("Tapez 1 pour un immuno-supresseur, 2 pour un placebo, et 3 pour un vaccin");
            }
        }
    }

    public static int size_choice(){
        System.out.println("Quelle taille de plateau voulez vous utiliser (5 recommendé pour une partie pas trop longue)");
        int choice = saisie_entier();
        while(choice <3){
            System.out.print("Plateau trop petit ! Veuillez choisir une valeur superieure à 2 : ");
            choice = saisie_entier();

        }
        return choice;
    
    }



    // Choix du type de joueur
    public static Player player_choice(Boolean cell_team){
        while(true){
            String choice = saisie_chaine();
            switch(choice){
                case "h" : return new Human_player(cell_team);
                case "r" : return new Robot_player(cell_team);
                default : System.out.println ("Tapez h pour un joueur humain, ou r pour un joueur robot");
            }
        }
    }

    //Game turn
    public static void turn(Board board, Player virus_player,Player cells_player){
        System.out.println("Virus turn");
        virus_player.turn(board,4);

        System.out.println("Cell turn");
        cells_player.turn(board,1);

        System.out.println("Board turn");
        board.turn(virus_player);
    }

    //Main
    public static void main(String[] args){
        int size = size_choice();
        int difficulty = difficulty_choice();
        Board board = new Board(size,difficulty);
        System.out.println("Player one, Virus ! Tapez h pour un joueur humain, ou r pour un joueur robot");
        Player virus_player = player_choice(false);
        System.out.println("Player two, Cells ! Tapez h pour un joueur humain, ou r pour un joueur robot");
        Player cells_player = player_choice(true);

        board.show();
        while(true){
            turn(board,virus_player,cells_player);
        }
    }
}
