package com.horse.instrument;

import java.util.ArrayList;

import android.R.anim;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

public class InstrumentView extends View {

	private boolean isMearsure = false; 
	private ArrayList<BitmapDrawable> numberList ;


	public InstrumentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initConfig(context);
	}
	public InstrumentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initConfig(context);
	}
	public InstrumentView(Context context) {
		super(context);
		initConfig(context);
	}
	
	
	 @Override
	   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        setMeasuredDimension(measure(widthMeasureSpec,true), measure(heightMeasureSpec,false));
	   }
	 
	 
	 private int measure(int measureSpec,boolean isWidth){
	        int result;
	        int mode = MeasureSpec.getMode(measureSpec);
	        int size = MeasureSpec.getSize(measureSpec);
	        int padding = isWidth?getPaddingLeft()+getPaddingRight():getPaddingTop()+getPaddingBottom();
	        if(mode == MeasureSpec.EXACTLY){
	            result = size;
	        }else{
	            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
	            result += padding;
	            if(mode == MeasureSpec.AT_MOST){
	                if(isWidth) {
	                    result = Math.max(result, size);
	                }
	                else{
	                    result = Math.min(result, size);
	                }
	            }
	        }
	        return result;
	    }
	 
	 
	 
	 private void initConfig(Context context){
		 numberList = new ArrayList<BitmapDrawable>();
			outerCirclePaint = new Paint();
			numberPaint = new Paint();
			
			numberPaint.setStyle(Paint.Style.STROKE);
			numberPaint.setStrokeWidth(7);
			
			
			priceDrawable = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.price)).getBitmap();
			dishDrawable = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.dish)).getBitmap();
			pointerDrawable = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.pointer)).getBitmap();
			
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.zero));
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.one));
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.two));
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.three));
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.four));
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.five));
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.six));
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.sevent));
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.eight));
			numberList.add((BitmapDrawable) context.getResources().getDrawable(R.drawable.nine));
			
	 }
		
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (!isMearsure) {
			measureSize();
			int angle = 0 ;
		}
		canvas.drawColor(Color.WHITE);
		
		mearsurePointer(canvas);
		drawFistCircle(canvas);
		drawPointer(canvas);
		
		drawNumber(canvas);
		
		drawText(canvas);
		
		drawPointerNumber(canvas); 
	}
	
	
	private void drawFistCircle(Canvas canvas){
		canvas.drawBitmap(dishDrawable, dishLeft, dishLeft , outerCirclePaint);
	}
	
	
	private void mearsurePointer (Canvas canvas  ){
		RectF oval = new RectF(priceLeft+2, priceLeft+2, mWidth - priceLeft - 2, mWidth -priceLeft - 2);
		if (drawAngle <= 90 ) {
			numberPaint.setColor(Color.GREEN);
			canvas.drawArc(oval, DEFLUT_ANGLE + startAngle, drawAngle, false, numberPaint);
		}else if (drawAngle > 90 && drawAngle <= 180 ) {
			numberPaint.setColor(Color.GREEN);
			canvas.drawArc(oval, DEFLUT_ANGLE + startAngle, 90, false, numberPaint);
			numberPaint.setColor(Color.YELLOW);
			canvas.drawArc(oval, DEFLUT_ANGLE + startAngle + 90, drawAngle - 90, false, numberPaint);
		}else if (drawAngle > 180){
			numberPaint.setColor(Color.GREEN);
			canvas.drawArc(oval, DEFLUT_ANGLE + startAngle, 90, false, numberPaint);
			numberPaint.setColor(Color.YELLOW);
			canvas.drawArc(oval, DEFLUT_ANGLE + startAngle + 90, drawAngle - 90, false, numberPaint);
			numberPaint.setColor(Color.RED);
			canvas.drawArc(oval, DEFLUT_ANGLE + startAngle + 180, drawAngle - 180, false, numberPaint);
		}
	}
	
	
	private void drawPointer (Canvas canvas ){ 
		canvas.save();
		canvas.rotate(startAngle + drawAngle, origin, origin);
		canvas.drawBitmap(pointerDrawable, pointerLeft, pointerLeft, outerCirclePaint);
		canvas.restore();
	}
	
	private void drawNumber(Canvas canvas){
		count();
		canvas.drawBitmap(numberList.get(drawNumber[0]).getBitmap(), mWidth / 2 + oneNumberWidth * 3 /2 + 8, dishDrawable.getWidth() * 5/6 , outerCirclePaint);
		canvas.drawBitmap(numberList.get(drawNumber[1]).getBitmap(), mWidth / 2 + oneNumberWidth  /2 + 4, dishDrawable.getWidth() * 5/6 , outerCirclePaint);
		canvas.drawBitmap(numberList.get(drawNumber[2]).getBitmap(), mWidth / 2 - oneNumberWidth  /2, dishDrawable.getWidth() * 5/6 , outerCirclePaint);
		canvas.drawBitmap(numberList.get(drawNumber[3]).getBitmap(), mWidth / 2 - oneNumberWidth * 3 /2 - 4, dishDrawable.getWidth() * 5/6 , outerCirclePaint);
		canvas.drawBitmap(numberList.get(drawNumber[4]).getBitmap(), mWidth / 2 - oneNumberWidth * 5 /2 - 8, dishDrawable.getWidth() * 5/6 , outerCirclePaint);
	}
	
	
	private void drawText(Canvas canvas ){
		String text = "累计用电量";
		float textWidth = outerCirclePaint.measureText(text);
		outerCirclePaint.setTextSize(30);
		canvas.drawText(text, mWidth / 2 - textWidth / 2,dishDrawable.getWidth()*5/6  + numberList.get(1).getBitmap().getHeight()+dishLeft , outerCirclePaint);
	}
	
	
	private int[] drawNumber = new int[5];
	private int drawOneNumber = 0 ;
	private void count(){
		int countNumber = drawOneNumber ;
		for (int i = 0; i < drawNumber.length; i++) {
			drawNumber[i] = countNumber % 10 ;
			countNumber = countNumber / 10 ;
		}
	}
	
	
	private void drawPointerNumber(Canvas canvas){
		int haha = dishLeft+dishDrawable.getWidth()/ 2;
		canvas.drawText(coord[1]+"", dishLeft + 50, haha , outerCirclePaint);
		canvas.drawText(coord[3] + "", haha - 10,  dishLeft + 80, outerCirclePaint);
		canvas.drawText(coord[5]+"", dishLeft + dishDrawable.getWidth() - 120,  haha, outerCirclePaint);
		double cos = Math.cos(45);
		double sin = Math.sin(45);
		canvas.drawText(coord[0] + "",(float)(centerX -  sin * dishDrawable.getWidth()/ 2 + 60) ,  (float)(centerY + cos * dishDrawable.getWidth() / 2), outerCirclePaint);
		canvas.drawText(coord[6]+"",(float)(centerX +  sin * dishDrawable.getWidth()/ 2 - 120) ,  (float)(centerY + cos * dishDrawable.getWidth() / 2), outerCirclePaint);
		canvas.drawText(coord[2]+"",(float)(centerX -  sin * dishDrawable.getWidth()/ 2 + 70) ,  (float)(centerY - cos * dishDrawable.getWidth() / 2), outerCirclePaint);
		canvas.drawText(coord[4]+"",(float)(centerX +  sin * dishDrawable.getWidth()/ 2 - 130) ,  (float)(centerY - cos * dishDrawable.getWidth() / 2), outerCirclePaint);
	}
	
	
	private int drawAngle = 0 ;
	private int mWidth ;
	private Paint outerCirclePaint;
	private Paint numberPaint;
	private Bitmap priceDrawable;
	private Bitmap dishDrawable;
	private static final int DEFLUT_ANGLE = 90 ;
	private Bitmap pointerDrawable;
	private int dishLeft ;
	private int priceLeft ;
	private int pointerLeft ;
	private int origin ;
	private int startAngle = 45; 
	private int oneNumberWidth = 0  ; 
	private int centerX ; 
	private int centerY ;
	
	private void measureSize(){
		mWidth = getWidth();
		isMearsure = true;
		dishLeft = (mWidth - dishDrawable.getWidth() )/ 2;
		priceLeft = (mWidth - priceDrawable.getWidth()) / 2 ;
		pointerLeft = (mWidth - pointerDrawable.getWidth()) / 2  ;
		origin = mWidth / 2;
		oneNumberWidth = numberList.get(0).getBitmap().getWidth();
		centerX = dishLeft + dishDrawable.getWidth() / 2 ;
		centerY = centerX ;
	}
	
	
	
	
	
	
	
	
	public int getDrawAngle() {
		return drawAngle;
	}
	public void setDrawAngle(int drawAngle) {
		this.drawAngle = drawAngle;
		System.out.println("测试调用到的set放大法");
		invalidate();
	}


	private int max ;
	
	
	private  void setMax(int max){
		this.max = max;
	}
	
	
	private int progress = 0  ; 
	public void setProgress(int progress , InstrumentView instrumentView){
		if (progress > coord[6]) {
			throw new RuntimeException("progress的值不能大于坐标的最大值");
		}
		this.progress  =progress ;
		
		countAngle();
		
		AnimatorSet set = new AnimatorSet();
		ValueAnimator animator = ObjectAnimator.ofInt(this, "drawAngle", 0 , countAngle);
		ValueAnimator animator2 = ObjectAnimator.ofInt(this, "drawOneNumber", 0 , countAngle);
		animator.setEvaluator(new IntEvaluator());
		animator2.setEvaluator(new IntEvaluator());
		animator.setInterpolator(new BounceInterpolator());
		animator2.setInterpolator(new AccelerateDecelerateInterpolator());
		set.setDuration(1200);
		set.playTogether(animator,animator2);
		set.start();
	}
	
	
	
	
	
	public int getDrawOneNumber() {
		return drawOneNumber;
	}
	public void setDrawOneNumber(int drawOneNumber) {
		this.drawOneNumber = drawOneNumber;
		invalidate();
	}

	private int[] defultAngle = {0,45,90,135,180,225,270};
	
	
	private int countAngle = 0 ;
	private void countAngle(){
		int shortTime = 0 ;
		if (progress == coord[6]) {
			countAngle = 270 ;
		}else {
			for (int i = 0; i < coord.length - 1; i++) {
				if (progress < coord[i+1]) {
					shortTime = i ;
					break;
				} 
			}
			countAngle = defultAngle[shortTime] + (progress - coord[shortTime]) * 45 /(coord[shortTime + 1] - coord[shortTime]);
		}
	}
	
	
	private int number ;
	public  void setNumber(int number){
		this.number = number;
	}
	
	
	private int[]coord = new int[7];
	public void setCoord(int... coord){
		if (coord.length < 6 ) {
			throw new RuntimeException("必须是六个数组元素");
		}
		this.coord = coord;
		setMax(coord[6]);
	}
}
