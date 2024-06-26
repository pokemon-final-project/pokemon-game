import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Main {
    public static void main(String[] args) {
        GameLogin login = new GameLogin();
        login.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt){
                if ("summit".equals(evt.getPropertyName()) && (int) evt.getNewValue() == 1) {
                    login.dispose();
                    ChoosePokemon choosePokemon = new ChoosePokemon();
                    choosePokemon.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("summit".equals(evt.getPropertyName()) && (int) evt.getNewValue() == 1) {
                                System.out.println(choosePokemon.player1PokemonId);
                                InitialPlayerPokemon initialPlayerPokemon = new InitialPlayerPokemon(choosePokemon.player1PokemonId,choosePokemon.player2PokemonId);
                                FightPokemon fightPokemon = new FightPokemon(initialPlayerPokemon.player1List,initialPlayerPokemon.player2List);
                                choosePokemon.dispose();
                                System.out.println("寶可夢選擇已確認"); 
                                // 在這裡處理確認後的操作
                            }
                        }
                    });
                }
            }
        });
        
    }
}
