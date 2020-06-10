package com.cly.mineworld.service.service.impl;

import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.dao.UserUcInviteFriendsDao;
import com.cly.mineworld.service.service.UserUcInviteFriendsService;
import com.cly.mineworld.service.vo.common.ResultVo;
import com.cly.mineworld.service.vo.userFriend.UserFriendVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户端-用户个人中心--邀请好友-调度服务
 *
 * @author william
 */
@Service
@Transactional
public class UserUcInviteFriendsServiceImpl implements UserUcInviteFriendsService {

    @Autowired
    private UserUcInviteFriendsDao ucInviteFriendsDao;


    /**
     * 邀请好友
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo inviteGoodFriends(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                mapQueryUser.put("userStrId", userStrId);
                List<UserFriendVo> listUserFriend = ucInviteFriendsDao.findUserFriend(mapQueryUser);
                if (listUserFriend.size() > 0) {
                    jData.put("userFriend", listUserFriend);
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
