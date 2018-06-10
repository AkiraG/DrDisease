/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author verratti.gfv
 */
public class BossCore extends BossConcept {
    
    SpriteSheet sIntro01,sIntro02,sIdle,sAtkSet,sAtkChrg,sAtkShoot,sAtkCd,sAtkReset;
    Animation aBase,aIntro01,aIntro02,aIdle,aAtkSet,aAtkChrg,aAtkShoot,aAtkCd,aAtkReset;
    
    boolean isAtk;
    
    int time=0,cd=1000;
    
    

    public BossCore(Point location) {
        super(location);
        
        isAtk=false;
        
        try {
            sIntro01 = new SpriteSheet("data/image/Fase01/core-1-1-intro-pt1.png", 128, 128);
            sIntro02 = new SpriteSheet("data/image/Fase01/core-1-1-intro-pt2.png", 128, 128);
            sIdle = new SpriteSheet("data/image/Fase01/core-1-1-idle.png", 128, 128);
            sAtkSet = new SpriteSheet("data/image/Fase01/core-1-1-atk-set.png", 128, 128);
            sAtkChrg = new SpriteSheet("data/image/Fase01/core-1-1-atk-charge.png", 128, 128);
            sAtkShoot = new SpriteSheet("data/image/Fase01/core-1-1-atk-shoot.png", 128, 128);
            sAtkCd = new SpriteSheet("data/image/Fase01/core-1-1-atk-cd.png", 128, 128);
            sAtkReset = new SpriteSheet("data/image/Fase01/core-1-1-atk-reset.png", 128, 128);
            
            aIntro01= new Animation(sIntro01,60);
            aIntro01.setAutoUpdate(false);
            aIntro01.stopAt(aIntro01.getFrameCount()-3);
            
            aIntro02= new Animation(sIntro02,60);
            aIntro02.setAutoUpdate(false);
            aIntro02.stopAt(aIntro02.getFrameCount()-1);
            
            aIdle = new Animation(sIdle,60);
            aIdle.stopAt(aIdle.getFrameCount()-3);
            
            aAtkSet = new Animation(sAtkSet,60);
            aAtkSet.setAutoUpdate(false);
            aAtkSet.stopAt(aAtkSet.getFrameCount()-4);
            
            aAtkChrg = new Animation(sAtkChrg,60);
            aAtkChrg.setAutoUpdate(false);
            aAtkChrg.stopAt(aAtkChrg.getFrameCount()-1);
            
            aAtkShoot = new Animation(sAtkShoot,60);
            aAtkShoot.setAutoUpdate(false);
            aAtkShoot.stopAt(aAtkShoot.getFrameCount()-1);
            
            aAtkCd = new Animation(sAtkCd,60);
            aAtkCd.setAutoUpdate(false);
            aAtkCd.stopAt(aAtkCd.getFrameCount()-1);
            
            aAtkReset = new Animation(sAtkReset,60);
            aAtkReset.setAutoUpdate(false);
            aAtkReset.stopAt(aAtkReset.getFrameCount()-2);
            
            aBase=aIdle;
            
            
            
        } catch (SlickException ex) {
            Logger.getLogger(BossCore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        super.setHitbox(new Circle(location.getX()+aBase.getWidth()/2,
                location.getY()+aBase.getHeight()/2,aBase.getWidth()/2));
        
    }

    @Override
    public void draw(Graphics g) {
        aBase.draw(location.getX(), location.getY());
//        g.draw(hitbox);
    }
    
     @Override
    public void draw(Graphics g, Color c) {
        aBase.draw(location.getX(), location.getY(),c);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        
          if(isAtk){
              if(aAtkSet.isStopped()){
                  aBase.restart();
                  aBase.setAutoUpdate(false);
                  aBase=aAtkChrg;
                  aBase.setAutoUpdate(true);
              }else if(aAtkChrg.isStopped()){
                  aBase.restart();
                  aBase.setAutoUpdate(false);
                  aBase=aAtkShoot;
                  aBase.setAutoUpdate(true);
              }else if(aAtkShoot.isStopped()){
                  time+=delta;
                    if(time>=cd){
                        aBase.restart();
                        aBase.setAutoUpdate(false);
                        aBase=aAtkCd;
                        aBase.setAutoUpdate(true);
                    }
              }else if(aAtkCd.isStopped()){
                    aBase.restart();
                    aBase.setAutoUpdate(false);
                    aBase=aAtkReset;
                    aBase.setAutoUpdate(true);
              }else if(aAtkReset.isStopped()){
                  aBase.restart();
                  aBase.setAutoUpdate(false);
                  aBase=aIdle;
                  aBase.setAutoUpdate(true);
                  isAtk=false;
              }
              
          }else if(aBase.isStopped())aBase.restart();
    
    }
    
    public Shape getHitbox(){
        return hitbox;
    }
    
    public boolean coreAtk(){
        if(!isAtk){
            aBase=aAtkSet;
            aBase.setAutoUpdate(true);
            isAtk=true;
            return true;
        }
        return false;
    }
    
}
