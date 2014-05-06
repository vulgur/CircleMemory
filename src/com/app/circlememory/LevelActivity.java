package com.app.circlememory;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class LevelActivity extends Activity {
	private List<Circle> circles = null;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("circle", "LevelActivity - onCreate");
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_level);
		Intent intent = getIntent();

		circles = (List<Circle>) intent.getSerializableExtra("circles");
		int level = circles.size();
		TextView levelView = (TextView) findViewById(R.id.levelView);
		levelView.setText("LEVEL - " + (level + 1));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Intent intent = new Intent(this, CircleActivity.class);
			intent.putExtra("circles", (Serializable)circles);
			this.startActivity(intent);
			this.finish();
		}
		
		return super.onTouchEvent(event);
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Builder builder = new AlertDialog.Builder(this).setMessage("Are your sure you want to quit?")
					.setPositiveButton("Yes", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent intent = new Intent(LevelActivity.this, MainActivity.class);
							LevelActivity.this.startActivity(intent);
							LevelActivity.this.finish();
						}
					}).setNegativeButton("No", null);
			builder.create().show();
		}
		return super.onKeyDown(keyCode, event);
	}

}
