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
        populate(Virus,"Virus",3);

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
                    item = new Virus(x,y,5); 
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
            if (x != -1){
                //System.out.println(" " + x + " " + y + " " + size*(size-y-1) +x);
                foreground[size*(size-y-1) +x] = item.display();
            }
            //System.out.println("x : "+ Integer.toString(x) + ", y :" + Integer.toString(y));
            item.debug();
        }
              
        for(Iterator e = Virus.iterator(); e.hasNext();){
            Agent item = (Agent) e.next();
            int x = item.position()[0];
            int y = item.position()[1];
            if (x != -1){
                foreground[size*(size-y-1) +x] = item.display();
            }
            item.debug();
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

    public void cell_interacting(Cell A){
        //Cells
        Cell C = (Cell) full_collision_check(A,Cells);
        if (C != null){
            fusion(A,C);
        }

        //Virus  
        Virus V = (Virus) full_collision_check(A,Virus);
        if (V != null){
            if(A.getVirus() == null){
                infection(V,A);
            } else {
                V.death();
            }
            
        }
    }

    public void virus_interacting(Virus A){
        Cell C = (Cell) full_collision_check(A,Cells);
        if (C != null){
            infection(A,C);
        }
    }

    public void fusion(Cell cell1, Cell cell2){

        if(cell1.get_immune()){
            cell1.death();
        } else if (cell2.get_immune()){
            cell2.death();
        } else if(cell1.get_infection_threshold() < cell2.get_infection_threshold()){
            cell1.cure();
            cell2.death();
        } else {
            cell2.cure();
            cell1.death();
        }
    }

    public void infection(Virus virus, Cell cell){
        if (cell.initial_infection(virus)){
            virus.infect(cell);
        }
    }

    public void turn(Player virus_player){
        Agent item2 = null;
        for(Iterator e = Virus.iterator(); e.hasNext();){
            Virus item = (Virus) e.next();
            int [] position = item.update();
            if (position[0] != -1){
                int x = position[0];
                int y = position[1];
                item2 = new Virus(x,y,5);
                boolean legal_movement = false;
                System.out.println("Votre virus s'est cloné ! Vers quelle direction voulez vous déplacer le nouveau né ?");
                while (!legal_movement){
                    legal_movement = virus_player.move(item2,this);
                }
                Virus.add(item2);
                break;
            }
        }

        //Removes dead cells and virus from vector
        Virus.removeIf(e-> !e.isAlive());
        Cells.removeIf(e-> !e.isAlive());

        show();
    }
}
