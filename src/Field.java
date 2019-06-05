import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Field implements Runnable {
    private Cell[][] field;
    private Cell[][] tempField;
    private JFrame frame;
    private JButton startStopBtn;
    private JButton clearBtn;
    private boolean isRunning = false;
    private Timer timer;
    private JLabel label;
    private int generation = 0;

    private void initFrame() {
        frame = new JFrame();
        startStopBtn = new JButton("Start");
        clearBtn = new JButton("Clear");
        label = new JLabel("Generation: " + generation);
        field = new Cell[Config.FIELD_WIDTH][Config.FIELD_HEIGHT];
        tempField = new Cell[Config.FIELD_WIDTH][Config.FIELD_HEIGHT];
        frame.getContentPane().setLayout(null);
        frame.setSize(Config.CELL_SIZE * Config.FIELD_WIDTH + 20, Config.CELL_SIZE * Config.FIELD_HEIGHT + 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Life Game");
        startStopBtn.setBounds(100, Config.CELL_SIZE * Config.FIELD_HEIGHT + 20, 100, 20);
        clearBtn.setBounds(250, Config.CELL_SIZE * Config.FIELD_HEIGHT + 20, 100, 20);
        label.setBounds(400, Config.CELL_SIZE * Config.FIELD_HEIGHT + 20, 100, 20);
        startStopBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    timer.start();
                    isRunning = true;
                    startStopBtn.setText("Stop");
                }
                else {
                    timer.stop();
                    isRunning = false;
                    startStopBtn.setText("Stat");
                }
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
                generation = 0;
                label.setText("Generation: " + generation);
            }
        });
        frame.add(startStopBtn);
        frame.add(clearBtn);
        frame.add(label);
        frame.setVisible(true);
    }

    private void clearBoard() {
        for(int x = 0; x < Config.FIELD_WIDTH; x++)
            for(int y = 0; y < Config.FIELD_HEIGHT; y++) {
                field[x][y].setAlive(false);
                field[x][y].setColor();
                tempField[x][y].setAlive(false);
            }

    }

    private void initTimer() {
        TimerListener timerListener = new TimerListener();
        timer = new Timer(Config.GAME_SLEEP, timerListener);
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            recalcField();
            generation++;
            label.setText("Generation: " + generation);
        }
    }

    private void initField() {
        for(int x = 0; x < Config.FIELD_WIDTH; x++)
            for(int y = 0; y < Config.FIELD_HEIGHT; y++){
                field[x][y] = new Cell(x, y, false);
                tempField[x][y] = new Cell(x, y, false);
                frame.add(field[x][y]);
            }
    }

    @Override
    public void run() {
        initFrame();
        initField();
        initTimer();
        testFill();
    }

    private void recalcField() {
        for(int x = 0; x < Config.FIELD_WIDTH; x++)
            for(int y = 0; y < Config.FIELD_HEIGHT; y++) {
                field[x][y].setNeighbors(findNeighbors(x,y));
            }

        for(int x = 0; x < Config.FIELD_WIDTH; x++)
            for(int y = 0; y < Config.FIELD_HEIGHT; y++) {
                tempField[x][y].setAlive(field[x][y].checkNeighbors());
            }

        for(int x = 0; x < Config.FIELD_WIDTH; x++)
            for(int y = 0; y < Config.FIELD_HEIGHT; y++) {
                field[x][y].setAlive(tempField[x][y].getAlive());
            }

        for(int x = 0; x < Config.FIELD_WIDTH; x++)
            for(int y = 0; y < Config.FIELD_HEIGHT; y++) {
                field[x][y].setColor();
            }

    }

    private int findNeighbors(int x, int y) {
        int count = 0;
        for(int sx = -1; sx <= 1; sx++)
            for(int sy = -1; sy <= 1; sy++)
                if(!(sx == 0 && sy == 0))
                    if ((x + sx >= 0) && (x + sx < Config.FIELD_WIDTH) && (y + sy >= 0) && (y + sy < Config.FIELD_HEIGHT))
                        if(field[sx + x][sy + y].getAlive()) count++;
        return count;
    }

    private void testFill() {
        for(int x = 0; x < Config.FIELD_WIDTH; x += 3)
            for(int y = 0; y < Config.FIELD_HEIGHT; y += 3) {
                field[x+1][y+1].setAlive(true);
                field[x+1][y+2].setAlive(true);
                field[x+2][y+1].setAlive(true);
                field[x+2][y+2].setAlive(true);

                field[x+1][y+1].setColor();
                field[x+1][y+2].setColor();
                field[x+2][y+1].setColor();
                field[x+2][y+2].setColor();
            }
        field[89][90].setAlive(true);
        field[89][90].setColor();
    }
}
