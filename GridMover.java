import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GridMover extends JPanel {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;
    private int pointX = 0;
    private int pointY = 0;

    public GridMover() {
        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        setBackground(Color.WHITE);
        setFocusable(true);

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
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                g.drawRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        g.setColor(Color.RED);
        g.fillOval(pointX * CELL_SIZE, pointY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grid Mover");
        GridMover gridMover = new GridMover();
        frame.add(gridMover);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
