/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 * @author Gabriel
 */
public class Boss {
    
    
    String status;
    
    Color renderColor;
    
    Shape targetHitbox;
    
    float hp,coreDamage,bodyDamage,blasterDamage;
    boolean isAtk,isCoreAtk;
    String typeAtk;
    float targetAngle;
    
    int time,cdAtk,repeatAtk,stage;
    
    ArrayList<BossConcept> partList;
    ArrayList<BossBlaster> blasterList;
    ArrayList<Projectile> shootList;
    
    Point location;
    Vector2f direction;
    
    BossCore core;
    BossBody body;
    
    BossBlaster blaster01;
    BossBlaster blaster02;
    BossBlaster blaster03;
    BossBlaster blaster04;

    public Boss(Point location) {
        
        renderColor = new Color(255,0,0,255);
        
        status="Intro";
        stage=0;
        hp=2000;
        coreDamage=5f;
        bodyDamage=0.1f;
        blasterDamage=1f;
        
        time=0;
        cdAtk=100;
        isAtk=false;
        isCoreAtk=false;
        
        direction=new Vector2f(0,1);
        
        this.location = location;
        
        blasterList = new ArrayList<>();
        partList = new ArrayList<>();
        shootList = new ArrayList<>();
        
        blaster01 = new BossBlaster(new Point(location.getX()+18,location.getY()+152),direction.copy(),200);
        blasterList.add(blaster01);
        blaster02 = new BossBlaster(new Point(location.getX()+210,location.getY()+162),direction.copy(),200);
        blasterList.add(blaster02);
        blaster03 = new BossBlaster(new Point(location.getX()+406,location.getY()+162),direction.copy(),200);
        blasterList.add(blaster03);
        blaster04 = new BossBlaster(new Point(location.getX()+598,location.getY()+152),direction.copy(),200);
        blasterList.add(blaster04);
        
        body = new BossBody(new Point(location.getX(),location.getY()));

        
        core = new BossCore(new Point(location.getX()+244,location.getY()+64));
        partList.add(core);
         
    }
    
    public void draw(Graphics g){
        partList.forEach(item -> item.draw(g));
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        
        time+=delta;
        partList.forEach(item -> item.update(gc, sbg, delta));

        if(status.equals("Intro")){
            if(core.aIntro01.isStopped()){
                if(!partList.contains(body)){
                    partList.add(0,body);
                    body.runIntro();
                }else if(body.checkAnimation().isStopped()){
                    core.runAnimation();
                    partList.add(0, blaster01);
                    partList.add(0, blaster02);
                    partList.add(0, blaster03);
                    partList.add(0, blaster04);
                    blasterList.forEach(blaster -> blaster.runIntro());
                    status="Game";
                }
            }
            
        }else if(status.equals("Game")){
        if(hp>1500){
            if(stage==0){
                isCoreAtk=false;
                stage++;
            }
            else if(!core.isAtk()){
                this.attackSequenceL(0, 15, 600,200);
                this.attackSequenceR(3, 15, 600,200);
                }
        }else if(hp>1000){
            if(stage==1){
                isCoreAtk=false;
                stage++;
            }
            else if(!isCoreAtk){
                this.stopBlasters();
                this.coreAttack(0);//random core atk
                
            }else if(core.isAtk()){
                this.directAttack(0, 45, 60, 400);
                this.directAttack(1, 90, 60, 400);
                this.directAttack(2, 90, 60, 400);
                this.directAttack(3, 135, 60, 400);

  
            }else if(!core.isAtk()){
                this.attackSequenceL(0, 15, 400,400);
                this.attackSequenceR(3, 15, 400,400);
                this.attackTarget(1, targetHitbox, 400, 1000);
                this.attackTarget(2, targetHitbox, 400, 1000);
            }      
        }else if(hp>500){
            if(stage==2){
                isCoreAtk=false;
                stage++;
            }else if(!isCoreAtk){
                this.stopBlasters();
                this.coreAttack(1);
            }else if(core.isAtk()){
                this.directAttack(0, 45, 60, 700);
                this.directAttack(1, 100, 60, 400);
                this.directAttack(2, 80, 60, 400);
                this.directAttack(3, 135, 60, 700);    
             
            }else if(!core.isAtk()){
                this.attackSequenceR(0, 10, 400,300);
                this.attackSequenceL(3, 10, 400,300);
                this.attackTarget(1, targetHitbox, 500, 1000);
                this.attackTarget(2, targetHitbox, 500, 1000);
            }
        }else if(hp>0){
            if(stage==3){
                isCoreAtk=false;
                stage++;
            }else if(!isCoreAtk){
                this.stopBlasters();
                this.coreAttack(2);
            }else if(core.isAtk()){
                
                this.directAttack(0, 35, 60, 1000);
                this.directAttack(1, 90, 60, 800);
                this.directAttack(2, 90, 60, 800);
                this.directAttack(3, 145, 60, 1000);      
                
            }else if(!core.isAtk()){
                this.attackSequenceL(0,3,100,700);
                this.attackSequenceR(3,3,100,700);
                this.attackSequenceL(2,3,100,700);
                this.attackSequenceR(1,3,100,700);  
            }
        }else if(status.equals("GameOver")){
            
        }
        }
    }
    
    public void checkCollision(ArrayList<Projectile> lista){
        if(status.equals("Game")){
        lista.forEach(shoot -> {
            if(shoot.checkCollision(core.getHitbox())){
                if(core.isVulnerable())hp-=coreDamage*2;
                else hp-=coreDamage;
            }
            else if(shoot.checkCollision(body.getHitbox()))hp-=bodyDamage;
            else if(shoot.checkCollision(blaster01.getHitbox()))hp-=blasterDamage;
            else if(shoot.checkCollision(blaster02.getHitbox()))hp-=blasterDamage;
            else if(shoot.checkCollision(blaster03.getHitbox()))hp-=blasterDamage;
            else if(shoot.checkCollision(blaster04.getHitbox()))hp-=blasterDamage;
        });
        }
    }
    
    public void checkBulletCollision(Shape c){
        if(status.equals("Game")){
        blaster01.checkBulletCollision(c);
        blaster02.checkBulletCollision(c);
        blaster03.checkBulletCollision(c);
        blaster04.checkBulletCollision(c);
        }
    }
   
    public void attackSequence(){
        int cd = (int)(Math.random()*500);
        int stepAngle = (int)((Math.random()*20)+5);
            blasterList.get(0).sequenceAttack(45,135,cd, stepAngle);
            blasterList.get(3).sequenceAttack(135,45,cd, stepAngle);    
    }
    
    public void directAttack(int blaster, float angle , int cd, int speed){
        
        blasterList.get(blaster).continuousAttack(angle, cd, speed);
        
    }
    
    public void attackSequence(float startAngle,float finalAngle,int blaster,int step, int cd){
        blasterList.get(blaster).sequenceAttack(startAngle, finalAngle, cd, step);
        
    }
    
    public void attackSequenceR(int blaster,int step,int cd , int speed){
        blasterList.get(blaster).sequenceAttack(45,135, cd, step);
        blasterList.get(blaster).speed=speed;
        
    }
    
    public void attackSequenceL(int blaster,int step,int cd, int speed){
        blasterList.get(blaster).sequenceAttack(135,45,cd,step);
        blasterList.get(blaster).speed=speed;
    }
    
    public void attackTarget(Shape t){
            blasterList.get(1).targetAttack(t);
            blasterList.get(2).targetAttack(t);
    }
    
    public void attackTarget(int blaster , Shape t , int speed , int cd){
        blasterList.get(blaster).setCdAtk(cd);
        blasterList.get(blaster).speed=speed;
        blasterList.get(blaster).targetAttack(t);
    }
    
    public void takeDamage(int damage){
        this.hp -= damage;
    }
    
    public void setTarget(Shape c){
        this.targetHitbox=c;
    }
    
    public ArrayList<Projectile> getShootList(){
        ArrayList<Projectile> shootList = new ArrayList<>();
        blasterList.forEach(blaster -> {
           shootList.addAll(blaster.shootList);
        });
        return shootList;
    }
    
    public ArrayList<LaserShot> getLaserList(){
        return core.getLaserList();
    }
    
    public void coreAttack(int a){
        if(!isCoreAtk){
            isCoreAtk=true;
            core.coreAtk(a);
        }
    }
    
    public void stopBlasters(){
        blasterList.forEach(blaster -> blaster.cancelAttack());
    }
    
    public void runIntro(){
        core.runAnimation();
    }
    
}
