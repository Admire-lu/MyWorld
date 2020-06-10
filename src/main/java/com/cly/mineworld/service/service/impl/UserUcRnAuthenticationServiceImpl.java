package com.cly.mineworld.service.service.impl;

import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.OssUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcRnAuthenticationDao;
import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.entity.MUserId;
import com.cly.mineworld.service.service.UserUcRnAuthenticationService;
import com.cly.mineworld.service.vo.common.ResultVo;
import com.cly.mineworld.service.vo.userRealnameInformation.UserRealNameInformationVo;
import net.sf.json.JSONObject;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户端-用户个人中心-实名认证-调度服务
 *
 * @author william
 */
@Service
@Transactional
public class UserUcRnAuthenticationServiceImpl implements UserUcRnAuthenticationService {

    @Autowired
    private UserUcRnAuthenticationDao ucRnAuthenticationDao;


    /**
     * 校验是否已经实名认证
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo checkRealNameIsExist(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                List<MUserId> userIdList = ucRnAuthenticationDao.selectUserId(mapQuery);
                if (userIdList.size() > 0) {//已经实名认证并返回用户实名认证的信息
                    MUserId userId = userIdList.get(0);
                    result = "-32";//已经实名注册
                    message = "RealNameInformation Completed!";
                    jData.put("authenticationStatus", userId.getAuthenticationStatus());//审核状态：1，审核通过，2,审核未通过
                }
            } else {
                result = "-2";//参数错误
                message = "Parameters error!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 实名信息详情
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo findRealNameInformation(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                List<UserRealNameInformationVo> realNameInformationList = ucRnAuthenticationDao.findRealNameInformation(mapQuery);
                jData.put("realNameInformation", realNameInformationList);
            } else {
                result = "-2";//参数错误
                message = "Parameters error!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 实名认证
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo realNameAuthentication(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","
        // idNo":"360732166546","idRealName":"1234615","idImageOne"："122354","idImageTwo":"45154641"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String idNo = data.getString("idNo");
            String idRealName = data.getString("idRealName");
            String idImageOne = data.getString("idImageOne");
            String idImageTwo = data.getString("idImageTwo");
            if (null != userStrId && !"".equals(userStrId) && null != idNo && !"".equals(idNo)
                    && null != idRealName && !"".equals(idRealName) && null != idImageOne && !"".equals(idImageOne)
                    && null != idImageTwo && !"".equals(idImageTwo)) {
                //将照片上传
                byte[] imageOneByte = Base64.getDecoder().decode(idImageOne);
                ByteArrayInputStream imageOneInputStream = new ByteArrayInputStream(imageOneByte);
                String imageOneName = OssUtils.uploadFileToOss(imageOneInputStream);
                byte[] idImageTwoByte = Base64.getDecoder().decode(idImageTwo);
                ByteArrayInputStream idImageTwoInputStream = new ByteArrayInputStream(idImageTwoByte);
                String imageTwoName = OssUtils.uploadFileToOss(idImageTwoInputStream);
                //保存用户
                MUserId userId = new MUserId();
                userId.setStrId(Utils.getUUID());
                userId.setUserStrId(userStrId);
                userId.setIdNo(idNo);
                userId.setIdRealname(idRealName);
                userId.setIdImageOne(imageOneName);
                userId.setIdImageTwo(imageTwoName);
                userId.setIdImageThree("");
                userId.setAuthenticationStatus(2); //还未通过认证
                userId.setStatus(1);
                userId.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                userId.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                ucRnAuthenticationDao.insertUserId(userId);
                //给用户注册一个充值地址
                try {
                    Map<String,Object> mapQueryUserIds = new HashMap<String,Object>();
                    mapQueryUserIds.put("userStrId", userStrId);
                    List<MUserId> listUserIds = ucRnAuthenticationDao.selectUserIdList(mapQueryUserIds);
                    if(listUserIds.size() > 0) {
                        String userNewChildAddress = getRegisterChildAddress();//获取子地址
                        MUserId userId1 = listUserIds.get(0);
                        /*查询用户信息*/
                        Map<String,Object> mapQueryUser = new HashMap<String,Object>();
                        mapQueryUser.put("userStrId", userId.getUserStrId());
                        List<MUser> listUser = ucRnAuthenticationDao.selectUserList(mapQueryUser);
                        if(listUser.size() > 0 && null != listUser.get(0).getTokenAddress()) {
                            /*修改用户信息*/
                            Map<String,Object> mapModifyUser = new HashMap<String,Object>();
                            mapModifyUser.put("userStrId", userId1.getUserStrId());
                            mapModifyUser.put("tokenAddress", userNewChildAddress);
                            ucRnAuthenticationDao.updateUser(mapModifyUser);//修改用户token地址
                        }else {
                            result = "-13";//用户不存在
                            message = "User is not exist!";
                            throw new Exception("ERROR User is not exists or user's tokenAddress is exists!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result = "-48";//申请地址失败
                    message = "Failed to apply for address!";
                }
            } else {
                result = "-2";//参数错误
                message = "Parameters error!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 注册交易所子地址
     */
    private String getRegisterChildAddress() throws Exception {
        String url = "http://47.244.130.245:10013/getnewaddress";
        long timestamp = System.currentTimeMillis() * 1000;
        Map<String,String> headParam = new HashMap<String,String>();
        headParam.put("Authorization", "Basic " + DatatypeConverter.printBase64Binary("ETH:ethpassword".getBytes()));
        headParam.put("Content-Type", "application/x-www-form-urlencoded");
        headParam.put("json-Rpc-Tonce", Long.toString(timestamp));
        StringEntity postingString = new StringEntity("{\"method\":\"getnewaddress\", \"params\":[\"1\"], \"id\": 1}");
        String str = Utils.doPostHead(url, null,headParam,postingString,false);
        System.out.println(str);
        JSONObject jsoResult = JSONObject.fromObject(str);
        if(null == jsoResult.get("result") || "".equals(jsoResult.getString("result"))) {
            throw new Exception("GetRegisterChildAddress Result is null");
        }
        if(!"null".equals(jsoResult.getString("error"))) {
            throw new Exception("GetRegisterChildAddress ERROR");
        }
        return jsoResult.getString("result");
    }
}
