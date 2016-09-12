package teams.xianlin.com.teamshit.widget.DialogUtils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.RoundImageView;

/**
 * Created by Administrator on 2016/9/11.
 */
public class SelectorDiceNumberDialog extends Dialog implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.img_roll)
    RoundImageView imgRoll;
    @Bind(R.id.rbtn_one)
    RadioButton rbtnOne;
    @Bind(R.id.rbtn_two)
    RadioButton rbtnTwo;
    @Bind(R.id.rbtn_three)
    RadioButton rbtnThree;
    @Bind(R.id.rbtn_four)
    RadioButton rbtnFour;
    @Bind(R.id.rbtn_five)
    RadioButton rbtnFive;
    @Bind(R.id.rbtn_add_one)
    RadioButton rbtnAddOne;
    @Bind(R.id.img_dice_one)
    RoundImageView imgDiceOne;
    @Bind(R.id.img_dice_two)
    RoundImageView imgDiceTwo;
    @Bind(R.id.img_dice_three)
    RoundImageView imgDiceThree;
    @Bind(R.id.img_dice_four)
    RoundImageView imgDiceFour;
    @Bind(R.id.img_dice_five)
    RoundImageView imgDiceFive;
    @Bind(R.id.img_dice_sixten)
    RoundImageView imgDiceSixten;
    @Bind(R.id.rdg_dice_number)
    RadioGroup rdgDiceNumber;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private Context mContext;

    private int mDiceNumber = 6;//选择的点数

    private int mDiceCount;//骰子的个数

    public SelectorDiceNumberDialog(Context context) {
        this(context, 0);
    }

    public SelectorDiceNumberDialog(Context context, int themeResId) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        initialView();
        initialData();
        inlfateView();
    }


    private void initialView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_selector_dice_number_layout, null, false);
        ButterKnife.bind(this, inflate);
        this.setContentView(inflate);
    }

    private void initialData() {
    }

    private void inlfateView() {
    }

    @OnClick({R.id.img_dice_one, R.id.img_dice_two, R.id.img_dice_three, R.id.img_dice_four, R.id.img_dice_five, R.id.img_dice_sixten})
    @Override
    public void onClick(View view) {
        String mClickDiceNumber = (String) view.getTag();
        if (StringUtils.isBlank(mClickDiceNumber) || mDiceNumber <= Integer.valueOf(mClickDiceNumber)) {
            ToastUtil.show(mContext, "选择的点数必须比上家点数大"+mClickDiceNumber);
            return;
        }
        switch (view.getId()) {
            case R.id.img_dice_one:
                mDiceNumber = 1;
                changeRollView(imgDiceOne);
                break;
            case R.id.img_dice_two:
                mDiceNumber = 2;
                changeRollView(imgDiceTwo);
                break;
            case R.id.img_dice_three:
                mDiceNumber = 3;
                changeRollView(imgDiceThree);
                break;
            case R.id.img_dice_four:
                mDiceNumber = 4;
                changeRollView(imgDiceFour);
                break;
            case R.id.img_dice_five:
                mDiceNumber = 5;
                changeRollView(imgDiceFive);
                break;
            case R.id.img_dice_sixten:
                mDiceNumber = 6;
                changeRollView(imgDiceSixten);
                break;
        }
    }

    public void changeRollView(RoundImageView imgDice) {
        imgDiceOne.setNormal();
        imgDiceTwo.setNormal();
        imgDiceThree.setNormal();
        imgDiceFour.setNormal();
        imgDiceFive.setNormal();
        imgDiceSixten.setNormal();
        imgDice.setSelector();
    }


    @OnCheckedChanged({R.id.rbtn_add_one, R.id.rbtn_one, R.id.rbtn_two, R.id.rbtn_three, R.id.rbtn_four, R.id.rbtn_five})
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.rbtn_add_one:
                mDiceCount++;
                break;
            case R.id.rbtn_one:
                mDiceCount = mDiceCount;
                break;
            case R.id.rbtn_two:
                mDiceCount += 2;
                break;
            case R.id.rbtn_three:
                mDiceCount += 3;
                break;
            case R.id.rbtn_four:
                mDiceCount += 4;
                break;
            case R.id.rbtn_five:
                mDiceCount += 5;
                break;
        }
    }
}
