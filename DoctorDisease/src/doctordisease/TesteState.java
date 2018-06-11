/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author saita
 */
public class TesteState extends BasicGameState{
    long t1;
    long t2;
    boolean render=false;
    boolean update=false;
    int cd=0;
    float angle=90;
    ArrayList<Line> EDGEmovement = new ArrayList <>();
    ArrayList<Line> EDGE = new ArrayList<>();
    int time=0;

    
    
    Player guts = new Player(new Point(512,500),300);
    
    Boss boss = new Boss(new Point(204,0));
    
    LaserShot laser = new LaserShot(new Point(0,0),new Vector2f(0,1));
    
    



    
    public TesteState(int state){
        
    }

    @Override
    public int getID() {
        return 10;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        guts = new Player(new Point(512,500),300);
        boss = new Boss(new Point(204,0));
        laser = new LaserShot(new Point(0,0),new Vector2f(0,1));
        EDGEmovement.add(new Line(2, 200, 1022, 200)); // desenhando a parte do game
        EDGEmovement.add(new Line(1, 1, 1, 767)); // desenhando a parte do game
        EDGEmovement.add(new Line(1023, 767, 1, 767)); // desenhando a parte do game
        EDGEmovement.add(new Line(1023, 767, 1023, 1)); // desenhando a parte do game
        EDGE.add(new Line(2, 2, 1022, 2)); // desenhando a parte do game
        EDGE.add(EDGEmovement.get(1)); // desenhando a parte do game
        EDGE.add(EDGEmovement.get(2)); // desenhando a parte do game
        EDGE.add(EDGEmovement.get(3)); // desenhando a parte do game
        guts.setLimits(EDGEmovement);
        
        boss.setTarget(guts.getHitbox());

        guts.status="Game";
        boss.runIntro();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        

        boss.draw(g);
        guts.draw(g);  
        g.drawString(Float.toString(boss.hp), 50, 50);
        g.drawString(Float.toString(guts.hp),900,50);

 
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        
        if(guts.hp<=0)this.init(container, game);
//        long t1 = System.currentTimeMillis();


        boss.update(container, game, delta);
        Input input = container.getInput();

        guts.update(container, game, delta);
        boss.checkCollision(guts.getShootList());
        guts.checkCollision(boss.getShootList());
        guts.checkLaserCollision(boss.getLaserList());
        
        EDGE.forEach(parede -> {
           boss.checkBulletCollision(parede);
           guts.checkBulletCollision(parede);
               });      
        
//        long t2 = System.currentTimeMillis();
//        System.out.println(t2-t1);
    

        
    }

}