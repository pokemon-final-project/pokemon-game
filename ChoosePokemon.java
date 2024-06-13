import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ChoosePokemon extends JFrame {
    private List<BufferedImage> imageList;
    private List<JLabel> pokemonLabels;
    private JPanel chosenPanel;

    public ChoosePokemon() {
        // 設置框架標題
        setTitle("Pokemon Fight");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 設置框架的大小
        setSize(1200, 800 );

        // 初始化主面板並設置為 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel northBox = new JLabel("選擇你的Pokemon", SwingConstants.CENTER);
        northBox.setPreferredSize(new Dimension(100, 100));
        northBox.setFont(new Font("Serif", Font.PLAIN, 20)); 
        
        // pokemon 列表
        JPanel westBox = new JPanel(new GridLayout(6, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);    

        for(int i=0; i<30;i++){
            ImageIcon icon = new ImageIcon("pokemon-data/image/"+i+".png");
            Image image = icon.getImage(); // Transform it
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way
            icon = new ImageIcon(newimg);  // Transform it back
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
                    Timer timer = new Timer(1000, evt -> selectedLabel.setBorder(null));
                    timer.setRepeats(false);
                    timer.start();
                    repaint(); //刷新頁面
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel enteredLabel = (JLabel) e.getSource();
                    enteredLabel.setBorder(new LineBorder(Color.RED));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel exitedLabel = (JLabel) e.getSource();
                    exitedLabel.setBorder(null);
                }
            });
        }
        
        westBox.setBorder(BorderFactory.createTitledBorder("ALL Pokemon"));
        westBox.setPreferredSize(new Dimension(600,  1000));

        // 用戶所選的寶可夢
        Box eastBox = Box.createVerticalBox();
        JLabel box1 = new JLabel("選擇你的Pokemon", SwingConstants.CENTER);
        box1.setPreferredSize(new Dimension(400, 400));
        
        Box chosen = Box.createVerticalBox();
        JLabel chosenBox1 = new JLabel("已選擇的Pokemon", SwingConstants.CENTER);
        JLabel chosenBox2 = new JLabel("選擇你的Pokemon", SwingConstants.CENTER);
        chosen.add(chosenBox1);
        chosen.add(chosenBox2);
        chosen.setPreferredSize(new Dimension(400, 400));
        // eastBox.setBorder(BorderFactory.createTitledBorder("BoxLayout"));
        eastBox.add(box1);
        eastBox.add(chosen);
        eastBox.setPreferredSize(new Dimension(600,  1000));
        // JPanel westBox = new JPanel(new GridLayout(6, 5));

        // 添加一個包含 FlowLayout 的面板

        JButton summiButton = new JButton("準備確定");
        summiButton.setPreferredSize(new Dimension(1200, 100));
        summiButton.setFont(new Font("Serif", Font.PLAIN, 20)); 
        // JPanel flowPanel = new JPanel(new FlowLayout());    
        // flowPanel.add(new JButton("準備確定"));
        // flowPanel.setBorder(BorderFactory.createTitledBorder("FlowLayout"));
        // flowPanel.setFont(new Font("Serif", Font.PLAIN, 20)); 
        // eastBox.setPreferredSize(new Dimension(600,  100));



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
