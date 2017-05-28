
import java.util.Scanner;
    /*
    By:
            Jeff Gyldenbrand - jegyl16
            Bjarke Kreiberg - bjkre16
            Mathias Helsengren - mahel16
    */

public class Treesort {
    public static void main(String[] args) {

        Dict dict = new DictBinTree();

	int k;
	Scanner sc = new Scanner(System.in);
	while (sc.hasNextInt()) {
	    k = sc.nextInt();

            dict.insert(k);
       }
            int[] list = dict.orderedTraversal();

            for (int i = 0; i < list.length; i++) {
                System.out.println(list[i]);
            }
   }

}
