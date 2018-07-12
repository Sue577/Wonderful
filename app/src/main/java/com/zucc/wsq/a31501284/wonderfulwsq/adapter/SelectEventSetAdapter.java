package com.zucc.wsq.a31501284.wonderfulwsq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.zucc.wsq.a31501284.wonderfulwsq.R;
import com.zucc.wsq.a31501284.wonderfulwsq.common.bean.EventSet;

import java.util.List;

/**
 * Created by dell on 2018/7/11.
 */
public class SelectEventSetAdapter extends BaseAdapter {

    private Context mContext;
    private List<EventSet> mEventSets;
    private int mSelectPosition;

    public SelectEventSetAdapter(Context context, List<EventSet> eventSets, int selectPosition) {
        mContext = context;
        mEventSets = eventSets;
        mSelectPosition = selectPosition;
    }

    @Override
    public int getCount() {
        return mEventSets.size();
    }

    @Override
    public Object getItem(int position) {
        return mEventSets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mEventSets.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        EventSet eventSet = mEventSets.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_event_set, parent, false);
            holder.ivEventSetIcon = (ImageView) convertView.findViewById(R.id.ivEventSetIcon);
            holder.tvEventSetName = (TextView) convertView.findViewById(R.id.tvEventSetName);
            holder.rbEventSet = (RadioButton) convertView.findViewById(R.id.rbEventSet);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvEventSetName.setText(eventSet.getName());
        holder.ivEventSetIcon.setImageResource(eventSet.getId() == 0 ? R.mipmap.ic_select_event_set_category : R.mipmap.ic_select_event_set_icon);
        holder.rbEventSet.setChecked(position == mSelectPosition);
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivEventSetIcon;
        private TextView tvEventSetName;
        private RadioButton rbEventSet;
    }

    public void setSelectPosition(int selectPosition) {
        mSelectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public int getSelectPosition() {
        return mSelectPosition;
    }
}
