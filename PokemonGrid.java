import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PokemonGrid extends JPanel {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;
    private static final int SELECTED_GRID_SIZE = 6;
    private int selectedX = 0;
    private int selectedY = 0;
    private ImageIcon[] pokemonIcons;
    private ArrayList<ImageIcon> selectedPokemons = new ArrayList<>();
    private JPanel selectedPanel;

    public PokemonGrid() {
        setLayout(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        gridPanel.setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        selectedPanel = new JPanel(new GridLayout(1, SELECTED_GRID_SIZE));
        selectedPanel.setPreferredSize(new Dimension(SELECTED_GRID_SIZE * CELL_SIZE, CELL_SIZE));

        loadPokemonImages();
        
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            JLabel label = new JLabel(pokemonIcons[i % pokemonIcons.length]);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(label);
        }

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(selectedPanel, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!selectedPokemons.isEmpty()) {
                    selectedPokemons.remove(selectedPokemons.size() - 1);
                    refreshSelectedPanel();
                }
            }
        });
        rightPanel.add(cancelButton, BorderLayout.SOUTH);

        add(gridPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (selectedY > 0) selectedY--;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (selectedY < GRID_SIZE - 1) selectedY++;
                        break;
                    case KeyEvent.VK_LEFT:
                        if (selectedX > 0) selectedX--;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (selectedX < GRID_SIZE - 1) selectedX++;
                        break;
                    case KeyEvent.VK_ENTER:
                        int index = selectedY * GRID_SIZE + selectedX;
                        if (selectedPokemons.size() < SELECTED_GRID_SIZE) {
                            selectedPokemons.add(pokemonIcons[index % pokemonIcons.length]);
                            refreshSelectedPanel();
                        }
                        break;
                }
                repaint();
            }
        });
    }

    private void loadPokemonImages() {
        // Load 10 Pokemon images (you can change this to actual image paths)
        pokemonIcons = new ImageIcon[10];
        for (int i = 0; i < 10; i++) {
            pokemonIcons[i] = new ImageIcon("pokemon-data/image" + (i + 1) + ".png");
        }
    }

    private void refreshSelectedPanel() {
        selectedPanel.removeAll();
        for (ImageIcon icon : selectedPokemons) {
            selectedPanel.add(new JLabel(icon));
        }
        selectedPanel.revalidate();
        selectedPanel.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pokemon Grid");
        PokemonGrid pokemonGrid = new PokemonGrid();
        frame.add(pokemonGrid);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
