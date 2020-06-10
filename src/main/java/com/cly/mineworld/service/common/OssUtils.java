package com.cly.mineworld.service.common;

import com.aliyun.oss.OSSClient;
import java.io.InputStream;

public class OssUtils {

    //public final static String SERVER_URL ="http://oss-cn-shenzhen.aliyuncs.com";
    //public final static String ACCESS_KEY_ID = "LTAI4bPRd6ooYn23";
    //public final static String ACCESS_KEY_SECRET = "t4koX4MsApqVnz05vrJ7mbKBSdhWfM";
	/*生产*/
    public final static String SERVER_URL ="http://oss-cn-hongkong.aliyuncs.com";
    public final static String ACCESS_KEY_ID = "LTAI1Hdh9H2DLrON";
    public final static String ACCESS_KEY_SECRET = "C7hcR9046GIZ0Rux2rNc5Yx8YlCFh8";

    /**
     * 上传文件到oss
     * @param fileInputStream
     * @return
     */
    public static String uploadFileToOss(InputStream fileInputStream){
        String targetFileName = getUUID() + ".jpg";
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(SERVER_URL, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        //ossClient.putObject("clyshop", targetFileName, fileInputStream);
        ossClient.putObject("evagame", targetFileName, fileInputStream);//生产
        // 关闭client
        ossClient.shutdown();
        //String newFileUrl = "https://clyshop.oss-cn-shenzhen.aliyuncs.com/" + targetFileName;
        String newFileUrl = "https://evagame.oss-cn-hongkong.aliyuncs.com/" + targetFileName;//生产
        return newFileUrl;
    }

    /**
     * 生成唯一码
     * @return
     */
    public static String  getUUID(){
        return  java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }
}