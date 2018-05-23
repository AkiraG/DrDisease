/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Gabriel
 */
public class HitBoxBoss extends BossConcept {
    
    Rectangle hitbox;
    Rectangle WTF;
    
    Animation blasterIntro;
    Animation blaster;
    

    boolean onAttack = false; 
    boolean onIntro = true;
    
    int x, x2, y;
    float targetX, targetY, ang;

    public HitBoxBoss(int x, int y) throws SlickException {
        this.x = x;
        this.x2 = x - 16;
        this.y = y;
        
        hitbox = new Rectangle(this.x, this.y, 20, 37);
        WTF = new Rectangle(this.x, this.y, 50, 50);
        
        blasterIntro = new Animation(new SpriteSheet("data/image/Fase01/blaster-1-1-intro.png", 52, 60), 75);
        blasterIntro.stopAt(11);
        
        blaster = new Animation(new SpriteSheet("data/image/Fase01/blaster-1-1-shoot.png", 52, 60), 100);
        blaster.setAutoUpdate(false);
    }
    
    public void render(Graphics g) throws SlickException {
        if (onIntro == true) {
            g.drawAnimation(blasterIntro, x - 16, y);
        }
        else {
            g.draw(hitbox);
            g.drawAnimation(blaster, x - 16, y);
        }
    }
    
    @Override
    public void update() throws SlickException {
        if (blasterIntro.isStopped()) onIntro = false;
        
        for (int y = 0; y < blaster.getFrameCount(); y++){
            this.calcAng();
            blaster.getImage(y).setCenterOfRotation(27, 8);
            if (Player.x > x) {
                blaster.getImage(y).setRotation(ang * -1);
            }
            else blaster.getImage(y).setRotation(ang);
        }
        if (blaster.isStopped()){
            blaster.restart();
            blaster.setAutoUpdate(false);
        }
    }
    
    public void attack() throws SlickException {
        if (onAttack == false) {
            onAttack = true;
            blaster.restart();
            this.calcAng();
            blaster.setAutoUpdate(true);
            blaster.stopAt(6);
        }
    }
        
    public void calcAng() {
        targetY = Player.y - y;
        if (Player.x > x) targetX = Player.x - x;
        else targetX = x - Player.x;
        ang = (float) Math.toDegrees(Math.atan(Math.sin(targetX / targetY)));    
    }
    
    public Rectangle getHitBox() {
        return hitbox;
    }
}
