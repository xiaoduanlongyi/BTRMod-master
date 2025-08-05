package btrmod.cards.attack.basic;

import basemod.helpers.TooltipInfo;
import btrmod.BTRMod;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import btrmod.util.KeywordInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static btrmod.util.CardTagEnum.*;

public class StrikeBocchi extends BaseCard {
    public static final String ID = makeID(StrikeBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int GROOVE = 2;
    private static final int UPG_GROOVE = 0;

    public StrikeBocchi() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setCustomVar("GRV", GROOVE, UPG_GROOVE);

        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CardTags.STRIKE);
        tags.add(BOCCHI);
        tags.add(GROOVE_GRANT);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();

        KeywordInfo BocchiInfo = BTRMod.keywords.get("BOCCHI");
        if (BocchiInfo != null) {
            tips.add(new TooltipInfo(
                    BocchiInfo.PROPER_NAME,
                    BocchiInfo.DESCRIPTION
            ));
        }

        return tips;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new StrikeBocchi();
    }
    }
