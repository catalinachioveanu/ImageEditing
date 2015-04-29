package com.bitmap.imageediting.ColorEditing;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bitmap.imageediting.R;
import com.bitmap.imageediting.Views.ColorListAdapter;
import com.bitmap.imageeditinglibrary.ImageColorService;

public class ReplaceColorActivity extends ActionBarActivity
{

	private Integer[] colors;
	private Integer replacingColor;
	private ImageView selectedColorImageView;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_replace_color);

		ListView colorsListView = (ListView) findViewById(R.id.colorsListView);
		colors = new Integer[]{Color.RED, Color.GREEN, Color.BLUE};
		ColorListAdapter adapter = new ColorListAdapter(this, colors);

		selectedColorImageView = (ImageView) findViewById(R.id.selectedColorImageView);
		colorsListView.setAdapter(adapter);

		colorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				setReplacingColor(position);
			}
		});


		imageView = (ImageView) findViewById(R.id.imageView);
	}

	private void setReplacingColor(int position)
	{
		selectedColorImageView.setBackgroundColor(colors[position]);
		replacingColor = colors[position];
		final Bitmap bitmap = ((BitmapDrawable) (imageView.getDrawable())).getBitmap();


		final ProgressDialog dialog = new ProgressDialog(ReplaceColorActivity.this);
		dialog.setMessage("Loading...");
		dialog.setCancelable(false);

		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				dialog.show();
			}
		});

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{

				final Bitmap changedBitmap = ImageColorService.replaceColor(bitmap, Color.RED, replacingColor);

				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						dialog.dismiss();
						imageView.setImageBitmap(null);
						bitmap.recycle();
						imageView.setImageBitmap(changedBitmap);
					}
				});
			}
		}).start();
	}
}
