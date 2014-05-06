package com.app.circlememory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_end);

		Intent intent = getIntent();
		int score = intent.getIntExtra("score", 0) - 1;
		TextView scoreTextView = (TextView) findViewById(R.id.scoreTextView);
		scoreTextView.setText("Score:" + String.valueOf(score));

		Button restartBtn = (Button) findViewById(R.id.restartBtn);
		restartBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EndActivity.this, MainActivity.class);
				EndActivity.this.startActivity(intent);
				EndActivity.this.finish();
			}

		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Builder builder = new AlertDialog.Builder(this).setMessage("Are your sure you want to quit?")
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent intent = new Intent(EndActivity.this, MainActivity.class);
							EndActivity.this.startActivity(intent);
							EndActivity.this.finish();
						}


					}).setNegativeButton("No", null);
			builder.create().show();
		}
		return super.onKeyDown(keyCode, event);
	}
}
