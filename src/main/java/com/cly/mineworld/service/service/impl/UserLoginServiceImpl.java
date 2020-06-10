package com.cly.mineworld.service.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import com.cly.mineworld.service.entity.MAppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserLoginDao;
import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.service.UserLoginService;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;

@Service
@Transactional
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserLoginDao userLoginDao;


    //用户登陆
    @Override
    public ResultVo userLogin(String jsonData) {
        //jsonData报文格式：{"data":{"loginType":"123456","psw":"123456"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String loginType = data.getString("loginType");
            String psw = data.getString("psw");
            String sign = (String) data.get("sign");
            if (null != sign) {
                //邮箱登陆
                if (null != loginType && !"".equals(loginType)
                        && null != psw && !"".equals(psw)
                        && null != sign && !"".equals(sign)) {
                    String[] appVersions = sign.split("-");
                    String appVersion = appVersions[0];
                    Map<String, Object> mapAppVersion = new HashMap<String, Object>();
                    mapAppVersion.put("appVersion", appVersion);
                    List<MAppVersion> listAppVersion = userLoginDao.selectAppVersionList(mapAppVersion);
                    if (listAppVersion.size() > 0) {
                        if (loginType.indexOf("@") != -1) {
                            //邮箱方式登陆
                            Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                            mapQueryUser.put("email", loginType);
                            mapQueryUser.put("psw", Utils.md5(psw));
                            List<MUser> listUser = userLoginDao.selectUserList(mapQueryUser);
                            if (listUser.size() > 0) {//登录成功
                                jData.put("user", listUser.get(0));
                            } else {
                                //登录失败
                                jData.put("user", new JSONObject());
                                result = "-19";
                                message = "User login fail!";
                            }
                        } else {
                            //手机方式登陆
                            Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                            mapQueryUser.put("mobile", loginType);
                            mapQueryUser.put("psw", Utils.md5(psw));
                            List<MUser> listUser = userLoginDao.selectUserList(mapQueryUser);
                            if (listUser.size() > 0) {//登录成功
                                jData.put("user", listUser.get(0));
                            } else {
                                //登录失败
                                jData.put("user", new JSONObject());
                                result = "-19";
                                message = "User login fail!";
                            }
                        }
                    } else {
                        result = "-60";//当前版本不对
                        message = "Please update to the latest version!";
                    }
                } else {
                    result = "-2";//参数错误
                    message = "Parameters error!";
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
}
