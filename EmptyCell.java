import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class EmptyCell extends ScalableCell {    
    private int neighboursMinesQuantity = -1;
    private boolean visited = false;
    
    public EmptyCell(World world) {
        super(world);
        refreshImageScale();
    }
    
    public void init() {
        neighboursMinesQuantity = getNeighbours(1, true, Mine.class).size();
        setImage("images/" + neighboursMinesQuantity + "_cell.png");
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
