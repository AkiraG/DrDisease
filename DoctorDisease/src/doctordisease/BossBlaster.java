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
 * @author saita
 */
public class BossBlaster extends BossConcept {
    
    String typeAtk;
    boolean isAtk,isShoot,takeHit;
    float targetAngle;
    int speed,cdAtk,time,timeHit,stepAngle;
    
    SpriteSheet bullet;
    SpriteSheet sIntro,sShoot;
    Animation aIntro,aShoot,aBase;
    
    ArrayList<Projectile> shootList;
    

    public BossBlaster(Point location, Vector2f direction,int speed) {
        
        super(location, direction);
        
        this.speed=speed;
        
        typeAtk="N";
        time=0;
        shootList = new ArrayList<>();
        isAtk=false;
        isShoot=false;
        targetAngle=0;
        cdAtk=100;
        
        try {
            bullet=new SpriteSheet("data/image/Fase01/bulletboss.png",40,40);
            
            sIntro=new SpriteSheet("data/image/Fase01/blaster-1-1-intro.png", 52, 52);
            sShoot=new SpriteSheet("data/image/Fase01/blaster-1-1-shoot.png", 52, 52);
            
            aIntro=new Animation(sIntro,60);
            aIntro.stopAt(aIntro.getFrameCount()-1);
            aIntro.setAutoUpdate(false);
            
            aShoot=new Animation(sShoot,30);
            aShoot.setAutoUpdate(false);
            aShoot.stopAt(aShoot.getFrameCount()-1);
            
            aBase=aIntro;
            
            for(int a=0;a<aIntro.getFrameCount();a++)
                aIntro.getImage(a).setCenterOfRotation(0,aIntro.getImage(a).getHeight()/2);
            
            for(int a=0;a<aShoot.getFrameCount();a++)
                aShoot.getImage(a).setCenterOfRotation(0,aShoot.getImage(a).getHeight()/2);
            
            super.setHitbox(new Circle(location.getX(),location.getY()+(aBase.getCurrentFrame().getHeight()/2),aBase.getWidth()/2));
            
            for(int a=0;a<aBase.getFrameCount();a++)
                aBase.getImage(a).setRotation((float)direction.getTheta());
            
            
            
        } catch (SlickException ex) {
            Logger.getLogger(BossBlaster.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

    @Override
    public void draw(Graphics g) {
        
        shootList.forEach(bullet -> bullet.draw(g));
        if(takeHit)aBase.draw(location.getX(),location.getY(),Color.red);
        else aBase.draw(location.getX(),location.getY());
        g.draw(hitbox);
    }
    
    @Override
    public void draw(Graphics g, Color c) {
        shootList.forEach(bullet -> bullet.draw(g));
        aBase.draw(location.getX(),location.getY(),c);
        
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        if(aIntro.isStopped())aBase=aShoot;
        
        if(takeHit){
            timeHit+=delta;
            if(timeHit>=100){
                timeHit=0;
                takeHit=false;
            }
        }
        
        time+=delta;
        shootList.removeIf(shoot -> shoot.checkAnimation());
        
        shootList.forEach(shoot -> shoot.update(delta));
        
        for(int a=0;a<aBase.getFrameCount();a++)
                aBase.getImage(a).setRotation((float)direction.getTheta());
 
        if(aBase.isStopped()){
            aBase.setAutoUpdate(false);
            aBase.restart();
        }
        
        switch (typeAtk) {
            case "Flower":
                if(time>=cdAtk && isAtk ){
                    time=0;
                    if((direction.getTheta()<=targetAngle)&&(aBase.getCurrentFrame().equals(aBase.getImage(0)))){
                        direction.setTheta(direction.getTheta()+stepAngle);
                        this.directAttack((float)direction.getTheta());
                        isShoot=false;
                    }else if(direction.getTheta()>=targetAngle){
                        isAtk=false;
                        this.cancelAttack();   
                    }
                }   break;
            case "RFlower":
                if(time>=cdAtk && isAtk ){
                    time=0;
                    if((direction.getTheta()>=targetAngle)&&(aBase.getCurrentFrame().equals(aBase.getImage(0)))){
                        direction.setTheta(direction.getTheta()+stepAngle);
                        this.directAttack((float)direction.getTheta());
                        isShoot=false;
                    }else if(direction.getTheta()<=targetAngle){
                        isAtk=false;
                        this.cancelAttack();   
                    }
                }   break;
            case "Target":
                if(time>=cdAtk && isAtk){
                    time=0;
                    this.directAttack();
                    isShoot=false;
                    isAtk=false;
                }   break;
            case "Direct":
                if(time>=cdAtk && isAtk){
                    time=0;
                    this.directAttack();
                    isShoot=false;
                    isAtk=false;
                } break;
            default:
                break;
        }
        

        if(!isShoot && aShoot.getFrame()==4){
            shootList.add(new Projectile(new Point(location.getX(),location.getY()+aBase.getCurrentFrame().getHeight()/2),
                    new Vector2f(direction.getX(),direction.getY()).scale(speed),bullet)); 
            isShoot=true;
        }
        
    }
    
    public void setAngle(float angle){
        direction.setTheta((double) angle); 
    }
    
    public void directAttack(){
        
        isAtk=true;
        aBase.setAutoUpdate(true);
    }
    
    public void directAttack(float angle){
        
        this.setAngle(angle);
        this.directAttack();
    }
    
    public void checkBulletCollision(Shape c){
        shootList.forEach(shoot -> shoot.checkCollision(c));  
    }
    
    public Shape getHitbox(){
        return hitbox;
    }
    
    public void setLocation(Point p){
        location.setX(p.getX());
        location.setY(p.getY());
        hitbox.setLocation(location.getX(), location.getY()); 
    }
    
    public void setLocation(int x, int y){
        location.setX(x);
        location.setY(y);
        hitbox.setLocation(location.getX(), location.getY()); 
        
    }
    
    public void cancelAttack(){
        isShoot=false;
        isAtk=false;
        aBase.setAutoUpdate(false);
        aBase.restart();
    }
    
    public void sequenceAttack(float angleStart , float angleEnd , int cd, int step){
        
        if(!isAtk){
            isAtk=true;
            this.setAngle(angleStart);
            targetAngle = angleEnd;
            cdAtk=cd;
            if(angleStart<angleEnd){
                stepAngle=step;    
                typeAtk="Flower";
            }else{
                stepAngle=step*-1;
                typeAtk="RFlower"; 
            }
         
            
            
        }
    }
    
    public void targetAttack(Shape t){
        if(!isAtk){
            isAtk=true;
            Vector2f target = new Vector2f((t.getCenterX()-location.getX()),(t.getCenterY()-location.getY()));
            target.normalise();
            this.setDirection(target.copy());
            typeAtk="Target";
        }
    }
    
    public void continuousAttack(float angle,int cd, int speed){
        if(!isAtk){
            isAtk=true;
            this.setAngle(angle);
            this.setCdAtk(cd);
            this.speed=speed;
            typeAtk="Direct";
            
        }
    }
    
    public void setCdAtk(int cd){
        this.cdAtk=cd;
    }
    
    public ArrayList<Projectile> getShootList(){
        return this.shootList;
    }
    
    public boolean isAtk(){
        return isAtk;
    }
    
    public void runIntro(){
        aIntro.setAutoUpdate(true);
    }
    
    public Animation checkAnimation(){
        return aIntro;
    }
    
    public void takeHit(){
        if(!takeHit)takeHit=true;
    }

   
    
    
    
        
}
