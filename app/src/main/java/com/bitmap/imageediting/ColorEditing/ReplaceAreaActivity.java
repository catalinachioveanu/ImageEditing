package com.bitmap.imageediting.ColorEditing;

import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bitmap.imageediting.R;
import com.bitmap.imageediting.Views.ColorListAdapter;
import com.bitmap.imageeditinglibrary.ImageColorService;

public class ReplaceAreaActivity extends ActionBarActivity
{
	private Integer replacingColor;
	private Integer[] colors;
	private ImageView selectedColorImageView;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_replace_area);

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


		final ImageView imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, final MotionEvent event)
			{
				int action = event.getAction();

				if (action == MotionEvent.ACTION_DOWN)
				{

					final ProgressDialog dialog = new ProgressDialog(ReplaceAreaActivity.this);
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
							int x = (int) event.getX();
							int y = (int) event.getY();

							final TypedArray styledAttributes = ReplaceAreaActivity.this.getTheme().obtainStyledAttributes(
									new int[] { android.R.attr.actionBarSize });
							int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
							styledAttributes.recycle();

							final Bitmap bitmap = ((BitmapDrawable)(imageView.getDrawable())).getBitmap();

							Display display = getWindowManager().getDefaultDisplay();
							Point size = new Point();
							display.getSize(size);
							int width = size.x;
							int height = size.y;

							int realX = bitmap.getWidth() * x / width;
							int realY = bitmap.getHeight() * y / height+mActionBarSize;

							final Bitmap changedBitmap = ImageColorService.replaceArea(bitmap, realX, realY, replacingColor );

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
				return true;
			}
		});
	}


	private void setReplacingColor(int position)
	{
		selectedColorImageView.setBackgroundColor(colors[position]);
		replacingColor = colors[position];
	}



}
