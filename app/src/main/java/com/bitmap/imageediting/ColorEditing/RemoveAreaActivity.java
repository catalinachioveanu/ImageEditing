package com.bitmap.imageediting.ColorEditing;

import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bitmap.imageediting.R;
import com.bitmap.imageeditinglibrary.ImageColorService;

public class RemoveAreaActivity extends ActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remove_area);

		final ImageView imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, final MotionEvent event)
			{
				int action = event.getAction();

				if (action == MotionEvent.ACTION_DOWN)
				{

					final ProgressDialog dialog = new ProgressDialog(RemoveAreaActivity.this);
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

							final TypedArray styledAttributes = RemoveAreaActivity.this.getTheme().obtainStyledAttributes(
									new int[]{android.R.attr.actionBarSize});
							int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
							styledAttributes.recycle();


							final Bitmap bitmap = ((BitmapDrawable) (imageView.getDrawable())).getBitmap();

							Display display = getWindowManager().getDefaultDisplay();
							Point size = new Point();
							display.getSize(size);
							int width = size.x;

							int realX = bitmap.getWidth() * x / width;
							int realY = bitmap.getWidth() * y / width - mActionBarSize;

							final Bitmap changedBitmap = ImageColorService.removeArea(bitmap, realX, realY);

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

}
