/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author saita
 */
public class Fase01 extends BasicGameState implements InputProviderListener{
    
    String status;
    
    Image hp,health;
    ArrayList<Line> EDGEmovement;
    ArrayList<Line> EDGE;
    
    int time;
    
    Player guts;
 
    Boss boss;
    
    private Command esc;
    InputProvider provider;
    
    Image background,boxPause,pausePT,pauseENG;
    
    Image img_quitPT[], img_quitENG[] ,img_menuPTENG[];
    
    boolean pause,listener,active;
    
    MouseOverArea bt_quitPT,bt_quitENG,bt_menuPTENG;

    public Fase01(int state){
        
    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        
        hp = new Image("data/image/Fase01/hp.png");
        
        health = new Image ("data/image/Fase01/health.png");
        
        EDGEmovement = new ArrayList <>();
        EDGE = new ArrayList<>();
        time=0;
        
        esc = new BasicCommand("esc");
        
        pause=false;
        
        guts = new Player(new Point(490,500),300);
        boss = new Boss(new Point(204,0));
        
        
        pausePT = new Image("/data/image/Fase01/pause-pt.png");
        pauseENG = new Image("/data/image/Fase01/pause-eng.png");
        boxPause = new Image("/data/image/MainMenu/box_options.png");
            
        img_quitPT=new Image[]{new Image("/data/image/Fase01/back-pause-pt1.png"),new Image("/data/image/Fase01/back-pause-pt2.png")};
        img_quitENG = new Image[]{new Image("/data/image/Fase01/back-pause-eng1.png"),new Image("/data/image/Fase01/back-pause-eng2.png")};
        img_menuPTENG = new Image[]{new Image("/data/image/Fase01/menu-pause-pt1.png"),new Image("/data/image/Fase01/menu-pause-pt2.png")};
        
            bt_quitPT = new MouseOverArea(container, img_quitPT[0], 682-(img_quitPT[0].getWidth()/2) , 380, img_quitPT[0].getWidth(),
            img_quitPT[0].getHeight(), new ComponentListener() {

                @Override
                public void componentActivated(AbstractComponent arg0) {
                        pause=!pause;
                        guts.pause();
                        boss.pause();

                }
            });
            bt_quitPT.setMouseOverImage(img_quitPT[1]);
            
            bt_quitENG = new MouseOverArea(container, img_quitENG[0],682-(img_quitENG[0].getWidth()/2) , 380, img_quitENG[0].getWidth(),
            img_quitENG[0].getHeight(), new ComponentListener() {

                @Override
                public void componentActivated(AbstractComponent arg0) {
                        pause=!pause;
                        guts.pause();
                        boss.pause();

                }
            });
            bt_quitENG.setMouseOverImage(img_quitENG[1]);
            
            bt_menuPTENG = new MouseOverArea(container, img_menuPTENG[0],341-(img_menuPTENG[0].getWidth()/2) , 380, img_menuPTENG[0].getWidth(),
            img_menuPTENG[0].getHeight(), new ComponentListener() {

                @Override
                public void componentActivated(AbstractComponent arg0) {
                        //provider.removeListener(Fase01.this);
                        pause = !pause;
                        guts.pause();
                        boss.pause();
                    try {
                        game.getState(1).init(container, game);
                    } catch (SlickException ex) {
                        Logger.getLogger(Fase01.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        game.enterState(1,new FadeOutTransition(new Color (0,0,0)) ,new FadeInTransition(new Color (0,0,0)));
                        DoctorDisease.gameState = 1;
                        provider.setActive(false);

                }
            });
            bt_menuPTENG.setMouseOverImage(img_menuPTENG[1]);
            
        provider = new InputProvider(container.getInput());
        this.addListener();
        if(!active){
            provider.setActive(false);
            active=true;
        }else provider.setActive(true);
        
        provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), esc);
        
        background = new Image("data/image/Fase01/background-stomach.png");
        
        status="Intro";

        EDGEmovement.add(new Line(2, 200, 1022, 200)); 
        EDGEmovement.add(new Line(1, 1, 1, 767)); 
        EDGEmovement.add(new Line(1023, 767, 1, 767)); 
        EDGEmovement.add(new Line(1023, 767, 1023, 1)); 
        EDGE.add(new Line(2, 2, 1022, 2)); 
        EDGE.add(EDGEmovement.get(1)); 
        EDGE.add(EDGEmovement.get(2));
        EDGE.add(EDGEmovement.get(3)); 
        
        
        guts.setLimits(EDGEmovement); 
        boss.setTarget(guts.getHitbox());

        boss.runIntro();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        
        background.draw();
        boss.draw(g);
        guts.draw(g);  
//        g.drawString(Float.toString(boss.hp), 50, 50);
//        g.drawString(Float.toString(guts.hp),900,50);
        hp.drawCentered(512, 720);
        health.draw(267, 700,guts.hp/100*490 , 40);
        
        if(pause){
            boxPause.draw(112,200, 800, 300);
            bt_menuPTENG.render(container, g);
            if(Button.estados[1]==0){
                pausePT.drawCentered(512, 270);
                bt_quitPT.render(container, g);
            }
            else{
                pauseENG.drawCentered(512, 270);
                bt_quitENG.render(container, g);
            }
        }
 
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(guts.hp<=0 || boss.body.aIntro02.isStopped()){
            game.getState(3).init(container, game);
            game.enterState(3,new FadeOutTransition(new Color (0,0,0)) ,new FadeInTransition(new Color (0,0,0)));
        }
        if(pause){
            Input input = container.getInput();
            if(input.isKeyPressed(Input.KEY_ENTER)){
                game.getState(1).init(container, game);
                game.enterState(1);
            }
        }
        else{
        if(status.equals("Intro")){
            if(boss.checkStatus().equals("Game")){
                guts.status="Game";
                this.status="Game";
            }
           
        }
        
//       if(guts.hp<=0)this.init(container, game);
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

    @Override
    public void controlPressed(Command command) {
           if (command.toString().contains("esc")) {
               
                pause = !pause;
                if(pause)DoctorDisease.app.pause();
                else DoctorDisease.app.resume();
                guts.pause();
                boss.pause();
            }
    }

    @Override
    public void controlReleased(Command command) {

            
        
    }
    
    public void addListener(){
        if(!listener)
            System.out.println("AddLISTENER");
            provider.addListener(this);
            listener=true;
    }

}