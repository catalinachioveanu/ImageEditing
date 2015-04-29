package com.bitmap.imageediting;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bitmap.imageediting.ColorEditing.RemoveAreaActivity;
import com.bitmap.imageediting.ColorEditing.RemoveAreaWithToleranceActivity;
import com.bitmap.imageediting.ColorEditing.RemoveColorActivity;
import com.bitmap.imageediting.ColorEditing.ReplaceAreaActivity;
import com.bitmap.imageediting.ColorEditing.ReplaceAreaWithToleranceActivity;
import com.bitmap.imageediting.ColorEditing.ReplaceColorActivity;
import com.bitmap.imageediting.ColorEditing.SoftenEdgesActivity;


public class MainActivity extends ListActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView list = (ListView)findViewById(android.R.id.list);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"Remove color", "Replace color", "Remove area", "Replace area", "Remove area with tolerance", "Replace area with tolerance", "Soften edges"});
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				selectView(position);
			}
		});
	}

	private void selectView(int position)
	{
		switch (position){
			case 0:
				Intent removeColorIntent = new Intent(this, RemoveColorActivity.class);
				startActivity(removeColorIntent);
				break;

			case 1:
				Intent replaceColorIntent = new Intent(this, ReplaceColorActivity.class);
				startActivity(replaceColorIntent);
				break;

			case 2:
				Intent removeAreaIntent = new Intent(this, RemoveAreaActivity.class);
				startActivity(removeAreaIntent);
				break;

			case 3:
				Intent replaceAreaIntent = new Intent(this, ReplaceAreaActivity.class);
				startActivity(replaceAreaIntent);
				break;
			case 4:
				Intent removeAreaWithToleranceIntent = new Intent(this, RemoveAreaWithToleranceActivity.class);
				startActivity(removeAreaWithToleranceIntent);
				break;
			case 5:
				Intent replaceAreaWithToleranceIntent = new Intent(this, ReplaceAreaWithToleranceActivity.class);
				startActivity(replaceAreaWithToleranceIntent);
				break;
			case 6:
				Intent softenEdgesIntent = new Intent(this, SoftenEdgesActivity.class);
				startActivity(softenEdgesIntent);
				break;
		}
	}

}
