package com.cly.mineworld.service.service.impl;

import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcTeamDao;
import com.cly.mineworld.service.entity.MUserTeam;
import com.cly.mineworld.service.service.UserUcTeamService;
import com.cly.mineworld.service.vo.common.ResultVo;
import com.cly.mineworld.service.vo.userInformation.UserInformationVo;
import com.cly.mineworld.service.vo.userRank.UserRankVo;
import com.cly.mineworld.service.vo.userManagerTeam.UserManagerTeamVo;
import com.cly.mineworld.service.vo.userTeams.UserTeamsVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户端-用户个人中心-我的团队-调度服务
 *
 * @author william
 */
@Service
@Transactional
public class UserUcTeamServiceImpl implements UserUcTeamService {

    @Autowired
    private UserUcTeamDao userUcTeamDao;


    /**
     * 我的等级
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo myRank(String jsonData) {
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
               //List<MUserTeam> userTeamList =  userUcTeamDao.selectMyTeam(mapQuery);
              // if (userTeamList.size()>0){//用户有团队 不判断
                   List<UserRankVo> listUserRank = userUcTeamDao.selectMyRank(mapQuery);
                   if (listUserRank.size() > 0) {
                       jData.put("userRank", listUserRank);
               }else {
                       jData.put("teamLv","普通玩家");
                   }
             /*   } else {
                    result = "-2";//参数错误
                    message = "Parameters error!";
                }*/
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
     * 我的管理团队
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo findUserManagerTeam(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQueryUserTeam = new HashMap<String, Object>();
                mapQueryUserTeam.put("userStrId", userStrId);
                List<UserManagerTeamVo> listManagerUserTeam = userUcTeamDao.findUserManagerTeam(mapQueryUserTeam);
                if (listManagerUserTeam.size() > 0) {
                    jData.put("userTeams", listManagerUserTeam);
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

    /**
     * 我的团队
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo findUserTeams(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQueryUserTeam = new HashMap<String, Object>();
                mapQueryUserTeam.put("userStrId", userStrId);
                List<UserTeamsVo> listUserTeams = userUcTeamDao.findUserTeams(mapQueryUserTeam);
                if (listUserTeams.size() > 0) {
                    if (!"0".equals(listUserTeams.get(0).getFriendTotal().toString())) {
                        jData.put("userTeams", listUserTeams);
                    } else {
                        result = "-34";//团队数量为零
                        message = "Teams Number is Zero!";
                        jData.put("userTeams", listUserTeams);
                    }
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
     * 查询好友信息
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo findUserFriend(String jsonData) {
        //jsonData报文格式：{"findFriendType":"15012344321","userStrId":"8541b4a043f4479c86124f8f701e399b"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String findFriendType = data.getString("findFriendType");
            String userStrId = data.getString("userStrId");
            if (null != findFriendType && !"".equals(findFriendType)) {
                Map<String, Object> mapQueryUserFriend = new HashMap<String, Object>();
                mapQueryUserFriend.put("userStrId", userStrId);
                if (findFriendType.indexOf("@") != -1) {//邮箱方式
                    String friendEmail = findFriendType;
                    //校验邮箱的规范
                    if (Utils.isEmail(friendEmail)) {
                        mapQueryUserFriend.put("friendEmail", friendEmail);
                        List<UserTeamsVo> friends = userUcTeamDao.findUserFriendOne(mapQueryUserFriend);
                        if (friends.size() > 0) {
                            if (!"0".equals(friends.get(0).getNickName())) {
                                jData.put("friend", friends);
                            } else {
                                result = "-29";//好友不存在
                                message = "Friend not exist!";
                            }
                        }
                    } else {
                        result = "-24";//邮箱格式错误
                        message = "Email format error!";
                    }
                } else {//手机方式
                    String friendMobile = findFriendType;
                    //校验手机号码合法性
                    if (friendMobile.length() == 11) {
                        if (Utils.isMobileNum(friendMobile)) {
                            mapQueryUserFriend.put("friendMobile", friendMobile);
                            List<UserTeamsVo> friends = userUcTeamDao.findUserFriendOne(mapQueryUserFriend);
                            if (friends.size() > 0) {
                                if (!"0".equals(friends.get(0).getNickName())) {
                                    jData.put("friend", friends);
                                } else {
                                    result = "-29";//好友不存在
                                    message = "Friend not exist!";
                                }
                            }
                        } else {
                            result = "-22";// 手机格式错误
                            message = "Mobile format error!";
                        }
                    } else {
                        result = "-21";// 手机号应为11位数
                        message = "Mobile length error!";
                    }
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
     * 根据用户StrId查询
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo findUserById(String jsonData) {
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
                List<UserInformationVo> listUser = userUcTeamDao.findUserById(mapQueryUser);
                if (listUser.size() > 0) {
                    UserInformationVo userInformation = listUser.get(0);
                    //设置交易密码是否强制
                    if (null != userInformation.getTransactionPsw() && !"".equals(userInformation.getTransactionPsw())) {
                        jData.put("user", userInformation);
                    } else {
                        //查询数据库未设置交易密码，返回状态码，将交易密码添加进数据库
                        result = "-26";//未设置交易密码
                        message = "TransactionPsw Is Null!";
                        jData.put("user", userInformation);
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
