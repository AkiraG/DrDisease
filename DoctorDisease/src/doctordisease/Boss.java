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
 * 
 * Classe criada para fazer a interação das demais partes do Boss da Fase 01
 * @author saita
 */
public class Boss {
    
    
    String status;
    
    Color renderColor;
    
    Shape targetHitbox;
    
    float hp,coreDamage,bodyDamage,blasterDamage;
    boolean isAtk,isCoreAtk,pause;
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
     /*
    Método chamado para desenhar o Boss da Fase01 e suas partes como um todo
    */
    public void draw(Graphics g){
        if(status.equals("Intro"))partList.forEach(item -> item.draw(g, Color.gray));
        else partList.forEach(item -> item.draw(g));
    }
    /*
    Método que cuida da atualização de toda lógica envolvendo o Boss e suas partes, como rodar animações de intro,transformações
    e realizar os padrões de ataques
    */
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        if(pause){
            
        
        }else{
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
        }else if(hp<=0){
            for(int x=0;x<4;x++){
                blasterList.get(x).cancelAttack();
                partList.remove(0);
            }
            body.runIntro02();
            
            this.status="GameOver";
            
        }else if(status.equals("GameOver")){

            
        }
        }
        }
    }
    /*
    Método que checa a colisão dos ataques recebidos pelo boss em forma de projéteis
    */
    public void checkCollision(ArrayList<Projectile> lista){
        if(status.equals("Game")){
        lista.forEach(shoot -> {
            if(shoot.checkCollision(core.getHitbox())){
                hp-=core.takeHit();
            }
            else if(shoot.checkCollision(body.getHitbox()))hp-=bodyDamage;
            else {
                blasterList.forEach(blaster -> {
                    if(shoot.checkCollision(blaster.getHitbox())){
                        hp-=blasterDamage;
                        blaster.takeHit();
                    }
                    
                });
            }
        });
        }
    }
    /*
    Método que checa a colisão dos ataques do Boss na forma de projéteis com alguma hitbox
    */
    public void checkBulletCollision(Shape c){
        if(status.equals("Game")){
        blaster01.checkBulletCollision(c);
        blaster02.checkBulletCollision(c);
        blaster03.checkBulletCollision(c);
        blaster04.checkBulletCollision(c);
        }
    }
    /*
    Método que cria uma sequência randômica de ataque nos blaster 1 e 4
    */
    public void attackSequence(){
        int cd = (int)(Math.random()*500);
        int stepAngle = (int)((Math.random()*20)+5);
            blasterList.get(0).sequenceAttack(45,135,cd, stepAngle);
            blasterList.get(3).sequenceAttack(135,45,cd, stepAngle);    
    }
    /*
    Método que cria um ataque direto baseado no blaster, ângulo de ataque, cooldown entre os ataques e velocidade dos projéteis
    */
    public void directAttack(int blaster, float angle , int cd, int speed){
        
        blasterList.get(blaster).continuousAttack(angle, cd, speed);
        
    }
    /*
    Método que cria uma sequência de ataques baseada em um ângulo inicial e final,o blaster que irá atacar, qual o passo de
    rotação de cada ataque e o cooldown entre os ataques
    */
    public void attackSequence(float startAngle,float finalAngle,int blaster,int step, int cd){
        blasterList.get(blaster).sequenceAttack(startAngle, finalAngle, cd, step);
        
    }
    /*
    Método que cria uma sequência de ataques com ângulo final e inicial fixo, fazendo com que o blaster percorra um caminho
    no sentido horário
    */
    public void attackSequenceR(int blaster,int step,int cd , int speed){
        blasterList.get(blaster).sequenceAttack(45,135, cd, step);
        blasterList.get(blaster).speed=speed;
        
    }
     /*
    Método que cria uma sequência de ataques com ângulo final e inicial fixo, fazendo com que o blaster percorra um caminho
    no sentido anti-horário
    */
    public void attackSequenceL(int blaster,int step,int cd, int speed){
        blasterList.get(blaster).sequenceAttack(135,45,cd,step);
        blasterList.get(blaster).speed=speed;
    }
    /*
    Método que cria um ataque dos blasters 2 e 3, em direção a um alvo(hitbox) específico.
    */
    public void attackTarget(Shape t){
            blasterList.get(1).targetAttack(t);
            blasterList.get(2).targetAttack(t);
    }
    /*
    Método que cria um ataque  em direção a um alvo(hitbox) específico com a configuração de.
    qual blaster irá atacar, qual a velocidade do tiro, e o cooldown entre os ataques
    */
    public void attackTarget(int blaster , Shape t , int speed , int cd){
        blasterList.get(blaster).setCdAtk(cd);
        blasterList.get(blaster).speed=speed;
        blasterList.get(blaster).targetAttack(t);
    }
    /*
    Método que lida com o Hp retirando uma quantidade baseado na entrada
    */
    public void takeDamage(int damage){
        this.hp -= damage;
    }
    /*
    Método que configura o hitbox alvo do boss
    */
    public void setTarget(Shape c){
        this.targetHitbox=c;
    }
    /*
    Método que retorna a lista onde o Boss armazena seus ataques de projéteis ativos
    */
    public ArrayList<Projectile> getShootList(){
        ArrayList<Projectile> shootList = new ArrayList<>();
        blasterList.forEach(blaster -> {
           shootList.addAll(blaster.shootList);
        });
        return shootList;
    }
    /*
    Método que retorna a lista onde o Boss armazena seus ataques de Lasers ativos
    */
    public ArrayList<LaserShot> getLaserList(){
        return core.getLaserList();
    }
    /*
    Método que inicia o ataque especial do núcleo do boss
    */
    public void coreAttack(int a){
        if(!isCoreAtk){
            isCoreAtk=true;
            core.coreAtk(a);
        }
    }
    /*
    Método que para qualquer sequência de ataque iniciada
    */
    public void stopBlasters(){
        blasterList.forEach(blaster -> blaster.cancelAttack());
    }
    /*
    Método utilizado para rodar a animação de introdução do núcleo
    */
    public void runIntro(){
        core.runAnimation();
    }
    /*
    Método que retorna a variável que demonstra o estado atual de gameplay que o boss se encontra (Intro,Game,etc..)
    */
    public String checkStatus(){
        return status;
    }
     /*
    Método que atualiza o status de pause do Boss
    */
    public void pause(){
        
            pause=!pause;
            partList.forEach(part -> part.pause());

    }
    
}
