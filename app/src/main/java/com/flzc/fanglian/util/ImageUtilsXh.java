package com.flzc.fanglian.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;

public class ImageUtilsXh {
	public static final int SCALE_IMAGE_WIDTH = 640;
	public static final int SCALE_IMAGE_HEIGHT = 960;

	public static Bitmap getVideoThumbnail(String paramString, int paramInt1,
			int paramInt2, int paramInt3) {
		Bitmap localBitmap = null;
		localBitmap = ThumbnailUtils.createVideoThumbnail(paramString,
				paramInt3);
		localBitmap = ThumbnailUtils.extractThumbnail(localBitmap, paramInt1,
				paramInt2, 2);
		return localBitmap;
	}

	public static Bitmap decodeScaleImage(String paramString, int paramInt1,
			int paramInt2) {
		BitmapFactory.Options localOptions = getBitmapOptions(paramString);
		int i = calculateInSampleSize(localOptions, paramInt1, paramInt2);
		localOptions.inSampleSize = i;
		localOptions.inJustDecodeBounds = false;
		Bitmap localBitmap1 = BitmapFactory.decodeFile(paramString,
				localOptions);
		int j = readPictureDegree(paramString);
		Bitmap localBitmap2 = null;
		if ((localBitmap1 != null) && (j != 0)) {
			localBitmap2 = rotaingImageView(j, localBitmap1);
			localBitmap1.recycle();
			localBitmap1 = null;
			return localBitmap2;
		}
		return localBitmap1;
	}

	public static Bitmap decodeScaleImage(Context paramContext, int paramInt1,
			int paramInt2, int paramInt3) {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(paramContext.getResources(), paramInt1,
				localOptions);
		int i = calculateInSampleSize(localOptions, paramInt2, paramInt3);
		localOptions.inSampleSize = i;
		localOptions.inJustDecodeBounds = false;
		Bitmap localBitmap = BitmapFactory.decodeResource(
				paramContext.getResources(), paramInt1, localOptions);
		return localBitmap;
	}

	public static int calculateInSampleSize(BitmapFactory.Options paramOptions,
			int paramInt1, int paramInt2) {
		int i = paramOptions.outHeight;
		int j = paramOptions.outWidth;
		int k = 1;
		if ((i > paramInt2) || (j > paramInt1)) {
			int m = Math.round(i / paramInt2);
			int n = Math.round(j / paramInt1);
			k = m > n ? m : n;
		}
		return k;
	}

	public static String getThumbnailImage(String paramString, int paramInt) {
		Bitmap localBitmap = decodeScaleImage(paramString, paramInt, paramInt);
		try {
			File localFile = File.createTempFile("image", ".jpg");
			FileOutputStream localFileOutputStream = new FileOutputStream(
					localFile);
			localBitmap.compress(Bitmap.CompressFormat.JPEG, 60,
					localFileOutputStream);
			localFileOutputStream.close();
			return localFile.getAbsolutePath();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return paramString;
	}

	public static String getThumbnailImage(String thupath, String paramString) {
		Bitmap localBitmap = decodeScaleImage(paramString, 640, 960);
		if(localBitmap!=null){
			try {
				File localFile = new File(thupath);
				if (!localFile.exists()) {
					localFile.createNewFile();
				}
				FileOutputStream localFileOutputStream = new FileOutputStream(
						localFile);
				localBitmap.compress(Bitmap.CompressFormat.JPEG, 60,
						localFileOutputStream);
				localFileOutputStream.close();
				return localFile.getAbsolutePath();
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return paramString;
	}
	
	public static String getResetImage(String thupath, String paramString) {
		Bitmap localBitmap = decodeScaleImage(paramString, 640, 960);
		if(localBitmap!=null){
			try {
				File localFile = new File(thupath);
				if (!localFile.exists()) {
					localFile.createNewFile();
				}
				FileOutputStream localFileOutputStream = new FileOutputStream(
						localFile);
				localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
						localFileOutputStream);
				localFileOutputStream.close();
				return localFile.getAbsolutePath();
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
		return paramString;
	}

	public static String getScaledImage(Context paramContext, String paramString) {
		File localFile1 = new File(paramString);
		if (!localFile1.exists()) {
			return paramString;
		}
		long l = localFile1.length();
		if (l <= 102400L) {
			return paramString;
		}
		Bitmap localBitmap = decodeScaleImage(paramString, 640, 960);
		try {
			File localFile2 = File.createTempFile("image", ".jpg",
					paramContext.getFilesDir());
			FileOutputStream localFileOutputStream = new FileOutputStream(
					localFile2);
			localBitmap.compress(Bitmap.CompressFormat.JPEG, 70,
					localFileOutputStream);
			localFileOutputStream.close();
			return localFile2.getAbsolutePath();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return paramString;
	}

	public static String getScaledImage(Context paramContext,
			String paramString, int paramInt) {
		File localFile1 = new File(paramString);
		if (localFile1.exists()) {
			long l = localFile1.length();
			if (l > 102400L) {
				Bitmap localBitmap = decodeScaleImage(paramString, 640, 960);
				try {
					File localFile2 = new File(
							paramContext.getExternalCacheDir(), "eaemobTemp"
									+ paramInt + ".jpg");
					FileOutputStream localFileOutputStream = new FileOutputStream(
							localFile2);
					localBitmap.compress(Bitmap.CompressFormat.JPEG, 60,
							localFileOutputStream);
					localFileOutputStream.close();
					return localFile2.getAbsolutePath();
				} catch (Exception localException) {
					localException.printStackTrace();
				}
			}
		}
		return paramString;
	}

	public static int readPictureDegree(String paramString) {
		int i = 0;
		try {
			ExifInterface localExifInterface = new ExifInterface(paramString);
			int j = localExifInterface.getAttributeInt("Orientation", 1);
			switch (j) {
			case 6:
				i = 90;
				break;
			case 3:
				i = 180;
				break;
			case 8:
				i = 270;
			}
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
		return i;
	}

	public static Bitmap rotaingImageView(int paramInt, Bitmap paramBitmap) {
		Matrix localMatrix = new Matrix();
		localMatrix.postRotate(paramInt);
		Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0,
				paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix,
				true);
		return localBitmap;
	}

	public static BitmapFactory.Options getBitmapOptions(String paramString) {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(paramString, localOptions);
		return localOptions;
	}
}
