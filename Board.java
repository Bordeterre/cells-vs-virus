import java.io.*;
import java.util.*;
import java.lang.*;

class Board{
    private int size;
    public Vector <Agent> Cells;
    public Vector <Agent> Virus;

    // Utilitaires
    public boolean collision_check(Agent A, Agent B){
        if (Arrays.equals(A.position(),B.position())){
            return true;
        }
        return false;
    }

    public Agent full_collision_check(Agent A, Vector <Agent> team){
        for(Iterator e = team.iterator(); e.hasNext();){
            Agent B = (Agent) e.next();
            if (A != B && collision_check(A,B)){
                return B;
            }
        }
        return null;
    }
    // Initialisation
    public Board(int size,int difficulty){
        this.size = size;

        Cells = new Vector<Agent>();
        Virus = new Vector<Agent>();

        populate(Cells, "X_cell",1);
        populate(Cells, "Y_cell",1);
        populate(Cells, "Z_cell",1);
        //populate(Virus,"Virus",10);

        show();
    }

    public void populate(Vector<Agent> team, String type, int amount){
        amount += team.size();

        while(team.size() < amount){
            int x = (int)(Math.random()*size);
            int y = (int)(Math.random()*size);
            Agent item = null;
            switch(type){
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

            if(full_collision_check(item,Cells) == null && full_collision_check(item,Virus) == null){
                team.add(item);             
            }
        }
    }

    //Publication
    public int getSize(){
        return size;
    }

    public void show(){
        //Foreground
        String [] foreground = new String[size*size];
        Arrays.fill(foreground,"   ");
        for(Iterator e = Cells.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            int x = item.position()[0];
            int y = item.position()[1];
            foreground[size*(size-y-1) +x] = item.display();
            //System.out.println("x : "+ Integer.toString(x) + ", y :" + Integer.toString(y));
        }
              
        for(Iterator e = Virus.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            int x = item.position()[0];
            int y = item.position()[1];
            foreground[size*(size-y-1) +x] = item.display();
            //System.out.println("x : "+ Integer.toString(x) + ", y : " + Integer.toString(y));
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

    //Tour
    public void cell_interacting(Agent A){
        //Cells
        Agent C = full_collision_check(A,Cells);
        if (C != null){
            fusion(A,C);
        }

        //Virus  
        Agent V = full_collision_check(A,Virus);
        if (V != null){
            infection(V,A);
        }
    }

    public void virus_interacting(Agent A){
        Agent C = full_collision_check(A,Cells);
        if (C != null){
            infection(A,C);
        }
    }

    public void fusion(Agent cell1, Agent cell2){
        boolean immune = cell2.get_immune();
        int infection_threshold = cell2.get_infection_threshold();
        cell2.death();

        cell1.fusion(immune, infection_threshold);

    }

    public void infection(Agent virus, Agent cell){
        if (cell.initial_infection){
            //??
        }
    }
}