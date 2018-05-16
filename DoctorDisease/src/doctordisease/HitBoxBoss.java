/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Gabriel
 */
public class HitBoxBoss {
    
    Rectangle hitbox;
    Animation blaster;
    SpriteSheet blasterSheet;
    boolean onAttack;
    int x, x2, y;
    float targetX, targetY, ang;

    public HitBoxBoss(int x, int y) throws SlickException {
        this.x = x;
        this.x2 = x - 16;
        this.y = y;
        hitbox = new Rectangle(this.x, this.y, 20, 37);
        blasterSheet = new SpriteSheet("data/image/Fase01/blaster1-1.png", 52, 60);
        blaster = new Animation(blasterSheet, 100);
    }
    
    public void calcAng() {
        targetY = Player.y - y;
        if (Player.x > x) targetX = Player.x - x;
        else targetX = x - Player.x;
        ang = (float) Math.toDegrees(Math.atan(Math.sin(targetX / targetY)));    
    }
    
    public void update() throws SlickException {
        for (int y = 0; y < blaster.getFrameCount(); y++){ // atualiza todos sempre \/
            this.calcAng();
            blaster.getImage(y).setCenterOfRotation(27, 8);
            if (Player.x > x) {
                blaster.getImage(y).setRotation(ang * -1);
            }
            else blaster.getImage(y).setRotation(ang);
        } // atualiza todos sempre /\
        /*    
        if (onAttack){ // atualiza sempre que for atirar \/
            for (int y = 0; y < blaster.getFrameCount(); y++){
                this.calcAng();
                blaster.getImage(y).setCenterOfRotation(27, 8);
                if (Player.x > x) {
                    blaster.getImage(y).setRotation(ang * -1);
                }
                else blaster.getImage(y).setRotation(ang);
            }
        } // atualiza sempre que for atirar /\
        */
        if (blaster.isStopped()){
            blaster.restart();
            blaster.setAutoUpdate(false);
        }
    }
    
    public void attack() throws SlickException {
        onAttack = true;
        blaster.restart();
        this.calcAng();
        blaster.setAutoUpdate(true);
        blaster.stopAt(6);
    }
    
    public Rectangle getHitBox() {
        return hitbox;
    }
}
