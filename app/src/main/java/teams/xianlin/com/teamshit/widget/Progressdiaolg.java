package teams.xianlin.com.teamshit.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import teams.xianlin.com.teamshit.R;

public class Progressdiaolg extends Dialog {
    private TextView title;
    private String txt;

    public Progressdiaolg(Context context, String txt) {
        super(context, R.style.progress_dialog);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.txt = txt;
        setDialogView(context, txt);
    }

    public void setDialogView(Context context, String txt) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_wait_progress, null);
        title = (TextView) view.findViewById(R.id.title);
        title.setText(txt);
        super.setContentView(view);
    }

    public void setTitle(String string) {
        title.setText(txt);
    }

//    public void show() {
//        if (!this.isShowing()) {
//            this.show();
//        }
//    }
//
//    public void cancel() {
//        if (this.isShowing()) {
//            this.cancel();
//        }
//    }

}
