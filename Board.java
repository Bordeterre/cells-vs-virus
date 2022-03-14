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

    public static Agent full_collision_check(Agent A, Vector <Agent> Cells, Vector <Agent> Virus){
        Agent out = null;
        for(Iterator e = Cells.iterator(); e.hasNext();){
            Agent B = (Agent) e.next();
            if (A != B && collision_check(A,B)){
                if (out == null){
                    out = B;
                } else {
                    System.out.println("ERREUR, COLLISION AVEC PLUSIEURS AGENTS !");
                }
            }
        }
        for(Iterator e = Virus.iterator(); e.hasNext();){
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
    public static void initialize(Vector <Agent> Cells, Vector <Agent> Virus, int board_size){
        //populate(Cells,Virus,board_size, "X_cell",10);
        //populate(Cells,Virus,board_size, "Y_cell",10);
        //populate(Cells,Virus,board_size, "Z_cell",10);
        populate(Virus,Cells,board_size, "Virus",1);
        
    }

    public static void populate(Vector <Agent> Agents, Vector <Agent> Foes_agents, int board_size, String type_name, int amount){
        amount += Agents.size();
        
        while (Agents.size() < amount){
            
            int x = (int)(Math.random()*board_size);
            int y = (int)(Math.random()*board_size);
            Agent item = null;

            switch(type_name){
                case "X_cell" :
                    item = new Cell(x,y,true,1,"X");
                    break;
                case "Y_cell" :
                    item = new Cell(x,y,false,3, "Y"); 
                    break;
                case "Z_cell" :
                    item = new Cell(x,y,false,1, "Z");
                    break;
                case "Virus" :
                    item = new Virus(x,y); 
                    break;
            }

        
            if( full_collision_check(item,Agents, Foes_agents) == null){
                Agents.add(item);             
            }
        }
    }

    //Game turn
    public static void turn(Vector <Agent> Cells, Vector <Agent> Virus, Player virus_player,Player cells_player,int size){
        Agent selected;
        // joueur virus x1
        selected = virus_player.select(Virus, size);
        virus_player.move(selected);
        // Joueur cellules x4   
        
    }

    public static void display(Vector <Agent> Cells, Vector <Agent> Virus, int size){
        //Foreground
        String [] foreground = new String[size*size];
        Arrays.fill(foreground,"   ");
        for(Iterator e = Cells.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            int x = item.position()[0];
            int y = item.position()[1];
            foreground[size*(size-y-1) +x] = item.display();
            System.out.println("x : "+ Integer.toString(x) + ", y :" + Integer.toString(y));
        }
            
            
        for(Iterator e = Virus.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            int x = item.position()[0];
            int y = item.position()[1];
            foreground[size*(size-y-1) +x] = item.display();
            System.out.println("x : "+ Integer.toString(x) + ", y : " + Integer.toString(y));
            }


        //Background


        String line = "───┼";
        for(int x = 0 ; x < size; x++){
            line += "───┼";
        }
        System.out.println(line);

        for(int y = 0 ; y < size; y++){
            line = "";
            String y_string = Integer.toString(size-y);
            line += y_string;
            for(int i = 0 ; i < 3 - y_string.length(); i++){
                line += " ";
            }
            line += "│";
            for(int x = 0 ; x < size; x++){
                line += foreground[y*size + x];
                line += "│";
            }  
            System.out.println(line) ;  

            line = "───┼";
            for(int x = 0 ; x < size; x++){
                line += "───┼";
            }
            System.out.println(line);
        }

        line = "   │";
        for (int x = 1; x <= size; x++){
            String x_string = Integer.toString(x);
            line += x_string;
            for(int i = 0 ; i < 3 - x_string.length(); i++){
                line += " ";
            }
            line += "│";
        }
        System.out.println(line);

    }

    //Main
    public static void main(String[] args){
        int size = 10;
        Vector <Agent> Cells = new Vector<Agent>();
        Vector <Agent> Virus = new Vector<Agent>();

        initialize(Cells,Virus, size);
        display(Cells,Virus, size);

        Player cells_player = new Human_player(true);
        Player virus_player = new Human_player(false);


        for (int i = 0 ; i<3; i++){
            turn(Cells,Virus,virus_player,cells_player, size);
            display(Cells,Virus, size);
        }
    }
}