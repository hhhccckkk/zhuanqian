package com.hck.zhuanqian.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageUtils {

	public static byte[] bitmapToByte(Bitmap b) { // 把一个bitmap转换成字节数组
		if (b == null) {
			return null;
		}

		ByteArrayOutputStream o = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 100, o);
		return o.toByteArray();
	}

	public static Bitmap byteToBitmap(byte[] b) { // 把字节数组转换成bitmap
		return (b == null || b.length == 0) ? null : BitmapFactory
				.decodeByteArray(b, 0, b.length);
	}

	public static Bitmap drawableToBitmap(Drawable d) { // 用Drawable获取一个bitmap
		return d == null ? null : ((BitmapDrawable) d).getBitmap();
	}

	public static Drawable bitmapToDrawable(Bitmap b) { // Bitmap获取Drawable
		return b == null ? null : new BitmapDrawable(b);
	}

	public static byte[] drawableToByte(Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}

	public static Drawable byteToDrawable(byte[] b) {
		return bitmapToDrawable(byteToBitmap(b));
	}

	public static InputStream getInputStreamFromUrl(String imageUrl,
			int readTimeOutMillis) { // 用一个url地址获取一个流对象
		return getInputStreamFromUrl(imageUrl, readTimeOutMillis, true);
	}

	public static InputStream getInputStreamFromUrl(String imageUrl,
			int readTimeOutMillis, boolean isConnecionKeepAlive) {
		InputStream stream = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// default is keep alive
			if (!isConnecionKeepAlive) {
				con.addRequestProperty("Connection", "false");
			}
			if (readTimeOutMillis > 0) {
				con.setReadTimeout(readTimeOutMillis);
			}
			stream = con.getInputStream();
		} catch (MalformedURLException e) {
			closeInputStream(stream);
			throw new RuntimeException("MalformedURLException occurred. ", e);
		} catch (IOException e) {
			closeInputStream(stream);
			throw new RuntimeException("IOException occurred. ", e);
		}
		return stream;
	}

	public static Drawable getDrawableFromUrl(String imageUrl,
			int readTimeOutMillis) { // 通过url获取一个Drawable
		return getDrawableFromUrl(imageUrl, readTimeOutMillis, true);
	}

	public static Drawable getDrawableFromUrl(String imageUrl,
			int readTimeOutMillis, boolean isConnecionKeepAlive) {
		InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOutMillis,
				isConnecionKeepAlive);
		Drawable d = Drawable.createFromStream(stream, "src");
		closeInputStream(stream);
		return d;
	}

	public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut) { // 通过url获取一个Bitmap
		return getBitmapFromUrl(imageUrl, readTimeOut, true);
	}

	public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut,
			boolean isConnecionKeepAlive) {
		InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOut,
				isConnecionKeepAlive);
		Bitmap b = BitmapFactory.decodeStream(stream);
		closeInputStream(stream);
		return b;
	}

	public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) { // 缩放图片
		return scaleImage(org, (float) newWidth / org.getWidth(),
				(float) newHeight / org.getHeight());
	}

	public static Bitmap scaleImage(Bitmap org, float scaleWidth,
			float scaleHeight) {
		if (org == null) {
			return null;
		}

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(),
				matrix, true);
	}

	private static void closeInputStream(InputStream s) {
		if (s == null) {
			return;
		}

		try {
			s.close();
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		}
	}
}
