package com.hck.getmoney.widget;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.hck.kedouzq.R;



public class PopupWindowController {

	private final int REQUEST_CODE_TAKE_PHOTO;
	private final int REQUEST_CODE_CHOOSE_PIC;
	private Activity activity;
	private PopupWindow popupWindow;
	private Button takePhotoBtn;
	private Button choosePicBtn;
	private Button cancelBtn;
	/**使用
	 * public void chicePhoto(View view) {
		PopupController popupController = new PopupController(this,
				REQUEST_CODE_TAKE_PHOTO, REQUEST_CODE_CHOOSE_PIC);
		popupController.checkPopupWindow();
		
		popupController.getPopupWindow().showAsDropDown(view);

	}
	 * @param activity
	 * @param take_photo_code
	 * @param choose_pic_code
	 */
	public PopupWindowController(Activity activity, int take_photo_code, int choose_pic_code) {
		this.activity = activity;
		this.REQUEST_CODE_TAKE_PHOTO = take_photo_code;
		this.REQUEST_CODE_CHOOSE_PIC = choose_pic_code;
	}
	
	public PopupWindow getPopupWindow() {
		return popupWindow;
	}
	
	/***
	 * 获取PopupWindow实例
	 */
	public void checkPopupWindow() {
		if (null != popupWindow) {
			popupWindow.dismiss();
			return;
		} else {
			initPopuptWindow();
		}
	}
	
	private void initPopuptWindow() {
		// 获取自定义布局文件pop.xml的视图
		View popupWindow_view = activity.getLayoutInflater().inflate(R.layout.alert, null, false);
		// 创建PopupWindow实例,宽度和高度
		popupWindow = new PopupWindow(popupWindow_view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		// 设置动画效果
	//	popupWindow.setAnimationStyle(R.style.animation_fade);
		// 点击其他地方消失
		popupWindow_view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});
		// pop.xml视图里面的控件
		//takePhotoBtn = (Button) popupWindow_view.findViewById(R.id.take_photo);
		//choosePicBtn = (Button) popupWindow_view.findViewById(R.id.choose_pic);
		//cancelBtn = (Button) popupWindow_view.findViewById(R.id.cancel);
		
		takePhotoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 这里可以执行相关操作
				popupWindow.dismiss();
				popupWindow = null;
	            String state = Environment.getExternalStorageState(); // 获取系统的存储状态
	            if (state.equals(Environment.MEDIA_MOUNTED)) { // 被分区,有读和写的权限
	            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 设置 intent 的行为为拍照
	            	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
	            			Environment.getExternalStorageDirectory(), "pic.png")));
	            	activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
	            } else {
	            	Toast.makeText(activity, "SD卡无读写权限", Toast.LENGTH_LONG).show();
	            }
	            /*两种方法，一种是直接调用系统的照相Intent，使用onActivityResult获取图片资源；
	             * 一种是用SurfaceView自己控制界面。
	             * 两种各有优缺点，前一种直接使用系统照相程序，会自带了很多系统功能，比如焦距调节，闪光灯调节之类的.
	             * 后一种可以自定义界面，添加些自己需要增加的 功能。但竖屏拍照的预览照片角度不对。*/
			}
		});
		
		choosePicBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 这里可以执行相关操作
				popupWindow.dismiss();
				popupWindow = null;
				// 点击选择图片时所要调用的代码
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				 // 定义一个明确的类型
				activity.startActivityForResult(intent, REQUEST_CODE_CHOOSE_PIC);
			}
		});
		// 关闭
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 这里可以执行相关操作
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
	}
}
