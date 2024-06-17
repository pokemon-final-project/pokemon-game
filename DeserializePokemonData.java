import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

class PokemonAbility implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int damage;
    String attribute;

    public PokemonAbility(String name, int damage, String attribute) {
        this.name = name;
        this.damage = damage;
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                ", damage=" + damage +
                ", attribute='" + attribute + '\'' +
                '}';
    }
}

class Pokemon implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    int HP;
    String attribute;
    List<PokemonAbility> abilities;

    // public Pokemon(int id, String name, int HP, String attribute, List<PokemonAbility> abilities) {
    //     this.id = id;
    //     this.name = name;
    //     this.HP = HP;
    //     this.attribute = attribute;
    //     this.abilities = abilities;
    // }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", HP=" + HP +
                ", attribute='" + attribute + '\'' +
                ", abilities=" + abilities +
                '}';
    }
}

public class DeserializePokemonData {
    public static void main(String[] args) {
        // Deserialize the Pokemon list from the file
        try (FileInputStream fis = new FileInputStream("pokemon_data.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<Pokemon> pokemonList = (List<Pokemon>) ois.readObject();
            System.out.println("Successfully read Pokemon data from file.");
            
            // Print each Pokemon's details
            for (Pokemon pokemon : pokemonList) {
                System.out.println(pokemon.name);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
