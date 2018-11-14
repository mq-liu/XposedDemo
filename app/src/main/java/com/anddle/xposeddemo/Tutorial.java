package com.anddle.xposeddemo;

import android.app.Application;
import android.content.Context;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

import javax.crypto.spec.SecretKeySpec;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Tutorial implements IXposedHookLoadPackage {
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("Loaded app: " + lpparam.packageName);


        findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                findAndHookMethod("com.xxx.xxx.xxx", lpparam.classLoader, "getXxxx",
                        byte[].class, // 参数列表
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Object[] args = param.args; // 参数
                                byte[] result = (byte[])param.getResult(); // 返回值
                                XposedBridge.log("Encoded:" + Arrays.toString(result));
                                XposedBridge.log("result:" + new String(result));

                            }
                        });
            }
        });
    }
}
