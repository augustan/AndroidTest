
package com.aug.androidtest;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class DrawTextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        
        TextView txView = new TextView(this);
        rootLayout.addView(txView);
        rootLayout.addView(new SampleView(this));
        fillTextViewSpan(txView);
        
        setContentView(rootLayout);
    }
    
    private void fillTextViewSpan(TextView txView) {
        //创建一个 SpannableString对象 
        SpannableString msp = new SpannableString("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站短信彩信地图X轴综合/bot");

        //设置字体(default,default-bold,monospace,serif,sans-serif) 
        msp.setSpan(new TypefaceSpan("monospace"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置字体大小（绝对值,单位：像素） 
        msp.setSpan(new AbsoluteSizeSpan(20), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(20, true), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。 

        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍 
        msp.setSpan(new RelativeSizeSpan(0.5f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半 
        msp.setSpan(new RelativeSizeSpan(2.0f), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //2.0f表示默认字体大小的两倍 

        //设置字体前景色 
        msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色 

        //设置字体背景色 
        msp.setSpan(new BackgroundColorSpan(Color.CYAN), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置背景色为青色 

        //设置字体样式正常，粗体，斜体，粗斜体 
        msp.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //正常 
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 20, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体 
        msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体 
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 24, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗斜体 

        //设置下划线 
        msp.setSpan(new UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置删除线 
        msp.setSpan(new StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置上下标 
        msp.setSpan(new SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //下标 
        msp.setSpan(new SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标             

        //超级链接（需要添加setMovementMethod方法附加响应） 
        msp.setSpan(new URLSpan("tel:4155551212"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //电话 
        msp.setSpan(new URLSpan("mailto:webmaster@google.com"), 39, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //邮件 
        msp.setSpan(new URLSpan("http://www.2cto.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //网络 
        msp.setSpan(new URLSpan("sms:4155551212"), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //短信   使用sms:或者smsto: 
        msp.setSpan(new URLSpan("mms:4155551212"), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //彩信   使用mms:或者mmsto: 
        msp.setSpan(new URLSpan("geo:38.899533,-77.036476"), 47, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //地图    

        //设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍 
        msp.setSpan(new ScaleXSpan(2.0f), 49, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变 

//        //设置字体（依次包括字体名称，字体大小，字体样式，字体颜色，链接颜色） 
//        ColorStateList csllink = null;
//        ColorStateList csl = null;
//        XmlResourceParser xppcolor = getResources().getXml(R.color.color);
//        try {
//            csl = ColorStateList.createFromXml(getResources(), xppcolor);
//        } catch (XmlPullParserException e) {
//            // TODO: handle exception 
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO: handle exception 
//            e.printStackTrace();
//        }
//
//        XmlResourceParser xpplinkcolor = getResources().getXml(R.color.linkcolor);
//        try {
//            csllink = ColorStateList.createFromXml(getResources(), xpplinkcolor);
//        } catch (XmlPullParserException e) {
//            // TODO: handle exception 
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO: handle exception 
//            e.printStackTrace();
//        }
//        msp.setSpan(new TextAppearanceSpan("monospace", android.graphics.Typeface.BOLD_ITALIC, 30, csl, csllink), 51,
//                53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置项目符号 android.text.style.BulletSpan.STANDARD_GAP_WIDTH
        msp.setSpan(new BulletSpan(30, Color.RED), 0, msp.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色 

        //设置图片 
        Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        msp.setSpan(new ImageSpan(drawable), 53, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txView.setText(msp);
        txView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static class SampleView extends View {
        private Paint mPaint;
        private float mX;
        private float[] mPos;

        private Path mPath;
        private Paint mPathPaint;

        private static final int DY = 30;
        private static final String TEXT_L = "Left";
        private static final String TEXT_C = "Center";
        private static final String TEXT_R = "Right";
        private static final String POSTEXT = "Positioned";
        private static final String TEXTONPATH = "Along a path";

        private static void makePath(Path p) {
            p.moveTo(10, 0);
            p.cubicTo(100, -50, 200, 50, 300, 0);
        }

        private float[] buildTextPositions(String text, float y, Paint paint) {
            float[] widths = new float[text.length()];
            // initially get the widths for each char
            int n = paint.getTextWidths(text, widths);
            // now popuplate the array, interleaving spaces for the Y values
            float[] pos = new float[n * 2];
            float accumulatedX = 0;
            for (int i = 0; i < n; i++) {
                pos[i * 2 + 0] = accumulatedX;
                pos[i * 2 + 1] = y;
                accumulatedX += widths[i];
            }
            return pos;
        }

        public SampleView(Context context) {
            super(context);
            setFocusable(true);

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(30);
            mPaint.setTypeface(Typeface.SERIF);

            mPos = buildTextPositions(POSTEXT, 0, mPaint);

            mPath = new Path();
            makePath(mPath);

            mPathPaint = new Paint();
            mPathPaint.setAntiAlias(true);
            mPathPaint.setColor(0x800000FF);
            mPathPaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);

            Paint p = mPaint;
            float x = mX;
            float y = 0;
            float[] pos = mPos;

            // draw the normal strings

            p.setColor(0x80FF0000);
            canvas.drawLine(x, y, x, y + DY * 3, p);
            p.setColor(Color.BLACK);

            canvas.translate(0, DY);
            p.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(TEXT_L, x, y, p);

            canvas.translate(0, DY);
            p.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(TEXT_C, x, y, p);

            canvas.translate(0, DY);
            p.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(TEXT_R, x, y, p);

            canvas.translate(100, DY * 2);

            // now draw the positioned strings

            p.setColor(0xBB00FF00);
            for (int i = 0; i < pos.length / 2; i++) {
                canvas.drawLine(pos[i * 2 + 0], pos[i * 2 + 1] - DY, pos[i * 2 + 0], pos[i * 2 + 1]
                        + DY * 2, p);
            }
            p.setColor(Color.BLACK);

            p.setTextAlign(Paint.Align.LEFT);
            canvas.drawPosText(POSTEXT, pos, p);

            canvas.translate(0, DY);
            p.setTextAlign(Paint.Align.CENTER);
            canvas.drawPosText(POSTEXT, pos, p);

            canvas.translate(0, DY);
            p.setTextAlign(Paint.Align.RIGHT);
            canvas.drawPosText(POSTEXT, pos, p);

            // now draw the text on path

            canvas.translate(-100, DY * 2);

            canvas.drawPath(mPath, mPathPaint);
            p.setTextAlign(Paint.Align.LEFT);
            canvas.drawTextOnPath(TEXTONPATH, mPath, 0, 0, p);

            canvas.translate(0, DY * 1.5f);
            canvas.drawPath(mPath, mPathPaint);
            p.setTextAlign(Paint.Align.CENTER);
            canvas.drawTextOnPath(TEXTONPATH, mPath, 0, 0, p);

            canvas.translate(0, DY * 1.5f);
            canvas.drawPath(mPath, mPathPaint);
            p.setTextAlign(Paint.Align.RIGHT);
            canvas.drawTextOnPath(TEXTONPATH, mPath, 0, 0, p);
        }

        @Override
        protected void onSizeChanged(int w, int h, int ow, int oh) {
            super.onSizeChanged(w, h, ow, oh);
            mX = w * 0.5f; // remember the center of the screen
        }
    }
}
