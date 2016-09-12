package teams.xianlin.com.teamshit.Presenter.Base;


import android.app.Activity;
import android.content.Context;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.Presenter.Presenter;

public abstract class BasePresenter<V> implements Presenter<V> {

    private V mvpView;

    @Override
    public void attchView(V v) {
        // TODO Auto-generated method stub
        this.mvpView = v;
    }

    @Override
    public void detachView(V mvpView) {
        // TODO Auto-generated method stub
        this.mvpView = null;
    }

    public ErrorMsg creteErrorMsg(String errorStr, int RespCode, int errorCode) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setRespCode(RespCode);
        errorMsg.setErrorCode(errorCode);
        return errorMsg;
    }


}
