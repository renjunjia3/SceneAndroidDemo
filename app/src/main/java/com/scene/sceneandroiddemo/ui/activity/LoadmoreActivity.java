package com.scene.sceneandroiddemo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.scene.mylib.view.loadstateview.LoadStateView;
import com.scene.mylib.view.recyclerview.EndlessRecyclerOnScrollListener;
import com.scene.mylib.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.scene.mylib.view.recyclerview.OnRecyclerViewItemClickListener;
import com.scene.mylib.view.utils.RecyclerViewStateUtils;
import com.scene.mylib.view.weight.LoadingFooter;
import com.scene.sceneandroiddemo.BaseActivity;
import com.scene.sceneandroiddemo.R;
import com.scene.sceneandroiddemo.adapter.LoadMoreAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 加载更多
 * Created by scene on 16/01/26.
 */
public class LoadmoreActivity extends BaseActivity implements OnRecyclerViewItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.load_state_view)
    LoadStateView loadStateView;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;


    private List<String> dataList;
    PreviewHandler preHandler = new PreviewHandler(this);

    private LoadMoreAdapter adapter = null;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = null;

    //分页需要用到的
    public int page = 1;
    public int totalCount = 0;
    public static final int REQUEST_COUNT = 15;
    private boolean isLoading = false;

    //测试用的按钮
    @Bind(R.id.empty)
    Button empty;
    @Bind(R.id.error)
    Button error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        bindRecyclerView();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_loadmore;
    }


    private void initToolBar() {
        toolbar.setTitle("RecyclerView分页");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 绑定adapter 设置自定义的滚动监听
     */
    private void bindRecyclerView() {
        dataList = new ArrayList<>();
        adapter = new LoadMoreAdapter(mContext, dataList, recyclerView);
        adapter.setOnRecyclerViewItemClickListener(this);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(scrollListener);
        refreshLayout.setOnRefreshListener(this);

        loadStateView.showLoading();
        reqData();
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        page = 1;
        reqData();
    }

    /**
     * 加载更多
     */
    EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);

            if (state == LoadingFooter.State.Loading || isLoading) {
                Log.d("@scene", "the state is Loading, just wait..");
                return;
            }
            if (dataList.size() < totalCount) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(LoadmoreActivity.this, recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                page++;
                reqData();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(LoadmoreActivity.this, recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击到底了");
                    }
                });
            }
        }
    };
    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(LoadmoreActivity.this, recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            reqData();
        }
    };

    /**
     * 自定义的监听
     *
     * @param position
     */
    @Override
    public void onRecyclerViewItemClick(int position) {
        showToast("点击的是：" + dataList.get(position));
    }

    /**
     * 模拟获取数据与展示
     */
    private void reqData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preHandler.sendEmptyMessage(-1);
            }
        }, 3000);
    }

    /**
     * 模拟请求之后的数据处理
     */
    private class PreviewHandler extends Handler {

        private WeakReference<LoadmoreActivity> ref;

        PreviewHandler(LoadmoreActivity activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final LoadmoreActivity activity = ref.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            loadStateView.showContent();
            isLoading = false;
            refreshLayout.setRefreshing(false);
            switch (msg.what) {
                case -1://正常状态
                    totalCount = 64;
                    if (page == 1) {
                        dataList.clear();
                    }
                    //模拟组装15个数据
                    for (int i = 0; i < 15; i++) {
                        if (dataList.size() >= totalCount) {
                            break;
                        }
                        dataList.add("item" + ((15 * (page - 1)) + (i + 1)));
                    }
                    headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
                    RecyclerViewStateUtils.setFooterViewState(activity.recyclerView, LoadingFooter.State.Normal);
                    break;
                case -2:
                    headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
                    break;
                case -3://失败时候的处理方式处理
                    RecyclerViewStateUtils.setFooterViewState(activity, activity.recyclerView, REQUEST_COUNT, LoadingFooter.State.NetWorkError, LoadmoreActivity.this.mFooterClick);
                    break;
            }
        }
    }

    /**
     * 以下2个方法是测试显示空和错误的布局
     */

    @OnClick(R.id.error)
    public void onClickError() {
        /**
         * 显示错误页面有5个参数
         * 1、提示图片（可以是drawable和resId）
         * 2、提示文字标题
         * 3、提示内容
         * 4、按钮文字
         * 5、按钮的监听
         */
        loadStateView.showError(R.drawable.btn_crop_operator, "网络异常", "请检查网络连接", "点击重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStateView.showLoading();
                //然后在这儿重新请求数据
            }
        });
    }

    @OnClick(R.id.empty)
    public void onClickEmpty() {
        /**
         * 显示空页面有4个参数
         */
        loadStateView.showEmpty(R.drawable.btn_crop_operator, "没有数据", "这儿没有什么东西");
    }
}
