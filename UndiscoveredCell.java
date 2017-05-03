import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class UndiscoveredCell extends Actor {
    private boolean suspected = false;

    public void act() {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if ( Greenfoot.mouseClicked(this) ) {
            Minefield w = (Minefield) getWorld();
            
            if (mi.getButton() == 1) {
                if (!suspected) {
                    
                    if ( w.wasNeverRevealed() ) {
                        w.preRevealInit( getX(), getY() );
                        w.toggleNeverRevealed();
                    }
                    
                    w.checkForMine( getX(), getY() );
                    w.revealEmptyCell( getX(), getY() );
                    w.removeObject(this);
                    
                    w.checkForWin();
                }
            } else if (mi.getButton() == 3) {
                toggleSuspect();
            }
            
            if (w.getMinesQuantity() == 0) {
                w.checkForWin();
            }
        }
        
        if ( Greenfoot.isKeyDown("space") ) {
            getImage().setTransparency(210);
        }
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
    
    public void toggleSuspect() {
        Minefield w = (Minefield) getWorld();
        
        if (!suspected) {
            setImage("images/suspected_cell.png");
            w.setMinesQuantity(w.getMinesQuantity() - 1);
        } else {
            setImage("images/undiscovered_cell.png");
            w.setMinesQuantity(w.getMinesQuantity() + 1);
        }
        
        suspected = !suspected;
    }
    
    public boolean isSuspected() {
        return suspected;
    }
}
