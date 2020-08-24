public class Permutations {

    static int[] options = {5, 6, 7, 8, 9};
    static int[] temp = new int[options.length];
    static int choose = 4;
    static int[] combo = new int[choose];
    static int loopCount = 0;

    

    public static void permute(int pos) {

        if(pos == options.length) {
            for (int j:temp) {
                System.out.print(j+" ");
            }
            System.out.println("");
            return;
        } else {
            for (int i = 0; i < options.length; i++) {
                boolean found = false;
                for (int j=0; j < pos; j++) {
                    if (temp[j] == options[i]) {
                        found = true;
                        break;
                    }
                }
                if (found)
                    continue;
                temp[pos] = options[i];
                //System.out.print(i + " ");
                permute(pos+1);
            }
        }


    }

    

    public static int factorial(int x) {
        if(x <= 1) {
            return 1;
        } else {
            return x * factorial(x -1);
        }
    }

    public static void combinate(int pos, int prev) {

        if(pos == choose) {
            for (int j:combo) {
                System.out.print(j+" ");
            }
            System.out.println("");
            return;
        } else {
            for (int i = prev; i < options.length; i++) {

                combo[pos] = options[i];
                //System.out.println("operating with pos= " + pos + " i= " + i);
                combinate(pos + 1, i + 1);
            }
        }

    }


}