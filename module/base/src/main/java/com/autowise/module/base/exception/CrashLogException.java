package com.autowise.module.base.exception;

import android.widget.Toast;

import com.autowise.module.base.AppContext;
import com.autowise.module.base.common.utils.ToastUtils;
import com.autowise.module.base.common.utils.file.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: yun.chen1.o
 * Date: 2021/8/17
 * Description:
 * FIXME
 */
public class CrashLogException implements Thread.UncaughtExceptionHandler {

    private static volatile CrashLogException instance = null;

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private String Log_PATH = FileUtils.getExternalFilePath(AppContext.app, "crashlog/").getPath();

    private CrashLogException() {
    }

    public static CrashLogException getInstance() {
        if (instance == null) {
            synchronized (CrashLogException.class) {
                if (instance == null) {
                    instance = new CrashLogException();
                }
            }
        }
        return instance;
    }

    /**
     * 异常处理
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handleException(e);
        mDefaultHandler.uncaughtException(t, e);

//        if (!handleException(e) && mDefaultHandler != null) {
//            // 如果用户没有处理则让系统默认的异常处理器来处理
//            mDefaultHandler.uncaughtException(t, e);
//        } else {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException interruptedException) {
//                interruptedException.printStackTrace();
//            }
//            // 退出程序
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
//        }
    }

    /**
     * 初始化
     */
    public void init() {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该本类为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 自定义错误处理
     */
    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }
        ToastUtils.INSTANCE.showToast("很抱歉,程序出现异常,即将退出", Toast.LENGTH_SHORT);
        try {
            new StringWriter();
            BufferedWriter bw = null;
            bw = new BufferedWriter(new FileWriter(Log_PATH + "/crash.txt", true));//追加
            PrintWriter printWriter = new PrintWriter(bw);
            e.printStackTrace(printWriter);
            FileUtils.closeIO(printWriter);
            FileUtils.closeIO(bw);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return true;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public String getErrorLog() {
        File file = new File(Log_PATH + "/crash.txt");
        String result = FileUtils.readStringFromFile(file);
        FileUtils.clearFileContent(file);
        return result;
    }
}
