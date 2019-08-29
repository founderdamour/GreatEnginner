package com.yzz.great.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.yzz.great.R;

/**
 * 主界面的切换tab
 */
public class MainTabItemView extends FrameLayout {

    private View itemView;

    private TextView tabItemView, unreadNumView;

    private ImageView dotView;

    private String itemTitle, unreadNum;

    private Drawable itemImg, itemImgSelected;

    private int itemTitleColor, itemTitleColorSelected;

    private boolean itemStateSelected, dotVisible, unreadNumVisible;

    private Class<? extends Fragment> fragmentClass;

    private OnTabItemStateChangeListener onTabItemStateChangeListener;

    public void setOnTabItemStateChangeListener(OnTabItemStateChangeListener onTabItemStateChangeListener) {
        this.onTabItemStateChangeListener = onTabItemStateChangeListener;
    }

    public MainTabItemView(@NonNull Context context) {
        this(context, null);
    }

    public MainTabItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTabItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttr(context, attrs);
        initListener();
    }

    private void initView(Context context) {
        itemView = LayoutInflater.from(context).inflate(R.layout.main_tab_item_view, this, false);
        addView(itemView);
        tabItemView = itemView.findViewById(R.id.tabItemView);
        unreadNumView = itemView.findViewById(R.id.unreadNumView);
        dotView = itemView.findViewById(R.id.dotView);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MainTabItemView);
        itemTitle = typedArray.getString(R.styleable.MainTabItemView_itemTitle);
        setItemTitle(itemTitle);
        itemImg = typedArray.getDrawable(R.styleable.MainTabItemView_itemImg);
        itemImgSelected = typedArray.getDrawable(R.styleable.MainTabItemView_itemImgSelected);
        itemTitleColor = typedArray.getColor(R.styleable.MainTabItemView_itemTitleColor, Color.parseColor("#8d8d8d"));
        itemTitleColorSelected = typedArray.getColor(R.styleable.MainTabItemView_itemTitleColorSelected, Color.parseColor("#11d7aa"));
        itemStateSelected = typedArray.getBoolean(R.styleable.MainTabItemView_itemStateSelected, false);
        setItemStateSelected(itemStateSelected);
        dotVisible = typedArray.getBoolean(R.styleable.MainTabItemView_dotVisible, false);
        setDotVisible(dotVisible);
        unreadNumVisible = typedArray.getBoolean(R.styleable.MainTabItemView_unreadNumVisible, false);
        setUnreadNumVisible(unreadNumVisible);
        unreadNum = typedArray.getString(R.styleable.MainTabItemView_unreadNum);
        setUnreadNum(unreadNum);
        typedArray.recycle();
    }

    private void initListener() {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTabItemStateChangeListener != null) {
                    boolean itemState = onTabItemStateChangeListener.shouldChangeTabItemState(MainTabItemView.this);
                    if (itemState) {
                        setItemStateSelected(itemStateSelected);
                        onTabItemStateChangeListener.onTabItemStateChanged(MainTabItemView.this);
                    }
                } else {
                    setItemStateSelected(!itemStateSelected);
                }
            }
        });
    }

    public void setItemTitle(String itemTitle) {
        if (TextUtils.isEmpty(itemTitle)) {
            throw new RuntimeException("the title cannot be empty");
        }
        this.itemTitle = itemTitle;
        tabItemView.setText(itemTitle);
    }

    public void setItemImg(Drawable itemImg) {
        if (itemImg == null) {
            throw new RuntimeException("the title drawable cannot be empty");
        }
        this.itemImg = itemImg;
        tabItemView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, itemImg, null, null);
    }

    public void setItemImgSelected(Drawable itemImgSelected) {
        if (itemImgSelected == null) {
            throw new RuntimeException("the title selected drawable cannot be empty");
        }
        this.itemImgSelected = itemImgSelected;
        tabItemView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, itemImgSelected, null, null);
    }

    public void setItemTitleColor(@ColorInt int itemTitleColor) {
        this.itemTitleColor = itemTitleColor;
        tabItemView.setTextColor(itemTitleColor);
    }

    public void setItemTitleColorSelected(@ColorInt int itemTitleColorSelected) {
        this.itemTitleColorSelected = itemTitleColorSelected;
        tabItemView.setTextColor(itemTitleColorSelected);
    }


    public void setItemStateSelected(boolean itemStateSelected) {
        this.itemStateSelected = itemStateSelected;
        if (itemStateSelected) {
            setItemImgSelected(itemImgSelected);
            setItemTitleColorSelected(itemTitleColorSelected);
        } else {
            setItemImg(itemImg);
            setItemTitleColor(itemTitleColor);
        }
    }

    public boolean isItemStateSelected() {
        return itemStateSelected;
    }

    public void setDotVisible(boolean dotVisible) {
        this.dotVisible = dotVisible;
        dotView.setVisibility(dotVisible ? VISIBLE : GONE);
    }

    public void setUnreadNumVisible(boolean unreadNumVisible) {
        this.unreadNumVisible = unreadNumVisible;
        unreadNumView.setVisibility(unreadNumVisible ? VISIBLE : GONE);
    }

    public void setUnreadNum(String unreadNum) {
        if (TextUtils.isEmpty(unreadNum)) {
            unreadNumView.setVisibility(GONE);
        } else {
            this.unreadNum = unreadNum;
            unreadNumView.setVisibility(VISIBLE);
            unreadNumView.setText(unreadNum);
        }
    }

    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    public void setFragmentClass(Class<? extends Fragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }

    /**
     * 条目状态改变的监听
     */
    public interface OnTabItemStateChangeListener {
        // 是否应该改变
        boolean shouldChangeTabItemState(MainTabItemView tabItemView);

        // 已经改变
        void onTabItemStateChanged(MainTabItemView tabItemView);
    }
}
