package com.hck.getmoney.util;

import com.tencent.tauth.UiError;

public interface BaseTenXunCallBack {
   public void OnTenXunSuccess(String result,int type);
   public void cancelTenXun(String result,int type);
   public void OnTenXunFailue(UiError result,int type);
}
