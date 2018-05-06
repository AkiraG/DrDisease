/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.StateBasedGame;

public class Tiro {
    
    int x, y;
    Circle tiro;
    
    public Tiro(int x, int y) throws SlickException {
        this.x = x;
        this.y = y;
        tiro = new Circle(x, y, 10);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.draw(tiro);
    }

    public void update() throws SlickException {
        y -= 15;
        tiro.setY(y);
    }        
}