package teams.xianlin.com.teamshit.RongYun.Provider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.ArrayList;

import io.rong.imkit.RongContext;
import io.rong.imkit.SendImageManager;
import io.rong.imkit.activity.PictureSelectorActivity;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.model.Conversation;
import teams.xianlin.com.teamshit.Activity.PassNoteActivity;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/8/23.
 */
public class TeamHitPassNoteProvider extends InputProvider.ExtendProvider {

    private static final int Pass_Note_Code = 101;

    public TeamHitPassNoteProvider(RongContext context) {
        super(context);
    }

    @Override
    public Drawable obtainPluginDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.default_head);
    }

    @Override
    public CharSequence obtainPluginTitle(Context context) {
        return context.getResources().getString(R.string.Pass_Note);
    }

    @Override
    public void onPluginClick(View view) {
        Intent PassNoteIntent = new Intent();
        PassNoteIntent.setClass(view.getContext(), PassNoteActivity.class);
        this.startActivityForResult(PassNoteIntent, Pass_Note_Code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            boolean sendOrigin = data.getBooleanExtra("sendOrigin", false);
            ArrayList list = data.getParcelableArrayListExtra("android.intent.extra.RETURN_RESULT");
            Conversation conversation = this.getCurrentConversation();
            SendImageManager.getInstance().sendImages(conversation.getConversationType(), conversation.getTargetId(), list, sendOrigin);
        }
    }
}
