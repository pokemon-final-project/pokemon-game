import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class FightPokemon extends JFrame {
    List<Pokemon> pokemonList;
    private PropertyChangeSupport support;
    private int round = 1 ;

    private List<Pokemon> player1List;
    private Pokemon player1Current;
    
    private List<Pokemon> player2List;
    private Pokemon player2Current;
    
    
    public FightPokemon(List<Integer> chosenPokemonIds) {
        support = new PropertyChangeSupport(this);
        // 載入Pokemon Data(反序列化)
        try (FileInputStream fis = new FileInputStream("pokemon_data.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            pokemonList = (List<Pokemon>) ois.readObject();
            System.out.println("Successfully read Pokemon data from file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 設置框架標題
        setTitle("Pokemon Fight");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 設置框架的大小
        setSize(1300, 1000);

        // 初始化主面板並設置為 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel container = new JPanel(new GridLayout(3,1));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new LineBorder(Color.cyan, 2));
        container.setPreferredSize(new Dimension(1200, 1000));

        //回合顯示
        JLabel box1 = new JLabel("player1回合", SwingConstants.CENTER);
        if(round == 1){
            box1 = new JLabel("player1回合", SwingConstants.CENTER);
        }else{
            box1 = new JLabel("player2回合", SwingConstants.CENTER);
        }
        box1.setPreferredSize(new Dimension(1200, 100));
        box1.setMinimumSize(new Dimension(1200, 100));
        box1.setMaximumSize(new Dimension(1200, 100));
        box1.setBorder(new LineBorder(Color.BLACK, 2));


        //對戰界面
        JPanel box2 = new JPanel(new GridLayout(1,2));
        JPanel subbox1 = new JPanel(new GridLayout(2,2));
        subbox1.setPreferredSize(new Dimension(1200, 400));
        subbox1.setBorder(new LineBorder(Color.cyan, 2));
        // for(int i=0;i<chosenPokemonIds)


        JPanel subbox2 = new JPanel(new GridLayout(2,2));
        subbox2.setPreferredSize(new Dimension(1200, 400));
        subbox2.setBorder(new LineBorder(Color.cyan, 2));
        
        
        box2.add(subbox1);
        box2.add(subbox2);



        //操作界面
        JLabel box3 = new JLabel("已選擇的Pokemon", SwingConstants.CENTER);
        box3.setPreferredSize(new Dimension(1200, 400));
        box3.setMaximumSize(new Dimension(1200, 400));



        container.add(box1);
        container.add(box2);
        container.add(box3);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // 設置外邊距

        mainPanel.add(container, BorderLayout.CENTER);

        // 添加主面板到框架
        add(mainPanel);
        System.out.println(chosenPokemonIds);
        // 設置框架可見
        setVisible(true);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
}
