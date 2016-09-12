package teams.xianlin.com.teamshit.widget.DialogUtils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import teams.xianlin.com.teamshit.Interface.SelectorSingleCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;

/**
 * Created by Administrator on 2016/9/7.
 */
public class SelectorSingleItemDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.lvi_content)
    ListView lviContent;
    @Bind(R.id.layout)
    LinearLayout layout;
    private String[] datas;

    private Context mContext;

    private SelectorSingleCallBack selectorSingleCallBack;

    private int selectorType;

    public SelectorSingleItemDialog(Context context, int selectorType, SelectorSingleCallBack selectorSingleCallBack, String[] datas) {
        super(context, R.style.select_single_dialog_style);
        this.datas = datas;
        this.mContext = context;
        this.selectorType = selectorType;
        this.selectorSingleCallBack = selectorSingleCallBack;
        initialView();
        InflateData();
    }

    private void initialView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_selector_single_item, null, false);
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setCancelable(true);
    }

    private void InflateData() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, R.layout.list_item_selector_single_item, R.id.txt_single_item, datas);
        lviContent.setAdapter(arrayAdapter);
    }

    @OnItemClick({R.id.lvi_content})
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (view.getId()) {
            case R.id.txt_single_item:
                selectorSingleCallBack.SelectorSingleOnSuccess(selectorType, i, datas[i]);
                break;
        }
        this.dismiss();
    }

    @OnClick({R.id.layout})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout:
                this.dismiss();
                break;
        }
    }

    public void setTitle(String title) {
        txtTitle.setText("" + title);
    }
}
