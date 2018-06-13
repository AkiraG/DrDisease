/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author saita
 */
public class BossCore extends BossConcept {
    
    ArrayList<LaserShot> laserList;
    
    String status;
    Sound laser;
    int atkType;
    
    SpriteSheet sIntro01,sIntro02,sIdle,sAtkSet,sAtkChrg,sAtkShoot,sAtkCd,sAtkReset;
    Animation aBase,aIntro01,aIntro02,aIdle,aAtkSet,aAtkChrg,aAtkShoot,aAtkCd,aAtkReset;
    

    
    boolean isAtk,takeHit,pause;
    
    int time,cd=1000,timeHit;
    
    

    public BossCore(Point location) {
        super(location);
        
        status = "Intro";
        
        laserList= new ArrayList<>();
        
        isAtk=false;
        
        try {
            laser= new Sound("data/sound/Fase01/Laser_Full.ogg");
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
            
            aBase=aIntro01;
            
            
            
        } catch (SlickException ex) {
            Logger.getLogger(BossCore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        super.setHitbox(new Circle(location.getX()+aBase.getWidth()/2,
                location.getY()+aBase.getHeight()/2,aBase.getWidth()/2));
        
    }

    @Override
    public void draw(Graphics g) throws ConcurrentModificationException {
        laserList.forEach(laser -> laser.draw(g));
        if(takeHit)aBase.draw(location.getX(), location.getY(),Color.red);
        else aBase.draw(location.getX(), location.getY());
        
//        g.draw(hitbox);
    }
    
    @Override
    public void draw(Graphics g, Color c) throws ConcurrentModificationException {
        aBase.draw(location.getX(), location.getY(),c);
    }

    @Override
    public void update(int delta) {
        laserList.forEach(laser -> laser.update(delta));
        laserList.removeIf(laser -> laser.checkAnimation());
        
        if(status.equals("Intro")){
            if(aIntro01.isStopped()){
                aBase=aIntro02;
                }
            if(aIntro02.isStopped()){
                status="Game";
                aBase=aIdle;
            }
        }else if(status.equals("Game")){
          if(takeHit){
              timeHit+=delta;
              if(timeHit>=100){

                  timeHit=0;
                  takeHit=false;
              }
          }
          if(isAtk){
              if(aAtkSet.isStopped()){
                  aBase.restart();
                  aBase.setAutoUpdate(false);
                  aBase=aAtkChrg;
                  aBase.setAutoUpdate(true);
              }else if(aAtkChrg.isStopped()){
                  if(!laser.playing())laser.play(0.9f,0.5f);
                  //playlaser
                  time+=1;
                  if(time>=10){
                        time=0;
                        aBase.restart();
                        aBase.setAutoUpdate(false);
                        aBase=aAtkShoot;
                        aBase.setAutoUpdate(true);
                        
                        switch(atkType){
                            case 0:
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                          location.getY()+(aBase.getHeight())-30),new Vector2f(0,1)));
                            break;
                            case 1:
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                      location.getY()+(aBase.getHeight())-30),new Vector2f(0,1)));
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                      location.getY()+(aBase.getHeight())-30),new Vector2f(0.5f,1)));
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                      location.getY()+(aBase.getHeight())-30),new Vector2f(-0.5f,1)));
                            break;
                            case 2:
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                      location.getY()+(aBase.getHeight())-30),new Vector2f(0.25f,1)));
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                      location.getY()+(aBase.getHeight())-30),new Vector2f(-0.25f,1)));
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                      location.getY()+(aBase.getHeight())-30),new Vector2f(0.75f,1)));
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                      location.getY()+(aBase.getHeight())-30),new Vector2f(-0.75f,1)));
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                      location.getY()+(aBase.getHeight())-30),new Vector2f(1.25f,1)));
                                  laserList.add(new LaserShot(new Point(location.getX()+(aBase.getWidth()/2),
                                      location.getY()+(aBase.getHeight())-30),new Vector2f(-1.25f,1)));
                            break;     
                        }
                    }else aAtkChrg.restart();
                  }else if(aAtkShoot.isStopped()){
                    time+=delta;
                      if(time>=cd){
                            time=0;
                            aBase.restart();
                            aBase.setAutoUpdate(false);
                            aBase=aAtkCd;
                            aBase.setAutoUpdate(true);
                      }
              }else if(aAtkCd.isStopped()){
                    time+=1;
                    if(time>=10){
                        time=0;
                        aBase.restart();
                        aBase.setAutoUpdate(false);
                        aBase=aAtkReset;
                        aBase.setAutoUpdate(true);
                    }else aAtkCd.restart();
              }else if(aAtkReset.isStopped()){
                  aBase.restart();
                  aBase.setAutoUpdate(false);
                  aBase=aIdle;
                  aBase.setAutoUpdate(true);
                  isAtk=false;
              }
              
          }else if(aBase.equals(aIdle) && aBase.isStopped())aBase.restart();
        }
    }
    
    public Shape getHitbox(){
        return hitbox;
    }
    
    public void coreAtk(int a){
        if(!isAtk){
            atkType=a;
            aBase=aAtkSet;
            aBase.setAutoUpdate(true);
            isAtk=true;
        }
    }
    
    public boolean isAtk(){
        return isAtk;
    }
    
    public boolean isVulnerable(){
        return(aBase.equals(aAtkCd));
    }
    
    public ArrayList<LaserShot> getLaserList(){
        return laserList;
    }
    
    public void runAnimation(){
        aBase.setAutoUpdate(true);
    }
    
    public Animation checkIntroAnimation(){
        return aIntro01;
    }
    
    public int takeHit(){
        if(!takeHit){
            takeHit=true;
            if(this.isVulnerable())return 10;
            else return 5;
        }else return 0;
    }
    @Override
    public void pause(){
        pause=!pause;
        aBase.setAutoUpdate(!pause);

    }
    
}
