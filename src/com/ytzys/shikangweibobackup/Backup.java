package com.ytzys.shikangweibobackup;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
//                    ListDataBean data = JsonMapper.INSTANCE.fromJson(tmp, ListDataBean.class);
                    for (int i = 0; i < data.data.list.size(); i++) {
                        ListDataBean.DataEntity.ListEntity listEntity = data.data.list.get(i);
                        String name = listEntity.created_at.replaceAll("[ :]", "_");
                        File file = new File(dir.getPath() + "/" + name + ".txt");
                        if (!file.exists()) {
                            System.out.println(i);
                            String string = getString(detailBaseUrl + listEntity.mblogid);
                            DetailBean detailBean = null;
                            try {
                                detailBean = JSON.parseObject(string, DetailBean.class);
                                String text = detailBean.data.text;
                                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                                writer.write(text);
                                writer.close();
                            } catch (Exception e) {
                                System.out.println(listEntity.text);
                            }
                        } else {
                            if (listEntity.edit_count >= 1) {
                                String string = getString(editHistryBaseUrl + listEntity.mid);
                                EditHistoryBean editHistoryBean = JSON.parseObject(string, EditHistoryBean.class);
                                List<EditHistoryBean.StatusesEntity> statuses = editHistoryBean.statuses;
                                for (int j = 0; j < statuses.size(); j++) {
                                    String text = statuses.get(j).text;
                                    String fileStr = name + "_" + statuses.get(j).created_at.replaceAll("[ :]", "_");
                                    File file1 = new File(dir.getPath() + "/" + fileStr + ".txt");
                                    if (!file1.exists()) {
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(file1));
                                        writer.write(text);
                                        writer.close();
                                    }
                                }
                            }
                        }
//                        Date date = new Date(listEntity.created_at);
//                        if (date.compareTo(new Date("Wed Mar 02 16:12:57 +0800 2022")) > 0) {
//                            System.out.println(listEntity.created_at);
//                            JOptionPane.showMessageDialog(null, "we got it",
//                                    "result", JOptionPane.INFORMATION_MESSAGE);
//                            break;
//                        }

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

    private static String getString(String urlStr) throws IOException {
        String tmp = "";
        URL url = new URL(urlStr);
        byte[] buffer = new byte[1024 * 8];
        int readSize;
        HttpURLConnection conn = (HttpURLConnection) url
                .openConnection();
        conn.setRequestProperty("Cookie", "SSOLoginState=1602124982; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WF-lBYf6QcxT_0oJh.kv3dw5JpX5KMhUgL.Foz7e0qRS0-41K-2dJLoIEXLxKqLBozL1hnLxK.L1KnLBoeLxKBLBonLBoBLxKqLBo2L1-qLxKBLB.zL1K.t; _s_tentry=login.sina.com.cn; Apache=8247843021384.305.1646184153171; SINAGLOBAL=8247843021384.305.1646184153171; ULV=1646184153215:1:1:1:8247843021384.305.1646184153171:; XSRF-TOKEN=HukLs4DJDbQYzfbzdflz7kG8; UOR=login.sina.com.cn,weibo.com,www.google.com; WBPSESS=w6PQnJHNETvGmhGCZzFKLZIxY7thbUoQg_TAyNL7u2luKss5eTs1J5v1YIWgDFrbLrPicjbSYHtipj_qtI9JYd1oEOr3xn9uy5RkOeD0oYtUJJ0aYjk5RNPmQGGCbgfA; SCF=AtnWa351Jh6ZHltUaoVwCzxYld90sKX-uehoVN6Z0SoDXn7duf3O91Z24ayrMn95ohUTO2FrZtu8JXCuD7WqeMI.; SUB=_2A25PJe4yDeRhGeRO6FQZ9yvFwjmIHXVsU1j6rDV8PUNbmtB-LVnfkW9NUG1qmYzwOd8HHAX8j0lJGPiSdQJel10C; ALF=1677906399");
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
