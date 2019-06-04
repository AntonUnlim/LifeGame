import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JPanel {
    private boolean isAlive;
    private int neighbors;

    public Cell(int x, int y, boolean isAlive) {
        super();
        this.isAlive = isAlive;
        setBounds(x * Config.CELL_SIZE, y * Config.CELL_SIZE, Config.CELL_SIZE, Config.CELL_SIZE);
        setColor();
        Border border = BorderFactory.createLineBorder(Config.COLOR_BORDER);
        setBorder(border);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setAlive(!getAlive());
                setColor();
                Config.isPressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Config.isPressed = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(Config.isPressed) {
                    setAlive(!getAlive());
                    setColor();
                }
            }
        });
    }

    public boolean getAlive() {
        return isAlive;
    }

    public void setAlive (boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setNeighbors(int amount) {
        neighbors = amount;
    }

    public int getNeighbors() {
        return neighbors;
    }

    public void setColor() {
        setBackground(isAlive ? Config.COLOR_LIVE : Config.COLOR_NONE);
    }

    public boolean checkNeighbors() {
        if (!isAlive && neighbors == 3) return true;
        else if (!isAlive && (neighbors <=1 || neighbors >=4)) return false;
        else if (isAlive && neighbors == 3) return true;
        else if (isAlive && (neighbors <=1 || neighbors >= 4)) return false;
        else return isAlive;
    }
}
