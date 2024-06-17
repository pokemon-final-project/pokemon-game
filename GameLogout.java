import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogout extends JFrame {
    private Image backgroundImage;
    private ImageIcon gameOverImageIcon;

    public GameLogout() {
        // 背景圖片
        backgroundImage = new ImageIcon("pokemon-data/image/" + "pokemon-background" + ".jpeg").getImage();
        // 按鈕圖片 縮小尺寸
        ImageIcon originalIcon = new ImageIcon("pokemon-data/image/" + "game-ending" + ".png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(400, 170, Image.SCALE_SMOOTH);
        gameOverImageIcon = new ImageIcon(scaledImage);

        // 設置框架標題
        setTitle("Game Over");

        // 設置框架的大小
        setSize(1300, 1000);
        // 設置默認關閉操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 初始化主面板並設置為 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // 中央面板使用 GridBagLayout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // 設置面板背景為透明

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // 創建遊戲結束圖片標籤
        JLabel gameOverImageLabel = new JLabel(gameOverImageIcon);

        // 使用 JLayeredPane 作為按鈕和背景圖片的容器
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(gameOverImageIcon.getIconWidth(), gameOverImageIcon.getIconHeight()));

        // 設置遊戲結束圖片標籤的位置和大小
        gameOverImageLabel.setBounds(0, 0, gameOverImageIcon.getIconWidth(), gameOverImageIcon.getIconHeight());
        layeredPane.add(gameOverImageLabel, JLayeredPane.DEFAULT_LAYER);

        // 重新開始按鈕
        JButton restartButton = new JButton("重新開始");
        restartButton.setBounds(150, 105, 100, 30);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        layeredPane.add(restartButton, JLayeredPane.MODAL_LAYER);

        centerPanel.add(layeredPane, gbc);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 添加主面板到框架
        add(mainPanel);

        // 設置框架可見
        setVisible(true);
    }

    private void restartGame() {
        dispose(); // 關閉當前窗口
        new GameLogin(); // 假設 GameLogin 是遊戲的啟動類
    }

    public static void main(String[] args) {
        // 創建並顯示遊戲結束框架
        SwingUtilities.invokeLater(() -> new GameLogout());
    }
}
