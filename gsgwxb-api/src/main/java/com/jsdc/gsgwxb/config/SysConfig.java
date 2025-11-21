package com.jsdc.gsgwxb.config;


/**
 * ClassName: SysConfig
 * Description: 系统变量
 * date: 2024/4/22 9:20
 *
 * @author bn
 */
public class SysConfig {

    private static SysConfig sysConfig;



    private Boolean isOpenFlag;


    public static SysConfig getInstance(){
        if (null==sysConfig){
            sysConfig=new SysConfig();
        }
        return sysConfig;
    }


    private SysConfig() {
        isOpenFlag=false;

    }



    public Boolean getOpenFlag() {
        return isOpenFlag;
    }

    public void setOpenFlag(Boolean openFlag) {
        isOpenFlag = openFlag;
    }


}
