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
        try{
            BufferedReader buff = new BufferedReader
                (new InputStreamReader(System.in));
            String chaine=buff.readLine();
            int num = Integer.parseInt(chaine);
            return num;
        }
        catch(IOException e){return 0;}
    }
    
    //Initialisation
    public static Player player_choice(Boolean cell_team){
        String choice = "";
        while(true){
            choice = saisie_chaine();
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
        Board board = new Board(3,1);
        System.out.println("Player one, Virus ! Tapez h pour un joueur humain, ou r pour un joueur robot");
        Player virus_player = player_choice(false);
        System.out.println("Player two, Cells ! Tapez h pour un joueur humain, ou r pour un joueur robot");
        Player cells_player = player_choice(true);

        boolean playing = true;
        while(playing){
            playing = turn(board,virus_player,cells_player);
        }
    }
}
