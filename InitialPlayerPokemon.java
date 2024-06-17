import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class InitialPlayerPokemon {
    private static List<Pokemon> pokemonList;
    public List<Pokemon> player1List;
    public List<Pokemon> player2List;
    
    public InitialPlayerPokemon(List<Integer> player1PokemonId,List<Integer> player2PokemonId) {
        player1List = new ArrayList<>();
        player2List = new ArrayList<>();
        
        //讀取所有Pokemon
        try (FileInputStream fis = new FileInputStream("pokemon_data.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            pokemonList = (List<Pokemon>) ois.readObject();
            System.out.println("Successfully read Pokemon data from file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i<player1PokemonId.size();i++){
            int index = player1PokemonId.get(i); 
            player1List.add(pokemonList.get(index-1));
        }
        for(int i=0;i<player2PokemonId.size();i++){
            int index = player2PokemonId.get(i); 
            player2List.add(pokemonList.get(index-1));
        }
        // System.out.println(player1List);


    }

    public static void main(String[] args) {
    }
}
