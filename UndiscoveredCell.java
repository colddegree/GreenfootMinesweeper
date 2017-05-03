import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class UndiscoveredCell extends ScalableCell {
    private boolean suspected = false;
    
    public UndiscoveredCell(World world) {
        super(world);
        refreshImageScale();
    }

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
