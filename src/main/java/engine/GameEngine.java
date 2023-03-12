package engine;

import UI.UI;
import desktop.KeyManager;
import engine.loop.GameLoop;
import entity.CollisionChecker;
import entity.Entity;
import entity.Player;
import graphics.Screen;
import graphics.State;
import tile.TileManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public static KeyManager keyM = new KeyManager();
    static Sound backgroundMusic = new Sound(); // SOUND
    public static TileManager tileManager = new TileManager();
    public static AssetSetter assetSetter = new AssetSetter();
    public static CollisionChecker colChecker = new CollisionChecker();
    // GAME STATE
    public static State gameState;
    // EVENT
    public static EventManager eventManager = new EventManager();


    // ENTITY & OBJECT
    public static Player player;
    public static Entity[] obj = new Entity[10];
    public static Entity[] npc = new Entity[10];
    public static Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    public GameLoop gameLoop;
    public Screen screen;

    public GameEngine(Screen screen, GameLoop.Factory factory) {
        this.screen = screen;
        gameLoop = factory.create(this::onUpdate, this::onRender);
        player = new Player(this, keyM);

        setUpGame();
        gameLoop.start();
    }

    public void setUpGame(){
        gameState = State.TITLE;
        assetSetter.setEverything();
//        playBackgroundMusic(5);
    }

    public void onUpdate(){
        // PLAY
        if (gameState.equals(State.PLAY)) {
            // PLAYER
            player.update(screen.input());
            entityUpdate(npc);
            entityUpdate(monster);
        }
    }

    private static void entityUpdate(Entity[] monster) {
        for (Entity entity : monster){
            if (entity != null){
                entity.update();
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

                // PLAYER
                player.draw(graphics);
                entityList.add(player);

                // ADD ENTITIES TO THE LIST
                addEntityToEntityList(npc);
                addEntityToEntityList(obj);
                addEntityToEntityList(monster);

                // SORT
                Collections.sort(entityList, new Comparator<Entity>() {
                    @Override
                    public int compare(Entity o1, Entity o2) {
                        return Integer.compare(o1.worldY, o2.worldY);
                    }
                });
                // DRAW ENTITIES
                for (Entity entity : entityList) {
                    entity.render(graphics);
                }
                // EMPTY ENTITY LIST
                for (int i = 0; i < entityList.size(); i++) {
                    entityList.remove(i);
                }

                // UI
                ui.draw(graphics);
            }


            graphics.dispose();
        });

    }

    private void addEntityToEntityList(Entity[] obj) {
        for (Entity value : obj) {
            if (value != null) {
                entityList.add(value);
            }
        }
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


    public static void pause_play() {
        if (GameEngine.gameState.equals(State.PLAY)){
            GameEngine.gameState = State.PAUSE;
        } else if (GameEngine.gameState.equals(State.PAUSE)) {
            GameEngine.gameState = State.PLAY;
        }
    }


}
