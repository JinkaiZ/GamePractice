package com.jktech;

public class Simulation {

    public static int DEAD = 0;
    public static int ALIVE = 1;

    int _width;
    int _height;
    int[][] _board;

    public Simulation(int width, int height) {
        _width = width;
        _height = height;
        _board = new int[width][height];
    }

    public static Simulation copy(Simulation simulation){
        Simulation copy = new Simulation(simulation._width, simulation._height);
        for (int y = 0; y < simulation._height; y++) {
            for (int x = 0; x < simulation._width; x++) {
                copy.setState(x, y, simulation.getState(x, y));
            }
        }
        return copy;
    }

    public void printBoard() {
        System.out.println("---");
        for (int y = 0; y < _height; y++) {
            String line = "|";
            for (int x = 0; x < _width; x++) {
                if (_board[x][y] == DEAD) {
                    line += ".";
                } else {
                    line += "*";
                }
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println("---\n");
    }

    public void setAlive(int x, int y) {
        setState(x,y,ALIVE);
    }

    public void setDead(int x, int y) {

        setState(x,y,DEAD);
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;
        count += getState(x - 1, y - 1);
        count += getState(x, y - 1);
        count += getState(x + 1, y - 1);

        count += getState(x - 1, y);
        count += getState(x + 1, y);

        count += getState(x - 1, y + 1);
        count += getState(x, y + 1);
        count += getState(x + 1, y + 1);

        return count;
    }

    //Limit the bound of the board.
   public void setState(int x, int y, int state){
       if (x < 0 || x >= _width) {
           return;
       }
       if (y < 0 || y >= _height) {
           return;
       }

       _board[x][y] = state;

   }
    public int getState(int x, int y) {
        if (x < 0 || x >= _width) {
            return DEAD;
        }
        if (y < 0 || y >= _height) {
            return DEAD;
        }
        return _board[x][y];
    }

    public void step() {
        int[][] newBorad = new int[_width][_height];
        for (int y = 0; y < _height; y++) {
            for (int x = 0; x < _width; x++) {
                int aliveNeighbours = countAliveNeighbours(x, y);
                if (getState(x, y) == ALIVE) {
                    if (aliveNeighbours < 2) {
                        newBorad[x][y] = DEAD;
                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        newBorad[x][y] = ALIVE;
                    } else if (aliveNeighbours > 3) {
                        newBorad[x][y] = DEAD;
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        newBorad[x][y] = ALIVE;
                    }
                }

            }

        }
        _board = newBorad;
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation(8, 5);
        simulation.setAlive(2, 2);
        simulation.setAlive(3, 2);
        simulation.setAlive(4, 2);

        simulation.printBoard();

        simulation.step();
        simulation.printBoard();

        simulation.step();
        simulation.printBoard();


    }
}
