package com.march.reaper.common;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.march.reaper.R;
import com.march.reaper.utils.Lg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by march on 16/7/15.
 * 短信验证码平台
 */
public class SMSHelper {

    //    EVENT_GET_SUPPORTED_COUNTRIES = 1;
    //    EVENT_GET_CONTACTS = 4;
//    EVENT_SUBMIT_USER_INFO = 5;
//    EVENT_GET_FRIENDS_IN_APP = 6;
//    EVENT_GET_NEW_FRIENDS_COUNT = 7;
//    RESULT_COMPLETE = -1;
//    RESULT_ERROR = 0;
//    EVENT_GET_VERIFICATION_CODE = 2;
//    EVENT_SUBMIT_VERIFICATION_CODE = 3;
//    EVENT_GET_VOICE_VERIFICATION_CODE = 8;


    private static SMSHelper mInst;
    private Context context;

    private SMSHelper(Context context) {
        this.context = context;
    }

    public static SMSHelper newInst(Context context) {
        if (mInst == null) {
            synchronized (SMSHelper.class) {
                if (mInst == null) {
                    mInst = new SMSHelper(context);
                }
            }
        }
        return mInst;
    }

    public static SMSHelper get() {
        return mInst;
    }


    public void registerHandler(final SmsResultListener listener) {
//        SMSSDK.initSDK(context, context.getString(R.string.mob_sms_appkey), context.getString(R.string.mob_sms_appsecret));
//        EventHandler eh = new EventHandler() {
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    Lg.e("回调完成");
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                        Lg.e("提交验证码成功");
//                        if (listener != null)
//                            listener.onSubmitCodeSucceed();
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                        Lg.e("获取验证码成功");
//                        if (listener != null)
//                            listener.onGetCodeSucceed();
//                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
//                        Lg.e("返回支持发送验证码的国家列表" + data.toString());
//                    } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
//                        Lg.e("获取语音验证码成功");
//                        if (listener != null)
//                            listener.onGetVoiceSucceed();
//                    }
//                } else {
//                    ((Throwable) data).printStackTrace();
//                }
//            }
//        };
//        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    public void getCode(String phone) {
//        SMSSDK.getVerificationCode("86", phone);
    }

    public void getVoiceCode(String phone) {
//        SMSSDK.getVoiceVerifyCode("86", phone);
    }

    public void submitCode(String phone, String code) {
//        SMSSDK.submitVerificationCode("86", phone, code);
    }

    public boolean isMobile(String str) {
        if (str.length() <= 0 || str.length() > 11) {
            return false;
        }
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public interface SmsResultListener {
        void onGetCodeSucceed();

        void onSubmitCodeSucceed();

        void onGetVoiceSucceed();

        void onError();
    }
}
