/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author saita
 */
public class TesteState extends BasicGameState{
    boolean render=false;
    boolean update=false;
    int cd=0;
    float angle=90;
    ArrayList<Line> EDGE = new ArrayList <>();
    int time=0;

    
    Player guts = new Player(new Point(512,500),300);
    
    Boss boss = new Boss(new Point(204,0));
    
    



    
    public TesteState(int state){
        
    }

    @Override
    public int getID() {
        return 10;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        EDGE.add(new Line(2, 2, 1022, 2)); // desenhando a parte do game
        EDGE.add(new Line(1, 1, 1, 767)); // desenhando a parte do game
        EDGE.add(new Line(1023, 767, 1, 767)); // desenhando a parte do game
        EDGE.add(new Line(1023, 767, 1023, 1)); // desenhando a parte do game
        guts.setLimits(EDGE);
        
        boss.setTarget(guts.getHitbox());
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

        Input input = container.getInput();
        
        boss.update(container, game, delta);

        guts.update(container, game, delta);
        boss.checkCollision(guts.getShootList());
        guts.checkCollision(boss.getShootList());
        
       if(input.isKeyPressed(Input.KEY_S))boss.coreAttack();
       if(input.isKeyPressed(Input.KEY_W))boss.hp+=500;
       EDGE.forEach(parede -> {
           boss.checkBulletCollision(parede);
           guts.checkBulletCollision(parede);
               });
        
    }

}