package com.zk.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zk.design.R;
import com.zk.design.fragment.IFragment;

import java.util.List;

/**
 * Created by zhangkai
 * Description:底部导航栏
 *
 * @date 2019/7/9
 * QQ:405547628
 */
public class BottomNavigationView extends LinearLayout implements View.OnClickListener {
    //导航栏item的字体大小
    private int activeTextSize, inActiveTextSize;
    //导航栏item的字体颜色
    private int activeTextColor, inActiveTextColor;
    //导航栏item顶部和底部的间距
    private int activeItemPaddingTop, inActiveItemPaddingTop, activeItemPaddingBottom, inActiveItemPaddingBottom;
    //导航栏item上的提示（点/数字/文字等）的位置，以item的图片的右边距和上边距为准
    private int tipMarginLeft, tipMarginTop;
    //导航栏item上的提示文字的大小
    private int tipTextSize;
    //导航栏item上的提示文字的颜色
    private int tipTextColor;
    //导航栏item上的提示文字或点的背景颜色
    private int tipBgColor;
    //导航栏item上的提示为文字时的背景圆角半径 为圆点时的圆点半径
    private int tipBgCornerRadius, tipDotRadius;
    private FragmentManager fragmentManager;
    private int containerId;
    private List<IFragment> fragments;
    //当前页的索引
    private int curIndex = -1;

    public BottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationView);
        activeTextColor = array.getColor(R.styleable.BottomNavigationView_activeTextColor, getResources().getColor(android.R.color.black));
        inActiveTextColor = array.getColor(R.styleable.BottomNavigationView_inActiveTextColor, getResources().getColor(android.R.color.holo_blue_light));
        activeTextSize = array.getDimensionPixelSize(R.styleable.BottomNavigationView_activeTextSize, 24);
        inActiveTextSize = array.getDimensionPixelSize(R.styleable.BottomNavigationView_inActiveTextSize, activeTextSize);
        activeItemPaddingTop = array.getDimensionPixelSize(R.styleable.BottomNavigationView_activeItemPaddingTop, 12);
        inActiveItemPaddingTop = array.getDimensionPixelSize(R.styleable.BottomNavigationView_inActiveItemPaddingTop, activeItemPaddingTop);
        activeItemPaddingBottom = array.getDimensionPixelSize(R.styleable.BottomNavigationView_activeItemPaddingBottom, 12);
        inActiveItemPaddingBottom = array.getDimensionPixelSize(R.styleable.BottomNavigationView_inActiveItemPaddingBottom, activeItemPaddingBottom);
        tipMarginLeft = array.getDimensionPixelSize(R.styleable.BottomNavigationView_tipMarginLeft, 0);
        tipMarginTop = array.getDimensionPixelSize(R.styleable.BottomNavigationView_tipMarginTop, 0);
        tipTextSize = array.getDimensionPixelSize(R.styleable.BottomNavigationView_tipTextSize, 0);
        tipTextColor = array.getColor(R.styleable.BottomNavigationView_tipTextColor, getResources().getColor(android.R.color.white));
        tipBgColor = array.getColor(R.styleable.BottomNavigationView_tipBgColor, getResources().getColor(android.R.color.holo_red_light));
        tipBgCornerRadius = array.getDimensionPixelOffset(R.styleable.BottomNavigationView_tipBgCornerRadius, 0);
        tipDotRadius = array.getDimensionPixelOffset(R.styleable.BottomNavigationView_tipDotRadius, 0);
        array.recycle();
    }

    public void initFragments(FragmentManager fragmentManager, int containerId, List<IFragment> fragments) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        this.fragments = fragments;
        initItemViews();
        switchFragment(0);
    }

    /**
     * 初始化底部导航栏的所有item
     */
    private void initItemViews() {
        if (fragments == null || fragments.isEmpty()) {
            return;
        }

        removeAllViews();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        int count = fragments.size();
        for (int i = 0; i < count; i++) {
            IFragment fragment = fragments.get(i);
            BottomNavigationItemView itemView = new BottomNavigationItemView.Builder(getContext())
                    .setTitle(fragment.getTitle())
                    .setIcon(fragment.getActiveIconRes(), fragment.getInActiveIconRes())
                    .setTitleTextColor(activeTextColor, inActiveTextColor)
                    .setTitleTextSize(activeTextSize, inActiveTextSize)
                    .setPaddingTopAndBottom(activeItemPaddingTop, inActiveItemPaddingTop, activeItemPaddingBottom, inActiveItemPaddingBottom)
                    .setTipMarginLeftAndTop(tipMarginLeft, tipMarginTop)
                    .setTipTextSize(tipTextSize)
                    .setTipTextColor(tipTextColor)
                    .setTipBgColor(tipBgColor)
                    .setTipBgCornerRadius(tipBgCornerRadius)
                    .setTipDotRadius(tipDotRadius)
                    .build();
            itemView.setLayoutParams(layoutParams);
            itemView.setId(i);
            itemView.setOnClickListener(this);
            addView(itemView);
        }
    }

    public void switchFragment(int index) {
        if (fragments == null || index >= fragments.size()) {
            return;
        }

        //当前的fragment
        IFragment currentFragment = curIndex < 0 ? null : fragments.get(curIndex);
        //将要跳转的fragment
        IFragment nextFragment = fragments.get(index);
        if (curIndex == index) {
            //同一个导航栏item被连续点击时调用
            if (currentFragment != null) {
                currentFragment.onContinueClick();
            }
            return;
        }

        //检测是否拦截点击事件
        if (nextFragment.onInterceptClick(getContext())) {
            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment != null) {
            int curIndex = fragments.indexOf(currentFragment);
            ((BottomNavigationItemView) getChildAt(curIndex)).setActive(false);
            if (((Fragment) currentFragment).isAdded()) {
                transaction.hide((Fragment) currentFragment);
            }
        }
        ((BottomNavigationItemView) getChildAt(index)).setActive(true);
        if (((Fragment) nextFragment).isAdded()) {
            transaction.show((Fragment) nextFragment);
        } else {
            transaction.add(containerId, (Fragment) nextFragment, nextFragment.getClass().getSimpleName());
        }
        curIndex = index;
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switchFragment(v.getId());
    }

    public void showTip(int index, String tipMessage) {
        if (fragments == null || index >= fragments.size()) {
            return;
        }

        ((BottomNavigationItemView) getChildAt(index)).showTip(tipMessage);
    }

    public void showTipDot(int index, boolean showTipDot) {
        if (fragments == null || index >= fragments.size()) {
            return;
        }

        ((BottomNavigationItemView) getChildAt(index)).showTipDot(showTipDot);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.childrenStates = new SparseArray();
        savedState.curIndex = curIndex;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).saveHierarchyState(savedState.childrenStates);
        }
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).restoreHierarchyState(savedState.childrenStates);
        }
        switchFragment(savedState.curIndex);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    public static class SavedState extends BaseSavedState {
        SparseArray childrenStates;
        int curIndex;

        public SavedState(Parcel source) {
            super(source);
        }

        private SavedState(Parcel source, ClassLoader loader) {
            super(source);
            childrenStates = source.readSparseArray(loader);
            curIndex = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeSparseArray(childrenStates);
            out.writeInt(curIndex);
        }

        public static final ClassLoaderCreator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                return new SavedState(source, loader);
            }

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
