import java.io.*;
import java.util.*;
import java.lang.*;

public class Main{
    //Game turn
    public static void turn(Board board, Player virus_player,Player cells_player){
        Agent selected;
        // joueur virus x1
        selected = virus_player.select(board);
        virus_player.move(selected);
        // Joueur cellules x4   
        
    }

    //Main
    public static void main(String[] args){

        Board board = new Board(10,1);
        Player cells_player = new Human_player(true);
        Player virus_player = new Human_player(false);

        
        for (int i = 0 ; i<3; i++){
            turn(board,virus_player,cells_player);
            board.show();
        }
    }
}