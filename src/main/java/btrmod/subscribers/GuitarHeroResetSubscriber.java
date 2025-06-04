package btrmod.subscribers;

import basemod.interfaces.OnStartBattleSubscriber;
import btrmod.powers.BocchiAfraidPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class GuitarHeroResetSubscriber implements OnStartBattleSubscriber {

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        BocchiAfraidPower.guitarHeroTriggeredThisCombat = false;
    }
}