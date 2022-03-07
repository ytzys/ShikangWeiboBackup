package com.ytzys.shikangweibobackup.bean;

/**
 * @author zhangyisu
 * @time 2022/3/7 15:39
 */
public class LongTextBean {

    public int http_code;
    public DataEntity data;
    public int ok;

    public static class DataEntity {
        public String longTextContent;
    }
}
