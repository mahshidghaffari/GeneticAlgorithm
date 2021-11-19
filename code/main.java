import java.util.*;


public class main {

    public static String man = "mahshid";

    public static void main(String[] args) {
        GA test = new GA(5, 1.00);
        test.printPop();
        test.setFittness();
        test.naturalSelection();
    }
}
