package doctordisease;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class DoctorDisease extends StateBasedGame {
    
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    public static int gameState = 0;
    public static boolean gameRunning = false;
    public static AppGameContainer app;
    
    public DoctorDisease(String gamename) {
        super(gamename);
    }
    
    public static void main(String[] args) {
//        System.setProperty("java.library.path", "libs");
//        System.setProperty("org.lwjgl.librarypath", new File("libs/natives").getAbsolutePath());
        try
            {
		app = new AppGameContainer(new DoctorDisease("Dr Disease"));
		app.setDisplayMode(WIDTH, HEIGHT, true);
                //app.setTargetFrameRate(FPS);
		app.start();
            }
	catch (SlickException ex)
            {
		Logger.getLogger(DoctorDisease.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new IntroState(0));
        this.addState(new Menu(1));
        this.addState(new Fase01(2));
        this.addState(new GameOver(3));
        this.enterState(0);
    }
    
}
