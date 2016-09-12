package teams.xianlin.com.teamshit.MainFragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ConversationListStaticFragment extends BaseFragment {
    public static ConversationListStaticFragment getInstance() {
        return new ConversationListStaticFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversationlist, container, false);

        ConversationListFragment fragment = (ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.conversationlist);

        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);
        return view;
    }

    @Override
    protected void initialView() {

    }

    @Override
    protected void initialData() {

    }

    @Override
    protected void inflateView() {

    }
}
