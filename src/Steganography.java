import java.io.File;
import java.io.IOException;
import java.util.*;
import static java.lang.System.*;

public class Steganography
{
    public static void main (String[] args) throws IOException
    {
        Scanner file = new Scanner(new File("steganography.dat"));
        file.nextLine();

        do {
            String encoded = file.nextLine();
            StringBuilder decoded = new StringBuilder();

            for (int i = 0; i < encoded.length(); i += 2)
                decoded.append(encoded.charAt(i));

            out.println(decoded);

        } while (file.hasNextLine());
    }
}