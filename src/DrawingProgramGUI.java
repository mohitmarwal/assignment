import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawingProgramGUI extends JFrame {
    private char[][] canvas;
    private int width;
    private int height;
    private DrawingPanel drawingPanel;

    public DrawingProgramGUI() {
        setTitle("Drawing Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        canvas = null;
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton createButton = new JButton("Create Canvas");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCanvas();
            }
        });

        JButton lineButton = new JButton("Draw Line");
        lineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLine();
            }
        });

        JButton rectangleButton = new JButton("Draw Rectangle");
        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawRectangle();
            }
        });

        controlPanel.add(createButton);
        controlPanel.add(lineButton);
        controlPanel.add(rectangleButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void createCanvas() {
        try {
            String widthStr = JOptionPane.showInputDialog("Enter canvas width:");
            String heightStr = JOptionPane.showInputDialog("Enter canvas height");

            int w = Integer.parseInt(widthStr);
            int h = Integer.parseInt(heightStr);

            if (w <= 0 || h <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid canvas dimensions.");
                return;
            }

            width = w;
            height = h;
            canvas = new char[h][w];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    canvas[i][j] = ' ';
                }
            }
            drawingPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid integers.");
        }
    }

    private void drawLine() {
        if (canvas == null) {
            JOptionPane.showMessageDialog(this, "Please create a canvas first.");
            return;
        }

        try {
            String x1Str = JOptionPane.showInputDialog("Enter x1:");
            String y1Str = JOptionPane.showInputDialog("Enter y1:");
            String x2Str = JOptionPane.showInputDialog("Enter x2:");
            String y2Str = JOptionPane.showInputDialog("Enter y2:");

            int x1 = Integer.parseInt(x1Str);
            int y1 = Integer.parseInt(y1Str);
            int x2 = Integer.parseInt(x2Str);
            int y2 = Integer.parseInt(y2Str);

            drawLineOnCanvas(x1, y1, x2, y2);
            drawingPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid integers.");
        }
    }

    private void drawRectangle() {
        if (canvas == null) {
            JOptionPane.showMessageDialog(this, "Please create a canvas first.");
            return;
        }

        try {
            String x1Str = JOptionPane.showInputDialog("Enter x1:");
            String y1Str = JOptionPane.showInputDialog("Enter y1:");
            String x2Str = JOptionPane.showInputDialog("Enter x2:");
            String y2Str = JOptionPane.showInputDialog("Enter y2:");

            int x1 = Integer.parseInt(x1Str);
            int y1 = Integer.parseInt(y1Str);
            int x2 = Integer.parseInt(x2Str);
            int y2 = Integer.parseInt(y2Str);

            drawRectangleOnCanvas(x1, y1, x2, y2);
            drawingPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid integers.");
        }
    }

    private void drawLineOnCanvas(int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            // Single point, just set it to 'x'
            if (x1 >= 1 && x1 <= width && y1 >= 1 && y1 <= height) {
                canvas[y1 - 1][x1 - 1] = 'x';
            }
        } else {
            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);
            int sx = (x1 < x2) ? 1 : -1;
            int sy = (y1 < y2) ? 1 : -1;
            int err = dx - dy;

            while (true) {
                if (x1 >= 1 && x1 <= width && y1 >= 1 && y1 <= height) {
                    canvas[y1 - 1][x1 - 1] = 'x';
                }

                if (x1 == x2 && y1 == y2) {
                    break;
                }

                int e2 = 2 * err;
                if (e2 > -dy) {
                    err -= dy;
                    x1 += sx;
                }
                if (e2 < dx) {
                    err += dx;
                    y1 += sy;
                }
            }
        }
    }

    private void drawRectangleOnCanvas(int x1, int y1, int x2, int y2) {
        drawLineOnCanvas(x1, y1, x1, y2);
        drawLineOnCanvas(x1, y1, x2, y1);
        drawLineOnCanvas(x2, y1, x2, y2);
        drawLineOnCanvas(x1, y2, x2, y2);
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int cellWidth = getWidth() / width;
            int cellHeight = getHeight() / height;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (canvas != null && canvas[i][j] == 'x') {
                        g.setColor(Color.BLACK);
                        g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DrawingProgramGUI program = new DrawingProgramGUI();
                program.setVisible(true);
            }
        });
    }
}
