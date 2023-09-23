import java.util.Scanner;

public class DrawingProgram {
    public static void main(String[] args) {
        DrawingCanvas canvas = new DrawingCanvas();
        DrawingCommandProcessor processor = new DrawingCommandProcessor(canvas);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                continue; // Skip empty input
            }

            String[] tokens = input.split(" ");
            char command = tokens[0].charAt(0);

            try {
                switch (Character.toUpperCase(command)) {
                    case 'C':
                        processor.createCanvas(tokens);
                        break;

                    case 'L':
                        processor.drawLine(tokens);
                        break;

                    case 'R':
                        processor.drawRectangle(tokens);
                        break;

                    case 'Q':
                        System.out.println("Quitting the program.");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid command.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

            canvas.printCanvas();
        }
    }
}

class DrawingCanvas {
    private char[][] canvas;
    private int width;
    private int height;

    public void createCanvas(int w, int h) {
        if (w <= 0 || h <= 0) {
            System.out.println("Invalid canvas dimensions.");
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
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        if (canvas == null) {
            System.out.println("Please create a canvas first.");
            return;
        }

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

    public void drawRectangle(int x1, int y1, int x2, int y2) {
        drawLine(x1, y1, x1, y2);
        drawLine(x1, y1, x2, y1);
        drawLine(x2, y1, x2, y2);
        drawLine(x1, y2, x2, y2);
    }

    public void printCanvas() {
        if (canvas == null) {
            System.out.println("Canvas is empty. Create a canvas first.");
            return;
        }

        for (int i = 0; i <= width + 1; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (int i = 0; i < height; i++) {
            System.out.print("|");
            for (int j = 0; j < width; j++) {
                System.out.print(canvas[i][j]);
            }
            System.out.println("|");
        }

        for (int i = 0; i <= width + 1; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}

class DrawingCommandProcessor {
    private DrawingCanvas canvas;

    public DrawingCommandProcessor(DrawingCanvas canvas) {
        this.canvas = canvas;
    }

    public void createCanvas(String[] tokens) {
        if (tokens.length == 3) {
            int w = Integer.parseInt(tokens[1]);
            int h = Integer.parseInt(tokens[2]);
            canvas.createCanvas(w, h);
        } else {
            System.out.println("Invalid command. Usage: C <width> <height>");
        }
    }

    public void drawLine(String[] tokens) {
        if (tokens.length == 5) {
            int x1 = Integer.parseInt(tokens[1]);
            int y1 = Integer.parseInt(tokens[2]);
            int x2 = Integer.parseInt(tokens[3]);
            int y2 = Integer.parseInt(tokens[4]);
            canvas.drawLine(x1, y1, x2, y2);
        } else {
            System.out.println("Invalid command. Usage: L <x1> <y1> <x2> <y2>");
        }
    }

    public void drawRectangle(String[] tokens) {
        if (tokens.length == 5) {
            int x1 = Integer.parseInt(tokens[1]);
            int y1 = Integer.parseInt(tokens[2]);
            int x2 = Integer.parseInt(tokens[3]);
            int y2 = Integer.parseInt(tokens[4]);
            canvas.drawRectangle(x1, y1, x2, y2);
        } else {
            System.out.println("Invalid command. Usage: R <x1> <y1> <x2> <y2>");
        }
    }
}


