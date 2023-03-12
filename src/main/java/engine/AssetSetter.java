package engine;

import entity.Entity;
import entity.NPC_OldMan;
import monster.GreenSlime;
import object.*;

import static engine.GameEngine.*;

public class AssetSetter {

    public void setEverything(){
        setObject();
        setNPC();
        setMonster();
    }

    private void setMonster() {
        set(monster, 0, new GreenSlime(), 23, 36);
        set(monster,1,new GreenSlime(), 23, 38);
    }

    public void setObject(){
        set(obj, 0, new Door(), 21, 22);
        set(obj,1, new Door(), 22, 23);
        set(obj,2, new Key(), 23, 7);
    }

    public void setNPC(){
        set(npc,0, new NPC_OldMan(),21,21);
        set(npc,1, new NPC_OldMan(),19,21);
        set(npc,2, new NPC_OldMan(),21,19);
    }

    private void set(Entity[] list, int index, Entity entity, int worldX, int worldY){
        list[index] = entity;
        list[index].worldX = worldX * tileSize;
        list[index].worldY = worldY * tileSize;
    }

}
