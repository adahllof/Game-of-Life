package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameOfLife extends JFrame {

    private final int LBL_PREFERRED_HEIGHT = 20;            //Preferred height of labels
    private final int PNL_PREFERRED_WIDTH = 1000;            //Preferred width and height of game field
    private final int NUMBER_OF_GENERATIONS = 500;          //Number of generation to generate
    private final int SIZE_OF_WORLD = 500;
    private final Dimension BTN_SIZE = new Dimension(55, 30);
    private final String STR_RUN = String.valueOf((char) 0X23F5);
    private final String STR_PAUSE = String.valueOf((char) 0X23F8);
    private final String STR_RESET = String.valueOf((char) 0X279E);
    private final String STR_REPEAT = String.valueOf((char) 0X27F3);
    private final Font BTN_FONT = new Font("Ariel", Font.BOLD, 20);
    private final int SIDE_PNL_WIDTH = BTN_SIZE.width * 3 + 20;
    private final Dimension LBL_SIZE = new Dimension(SIDE_PNL_WIDTH - 10, LBL_PREFERRED_HEIGHT);
    private final int TIME = 100_000;

    Random random = new Random();
    private Universe myWorld;
    private Universe oldWorld;
    private int generation;
    Generation gen;


    enum mode {
        RUN,
        PAUSED,
        FINISHED
    }

    mode runMode;

    private final JPanel sidePanel = new JPanel();
    private final JLabel lblGeneration = new JLabel("Generation #0");
    private final JLabel lblAlive = new JLabel("Alive: ");
    private final JLabel lblMode = new JLabel("The Game of Live is running");
    private JPanel gameField;
    private final JButton btnPauseResume = new JButton();
    private final JButton btnReset = new JButton();
    private final JButton btnRepeat = new JButton();
    private final JSlider sldSpeedControl = new JSlider(1, 1000);
    private final JLabel lblSpeedControl = new JLabel("Speed control");


    public GameOfLife() {
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SIDE_PNL_WIDTH + gamePanelSize().width, gamePanelSize().height);
        setLayout(new BorderLayout());
        this.myWorld = new Universe(SIZE_OF_WORLD);
        this.oldWorld = new Universe(SIZE_OF_WORLD);
        gen = new Generation(SIZE_OF_WORLD); //Used to create new generations
        initWorld();
        initComponents();
        setVisible(true);
        runGame();
    }
    public void initWorld() {

        generation = 0;


        //Initialize the world with random
        for (int row = 1; row <= SIZE_OF_WORLD; row++) {
            for (int col = 1; col <= SIZE_OF_WORLD; col++) {
                if (random.nextBoolean()) {
                    myWorld.setLife(row, col);
                } else {
                    myWorld.kill(row, col);
                }
            }
        }

        Universe.copy(myWorld, oldWorld);
    }
    private void initComponents() {

        //Command buttons for controlling the game
        sidePanel.setPreferredSize(new Dimension(SIDE_PNL_WIDTH, gamePanelSize().height));
        sidePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        //Pause/Resume button
        btnPauseResume.setName("PlayToggleButton");
        // btnPauseResume.setAction();
        btnPauseResume.setPreferredSize(BTN_SIZE);
        btnPauseResume.setText(STR_PAUSE);
        btnPauseResume.setFont(BTN_FONT);
        btnPauseResume.setToolTipText("Click to pause the game");
        btnPauseResume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseResume();
            }
        });

        //Reset button
        btnReset.setName("ResetButton");
        btnReset.setPreferredSize(BTN_SIZE);
        btnReset.setText(STR_RESET);
        btnReset.setFont(BTN_FONT);
        btnReset.setToolTipText("Click to start new game!");
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        btnRepeat.setName("btnRepeat");
        btnRepeat.setPreferredSize(BTN_SIZE);
        btnRepeat.setText(STR_REPEAT);
        btnRepeat.setFont(BTN_FONT);
        btnRepeat.setToolTipText("Repeat");
        btnRepeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repeat();
            }
        });
        sidePanel.add(btnReset);
        sidePanel.add(btnPauseResume);
        sidePanel.add(btnRepeat);


        //Init the label for displaying generation number
        lblGeneration.setName("GenerationLabel");
        lblGeneration.setPreferredSize(LBL_SIZE);
        lblGeneration.setVerticalAlignment(1);

        //Init the label for displaying life count
        lblAlive.setName("AliveLabel");
        sidePanel.setBackground(new Color(226, 155, 155));
        lblAlive.setVerticalAlignment(1);
        lblAlive.setPreferredSize(LBL_SIZE);
        sidePanel.add(lblGeneration);
        sidePanel.add(lblAlive);

        //Init label for displaying game mode
        lblMode.setName("lblMode");
        lblMode.setPreferredSize(LBL_SIZE);
        lblMode.setVerticalAlignment(1);
        sidePanel.add(lblMode);

        //Init label for speed slider
        lblSpeedControl.setPreferredSize(new Dimension(LBL_SIZE.width, LBL_PREFERRED_HEIGHT / 2));
        lblSpeedControl.setFont(new Font("Ariel", Font.PLAIN, 12));
        sidePanel.add(lblSpeedControl);

        //Speed control slider
        sldSpeedControl.setPreferredSize(LBL_SIZE);
        sldSpeedControl.setValue(400);
        sidePanel.add(sldSpeedControl);


        add(sidePanel, BorderLayout.WEST);
        sidePanel.setVisible(true);

        //Init the game field panel
        gameField = new GameField(gamePanelSize(), sizeOfSquares(), myWorld);
        gameField.setBackground(new Color(99, 194, 194));
        add(gameField, BorderLayout.CENTER);
        gameField.setVisible(true);


        refreshPanels();
    }
    private void refreshPanels() {
        lblGeneration.setText("Generation# " + generation);
        if (myWorld != null) {
            lblAlive.setText("Alive: " + myWorld.countLife());
        }
        if (runMode != null) {
            switch (runMode) {
                case RUN: {
                    lblMode.setText("The Game of Life is running");
                    btnPauseResume.setText(STR_PAUSE);
                    btnPauseResume.setToolTipText("Click to pause the game");
                    btnPauseResume.setEnabled(true);
                    break;
                }
                case PAUSED: {
                    lblMode.setText("The Game is paused. ");
                    btnPauseResume.setText(STR_RUN);
                    btnPauseResume.setToolTipText("Click to resume the game");
                    btnPauseResume.setEnabled(true);
                    break;
                }
                case FINISHED: {
                    lblMode.setText("The Game has finished.");
                    btnPauseResume.setEnabled(false);
                    break;
                }
                default:
                    lblMode.setText("Unknown state of");
            }

        }
        gameField.repaint();
    }
    private long msDelay() {
        return TIME / sldSpeedControl.getValue();
    }

    private static void waitMs(long msDelay) {
        try {
            Thread.sleep(msDelay);
        } catch (Exception e) {
            System.out.println("An error has occurred in method waitMs. " + e.getMessage());
        }
    }
    private int sizeOfSquares() {
        return PNL_PREFERRED_WIDTH / SIZE_OF_WORLD;
    }
    private int panelWidth() {
        //Adjust Panel with to size of world
        return sizeOfSquares() * (SIZE_OF_WORLD + 2);
    }
    private Dimension gamePanelSize() {
        int width = panelWidth() + SIZE_OF_WORLD / 6;
        int height = sizeOfSquares() * (SIZE_OF_WORLD + 3) + SIZE_OF_WORLD / 4;
        return new Dimension( width, height );
    }
    private void runGame() {
        //Run the game
        runMode = mode.RUN;
        while (true) {
            do {
                if (runMode == mode.RUN) {
                    gen.next(myWorld);
                    generation++;
                    refreshPanels();
                    waitMs(msDelay());
                } else {
                    waitMs(msDelay());
                    refreshPanels();
                }
            } while (generation < NUMBER_OF_GENERATIONS);
            runMode = mode.FINISHED;
        }
    }
    private void pauseResume() {
        switch (runMode) {
            case RUN: {
                btnPauseResume.setText(STR_RUN);
                runMode = mode.PAUSED;
                break;
            }
            case PAUSED: {
                btnPauseResume.setText(STR_PAUSE);
                runMode = mode.RUN;
                break;
            }
            default: {
                btnPauseResume.setText(STR_PAUSE);
                runMode = mode.FINISHED;
            }
        }

    }
    private void reset() {
        runMode = mode.FINISHED;
        waitMs(msDelay() + 100L);
        initWorld();
        refreshPanels();
        runMode = mode.RUN;
    }

    private void repeat() {
        runMode = mode.FINISHED;
        waitMs(msDelay() + 100L);
        Universe.copy(oldWorld, myWorld);
        generation = 0;
        refreshPanels();
        runMode = mode.RUN;
    }

}
