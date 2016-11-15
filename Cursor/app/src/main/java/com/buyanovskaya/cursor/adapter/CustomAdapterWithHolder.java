package com.buyanovskaya.cursor.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.buyanovskaya.cursor.R;
import com.buyanovskaya.cursor.model.User;

import java.util.ArrayList;

public class CustomAdapterWithHolder extends ArrayAdapter<User> {

    Context mContext;
    int layoutResourceId;
    ArrayList<User> dataList = new ArrayList<User>();

    public CustomAdapterWithHolder(Context mContext, int layoutResourceId, ArrayList<User> item) {
        super(mContext, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.dataList = item;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public User getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolder;
        if (convertView == null) {

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.tvText = (TextView) convertView.findViewById(R.id.tvText);
            convertView.setTag(viewHolder);

        } else {
            User userInfo = (User) getItem(position);
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        User user = dataList.get(position);
        if (dataList != null) {
            viewHolder.tvText.setText(user.getUsers());
        }

        return convertView;

    }

    static class ViewHolderItem {
        TextView tvText;
    }

}


