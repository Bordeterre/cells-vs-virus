class Virus extends Agent {
    private Cell host_cell = null;
    private int turns;
    // Création
    public Virus(int pos_x, int pos_y, int turns){
        super(pos_x, pos_y, " v ");
        this.turns = turns;
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
}