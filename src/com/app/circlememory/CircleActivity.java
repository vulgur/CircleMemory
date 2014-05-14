package com.app.circlememory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CircleActivity extends Activity {
	private static class CircleView extends View {

		public CircleView(Context context) {
			super(context);
		}

		@SuppressLint("DrawAllocation")
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			// Log.i("circle", "canvas width=" + canvas.getWidth() + ", height="
			// + canvas.getHeight());
			setBackgroundColor(Color.BLACK);

			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setStyle(Style.FILL);

			for (Circle c : circles) {
				canvas.drawCircle(c.getX(), c.getY(), c.getRadius(), paint);
			}
		}

	}

	private static List<Circle> circles = null;
	private static int screenWidth;
	private static int screenHeight;
	private static Circle lastCircle = null;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("circle", "CircleActivity - onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.drawable.black);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// get width and height of the screen
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;

		// get intent
		Intent intent = getIntent();
		if (intent.getSerializableExtra("circles") != null) {
			circles = (List<Circle>) intent.getSerializableExtra("circles");
			// Log.i("circle", "circles size=" + circles.size());
		} else {
			circles = new ArrayList<>();
		}
		Circle circle = getACircle();
		lastCircle = circle;
		circles.add(circle);
		// set the view
		CircleView cv = new CircleView(this);

		this.setContentView(cv);
	}

	private boolean isIntersect(Circle circle, float x, float y, float radius) {
		float distanceX = circle.getX() - x;
		float distanceY = circle.getY() - y;
		float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
		if (distance > circle.getRadius() + radius)
			return false;
		else
			return true;
	}

	private Circle getACircle() {
		float radius = 0, x, y;
		while (true) {
			float random = (float) (Math.random() * screenWidth / 4);
			radius = random > 50 ? random : 50;

			float areaWidth = screenWidth - radius * 2;
			float areaHeight = screenHeight - radius * 2;

			x = radius + (float) (Math.random() * areaWidth);
			y = radius + (float) (Math.random() * areaHeight);

			// Log.i("circle",
			// String.format("screenWidth=%d, screenHeight=%d\nx=%f, y=%f, radius=%f\n",
			// screenWidth,
			// screenHeight, x, y, radius));
			boolean valid = true;
			for (Circle c : circles) {
				if (isIntersect(c, x, y, radius)) {
					valid = false;
					break;
				}
			}

			if (valid)
				break;
		}
		return new Circle(x, y, radius);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (isInCircle(lastCircle, event)) {
				// Log.i("circle", "Hit the circle");
				Intent intent = new Intent(this, LevelActivity.class);
				intent.putExtra("circles", (Serializable) circles);
				this.startActivity(intent);
				this.finish();
			} else {
				// Log.i("circle", "UnHit the circle");
				Intent intent = new Intent(this, EndActivity.class);
				intent.putExtra("score", circles.size());
				this.startActivity(intent);
				this.finish();
			}
		}
		return super.onTouchEvent(event);
	}

	private boolean isInCircle(Circle circle, MotionEvent event) {
		float touchX = event.getX();
		float touchY = event.getY();
		// Log.i("circle", "Circle Center:(" + circle.getX() + "," +
		// circle.getY() + ")");
		// Log.i("circle", "Touch Point:(" + touchX + "," + touchY + ")");
		float distanceX = circle.getX() - touchX;
		float distanceY = circle.getY() - touchY;
		float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
		// Log.i("circle", "distanceX=" + distanceX + ", distanceY=" +
		// distanceY);
		// Log.i("circle", "distance=" + distance + ", radius=" +
		// circle.getRadius());
		if (distance < circle.getRadius() + 200) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Builder builder = new AlertDialog.Builder(this).setMessage("Are your sure you want to quit?")
					.setPositiveButton("Yes", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent intent = new Intent(CircleActivity.this, MainActivity.class);
							CircleActivity.this.startActivity(intent);
							CircleActivity.this.finish();
						}
					}).setNegativeButton("No", null);
			builder.create().show();
		}
		return super.onKeyDown(keyCode, event);
	}
}
