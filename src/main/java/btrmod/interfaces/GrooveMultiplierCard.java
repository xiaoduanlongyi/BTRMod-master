package btrmod.interfaces;

/**
 * 实现此接口的卡牌可以自定义 Groove 伤害倍数
 */
public interface GrooveMultiplierCard {
    /**
     * 获取此卡牌的 Groove 倍数
     * @return Groove 伤害倍数（1.0 = 100%）
     */
    float getGrooveMultiplier();
}