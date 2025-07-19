package btrmod.effects;

import btrmod.events.BtrStartingEventRoom;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class StartingRelicEffect extends AbstractGameEffect {
    @Override
    public void update() {
        this.isDone = true;

        // 清除现有的对话选项
        RoomEventDialog.optionList.clear();

        // 获取当前节点
        MapRoomNode currNode = AbstractDungeon.getCurrMapNode();

        // 创建新节点，使用相同的坐标
        MapRoomNode node = new MapRoomNode(currNode.x, currNode.y);
        node.setRoom(new BtrStartingEventRoom());

        // 复制边缘连接
        for (MapEdge e : currNode.getEdges()) {
            node.addEdge(e);
        }

        // 设置并进入新房间
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.setCurrMapNode(node);
        AbstractDungeon.getCurrRoom().onPlayerEntry();
        AbstractDungeon.scene.nextRoom(node.room);
        AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // 不需要渲染任何东西
    }

    @Override
    public void dispose() {
        // 不需要清理资源
    }
}