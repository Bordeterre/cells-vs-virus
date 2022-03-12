import java.io.*;
import java.util.*;
import java.lang.*;

public class Board{
    //Utility
    public static boolean collision_check(Agent A, Agent B){
        if (Arrays.equals(A.position(),B.position())){
            return true;
        }
        return false;
    }

    public static Agent full_collision_check(Agent A, Vector <Agent> Agents){
        Agent out = null;
        for(Iterator e = Agents.iterator(); e.hasNext();){
            Agent B = (Agent) e.next();
            if (A != B && collision_check(A,B)){
                if (out == null){
                    out = B;
                } else {
                    System.out.println("ERREUR, COLLISION AVEC PLUSIEURS AGENTS !");
                }
            }
        }
        return out;
    }

    //Game initialisation
    public static void initialize(Vector <Agent> Agents, int board_size){
        populate(Agents,board_size, "X_cell",10);
        populate(Agents,board_size, "Y_cell",10);
        populate(Agents,board_size, "Z_cell",10);
        populate(Agents,board_size, "Virus",10);
        
    }

    public static void populate(Vector <Agent> Agents, int board_size, String type_name, int amount){
        amount += Agents.size();
        
        while (Agents.size() < amount){
            Agent item = null;
            int x = (int)(Math.random()*board_size);
            int y = (int)(Math.random()*board_size);
            switch(type_name){
                case "X_cell" :
                    item = new X_cell(x,y);
                    break;
                case "Y_cell" :
                    item = new Y_cell(x,y);
                    break;
                case "Z_cell" :
                    item = new Z_cell(x,y);
                    break;
                case "Virus" :
                    item = new Virus(x,y); 
                    break;
            }
            if( full_collision_check(item,Agents) == null){
                Agents.add(item);             
            }
        }
    }

    //Game turn
    public static void turn( Vector <Agent> Agents){
        // joueur virus x1
        // Joueur cellules x4
        
    }

     public static void display(Vector <Agent> Agents, int size){
        //Foreground
        String [] foreground = new String[size*size];
        Arrays.fill(foreground,"   ");
        for(Iterator e = Agents.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            int x = item.position()[0];
            int y = item.position()[1];
            foreground[size*(size-y-1) +x] = item.display();
            }

        //Background
        String line = "┼";
        for(int x = 0 ; x < size; x++){
            line += "───┼";
        }
        System.out.println(line);

        for(int y = 0 ; y < size; y++){
            line = "│";
            for(int x = 0 ; x < size; x++){
                line += foreground[y*size + x];
                line += "│";
            }  
            System.out.println(line) ;  

            line = "┼";
            for(int x = 0 ; x < size; x++){
                line += "───┼";
            }
            System.out.println(line);
        }
    }

    //Main
    public static void main(String[] args){
        int size = 10;
        Vector <Agent> Agents = new Vector<Agent>();
        String virus_player = "human";
        String cells_player = "human";

        initialize(Agents, size);
        display(Agents, size);
    }
}