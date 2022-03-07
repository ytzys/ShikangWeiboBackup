package com.ytzys.shikangweibobackup.bean;

import java.util.List;

/**
 * @author zhangyisu
 * @time 2022/3/2 18:13
 */
public class EditHistoryBean {

    public int total;
    public List<StatusesEntity> statuses;
    public int ok;

    public static class StatusesEntity {
        public String idstr;
        public boolean showFeedComment;
        public boolean isLongText;
        public String mblogid;
        public List<?> pic_ids;
        public String created_at;
        public String mid;
        public int mblogtype;
        public String source;
        public int attitudes_count;
        public String rid;
        public int share_repost_type;
        public String geo;
        public int is_show_bulletin;
        public LongTextEntity longText;
        public boolean showFeedRepost;
        public int mlevel;
        public int pic_num;
        public boolean pictureViewerSign;
        public int id;
        public String text;
        public boolean is_paid;
        public int reposts_count;
        public Continue_tagEntity continue_tag;
        public boolean showPictureViewer;
        public boolean favorited;
        public int mblog_vip_type;
        public int content_auth;
        public VisibleEntity visible;
        public String text_raw;
        public boolean can_edit;
        public int textLength;
        public Number_display_strategyEntity number_display_strategy;
        public int attitudes_status;
        public List<?> rcList;
        public int comments_count;
        public Comment_manage_infoEntity comment_manage_info;
        public int edit_count;
        public UserEntity user;

        public static class LongTextEntity {
            public int weibo_position;
            public List<?> pic_ids;
            public int show_attitude_bar;
            public String content;
        }

        public class Continue_tagEntity {
            /**
             * scheme : sinaweibo://detail?mblogid=-1&id=-1
             * pic : http://h5.sinaimg.cn/upload/2015/09/25/3/timeline_card_small_article.png
             */
            public String scheme;
            public String pic;
            public String title;
        }

        public static class VisibleEntity {
            /**
             * list_id : 0
             * type : 0
             */
            public int list_id;
            public int type;
        }

        public static class Number_display_strategyEntity {
            /**
             * apply_scenario_flag : 3
             * display_text_min_number : 1000000
             */
            public int apply_scenario_flag;
            public int display_text_min_number;
            public String display_text;
        }

        public static class Comment_manage_infoEntity {
            /**
             * comment_permission_type : -1
             * comment_sort_type : 0
             * approval_comment_type : 0
             */
            public int comment_permission_type;
            public int comment_sort_type;
            public int approval_comment_type;
        }

        public static class UserEntity {
            public String idstr;
            public boolean follow_me;
            public String profile_url;
            public boolean verified;
            public int pc_new;
            public String profile_image_url;
            public String weihao;
            public int verified_type;
            public int verified_type_ext;
            public String avatar_large;
            public String avatar_hd;
            public boolean planet_video;
            public int mbtype;
            public String screen_name;
            public String domain;
            public boolean following;
            public List<Icon_listEntity> icon_list;
            public int id;
            public int mbrank;

            public static class Icon_listEntity {
                /**
                 * data : {"mbtype":12,"mbrank":1}
                 * type : vip
                 */
                public DataEntity data;
                public String type;

                public static class DataEntity {
                    /**
                     * mbtype : 12
                     * mbrank : 1
                     */
                    public int mbtype;
                    public int mbrank;
                }
            }
        }
    }
}
