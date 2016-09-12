package teams.xianlin.com.teamshit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;

/**
 * Created by Administrator on 2016/7/12.
 */
public class TimeTextView extends TextView implements View.OnClickListener {
    private long lenght = 60 * 1000;// 倒计时长度,这里给了默认60秒
    private String textafter = "秒后重新获取";
    private String textbefore = "点击获取验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    private Context mContext;
    Map<String, Long> map = new HashMap<String, Long>();

    public TimeTextView(Context context) {
        super(context);
        setOnClickListener(this);

    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeTextView.this.setText(time / 1000 + textafter);
            time -= 1000;
            if (time < 0) {
                TimeTextView.this.setEnabled(true);
                TimeTextView.this.setText(textbefore);
                clearTimer();
            }
        }

        ;
    };

    private void initTimer() {
        time = lenght;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                Log.e("yung", time / 1000 + "");
                handler.sendEmptyMessage(0x01);
            }
        };
    }

    private void clearTimer() {
        Toast.makeText(mContext, "计时结束", Toast.LENGTH_SHORT).show();
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeTextView) {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null)
            mOnclickListener.onClick(v);
//        initTimer();
//        this.setText(time / 1000 + textafter);
//        this.setEnabled(false);
//        t.schedule(tt, 0, 1000);
        // t.scheduleAtFixedRate(task, delay, period);
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (TeamHitContext.map == null)
            TeamHitContext.map = new HashMap<String, Long>();
        TeamHitContext.map.put(TIME, time);
        TeamHitContext.map.put(CTIME, System.currentTimeMillis());
        clearTimer();
        Log.e("yung", "onDestroy");
    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate(Bundle bundle) {
        Log.e("yung", TeamHitContext.map + "");
        if (TeamHitContext.map == null)
            return;
        if (TeamHitContext.map.size() <= 0)// 这里表示没有上次未完成的计时
            return;
        long time = System.currentTimeMillis() - TeamHitContext.map.get(CTIME)
                - TeamHitContext.map.get(TIME);
        TeamHitContext.map.clear();
        if (time > 0)
            return;
        else {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setText(time + textafter);
            this.setEnabled(false);
        }
    }

    /**
     * 设置计时时候显示的文本
     */
    public TimeTextView setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    /**
     * 设置点击之前的文本
     */
    public TimeTextView setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param lenght 时间 默认毫秒
     * @return
     */
    public TimeTextView setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }

    public void setStartTime() {
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        t.schedule(tt, 0, 1000);
    }
}
