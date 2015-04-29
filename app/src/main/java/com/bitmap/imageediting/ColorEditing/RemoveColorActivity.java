package com.bitmap.imageediting.ColorEditing;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bitmap.imageediting.R;
import com.bitmap.imageediting.Views.ColorListAdapter;
import com.bitmap.imageeditinglibrary.ImageColorService;

public class RemoveColorActivity extends ActionBarActivity
{

	private Integer[] colors;
	private ImageView selectedColorImageView;
	private ImageView imageView;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remove_color);

		imageView = (ImageView) findViewById(R.id.imageView);
		dialog = new ProgressDialog(this);
		dialog.setMessage("Loading...");

		Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		 colors = ImageColorService.getColorsFromBitmap(bitmap);


		ListView colorsListView = (ListView) findViewById(R.id.colorsListView);
		ColorListAdapter adapter = new ColorListAdapter(this, colors);

		selectedColorImageView = (ImageView) findViewById(R.id.selectedColorImageView);
		colorsListView.setAdapter(adapter);

		colorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
			{
				dialog.show();

				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						removeColor(position);
					}
				}).start();
			}
		});


	}

	private void removeColor(int position)
	{
		Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

		final Bitmap changedBitmap = ImageColorService.removeColor(bitmap, colors[position]);

		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				if(dialog!=null) dialog.dismiss();
				imageView.setImageBitmap(null);
				imageView.setImageBitmap(changedBitmap);
			}
		});

	}

}
