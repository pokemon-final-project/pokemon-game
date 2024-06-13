import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.swing.border.LineBorder;

import java.util.ArrayList;
import java.util.List;

public class GridMover extends JPanel {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;
    private int pointX = 0;
    private int pointY = 0;
    private List<BufferedImage> imageList;
        // private BufferedImage image;

    public GridMover() {
        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        setBackground(Color.WHITE);
        setFocusable(true);
        try {
            // 加載圖片
            imageList = new ArrayList<>();

            for(int i=1;i<2;i++){
                System.out.print("pokemon-data/image/"+i+".png");
                BufferedImage image = ImageIO.read(new File("pokemon-data/image/" + i + ".png"));
                imageList.add(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (pointY > 0) pointY--;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (pointY < GRID_SIZE - 1) pointY++;
                        break;
                    case KeyEvent.VK_LEFT:
                        if (pointX > 0) pointX--;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (pointX < GRID_SIZE - 1) pointX++;
                        break;
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                g.drawRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                //加載圖片
                if (imageList.get(0) != null) {
                    g2d.drawImage(imageList.get(0), pointX * CELL_SIZE, pointY * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                }else{
                    System.out.println("NO image");
                }
            }
        }
        setCellBorder(g2d, pointX * CELL_SIZE, pointY * CELL_SIZE, Color.RED);
        // g.setColor(Color.BLUE);
        // g.fillOval(pointX * CELL_SIZE, pointY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    private void setCellBorder(Graphics2D g2d, int x, int y, Color color) {
        g2d.setColor(color);
        g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grid Mover");
        GridMover gridMover = new GridMover();
        frame.add(gridMover);
        frame.pack();
        frame.setTitle("Pokemon Final");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
