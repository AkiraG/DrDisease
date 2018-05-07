/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.io.InputStream;
import org.newdawn.slick.Animation;
import org.newdawn.slick.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import java.awt.Font;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Gabriel
 */
public class Menu extends BasicGameState {
    
    Image background, boxOption, boxOption2,telaTutorial;
    Image[] logoGame = new Image[8];    
    Animation logo = new Animation(logoGame, 2);
    
    
    Celulas[] celulasLst = new Celulas [15];
    Button btBack,btBack2, btStart, btOptions, btQuit, btLanguage, btSound, btTuto, btCred, btSobre;
    Button[] btList;
    
    static Sound btClick, btReturn;
    private Music bgMusic;
    private Font font;
    private TrueTypeFont pixelFont;
    
    
    public Menu(int ID) {
    }        
    
    @Override
    public int getID() {
        return 1;        
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
        //Set font
        try{
            InputStream inputStream = ResourceLoader.getResourceAsStream("/data/fonts/pixelart.ttf");
 
			Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			font = font.deriveFont(20f); // set font size
			pixelFont = new TrueTypeFont(font, true);
            
                } catch (Exception e) {
                e.printStackTrace();
                }
        
        bgMusic= new Music("/data/sound/Intro_Drop.ogg");
        btClick= new Sound("/data/sound/Main_Menu_Select.ogg");
        btReturn= new Sound("/data/sound/Main_Menu_Back.ogg");
        
        // path pra imagem, posX, posY, valor do state, som do botão - isso pra botôes que alterem o state
        btStart = new Button("bt_start", 512, 500, 2, btClick, "state");
        btTuto = new Button("bt_tutorial",512,550,4,btClick,"state");
        btOptions = new Button("bt_options", 512, 600, 1, btClick, "state");
        btQuit = new Button("bt_quit", 512, 650, 3, btClick, "state");
        btCred = new Button("bt_cred",100,700,5, btClick, "state");
        btSobre = new Button("bt_sobre",924,700,6, btClick, "state");        
        btBack = new Button("bt_back", 850, 650, 0, btReturn, "state");
        btBack2 = new Button("bt_back",850,700,0,btReturn,"state");
        // path para imagem, posX, posY, qual eh a var de controle a ser alterada, som do click - isso para botôes de controle
        // 1 para language e 2 para o som
        btLanguage = new Button("language_options", 450, 550, 1, btClick, "control");
        btSound = new Button("sound_options", 650, 550, 2, btClick, "control");
        // criar os botoes na parte de cima e adiciona-los na lista
        btList = new Button[] {btStart, btOptions, btQuit, btBack,btBack2, btLanguage, btSound, btTuto, btCred, btSobre};
        for (Button b: btList)
            b.init(gc, sbg); // roda a lista e da o init dos botoes
        
        for (int x = 1; x <= 8; x++){
            logoGame[(x - 1)] = new Image("data/image/MainMenu/logo_game" + x +".png");
        }
        logo = new Animation(logoGame, 100, true);
        
        background = new Image("data/image/MainMenu/background.png");
        boxOption = new Image("data/image/MainMenu/box_options.png");
        boxOption2 = new Image("data/image/MainMenu/box_options2.png");// box usado para creditos tamanho alterado
        telaTutorial = new Image("data/image/MainMenu/tela_tutorial.png");
        
        for (int x = 0 ; x < 15 ; x++){
            celulasLst[x] = new Celulas();
            celulasLst[x].init(gc, sbg);
        }        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        for (Celulas c : celulasLst) c.render(gc, sbg, g);
      
        switch (Button.estados[0]){
            case 0:
                btStart.render(gc, sbg, g);
                btTuto.render(gc, sbg, g);
                btOptions.render(gc, sbg, g);
                btCred.render(gc, sbg, g);
                btSobre.render(gc, sbg, g);
                btQuit.render(gc, sbg, g);  
                break;
            case 1:
                g.drawImage(boxOption, 100, 400);
                pixelFont.drawString(410, 520, "IDIOMA");
                btLanguage.render(gc, sbg, g);
                pixelFont.drawString(610, 520, "AUDIO");
                btSound.render(gc, sbg, g);
                btBack.render(gc, sbg, g); 
                break;
            case 2:
                sbg.enterState(2);
                bgMusic.stop();
                break;
            case 3: // Quit - Fecha o jogo
                DoctorDisease.gameRunning = false;
                System.exit(0);
                break;
            case 4: // Tutorial
                g.drawImage(boxOption2, 62, 320);
                g.drawImage(telaTutorial, 130, 400);
                btBack2.render(gc, sbg, g);
                break;
            case 5: // Creditos
                g.drawImage(boxOption2, 62, 320);
                pixelFont.drawString(130, 430,"PROGRAMACAO");
                pixelFont.drawString(150,460 , "Flavio Alves. Grabriel Faggione");
                pixelFont.drawString(130, 490, "ARTE e DESIGN");
                pixelFont.drawString(150, 520, "George Dourado");
                pixelFont.drawString(130, 550, "OST . EFEITOS SONOROS");
                pixelFont.drawString(150, 580, "Alexander Rodrigues");
                pixelFont.drawString(130, 610, "ROTEIRO");
                pixelFont.drawString(150, 640, "Jose Roberto Calderon");
                pixelFont.drawString(130, 670, "PRODUCAO");
                pixelFont.drawString(150, 700, "Flavio Alves. George Dourado");
                btBack2.render(gc, sbg, g);
                break;
            case 6:// botao sobre
                g.drawImage(boxOption2, 62, 320);
                pixelFont.drawString(130, 430,"Doctor Disease e um game do genero Bullet Hell");
                pixelFont.drawString(130, 450,"onde o jogador trava batalhas contra doencas");
                pixelFont.drawString(130, 470,"dentro do corpo humano");
                pixelFont.drawString(130, 510,"Este game foi desenvolvido como projeto de conclusao do");
                pixelFont.drawString(130, 530,"3o Semestre da diciplina de Tecnologia em Jogos Digitais");
                pixelFont.drawString(130, 550,"da Faculdade de Tecnologia de Carapicuiba");
                pixelFont.drawString(130, 580,"Classificacao Indicativa 10 anos");
                btBack2.render(gc, sbg, g);
                break;
           
        }       
        logo.draw(315, 30);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        
        if (DoctorDisease.gameState != 1) DoctorDisease.gameState = 1;
        
        for (Celulas c : celulasLst) c.update(gc, sbg, i);     
        /**
        if (!bgMusic.playing() && Button.estados[2] == 0) {
            bgMusic.play();
            DoctorDisease.app.setSoundOn(true);
        }
        else if (Button.estados[2] != 0) {
            bgMusic.pause();
            DoctorDisease.app.setSoundOn(false);
        }
        **/
    }
}

