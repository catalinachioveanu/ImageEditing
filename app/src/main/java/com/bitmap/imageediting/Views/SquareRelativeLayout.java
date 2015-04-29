package com.bitmap.imageediting.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.bitmap.imageediting.R;


public class SquareRelativeLayout extends RelativeLayout
{

	private Fixed side = Fixed.Width;

	public SquareRelativeLayout(Context context, Fixed side)
	{
		super(context);
		this.side = side;
	}

	public SquareRelativeLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquareView);
		String value = a.getString(R.styleable.SquareView_side);
		side = Fixed.valueOf(value);
		a.recycle();
	}

	public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleIttr)
	{
		super(context, attrs, defStyleIttr);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquareView);
		String value = a.getString(R.styleable.SquareView_side);
		side = Fixed.valueOf(value);
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (side == Fixed.Height)
		{
			int dimension = getMeasuredHeight();
			if (dimension != 0)
			{
				setMeasuredDimension(dimension, dimension);
			}
		}
		else if (side == Fixed.Width)
		{
			int dimension = getMeasuredWidth();
			if (dimension != 0)
			{
				setMeasuredDimension(dimension, dimension);
			}
		}
	}

	public enum Fixed {
		Height,
		Width
	}

}
