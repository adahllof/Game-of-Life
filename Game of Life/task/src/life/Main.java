package life;

public class Main {
    public static void main(String[] args) {

        new GameOfLife();

    }
/*
    This not used any more:

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }
        catch (IOException | InterruptedException e) {
            System.out.println("An error occurred when trying to clear the console!");
        }

    }
    private static void waitMs(long msDelay) {
        try {
            Thread.sleep(msDelay);
        } catch (Exception e) {
            System.out.println("An error has occurred int method waitMs. " + e.getMessage());
        }
    }

 */
}
