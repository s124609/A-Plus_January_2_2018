import java.io.File;
import java.io.IOException;
import java.util.*;
import static java.lang.System.*;

public class Lethologica
{
    public static void main(String[] args) throws IOException
    {
        Scanner file = new Scanner(new File("lethologica.dat"));
        file.nextLine();

        do {
            String line = file.nextLine();
            String word = file.nextLine();

            line = line.replaceAll("\\*", word);

            out.println(line);

        } while (file.hasNextLine());
    }
}
