/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.StateBasedGame;

public class Tiro {
    
    int x, y;
    Circle tiro;
    SpriteSheet bulletSheet;
    Animation bullet;
    
    public Tiro(int x, int y) throws SlickException {
        this.x = x;
        this.y = y;
        tiro = new Circle(x, y, 10);
        bulletSheet = new SpriteSheet("data/image/Fase01/bulletsSheet.png", 10, 10);
        bullet = new Animation(bulletSheet, 100);
        bullet.setAutoUpdate(false);
        bullet.stopAt(2);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawAnimation(bullet, x, y);
    }

    public void update() throws SlickException {
        y -= 15;
        tiro.setY(y);

    }
}