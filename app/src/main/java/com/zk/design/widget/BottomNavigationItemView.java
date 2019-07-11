package com.zk.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by zhangkai
 * Description:底部导航栏的item
 *
 * @date 2019/7/9
 * QQ:405547628
 */
public class BottomNavigationItemView extends View {
    private String title;
    private int activeTextSize, inActiveTextSize;
    private int activeTextColor, inActiveTextColor;
    private int activeItemPaddingTop, inActiveItemPaddingTop, activeItemPaddingBottom, inActiveItemPaddingBottom;
    private int tipMarginLeft, tipMarginTop;
    private int tipTextSize;
    private int tipTextColor;
    private int tipBgColor;
    private int tipBgCornerRadius, tipDotRadius;
    //当前是否处于已选中状态
    private boolean isActive;
    private Drawable inActiveIcon, activeIcon;
    private Paint paint;
    private boolean showTipDot;
    private String tipMessage;

    public BottomNavigationItemView(Builder builder) {
        super(builder.context);
        title = builder.title;
        activeTextSize = builder.activeTextSize;
        inActiveTextSize = builder.inActiveTextSize;
        activeTextColor = builder.activeTextColor;
        inActiveTextColor = builder.inActiveTextColor;
        if (builder.activeIconRes != 0) {
            activeIcon = getResources().getDrawable(builder.activeIconRes);
        }
        if (builder.inActiveIconRes != 0) {
            inActiveIcon = getResources().getDrawable(builder.inActiveIconRes);
        }
        activeItemPaddingTop = builder.activeItemPaddingTop;
        inActiveItemPaddingTop = builder.inActiveItemPaddingTop;
        activeItemPaddingBottom = builder.activeItemPaddingBottom;
        inActiveItemPaddingBottom = builder.inActiveItemPaddingBottom;
        tipMarginLeft = builder.tipMarginLeft;
        tipMarginTop = builder.tipMarginTop;
        tipTextSize = builder.tipTextSize;
        tipTextColor = builder.tipTextColor;
        tipBgColor = builder.tipBgColor;
        tipBgCornerRadius = builder.tipBgCornerRadius;
        tipDotRadius = builder.tipDotRadius;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        invalidate();
    }

    public void showTip(String tipMessage) {
        this.tipMessage = tipMessage;
        invalidate();
    }

    public void showTipDot(boolean showTipDot) {
        this.showTipDot = showTipDot;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint == null) {
            paint = new Paint();
        }
        paint.setAntiAlias(true);

        int iconWidth = isActive ? (activeIcon == null ? 0 : activeIcon.getIntrinsicWidth())
                : (inActiveIcon == null ? 0 : inActiveIcon.getIntrinsicWidth());
        int iconHeight = isActive ? (activeIcon == null ? 0 : activeIcon.getIntrinsicHeight())
                : (inActiveIcon == null ? 0 : inActiveIcon.getIntrinsicHeight());
        Drawable icon = isActive ? (activeIcon == null ? null : activeIcon)
                : (inActiveIcon == null ? null : inActiveIcon);
        if (icon != null) {
            icon.setBounds((getWidth() - iconWidth) / 2, activeItemPaddingTop,
                    (getWidth() + iconWidth) / 2, activeItemPaddingTop + iconHeight);
            icon.draw(canvas);
        }

        if (!TextUtils.isEmpty(title)) {
            paint.setTextSize(isActive ? activeTextSize : inActiveTextSize);
            paint.setColor(isActive ? activeTextColor : inActiveTextColor);
            //测量标题的宽度
            int titleWidth = (int) paint.measureText(title);
            Paint.FontMetrics metrics = paint.getFontMetrics();
            canvas.drawText(title, (getWidth() - titleWidth) / 2,
                    getHeight() - (isActive ? activeItemPaddingBottom : inActiveItemPaddingBottom) - metrics.bottom, paint);
        }

        if (showTipDot) {
            paint.setColor(tipBgColor);
            canvas.drawCircle((getWidth() + iconWidth) / 2 - tipMarginLeft + tipDotRadius,
                    (isActive ? activeItemPaddingTop : inActiveItemPaddingTop) + tipMarginTop + tipDotRadius, tipDotRadius, paint);
        }

        if (!TextUtils.isEmpty(tipMessage)) {
            paint.setColor(tipBgColor);
            paint.setTextSize(tipTextSize);
            int msgWidth = (int) paint.measureText(tipMessage);
            Path path = new Path();
            int left = (getWidth() + iconWidth) / 2 - tipMarginLeft;
            int top = (isActive ? activeItemPaddingTop : inActiveItemPaddingTop) + tipMarginTop;
            int right = left + msgWidth + tipBgCornerRadius;
            int bottom = top + tipBgCornerRadius * 2;
            float[] radius = {tipBgCornerRadius, tipBgCornerRadius, tipBgCornerRadius, tipBgCornerRadius,
                    tipBgCornerRadius, tipBgCornerRadius, tipBgCornerRadius, tipBgCornerRadius};
            path.addRoundRect(new RectF(left, top, right, bottom), radius, Path.Direction.CW);
            canvas.drawPath(path, paint);
            paint.setColor(tipTextColor);
            Paint.FontMetrics metrics = paint.getFontMetrics();
            canvas.drawText(tipMessage, left + tipBgCornerRadius / 2,
                    top + tipBgCornerRadius - metrics.top / 2 - metrics.bottom / 2, paint);
        }
    }

    public static class Builder {
        private Context context;
        private String title;
        private int activeTextSize, inActiveTextSize;
        private int activeTextColor, inActiveTextColor;
        private int activeItemPaddingTop, inActiveItemPaddingTop, activeItemPaddingBottom, inActiveItemPaddingBottom;
        private int activeIconRes, inActiveIconRes;
        private int tipMarginLeft, tipMarginTop;
        private int tipTextSize;
        private int tipTextColor;
        private int tipBgColor;
        private int tipBgCornerRadius, tipDotRadius;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleTextSize(int activeTitleTextSize, int inActiveTitleTextSize) {
            this.activeTextSize = activeTitleTextSize;
            this.inActiveTextSize = inActiveTitleTextSize;
            return this;
        }

        public Builder setTitleTextColor(int activeTextColor, int inActiveTextColor) {
            this.activeTextColor = activeTextColor;
            this.inActiveTextColor = inActiveTextColor;
            return this;
        }

        public Builder setIcon(int activeIconRes, int inActiveIconRes) {
            this.activeIconRes = activeIconRes;
            this.inActiveIconRes = inActiveIconRes;
            return this;
        }

        public Builder setPaddingTopAndBottom(int activeItemPaddingTop, int inActiveItemPaddingTop,
                                              int activeItemPaddingBottom, int inActiveItemPaddingBottom) {
            this.activeItemPaddingTop = activeItemPaddingTop;
            this.inActiveItemPaddingTop = inActiveItemPaddingTop;
            this.activeItemPaddingBottom = activeItemPaddingBottom;
            this.inActiveItemPaddingBottom = inActiveItemPaddingBottom;
            return this;
        }

        public Builder setTipMarginLeftAndTop(int tipMarginLeft, int tipMarginTop) {
            this.tipMarginLeft = tipMarginLeft;
            this.tipMarginTop = tipMarginTop;
            return this;
        }

        public Builder setTipTextSize(int tipTextSize) {
            this.tipTextSize = tipTextSize;
            return this;
        }

        public Builder setTipTextColor(int tipTextColor) {
            this.tipTextColor = tipTextColor;
            return this;
        }

        public Builder setTipBgColor(int tipBgColor) {
            this.tipBgColor = tipBgColor;
            return this;
        }

        public Builder setTipBgCornerRadius(int tipBgCornerRadius) {
            this.tipBgCornerRadius = tipBgCornerRadius;
            return this;
        }

        public Builder setTipDotRadius(int tipDotRadius) {
            this.tipDotRadius = tipDotRadius;
            return this;
        }

        public BottomNavigationItemView build() {
            return new BottomNavigationItemView(this);
        }
    }
}
