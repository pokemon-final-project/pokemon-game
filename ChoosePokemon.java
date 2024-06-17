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

// _initialization
public class ChoosePokemon extends JFrame { 
    private int currentPokemonId;
    public List<Integer> chosenPokemonIds;
    public FightPokemon fightpokemon;
    List<Pokemon> pokemonList; //紀錄序列化的寶可夢資料
    private int summit = 0;
    private PropertyChangeSupport support;
    private int round = 1; //round = 1玩家一 round = 2玩家二
    public List<Integer> player1PokemonId;
    public List<Integer> player2PokemonId;
    public List<Pokemon> player1Pokemon;
    public List<Pokemon> player2Pokemon;

    public ChoosePokemon() {
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

        // 設置框架大小
        setSize(1300, 1000);

        // 初始化主面板並設置為 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // EAST
        JPanel eastBox = new JPanel(new GridLayout(2, 1));

        // box1 是當前Pokemon的技能
        JPanel box1 = new JPanel(new GridLayout(1, 2));
        box1.setPreferredSize(new Dimension(650, 400));
        box1.setBorder(new LineBorder(Color.cyan, 2));

        // 用戶所選的寶可夢
        Box chosen = Box.createVerticalBox();
        chosen.setBorder(new LineBorder(Color.cyan, 2));

        JLabel chosenBox1 = new JLabel("已選擇的Pokemon", SwingConstants.CENTER);
        JPanel chosenBox2 = new JPanel(new GridLayout(2, 3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(100, 0, 0, 0);
        chosen.add(chosenBox1);
        chosen.add(chosenBox2);
        chosen.setPreferredSize(new Dimension(650, 400));
        eastBox.add(box1);
        eastBox.add(chosen);

        eastBox.setPreferredSize(new Dimension(650, 800));

        // NORTH
        JLabel northBox = new JLabel("選擇你的隊伍", SwingConstants.CENTER);
        updateNorthBoxLabel(northBox);
        northBox.setPreferredSize(new Dimension(100, 100));
        northBox.setFont(new Font("Serif", Font.PLAIN, 20));

        player1PokemonId = new ArrayList<>();
        player2PokemonId = new ArrayList<>();
        player1Pokemon = new ArrayList<>();
        player2Pokemon = new ArrayList<>();
        chosenPokemonIds = new ArrayList<>();

        // WEST
        JPanel westBox = new JPanel(new GridLayout(6, 5));
        gbc.insets = new Insets(10, 10, 10, 10);

        for (int i = 1; i <= 30; i++) { //把所有寶可夢的圖片選取按鈕放進去
            int index = i;
            ImageIcon icon = new ImageIcon("pokemon-data/image/" + i + ".png");
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
            JLabel label = new JLabel(icon);
            westBox.add(label, gbc);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel selectedLabel = (JLabel) e.getSource();
                    selectedLabel.setBorder(new LineBorder(Color.BLACK, 6));
                    Timer timer = new Timer(1000, evt -> selectedLabel.setBorder(null));
                    timer.setRepeats(false);
                    timer.start();

                    // 將兩個player的pokemon加進去arraylist, player1
                    if (round == 1) {
                        if (player1PokemonId.size() >= 0 && player1PokemonId.size() < 6) {
                            player1PokemonId.add(index);
                            player1Pokemon.add(pokemonList.get(index - 1));
                            System.out.println(player1PokemonId);
                            chosenBox2.removeAll();
                            for (int i = 0; i < player1PokemonId.size(); i++) {
                                ImageIcon icon = new ImageIcon("pokemon-data/image/" + player1PokemonId.get(i) + ".png");
                                Image image = icon.getImage();
                                Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                                icon = new ImageIcon(newimg);
                                JLabel label = new JLabel(icon);
                                chosenBox2.add(label);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Pokemon數量已達上限");
                        }
                        repaint();
                    } else { //player 2
                        if (player2PokemonId.size() >= 0 && player2PokemonId.size() < 6) {
                            player2PokemonId.add(index);
                            player2Pokemon.add(pokemonList.get(index - 1));
                            System.out.println(player2PokemonId);
                            chosenBox2.removeAll();
                            for (int i = 0; i < player2PokemonId.size(); i++) {
                                ImageIcon icon = new ImageIcon("pokemon-data/image/" + player2PokemonId.get(i) + ".png");
                                Image image = icon.getImage();
                                Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                                icon = new ImageIcon(newimg);
                                JLabel label = new JLabel(icon);
                                chosenBox2.add(label);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Pokemon數量已達上限");
                        }
                        repaint();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) { //鼠標移動到寶可夢圖片顯示屬性
                    JLabel enteredLabel = (JLabel) e.getSource();
                    enteredLabel.setBorder(new LineBorder(Color.RED));

                    currentPokemonId = index;
                    if (currentPokemonId != 0) {
                        ImageIcon icon = new ImageIcon("pokemon-data/image/" + currentPokemonId + ".png");
                        Image image = icon.getImage();
                        Image newimg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(newimg);
                        JLabel label = new JLabel(icon);
                        label.setPreferredSize(new Dimension(200, 400));

                        JPanel subboxes = new JPanel(new GridLayout(2, 1));
                        JLabel subbox1 = new JLabel(pokemonList.get(index - 1).name, SwingConstants.CENTER);
                        JPanel subbox2 = new JPanel(new GridLayout(2, 2));
                        for (int i = 0; i < pokemonList.get(index - 1).abilities.size(); i++) {
                            String name = pokemonList.get(index - 1).abilities.get(i).name;
                            int damage = pokemonList.get(index - 1).abilities.get(i).damage;
                            String attribute = pokemonList.get(index - 1).abilities.get(i).attribute;
                            JLabel ability = new JLabel(name + "   " + "傷害" + damage + "(" + attribute + ")", SwingConstants.CENTER);
                            subbox2.add(ability);
                        }
                        subbox1.setPreferredSize(new Dimension(100, 100));
                        subbox1.setFont(new Font("Serif", Font.PLAIN, 20));
                        subboxes.add(subbox1);
                        subboxes.add(subbox2);

                        box1.add(label);
                        box1.add(subboxes);
                    }
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel exitedLabel = (JLabel) e.getSource();
                    exitedLabel.setBorder(null);
                    currentPokemonId = 0;
                    box1.removeAll();
                    repaint();
                }
            });
        }

        westBox.setBorder(BorderFactory.createTitledBorder("所有 Pokemon"));
        westBox.setPreferredSize(new Dimension(600, 1000));

        // 準備確定
        JButton submitButton = new JButton();
        updateSubmitButtonLabel(submitButton);
        submitButton.setPreferredSize(new Dimension(1200, 100));
        submitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (round == 1) {
                    round = 2;
                    updateNorthBoxLabel(northBox);
                    updateSubmitButtonLabel(submitButton);
                    chosenBox2.removeAll();
                    repaint();
                } else {
                    
                    setSummit(1); // 通過方法設置 summit 狀態
                    // mainPanel.removeAll();
                    // repaint();
                }
            }
        });

        // 加到面板到主面板
        mainPanel.add(northBox, BorderLayout.NORTH);
        mainPanel.add(submitButton, BorderLayout.SOUTH);
        mainPanel.add(westBox, BorderLayout.WEST);
        mainPanel.add(eastBox, BorderLayout.EAST);
        // mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // 設置外邊距
        // 添加主面板到框架
        add(mainPanel);

        // 設置框架可見
        setVisible(true);
    }

    
    private void updateNorthBoxLabel(JLabel label) {
        if (round == 1) {
            label.setText("Player1請選擇你的隊伍");
        } else {
            label.setText("Player2請選擇你的隊伍");
        }
    }

    private void updateSubmitButtonLabel(JButton button) {
        if (round == 1) {
            button.setText("Player1準備確定");
        } else {
            button.setText("Player2準備確定");
        }
    }

    public void setSummit(int summit) {
        int oldSummit = this.summit;
        this.summit = summit;
        support.firePropertyChange("summit", oldSummit, summit);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChoosePokemon());
    }
}
