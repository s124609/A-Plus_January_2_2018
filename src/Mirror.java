import java.io.File;
import java.io.IOException;
import java.util.*;
import static java.lang.System.*;

public class Mirror
{
    public static void main (String[] args) throws IOException
    {
        Scanner file = new Scanner(new File("mirror.dat"));
        file.nextLine();

        do {
            for (int i = 0; i < 7; i++){
                StringBuilder line = new StringBuilder();
                line.append(file.nextLine());

                out.print(line + "|");

                line.reverse();

                out.println(line);
            }

            out.println("***");

        } while (file.hasNextLine());
    }
}