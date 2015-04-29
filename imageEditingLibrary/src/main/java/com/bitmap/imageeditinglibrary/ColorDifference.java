package com.bitmap.imageeditinglibrary;

import android.graphics.Color;

/**
 * Created by Catalina on 17/03/2015.
 *
 */
public class ColorDifference
{
	static public double findDifference(int argb1, int argb2)
	{
		int red1 = Color.red(argb1);
		int blue1 = Color.blue(argb1);
		int green1 = Color.green(argb1);

		int red2 = Color.red(argb2);
		int blue2 = Color.blue(argb2);
		int green2 = Color.green(argb2);

		int diffRed   = Math.abs(red1   - red2);
		int diffGreen = Math.abs(green1 - green2);
		int diffBlue  = Math.abs(blue1  - blue2);

		float pctDiffRed   = (float)diffRed   / 255;
		float pctDiffGreen = (float)diffGreen / 255;
		float pctDiffBlue   = (float)diffBlue  / 255;


		double d = (pctDiffRed + pctDiffGreen + pctDiffBlue) / 3 * 100;
		return d;
	}

}
