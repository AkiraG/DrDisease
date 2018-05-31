/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Gabriel
 */
public class BossBlaster extends BossConcept {
    
    Rectangle hitbox;
    
    Animation blasterIntro;
    Animation blasterAttack;
    List<Animation> blaster = new ArrayList<>();

    boolean onAttack = false;
   
    int x, y;

    public BossBlaster(int x, int y) throws SlickException {
        this.x = x;
        this.y = y;
        
        hitbox = new Rectangle(this.x, this.y, 20, 37);
        
        blasterIntro = new Animation(new SpriteSheet("data/image/Fase01/blaster-1-1-intro.png", 52, 60), 80);
        blasterIntro.stopAt(11);
        
        blasterAttack = new Animation(new SpriteSheet("data/image/Fase01/blaster-1-1-shoot.png", 52, 60), 120);
        blasterAttack.setAutoUpdate(false);
        blasterAttack.stopAt(6);
        
        blaster.add(blasterIntro);
    }
    
    @Override
    public void render(Graphics g) throws SlickException {
        blaster.forEach((b) -> {
            g.drawAnimation(b, x, y);
        });
    }
    
    @Override
    public void update(int delta) throws SlickException {
        if (blasterIntro.isStopped()) {
            blaster.remove(0);
            blaster.add(blasterAttack);
        }
        if (blasterAttack.getFrame() >= 5 && onAttack){
            onAttack = false;
            Boss.bullets.add(new BossBullet(new Vector2f(x,y), new Vector2f( (Play.guts.vec.getX() - x) , (Play.guts.vec.getY() - y) ) ) );
        }
        if (blasterAttack.isStopped()) {
            blasterAttack.restart();
            blasterAttack.setAutoUpdate(false);
        }
        
    }
    
    public void directAttack() throws SlickException {
        if (onAttack == false) {
            onAttack = true;
            blasterAttack.setAutoUpdate(true);
        }
    }
    
    public Rectangle getHitBox() {
        return hitbox;
    }
}
