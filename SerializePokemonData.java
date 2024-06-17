import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public Pokemon(int id, String name, int HP, String attribute, List<PokemonAbility> abilities) {
        this.id = id;
        this.name = name;
        this.HP = HP;
        this.attribute = attribute;
        this.abilities = abilities;
    }

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

public class SerializePokemonData {
    public static void main(String[] args) {
        // Create a list of Pokemon
        List<Pokemon> pokemonList = new ArrayList<>();
        String[] names = { "妙蛙種子", "霸王花","小火龍",  "噴火龍","傑尼龜","水箭龜", "皮卡丘", "雷丘", "可逹鴨",  "喵喵", "小拳石", "大蒽鴨", "大岩蛇", "鯉魚王", "卡比獸", "超夢", "快龍", "雷公", "洛奇亞", "波士可多拉", "正電拍拍", "負電拍拍", "鐵螫龍蝦", "風鈴鈴", "大比鳥","九尾", "怪力", "呆呆獸", "韓國魚", "菜英文"};
        String[] attributes = {"fire", "grass", "water", "electric", "fairy", "grass", "fire", "water", "electric", "fairy", "ground", "fighting", "normal", "water", "normal", "fire", "electric", "water", "psychic", "dark", "psychic", "fairy", "bug", "dragon", "dragon", "dragon", "electric", "psychic", "water", "dragon"};
        
        Random random = new Random();
        
        for (int i = 0; i < 30; i++) {
            List<PokemonAbility> abilities = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                abilities.add(new PokemonAbility("能力" + j, random.nextInt(50) + 1, attributes[i]));
            }
            pokemonList.add(new Pokemon(i + 1, names[i], random.nextInt(200) + 50, attributes[i], abilities));
        }

        // Serialize the Pokemon list to a file
        try (FileOutputStream fos = new FileOutputStream("pokemon_data.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(pokemonList);
            System.out.println("Successfully serialized Pokemon data to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
