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
    
    
    //Game turn
    public static void turn(Board board, Player virus_player,Player cells_player){
        virus_player.turn(board,1);
        cells_player.turn(board,1);
        board.turn();
        board.show();
        
    }

    public static Player virus_player_choice(){
        String player_virus = "N";
        Player virus_player  = new Robot_player(false);
        //while (player_virus != "H" && player_virus != "R"){
            System.out.println("Player One ! Virus ! Si les virus sont joués par un humain tapez 'H', pour qu'ils soient joués par un robot tapez 'R'");
            player_virus = saisie_chaine();
            switch(player_virus){
                case "H" : virus_player = new Human_player(false);
                        break;
                case "R" : virus_player = new Robot_player(false);
                        break;
                default : System.out.println ("Vous n'avez pas entré de joueur possible.");
            }
        //}
        return virus_player;
    }


    public static Player cells_player_choice(){
        String player_cells = "N";
        Player cells_player = new Robot_player(true);
        //while (player_cells != "H" && player_cells != "R"){
            System.out.println("Player Two ! Cells ! Si les cellules sont joués par un humain tapez 'H', pour qu'elles soient joués par un robot tapez 'R'");
            player_cells = saisie_chaine();
            switch(player_cells){
                case "H" : cells_player = new Human_player(true);
                        break;
                case "R" : cells_player = new Robot_player(true);
                        break;
                default : System.out.println ("Vous n'avez pas entré de joueur possible.");
            }
        //}
        return cells_player;
    }


    //Main
    public static void main(String[] args){
        Board board = new Board(3,1);
        Player virus_player = virus_player_choice();
        Player cells_player = cells_player_choice();

        while(true){
            turn(board,virus_player,cells_player);
        }
    }
}
