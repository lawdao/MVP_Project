package example.fussen.baselibrary.network.bean;

import java.util.List;

/**
 * Created by Fussen on 2016/12/2.
 */

public class UserInfo {


    /**
     * userId : 405
     * isBetaUser : 0
     * nickName : 爱你❤❤
     * avatarUrl : http://dev.bodyplus.cc:8088//images/avatar/avatar_1477286267.jpg
     * mobile : 18219200511
     * gender : 1
     * height : 160
     * birth : 2016-08-20
     * weight : 71
     * doneTotal : 0
     * useUp : 0
     * kmTotal : 0.00
     * monthRate : 0
     * monthTplNum : 0
     * sportTime : 0
     * profile : 你发你发那些年想你想男的女的那女的你的男的女的记得记得就到家对你的新的男的女的呢男的女的呢新新娘想你想心息怒息怒现金想你不行不行下班不行不行。下班不行不行不懂信息不行不行行吧行吧不想，v对不对八点半下班不对不对不想好的好的都比不多，不对不对不对不对不对不对下班不行不行

     * province : 北京市
     * city : 昌平区
     * bluetoothDevice :
     * SWVN : 152
     * HWVN : 9
     * isBetaVN : 0
     * packageUrl : http://dev.bodyplus.cc:8088//images/firmware/stm_0009_0098.zip
     * updateLog :
     * haveCoach : 0
     * newestAppVN : 1.4.0
     * newestBleVN : 7
     * isBleBeta : 0
     * blePackageUrl : http://dev.bodyplus.cc:8088//images/ble/nrf_0007_160901.zip
     * MuscleDataList : [{"muscleID":"1","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"2","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"3","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.69"},{"muscleID":"4","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"5","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"6","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"7","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"8","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"9","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.69"},{"muscleID":"10","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"11","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"12","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"13","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"14","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"15","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"16","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"17","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"18","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"19","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"20","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"21","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"22","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.88"},{"muscleID":"23","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"24","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.88"},{"muscleID":"25","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"26","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"},{"muscleID":"27","maxValue":"255","minValue":"0","adjust":"0","modulus":"1.3"}]
     * easeUserName : ca438c3337c005e18ab91d5eba3c877d
     * isPerfect : 1
     */

    public String userId;
    public String isBetaUser;
    public String nickName;
    public String avatarUrl;
    public String mobile;
    public String gender;
    public String height;
    public String birth;
    public String weight;
    public String doneTotal;
    public String useUp;
    public String kmTotal;
    public String monthRate;
    public String monthTplNum;
    public String sportTime;
    public String profile;
    public String province;
    public String city;
    public String bluetoothDevice;
    public String SWVN;
    public String HWVN;
    public String isBetaVN;
    public String packageUrl;
    public String updateLog;
    public String haveCoach;
    public String newestAppVN;
    public String newestBleVN;
    public String isBleBeta;
    public String blePackageUrl;
    public String easeUserName;
    public int isPerfect;
    public List<MuscleDataListUser> MuscleDataList;

    public static class MuscleDataListUser {
        /**
         * muscleID : 1
         * maxValue : 255
         * minValue : 0
         * adjust : 0
         * modulus : 1.3
         */

        public String muscleID;
        public String maxValue;
        public String minValue;
        public String adjust;
        public String modulus;
    }
}
