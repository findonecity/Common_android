package com.example.zj.common_android.util;
/**
 * Created by ZhengJie on 2017/10/30.
 * is use for:静态地址
 */
public interface Constant {
    class url{
        public static final String BASE ="http://192.168.2.192:8080/";//baseUrl

        //测试地址
        public static final String TEST = BASE + "test.do";
        //android版本号
        public static final String Ver =  "android" + android.os.Build.VERSION.RELEASE;
    }

    /**
     * 跳转测试设置
     */
    class jump {

    }

    /***
     * 静态参数
     */
    class sign{

    }

    /**
     * 广播类
     */
    class boardcast{
    }

    /**
     * 系统
     */
    class System{
        public static final String OS = "AOI";
    }
}
