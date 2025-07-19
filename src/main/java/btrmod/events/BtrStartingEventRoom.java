package btrmod.events;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.EventRoom;

public class BtrStartingEventRoom extends EventRoom {
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = new BtrStartingEvent();
        this.event.onEnterRoom();
    }
}
