/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
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
    
    public void attack() throws SlickException {
        targetY = Player.y - y;
        blaster.restart();
        if (Player.x > x) targetX = Player.x - x;
        else targetX = x - Player.x;
        ang = targetX / targetY;
        for (int y = 0; y < blaster.getFrameCount(); y++){
            blaster.getImage(y).setCenterOfRotation(27, 8);
            if (Player.x > x)   {
                blaster.getImage(y).setRotation((float) Math.toDegrees(Math.atan(Math.sin(ang))) * -1);
            }
            else blaster.getImage(y).setRotation((float) Math.toDegrees(Math.atan(Math.sin(ang))));
        }
        blaster.setAutoUpdate(true);
        blaster.stopAt(6);
    }
}
