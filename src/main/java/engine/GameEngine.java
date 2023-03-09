package engine;

import UI.UI;
import desktop.KeyHandler;
import engine.loop.GameLoop;
import entity.CollisionChecker;
import entity.Entity;
import entity.Player;
import graphics.Screen;
import graphics.State;
import object.SuperObject;
import tile.TileManager;

public final class GameEngine{

    // SCREEN SETTINGS
    static final int originalTileSizes = 16; // 16x16 Tile
    public static final int tileSize = originalTileSizes * 3; // 48x48 Tile
    public static final int maxScreenRow = 16;
    public static final int maxScreenCol = 12;
    public static final int screenWidth = tileSize * maxScreenRow; // 768 pixels
    public static final int screenHeight = tileSize * maxScreenCol; // 576 pixels


    // SYSTEM
    public static UI ui = new UI();
    Sound soundEffect = new Sound(); // SOUND
    public static KeyHandler keyH = new KeyHandler();
    static Sound backgroundMusic = new Sound(); // SOUND
    public static TileManager tileManager = new TileManager();
    public static AssetSetter assetSetter = new AssetSetter();
    public static CollisionChecker colChecker = new CollisionChecker();
    // GAME STATE
    public static State gameState;
    // EVENT
    public static EventHandler eventHandler = new EventHandler();


    // ENTITY & OBJECT
    public static Player player;
    public static SuperObject[] obj = new SuperObject[10];
    public static Entity[] npc = new Entity[10];

    public GameLoop gameLoop;
    public Screen screen;

    public GameEngine(Screen screen, GameLoop.Factory factory) {
        this.screen = screen;
        gameLoop = factory.create(this::onUpdate, this::onRender);
        player = new Player(this, keyH);

        setUpGame();
        gameLoop.start();
    }

    public void setUpGame(){
        gameState = State.TITLE;
        assetSetter.setObject();
        assetSetter.setNPC();
//        playBackgroundMusic(5);
    }

    public void onUpdate(){
        // PLAY
        if (gameState.equals(State.PLAY)) {
            // PLAYER
            player.update(screen.input());
            // NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
    }

    public void onRender(){

        screen.render(graphics -> {

            // TITLE SCREEN
            if (gameState.equals(State.TITLE)){
                ui.draw(graphics);
            }
            else {

                // TILE
                tileManager.draw(graphics);

                // OBJECT
                for (SuperObject object : obj) {
                    if (object != null) {
                        object.draw(graphics, this);
                    }
                }

                // NPC
                for (Entity entity : npc) {
                    if (entity != null) {
                        entity.render(graphics);
                    }
                }

                // PLAYER
                player.draw(graphics);

                // UI
                ui.draw(graphics);
            }


            graphics.dispose();
        });

    }

    public static void playBackgroundMusic(int i){
        backgroundMusic.setFile(i);
        backgroundMusic.play();
        backgroundMusic.loop();
    }

    public void playSE(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public static void stopBackgroundMusic(){
        backgroundMusic.stop();
    }


    public static void changeGameState() {
        if (GameEngine.gameState.equals(State.PLAY)){
            GameEngine.gameState = State.PAUSE;
        } else if (GameEngine.gameState.equals(State.PAUSE)) {
            GameEngine.gameState = State.PLAY;
        }
    }


}
