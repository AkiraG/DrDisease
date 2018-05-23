/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.Iterator;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Gabriel
 */
public class TiroBoss {
    
    int x, y;
    float targetX, targetY, ang;
    SpriteSheet bulletSheet ;
    Animation bullet;
    Circle hitbox;  

    public TiroBoss(int x, int y, float ang) throws SlickException {
        this.x = x;
        this.y = y;
        this.ang = ang;
        
        bulletSheet = new SpriteSheet("data/image/Fase01/bulletBoss.png", 40,40);
        bullet = new Animation(bulletSheet, 100);
        bullet.setAutoUpdate(false);
        bullet.getCurrentFrame().setCenterOfRotation(16, 18);
        bullet.stopAt(2);
        
        targetY = (Player.y - y + 15) / 10;
        if (Player.x > x) {
            targetX = (Player.x - x + 20) / targetY;
            this.ang *= -1;
        }
        else {
            targetX = ((x - Player.x - 20) / targetY) * -1;
        }
        bullet.getCurrentFrame().rotate(this.ang);
        hitbox = new Circle(x, y, 6);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawAnimation(bullet, x, y);
        g.draw(hitbox);
    }

    public void update() throws SlickException {
        if (bullet.getFrame() == 0){
            x += targetX;
            y += 10;
            hitbox.setLocation(x + bullet.getCurrentFrame().getCenterOfRotationX(), y + bullet.getCurrentFrame().getCenterOfRotationY());
        }
        for(Iterator<Line> iter = Play.EDGE.iterator(); 
            iter.hasNext();) {
                Line edge = iter.next();
                if (hitbox.intersects(edge)) {
                    bullet.setCurrentFrame(1);
                    bullet.setAutoUpdate(true);
                }
        }
    }
    
    public void intersect(Player player) throws SlickException {
        if (hitbox.intersects(player.hitbox) || hitbox.intersects(Play.EDGE.iterator().next())) {
            bullet.setAutoUpdate(true);
            bullet.getCurrentFrame().setRotation(ang);
        }
        else if (bullet.getFrame() == 0) {
            this.update();
        }
    }        
}
