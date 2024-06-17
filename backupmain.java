import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class backupmain {
    public static void main(String[] args) {
        GameLogin login = new GameLogin();
        login.addPropertyChangeListener(new PropertyChangeListener() {
            ChoosePokemon choosePokemon;
            public void propertyChange(PropertyChangeEvent evt){
                if ("summit".equals(evt.getPropertyName()) && (int) evt.getNewValue() == 1) {
                    choosePokemon = new ChoosePokemon();
                    choosePokemon.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("summit".equals(evt.getPropertyName()) && (int) evt.getNewValue() == 1) {
                                InitialPlayerPokemon initialPlayerPokemon = new InitialPlayerPokemon(login.choosePokemon.player1PokemonId,login.choosePokemon.player2PokemonId);
                                System.out.println("testing");
                                // System.out.println(initialPlayerPokemon.player1List);
                                // System.out.println("player2");
                                // System.out.println(initialPlayerPokemon.player2List);
                                FightPokemon fightPokemon = new FightPokemon(initialPlayerPokemon.player1List,initialPlayerPokemon.player2List);
                                login.dispose();
                            }
                        }
                    });
                }
            }
        });
        
    }
}
