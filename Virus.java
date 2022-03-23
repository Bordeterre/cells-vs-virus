class Virus extends Agent {
    private Cell host_cell = null;
    private int turns;
    private String original_icon;
    // Création
    public Virus(int pos_x, int pos_y, int turns){
        super(pos_x, pos_y, " v ");
        original_icon = " v ";
        this.turns = turns;
    }

    public void death (){
        if(host_cell != null){
            host_cell.cure();
        }
        true_death();
    }

    // Publier paramètres
    public void debug(){
        int x = position()[0];
        int y = position()[1];
        String tmp = display();
        tmp += "[ " + x + ", " + y + "] ; ";
        tmp += "turns :" + turns;
        if (host_cell != null){
            tmp += " "+host_cell.display();
        }
        System.out.println(tmp);

        
    }

    public int get_turns(){
        return turns;
    }
    // Interaction 
    public void infect(Cell host_cell){
        this.host_cell = host_cell;
        setIcon("[" + host_cell.display().charAt(1) + "]");
    }

    public int[] update(){
        if(host_cell == null){
            turns -=1;
            if (turns <= 0){
                death();
            }
        } else {
            turns +=1;
            if(host_cell.ongoing_infection()){
                host_cell = null;
            }
            if (turns >= 10){
                turns = 4;
                return position();
            }
        }
        int[] nosplit = {-1,-1};
        return nosplit;
    }

    public boolean move(String movement){
        boolean legal_move = true_move(movement);
        if (legal_move && host_cell != null){
            host_cell.cure();
        }
        return legal_move;
    }

    public void exit(){
        host_cell = null;
        setIcon(original_icon);
    }

}