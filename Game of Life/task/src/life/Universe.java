package life;

public class Universe {
    private int size;
    private boolean[][] map;

    public Universe(int size) {
        this.size = size;
        this.map = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = false;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setLife(int row, int col) {
        if (1 <= row && row <= size && 1 <= col && col <= size) {
            map[row - 1][col - 1] = true;
        } else {
            System.out.println("Index out of range! Row = " + row + " col =  " + col);
        }
    }

    public void kill(int row, int col) {
        if (1 <= row && row <= size && 1 <= col && col <= size) {
            map[row - 1][col - 1] = false;
        } else {
            System.out.println("Index out of range! Row = " + row + " col =  " + col);
        }
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j]) {
                    System.out.print('O');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    public int countLife() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (map[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean alive(int row, int col) {
        return map[row - 1][col - 1];
    }

    public int neighbours(int row, int col) {
        int count = 0;
        if (1 <= row && row <= size && 1 <= col && col <= size) {
           if (nw(row,col)) {
               count++;
           }
            if (n(row,col)) {
                count++;
            }
            if (ne(row,col)) {
                count++;
            }
            if (w(row,col)) {
                count++;
            }
            if (e(row,col)) {
                count++;
            }
            if (sw(row,col)) {
                count++;
            }
            if (s(row,col)) {
                count++;
            }
            if (se(row,col)) {
                count++;
            }
        } else {
            System.out.println("Index out of range! Row = " + row + " col =  " + col);
        }
        return count;
    }

    public static void copy(Universe from, Universe to) {
        if (from == null || to == null) {
            System.out.println("Error, the universe is null!");
        } else if (from.getSize() != to.getSize()) {
            System.out.println("Error, the size of the universes must be equal!");
        } else {
            for (int row = 1; row <= from.getSize(); row++) {
                for (int col = 1; col <= from.getSize(); col++) {
                    if (from.alive(row, col)) {
                        to.setLife(row, col);
                    } else {
                        to.kill(row, col);
                    }
                }
            }
        }
    }


    private boolean nw(int row, int col) {
        row--;
        col--;
        if (row < 1) {
            row = size;
        }
        if (col < 1) {
            col = size;
        }
        return map[row - 1][col - 1];
    }

    private boolean n(int row, int col) {
        row--;
        if (row < 1) {
            row = size;
        }
        return map[row - 1][col - 1];
    }

    private boolean ne(int row, int col) {
        row--;
        col++;
        if (row < 1) {
            row = size;
        }
        if (col > size) {
            col = 1;
        }
        return map[row - 1][col - 1];
    }

    private boolean w(int row, int col) {
        col--;
        if (col < 1) {
            col = size;
        }
        return map[row - 1][col - 1];
    }

    private boolean e(int row, int col) {
        col++;
        if (col > size) {
            col = 1;
        }
        return map[row - 1][col - 1];
    }

    private boolean sw(int row, int col) {
        row++;
        col--;
        if (row > size) {
            row = 1;
        }
        if (col < 1) {
            col = size;
        }
        return map[row - 1][col - 1];
    }

    private boolean s(int row, int col) {
        row++;
        if (row > size) {
            row = 1;
        }

        return map[row - 1][col - 1];
    }

    private boolean se(int row, int col) {
        row++;
        col++;
        if (row > size) {
            row = 1;
        }
        if (col > size) {
            col = 1;
        }
        return map[row - 1][col - 1];
    }
}
