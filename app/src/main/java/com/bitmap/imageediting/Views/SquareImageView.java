package com.bitmap.imageediting.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bitmap.imageediting.R;

/**
 * Created by Catalina on 23/03/2015.
 */
public class SquareImageView extends ImageView {
	private SquareRelativeLayout.Fixed side = SquareRelativeLayout.Fixed.Width;

	public SquareImageView(Context context, SquareRelativeLayout.Fixed side) {
		super(context);
		this.side = side;
	}

	public SquareImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquareView);
		String value = a.getString(R.styleable.SquareView_side);
		 side = SquareRelativeLayout.Fixed.valueOf(value);
		a.recycle();
	}

	public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquareView);
		String value = a.getString(R.styleable.SquareView_side);
		 side = SquareRelativeLayout.Fixed.valueOf(value);
		a.recycle();
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (side == SquareRelativeLayout.Fixed.Height) {
			int dimension = getMeasuredHeight();
			if (dimension != 0) {
				setMeasuredDimension(dimension, dimension);
			}
		} else if (side == SquareRelativeLayout.Fixed.Width) {
			int dimension = getMeasuredWidth();
			if (dimension != 0) {
				setMeasuredDimension(dimension, dimension);
			}
		}
	}
}