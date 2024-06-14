import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Main {
    public static void main(String[] args) {
        ChoosePokemon choosePokemon = new ChoosePokemon();
        
        choosePokemon.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("summit".equals(evt.getPropertyName()) && (int) evt.getNewValue() == 1) {
                    FightPokemon fightPokemon = new FightPokemon();
                    System.out.println("寶可夢選擇已確認");
                    // 在這裡處理確認後的操作
                }
            }
        });
    }
}
