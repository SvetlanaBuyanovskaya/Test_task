package com.buyanovskaya.cursor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.ListView;

import com.buyanovskaya.cursor.R;
import com.buyanovskaya.cursor.db.UsersDataBase;
import com.buyanovskaya.cursor.adapter.CustomAdapterWithHolder;
import com.buyanovskaya.cursor.model.User;
import com.buyanovskaya.cursor.utils.AConstants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listView;
    private UsersDataBase usersDataBase;
    private CustomAdapterWithHolder listAdapter;
    private ArrayList<User> usersList;
    private boolean isLoading = false;
    private int totalNum = 1000;
    private int visibleItemsCount;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersList = new ArrayList<User>();
        usersDataBase = new UsersDataBase(this);
        initList();

        listAdapter = new CustomAdapterWithHolder(this, R.layout.item, usersList);
        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(listAdapter);
        fetchVisibleItemsDelayed(AConstants.DEFAULT_DELAY);

        listView.setOnScrollListener(scrollListener);

    }

    private void loadMore(int startId) {
        List<User> additionalUsers = usersDataBase.getElements(startId,
                visibleItemsCount > 0 ? visibleItemsCount : AConstants.DEFAULT_ITEMS_COUNT);
        usersList.addAll(additionalUsers);
        listAdapter.notifyDataSetChanged();
        isLoading = false;

    }

    private void initList() {
        usersDataBase.open();
        usersList = usersDataBase.getStartingData();

    }

    private void fetchVisibleItemsDelayed(long delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchVisibleItems();
            }
        }, delayMillis);
    }

    private void fetchVisibleItems() {
        int count = 0;

        for (int i = 0; i <= listView.getLastVisiblePosition(); i++) {
            if (listView.getChildAt(i) != null) {
                count++;
            }
        }
        visibleItemsCount = count;
    }

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            int position = firstVisibleItem + visibleItemCount;
            if (position >= totalItemCount && totalItemCount > 0 && totalItemCount < totalNum && !isLoading) {
                isLoading = true;
                loadMore(position);
            }
        }
    };
}



