package com.zk.design.fragment;

import android.content.Context;

/**
 * Created by zhangkai
 * Description:为底部导航栏设置的fragment接口
 *
 * @date 2019/7/9
 * QQ:405547628
 */
public interface IFragment {
    /**
     * 获取底部导航栏标题
     *
     * @return
     */
    String getTitle();

    /**
     * 获取底部导航栏已选中图片
     *
     * @return
     */
    int getActiveIconRes();

    /**
     * 获取底部导航栏未选中图片
     *
     * @return
     */
    int getInActiveIconRes();

    /**
     * 同一个导航栏item被连续点击时调用，可用于回到顶部，刷新列表等
     */
    void onContinueClick();

    /**
     * 是否拦截点击事件
     *
     * @return
     */
    boolean onInterceptClick(Context context);
}
