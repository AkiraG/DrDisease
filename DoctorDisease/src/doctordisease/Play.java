/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.*;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.state.*;

/**
 *
 * @author saita
 */
public class Play extends BasicGameState implements InputProviderListener {
    
    public final int ID = 2;
    static List<Line> EDGE = new ArrayList <Line>();
    static Player guts;
    Boss firstBoss;
    private Command esc = new BasicCommand("esc");
    InputProvider provider;
    String message;
    boolean pause = false;
   
    public Play(int state){    
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        EDGE.add(new Line(2, 2, 1022, 2)); // desenhando a parte do game
        EDGE.add(new Line(1, 1, 1, 767)); // desenhando a parte do game
        EDGE.add(new Line(1023, 767, 1, 767)); // desenhando a parte do game
        EDGE.add(new Line(1023, 767, 1023, 1)); // desenhando a parte do game
        guts = new Player();
        guts.init(gc, sbg);
        firstBoss = new Boss();
        firstBoss.init(gc, sbg);
        provider = new InputProvider(gc.getInput());
        provider.addListener(this);
        provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), esc);
    }
   
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        EDGE.forEach(EDGE -> g.draw(EDGE));
        firstBoss.render(gc, sbg, g);
        guts.render(gc, sbg, g);  
    }
   
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
<<<<<<< HEAD
        if (pause == false){
            guts.update(gc, sbg, delta);
            firstBoss.update(gc, sbg, ID);
        }
        else {
            guts.pause();
            firstBoss.pause();
        }
=======
        guts.update(gc, sbg, delta);
>>>>>>> 245a546bc6d93ed72e528f6a75289c65a054fc76
    }
    
    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void controlPressed(Command cmnd) {
        if (cmnd.toString().contains("esc")) pause = !pause;
    }

    @Override
    public void controlReleased(Command cmnd) {
        
    }
    
}