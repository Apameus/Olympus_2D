package engine;

import entity.Entity;
import entity.NPC_OldMan;
import object.*;

import java.awt.*;
import java.lang.reflect.Array;

import static engine.GameEngine.*;

public class AssetSetter {

    public void setObject(){
        set(0, new Door(), 21, 22);
        set(1, new Door(), 22, 23);
        set(3, new Key(), 23, 7);
//        set(1, new Key(), 23, 40);
//        set(2, new Key(), 38, 8);
//        set(3, new Door(), 10, 12);
//        set(4, new Door(), 8, 28);
//        set(5, new Door(), 12, 23);
//        set(6, new Chest(), 10, 9);
//        set(7, new Boots(), 37, 42);
    }

    public void setNPC(){
        npc[0] = new NPC_OldMan();
        npc[0].worldX = 21 * tileSize;
        npc[0].worldY = 21 * tileSize;

        npc[1] = new NPC_OldMan();
        npc[1].worldX = 19 * tileSize;
        npc[1].worldY = 21 * tileSize;

        npc[2] = new NPC_OldMan();
        npc[2].worldX = 21 * tileSize;
        npc[2].worldY = 19 * tileSize;
    }

    private void set(int index, Entity entity, int worldX, int worldY){
        obj[index] = entity;
        obj[index].worldX = worldX * tileSize;
        obj[index].worldY = worldY * tileSize;
    }

}
