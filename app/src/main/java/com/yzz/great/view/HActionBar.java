package com.yzz.great.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yzz.great.base.BaseActivity;
import com.yzz.great.R;
import com.yzz.great.util.CommonUtils;

/**
 * 标题栏
 */
public class HActionBar extends RelativeLayout implements View.OnClickListener, View.OnLongClickListener {

    // 左边的按钮
    public static final int FUNCTION_BTN_IMG_BACK = 1;

    // 右边的按钮
    public static final int FUNCTION_BTN_IMG_RIGHT = 2;

    // 中间的文本控件
    public static final int FUNCTION_TEXT_TITLE = 4;

    // 右边的文本控件
    public static final int FUNCTION_BTN_TEXT_RIGHT = 8;

    // 默认打开的功能
    private int currentFunction = 0;

    private ImageView backBtn, rightBtn;

    private TextView titleTv, rightTextBtn;

    private OnActionBarClickListener onActionBarClickListener;

    public void setOnActionBarClickListener(OnActionBarClickListener onActionBarClickListener) {
        this.onActionBarClickListener = onActionBarClickListener;
    }

    public HActionBar(Context context) {
        this(context, null);
    }

    public HActionBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HActionBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
        initListener();
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.action_bar_view, this, false);
        addView(rootView);
        backBtn = rootView.findViewById(R.id.backBtn);
        rightBtn = rootView.findViewById(R.id.rightBtn);
        titleTv = rootView.findViewById(R.id.titleTv);
        rightTextBtn = rootView.findViewById(R.id.rightTextBtn);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HActionBar);
        Drawable background = typedArray.getDrawable(R.styleable.HActionBar_srcBackground);
        if (background == null) {
            background = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
        }
        setBackground(background);
        setFunction(typedArray.getInt(R.styleable.HActionBar_function, 0));
        setTitleText(typedArray.getString(R.styleable.HActionBar_textTitle));
        setRightText(typedArray.getString(R.styleable.HActionBar_textRight));
        setRightSrc(typedArray.getDrawable(R.styleable.HActionBar_srcRight));
        typedArray.recycle();
    }

    private void initListener() {
        backBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        rightTextBtn.setOnClickListener(this);
        titleTv.setOnLongClickListener(this);
    }

    private void setRightText(String rightText) {
        rightTextBtn.setText(rightText);
    }

    private void setRightSrc(Drawable drawable) {
        rightBtn.setImageDrawable(drawable);
    }

    public void setTitleText(String titleText) {
        titleTv.setText(titleText);
    }

    // 设置功能
    private void setFunction(int function) {
        if (currentFunction == function) {
            return;
        }
        currentFunction = function;
        // 条件标题栏
        titleTv.setVisibility(isAddFunction(FUNCTION_TEXT_TITLE) ? VISIBLE : INVISIBLE);
        // 添加返回按钮Button
        backBtn.setVisibility(isAddFunction(FUNCTION_BTN_IMG_BACK) ? VISIBLE : INVISIBLE);
        // 添加RightButton
        rightBtn.setVisibility(isAddFunction(FUNCTION_BTN_IMG_RIGHT) ? VISIBLE : INVISIBLE);
        // 添加文本按钮
        rightTextBtn.setVisibility(isAddFunction(FUNCTION_BTN_TEXT_RIGHT) ? VISIBLE : GONE);
    }

    public boolean isAddFunction(int function) {
        return (currentFunction & function) == function;
    }

    public void addFunction(int function) {
        setFunction(currentFunction | function);
    }

    public void removeFunction(int function) {
        setFunction(currentFunction & (~function));
    }

    @Override
    public boolean onLongClick(View v) {
        //只对标题栏设置长按监听
        if (v == titleTv) {
            if (isAddFunction(FUNCTION_TEXT_TITLE)) {
                if (onActionBarClickListener != null) {
                    onActionBarClickListener.onLongActionBarClick(FUNCTION_TEXT_TITLE);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int function = 0;
        if (v == backBtn) {
            function = FUNCTION_BTN_IMG_BACK;
        } else if (v == rightBtn) {
            function = FUNCTION_BTN_IMG_RIGHT;
        } else if (v == rightTextBtn) {
            function = FUNCTION_BTN_TEXT_RIGHT;
        }
        // 根据控件ID处理点击事件
        if (isAddFunction(function) && function != 0) {
            boolean result = false;
            if (onActionBarClickListener != null) {
                result = onActionBarClickListener.onActionBarClick(function);
            }
            // 如果我们不对按钮做处理，默认对返回按钮做处理
            if (!result) {
                Context context = getContext();
                if (context instanceof Activity) {
                    if (function == FUNCTION_BTN_IMG_BACK) {
                        CommonUtils.hideKeyboard((Activity) context);
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).onBackClick();
                        } else {
                            ((Activity) context).finish();
                        }
                    }
                }
            }
        }
    }

    /**
     * 标题栏按钮事件
     */
    public interface OnActionBarClickListener {
        // 返回true 表示做处理，执行自己的事件， false 表示不做处理
        boolean onActionBarClick(int function);

        // 长按事件
        void onLongActionBarClick(int function);
    }
}
