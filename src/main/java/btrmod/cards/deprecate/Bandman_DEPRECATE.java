//package btrmod.cards.deprecate;
//
//import btrmod.cards.BaseCard;
//import btrmod.powers.GroovePower;
//import btrmod.util.CardStats;
//import com.badlogic.gdx.math.MathUtils;
//import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.common.*;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.DamageInfo;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//public class Bandman_DEPRECATE extends BaseCard {
//    public static final String ID = makeID(Bandman_DEPRECATE.class.getSimpleName());
//    private static final CardStats info = new CardStats(
//            CardColor.COLORLESS,
//            CardType.ATTACK,
//            CardRarity.SPECIAL,
//            CardTarget.ENEMY,
//            2
//    );
//
//    private static final int DAMAGE = 8;
//    private static final int UPG_DAMAGE = 2;
//    private static final int GROOVE_TO_DIVIDE = 1;
//    private static final int UPG_GROOVE_TO_DIVIDE = 0;
//
//    public Bandman_DEPRECATE() {
//        super(ID, info);
//
//        setDamage(DAMAGE, UPG_DAMAGE);
//        setMagic(GROOVE_TO_DIVIDE, UPG_GROOVE_TO_DIVIDE);
//        setExhaust(true);
//
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//
//        int goldToAdd = MathUtils.floor((float) getGrooveStacks() / magicNumber);
//
//        if (p.hasPower(GroovePower.POWER_ID)) {
//            addToBot(new RemoveSpecificPowerAction(p, p, GroovePower.POWER_ID));
//        }
//        if (goldToAdd > 0) {
//            addToBot(new GainGoldAction(goldToAdd));
//        }
//
//        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
//
//        CardCrawlGame.sound.play("BandMan");
//    }
//
//    private int getGrooveStacks()
//    {
//        int grooveStacks = 0;
//        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(GroovePower.POWER_ID)) {
//            grooveStacks = AbstractDungeon.player.getPower(GroovePower.POWER_ID).amount;
//        }
//
//        return grooveStacks;
//    }
//
//    @Override
//    public AbstractCard makeCopy() { //Optional
//        return new Bandman_DEPRECATE();
//    }
//}
