import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Mine extends Actor {
    
    public void act() {
        
    }
    
    public void init() {
        refreshImageScale();
    }
    
    public void refreshImageScale() {
        int cellSize = getWorld().getCellSize();
        getImage().scale(cellSize, cellSize);
    }
    
    public void setImage(String filename) {
        super.setImage(filename);
        refreshImageScale();
    }
}
