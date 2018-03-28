package ic.aiczone.kmoesic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ic.aiczone.kmoesic.R;
import ic.aiczone.kmoesic.model.KamusModel;

/**
 * Created by aic on 27/03/18.
 */

public class KamusAdapter extends BaseAdapter {

    private ArrayList<KamusModel> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    private OnItemClickListener mOnItemClickListener;

    public KamusAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, KamusModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setData(ArrayList<KamusModel> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final KamusModel item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;

        return mData.size();
    }

    @Override
    public KamusModel getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.kamus_keys, null);
            holder.tvKey = convertView.findViewById(R.id.tvKey);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvKey.setText(mData.get(position).getKey());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, mData.get(position), position);
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView tvKey;
    }

}





