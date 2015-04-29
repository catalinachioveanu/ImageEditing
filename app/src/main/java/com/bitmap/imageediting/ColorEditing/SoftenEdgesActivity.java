package com.bitmap.imageediting.ColorEditing;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bitmap.imageediting.R;
import com.bitmap.imageeditinglibrary.ImageColorService;

public class SoftenEdgesActivity extends ActionBarActivity
{

	private ImageView imageView;
	private long wait = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soften_edges);
		Button softenEdgesButton = (Button) findViewById(R.id.softenEdgesButton);
		imageView = (ImageView) findViewById(R.id.imageView);
		softenEdgesButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Bitmap original = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
				Bitmap b = ImageColorService.setBitmapGlow(original, 150, 150, 150);

				imageView.setImageBitmap(null);
				imageView.setImageBitmap(b);
			}
		});

	}



}
