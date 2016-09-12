package teams.xianlin.com.teamshit.Presenter;

public interface Presenter<V> {

	void attchView(V mvpView);

	void detachView(V mvpView);
}
