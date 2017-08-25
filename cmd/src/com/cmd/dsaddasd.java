/*
 * @(#)dsaddasd.java 2017-4-21 下午12:04:32 cmd Copyright 2017 Thuisoft, Inc. All
 * rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com.cmd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * dsaddasd
 * @author Administrator
 * @version 1.0
 *
 */
public class dsaddasd {
    /** THREAD_CORE_POOL_SIZE */
    private static final int THREAD_POOL_CORE_POOL_SIZE = 10;

    /** THREAD_MAX_POOL_SIZE */
    private static final int THREAD_POOL_MAX_POOL_SIZE = 10;

    /** THREAD_POOL_KEEP_ALIVE_SECONDS */
    private static final int THREAD_POOL_KEEP_ALIVE_SECONDS = 20;

    private static final ThreadPoolExecutor IMPORTTASTEXECUTOR = new ThreadPoolExecutor(THREAD_POOL_CORE_POOL_SIZE,
            THREAD_POOL_MAX_POOL_SIZE, THREAD_POOL_KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

    /** THREAD_CORE_POOL_SIZE */
    private static final int THREAD_POOL_CORE_POOL_SIZE1 = 10;

    /** THREAD_MAX_POOL_SIZE */
    private static final int THREAD_POOL_MAX_POOL_SIZE1 = 10;

    /** THREAD_POOL_KEEP_ALIVE_SECONDS */
    private static final int THREAD_POOL_KEEP_ALIVE_SECONDS1 = 60;

    private static final ThreadPoolExecutor IMPORTTASTEXECUTOR1 = new ThreadPoolExecutor(THREAD_POOL_CORE_POOL_SIZE1,
            THREAD_POOL_MAX_POOL_SIZE1, THREAD_POOL_KEEP_ALIVE_SECONDS1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

    /*
     * @param args
     */
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date d = dateFormat.parse("2016/04/30");
        System.out.println(d);
        //xxx();
        // System.out.println("22");
    }

    public static void xxx() {
        List<Future<Object>> allFutures = new ArrayList<Future<Object>>();
        for (int i = 0; i < 100; i++) {
            Callable<Object> run = new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    System.out.println("个线程==");
                    xxx1();
                    return true;
                }
            };
            Future<Object> result = IMPORTTASTEXECUTOR.submit(run);
            allFutures.add(result);
        }

        for (Future<Object> f : allFutures) {
            try {
                f.get();
            } catch (InterruptedException e) {
                System.out.println("1");
            } catch (ExecutionException e) {
                System.out.println("1");
            }
        }
        System.err.println("end=================");
        //xxx();
    }

    public static void xxx1() {
        List<Future<Object>> allFutures = new ArrayList<Future<Object>>();
        for (int j = 0; j < 5; j++) {
            Callable<Object> run = new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    //   Thread.sleep(1000);
                    return true;
                }
            };
            Future<Object> result = IMPORTTASTEXECUTOR1.submit(run);
            allFutures.add(result);
        }
        for (Future<Object> f : allFutures) {
            try {
                f.get();
            } catch (InterruptedException e) {
                System.out.println("A1");
            } catch (ExecutionException e) {
                System.out.println("A1");
            }
        }
        return;
    }

}
