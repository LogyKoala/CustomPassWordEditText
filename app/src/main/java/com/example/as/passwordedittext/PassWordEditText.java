package com.example.as.passwordedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

/**
 * Created by as on 2017/4/20.
 * 自定义密码输入框
 */

public class PassWordEditText extends EditText {

    private PWCommitListener mListener;
    // 画笔
    private Paint mPaint;
    // 一个密码所占的宽度
    private int mPasswordItemWidth;
    // 密码的个数默认为6位数
    private int mPasswordNumber = 6;
    // 背景边框颜色
    private int mBgColor = Color.parseColor("#d1d2d6");
    // 背景边框大小
    private int mBgSize = 1;
    // 背景边框圆角大小
    private int mBgCorner = 0;
    // 分割线的颜色
    private int mDivisionLineColor = mBgColor;
    // 分割线的大小
    private int mDivisionLineSize = 1;
    // 密码圆点的颜色
    private int mPasswordColor = mDivisionLineColor;
    // 密码圆点的半径大小
    private int mPasswordRadius = 4;

    public PassWordEditText(Context context) {
        this(context, null);
    }

    public PassWordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        initPaint();
        //初始化自定义属性数据
        initAttributeSet(context, attrs);
        setMaxEms(mPasswordNumber);
        setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        // 不显示光标
        setCursorVisible(false);
        setEnabled(false);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        //设置坑锯齿
        mPaint.setAntiAlias(true);
    }

    /**
     * 初始化自定义属性
     */
    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PassWordEditText);
        // 获取大小
        mDivisionLineSize = (int) array.getDimension(R.styleable.PassWordEditText_divisionLinesSize, dip2px(mDivisionLineSize));
        mPasswordRadius = (int) array.getDimension(R.styleable.PassWordEditText_passwordRadius, dip2px(mPasswordRadius));
        mBgSize = (int) array.getDimension(R.styleable.PassWordEditText_bgSize, dip2px(mBgSize));
        mBgCorner = (int) array.getDimension(R.styleable.PassWordEditText_bgCorner, 0);
        // 获取颜色
        mBgColor = array.getColor(R.styleable.PassWordEditText_bgColor, mBgColor);
        mDivisionLineColor = array.getColor(R.styleable.PassWordEditText_divisionLineColor, mDivisionLineColor);
        mPasswordColor = array.getColor(R.styleable.PassWordEditText_passwordColor, mDivisionLineColor);
        mPasswordNumber = array.getInt(R.styleable.PassWordEditText_passwordNumber, mPasswordNumber);
        //回收
        array.recycle();
    }

    /**
     * dip 转 px
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int passwordWidth = getWidth() - (mPasswordNumber - 1) * mDivisionLineSize;
        mPasswordItemWidth = passwordWidth / mPasswordNumber;
        //绘制背景
        drawBG(canvas);
        //绘制分割线
        drawDivisionLion(canvas);
        //绘制密码
        drawPassWord(canvas);
    }

    //添加密码将自定义键盘和密码框联系起来
    public void addPW(String pw){
      if(!TextUtils.isEmpty(pw)){
          pw = getText().toString().trim()+pw;
          if(pw.length() >=mPasswordNumber){
              //密码输入完成回调
              if(pw.length() ==mPasswordNumber){
                  setText(pw);
              }
              mListener.commit_PW(pw);
              return;
          }
          setText(pw);
      }
    }

    //删除最后一个密码
    public void removePW(){
        String pw = getText().toString().trim();
        if(TextUtils.isEmpty(pw)){
            return;
        }
        pw = pw.substring(0,pw.length()-1);
        setText(pw);
    }

    //绘制密码
    private void drawPassWord(Canvas canvas) {
        //获取当前文本长度的原因是因为让用户输一个我们动态的展示一个密码
        int passwordLength = getText().length();
        //设置画笔颜色
        mPaint.setColor(mPasswordColor);
        //设密码为实心
        mPaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < passwordLength; i++) {
            int cx = i * mDivisionLineSize + i * mPasswordItemWidth + mPasswordItemWidth / 2 + mBgSize;
            canvas.drawCircle(cx, getHeight() / 2, mPasswordRadius, mPaint);
        }
    }

    //绘制分割线
    private void drawDivisionLion(Canvas canvas) {

        //设置画笔颜色
        mPaint.setColor(mDivisionLineColor);
        //设置画笔大小
        mPaint.setStrokeWidth(mDivisionLineSize);

        for (int i = 0; i < mPasswordNumber - 1; i++) {
            int startX = mPasswordItemWidth * (i + 1) + mDivisionLineSize * (i + 1);
            canvas.drawLine(startX, mBgSize, startX, getHeight() - mBgSize, mPaint);
        }
    }

    //绘制背景
    private void drawBG(Canvas canvas) {
        //设置背景颜色
        mPaint.setColor(mBgColor);
        //设置画笔为空心
        mPaint.setStyle(Paint.Style.STROKE);
        //设置空心线宽
        mPaint.setStrokeWidth(mBgSize);
        RectF rectF = new RectF(mBgSize, mBgSize, getWidth() - mBgSize, getHeight());
        //进行绘制绘制过程中判断是否是圆角
        if (mBgCorner != 0) {
            //绘制圆角    rectf   圆角弧度    圆角弧度   画笔
            canvas.drawRoundRect(rectF, mBgCorner, mBgCorner, mPaint);
        } else {
            canvas.drawRect(rectF, mPaint);
        }
    }
    public void setOnPWCommitListener(PWCommitListener listener){
        this.mListener =listener;
    }

    //密码完成回调
    public interface PWCommitListener{
        void commit_PW(String pw);
    }
}
