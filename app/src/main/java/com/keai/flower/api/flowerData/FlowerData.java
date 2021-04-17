package com.keai.flower.api.flowerData;

import java.io.Serializable;
import java.util.List;

public class FlowerData implements Serializable {

    /**
     * code : 0
     * msg : 请求成功
     * data : {"user_info":{"key":"00001","email":"keai@keai.icu","time":"1614360654"},"machine_list":[{"mac":"1137038B4988","name":"君子兰","up":"1614711444","sm":75,"ah":50,"at":15},{"mac":"1237038B4988","name":"仙人掌","up":"1614711458","sm":20,"ah":50,"at":15},{"mac":"6001943a41e1","name":"❤","up":"1614785516","sm":35,"ah":55,"at":15},{"mac":"8caab56fd899","name":"测试","up":"1617198165","sm":12,"ah":13,"at":15},{"mac":"8caab56fd8c2","name":"硬件测试机","up":"1617198138","sm":12,"ah":13,"at":15}]}
     */

    private int code;
    private String msg;
    /**
     * user_info : {"key":"00001","email":"keai@keai.icu","time":"1614360654"}
     * machine_list : [{"mac":"1137038B4988","name":"君子兰","up":"1614711444","sm":75,"ah":50,"at":15},{"mac":"1237038B4988","name":"仙人掌","up":"1614711458","sm":20,"ah":50,"at":15},{"mac":"6001943a41e1","name":"❤","up":"1614785516","sm":35,"ah":55,"at":15},{"mac":"8caab56fd899","name":"测试","up":"1617198165","sm":12,"ah":13,"at":15},{"mac":"8caab56fd8c2","name":"硬件测试机","up":"1617198138","sm":12,"ah":13,"at":15}]
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * key : 00001
         * email : keai@keai.icu
         * time : 1614360654
         */

        private UserInfoBean user_info;
        /**
         * mac : 1137038B4988
         * name : 君子兰
         * up : 1614711444
         * sm : 75
         * ah : 50
         * at : 15
         */

        private List<MachineListBean> machine_list;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public List<MachineListBean> getMachine_list() {
            return machine_list;
        }

        public void setMachine_list(List<MachineListBean> machine_list) {
            this.machine_list = machine_list;
        }

        public static class UserInfoBean {
            private String key;
            private String email;
            private String time;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class MachineListBean {
            private String mac;
            private String name;
            private String up;
            private int sm;
            private int ah;
            private int at;

            public String getMac() {
                return mac;
            }

            public void setMac(String mac) {
                this.mac = mac;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUp() {
                return up;
            }

            public void setUp(String up) {
                this.up = up;
            }

            public int getSm() {
                return sm;
            }

            public void setSm(int sm) {
                this.sm = sm;
            }

            public int getAh() {
                return ah;
            }

            public void setAh(int ah) {
                this.ah = ah;
            }

            public int getAt() {
                return at;
            }

            public void setAt(int at) {
                this.at = at;
            }
        }
    }
}
