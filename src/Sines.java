import java.io.File;
import java.io.IOException;
import java.util.*;
import static java.lang.System.*;

public class Sines
{
    public static void main (String[] args) throws IOException
    {
        Scanner file = new Scanner(new File("sines.dat"));
        file.nextLine();

        do {
            double A = file.nextInt();
            double a = file.nextInt();
            double b = file.nextInt();

            double B = (A/(Math.sin(Math.toRadians(a))));
            B = B * Math.sin(Math.toRadians(b));

            out.printf("%.1f\n",B);

        } while (file.hasNextInt());
    }
}