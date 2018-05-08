/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Gabriel
 */
public class Boss {
    
    int x, y, hp;
    Rectangle hitbox;
    Image boss;

    public Boss() {
        x = 300;
        y = 0;
        hp = 1000;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        hitbox = new Rectangle(x, y, 300, 100);
        boss = new Image("data/image/Fase01/body1-1.png");
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(boss, 240, 0);
        g.draw(hitbox);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }
    
}
