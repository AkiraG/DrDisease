/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.List;
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
        //g.setColor(Color.transparent);
        g.draw(hitbox);
    }

    public void update() throws SlickException {
        y -= 15;
        hitbox.setY(y);
    }
    
    public void intersect(List<HitBoxBoss> hitboxBoss) throws SlickException {
        if (this.hitbox.intersects(hitboxBoss.get(0).hitbox) || 
            this.hitbox.intersects(hitboxBoss.get(1).hitbox) ||
            this.hitbox.intersects(hitboxBoss.get(2).hitbox) ||
            this.hitbox.intersects(hitboxBoss.get(3).hitbox) ||
            y < 10){
            this.bullet.setAutoUpdate(true);  
        }
        else{
            this.update();
        }
    }    
}