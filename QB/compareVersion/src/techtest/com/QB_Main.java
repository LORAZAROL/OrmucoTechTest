package techtest.com;

import java.util.Scanner;
import java.util.StringTokenizer;

public class QB_Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("Please enter the first version string: ");
            String v1 = sc.nextLine();
            // quit the process as long as the user types in 'q'
            if(v1.equals("q") || v1.equals("Q")) return;

            System.out.println("Please enter the second version string: ");
            String v2 = sc.nextLine();
            if(v2.equals("q") || v2.equals("Q")) return;

            Comparator result = compare(v1, v2);
            if(result==Comparator.Equal){
                System.out.println(v1+" equals to "+v2);
            }else if(result==Comparator.Greater){
                System.out.println(v1+" is greater than "+v2);
            }else if(result==Comparator.Less){
                System.out.println(v1+" is less than "+v2);
            }else{
                System.out.println("Please input the valid format of version.");
            }
            System.out.println("Press 'Q' to quit");
            System.out.println();
        }
    }

    /**
     * The enum states four possible output of comparing two version strings.
     * The first string can be greater/less than, or equals to the second one.
     * If any of them are not consisted of integers and '.'s, the input is not valid
     */
    public enum Comparator{
        Greater,
        Less,
        Equal,
        InvalidInput
    }

    /**
     * Compare two version strings v1 and v2,
     * give the relative comparison result of v1 relative to v2
     * @param v1 the first version string
     * @param v2 the second version string
     * @return the relative value of v1 to v2
     */
    static Comparator compare(String v1, String v2){
        StringTokenizer st1 = new StringTokenizer(v1, ".");
        StringTokenizer st2 = new StringTokenizer(v2, ".");

        while(st1.hasMoreTokens() && st2.hasMoreTokens()){
            String cur1 = st1.nextToken();
            String cur2 = st2.nextToken();

            int c1 = -1;
            int c2 = -1;
            try{
                c1 = Integer.parseInt(cur1);
                c2 = Integer.parseInt(cur2);
            }catch(NumberFormatException e){
                return Comparator.InvalidInput;
            }
            if(c1<0 || c2<0) return Comparator.InvalidInput; //invalid if the version number is negative
            if(c1<c2) return Comparator.Less;
            else if(c1>c2) return Comparator.Greater;
            else continue;
        }

        // At least one string ends, with all elements ahead identical
        if(st1.hasMoreTokens()) return Comparator.Greater;
        else if(st2.hasMoreTokens()) return Comparator.Less;
        else return Comparator.Equal;   // if both of them ends, then they are equal
    }
}
