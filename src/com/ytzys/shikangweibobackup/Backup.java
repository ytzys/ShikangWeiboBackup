package com.ytzys.shikangweibobackup;

import com.alibaba.fastjson.JSON;
import com.ytzys.shikangweibobackup.bean.EditHistoryBean;
import com.ytzys.shikangweibobackup.bean.ListDataBean;
import com.ytzys.shikangweibobackup.bean.LongTextBean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author zhangyisu
 * @time 2022/3/3 17:36
 */
public class Backup {
    public static String listUrl = "https://weibo.com/ajax/statuses/mymblog?uid=1191808911&page=1&feature=0";
    public static String detailBaseUrl = "https://m.weibo.cn/statuses/show?id=";
    public static String editHistryBaseUrl = "https://weibo.com/ajax/statuses/editHistory?page=1&mid=";
    public static String longTextBaseUrl = "https://weibo.com/ajax/statuses/longtext?id=";

    static SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
    static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss E", Locale.US);

    public static void main(String[] args) {

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = new Runnable() {

            @Override
            public void run() {
                String tmp = "";
                try {
                    tmp = getString(listUrl);
                    ListDataBean data = JSON.parseObject(tmp, ListDataBean.class);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String format = sdf.format(new Date());
                    File dir = new File(format);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    for (int i = 0; i < data.data.list.size(); i++) {
                        ListDataBean.DataEntity.ListEntity listEntity = data.data.list.get(i);
                        String name = getFileName(listEntity.created_at);
                        File file = new File(dir.getPath() + "/" + name + ".txt");
                        if (!file.exists()) {
                            System.out.println(i);
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                            // is long weibo
                            if (listEntity.isLongText) {
                                String url = longTextBaseUrl + listEntity.mblogid;
                                try {
                                    String res = getString(url);
                                    LongTextBean longTextBean = JSON.parseObject(res, LongTextBean.class);
                                    writer.write(longTextBean.data.longTextContent);
                                    writer.close();
                                } catch (Exception e1) {
                                    System.out.println(url + " ;Get long text failed.");
                                }
                            } else {
                                writer.write(listEntity.text);
                                writer.close();
                            }
                            // handle edited weibo
                        } else {
                            if (listEntity.edit_count >= 1) {
                                String string = getString(editHistryBaseUrl + listEntity.mid);
                                EditHistoryBean editHistoryBean = JSON.parseObject(string, EditHistoryBean.class);
                                List<EditHistoryBean.StatusesEntity> statuses = editHistoryBean.statuses;
                                if (statuses != null)
                                    for (int j = 0; j < statuses.size(); j++) {
                                        String text = statuses.get(j).text;
//                                        String fileStr = name + "_" + statuses.get(j).created_at.replaceAll("[ :]", "_");
                                        String fileStr = name + "_" + getFileName(statuses.get(j).created_at);
                                        File file1 = new File(dir.getPath() + "/" + fileStr + ".txt");
                                        if (!file1.exists()) {
                                            BufferedWriter writer = new BufferedWriter(new FileWriter(file1));
                                            writer.write(text);
                                            writer.close();
                                        }
                                    }
                            }
                        }
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

    // get file name, using the create time, file name format: yyyy-MM-dd HH-mm-ss E
    private static String getFileName(String created_at) {
        try {
            return sdf2.format(sdf1.parse(created_at));
        } catch (ParseException e) {
            e.printStackTrace();
            return "error";
        }
    }

    private static String getString(String urlStr) throws IOException {
        String tmp = "";
        URL url = new URL(urlStr);
        byte[] buffer = new byte[1024 * 8];
        int readSize;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Cookie", "XSRF-TOKEN=iqzG9COP6KgxpBFhPhzK0qe3; _s_tentry=weibo.com; Apache=3192130945781.428.1665200599512; SINAGLOBAL=3192130945781.428.1665200599512; ULV=1665200599523:1:1:1:3192130945781.428.1665200599512:; SUB=_2A25ORXLXDeRhGeRO6FQZ9yvFwjmIHXVtM-MfrDV8PUNbmtAKLRTEkW9NUG1qmUxwEGAbbB-S0P4c0CH2RSMvpppb; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WF-lBYf6QcxT_0oJh.kv3dw5JpX5KzhUgL.Foz7e0qRS0-41K-2dJLoIEXLxKqLBozL1hnLxK.L1KnLBoeLxKBLBonLBoBLxKqLBo2L1-qLxKBLB.zL1K.t; ALF=1696740870; SSOLoginState=1665204871; WBPSESS=w6PQnJHNETvGmhGCZzFKLVgYU04Zkm35mgyIf2do9gfZBKS0Ne-c04LWmA545h0-Bt2IJYHHwecSYrAcpclMeNIMFFCVYPQcX8gd7DX030ovPee8Z2_lVPN3mYEBl_plvnn1ggfbwq62Yg2xIiaDeQ==");
        conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((readSize = is.read(buffer)) >= 0) {
                bos.write(buffer, 0, readSize);
            }
            tmp = bos.toString();


        }
        return tmp;
    }
}
