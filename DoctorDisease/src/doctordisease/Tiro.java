/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class Tiro {
    
    int x, y;
    SpriteSheet bulletSheet;
    Animation bullet;
    Rectangle hitbox;
    
    public Tiro(int x, int y) throws SlickException {
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(x + 3, y, 4,9);
        bulletSheet = new SpriteSheet("data/image/Fase01/bulletsSheet.png", 10, 10);
        bullet = new Animation(bulletSheet, 100);
        bullet.setAutoUpdate(false);
        bullet.stopAt(2);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawAnimation(bullet, x, y);
        g.draw(hitbox);
        g.setColor(Color.transparent);
    }

    public void update() { //Boss.blasters
        if (bullet.getFrame()==0) {
            if (y >= 15) y -= 15;
            else y -= y;
            hitbox.setY(y);
            this.checkIntersect(Boss.blasters);
        }
    }
    
    public void checkIntersect(List<HitBoxBoss> hitBoxBoss) {
        for(Iterator<HitBoxBoss> iter = hitBoxBoss.iterator(); 
            iter.hasNext();) {
                HitBoxBoss hitboxAtual = iter.next();
                if (hitbox.intersects(hitboxAtual.hitbox) || hitbox.intersects(Play.EDGE.get(0))) {
                    bullet.setCurrentFrame(1);
                    bullet.setAutoUpdate(true);
                }
        }        
    }
}