package com.cly.mineworld.service.service.impl;

import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcNodeDao;
import com.cly.mineworld.service.entity.MNodeInviteCode;
import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.entity.MUserNodeOrder;
import com.cly.mineworld.service.service.UserUcNodeService;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户端-用户个人中心-节点-调度服务
 *
 * @author william
 */
@Service
@Transactional
public class UserUcNodeServiceImpl implements UserUcNodeService {

    @Autowired
    private UserUcNodeDao userUcNodeDao;


    /**
     * 激活码是否有效
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo activationOrNot(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","activationCode":"12103"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String activationCode = data.getString("activationCode");
            if (null != userStrId && !"".equals(userStrId) && null != activationCode && !"".equals(activationCode)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                mapQuery.put("activationCode", activationCode);
                List<MUser> userList = userUcNodeDao.selectUserList(mapQuery);
                if (userList.size() > 0) {
                    MUser user = userList.get(0);
                    //查询m_node_invite_code
                    Map<String, Object> mapQueryInviteCodeMap = new HashMap<String, Object>();
                    mapQueryInviteCodeMap.put("activationCode", activationCode);
                    List<MNodeInviteCode> inviteCodeList = userUcNodeDao.selectInviteNodeOrder(mapQueryInviteCodeMap);
                    if (inviteCodeList.size() > 0) {//激活码存在
                        if ("2".equals(inviteCodeList.get(0).getCodeUseStatus().toString())) {//邀请码可以使用
                            MNodeInviteCode inviteCode = inviteCodeList.get(0);
                            //查询m_user_node_order  用户节点信息
                            Map<String, Object> nodeOrderMap = new HashMap<String, Object>();
                            nodeOrderMap.put("userStrId", userStrId);
                            List<MUserNodeOrder> nodeOrderList = userUcNodeDao.selectNodeOrder(nodeOrderMap);
                            if (nodeOrderList.size()>0){//已经申请，现添加激活码
                                MUserNodeOrder nodeOrder = nodeOrderList.get(0);
                                Map<String, Object> modifyInviteMap = new HashMap<String, Object>();
                                modifyInviteMap.put("userStrId", userStrId);
                                modifyInviteMap.put("activationCode", activationCode);//激活码
                                modifyInviteMap.put("orderVerifyStatus", 1);//审核状态
                                modifyInviteMap.put("orderReadStatus", 1);//订单已读状态
                                modifyInviteMap.put("nodeLv", inviteCode.getNodeLv());//节点等级
                                userUcNodeDao.updateNodeOrder(modifyInviteMap);
                            }else {//用户没有m_user_node_order的情况下 ，直接新增
                                //将用户变成节点
                                MUserNodeOrder nodeOrder = new MUserNodeOrder();
                                nodeOrder.setStrId(Utils.getUUID());
                                nodeOrder.setUserId(user.getId());
                                nodeOrder.setUserStrId(user.getStrId());
                                nodeOrder.setInviteCode(activationCode);
                                nodeOrder.setMobile(user.getMobile());
                                nodeOrder.setOrderVerifyStatus(1);
                                nodeOrder.setOrderReadStatus(1);
                                nodeOrder.setOrderReviewerStrId("");
                                nodeOrder.setNodeLv(inviteCode.getNodeLv());
                                nodeOrder.setStatus(1);
                                nodeOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                nodeOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                userUcNodeDao.insertNodeOrder(nodeOrder);
                            }
                            //将邀请码状态改为“1”
                            Map<String, Object> modifyInviteCodeMap = new HashMap<String, Object>();
                            modifyInviteCodeMap.put("activationCode", activationCode);
                            modifyInviteCodeMap.put("codeUseStatus", 1);//将未使用改成使用
                            modifyInviteCodeMap.put("userStrId",user.getStrId());
                            userUcNodeDao.updateInviteCode(modifyInviteCodeMap);
                        }
                    } else {
                        result = "-42";//邀请码不存在
                        message = "Invitation code does not exist";
                    }
                } else {
                    result = "-13";//用户不存在
                    message = "User is not exist!";
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
     * 申请节点  2019-10-16
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo applicationNode(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","mobile":"12345678901"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String mobile = data.getString("mobile");
            if (null != userStrId && !"".equals(userStrId)
                    && null != mobile && !"".equals(mobile)) {
                //校验手机号码合法性
                if (mobile.length() == 11) {
                    if (Utils.isMobileNum(mobile)) {
                        Map<String, Object> mapQuery = new HashMap<String, Object>();
                        mapQuery.put("userStrId", userStrId);
                        List<MUser> userList = userUcNodeDao.selectUserList(mapQuery);
                        MUser user = userList.get(0);
                        MUserNodeOrder nodeOrder = new MUserNodeOrder();
                        nodeOrder.setStrId(Utils.getUUID());
                        nodeOrder.setUserStrId(userStrId);
                        nodeOrder.setUserId(user.getId());
                        nodeOrder.setInviteCode("");
                        nodeOrder.setMobile(mobile);
                        nodeOrder.setOrderVerifyStatus(2);
                        nodeOrder.setOrderReadStatus(2);
                        nodeOrder.setOrderReviewerStrId("");
                        nodeOrder.setNodeLv(0);
                        nodeOrder.setStatus(1);
                        nodeOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                        nodeOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                        userUcNodeDao.insertNodeOrder(nodeOrder);
                    } else {
                        result = "-22";// 手机格式错误
                        message = "Mobile format error!";
                    }
                } else {
                    result = "-21";// 手机号应为11位数
                    message = "Mobile length error!";
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
     * 查询节点信息
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo getNodeInformation(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                List<MUserNodeOrder> nodeOrderList = userUcNodeDao.selectNodeOrder(mapQuery);
                if (nodeOrderList.size()>0){
                    MUserNodeOrder nodeOrder = nodeOrderList.get(0);
                    if (null != nodeOrder.getInviteCode() && !"".equals(nodeOrder.getInviteCode())){
                        jData.put("nodeOrders",nodeOrderList);
                        jData.put("frozenRegisteredCurrency",0); //TODO 展示数据需要确定
                        jData.put("newUsersHaveBeenOpened",0);
                        jData.put("benefitsAchieved",0);
                        result = "-43";//已经成为节点
                        message = "Has become a node!";
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
     * 收益明细
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo nodeProfitDetailed(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                //TODO
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
     * 注册明细
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo nodeRegisterDetailed(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
               //TODO
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
     * 收支明细
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo nodeBudgetDetailed(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
              //TODO
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
