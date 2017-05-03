import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ScalableCell extends Actor {
    private World w;
    
    public ScalableCell(World world) {
        this.w = world;
    }
    
    public void refreshImageScale() {
        int cellSize = w.getCellSize();
        getImage().scale(cellSize, cellSize);
    }
    
    public void setImage(String filename) {
        super.setImage(filename);
        refreshImageScale();
    }  
}
