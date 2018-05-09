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
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Gabriel
 */
public class TiroBoss {
    
    int x, y;
    float targetX, targetY, arc;
    SpriteSheet bulletSheet;
    Animation bullet;
    Rectangle hitbox;  

    public TiroBoss(int x, int y) throws SlickException {
        this.x = x;
        this.y = y;
        if (Player.x > x) targetX = Player.x - x;
        else targetX = x - Player.x;
        bulletSheet = new SpriteSheet("data/image/Fase01/bulletBoss.png", 40,40);
        bullet = new Animation(bulletSheet, 100);
        bullet.setAutoUpdate(false);
        hitbox = new Rectangle(x, y, 20, 20);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.draw(hitbox);
        g.drawAnimation(bullet, x, y);
    }

    public void update() throws SlickException {
        System.out.println(targetX);
        x += targetX / 100;
        y += 10;
        hitbox.setY(y);
        hitbox.setX(x);
    }   
}
