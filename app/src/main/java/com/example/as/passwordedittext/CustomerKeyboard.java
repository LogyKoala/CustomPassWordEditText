package com.example.as.passwordedittext;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by as on 2017/4/20.
 * 自定义键盘
 */

public class CustomerKeyboard extends LinearLayout implements View.OnClickListener {

    private CustomerKeyboardClickListener mListener;
    public CustomerKeyboard(Context context) {
        this(context, null);
    }

    public CustomerKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerKeyboard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context,R.layout.kayboard,this);

        setOnChildClick(this);
    }

    public void setOnChildClick(ViewGroup parant) {
        //获取所有的子条目的个数
        int childCount = parant.getChildCount();

        //对所有子条目进行遍历
        for(int i =0;i<childCount;i++){
            View view =parant.getChildAt(i);
            if(view instanceof ViewGroup){
                //通过递归继续向下判断知道子条目不是VIewGroup
                setOnChildClick((ViewGroup) parant.getChildAt(i));
                //退出本次循环
                continue;
            }
            //如果不是ViewGroup就给当前组件设置点击事件
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        //判断当前点击的按钮的类型我们的键盘只有TextView和ImageVIew
        //如果当前View的类型是TextVIew那么他就是输入的密码
        if(v instanceof TextView){
            String pw =((TextView)v).getText().toString().trim();
            //这里判空一个是因为代码的健壮性另外一个是因为我们TextView是空的如果不判断是会出问题的
            if(!TextUtils.isEmpty(pw)){
                if(mListener !=null) {
                    mListener.click(pw);
                }
            }
            //如果当前View的类型是ImageVIew那么点击的就是删除键
        }else if(v instanceof ImageView){
            if(mListener !=null) {
                mListener.remove();
            }
        }

    }

    /**
     *
     * @param listener CustomerKeyboardClickListener对象
     * 点击回调监听
     */
    public void setOnCustomerKeyboardClickListener(CustomerKeyboardClickListener listener){
        this.mListener =listener;
    }
    /**
     * 回调接口用来判断键盘的点击
     * 如果点击数字调用click方法并将改密码返回回调出去
     * 如果点击删除键则回调remove方法
     */
    public interface  CustomerKeyboardClickListener{
         void click(String pw);
         void remove();
    }
}
