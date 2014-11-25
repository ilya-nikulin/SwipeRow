package ru.simpls.swiperow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nikulin on 25.08.2014.
 */
public class SwipedHorizontalScrollView extends HorizontalScrollView {
    private float lastX = 0;
    private float xDistance = 0;
    private float curX = 0;
    private float lastX2 = 0;
    private float xDistance2 = 0;
    private float curX2 = 0;
    private float lastX3 = 0;
    private View templateMainLayout;
    private boolean animationStarted = false;
    private ImageView indicatorImage;
    private AnimationDrawable frameAnimation;

    /////////////////////////////////
    public SwipedHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int mainPartWidth = getTemplateMainLayout().getWidth();
        double insBorder = mainPartWidth*0.01;//insensitive border
        int insenseBorder = (int) insBorder;
        double sBorder = mainPartWidth*0.10;//sensitive border
        int senseBorder = (int) sBorder;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (lastX == 0) lastX = ev.getX();
                if (lastX2 == 0) lastX2 = getScrollX();
                if (lastX3 == 0) lastX3 = getTemplateMainLayout().getLeft();
                curX = ev.getX();
                curX2 = getScrollX();
                if (curX2 < 1 && animationStarted == false) startAnimation();
                xDistance = curX - lastX;
                if (xDistance < insenseBorder && xDistance > -insenseBorder )
                    return true;
                else break;
            case MotionEvent.ACTION_UP:
                curX = ev.getX();
                curX2 = getScrollX();
                xDistance = curX - lastX;
                xDistance2 = curX2 - lastX2;
                if (xDistance < -senseBorder) {
                    stopAnimation();
                    if (curX2 >= lastX3) fullScroll(FOCUS_RIGHT);
                    else {smoothScrollTo((int) lastX3, 0); }
                }
                if (xDistance > senseBorder) {
                    if (curX2 <= lastX3 && frameAnimation!=null && frameAnimation.getCurrent() == frameAnimation.getFrame(frameAnimation.getNumberOfFrames() - 1))
                        fullScroll(FOCUS_LEFT);
                    else
                        smoothScrollTo((int) lastX3, 0);
                }
                if (xDistance >= -senseBorder && xDistance <= -insenseBorder){
                    if (curX2 >= lastX3) smoothScrollTo((int) lastX3, 0);
                    else fullScroll(FOCUS_LEFT);
                }
                if (xDistance <= senseBorder && xDistance >= insenseBorder) {
                    if (curX2 <= lastX3) { smoothScrollTo((int) lastX3, 0); }
                    else fullScroll(FOCUS_RIGHT);
                }
                if (frameAnimation!=null && frameAnimation.getCurrent() != frameAnimation.getFrame(frameAnimation.getNumberOfFrames() - 1)) stopAnimation();



                lastX = 0;
                lastX2 = 0;
                lastX3 = 0;
                return false;
        }
        return super.onTouchEvent(ev);
    }
    public View getTemplateMainLayout() {
        return templateMainLayout;
    }

    public void setTemplateMainLayout(View templateMainLayout) {
        this.templateMainLayout = templateMainLayout;
    }
    private void stopAnimation (){
        if (animationStarted==true){
            frameAnimation.stop();
            indicatorImage.setBackgroundResource(0);
            TextView text = (TextView) this.findViewById(R.id.templatePayText);
            String s = getResources().getString(R.string.template_pay_wait);
            text.setText(s);
            animationStarted = false;
        }
    }
    private void startAnimation(){
        indicatorImage = (ImageView) this.findViewById(R.id.payTemplateIndicatorImage);
        if (indicatorImage == null) return;
        indicatorImage.setBackgroundResource(R.drawable.pay_template_indicator);
        frameAnimation = (AnimationDrawable) indicatorImage.getBackground();
        frameAnimation.start();
        animationStarted = true;
        checkIfAnimationDone(frameAnimation);
    }
    private void checkIfAnimationDone(AnimationDrawable anim){
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                    checkIfAnimationDone(a);
                } else{
                    makeTemplatePayment();
                }
            }
        }, timeBetweenChecks);
    };
    private void makeTemplatePayment(){
        TextView text = (TextView) this.findViewById(R.id.templatePayText);
        String s = getResources().getString(R.string.template_pay_sent);
        text.setText(s);
    }
}