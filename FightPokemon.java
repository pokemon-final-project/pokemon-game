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
    private int operationMode = 1;

    public List<Pokemon> player1List;
    public Pokemon player1Current;

    public List<Pokemon> player2List;
    public Pokemon player2Current;

    public FightPokemon(List<Pokemon> player1List,List<Pokemon> player2List) {
        player1Current = player1List.get(0);
        player2Current = player2List.get(0);
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
        JLabel box1 = new JLabel("player1回合", SwingConstants.CENTER);
        updateRoundLabel(box1);
        box1.setPreferredSize(new Dimension(1200, 90));
        // box1.setBorder(new LineBorder(Color.BLACK, 2));

        // 對戰界面
        JPanel box2 = new JPanel(new GridLayout(1, 2));
        box2.setPreferredSize(new Dimension(1200, 500));
        JPanel subbox1 = new JPanel(new GridLayout(2, 1));
        JLabel HP = new JLabel("HP:"+player1Current.HP, SwingConstants.CENTER);
        subbox1.add(HP);

        ImageIcon icon = new ImageIcon("pokemon-data/image/" + player1Current.id + ".png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JLabel label = new JLabel(icon);
        subbox1.add(label);

        
        subbox1.setBorder(new LineBorder(Color.BLACK, 2));
        JPanel subbox2 = new JPanel(new GridLayout(2, 1));
        subbox2.setBorder(new LineBorder(Color.BLACK, 2));
        box2.add(subbox1);
        box2.add(subbox2);

        // 操作界面
        JPanel box3 = new JPanel(new GridLayout(1, 2));
        box3.setPreferredSize(new Dimension(1200, 300));

        JPanel operationBox1 = new JPanel(new GridLayout(2, 2));
        operationBox1.setBorder(new LineBorder(Color.BLACK, 2));
        JLabel label1 = new JLabel("技能1", SwingConstants.CENTER);
        operationBox1.add(label1);
        JLabel label2 = new JLabel("技能2", SwingConstants.CENTER);
        operationBox1.add(label2);
        JLabel label3 = new JLabel("技能3", SwingConstants.CENTER);
        operationBox1.add(label3);
        JLabel label4 = new JLabel("技能4", SwingConstants.CENTER);
        operationBox1.add(label4);
        
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
                    updateOperationMode(operationMode,operationBox1,player1List, subbox1, subbox2);
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
        box3.add(operationBox1);
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
            case 1:
                // JPanel operationBox1 = new JPanel(new GridLayout(2, 2));
                operationBox1.setBorder(new LineBorder(Color.BLACK, 2));
                JLabel label1 = new JLabel("技能1", SwingConstants.CENTER);
                operationBox1.add(label1);
                JLabel label2 = new JLabel("技能2", SwingConstants.CENTER);
                operationBox1.add(label2);
                JLabel label3 = new JLabel("技能3", SwingConstants.CENTER);
                operationBox1.add(label3);
                JLabel label4 = new JLabel("技能4", SwingConstants.CENTER);
                operationBox1.add(label4);
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
                                player1Current = playerList.get(index);
                            }else{
                                player2Current = playerList.get(index);
                            }
                            // System.out.println(player1Current);
                            // System.out.println(player2Current);
                            updatePlayer1Current(subbox1);
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
                break;
        }
    }
    
    public void updatePlayer1Current(JPanel subbox1){
        subbox1.removeAll();
        JLabel HP = new JLabel("HP:"+player1Current.HP, SwingConstants.CENTER);
        subbox1.add(HP);
        ImageIcon icon = new ImageIcon("pokemon-data/image/" + player1Current.id + ".png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JLabel label = new JLabel(icon);
        subbox1.add(label);
        repaint();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }



    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new FightPokemon(List.of(1, 2, 3, 4, 5, 6)));
    // }
}

// actionLabel1.addMouseListener(new MouseAdapter() {
//     @Override
//     public void mouseClicked(MouseEvent e) {
//         repaint();
//     }

//     @Override
//     public void mouseEntered(MouseEvent e) {
//         repaint();
//     }

//     @Override
//     public void mouseExited(MouseEvent e) {
//         repaint();
//     }
// });
