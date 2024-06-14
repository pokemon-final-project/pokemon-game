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
    private List<JLabel> pokemonLabels;
    private JPanel chosenPanel;
    private int currentPokemonId;
    private List<Integer> chosenPokemonIds;
    List<Pokemon> pokemonList;
    private int summit = 0; // 修改為實例變量
    private PropertyChangeSupport support;

    public FightPokemon() {
        support = new PropertyChangeSupport(this);

        //載入Pokemon Data(反序列化)
        try (FileInputStream fis = new FileInputStream("pokemon_data.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            pokemonList = (List<Pokemon>) ois.readObject();
            System.out.println("Successfully read Pokemon data from file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 設置框架標題
        chosenPokemonIds = new ArrayList<>();
        setTitle("Pokemon Fight");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 設置框架的大小
        setSize(1300, 1000);

        // 初始化主面板並設置為 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        //EAST
        JPanel eastBox = new JPanel(new GridLayout(2, 1));
        
        //box1 是當前Pokemon的技能
        JPanel box1 = new JPanel(new GridLayout(1, 2));
        box1.setPreferredSize(new Dimension(650, 400));
        box1.setBorder(new LineBorder(Color.cyan,2));

        // 用戶所選的寶可夢
        Box chosen = Box.createVerticalBox();
        chosen.setBorder(new LineBorder(Color.cyan,2));

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

        //NORTH
        JLabel northBox = new JLabel("選擇你的隊伍", SwingConstants.CENTER);
        northBox.setPreferredSize(new Dimension(100, 100));
        northBox.setFont(new Font("Serif", Font.PLAIN, 20)); 

        //WEST
        JPanel westBox = new JPanel(new GridLayout(6, 5));
        gbc.insets = new Insets(10, 10, 10, 10);    
        
        for (int i = 1; i <= 30; i++) {
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
                    
                    if (chosenPokemonIds.size() >= 0 && chosenPokemonIds.size() < 6) {
                        chosenPokemonIds.add(index);
                        chosenBox2.removeAll();
                        for (int i = 0; i < chosenPokemonIds.size(); i++) {
                            ImageIcon icon = new ImageIcon("pokemon-data/image/" + chosenPokemonIds.get(i) + ".png");
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

                @Override
                public void mouseEntered(MouseEvent e) {
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

        //準備確定
        // JButton submitButton = new JButton("準備確定");
        // submitButton.setPreferredSize(new Dimension(1200, 100));
        // submitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        // submitButton.addMouseListener(new MouseAdapter() {
        //     @Override
        //     public void mouseClicked(MouseEvent e) {
        //         setSummit(1); // 通過方法設置 summit 狀態
        //         mainPanel.removeAll();
        //         repaint();
        //     }
        // });

        // 加到面板到主面板
        mainPanel.add(northBox, BorderLayout.NORTH);
        // mainPanel.add(submitButton, BorderLayout.SOUTH);
        mainPanel.add(westBox, BorderLayout.WEST);
        mainPanel.add(eastBox, BorderLayout.EAST);

        // 添加主面板到框架
        add(mainPanel);

        // 設置框架可見
        setVisible(true);
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
