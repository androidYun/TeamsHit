package teams.xianlin.com.teamshit.VaryView;

import android.view.View;

import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.ProgressWheel;


/**
 * 作者： corer 时间： 2015/10/18. 功能：帮助切换错误，数据为空，正在加载的页面 修改：
 */
public class VaryViewHelper {
    /**
     * 切换不同视图的帮助类
     */
    public OverlapViewHelper mViewHelper;
    /**
     * 错误页面
     */
    public View mErrorView;
    /**
     * 正在加载页面
     */
    public View mLoadingView;
    /**
     * 数据为空的页面
     */
    public View mEmptyView;
    /**
     * 正在加载页面的进度环
     */
    public ProgressWheel mLoadingProgress;

    private OverlapViewHelper overlapViewHelper;

    public VaryViewHelper(View view) {
        this(new OverlapViewHelper(view));
        overlapViewHelper = new OverlapViewHelper(view);
    }

    public VaryViewHelper(OverlapViewHelper helper) {
        this.mViewHelper = helper;
    }

    public void setUpEmptyView(View view) {
        mEmptyView = view;
        mEmptyView.setClickable(true);
    }

    public void setUpErrorView(View view, View.OnClickListener listener) {
        mErrorView = view;
        mErrorView.setClickable(true);
        View btn = view.findViewById(R.id.vv_error_refresh);
        if (btn != null) {
            btn.setOnClickListener(listener);
        }
    }

    public void setUpLoadingView(View view) {
        mLoadingView = view;
        mLoadingView.setClickable(true);
        mLoadingProgress = (ProgressWheel) view
                .findViewById(R.id.vv_loading_progress);
    }

    public void showEmptyView() {
       // showParentView();
        mViewHelper.showCaseLayout(mEmptyView);
        stopProgressLoading();
    }

    public void showErrorView() {
        //showParentView();
        mViewHelper.showCaseLayout(mErrorView);
        stopProgressLoading();
    }

    public void showLoadingView() {
        //showParentView();
        mViewHelper.showCaseLayout(mLoadingView);
        startProgressLoading();
    }

    public void showDataView() {
       // showParentView();
        mViewHelper.restoreLayout();
        stopProgressLoading();
    }

    private void stopProgressLoading() {
        if (mLoadingProgress != null && mLoadingProgress.isSpinning()) {
            mLoadingProgress.stopSpinning();
        }
    }

    private void startProgressLoading() {
        if (mLoadingProgress != null && !mLoadingProgress.isSpinning()) {
            mLoadingProgress.spin();
        }
    }

    public void releaseVaryView() {
        mErrorView = null;
        mLoadingView = null;
        mEmptyView = null;
    }


    public static class Builder {
        private View mErrorView;
        private View mLoadingView;
        private View mEmptyView;
        private View mDataView;
        private View.OnClickListener mRefreshListener;

        public Builder setErrorView(View errorView) {
            mErrorView = errorView;
            return this;
        }

        public Builder setLoadingView(View loadingView) {
            mLoadingView = loadingView;
            return this;
        }

        public Builder setEmptyView(View emptyView) {
            mEmptyView = emptyView;
            return this;
        }

        public Builder setDataView(View dataView) {
            mDataView = dataView;
            return this;
        }

        public Builder setRefreshListener(View.OnClickListener refreshListener) {
            mRefreshListener = refreshListener;
            return this;
        }

        public VaryViewHelper build() {
            VaryViewHelper helper = new VaryViewHelper(mDataView);
            if (mEmptyView != null) {
                helper.setUpEmptyView(mEmptyView);
            }
            if (mErrorView != null) {
                helper.setUpErrorView(mErrorView, mRefreshListener);
            }
            if (mLoadingView != null) {
                helper.setUpLoadingView(mLoadingView);
            }
            return helper;
        }
    }

    public void showParentView() {
        overlapViewHelper.showParentShow();
    }
}
