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

/*
Classe do personagem jogável,onde consta todos os atributos referentes ao player como , animação
posicionamento, velocidade, disparo de projéteis, vida etc...
*/

public class Player {
    
    GameContainer gc;
    StateBasedGame sbg;
    int deltaUpdate;
    
    String status;
    
    Sound bulletSound;
    
    int speed,cd,timer,timerHit,cdHit;
    
    float hp,alphaIntro;
    
    boolean isOnCd,isAtk,takeHit,pause;
    
    Point location;
    Vector2f direction;
    Rectangle hitbox;
    ArrayList<Projectile> shootList;
    ArrayList<Line> moveLimit;
    
    SpriteSheet sBase,sPropulsion,sBullet;
    Animation aBase,aPropulsion,aPropulsionUp,aPropulsionIdle,aPropulsionDown;
    
    /*
    Constructor da classe player baseado em um ponto de coordenada no plano cartesiano e a velocidade dos projéteis,
    Os demais atributos como animações e sons, foram estabelecidas como padrão por isso não entra como parâmetro.
    */
    public Player(Point location, int speed) {
        
        alphaIntro=0f;
        
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
            
            aBase.getImage(0).setAlpha(alphaIntro);

            for(int x=0;x<aPropulsion.getFrameCount()-1;x++){
                aPropulsion.getImage(x).setAlpha(alphaIntro);
            }
        
        } catch (SlickException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        hitbox = new Rectangle(location.getX(),location.getY(),aBase.getWidth(),aBase.getHeight());
        direction=new Vector2f(0,0);
    }
    /*
    Método chamado para desenhar o player e seus respectivos projéteis pelo contexto gráfico,
    com a opção de desenhar a hitbox para efeito de debug etc..
    */
    public void draw(Graphics g){
        
        shootList.forEach(shoot -> {
            shoot.draw(g);
        });
        
//        g.draw(hitbox);
        if(takeHit)aBase.drawFlash(location.getX(), location.getY(), aBase.getWidth(), aBase.getHeight());
        else aBase.draw(location.getX(), location.getY());
        
        aPropulsion.draw(location.getX()+ 18, location.getY()+40);
        
  
    }
    /*
    Método responsável por atualizar os principais atributos do player como (posição, animação atual) e também contém o 
    Scanner do teclado referente a este player
    */
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        if(pause){

            
        }else{
        if(status.equals("Intro")){
            timer+=delta;
            if(timer>=50 && alphaIntro<1){
                timer=0;
                alphaIntro+=0.01f;
            }
            aBase.getCurrentFrame().setAlpha(alphaIntro);
            aPropulsion.getCurrentFrame().setAlpha(alphaIntro);
            
        }
        else if(status.equals("Game")){
            for(int x=0;x<aBase.getFrameCount();x++){
                aBase.getImage(x).setAlpha(1);
            }
            for(int x=0;x<aPropulsion.getFrameCount();x++){
                aPropulsion.getImage(x).setAlpha(1);
            }
            
            aPropulsion=aPropulsionIdle;
            aPropulsion.setAutoUpdate(true);
            hitbox.setLocation(location.getX(), location.getY());

            if(takeHit){
                timerHit+=delta;
                if(timerHit>=100){
                    timerHit=0;
                    takeHit=false;
                }
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
        
    }
    /*
    Método responsável para lidar com os ataques do player baseado em sua animação de ataque,também faz com que o som do 
    tiro seja executado e adiciona os projéteis criados na lista de projéteis
    */
    public void handleAtk(){
            if(aBase.getFrame()==2 && !isAtk){
                shootList.add(new Projectile(new Point(location.getX()+37,location.getY()+12),new Vector2f(0,-600),sBullet));
                if(!bulletSound.playing())bulletSound.play(1, 0.2f);
                isAtk=!isAtk;
            }else if(aBase.getFrame()==3 && isAtk){
                shootList.add(new Projectile(new Point(location.getX()+5,location.getY()+12),new Vector2f(0,-600),sBullet));
                if(!bulletSound.playing())bulletSound.play(1, 0.2f);
                isAtk=!isAtk;    
            }else if(aBase.getFrame()==5){
                aBase.setCurrentFrame(2);   
            }  
    }
    /*
    Método responsável para atualizar a posição do player baseado no vetor que foi setado de acordo com 
    as teclas pressionadas anteriormente e após isso reseta o vetor para uma nova verificação
    */
    public void move(int delta){
        
        location.setX(location.getX()+(float)(direction.getX()*(speed * delta/1000.0)));
        location.setY(location.getY()+(float)(direction.getY()*(speed * delta/1000.0)));
        direction.set(0,0);
        
    }
    /*
    Método responsável por lidar com a colisão entre algum objeto e a hitbox do player
    */
    public boolean checkCollision(Shape c){
        if(status.equals("Game")){
            return hitbox.intersects(c);
        }
        return false;
    }
    /*
    Método responsável por lidar com a colisão de projéteis a partir de uma lista com o hitbox do player e 
    atualiza o hp caso haja colisão
    */
    public void checkCollision(ArrayList<Projectile> shootList){
        if(status.equals("Game")){
        shootList.forEach(shoot -> {
                    if(shoot.checkCollision(hitbox)){
                        this.takeHit(5);
                    }
            });
        }
    }
    /*
    Método responsável por lidar com a colisão de lasers a partir de uma lista com o hitbox do player 
    */
    public void checkLaserCollision(ArrayList<LaserShot> laserList){
        if(status.equals("Game")){
                laserList.forEach(laser -> {
                    if(laser.checkCollision(hitbox)){
                        this.takeHit(8);
                    }
            });
        }
        
    }
    /*
    Método que retorna true caso haja a colisão dos tiros do player com alguma hitbox
    */
    public void checkBulletCollision(Shape c){
        
        if(status.equals("Game"))shootList.forEach(bullet -> bullet.checkCollision(c));
        
    }
    /*
    Método que retorna a hitbox do player
    */
    public Shape getHitbox(){
        return hitbox;
    }
    /*
    Método que seta o limite espacial que o player poderá percorrer a partir de uma lista de Linhas
    */
    public void setLimits(ArrayList<Line> c){
        this.moveLimit=c;
    }
    /*
    Método que retorna a lista que armazena os projéteis do player
    */
    public ArrayList<Projectile> getShootList(){
        return shootList;
    }
    /*
    Método que atualiza o hp do player com o valo de entrada referente ao dano que ele receberá
    */
    public void takeHit(int hp){
        if(!takeHit){
            takeHit=true;
            this.hp-=hp;
        }
    }
    /*
    Método que atualiza o status de pause do player
    */
    public void pause() {
        System.out.println("PlayerPaused");
        pause=!pause;
        aBase.setAutoUpdate(false);
        aPropulsion.setAutoUpdate(false);  
    }

    
    public void setDelta(int x){
        this.deltaUpdate=x;
    }
    
    public void setGame(GameContainer gc, StateBasedGame sbg){
        this.gc=gc;
        this.sbg=sbg;
    }
    
}