import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class EmptyCell extends Actor {    
    private int neighboursMinesQuantity;
    private boolean visited = false;
    
    public void act() {
        
    }

    public void init() {
        neighboursMinesQuantity = getNeighbours(1, true, Mine.class).size();
        setImage("images/" + neighboursMinesQuantity + "_cell.png");
        int cellSize = getWorld().getCellSize();
        getImage().scale(cellSize, cellSize);
    }
    
    public int getNeighboursMinesQuantity() {
        return neighboursMinesQuantity;
    }
    
    public boolean isVisited() {
        return visited;
    }
    
    public void toggleVisited() {
        visited = !visited;
    }
}
