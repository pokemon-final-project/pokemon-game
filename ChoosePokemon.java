import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ChoosePokemon extends JFrame {
    private List<BufferedImage> imageList;
    private List<JLabel> pokemonLabels;
    private JPanel chosenPanel;
    private int currentPokemonId;
    private List<Integer> chosenPokemonIds;
    List<Pokemon> pokemonList;

    // private 

    public ChoosePokemon() {
        //載入Pokemon Data(反序列化)
        try (FileInputStream fis = new FileInputStream("pokemon_data.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            pokemonList = (List<Pokemon>) ois.readObject();
            System.out.println("Successfully read Pokemon data from file.");
            
            // for (Pokemon pokemon : pokemonList) {
            //     System.out.println(pokemon.name);
            // }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        // 設置框架標題
        List<Integer> chosenPokemonIds = new ArrayList<>();
        setTitle("Pokemon Fight");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 設置框架的大小
        setSize(1300, 1000 );

        // 初始化主面板並設置為 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        //EAST
        //右邊的格子
        // Box eastBox = Box.createVerticalBox();
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
        //後面把for loop 改掉
        for(int i = 0; i<chosenPokemonIds.size();i++){
            if(chosenPokemonIds.size() <=0){
                break;
            }
            ImageIcon icon = new ImageIcon("pokemon-data/image/"+chosenPokemonIds.get(i)+".png");
            Image image = icon.getImage(); // Transform it
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way
            icon = new ImageIcon(newimg);  // Transform it back
            JLabel label = new JLabel(icon);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 0, 10, 0);    
            chosenBox2.add(label,gbc);
        }
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(100, 0, 0, 0); 
        chosen.add(chosenBox1);
        chosen.add(chosenBox2);
        chosen.setPreferredSize(new Dimension(650, 400));
        // eastBox.setBorder(BorderFactory.createTitledBorder("BoxLayout"));
        eastBox.add(box1);
        eastBox.add(chosen);
        
        eastBox.setPreferredSize(new Dimension(650,  800));
        // JPanel westBox = new JPanel(new GridLayout(6, 5));

        
        //NORTH
        JLabel northBox = new JLabel("選擇你的隊伍", SwingConstants.CENTER);
        northBox.setPreferredSize(new Dimension(100, 100));
        northBox.setFont(new Font("Serif", Font.PLAIN, 20)); 
        

        //WEST
        // pokemon 列表
        JPanel westBox = new JPanel(new GridLayout(6, 5));
        // GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);    
        
        for(int i=1; i<=30;i++){
            //載入圖片
            int index = i;
            ImageIcon icon = new ImageIcon("pokemon-data/image/"+i+".png");
            Image image = icon.getImage(); 
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way
            icon = new ImageIcon(newimg); 
            JLabel label = new JLabel(icon);
            westBox.add(label,gbc);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel selectedLabel = (JLabel) e.getSource();
                    // JOptionPane.showMessageDialog(null, "You selected " + selectedLabel.getIcon());
                    // Change border thickness
                    selectedLabel.setBorder(new LineBorder(Color.BLACK, 6));
                    // Reset border after a delay
                    // console.log(i);
                    
                    System.out.println(index);
                    System.out.println(chosenPokemonIds);
                    
                    Timer timer = new Timer(1000, evt -> selectedLabel.setBorder(null));
                    timer.setRepeats(false);
                    timer.start();
                    //加入Pokemon
                    if(chosenPokemonIds.size() >= 0 && chosenPokemonIds.size() < 6){
                        chosenPokemonIds.add(index);
                        chosenBox2.removeAll();
                        for(int i = 0; i<chosenPokemonIds.size();i++){
                            ImageIcon icon = new ImageIcon("pokemon-data/image/"+chosenPokemonIds.get(i)+".png");
                            Image image = icon.getImage(); // Transform it
                            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way
                            icon = new ImageIcon(newimg);  // Transform it back
                            JLabel label = new JLabel(icon);
                            chosenBox2.add(label);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Pokemon數量已逹上限" );
                    }
                    repaint(); //刷新頁面
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel enteredLabel = (JLabel) e.getSource();
                    enteredLabel.setBorder(new LineBorder(Color.RED));
                    
                    currentPokemonId = index;
                    if(currentPokemonId != 0){
                        // JLabel subbox1 = new JLabel("選擇你的Pokemon", SwingConstants.CENTER);
                        ImageIcon icon = new ImageIcon("pokemon-data/image/"+currentPokemonId+".png");
                        Image image = icon.getImage(); 
                        Image newimg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); 
                        icon = new ImageIcon(newimg);  
                        JLabel label = new JLabel(icon);
                        label.setPreferredSize(new Dimension(200, 400));

                        JPanel subboxes = new JPanel(new GridLayout(2, 1));
                        JLabel subbox1 = new JLabel(pokemonList.get(index).name, SwingConstants.CENTER);
                        JPanel subbox2 = new JPanel(new GridLayout(2, 2));
                        for(int i=0;i<pokemonList.get(index).abilities.size();i++){
                            String name = pokemonList.get(index).abilities.get(i).name;
                            int damage = pokemonList.get(index).abilities.get(i).damage;
                            String attribute = pokemonList.get(index).abilities.get(i).attribute;
                            JLabel ability = new JLabel(name +"   " +  "傷害" + damage + "("+ attribute +")", SwingConstants.CENTER);
                            subbox2.add(ability);
                        }
                        // JLabel subbox1 = new JLabel("a", SwingConstants.CENTER);
                        System.out.println(pokemonList.get(index));
                        subbox1.setPreferredSize(new Dimension(100, 100));
                        subbox1.setFont(new Font("Serif", Font.PLAIN, 20)); 
                        subboxes.add(subbox1);
                        subboxes.add(subbox2);
                        
                        box1.add(label);
                        box1.add(subboxes);
                    }
                    System.out.println(currentPokemonId);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel exitedLabel = (JLabel) e.getSource();
                    exitedLabel.setBorder(null);
                    currentPokemonId = 0;
                    box1.removeAll();
                    System.out.println(currentPokemonId);
                    repaint();
                }
            });
        }
        
        westBox.setBorder(BorderFactory.createTitledBorder("ALL Pokemon"));
        westBox.setPreferredSize(new Dimension(600,  1000));

        
        //準備確定
        JButton summiButton = new JButton("準備確定");
        summiButton.setPreferredSize(new Dimension(1200, 100));
        summiButton.setFont(new Font("Serif", Font.PLAIN, 20)); 
        summiButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainPanel.removeAll();
                repaint();
            }
        });


        // 加到面板到主面板
        mainPanel.add(northBox, BorderLayout.NORTH);
        mainPanel.add(summiButton, BorderLayout.SOUTH);
        mainPanel.add(westBox, BorderLayout.WEST);
        mainPanel.add(eastBox, BorderLayout.EAST);

        // 添加主面板到框架
        add(mainPanel);

        // 設置框架可見
        setVisible(true);
    }

    public static void main(String[] args) {
        // 創建並顯示應用程序框架
        SwingUtilities.invokeLater(() -> new ChoosePokemon());
    }
    
}
