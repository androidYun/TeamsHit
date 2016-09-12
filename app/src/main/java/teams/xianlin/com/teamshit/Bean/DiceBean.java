package teams.xianlin.com.teamshit.Bean;

/**
 * Created by Administrator on 2016/9/10.
 */
public class DiceBean {
    private boolean isSlector;//是否选中  true  是  false  是不是

    private int diceCount;//骰子点数

    public boolean isSlector() {
        return isSlector;
    }

    public void setSlector(boolean slector) {
        isSlector = slector;
    }

    public int getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(int diceCount) {
        this.diceCount = diceCount;
    }
}
