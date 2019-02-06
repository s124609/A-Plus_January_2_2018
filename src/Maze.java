import java.io.File;
import java.io.IOException;
import java.util.*;

public class Maze {
    public static void main(String[] args) throws IOException {
        aStar.input = new Scanner(new File("maze.dat"));
        aStar.parseMaze();
    }
}

class aStar {
    private static final int V_H_COST = 10;
    private static int tCase = 1;

    static Scanner input;


    static class Cell{
        int heuristicCost = 0; //Heuristic cost
        int finalCost = 0; //G+H
        int i, j;
        Cell parent;

        Cell(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
    }

    //Blocked cells are just null Cell values in grid
    private static Cell [][] grid = new Cell[5][5];

    private static PriorityQueue<Cell> open;

    private static boolean[][] closed;
    private static int startI, startJ;
    private static int endI, endJ;

    private static void setBlocked(int i, int j){
        grid[i][j] = null;
    }

    private static void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }

    private static void setEndCell(int i, int j){
        endI = i;
        endJ = j;
    }

    private static void checkAndUpdateCost(Cell current, Cell t, int cost){
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.heuristicCost+cost;

        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }

    private static void AStar(){

        //add the start location to open list.
        open.add(grid[startI][startJ]);

        Cell current;

        while(true){
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true;

            if(current.equals(grid[endI][endJ])){
                return;
            }

            Cell t;
            //left 2
            if(current.i-2>=0){
                t = grid[current.i-2][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
            }

            //down 2
            if(current.j-2>=0){
                t = grid[current.i][current.j-2];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
            }

            //up 1
            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);

                //up 2
                if(current.j+2<grid[0].length){
                    t = grid[current.i][current.j+2];
                    checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
                }
            }

            //right 1
            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);

                //right 2
                if(current.i+2<grid.length){
                    t = grid[current.i+2][current.j];
                    checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
                }
            }
        }
    }

    /*
    Params :
    tCase = test case No.
    x, y = Board's dimensions
    si, sj = start location's x and y coordinates
    ei, ej = end location's x and y coordinates
    int[][] blocked = array containing inaccessible cell coordinates
    */
    private static void test(int xy, int[][] blocked){
        System.out.println("\n\nTest Case #"+tCase);tCase++;
        //Reset
        long start_time = System.nanoTime();
        grid = new Cell[xy][xy];
        closed = new boolean[xy][xy];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;

            return Integer.compare(c1.finalCost, c2.finalCost);
        });

        for(int i=0;i<xy;++i){
            for(int j=0;j<xy;++j){
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i- aStar.endI)+Math.abs(j- aStar.endJ);
                //System.out.print(grid[i][j].heuristicCost+" ");
            }
            //System.out.println();
        }
        grid[startI][startJ].finalCost = 0;

        //Set blocked cells to null
        for (int bX = 0; bX < blocked.length; bX++) {
            for (int bY = 0; bY < blocked.length; bY++)
                if (blocked[bX][bY] == 1)
                    setBlocked(bX, bY);
        }

        String[][] maze = new String[xy][xy];
        //Display initial map
        System.out.println("Grid: ");
        char block = '▋';
        String open  = "[]";
        for(int i=0;i<xy;++i){
            for(int j=0;j<xy;++j){
                if(i==startI&&j==startJ){ System.out.print("SO  "); //Source
                    maze[i][j] = "SO  "; }
                else if(i==endI && j==endJ){ System.out.print("DE  ");  //Destination
                    maze[i][j] = "DE  "; }
                else if(grid[i][j]!=null){ System.out.print(open + "  ");
                    maze[i][j] = open + "  "; }
                else{ System.out.print(block + "   ");
                    maze[i][j] = block + "   "; }
            }
            System.out.println();
        }
        System.out.println();

        AStar();

        //Display cell score
        System.out.println("\nScores for cells: ");
        for(int i=0;i<xy;++i){
            for(int j=0;j<xy;++j){
                if(grid[i][j]!=null)System.out.printf("%-4d ", grid[i][j].finalCost);
                else System.out.print(block + "    ");
            }
            System.out.println();
        }
        System.out.println();

        if(closed[aStar.endI][aStar.endJ]){
            
            //Trace back the path
            System.out.println("Path: ");
            Cell current = grid[aStar.endI][aStar.endJ];
            System.out.print(current);
            ArrayList<Cell> path = new ArrayList<>();
            path.add(current);
            while(current.parent!=null){
                System.out.print(" -> "+current.parent);
                current = current.parent;
                path.add(current);
            }
            System.out.println("\n");

            Collections.reverse(path);

            int step = 0;
            for (Cell index : path) {
                if (path.contains(index))
                    maze[index.i][index.j] = step++ + "   ";
            }

            //Display final path
            System.out.println("Final Path: ");
            System.out.println(path.toString() + "\n\nFinal Size:\n" + (path.size()-1) + "\n");

            for(int i=0;i<xy;++i) {
                for (int j = 0; j < xy; ++j) {
                    if(i==startI&&j==startJ)System.out.print("SO  "); //Source
                    else if(i==endI && j==endJ)System.out.print("DE  ");  //Destination
                    else System.out.print(maze[i][j]);
                }
                System.out.println();
            }

            System.out.println();
        }else System.out.println("No possible path\n");

        long end_time = System.nanoTime();
        double difference = (end_time - start_time) / 1e6;

        System.out.println(difference + "ms\n");

    }

    static void parseMaze() {
        int num = input.nextInt();

        try {
            do {
                int size = input.nextInt();
                int[][] passable = new int[size][size];

                for (int i = 0; i < size; i++) {
                    String line = input.next();
                    for (int j = 0; j < size; j++) {
                        char test = line.charAt(j);
                        switch (test) {
                            case 'S':
                                setStartCell(i, j);
                                break;
                            case 'E':
                                setEndCell(i, j);
                                break;
                            case '.':
                                passable[i][j] = 0;
                                break;
                            default:
                                passable[i][j] = 1;
                                break;
                        }
                    }
                }
                aStar.test(passable.length, passable);
                num--;
            } while (num > 0);
        } catch (Exception e) {
            System.out.print("");
        }
    }
}