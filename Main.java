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
    
    //Initialisation
    public static int difficulty_choice(){
        while(true){
            System.out.println("Quelle substance voulez vous injecter aux cellules ? Tapez 1 pour un immuno-supresseur, 2 pour un placebo, et 2 pour un vaccin");
            String choice = saisie_chaine();
            switch(choice){
                case "1" : return 1;
                case "2" : return 2;
                case "3" : return 3;
                default : System.out.println ("Tapez 1 pour un immuno-supresseur, 2 pour un placebo, et 2 pour un vaccin");
            }
        }
    }
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
    public static boolean turn(Board board, Player virus_player,Player cells_player){
        if(board.check_victory()){
            return false;
        }
        System.out.println("Virus turn");
        virus_player.turn(board,1);

        if(board.check_victory()){
            return false;
        }
        System.out.println("Cell turn");
        cells_player.turn(board,1);

        if(board.check_victory()){
            return false;
        }
        System.out.println("Board turn");
        board.turn(virus_player);
        return true;
    }

    //Main
    public static void main(String[] args){
        int difficulty = difficulty_choice();
        Board board = new Board(5,1);
        System.out.println("Player one, Virus ! Tapez h pour un joueur humain, ou r pour un joueur robot");
        Player virus_player = player_choice(false);
        System.out.println("Player two, Cells ! Tapez h pour un joueur humain, ou r pour un joueur robot");
        Player cells_player = player_choice(true);

        board.show();
        boolean playing = true;
        while(playing){
            playing = turn(board,virus_player,cells_player);
        }
    }
}
