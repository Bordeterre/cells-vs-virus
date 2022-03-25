class Virus extends Agent {
    private Cell host_cell = null; //Adress de l'éventuelle cellule que le virus infecte. 
    private int turns; // Nombre de tours de vie du virus
    private String original_icon; //Icone originelle du virus (l'icone change lorsque le virus infecte une cellule)
    // Création
    public Virus(int pos_x, int pos_y, int turns){
        super(pos_x, pos_y, " v ");
        original_icon = " v ";
        this.turns = turns;
    }

    //Mort
    public void death (){
        if(host_cell != null){
            host_cell.cure();
        }
        true_death();
    }

    // Publier paramètres de position et de tours restants
    public void debug(){
        int x = position()[0];
        int y = position()[1];
        String tmp = display();
        tmp += "[ " + (x+1) + ", " + (y+1) + "] ";
        tmp += "("+turns +" tours restants)";
        if (host_cell != null){
            tmp += " "+host_cell.display();
        }
        System.out.println(tmp);

        
    }

    public int get_turns(){
        return turns;
    }
    public void set_turns(int turns){
        this.turns = turns;
    }

    // Infecte la cellule "host_cell", appelé par Board.infection
    public void infect(Cell host_cell){
        this.host_cell = host_cell;
        setIcon("[" + host_cell.display().charAt(1) + "]");
    }

    // Met à jour le nombre de tours restant du virus (et met à jour une éventuelle celluel hôte), et tue ou divise éventuellement le virs
    public int[] update(){
        if(host_cell == null){
            turns -=1;
        } else {
            turns +=1;
            host_cell.ongoing_infection();

        }
        if (turns >= 10){
            turns = 4;
            return position();
        }
        if (turns <= 0){
            death();
        }

        int[] nosplit = {-1,-1};
        return nosplit;
    }

    // Déplace le virus. Renvoie true si le déplacement est valide, false sinon. Soigne éventuellement une cellule hôte
    public boolean move(String movement){
        boolean legal_move = true_move(movement);
        if (legal_move && host_cell != null){
            host_cell.cure();
        }
        return legal_move;
    }

    // Permet de sortir le virus de sa cellule hôte. Appelé par Cell.cure()
    public void exit(){
        host_cell = null;
        setIcon(original_icon);
    }

}
