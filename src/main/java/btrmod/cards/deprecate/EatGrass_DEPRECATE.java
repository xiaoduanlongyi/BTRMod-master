//package btrmod.cards.deprecate;
//
//import btrmod.cards.BaseCard;
//import btrmod.util.CardStats;
//import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.common.DamageAction;
//import com.megacrit.cardcrawl.actions.common.LoseHPAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.DamageInfo;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//public class EatGrass_DEPRECATE extends BaseCard {
//    public static final String ID = makeID(EatGrass_DEPRECATE.class.getSimpleName());
//    private static final CardStats info = new CardStats(
//            CardColor.COLORLESS,
//            CardType.ATTACK,
//            CardRarity.SPECIAL,
//            CardTarget.ENEMY,
//            0
//    );
//
//    private static final int DAMAGE = 5;
//    private static final int UPG_DAMAGE = 2;
//    private static final int MAGIC = 2;
//
//    public EatGrass_DEPRECATE() {
//        super(ID, info);
//
//        setDamage(DAMAGE, UPG_DAMAGE);
//        setMagic(MAGIC);
//        setExhaust(true);
//
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        this.addToBot(new LoseHPAction(p, p, this.magicNumber));
//        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
//
//        CardCrawlGame.sound.play("RyoEatGrass");
//    }
//
//    @Override
//    public AbstractCard makeCopy() { //Optional
//        return new EatGrass_DEPRECATE();
//    }
//}
