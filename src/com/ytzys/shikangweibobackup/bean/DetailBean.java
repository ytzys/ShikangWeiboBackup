package com.ytzys.shikangweibobackup.bean;

import java.util.List;

/**
 * @author zhangyisu
 * @time 2022/3/2 18:12
 */
public class DetailBean {

    public DataEntity data;
    public int ok;

    public static class DataEntity {
        public String status_title;
        public boolean isLongText;
        public List<?> pic_ids;
        public String pic_types;
        public String created_at;
        public String mid;
        public int mblogtype;
        public String source;
        public int attitudes_count;
        public boolean can_reprint;
        public int hide_flag;
        public List<?> darwin_tags;
        public int pending_approval_count;
        public Alchemy_paramsEntity alchemy_params;
        public int mlevel;
        public int pic_num;
        public String id;
        public String text;
        public boolean is_paid;
        public int reposts_count;
        public int new_comment_style;
        public int ok;
        public int reward_exhibition_type;
        public int safe_tags;
        public boolean favorited;
        public int reprint_cmt_count;
        public int mblog_vip_type;
        public int content_auth;
        public int reprint_type;
        public VisibleEntity visible;
        public List<ButtonsEntity> buttons;
        public String reward_scheme;
        public boolean can_edit;
        public int textLength;
        public Number_display_strategyEntity number_display_strategy;
        public int version;
        public int comments_count;
        public int show_additional_indication;
        public String bid;
        public UserEntity user;
        public int more_info_type;

        public static class Alchemy_paramsEntity {
            /**
             * ug_red_envelope : false
             */
            public boolean ug_red_envelope;
        }

        public static class VisibleEntity {
            /**
             * list_id : 0
             * type : 0
             */
            public int list_id;
            public int type;
        }

        public static class ButtonsEntity {
            /**
             * sub_type : 0
             * type : follow
             * params : {"uid":1191808911}
             */
            public int sub_type;
            public String name;
            public String type;
            public ParamsEntity params;

            public static class ParamsEntity {
                /**
                 * uid : 1191808911
                 */
                public int uid;
            }
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

        public static class UserEntity {
            public boolean follow_me;
            public String gender;
            public String profile_url;
            public int urank;
            public String description;
            public String avatar_hd;
            public int mbtype;
            public String screen_name;
            public boolean close_blue_v;
            public String verified_reason;
            public int id;
            public int mbrank;
            public boolean like_me;
            public String followers_count_str;
            public boolean like;
            public boolean verified;
            public String profile_image_url;
            public int verified_type;
            public int verified_type_ext;
            public BadgeEntity badge;
            public int statuses_count;
            public int follow_count;
            public boolean following;
            public String followers_count;
            public String cover_image_phone;

            public static class BadgeEntity {
                /**
                 * user_name_certificate : 1
                 * unread_pool : 1
                 * pc_new : 7
                 * follow_whitelist_video : 1
                 * hongbao_2020 : 2
                 * unread_pool_ext : 1
                 */
                public int user_name_certificate;
                public int unread_pool;
                public int pc_new;
                public int follow_whitelist_video;
                public int hongbao_2020;
                public int unread_pool_ext;
            }
        }
    }
}
