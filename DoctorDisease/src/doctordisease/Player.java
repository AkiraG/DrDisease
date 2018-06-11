package doctordisease;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.*;

public class Player {
    
    String status;
    
    Sound bulletSound;
    
    int speed,cd,timer,timerFlash,cdHit;
    
    float hp;
    
    boolean isOnCd,isAtk,takeHit,flashDraw;
    
    Point location;
    Vector2f direction;
    Rectangle hitbox;
    
    ArrayList<Projectile> shootList;
    ArrayList<Line> moveLimit;
    
    SpriteSheet sBase,sPropulsion,sBullet;
    Animation aBase,aPropulsion,aPropulsionUp,aPropulsionIdle,aPropulsionDown;
    
    public Player(Point location, int speed) {
        
        status="Intro";
        
        hp = 100f;
        
        this.takeHit=false;
        this.cdHit = 100;
        this.cd = 300;
        this.isOnCd =false;
        this.isAtk=false;
        this.timer=0;
        this.shootList=new ArrayList<>();
        
        this.location = location;
        this.speed = speed;
        try {
            
            sBase = new SpriteSheet("/data/image/Fase01/Guts-shoot-Sheet.png",44,62);
            sPropulsion = new SpriteSheet("/data/image/Fase01/Guts-propulsion-Sheet.png",8,16);
            sBullet = new SpriteSheet("/data/image/FAse01/bulletSheet.png",20,20);
            bulletSound = new Sound("data/sound/Fase01/Guts_shot.ogg");
            
            aBase = new Animation(sBase,100);
            aBase.setAutoUpdate(false);
            aPropulsionUp = new Animation(sPropulsion,7,0,8,0,true,60,true);
            aPropulsionIdle = new Animation(sPropulsion,0,0,4,0,true,60,true);
            aPropulsionDown = new Animation(sPropulsion,5,0,6,0,true,60,true);
            aPropulsion = aPropulsionIdle;
        
        } catch (SlickException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        hitbox = new Rectangle(location.getX(),location.getY(),aBase.getWidth(),aBase.getHeight());
        direction=new Vector2f(0,0);
    }
    
    public void draw(Graphics g){
        
        shootList.forEach(shoot -> {
            shoot.draw(g);
        });
        
//        g.draw(hitbox);
        if(flashDraw)aBase.drawFlash(location.getX(), location.getY(), aBase.getWidth(), aBase.getHeight());
        else aBase.draw(location.getX(), location.getY());
        
        aPropulsion.draw(location.getX()+ 18, location.getY()+40);
        
  
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        if(status.equals("Intro")){
            
        }
        else if(status.equals("Game")){
            
            hitbox.setLocation(location.getX(), location.getY());

            if(takeHit){
                timer+=delta;
                if(timer>=500){
                    timer=0;
                    takeHit=false;
                }
            }
            if(flashDraw){
                if(timer>=300)flashDraw=false;
            }




            Input input = gc.getInput();


            if(input.isKeyDown(Input.KEY_LEFT) && !this.checkCollision(moveLimit.get(1)))
                direction.set(direction.getX()-1,direction.getY());

            if(input.isKeyDown(Input.KEY_RIGHT) && !this.checkCollision(moveLimit.get(3)))
                direction.set(direction.getX()+1,direction.getY());

            if(input.isKeyDown(Input.KEY_DOWN) && !this.checkCollision(moveLimit.get(2))){
                direction.set(direction.getX(),direction.getY()+1);
                aPropulsion=aPropulsionDown;
            }
            if(input.isKeyDown(Input.KEY_UP) && !this.checkCollision(moveLimit.get(0))){
                direction.set(direction.getX(),direction.getY()-1);
                aPropulsion=aPropulsionUp;
            }

            if(input.isKeyDown(Input.KEY_SPACE))aBase.setAutoUpdate(true);

            else {
                aBase.setAutoUpdate(false);
                aBase.setCurrentFrame(0);
            }


            this.move(delta);
            this.handleAtk();

            shootList.forEach(bullet -> bullet.update(delta));

            shootList.removeIf(bullet -> bullet.checkAnimation());
        }   
        
    }
    
    public void handleAtk(){
            if(aBase.getFrame()==2 && !isAtk){
                shootList.add(new Projectile(new Point(location.getX()+37,location.getY()+12),new Vector2f(0,-600),sBullet));
                //if(!bulletSound.playing())bulletSound.play();
                isAtk=!isAtk;
            }else if(aBase.getFrame()==3 && isAtk){
                shootList.add(new Projectile(new Point(location.getX()+5,location.getY()+12),new Vector2f(0,-600),sBullet));
                //if(!bulletSound.playing())bulletSound.play();
                isAtk=!isAtk;    
            }else if(aBase.getFrame()==5){
                aBase.setCurrentFrame(2);   
            }  
    }
    
    public void move(int delta){
        
        location.setX(location.getX()+(float)(direction.getX()*(speed * delta/1000.0)));
        location.setY(location.getY()+(float)(direction.getY()*(speed * delta/1000.0)));
        direction.set(0,0);
        
    }
    
    public boolean checkCollision(Shape c){
        if(status.equals("Game")){
            return hitbox.intersects(c);
        }
        return false;
    }
    
    public void checkCollision(ArrayList<Projectile> shootList){
        if(status.equals("Game")){
        shootList.forEach(shoot -> {
                if(!takeHit){
                    if(shoot.checkCollision(hitbox)){
                        takeHit=true;flashDraw=true;
                        hp-=5;
                    }
                }
            });
        }
    }
    
    public void checkLaserCollision(ArrayList<LaserShot> laserList){
        if(status.equals("Game")){
                laserList.forEach(laser -> {
                if(!takeHit){
                    if(laser.checkCollision(hitbox)){
                        takeHit=true;
                        flashDraw=true;
                        hp-=20;
                    }
                }
            });
        }
        
    }
    
    public void checkBulletCollision(Shape c){
        
        if(status.equals("Game"))shootList.forEach(bullet -> bullet.checkCollision(c));
        
    }
    
    public Shape getHitbox(){
        return hitbox;
    }
    
    public void setLimits(ArrayList<Line> c){
        this.moveLimit=c;
    }
    
    public ArrayList<Projectile> getShootList(){
        return shootList;
    }
    
}