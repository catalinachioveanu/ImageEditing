package com.bitmap.imageediting.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bitmap.imageediting.R;

/**
 * Created by Catalina on 23/03/2015.
 *
 */
public class ColorListAdapter extends ArrayAdapter<Integer>
{
	private Context context;
	private Integer[] values;


	@Override
	public int getCount()
	{
		return values.length;
	}

	public ColorListAdapter(Context context, Integer[] values)
	{
		super(context, R.layout.item_color, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.item_color, parent, false);
			final ViewHolder holder = new ViewHolder();
			holder.swatchImage = (ImageView) convertView.findViewById(R.id.color_picker_swatch);
			convertView.setTag(holder);
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();

		int color = values[position];
		holder.swatchImage.setBackgroundColor(color);
		return convertView;
	}

	private class ViewHolder
	{
		public ImageView swatchImage;
	}
}

