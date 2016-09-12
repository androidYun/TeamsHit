package teams.xianlin.com.teamshit.Presenter.Base;

/**
 * Created by Administrator on 2016/8/8.
 */

public interface IBasePsenter<T> {
    void onRespSucess(T t);

    void onRespFail(String errorStr, int RespCode, int errorCode);

}
