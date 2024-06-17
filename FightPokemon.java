import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class FightPokemon extends JFrame {
    private List<Pokemon> pokemonList;
    private PropertyChangeSupport support;
    private int round = 1;
    public int operationMode = 0;

    private List<Pokemon> player1List;
    private Pokemon player1Current;
    private int player1DeadAmount;

    private List<Pokemon> player2List;
    private Pokemon player2Current;
    private int player2DeadAmount;
    

    private JLabel box1; 
    public FightPokemon(List<Pokemon> player1List,List<Pokemon> player2List) {
        this.player1List = player1List;
        this.player2List = player2List;
        player1Current = player1List.get(0);
        player2Current = player2List.get(0);
        player1DeadAmount = 0;
        player2DeadAmount = 0;
    

        support = new PropertyChangeSupport(this);
        // System.out.println(player1List);
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
        setSize(1200, 1000);

        // 初始化主面板並設置為 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 用於容納所有子面板的容器
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new LineBorder(Color.cyan, 2));

        // 回合顯示
        box1 = new JLabel("player1回合", SwingConstants.CENTER);
        updateRoundLabel(box1);
        box1.setPreferredSize(new Dimension(1200, 90));
        // box1.setBorder(new LineBorder(Color.BLACK, 2));



        // 對戰界面
        JPanel box2 = new JPanel(new GridLayout(1, 2));
        box2.setPreferredSize(new Dimension(1200, 500));
        

        //玩家1
        JPanel subbox1 = new JPanel(new GridLayout(2, 1));
        JLabel HP = new JLabel("HP:"+player1Current.HP, SwingConstants.CENTER);
        HP.setPreferredSize(new Dimension(200,100));
        subbox1.add(HP);
        ImageIcon icon = new ImageIcon("pokemon-data/image/" + player1Current.id + ".png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JLabel label = new JLabel(icon);
        subbox1.add(label);
        subbox1.setBorder(new LineBorder(Color.BLACK, 2));

        //玩家2
        JPanel subbox2 = new JPanel(new GridLayout(2, 1));
        HP = new JLabel("HP:"+player2Current.HP, SwingConstants.CENTER);
        HP.setPreferredSize(new Dimension(200,100));
        subbox2.add(HP);
        icon = new ImageIcon("pokemon-data/image/" + player2Current.id + ".png");
        image = icon.getImage();
        newimg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        label = new JLabel(icon);
        subbox2.add(label);
        subbox2.setBorder(new LineBorder(Color.BLACK, 2));



        box2.add(subbox1);
        box2.add(subbox2);

        // 操作界面
        JPanel box3 = new JPanel(new GridLayout(1, 2));
        box3.setPreferredSize(new Dimension(1200, 300));

        JPanel operationBox1Border = new JPanel();
        operationBox1Border.setLayout(new BorderLayout());
        operationBox1Border.setBorder(new LineBorder(Color.BLACK, 2));
        JPanel operationBox1 = new JPanel(new GridLayout(2, 2));
        operationBox1.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        operationBox1Border.add(operationBox1);
        for(int i=0;i<4;i++){
            JLabel ability = new JLabel(player1Current.abilities.get(i).name, SwingConstants.CENTER);
            operationBox1.add(ability);
        }
        

        // JLabel label1 = new JLabel("技能1", SwingConstants.CENTER);
        // operationBox1.add(label1);
        // JLabel label2 = new JLabel("技能2", SwingConstants.CENTER);
        // operationBox1.add(label2);
        // JLabel label3 = new JLabel("技能3", SwingConstants.CENTER);
        // operationBox1.add(label3);
        // JLabel label4 = new JLabel("技能4", SwingConstants.CENTER);
        // operationBox1.add(label4);
        


        JPanel operationBox2Border = new JPanel();
        operationBox2Border.setLayout(new BorderLayout());
        operationBox2Border.setBorder(new LineBorder(Color.BLACK, 2));
        
        JPanel operationBox2 = new JPanel(new GridLayout(2, 2));
        operationBox2.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel actionLabel1 = new JLabel("攻擊", SwingConstants.CENTER);
        actionLabel1.setFont(new Font("Serif", Font.PLAIN, 20));
        actionLabel1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel selectedLabel = (JLabel) e.getSource();
                    selectedLabel.setBorder(new LineBorder(Color.BLACK, 6));
                    operationMode = 1;
                    operationBox1.removeAll();
                    if(round == 1){
                        updateOperationMode(operationMode,operationBox1,player1List, subbox1, subbox2);
                    }else{
                        updateOperationMode(operationMode,operationBox1,player2List, subbox1, subbox2);                        
                    }
                    repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel enteredLabel = (JLabel) e.getSource();
                    enteredLabel.setBorder(new LineBorder(Color.RED));
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel exitedLabel = (JLabel) e.getSource();
                    exitedLabel.setBorder(null);
                    repaint();
                }
        });
        updateOperationMode(1, operationBox1, player2List, subbox1, subbox2);
        // actionLabel1.setBorder(new LineBorder(Color.BLACK, 2));
        
        operationBox2.add(actionLabel1);

        JLabel actionLabel2 = new JLabel("背包", SwingConstants.CENTER);
        actionLabel2.setFont(new Font("Serif", Font.PLAIN, 20));
        
        // actionLabel2.setBorder(new LineBorder(Color.BLACK, 2));
        operationBox2.add(actionLabel2);

        JLabel actionLabel3 = new JLabel("換寶可夢", SwingConstants.CENTER);
        actionLabel3.setFont(new Font("Serif", Font.PLAIN, 20));
        actionLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JLabel selectedLabel = (JLabel) e.getSource();
                selectedLabel.setBorder(new LineBorder(Color.BLACK, 6));
                operationMode = 3;
                operationBox1.removeAll();
                if(round == 1 ){
                    updateOperationMode(operationMode,operationBox1,player1List, subbox1, subbox2);
                    // updatePlayer1Current(subbox1);
                    // repaint();
                }else{
                    updateOperationMode(operationMode,operationBox1,player2List, subbox1, subbox2);
                    // repaint();
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel enteredLabel = (JLabel) e.getSource();
                enteredLabel.setBorder(new LineBorder(Color.RED));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel exitedLabel = (JLabel) e.getSource();
                exitedLabel.setBorder(null);
                repaint();
            }
        });
        operationBox2.add(actionLabel3);

        JLabel actionLabel4 = new JLabel("逃跑", SwingConstants.CENTER);
        actionLabel4.setFont(new Font("Serif", Font.PLAIN, 20));
        // actionLabel4.setBorder(new LineBorder(Color.BLACK, 2));
        operationBox2.add(actionLabel4);

        operationBox2Border.add(operationBox2, BorderLayout.CENTER);
        box3.add(operationBox1Border);
        box3.add(operationBox2Border);

        container.add(box1);
        container.add(box2);
        container.add(box3);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // 設置外邊距
        mainPanel.add(container, BorderLayout.CENTER);

        // 添加主面板到框架
        add(mainPanel);

        

        // 設置框架可見
        setVisible(true);
    }

    private void updateRoundLabel(JLabel box1) {
        if (round == 1) {
            box1.setText("player1回合");
        } else {
            box1.setText("player2回合");
        }
    }

    private void updateOperationMode(int operationMode, JPanel operationBox1, List<Pokemon> playerList, JPanel subbox1, JPanel subbox2){
        switch (operationMode) {
            case 0:
                checkCurrentPokemonDie(player1Current, subbox1, subbox2, operationBox1);
                operationBox1.removeAll();
            case 1:
                // JPanel operationBox1 = new JPanel(new GridLayout(2, 2));
                operationBox1.removeAll();
                for(int i=0;i<4;i++){
                    int index = 1;
                    if(round == 1){
                        JLabel ability = new JLabel(player1Current.abilities.get(i).name, SwingConstants.CENTER);
                        ability.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                player2Current.HP = player2Current.HP - player1Current.abilities.get(index).damage;
                                checkCurrentPokemonDie(player2Current, subbox1, subbox2, operationBox1);
                                round = 2;
                                // checkCurrentPokemonDie(player1Current, subbox1, subbox2, operationBox1);
                                updatePlayer2Current(subbox1, subbox2, operationBox1);
                                updateRoundLabel(box1);
                                repaint();
                            }
                            
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                JLabel enteredLabel = (JLabel) e.getSource();
                                enteredLabel.setBorder(new LineBorder(Color.RED));
                                repaint();
                            }
            
                            @Override
                            public void mouseExited(MouseEvent e) {
                                JLabel exitedLabel = (JLabel) e.getSource();
                                exitedLabel.setBorder(null);
                                repaint();
                            }
                        });
                        operationBox1.add(ability);
                        
                    }else{
                        JLabel ability = new JLabel(player2Current.abilities.get(i).name, SwingConstants.CENTER);
                        ability.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                player1Current.HP = player1Current.HP - player2Current.abilities.get(index).damage;
                                checkCurrentPokemonDie(player1Current, subbox1, subbox2, operationBox1);
                                round = 1;
                                // checkCurrentPokemonDie(player2Current, subbox1, subbox2, operationBox1);
                                updatePlayer1Current(subbox1, subbox2, operationBox1);
                                updateRoundLabel(box1);
                                repaint();
                            }
                            
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                JLabel enteredLabel = (JLabel) e.getSource();
                                enteredLabel.setBorder(new LineBorder(Color.RED));
                                repaint();
                            }
            
                            @Override
                            public void mouseExited(MouseEvent e) {
                                JLabel exitedLabel = (JLabel) e.getSource();
                                exitedLabel.setBorder(null);
                                repaint();
                            }
                        });
                        operationBox1.add(ability);
                    }
                }
                break;
            case 2:
                // JPanel operationBox1 = new JPanel(new GridLayout(2, 2));
                operationBox1.removeAll();
                break;
            case 3:
                // System.out.println(player1List);
                for(int i=0;i<playerList.size();i++){
                    int index = i;
                    ImageIcon icon = new ImageIcon("pokemon-data/image/" + playerList.get(i).id + ".png");
                    Image image = icon.getImage();
                    Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    JLabel label = new JLabel(icon);
                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JLabel selectedLabel = (JLabel) e.getSource();
                            selectedLabel.setBorder(new LineBorder(Color.BLACK, 6));
                            if(round == 1){
                                // System.out.println(playerList.get(index));
                                if(playerList.get(index).HP > 0){
                                    player1Current = playerList.get(index);
                                    updatePlayer1Current(subbox1,subbox2,operationBox1);
                                    round = 2;
                                    updateRoundLabel(box1);
                                }else{
                                    JOptionPane.showMessageDialog(null, "你的寶死了，選另外一隻");
                                }
                            }else{
                                //判量歸0的寶可夢
                                // System.out.println(player2Current.HP);
                                if(playerList.get(index).HP > 0){
                                    player2Current = playerList.get(index);
                                    updatePlayer2Current(subbox1, subbox2, operationBox1);
                                    round = 1;
                                    updateRoundLabel(box1);
                                }else{
                                    JOptionPane.showMessageDialog(null, "你的寶死了，選另外一隻");
                                }
                            }
                            // System.out.println(player1Current);
                            // System.out.println(player2Current);
                            repaint();
                        }
                        
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            JLabel enteredLabel = (JLabel) e.getSource();
                            enteredLabel.setBorder(new LineBorder(Color.RED));
                            repaint();
                        }
        
                        @Override
                        public void mouseExited(MouseEvent e) {
                            JLabel exitedLabel = (JLabel) e.getSource();
                            exitedLabel.setBorder(null);
                            repaint();
                        }
                    });
                operationBox1.add(label);
                }
                break;
            default:
                repaint();
                break;
        }
    }
    
    public void updatePlayer1Current(JPanel subbox1,JPanel subbox2,JPanel operationBox1){
        subbox1.removeAll();
        //扣血時血量會變紅一下
        JLabel HP = new JLabel("HP:"+player1Current.HP, SwingConstants.CENTER);
        HP.setForeground(Color.RED);
        HP.setFont(new Font("Serif", Font.PLAIN, 20));
        Timer timer = new Timer(1000, evt -> HP.setForeground(Color.BLACK));
        timer.setRepeats(false);
        timer.start();

        subbox1.add(HP);
        ImageIcon icon = new ImageIcon("pokemon-data/image/" + player1Current.id + ".png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JLabel label = new JLabel(icon);
        subbox1.add(label);
        operationMode = 1;
        updateOperationMode(operationMode,operationBox1,player1List, subbox1, subbox2);
        repaint();
    }

    public void updatePlayer2Current(JPanel subbox1,JPanel subbox2,JPanel operationBox1){
        subbox2.removeAll();
        //扣血時血量會變紅一下
        JLabel HP = new JLabel("HP:"+player2Current.HP, SwingConstants.CENTER);
        HP.setForeground(Color.RED);
        HP.setFont(new Font("Serif", Font.PLAIN, 20));
        Timer timer = new Timer(1000, evt -> HP.setForeground(Color.BLACK));
        timer.setRepeats(false);
        timer.start();

        subbox2.add(HP);
        ImageIcon icon = new ImageIcon("pokemon-data/image/" + player2Current.id + ".png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JLabel label = new JLabel(icon);
        subbox2.add(label);
        operationMode = 1; 
        updateOperationMode(operationMode,operationBox1,player2List, subbox1, subbox2);
        repaint();
    }

    public void checkCurrentPokemonDie(Pokemon CurrentPokemon,JPanel subbox1,JPanel subbox2,JPanel operationBox1){
        //當前pokemon陣亡後自動更換List中第二隻Pokemon
        if(CurrentPokemon.HP <= 0){
            operationMode = 3;
            // System.out.println(player1List);
            operationBox1.removeAll();
            player1DeadAmount += 1;
            if(round == 2){
                for(int i=0;i<player1List.size();i++){
                    if(player1List.get(i).HP > 0){
                        player1Current = player1List.get(i);                
                        break;
                    }
                }
                if(player1DeadAmount >=player1List.size()){
                    JOptionPane.showMessageDialog(null, "Player1死光了，Player2獲勝負");
                }
                updatePlayer1Current(subbox1, subbox2, operationBox1);
            }else{
                player2DeadAmount += 1;
                for(int i=0;i<player2List.size();i++){
                    if(player2List.get(i).HP > 0){
                        player2Current = player2List.get(i);
                        break;
                    }
                }
                if(player2DeadAmount >= player2List.size()){
                    JOptionPane.showMessageDialog(null, "Player1死光了，Player2獲勝");
                }
                updatePlayer2Current(subbox1, subbox2, operationBox1);
            }
            repaint();
        }
    }

    // public void addPropertyChangeListener(PropertyChangeListener pcl) {
    //     support.addPropertyChangeListener(pcl);
    // }


    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new FightPokemon(List.of(1, 2, 3, 4, 5, 6)));
    // }
}

