import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogin extends JFrame {
    private Image backgroundImage;
    private ImageIcon additionalImageIcon;

    public GameLogin() {
        // 加載背景圖片
        backgroundImage = new ImageIcon("pokemon-data/image/" + "pokemon-background" + ".jpeg").getImage();
        // 加載額外圖片，並縮小尺寸
        ImageIcon originalIcon = new ImageIcon("pokemon-data/image/" + "titeled-button" + ".png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(400, 150, Image.SCALE_SMOOTH);
        additionalImageIcon = new ImageIcon(scaledImage);

        // 設定框架標題
        setTitle("Pokemon Login");

        // 設定框架的大小
        setSize(1250, 1000 );

        // 設定預設關閉操作
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
        centerPanel.setOpaque(false); // 設定面板背景為透明

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // 創建額外圖片標籤
        JLabel additionalImageLabel = new JLabel(additionalImageIcon);

        // 使用 JLayeredPane 作為按鈕和背景圖片的容器
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(additionalImageIcon.getIconWidth(), additionalImageIcon.getIconHeight()));

        // 設定額外圖片標籤的位置和大小
        additionalImageLabel.setBounds(0, 0, additionalImageIcon.getIconWidth(), additionalImageIcon.getIconHeight());
        layeredPane.add(additionalImageLabel, JLayeredPane.DEFAULT_LAYER);

        // 是按鈕
        JButton yesButton = new JButton("是");
        yesButton.setBounds(100, 80, 80, 30);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCharacterSelection();
            }
        });
        layeredPane.add(yesButton, JLayeredPane.MODAL_LAYER);

        // 否按鈕
        JButton noButton = new JButton("否");
        noButton.setBounds(220, 80, 80, 30);
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "還是可以玩");
                startCharacterSelection();
            }
        });
        layeredPane.add(noButton, JLayeredPane.MODAL_LAYER);

        centerPanel.add(layeredPane, gbc);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 添加主面板到框架
        add(mainPanel);

        // 設定框架可見
        setVisible(true);
    }

    private void startCharacterSelection() {
        // 這裡可以添加選擇角色的代碼或打開選擇角色的界面
        System.out.println("開始選角色");
    }

    public static void main(String[] args) {
        // 創建並顯示登錄框架
        SwingUtilities.invokeLater(() -> new GameLogin());
    }
}
