import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class JudgeMaze {
    private char[][] maze;
    private int[][] shadow;

    private JudgeMaze() throws Exception {
        Scanner file = new Scanner(new File("maze.dat"));
        int times = file.nextInt();file.nextLine();                 // times = number of mazes
        for(int num = 1; num <= times; num++) {                      // repeats for every maze
            int r = file.nextInt();                                 // r = size of maze
            maze = new char[r][r];
            shadow = new int[r][r];
            int[] locs = new int[4];                                // locs[] contains start and end positions
            file.nextLine();

            for(int i = 0; i < r; i++) {                            // repeats for the maze size
                maze[i] = file.nextLine().toCharArray();            // fills each row of maze[][] with file.nextLine
                Arrays.fill(shadow[i],1<<16);                   // fills shadow[][] with distance between positions in the maze (65536)
            }
            for(int g = 0; g < maze.length; g++){                   // finds start and end position in the maze and sets them in locs[]
                for(int l = 0; l < maze[g].length; l++) {
                    if(maze[g][l] == 'S'){                          // start position
                        locs[0] = g;
                        locs[1] = l;
                    }
                    if(maze[g][l] == 'E'){                          // end position
                        locs[2] = g;
                        locs[3] = l;
                    }
                }
            }
            permu(locs[0], locs[1],0);                      // runs permu() with the start position and sets current path length to 0

            System.out.println((shadow[locs[2]][locs[3]] != (1 << 16)) ? shadow[locs[2]][locs[3]] : "NOT POSSIBLE");            // if shadow[endR][endC] != 65536 it will display the path length
                                                                                                                                // else it will display "NOT POSSIBLE"
                                                                                                                                // booleanCondition ? valueIfTrue : valueIfFalse

            /*System.out.println("Maze number: " + num);

            for(int i=0;i<r;++i){
                for(int j=0;j<r;++j){
                    if(i==locs[0]&&j==locs[1])System.out.print("S  ");
                    else if(i==locs[2]&&j==locs[3])System.out.print("E  ");
                    else if(maze[i][j]!='#')System.out.print(".  ");
                    else System.out.print("▋  ");
                }
                System.out.println();
            }

            if (shadow[locs[2]][locs[3]] != (1 << 16)) {
                System.out.println("\nShortest path length: " + shadow[locs[2]][locs[3]]);
                System.out.println("Path length for cells: ");
                for(int i=0;i<r;++i){
                    for(int j=0;j<r;++j){
                        if(shadow[i][j]!=1<<16)System.out.printf("%-2d ", shadow[i][j]);
                        else System.out.print("▋  ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
            else System.out.println("NOT POSSIBLE\n");*/
        }
    }

    private void permu(int r, int c, int length) {                 // [r][c] = current position, length = path length
        if((r >= 0) && (c >= 0) && (r < maze.length) && (c < maze[r].length) && (maze[r][c] != '#') && (length < shadow[r][c])) {       // check if ([r][c] is bounds) & (is not a wall) & (length < previous length at position)
            shadow[r][c] = length;                // sets shadow[r][c] to length of path
            permu(r+2,c,length+1);      // Down 2
            permu(r-2,c,length+1);      // Up 2
            permu(r,c+2,length+1);      // Right 2
            permu(r,c-2,length+1);      // Left 2
            permu(r,c+1,length+1);      // Right 1
            permu(r-1,c,length+1);      // Up 1
        }
    }


    public static void main(String[] args) throws Exception {
        new JudgeMaze();
    }
}