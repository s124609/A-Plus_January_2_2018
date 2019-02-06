import java.io.File;
import java.io.IOException;
import java.util.*;
import static java.lang.System.*;

public class Cognates
{
    public static void main (String[] args) throws IOException
    {
        Scanner file = new Scanner(new File("cognates.dat"));
        file.nextLine();

        do {
            String one = file.nextLine();
            String two = file.nextLine();

            out.println(compare(one,two));

        } while (file.hasNextLine());
    }

    private static int compare(String one, String two)
    {
        String[] oneSplit = one.split("");
        String[] twoSplit = two.split("");

        Arrays.sort(oneSplit);
        Arrays.sort(twoSplit);

        int count = 0;

        for (String s : oneSplit) {
            int numberOne = 0;
            int numberTwo = 0;
            for (String s1 : oneSplit){

                if (s.equalsIgnoreCase(s1))
                    numberOne++;
            }

            for (String x1 : twoSplit){
                if (s.equalsIgnoreCase(x1))
                    numberTwo++;
            }

            count += numberTwo % numberOne;
        }

        return count;
    }
}