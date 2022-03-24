import java.io.*;
import java.util.*;
class Human_player extends Player{
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
        String chaine = saisie_chaine();
        while(!isInteger(chaine)){
            chaine = saisie_chaine();
        }
        int num = Integer.parseInt(chaine);
        return num;

    }

    public static boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            System.out.print("Veuillez entrer un chiffre entier ! ");
            return false;
        }
    }



    // Création
    public Human_player(boolean cells_team){
        super(true,cells_team);
    }
    //Choisir un agent

    public int[] select_choice(Vector <Agent> Agents){
        System.out.print("Veuillez sélectioner la position x de votre " + get_name() + " : ");
        int x =  saisie_entier() -1;
        System.out.print("Veuillez sélectioner la position y de votre " + get_name() + " : ");
        int y =  saisie_entier() -1;
        return new int []{x,y};     
    }

    public String move_choice(Agent selected){
        System.out.print("Veuillez entrer la direction du déplacement (z/q/s/d) : ");
        String direction = saisie_chaine();
        return direction;
    };
    
}