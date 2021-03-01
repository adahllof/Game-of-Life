package life;

public class Generation {
    private Universe nextGeneration;
    private int size;

    public Generation(int size) {
        this.size = size;
        this.nextGeneration = new Universe(size);
    }
    public void next(Universe initial) {
        //Compute next generation starting with supplied initial universe
        if (this.size == initial.getSize()) {
            //Compute next generation
            int countNeighbours;
            for (int row = 1; row <= size; row++) {
                for (int col = 1; col <= size; col++) {
                    countNeighbours = initial.neighbours(row, col);
                    if (initial.alive(row, col) && 2 <= countNeighbours &&  countNeighbours <= 3) {
                        nextGeneration.setLife(row, col);
                    } else if (initial.alive(row, col)) {
                        nextGeneration.kill(row, col);
                    } else if (countNeighbours == 3) {
                        nextGeneration.setLife(row, col);
                    } else {
                        nextGeneration.kill(row, col);
                    }
                }
            }
            updateUniverse(initial);
        } else {
            System.out.println("The initial world and the next generation must have the same size!");
        }
    }

    private void updateUniverse(Universe initial) {
        if (this.size == initial.getSize()) {
            for (int row = 1; row <= size; row++) {
                for (int col = 1; col <= size; col++) {
                    if (nextGeneration.alive(row, col)) {
                        initial.setLife(row, col);
                    } else {
                        initial.kill(row, col);
                    }
                }
            }
        } else {
            System.out.println("The initial world and the next generation must have the same size!");
        }
    }
}
