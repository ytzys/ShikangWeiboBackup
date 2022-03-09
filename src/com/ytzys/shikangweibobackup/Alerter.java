package com.ytzys.shikangweibobackup;

import com.alibaba.fastjson.JSON;
import com.ytzys.shikangweibobackup.bean.ListDataBean;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Alerter {

    public static void main(String[] args) {

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = new Runnable() {

            @Override
            public void run() {
                String tmp = "";
                URL url;
                try {
                    url = new URL(
                            "https://weibo.com/ajax/statuses/mymblog?uid=1191808911&page=1&feature=0");
                    byte[] buffer = new byte[1024 * 8];
                    int readSize;
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestProperty("Cookie", "SSOLoginState=1602124982; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WF-lBYf6QcxT_0oJh.kv3dw5JpX5KMhUgL.Foz7e0qRS0-41K-2dJLoIEXLxKqLBozL1hnLxK.L1KnLBoeLxKBLBonLBoBLxKqLBo2L1-qLxKBLB.zL1K.t; _s_tentry=login.sina.com.cn; Apache=8247843021384.305.1646184153171; SINAGLOBAL=8247843021384.305.1646184153171; ULV=1646184153215:1:1:1:8247843021384.305.1646184153171:; XSRF-TOKEN=HukLs4DJDbQYzfbzdflz7kG8; UOR=login.sina.com.cn,weibo.com,www.google.com; WBPSESS=w6PQnJHNETvGmhGCZzFKLZIxY7thbUoQg_TAyNL7u2k46QFnuqH-XG8Eq2pIjkbkbzH_xy1r2D0rAxS-sXiDRbgm6UQT1DDP4U2t-cXrRG1RefUNElppy2qDfk9Hn-bi; SCF=AtnWa351Jh6ZHltUaoVwCzxYld90sKX-uehoVN6Z0SoDKGsMMkzW6AMBK_nOxay8OMr0JwpjW8uMV9ms6_sYd68.; SUB=_2A25PLGPgDeRhGeRO6FQZ9yvFwjmIHXVsWNIorDV8PUNbmtB-LRjzkW9NUG1qmSZGawXO9mHyDmydTp3XRySNBH92; ALF=1678329644");
                    conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        while ((readSize = is.read(buffer)) >= 0) {
                            bos.write(buffer, 0, readSize);
                        }
                        tmp = bos.toString();


                    }
                    try {
                        ListDataBean data = JSON.parseObject(tmp, ListDataBean.class);
                        for (int i = 0; i < data.data.list.size(); i++) {
                            ListDataBean.DataEntity.ListEntity listEntity = data.data.list.get(i);

                            Date date = new Date(listEntity.created_at);
                            if (date.compareTo(new Date("Mon Mar 07 00:30:22 +0800 2022")) > 0) {
                                System.out.println(listEntity.created_at);
                                JOptionPane.showMessageDialog(null, "we got it",
                                        "result", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        executor.execute(task);
        while (true) {
            try {
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.execute(task);
        }
    }


}

