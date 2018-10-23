import java.util.Scanner;
import java.util.TreeSet;

public class ThreadClass implements Runnable {
    private static TreeSet<Integer> Integers = new TreeSet();

    /**
     * второй поток
     */
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                if (!Integers.isEmpty()) {
                    System.out.println("Delete " + Integers.first());
                    Integers.remove(Integers.first());
                }
            } catch (InterruptedException e) {
            }
        }
    }


    /**
     * первый поток, основная программа
     *
     * @param args
     */
    public static void main(String[] args) {
        Parser parser = new Parser();
        Thread thread = new Thread(new ThreadClass());
        thread.setDaemon(true);
        thread.start();
        Scanner in = new Scanner(System.in);
        while (true) {
            String str = in.nextLine();
            int result = parser.parse(str);
            if (result != -1) {
                Integers.add(result);
                System.out.println("Add " + result);
            } else {
                System.out.println("Wrong String");
            }
        }
    }
}

