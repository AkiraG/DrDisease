/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Gabriel
 */
public class BossBullet {
    
    SpriteSheet bulletSheet ;
    Animation bullet;
    
    Circle hitbox;  
    
    Vector2f pos, speed;

    float ang;
    
    public BossBullet(Vector2f pos, Vector2f speed) throws SlickException {
        this.pos = pos;
        this.speed = speed;
        
        bulletSheet = new SpriteSheet("data/image/Fase01/bulletBoss.png", 40,40);
        bullet = new Animation(bulletSheet, 100);
        bullet.setAutoUpdate(false);
        bullet.getCurrentFrame().setCenterOfRotation(16, 18);
       
        if (speed.getX() > pos.getX()) ang = (float) pos.getTheta() * -1;
        else ang = (float) pos.getTheta();
            
        bullet.getCurrentFrame().setRotation(ang);
        bullet.stopAt(2);
        
        //bullet.getCurrentFrame().rotate(this.ang);
        hitbox = new Circle(pos.getX(), pos.getY(), 6);
    }

    public void render(Graphics g) throws SlickException {
        g.drawAnimation(bullet, pos.x, pos.y);
        g.draw(hitbox);
    }

    public void update(int delta, Player p) throws SlickException {
        System.out.println(pos.getTheta());
        System.out.println(GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE));
        if (bullet.getFrame() == 0){
            Vector2f realSpeed = speed.copy();
            realSpeed.scale( (delta / 500.0f) );
            pos.add(realSpeed);
            hitbox.setLocation(pos.getX(), pos.getY());
        }
        
        if (hitbox.intersects(p.hitbox)) {
            bullet.setCurrentFrame(1);
            bullet.setAutoUpdate(true);
        }
        
        for (Line edge : Play.EDGE) {
            if (hitbox.intersects(edge)) {
                bullet.setCurrentFrame(1);
                bullet.setAutoUpdate(true);
            }
        }
    }        
}
