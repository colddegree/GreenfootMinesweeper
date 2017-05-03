import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Minefield extends World {
    private static final int DEFAULT_CELL_SIZE = 16;
    private static final int BACKGROUNDS_QUANTITY = 6;
    private boolean neverRevealed = true;
    private int minesQuantity;

    public Minefield() {
        super(20, 15, DEFAULT_CELL_SIZE * 2);
        this.minesQuantity = 25;
        init();
    }
    
    public Minefield(int width, int height, int cellSize, int minesQuantity) {
        super(width, height, cellSize);
        this.minesQuantity = minesQuantity;
        init();
    }
    
    public void init() {
        setPaintOrder(UndiscoveredCell.class, Mine.class, EmptyCell.class);
        setBackground("images/backgrounds/" + Greenfoot.getRandomNumber(BACKGROUNDS_QUANTITY) + ".png");
        coverFieldWithUndiscoveredCells();        
        
        Greenfoot.start();
    }
    
    public void preRevealInit(int x, int y) {
        generateMines(x, y);
        fillFieldWithEmptyCells();
    }
    
    public void generateMines(int firstRevealX, int firstRevealY) {
        int maxMines = getWidth() * getHeight() - 1;
        if (minesQuantity > maxMines) {
            minesQuantity = maxMines;
        }
        
        for (int i = 0; i < minesQuantity; i++) {
            
            int x, y;
            do {
                x = Greenfoot.getRandomNumber( getWidth() );
                y = Greenfoot.getRandomNumber( getHeight() );
            } while ( (x == firstRevealX) && (y == firstRevealY) || (getObjectsAt(x, y, Mine.class).size() > 0) );
            
            Mine mine = new Mine();
            addObject(mine, x, y);
            mine.init();
        }
    }
    
    public void fillFieldWithEmptyCells() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                if (getObjectsAt(x, y, Mine.class).size() == 0) {
                    EmptyCell ec = new EmptyCell();
                    addObject(ec, x, y);
                    ec.init();
                }
            }
        }
    }
    
    public void coverFieldWithUndiscoveredCells() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                UndiscoveredCell uc = new UndiscoveredCell();
                addObject(uc, x, y);
                uc.init();
            }
        }
    }
    
    public void checkForMine(int x, int y) {
        List<Mine> mines = getObjectsAt(x, y, Mine.class);
        if (mines.size() > 0) {            
            loseGame( mines.get(0) );
        }
    }

    public void revealEmptyCell(int x, int y) {
        if ( (x < 0) || ( x > getWidth() ) || (y < 0) || ( y > getHeight() ) ) {
            return;
        }
       
        List<EmptyCell> emptyCells = getObjectsAt(x, y, EmptyCell.class);
        if (emptyCells.size() == 0) {
            return;
        }
    
        EmptyCell ec = emptyCells.get(0);
        if ( ec.isVisited() ) {
            return;
        }
        
        List<UndiscoveredCell> uCells = getObjectsAt(x, y, UndiscoveredCell.class);
        if (uCells.size() == 0) {
            return;
        }
        
        UndiscoveredCell uc = uCells.get(0);
        if ( uc.isSuspected() ) {
            return;
        }
        
        removeObject(uc);

        if (ec.getNeighboursMinesQuantity() == 0) {
            ec.toggleVisited();
            
            revealEmptyCell(x - 1, y);
            revealEmptyCell(x + 1, y);
            
            revealEmptyCell(x, y - 1);
            revealEmptyCell(x, y + 1);
            
            revealEmptyCell(x - 1, y - 1);
            revealEmptyCell(x - 1, y + 1);
            revealEmptyCell(x + 1, y - 1);
            revealEmptyCell(x + 1, y + 1);
        }
    }
    
    public boolean wasNeverRevealed() {
        return neverRevealed;
    }
    
    public void toggleNeverRevealed() {
        neverRevealed = !neverRevealed;
    }

    public int getMinesQuantity() {
        return minesQuantity;
    }
    
    public void setMinesQuantity(int newValue) {
        minesQuantity = newValue;
    }
    
    public void checkForWin() {
        boolean failed = false;
        
        for (int y = 0; y < getHeight(); y++) {
            if (failed) {
                break;
            }
            for (int x = 0; x < getWidth(); x++) {
                List<UndiscoveredCell> uCells = getObjectsAt(x, y, UndiscoveredCell.class);
                List<Mine> mines = getObjectsAt(x, y, Mine.class);

                if ( uCells.size() != mines.size() ) {
                    failed = true;
                    break;
                }
            }
        }
        
        if (!failed) {
            for (int y = 0; y < getHeight(); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    List<UndiscoveredCell> uCells = getObjectsAt(x, y, UndiscoveredCell.class);
                    List<Mine> mines = getObjectsAt(x, y, Mine.class);
                    
                    if ( uCells.size() == mines.size() && mines.size() > 0 ) {
                        UndiscoveredCell uc = uCells.get(0);
                        if ( !uc.isSuspected() ) {
                            uCells.get(0).toggleSuspect();
                        }
                    }
                }
            }
            
            showText("You win!", getWidth() / 2, getHeight() / 2);
            Greenfoot.stop();
        }
   }
   
   public void loseGame(Mine explodedMine) {
       for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                List<UndiscoveredCell> uCells = getObjectsAt(x, y, UndiscoveredCell.class);
                List<Mine> mines = getObjectsAt(x, y, Mine.class);
                
                if ( uCells.size() > mines.size() && uCells.get(0).isSuspected() ) {
                    uCells.get(0).setImage("images/wrong_suspected_mine.png");
                } else if ( uCells.size() == mines.size() && mines.size() > 0 ) {
                    uCells.get(0).setImage("images/0_cell.png");
                }
            }
        }
       
       explodedMine.setImage("images/exploded_mine.png");
       showText("Game over!", getWidth() / 2, getHeight() / 2);
        
       setPaintOrder(Mine.class, UndiscoveredCell.class, EmptyCell.class);
       Greenfoot.stop();
   }
}
