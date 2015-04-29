package com.bitmap.imageeditinglibrary;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Catalina on 17/03/2015.
 * Image service
 */
public class ImageColorService
{
	/**
	 * Dumb color removal for Bitmap. Goes through every pixel of the bitmap and replaces it with Color.TRANSPARENT
	 * This is not ideal because if the focus of your image contains the colorToRemove it will be removed as well, use carefully.
	 * Faster than removeArea
	 *
	 * @param initialBitmap the original bitmap image
	 * @param colorToRemove the color that needs removing
	 * @return The bitmap with the removed color
	 */
	public static Bitmap removeColor(Bitmap initialBitmap, int colorToRemove)
	{
		Bitmap editedBitmap = initialBitmap.copy(Bitmap.Config.ARGB_8888, true);
		editedBitmap.setHasAlpha(true);
		for (int i = 0; i < editedBitmap.getWidth(); i++)
		{
			for (int j = 0; j < editedBitmap.getHeight(); j++)
			{
				int foo = editedBitmap.getPixel(i, j);
				if (foo == colorToRemove)
				{
					editedBitmap.setPixel(i, j, Color.TRANSPARENT);
				}
			}
		}
		return replaceColor(initialBitmap, colorToRemove, Color.TRANSPARENT);
	}

	/**
	 * Dumb color replace for Bitmap. Goes through every pixel of the bitmap and replaces it with Color.TRANSPARENT
	 * This is not ideal because if the focus of your image contains the colorToRemove it will be replaced as well, use carefully.
	 *
	 * @param initialBitmap      the original bitmap image
	 * @param colorToReplace     the color that needs replacing
	 * @param colorToReplaceWith the color to replace with
	 * @return The bitmap with the removed color
	 */
	public static Bitmap replaceColor(Bitmap initialBitmap, int colorToReplace, int colorToReplaceWith)
	{
		Bitmap editedBitmap = initialBitmap.copy(Bitmap.Config.ARGB_8888, true);
		editedBitmap.setHasAlpha(true);
		for (int i = 0; i < editedBitmap.getWidth(); i++)
		{
			for (int j = 0; j < editedBitmap.getHeight(); j++)
			{
				int foo = editedBitmap.getPixel(i, j);
				if (foo == colorToReplace)
				{
					editedBitmap.setPixel(i, j, colorToReplaceWith);
				}
			}
		}
		return editedBitmap;
	}


	/**
	 * Removes an area of the bitmap around a given point(reference position) in the bitmap
	 *
	 * @param bitmap The bitmap to be edited
	 * @param X      The X coordinate of the reference position
	 * @param Y      The Y coordinate of the reference position
	 * @return The edited bitmap with the missing area
	 */
	public static Bitmap removeArea(Bitmap bitmap, int X, int Y)
	{
		return replaceArea(bitmap, X, Y, Color.TRANSPARENT);
	}

	/**
	 * Replaces an area of the bitmap around a given point(reference position) in the bitmap with another color
	 *
	 * @param bitmap         The bitmap to be edited
	 * @param X              The X coordinate of the reference position
	 * @param Y              The Y coordinate of the reference position
	 * @param replacingColor The color to replace the color at the given position and its surroundings
	 * @return The edited bitmap with the missing area
	 */
	public static Bitmap replaceArea(Bitmap bitmap, int X, int Y, int replacingColor)
	{
		Bitmap newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
		newBitmap.setHasAlpha(true);
		int pixel = newBitmap.getPixel(X, Y);

		LinkedList<Point> queue = new LinkedList<>();
		queue.add(new Point(X, Y));

		int width = newBitmap.getWidth();
		int height = newBitmap.getHeight();

		while (!queue.isEmpty())
		{
			Point currentPoint = queue.pop();
			if (newBitmap.getPixel(currentPoint.X, currentPoint.Y) != replacingColor)
			{
				newBitmap.setPixel(currentPoint.X, currentPoint.Y, replacingColor);
				if (currentPoint.X - 1 >= 0)
				{
					if (newBitmap.getPixel(currentPoint.X - 1, currentPoint.Y) == pixel)
					{
						queue.add(new Point(currentPoint.X - 1, currentPoint.Y));
					}
				}

				if (currentPoint.X + 1 < width)
				{
					if (newBitmap.getPixel(currentPoint.X + 1, currentPoint.Y) == pixel)
					{
						queue.add(new Point(currentPoint.X + 1, currentPoint.Y));
					}
				}

				if (currentPoint.Y - 1 >= 0)
				{
					if (newBitmap.getPixel(currentPoint.X, currentPoint.Y - 1) == pixel)
					{
						queue.add(new Point(currentPoint.X, currentPoint.Y - 1));
					}
				}

				if (currentPoint.Y + 1 < height)
				{
					if (newBitmap.getPixel(currentPoint.X, currentPoint.Y + 1) == pixel)
					{
						queue.add(new Point(currentPoint.X, currentPoint.Y + 1));
					}
				}
			}
		}


		return newBitmap;
	}

	/**
	 * Replaces an area of the bitmap around a given point(reference position) in the bitmap with another color with tolerance
	 * Tolerance reflects on the fact that other colors will be selected as well, not just the color at the given position if these are similar
	 *
	 * @param bitmap         The bitmap to be edited
	 * @param X              The X coordinate of the reference position
	 * @param Y              The Y coordinate of the reference position
	 * @param replacingColor The color to replace the color at the given position and its surroundings
	 * @param tolerance      The tolerance of the image replacement
	 *                       Between 0 and 255
	 *                       If the tolerance is low the colors matched will be fewer
	 * @return The edited bitmap with the missing area
	 */
	public static Bitmap replaceAreaWithTolerance(Bitmap bitmap, int X, int Y, int replacingColor, double tolerance)
	{
		Bitmap newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
		newBitmap.setHasAlpha(true);
		int pixel = newBitmap.getPixel(X, Y);

		LinkedList<Point> queue = new LinkedList<>();
		queue.add(new Point(X, Y));

		int width = newBitmap.getWidth();
		int height = newBitmap.getHeight();

		while (!queue.isEmpty())
		{
			Point currentPoint = queue.pop();
			if (newBitmap.getPixel(currentPoint.X, currentPoint.Y) != replacingColor)
			{
				newBitmap.setPixel(currentPoint.X, currentPoint.Y, replacingColor);
				if (currentPoint.X - 1 >= 0)
				{
					double t = ColorDifference.findDifference(newBitmap.getPixel(currentPoint.X - 1, currentPoint.Y), pixel);
					if (t <= tolerance)
					{
						queue.add(new Point(currentPoint.X - 1, currentPoint.Y));
					}
				}

				if (currentPoint.X + 1 < width)
				{
					if (ColorDifference.findDifference(newBitmap.getPixel(currentPoint.X + 1, currentPoint.Y), pixel) <= tolerance)
					{
						queue.add(new Point(currentPoint.X + 1, currentPoint.Y));
					}
				}

				if (currentPoint.Y - 1 >= 0)
				{
					if (ColorDifference.findDifference(newBitmap.getPixel(currentPoint.X, currentPoint.Y - 1), pixel) <= tolerance)
					{
						queue.add(new Point(currentPoint.X, currentPoint.Y - 1));
					}
				}

				if (currentPoint.Y + 1 < height)
				{
					if (ColorDifference.findDifference(newBitmap.getPixel(currentPoint.X, currentPoint.Y + 1), pixel) <= tolerance)
					{
						queue.add(new Point(currentPoint.X, currentPoint.Y + 1));
					}
				}
			}
		}
		return newBitmap;
	}


	/**
	 * Returns all the colors existent in a Bitmap
	 *
	 * @param bitmap Bitmap to be analysed
	 * @return The colors found in the bitmap
	 */
	public static Integer[] getColorsFromBitmap(Bitmap bitmap)
	{
		ArrayList<Integer> colors = new ArrayList<>();
		for (int i = 0; i < bitmap.getWidth(); i++)
		{
			for (int j = 0; j < bitmap.getHeight(); j++)
			{
				if (!colors.contains(bitmap.getPixel(i, j)))
				{
					colors.add(bitmap.getPixel(i, j));
				}
			}
		}

		Integer[] colorsArray = new Integer[colors.size()];
		for (int i = 0; i < colors.size(); i++)
		{
			colorsArray[i] = colors.get(i);

		}

		return colorsArray;
	}

	public static Bitmap removeAreaWithTolerance(Bitmap bitmap, int realX, int realY, int tolerance)
	{
		return replaceAreaWithTolerance(bitmap, realX, realY, Color.TRANSPARENT, tolerance);
	}


	/**
	 * Adds glow around a bitmap
	 * @param image The bitmap that needs to be modified
	 * @param r The RED RGB value of the glow color
	 * @param g The GREEN RGB value of the glow color
	 * @param b The BLUE RGB value of the glow color
	 * @return Bitmap with glow
	 */
	public static Bitmap setBitmapGlow(Bitmap image, int r, int g, int b)
	{
		int margin = 10;
		int halfMargin = margin / 2;
		int glowRadius = 40;

		// extract the alpha from the source image
		Bitmap alpha = image.extractAlpha();

		// The output bitmap (with the icon + glow)
		Bitmap bmp = Bitmap.createBitmap(image.getWidth() + margin, image.getHeight() + margin, Bitmap.Config.ARGB_8888);

		// The canvas to paint on the image
		Canvas canvas = new Canvas(bmp);

		for (int i = glowRadius; i >= 0; i -= 10)
		{
			// the glow color
			int glowColor = Color.argb(100, r, g, b);
			Paint paint = new Paint();
			paint.setColor(glowColor);


			// outer glow
			paint.setMaskFilter(new BlurMaskFilter((glowRadius + 10 - i), BlurMaskFilter.Blur.OUTER));//For Inner glow set Blur.INNER
			canvas.drawBitmap(alpha, halfMargin, halfMargin, paint);
		}
		// original icon
		canvas.drawBitmap(image, halfMargin, halfMargin, null);

		return bmp;
	}


	/**
	 * NOT WORKING ATM!!!
	 * Softens the edges of the non transparent area of a bitmap. Fades the color of the edge to transparent
	 *
	 * @param bitmap The bitmap to be modified
	 * @param radius The radius of the softening of the edges
	 * @return Modified bitmap with softened edges
	 */
	public static Bitmap softenEdges(Bitmap bitmap, int radius)
	{
		Bitmap newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
		newBitmap.setHasAlpha(true);

		boolean[][] edgeArray = calculateEdge(bitmap);

		for (int i = 0; i < newBitmap.getWidth(); i++)
		{
			for (int j = 0; j < newBitmap.getHeight(); j++)
			{
				if (edgeArray[i][j])
				{
					for (int r = radius; r >= 1; r--)
					{
						int color = calculateColor(newBitmap.getPixel(i, j), radius, r);
						int plusColor = calculateColor(newBitmap.getPixel(i, j), radius, (radius - r));

						boolean changed = false;
						//top
						if (j - r >= 0)
							if (newBitmap.getPixel(i, j - r) == Color.TRANSPARENT)
							{
								newBitmap.setPixel(i, j - r, color);
								changed = true;
							}

						//left
						if (i - r >= 0)
							if (newBitmap.getPixel(i - r, j) == Color.TRANSPARENT)
							{
								newBitmap.setPixel(i - r, j, color);
								changed = true;
							}

						//bottom
						if (j + r < bitmap.getHeight())
							if (newBitmap.getPixel(i, j + r) == Color.TRANSPARENT)
							{
								newBitmap.setPixel(i, j + r, plusColor);
								changed = true;
							}


						//right
						if (i + r < newBitmap.getWidth())
							if (newBitmap.getPixel(i + r, j) == Color.TRANSPARENT)
							{
								newBitmap.setPixel(i + r, j, plusColor);
								changed = true;
							}


						if (!changed)
						{
							break;
						}

					}


				}
			}
		}

		return newBitmap;
	}

	private static boolean[][] calculateEdge(Bitmap bitmap)
	{
		boolean[][] array = new boolean[bitmap.getWidth()][bitmap.getHeight()];

		for (int i = 0; i < bitmap.getWidth(); i++)
		{
			for (int j = 0; j < bitmap.getHeight(); j++)
			{
				int point = bitmap.getPixel(i, j);
				if (point != Color.TRANSPARENT)
				{
					array[i][j] = true;
				}
			}
		}

		return array;
	}

	private static int calculateColor(int color, int radius, int r)
	{
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);
		int alpha = Color.alpha(color);

		int finalAlpha = (radius - r) * alpha / radius;

		return Color.argb(finalAlpha, red, green, blue);
	}
}
