import java.util.*;


public class main {

    public static String man = "mahshid";

    public static void main(String[] args) {
        // GA test = new GA(1000, 1.00);
        // test.printPop();
        // test.fittness();
        int selectId1;
		int selectId2;
		String selectedIds = "";
       
        selectId1 = 1;
        selectId2 = 3;
        int[] selectedIdsIntArr = {selectId1, selectId2};
        Arrays.sort(selectedIdsIntArr);
        selectedIds = selectedIdsIntArr.toString();
        System.out.println("+++++++++++++++++++++++++");
        System.out.println();
    }

}
