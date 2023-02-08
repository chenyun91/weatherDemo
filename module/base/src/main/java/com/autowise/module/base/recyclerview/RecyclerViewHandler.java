package com.autowise.module.base.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.recyclerview.widget.RecyclerView;



public class RecyclerViewHandler implements LoadMoreHandler {

    private RecyclerAdapterWithHF mRecyclerAdapter;
    private View mFooter;

    @Override
    public boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        boolean hasInit = false;
        mRecyclerAdapter = (RecyclerAdapterWithHF) recyclerView.getAdapter();
        if (loadMoreView != null) {
            final Context context = recyclerView.getContext().getApplicationContext();
            loadMoreView.init(new ILoadMoreViewFactory.FootViewAdder() {

                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId, recyclerView, false);
                    mFooter = view;
                    return addFootView(view);
                }

                @Override
                public View addFootView(View view) {
                    mRecyclerAdapter.addFooter(view);
                    return view;
                }
            }, onClickLoadMoreListener);
            hasInit = true;
        }
        return hasInit;
    }

    @Override
    public void addFooter() {
        if (mRecyclerAdapter.getFootSize() <= 0 && null != mFooter) {
            mRecyclerAdapter.addFooter(mFooter);
        }
    }

    @Override
    public void removeFooter() {
        if (mRecyclerAdapter.getFootSize() > 0 && null != mFooter) {
            mRecyclerAdapter.removeFooter(mFooter);
        }
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(onScrollBottomListener));
    }

    /**
     * 滑动监听
     */
    private static class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public RecyclerViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && isScrollBottom(recyclerView)) {
//            if (newState == RecyclerView.SCROLL_STATE_IDLE && isSlideToBottom(recyclerView)) {
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScrollBottom();
                }
            }
        }

        private boolean isScrollBottom(RecyclerView recyclerView) {
            return !isCanScrollVertically(recyclerView);
        }

        public boolean isSlideToBottom(RecyclerView recyclerView) {
            if (recyclerView == null) return false;
            if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                    >= recyclerView.computeVerticalScrollRange())
                return true;
            return false;
        }

        private boolean isCanScrollVertically(RecyclerView recyclerView) {
            return recyclerView.canScrollVertically(1);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }

    }

}
