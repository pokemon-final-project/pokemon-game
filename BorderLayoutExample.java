import javax.swing.*;
import java.awt.*;

public class BorderLayoutExample extends JFrame {
    public BorderLayoutExample() {
        // 設置框架標題
        setTitle("BorderLayout Example");

        // 設置默認關閉操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 設置框架的大小
        setSize(400, 300);

        // 設置框架的佈局管理器為 BorderLayout
        setLayout(new BorderLayout());

        // 創建並添加按鈕到不同的區域
        JButton northButton = new JButton("NORTH");
        JButton southButton = new JButton("SOUTH");
        JButton eastButton = new JButton("EAST");
        JButton westButton = new JButton("WEST");
        JButton centerButton = new JButton("CENTER");

        add(northButton, BorderLayout.NORTH);
        add(southButton, BorderLayout.SOUTH);
        add(eastButton, BorderLayout.EAST);
        add(westButton, BorderLayout.WEST);
        add(centerButton, BorderLayout.CENTER);

        // 設置框架可見
        setVisible(true);
    }

    public static void main(String[] args) {
        // 創建並顯示應用程序框架
        SwingUtilities.invokeLater(() -> new BorderLayoutExample());
    }
}
