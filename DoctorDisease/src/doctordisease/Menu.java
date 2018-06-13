/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctordisease;

import java.io.InputStream;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Gabriel
 */
public class Menu extends BasicGameState {
    
    Image background, boxOption, boxOption2,telaTutorial,telaTutorialEng;
    Image[] logoGame = new Image[8];    
    Animation logo = new Animation(logoGame, 2);
    
    
    Celulas[] celulasLst = new Celulas [15];
    Button btBack,btBackEng,btBack2,btBack2Eng, btStart,btStartEng, btOptions,btOptionsEng, btQuit,btQuitEng, btLanguage, btSound, btTuto, btCred,btCredEng, btAbout,btAboutEng;
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
        
        //configurando fonte usada
        try{
            InputStream inputStream = ResourceLoader.getResourceAsStream("/data/fonts/amiga4ever_pro2.ttf");
 
			Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			font = font.deriveFont(20f); // set font size
			pixelFont = new TrueTypeFont(font, true);
            
                } catch (Exception e) {
                e.printStackTrace();
                }

        btClick= new Sound("/data/sound/Menu/Main_Menu_Select.ogg");
        btReturn= new Sound("/data/sound/Menu/Main_Menu_Back.ogg");
        
        // path pra imagem, posX, posY, valor do state, som do botão - isso pra botôes que alterem o state
        btStart = new Button("bt_start_pt", 512, 500, 2, btClick, "state");
        btStartEng = new Button("bt_start_eng", 512, 500, 2, btClick, "state");
        btTuto = new Button("bt_tutorial",512,550,4,btClick,"state");
        btOptions = new Button("bt_options_pt", 512, 600, 1, btClick, "state");
        btOptionsEng = new Button("bt_options_eng", 512, 600, 1, btClick, "state");
        btQuit = new Button("bt_quit_pt", 512, 650, 3, btClick, "state");
        btQuitEng = new Button("bt_quit_eng", 512, 650, 3, btClick, "state");
        btCred = new Button("bt_credits_pt",100,700,5, btClick, "state");
        btCredEng = new Button("bt_credits_eng",100,700,5, btClick, "state");
        btAbout = new Button("bt_about_pt",924,700,6, btClick, "state");
        btAboutEng = new Button("bt_about_eng",924,700,6, btClick, "state");
        btBack = new Button("bt_back_pt", 850, 650, 0, btReturn, "state");
        btBackEng = new Button("bt_back_eng", 850, 650, 0, btReturn, "state");
        btBack2 = new Button("bt_back_pt",850,700,0,btReturn,"state");
        btBack2Eng = new Button("bt_back_eng",850,700,0,btReturn,"state");
        // path para imagem, posX, posY, qual eh a var de controle a ser alterada, som do click - isso para botôes de controle
        // 1 para language e 2 para o som
        btLanguage = new Button("language_options", 375, 550, 1, btClick, "lang");
        btSound = new Button("sound_options", 650, 550, 2, btClick, "control");
        // criar os botoes na parte de cima e adiciona-los na lista
        btList = new Button[] {btStart,btStartEng, btOptions,btOptionsEng, btQuit,btQuitEng, btBack,btBack2,btBackEng, btBack2Eng, btLanguage, btSound, btTuto, btCred,btCredEng, btAbout,btAboutEng};
        for (Button b: btList)
            b.init(gc, sbg); // roda a lista e da o init dos botoes
        
        for (int x = 1; x <= 8; x++){
            logoGame[(x - 1)] = new Image("data/image/MainMenu/logo_game" + x +".png");
        }
        logo = new Animation(logoGame, 100, true);
        
        background = new Image("data/image/MainMenu/background.png");
        boxOption = new Image("data/image/MainMenu/box_options.png");
        boxOption2 = new Image("data/image/MainMenu/box_options2.png");// box usado para creditos tamanho alterado
        telaTutorial = new Image("data/image/MainMenu/tela_tutorialpt.png");
        telaTutorialEng = new Image("data/image/MainMenu/tela_tutorialeng.png");
        
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
                if (Button.estados[1] == 0){
                    btStart.render(gc, sbg, g);
                    btTuto.render(gc, sbg, g);
                    btOptions.render(gc, sbg, g);
                    btCred.render(gc, sbg, g);
                    btAbout.render(gc, sbg, g);
                    btQuit.render(gc, sbg, g);}
                else {
                    btStartEng.render(gc, sbg, g);
                    btTuto.render(gc, sbg, g);
                    btOptionsEng.render(gc, sbg, g);
                    btCredEng.render(gc, sbg, g);
                    btAboutEng.render(gc, sbg, g);
                    btQuitEng.render(gc, sbg, g);
                }          
                break;
            case 1:
                if (Button.estados[1] == 0){
                    g.drawImage(boxOption, 100, 400);
                    pixelFont.drawString(375-(pixelFont.getWidth("IDIOMA")/2), 520, "IDIOMA");
                    btLanguage.render(gc, sbg, g);
                    pixelFont.drawString(650-(pixelFont.getWidth("AUDIO")/2), 520, "AUDIO");
                    btSound.render(gc, sbg, g);
                    btBack.render(gc, sbg, g);}
                else{
                    g.drawImage(boxOption, 100, 400);
                    pixelFont.drawString(375-(pixelFont.getWidth("LANGUAGE")/2), 520, "LANGUAGE");
                    btLanguage.render(gc, sbg, g);
                    pixelFont.drawString(650-(pixelFont.getWidth("SOUND")/2), 520, "SOUND");
                    btSound.render(gc, sbg, g);
                    btBackEng.render(gc, sbg, g);}

                                    
                break;                
            case 2:
                Button.estados[0]=0;
                DoctorDisease.gameState = 2;
                sbg.getState(2).init(gc, sbg);
                sbg.enterState(2,new FadeOutTransition(new Color (0,0,0)) ,new FadeInTransition(new Color (0,0,0)));
                
                break;
            case 3: // Quit - Fecha o jogo
                DoctorDisease.gameRunning = false;
                System.exit(0);
                break;
            case 4: // Tutorial
                if (Button.estados[1] == 0){                    
                    g.drawImage(boxOption2, 62, 320);
                    g.drawImage(telaTutorial, 230, 400);                    
                    btBack2.render(gc, sbg, g);
                    pixelFont.drawString(512-(pixelFont.getWidth("CONTROLES")/2), 450, "CONTROLES");
                }
                else{                    
                    g.drawImage(boxOption2, 62, 320);
                    g.drawImage(telaTutorialEng, 230, 400);
                    pixelFont.drawString(512-(pixelFont.getWidth("CONTROLS")/2), 450, "CONTROLS");
                    btBack2Eng.render(gc, sbg, g);
                } 
                    
                break;
            case 5: // Creditos
                if (Button.estados[1] == 0){
                    g.drawImage(boxOption2, 62, 320);
                    pixelFont.drawString(130, 430,"PROGRAMAÇÃO");
                    pixelFont.drawString(150,460 , "Flavio Alves. Grabriel Faggione");
                    pixelFont.drawString(130, 490, "ARTE e DESIGN");
                    pixelFont.drawString(150, 520, "George Dourado");
                    pixelFont.drawString(130, 550, "OST . EFEITOS SONOROS");
                    pixelFont.drawString(150, 580, "Alexander Rodrigues");
                    pixelFont.drawString(130, 610, "ROTEIRO");
                    pixelFont.drawString(150, 640, "Jose Roberto Calderon");
                    pixelFont.drawString(130, 670, "PRODUÇÃO");
                    pixelFont.drawString(150, 700, "Flávio Alves. George Dourado");
                    btBack2.render(gc, sbg, g);}
                else {
                    g.drawImage(boxOption2, 62, 320);
                    pixelFont.drawString(130, 430,"PROGRAM");
                    pixelFont.drawString(150,460 , "Flavio Alves. Grabriel Faggione");
                    pixelFont.drawString(130, 490, "ART and DESIGN");
                    pixelFont.drawString(150, 520, "George Dourado. Jose R. Calderon");
                    pixelFont.drawString(130, 550, "OST . SOUND EFFECTS");
                    pixelFont.drawString(150, 580, "Alexander Rodrigues");
                    pixelFont.drawString(130, 610, "SCRIPT");
                    pixelFont.drawString(150, 640, "Jose Roberto Calderon");
                    pixelFont.drawString(130, 670, "PRODUCTION");
                    pixelFont.drawString(150, 700, "Flavio Alves. George Dourado");
                    btBack2Eng.render(gc, sbg, g);
                }
                break;
            case 6:// botao sobre
                if (Button.estados[1] == 0){
                    g.drawImage(boxOption2, 62, 320);
                    pixelFont.drawString(130, 430,"Doctor Disease é um game do gênero Bullet");
                    pixelFont.drawString(130, 450,"Hell onde o jogador trava batalhas contra");
                    pixelFont.drawString(130, 470,"doenças dentro do corpo humano");
                    pixelFont.drawString(130, 510,"Este game foi desenvolvido como projeto de");
                    pixelFont.drawString(130, 530,"conclusão do 3o Semestre do Curso de");
                    pixelFont.drawString(130, 550,"Tecnologia em Jogos Digitais da Faculdade");
                    pixelFont.drawString(130, 580,"de Tecnologia de Carapicuiba.");
                    pixelFont.drawString(130, 640,"Classificação Indicativa 10 anos");                    
                    btBack2.render(gc, sbg, g);}
                else{
                    g.drawImage(boxOption2, 62, 320);
                    pixelFont.drawString(130, 430,"Doctor Disease is a game of the genre Bullet");
                    pixelFont.drawString(130, 450,"Hell where the player fights battles against");
                    pixelFont.drawString(130, 470,"diseases inside the human body");
                    pixelFont.drawString(130, 510,"This game was developed as a project to");
                    pixelFont.drawString(130, 530,"conclude the 3th Semester of the Technology ");
                    pixelFont.drawString(130, 550,"in Digital Games course of the Carapicuiba");
                    pixelFont.drawString(130, 580,"Technology College");
                    pixelFont.drawString(130, 640,"Indicative Classification 10 years");
                    btBack2Eng.render(gc, sbg, g);
                }
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

