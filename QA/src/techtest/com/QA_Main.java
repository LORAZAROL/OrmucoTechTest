package techtest.com;

import java.util.Scanner;

public class QA_Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){
            boolean overlapped = overlap(sc);
            if(overlapped){
                System.out.println("The two lines overlap.");
            }else{
                System.out.println("The two lines do not overlap.");
            }
            System.out.println("Continue?(Y/N): ");
            if(!(sc.nextLine().equals("Y")||sc.nextLine().equals("y"))){
                break;
            }
        }

    }

    /**
     * The method takes a scanner as the input, reads in the position
     * of each point of each line.
     * Given the positions, the method sees whether there exists at least
     * one point lies on another line. If there exists, the lines overlap.
     * Assumption: two lines with only one point overlaps are considered overlap.
     *
     * @param sc: the scanner that takes console input of the coordinates of two lines
     * @return true if the two lines overlap
     */
    static boolean overlap(Scanner sc){
        int[] lines = new int[4];
        int index = 0;

        while(index < 4){
            System.out.println("Enter the value of x"+(index+1)+":");
            if(sc.hasNextInt()){
                lines[index] = sc.nextInt();
                index++;
            }else{
                sc.nextLine();
                if(index>0) sc.nextLine();
                System.out.println("Please enter an integer.");
            }
        }

        int minL1 = Math.min(lines[0], lines[1]);
        int maxL1 = Math.max(lines[0], lines[1]);

        int minL2 = Math.min(lines[2], lines[3]);
        int maxL2 = Math.max(lines[2], lines[3]);

        return(((maxL1<=maxL2 && maxL1>=minL2)||(minL1<=maxL2 && minL1>=minL2)) ||
                ((maxL2<=maxL1 && maxL2>=minL1)||(minL2<=maxL1 && minL2>=minL1)));

    }
}
