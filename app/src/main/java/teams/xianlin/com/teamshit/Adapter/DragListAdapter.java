package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.widget.GraffitiBoard.DrawZoomImageView;

public class DragListAdapter extends ArrayAdapter<String> {
    private List<String> listPath;

    private Context mContext;

    private LayoutInflater layoutInflater;

    public DragListAdapter(List<String> listPath, Context mContext) {
        super(mContext, 0, listPath);
        this.listPath = listPath;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);

    }

    public void remove(String item) {
        listPath.remove(item);
    }

    public void add(int position, String item) {
        this.listPath.add(position, item);
        notifyDataSetChanged();
    }

    public List<String> getList() {
        return listPath;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        LogUtil.d("我知道");
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_item_pass_note, null, false);
            viewHolder.img_content = (ImageView) convertView.findViewById(R.id.img_content);
            viewHolder.img_cancle = (ImageView) convertView.findViewById(R.id.img_cancle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load("file://" + listPath.get(position)).into(viewHolder.img_content);

        return convertView;
    }

    class ViewHolder {
        ImageView img_content;
        ImageView img_cancle;
    }
}