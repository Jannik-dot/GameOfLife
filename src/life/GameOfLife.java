package life;

import javax.swing.*;
import java.awt.*;

/**
 * This is the Swing class where the GUI is created
 */

public class GameOfLife extends JFrame {

    JPanel window;
    JPanel info;
    JPanel field;
    JPanel cell;
    JPanel control;
    JLabel alive;
    JLabel generation;
    int speed;


    public GameOfLife() {
        super("Game of Life");
        // Changing the size integer will change the size of the universe!
        int size = 100;
        Simulation simulation = new Simulation(size);
        simulation.populate();
        Generator generator = new Generator(simulation.universe);
        this.speed = 1000;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setLocationRelativeTo(null);

        this.window = new JPanel();
        window.setLayout(new BorderLayout());
        add(window);

        this.control = new JPanel();
        control.setLayout(new BoxLayout(control, BoxLayout.X_AXIS));
        window.add(control, BorderLayout.NORTH);

        this.info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        control.add(info, LEFT_ALIGNMENT);

        this.field = new JPanel();
        field.setLayout(new GridLayout(size, size,1,1));
        field.setBackground(Color.BLACK);

        this.cell = new JPanel();

        /*
         * In the next step the playing field is created. Hereby it checks in the simulation.universe
         * array if a cell is alive. A beforehand created JPanel is colored white and added to the field.
         * If it is dead it is instead colored gray and also added to the field.
         */

        for (int i = 0; i < simulation.universe.length; i++) {
            for (int j = 0; j < simulation.universe.length; j++) {
                cell = new JPanel();
                if (simulation.universe[i][j]) {
                    cell.setBackground(Color.WHITE);
                } else {
                    cell.setBackground(Color.darkGray);
                }
                field.add(cell);
            }
        }
        window.add(field, BorderLayout.CENTER);

        this.alive = new JLabel();
        alive.setName("AliveLabel");
        alive.setText("Alive: " + simulation.countAlive() + "        ");
        info.add(alive, LEFT_ALIGNMENT);

        this.generation = new JLabel();
        generation.setName("GenerationLabel");
        generation.setText("Generation: " + generator.generation + "        ");
        info.add(generation, LEFT_ALIGNMENT);

        /*
         * The resetButton creates a new "Universe" upon activation and populates it following this
         * the working "Universe" is updated and the generation is set to 0
         */

        JButton resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");
        resetButton.addActionListener(e -> {
            simulation.resetUniverse();
            simulation.populate();
            generator.currUniverse = simulation.universe;
            generator.generation = 0;
        });
        control.add(resetButton);




        JToggleButton pauseButton = new JToggleButton("Start");
        pauseButton.setBackground(Color.GREEN);
        pauseButton.setName("PlayToggleButton");
        control.add(pauseButton);

        /*
         * Chronological actions:
         * -> first it cleans the playing field and the labels
         * -> then it updates the labels and adds them to the info Panel
         * -> following this the playing field is created and then added to the window Panel
         * -> the window Panel is then revalidated to make sure alle the components are placed correctly
         * and then it is repainted
         * -> in the last step the next generation of cells is calculated
         */

        final Timer timer = new Timer(speed, e -> {
            field.removeAll();
            info.removeAll();

            alive.setText("Alive: " + simulation.countAlive() + "        ");
            info.add(alive, LEFT_ALIGNMENT);

            generation.setText("Generation: " + generator.generation + "        ");
            info.add(generation, LEFT_ALIGNMENT);

            for (int i = 0; i < simulation.universe.length; i++) {
                for (int j = 0; j < simulation.universe.length; j++) {
                    cell = new JPanel();
                    if (simulation.universe[i][j]) {
                        cell.setBackground(Color.WHITE);
                    } else {
                        cell.setBackground(Color.darkGray);
                    }
                    field.add(cell);
                }
            }
            window.add(field, BorderLayout.CENTER);
            window.revalidate(); // invokes layout manager to give component a location
            window.repaint(); // makes sure component gets painted
            simulation.universe = generator.nextGeneration();

        });

        pauseButton.addActionListener(arg0 -> {
            if (pauseButton.isSelected()) {
                pauseButton.setText("Pause");
                timer.restart();
            } else {
                pauseButton.setText("Resume");
                timer.stop();
            }
        });

        /*
         * These Buttons control the speed:
         * -> normal has a delay of about a second
         * -> twice has a delay of two thirds of a second
         * -> triple has a delay of one third of a second
         */

        JButton normal = new JButton("1x");
        normal.addActionListener(e -> timer.setDelay(1000));
        control.add(normal);

        JButton twice = new JButton("2x");
        twice.addActionListener(e -> timer.setDelay(666));
        control.add(twice);

        JButton triple = new JButton("3x");
        triple.addActionListener(e -> timer.setDelay(333));
        control.add(triple);

        setVisible(true);


    }








}
