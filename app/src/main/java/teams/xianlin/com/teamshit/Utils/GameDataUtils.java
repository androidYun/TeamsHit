package teams.xianlin.com.teamshit.Utils;

/**
 * Created by Administrator on 2016/9/9.
 */
public class GameDataUtils {
    public static GameDataUtils getInstance() {
        return createGameData.gameDataUtils;
    }

    static class createGameData {
        final static GameDataUtils gameDataUtils = new GameDataUtils();
    }

    public String[] getGameType() {
        String[] mGameType = {"21点", "吹牛"};
        return mGameType;
    }

    public String[] getGamePeople() {
        String[] mGamePeople = {"2", "3", "4", "5", "6"};
        return mGamePeople;
    }

    public String[] getGameRole() {
        String[] mGameRole = {"1 ,轮流叫出不超过桌面上所有头子的个数", "2 ,当你认为上家加的点数太大，就开TA吧！", "3 ,1点可以当其他使用，但有人叫了一点之后，就变成普通点"};
        return mGameRole;
    }

    public String[] getGameCoin() {
        String[] mGameCoin = {"50", "100", "150", "200"};
        return mGameCoin;
    }

    public String[] getGroupVerifition() {
        String[] groupVerifition = {
                "允许任何人加入", "不允许任何人加入"
        };
        return groupVerifition;
    }
}
