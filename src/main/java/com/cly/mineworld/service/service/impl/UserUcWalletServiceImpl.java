package com.cly.mineworld.service.service.impl;

import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcWalletDao;
import com.cly.mineworld.service.entity.*;
import com.cly.mineworld.service.service.UserUcWalletService;
import com.cly.mineworld.service.vo.common.ResultVo;
import com.cly.mineworld.service.vo.userCapitalDetails.CapitalDetailsVo;
import com.cly.mineworld.service.vo.userProfit.UserProfitVo;
import com.cly.mineworld.service.vo.userWallet.UserWalletVo;
import com.cly.mineworld.service.vo.userWithdrawals.UserWithdrawalsVo;
import com.cly.mineworld.service.vo.userWithdrawalsRecord.WithdrawalsRecordVo;
import net.sf.json.JSONObject;

import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户端-用户个人中心-我的钱包-调度服务
 *
 * @author william
 */
@Service
@Transactional
public class UserUcWalletServiceImpl implements UserUcWalletService {

    @Autowired
    private UserUcWalletDao userUcWalletDao;


    /**
     * 收款人昵称查询
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo queryNickname(String jsonData) {
        //jsonData报文格式： {"data":{"queryMode":"15012630328"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String queryMode = data.getString("queryMode");
            if (null != queryMode && !"".equals(queryMode)) {
                Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                if (queryMode.indexOf("@") != -1) {//邮箱mobileOrEmail
                    mapQueryUser.put("email", queryMode);
                } else {//手机
                    mapQueryUser.put("mobile", queryMode);
                }
                List<MUser> listUser = userUcWalletDao.selectUserList(mapQueryUser);
                if (listUser.size() > 0) {
                    jData.put("user", listUser.get(0));
                } else {//用户不存在
                    result = "-13";
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
     * 充币地址查询
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo coinChargingQuery(String jsonData) {
        //jsonData报文格式： {"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","coinFillingType":"1"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String coinFillingType = data.getString("coinFillingType");//coinFillingType":"1" 1为vad，2为deh
            if (null != userStrId && !"".equals(userStrId)
                    && !"".equals(coinFillingType)) {
                //查询用户tokenAddress地址 -2019-10-15
                Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                mapQueryUser.put("userStrId", userStrId);
                List<MUser> listUser = userUcWalletDao.selectUserList(mapQueryUser);
                if (listUser.size() > 0) {
                    jData.put("userStrId", userStrId);
                    jData.put("coinAddress", listUser.get(0).getTokenAddress());
                } else {//用户不存在
                    result = "-13";
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
     * 充值记录
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo rechargeRecord(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","rechargeType":"1"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String rechargeType = data.getString("rechargeType");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                mapQuery.put("rechargeType", rechargeType);
                List<CapitalDetailsVo> rechargeRecords = userUcWalletDao.rechargeRecord(mapQuery);
                jData.put("rechargeRecords", rechargeRecords);
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
     * 资产互转
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo transferAccounts(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"8541b4a043f4479c86124f8f701e399b",
        //                   "amount":"99.00","mobileOrEmail":"15012630328","transferType":"1"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String amount = data.getString("amount");
            String mobileOrEmail = data.getString("mobileOrEmail");
            String transferType = data.getString("transferType");
            String sign = (String) data.get("sign");
            if (null != sign) {
                if (null != userStrId && !"".equals(userStrId) && null != amount && !"".equals(amount)
                        && null != mobileOrEmail && !"".equals(mobileOrEmail)
                        && null != transferType && !"".equals(transferType)
                        && null != sign && !"".equals(sign)) {
                    //查询转账双方用户信息
                    Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                    Map<String, Object> modifyUserMap = new HashMap<String, Object>();
                    mapQueryUser.put("userStrId", userStrId);
                    List<MUser> listUser = userUcWalletDao.selectUserList(mapQueryUser);//查询vad/deh
                    Map<String, Object> mapQueryFriend = new HashMap<String, Object>();
                    Map<String, Object> modifyFriendMap = new HashMap<String, Object>();
                    if (mobileOrEmail.indexOf("@") != -1) {//邮箱
                        mapQueryFriend.put("email", mobileOrEmail);
                        modifyFriendMap.put("email", mobileOrEmail);
                    } else {//手机
                        mapQueryFriend.put("mobile", mobileOrEmail);
                        modifyFriendMap.put("mobile", mobileOrEmail);
                    }
                    List<MUser> listFriend = userUcWalletDao.selectUserList(mapQueryFriend);//查询vad/deh
                    //校验是否有那么多金额供提现/校验mobile是否为用户的收账地址
                    if ((listUser.size() > 0) && listFriend.size() > 0) {//两个账号都存在
                        MUser mUser = listUser.get(0);
                        MUser friend = listFriend.get(0);
                        mapQueryFriend.put("friendStrId", friend.getStrId());
                        modifyFriendMap.put("friendStrId", friend.getStrId());
                        if (!mUser.getStrId().equals(friend.getStrId())) {//不能给自己转账
                            //判断是否是在同一推荐线上
                            Map<String, Object> recommendUserQueryMap = new HashMap<String, Object>();
                            recommendUserQueryMap.put("userId", mUser.getId());
                            recommendUserQueryMap.put("friendId", friend.getId());
                            Boolean isItCorrect = userUcWalletDao.selectUserRecommend(recommendUserQueryMap);
                            if (isItCorrect) {
                                if ("1".equals(transferType)) {//转vad
                                    if (Double.valueOf(amount) <= mUser.getVad()) {
                                        //减少自己的vad，增加好友的vad
                                        Double beforeUserVad = mUser.getVad();
                                        Double afterUserVad = beforeUserVad - Double.valueOf(amount);
                                        modifyUserMap.put("userStrId", userStrId);
                                        modifyUserMap.put("afterUserVad", afterUserVad);
                                        userUcWalletDao.updateUser(modifyUserMap);
                                        Double beforeFriendVad = friend.getVad();
                                        Double afterFriendVad = beforeFriendVad + Double.valueOf(amount);
                                        modifyFriendMap.put("afterFriendVad", afterFriendVad);
                                        userUcWalletDao.updateUser(modifyFriendMap);
                                        //用户、好友增加一条资金明细订单表
                                        MUserFundDetailOrder userOrder = new MUserFundDetailOrder();
                                        userOrder.setStrId(Utils.getUUID());
                                        userOrder.setUserStrId(userStrId);
                                        userOrder.setOrderCategory(2);
                                        userOrder.setRelationOrderStrId("");
                                        userOrder.setTokenCategory(1);
                                        userOrder.setAmount(Double.valueOf(amount));
                                        userOrder.setBeforeAmount(beforeUserVad);
                                        userOrder.setAfterAmount(afterUserVad);
                                        //userOrder.setRemarks(mUser.getNickname()+"向"+friend.getNickname()+"转了"+amount+"vad");
                                        userOrder.setRemarks("转出" + amount + "vad");
                                        userOrder.setOrderStatus(1);
                                        userOrder.setStatus(1);
                                        userOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                        userOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                        userUcWalletDao.insertDetailOrder(userOrder);
                                        MUserFundDetailOrder friendOrder = new MUserFundDetailOrder();
                                        friendOrder.setStrId(Utils.getUUID());
                                        friendOrder.setUserStrId(friend.getStrId());
                                        friendOrder.setOrderCategory(1);
                                        friendOrder.setRelationOrderStrId("");
                                        friendOrder.setTokenCategory(1);
                                        friendOrder.setAmount(Double.valueOf(amount));
                                        friendOrder.setBeforeAmount(beforeFriendVad);
                                        friendOrder.setAfterAmount(afterFriendVad);
                                        //friendOrder.setRemarks(friend.getNickname()+"收到"+mUser.getNickname()+"转的"+amount+"vad");
                                        friendOrder.setRemarks("转入" + amount + "vad");
                                        friendOrder.setOrderStatus(1);
                                        friendOrder.setStatus(1);
                                        friendOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                        friendOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                        userUcWalletDao.insertDetailOrder(friendOrder);
                                        //增加用户转账记录
                                        MUserTransferOrder transferOrder = new MUserTransferOrder();
                                        transferOrder.setStrId(Utils.getUUID());
                                        transferOrder.setTransferUserStrId(userStrId);//转账人
                                        transferOrder.setPayeeUserStrId(friend.getStrId());//收账人
                                        transferOrder.setTransferAmount(Double.valueOf(amount));
                                        transferOrder.setTransferType(1);//转VAD
                                        transferOrder.setTransferBeforeAmount(beforeUserVad);
                                        transferOrder.setTransferAfterAmount(afterUserVad);
                                        transferOrder.setPayeeBeforeAmount(beforeFriendVad);
                                        transferOrder.setPayeeAfterAmount(afterFriendVad);
                                        transferOrder.setOrderStatus(1);
                                        transferOrder.setStatus(1);
                                        transferOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                        transferOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                        userUcWalletDao.insertTransferOrder(transferOrder);
                                    } else {
                                        result = "-15";//vad不足
                                        message = "Vad is not enough";
                                    }
                                }
                                if ("2".equals(transferType)) {//转DEH
                                    if (Double.valueOf(amount) <= mUser.getDeh()) {
                                        //减少自己的DEH，增加好友的DEH
                                        Double beforeUserDeh = mUser.getDeh();
                                        Double afterUserDeh = beforeUserDeh - Double.valueOf(amount);
                                        modifyUserMap.put("userStrId", userStrId);
                                        modifyUserMap.put("afterUserDeh", afterUserDeh);
                                        userUcWalletDao.updateUser(modifyUserMap);
                                        Double beforeFriendDeh = friend.getDeh();
                                        Double afterFriendDeh = beforeFriendDeh + Double.valueOf(amount);
                                        // modifyFriendMap.put("mobile", mobileOrEmail);
                                        //modifyFriendMap.put("email", mobileOrEmail);
                                        modifyFriendMap.put("afterFriendDeh", afterFriendDeh);
                                        userUcWalletDao.updateUser(modifyFriendMap);
                                        //用户、好友增加一条资金明细订单表
                                        MUserFundDetailOrder userOrder = new MUserFundDetailOrder();
                                        userOrder.setStrId(Utils.getUUID());
                                        userOrder.setUserStrId(userStrId);
                                        userOrder.setOrderCategory(2);
                                        userOrder.setRelationOrderStrId("");
                                        userOrder.setTokenCategory(2);
                                        userOrder.setAmount(Double.valueOf(amount));
                                        userOrder.setBeforeAmount(beforeUserDeh);
                                        userOrder.setAfterAmount(afterUserDeh);
                                        //userOrder.setRemarks(mUser.getNickname()+"向"+friend.getNickname()+"转了"+amount+"deh");
                                        userOrder.setRemarks("转出" + amount + "deh");
                                        userOrder.setOrderStatus(1);
                                        userOrder.setStatus(1);
                                        userOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                        userOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                        userUcWalletDao.insertDetailOrder(userOrder);
                                        MUserFundDetailOrder friendOrder = new MUserFundDetailOrder();
                                        friendOrder.setStrId(Utils.getUUID());
                                        friendOrder.setUserStrId(friend.getStrId());
                                        friendOrder.setOrderCategory(1);
                                        friendOrder.setRelationOrderStrId("");
                                        friendOrder.setTokenCategory(2);
                                        friendOrder.setAmount(Double.valueOf(amount));
                                        friendOrder.setBeforeAmount(beforeFriendDeh);
                                        friendOrder.setAfterAmount(afterFriendDeh);
                                        //friendOrder.setRemarks(friend.getNickname()+"收到"+mUser.getNickname()+"转的"+amount+"deh");
                                        friendOrder.setRemarks("转入" + amount + "deh");
                                        friendOrder.setOrderStatus(1);
                                        friendOrder.setStatus(1);
                                        friendOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                        friendOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                        userUcWalletDao.insertDetailOrder(friendOrder);
                                        //增加用户转账记录
                                        MUserTransferOrder transferOrder = new MUserTransferOrder();
                                        transferOrder.setStrId(Utils.getUUID());
                                        transferOrder.setTransferUserStrId(userStrId);//转账人
                                        transferOrder.setPayeeUserStrId(friend.getStrId());//收账人
                                        transferOrder.setTransferAmount(Double.valueOf(amount));
                                        transferOrder.setTransferType(2);//转DEH
                                        transferOrder.setTransferBeforeAmount(beforeUserDeh);
                                        transferOrder.setTransferAfterAmount(afterUserDeh);
                                        transferOrder.setPayeeBeforeAmount(beforeFriendDeh);
                                        transferOrder.setPayeeAfterAmount(afterFriendDeh);
                                        transferOrder.setOrderStatus(1);
                                        transferOrder.setStatus(1);
                                        transferOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                        transferOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                        userUcWalletDao.insertTransferOrder(transferOrder);
                                    } else {
                                        result = "-40";//deh不足
                                        message = "Deh is not enough!";
                                    }
                                }
                                if ("3".equals(transferType)) {//转定向vad
                                    List<MActivity> activityUserList = userUcWalletDao.selectDirectionalVADList(mapQueryUser);//查询用户定向币
                                    List<MActivity> activityFriendList = userUcWalletDao.selectDirectionalVADList(mapQueryFriend);//查询好友定向币
                                    if (activityFriendList.size() < 1) {
                                        MActivity activity = new MActivity();
                                        activity.setStrId(Utils.getUUID());
                                        activity.setUserId(friend.getId());
                                        activity.setUserStrId(friend.getStrId());
                                        activity.setAmount(0.00);
                                        activity.setAmountType(1);
                                        activity.setStatus(1);
                                        activity.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                        activity.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                        userUcWalletDao.insertUserActivity(activity);
                                    }
                                    //查两次
                                    List<MActivity> activityFriendList1 = userUcWalletDao.selectDirectionalVADList(mapQueryFriend);//查询好友定向币
                                    if (activityUserList.size() > 0 && activityFriendList1.size() > 0) {
                                        MActivity mActivity = activityUserList.get(0);
                                        MActivity fActivity = activityFriendList1.get(0);
                                        if (Double.valueOf(amount) <= mActivity.getAmount()) {
                                            //减少自己的转定向vad，增加好友的转定向vad
                                            Double beforeUserVad = mActivity.getAmount();
                                            Double afterUserVad = beforeUserVad - Double.valueOf(amount);
                                            modifyUserMap.put("userStrId", userStrId);
                                            modifyUserMap.put("afterUserVad", afterUserVad);
                                            userUcWalletDao.updateUserActivity(modifyUserMap);
                                            Double beforeFriendVad = fActivity.getAmount();
                                            Double afterFriendVad = beforeFriendVad + Double.valueOf(amount);
                                            modifyFriendMap.put("afterFriendVad", afterFriendVad);
                                            userUcWalletDao.updateUserActivity(modifyFriendMap);
                                            //用户、好友增加一条资金明细订单表
                                            MUserFundDetailOrder userOrder = new MUserFundDetailOrder();
                                            userOrder.setStrId(Utils.getUUID());
                                            userOrder.setUserStrId(userStrId);
                                            userOrder.setOrderCategory(2);
                                            userOrder.setRelationOrderStrId("");
                                            userOrder.setTokenCategory(1);
                                            userOrder.setAmount(Double.valueOf(amount));
                                            userOrder.setBeforeAmount(beforeUserVad);
                                            userOrder.setAfterAmount(afterUserVad);
                                            //userOrder.setRemarks(mUser.getNickname()+"向"+friend.getNickname()+"转了"+amount+"vad");
                                            userOrder.setRemarks("转出" + amount + "定向vad");
                                            userOrder.setOrderStatus(1);
                                            userOrder.setStatus(1);
                                            userOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                            userOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                            userUcWalletDao.insertDetailOrder(userOrder);
                                            MUserFundDetailOrder friendOrder = new MUserFundDetailOrder();
                                            friendOrder.setStrId(Utils.getUUID());
                                            friendOrder.setUserStrId(friend.getStrId());
                                            friendOrder.setOrderCategory(1);
                                            friendOrder.setRelationOrderStrId("");
                                            friendOrder.setTokenCategory(1);
                                            friendOrder.setAmount(Double.valueOf(amount));
                                            friendOrder.setBeforeAmount(beforeFriendVad);
                                            friendOrder.setAfterAmount(afterFriendVad);
                                            //friendOrder.setRemarks(friend.getNickname()+"收到"+mUser.getNickname()+"转的"+amount+"vad");
                                            friendOrder.setRemarks("转入" + amount + "定向vad");
                                            friendOrder.setOrderStatus(1);
                                            friendOrder.setStatus(1);
                                            friendOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                            friendOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                            userUcWalletDao.insertDetailOrder(friendOrder);
                                            //增加用户转账记录
                                            MUserTransferOrder transferOrder = new MUserTransferOrder();
                                            transferOrder.setStrId(Utils.getUUID());
                                            transferOrder.setTransferUserStrId(userStrId);//转账人
                                            transferOrder.setPayeeUserStrId(friend.getStrId());//收账人
                                            transferOrder.setTransferAmount(Double.valueOf(amount));
                                            transferOrder.setTransferType(3);//转定向vad
                                            transferOrder.setTransferBeforeAmount(beforeUserVad);
                                            transferOrder.setTransferAfterAmount(afterUserVad);
                                            transferOrder.setPayeeBeforeAmount(beforeFriendVad);
                                            transferOrder.setPayeeAfterAmount(afterFriendVad);
                                            transferOrder.setOrderStatus(1);
                                            transferOrder.setStatus(1);
                                            transferOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                            transferOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                            userUcWalletDao.insertTransferOrder(transferOrder);
                                        } else {
                                            result = "-15";//vad不足
                                            message = "Vad is not enough";
                                        }
                                    } else {
                                        result = "-29";//好友不存在
                                        message = "Friend not exist!";
                                    }
                                }
                            } else {
                                result = "-45";//此用户不在推荐线上
                                message = "This friend is not on the recommendation line！";
                            }
                        } else {
                            result = "-44";//不能给自己转账
                            message = "Can't transfer money to oneself!";
                        }
                    } else {
                        result = "-29";//好友不存在
                        message = "Friend not exist!";
                    }
                } else {
                    result = "-2";//参数错误
                    message = "Parameters error!";
                }
            } else {
                result = "-60";//当前版本不对
                message = "Please update to the latest version!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 查询提现数据
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo cashQuery(String jsonData) {
        //jsonData报文格式： {"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21"，"typesOfWithdrawal":"1"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String typesOfWithdrawal = data.getString("typesOfWithdrawal");//提币类型
            if (null != userStrId && !"".equals(userStrId)
                    && !"".equals(typesOfWithdrawal)) {
                Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                mapQueryUser.put("userStrId", userStrId);
                mapQueryUser.put("typesOfWithdrawal", typesOfWithdrawal);
                List<UserWithdrawalsVo> withdrawalsVoList = userUcWalletDao.selectCashInformation(mapQueryUser);
                if (withdrawalsVoList.size() > 0) {
                    jData.put("cashInformation", withdrawalsVoList.get(0));
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
     * 提现记录
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo withdrawalsRecord(String jsonData) {
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
                List<WithdrawalsRecordVo> withdrawalsRecords = userUcWalletDao.withdrawalsRecord(mapQuery);
                jData.put("expenditureDetails", withdrawalsRecords);
//                if (withdrawalsRecords.size() > 0) {
//                    jData.put("expenditureDetails", withdrawalsRecords);
//                } else {
//                    result = "-2";//参数错误
//                    message = "Parameters error!";
//                }
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
     * 资金明细
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo detailsOfFunds(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String sign = (String) data.get("sign");
            if (null != sign) {
                if (null != userStrId && !"".equals(userStrId)
                        && null != sign && !"".equals(sign)) {
                    Map<String, Object> mapQuery = new HashMap<String, Object>();
                    mapQuery.put("userStrId", userStrId);
                    List<CapitalDetailsVo> details = userUcWalletDao.detailsOfFunds(mapQuery);
                    jData.put("details", details);
                } else {
                    result = "-2";//参数错误
                    message = "Parameters error!";
                }
            }else {
                result = "-60";//当前版本不对
                message = "Please update to the latest version!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    //转入明细
    @Override
    public ResultVo capitalDetails(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","recordType":"6"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String recordType = data.getString("recordType");
            if (null != userStrId && !"".equals(userStrId) && null != recordType && !"".equals(recordType)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                mapQuery.put("recordType", recordType);
                List<CapitalDetailsVo> details = userUcWalletDao.variousDetails(mapQuery);
                jData.put("details", details);
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
     * 转出明细
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo transferredEffluent(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","recordType":"6"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String recordType = data.getString("recordType");
            if (null != userStrId && !"".equals(userStrId) && null != recordType && !"".equals(recordType)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                mapQuery.put("recordType", recordType);
                List<CapitalDetailsVo> details = userUcWalletDao.variousDetails(mapQuery);
                jData.put("details", details);
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
     * 支出明细
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo expenditureDetails(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","recordType":"6"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String recordType = data.getString("recordType");
            if (null != userStrId && !"".equals(userStrId) && null != recordType && !"".equals(recordType)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                mapQuery.put("recordType", recordType);
                List<CapitalDetailsVo> details = userUcWalletDao.variousDetails(mapQuery);
                jData.put("details", details);
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
     * 奖励明细
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo bonusDetails(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","recordType":"6"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String recordType = data.getString("recordType");
            if (null != userStrId && !"".equals(userStrId) && null != recordType && !"".equals(recordType)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                mapQuery.put("recordType", recordType);
                List<CapitalDetailsVo> details = userUcWalletDao.variousDetails(mapQuery);
                jData.put("details", details);
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
     * 充币记录（资金明细下）
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo coinageDetails(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","recordType":"5"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String recordType = data.getString("recordType");
            if (null != userStrId && !"".equals(userStrId) && null != recordType && !"".equals(recordType)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                mapQuery.put("recordType", recordType);
                List<CapitalDetailsVo> details = userUcWalletDao.variousDetails(mapQuery);
                jData.put("details", details);
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
     * 提币记录
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo currencyWithdrawalRecord(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","recordType":"6"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String recordType = data.getString("recordType");
            if (null != userStrId && !"".equals(userStrId) && null != recordType && !"".equals(recordType)) {
                Map<String, Object> mapQuery = new HashMap<String, Object>();
                mapQuery.put("userStrId", userStrId);
                mapQuery.put("recordType", recordType);
                List<CapitalDetailsVo> details = userUcWalletDao.variousDetails(mapQuery);
                jData.put("details", details);
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
     * 今日收益
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo todayProfit(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String sign = (String) data.get("sign");
            if (null != sign) {
                if (null != userStrId && !"".equals(userStrId)
                        && null != sign && !"".equals(sign)) {
                    String startTime = Utils.getNowDateStr2()+" 00:00:00";
                    String endTime = Utils.getNowDateStr2()+" 23:59:59";
                    long timestamp = Utils.getTimestamp();
                    Map<String, Object> mapQuery = new HashMap<String, Object>();
                    mapQuery.put("userStrId", userStrId);
                    mapQuery.put("startTime",startTime);
                    mapQuery.put("endTime",endTime);
                    mapQuery.put("timestamp",timestamp);
                    List<UserProfitVo> listTodayProfit = userUcWalletDao.findTodayProfit(mapQuery);
                    jData.put("todayProfit", listTodayProfit);
                } else {
                    result = "-2";//参数错误
                    message = "Parameters error!";
                }
            }else {
                result = "-60";//当前版本不对
                message = "Please update to the latest version!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 我的钱包
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo myWallet(String jsonData) {
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
                //查询活动定向币
                List<MActivity> listUserActivity = userUcWalletDao.UserActivity(mapQuery);
                if (listUserActivity.size() < 1) {//不存在先添加
                    MActivity activity = new MActivity();
                    activity.setStrId(Utils.getUUID());
                    activity.setUserId(0);
                    activity.setUserStrId(userStrId);
                    activity.setAmount(0.00);
                    activity.setAmountType(1);
                    activity.setStatus(1);
                    activity.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                    activity.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                    userUcWalletDao.insertUserActivity(activity);
                }
                List<UserWalletVo> listUserWallet = userUcWalletDao.findMyWallet(mapQuery);
                jData.put("userWallet", listUserWallet);
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
     * 获取资产合成数据
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo getAssetsSynthesisData(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            if (null != userStrId && !"".equals(userStrId)) {
                Map<String, Object> mapQueryUserAssetsSynthesisOrder = new HashMap<String, Object>();
                mapQueryUserAssetsSynthesisOrder.put("userStrId", userStrId);
                mapQueryUserAssetsSynthesisOrder.put("synthesisStatus", 2);//正在合成的
                List<MUserAssetsSynthesisOrder> listOrder = userUcWalletDao.selectUserAssetsSynthesisOrderList(mapQueryUserAssetsSynthesisOrder);
                if (listOrder.size() > 0) {
                    jData.put("userAssetsSynthesisOrder", listOrder.get(0));
                    result = "-36"; //已存在合成的订单
                    message = "Assets synthesis order already";
                } else {
                    jData.put("userAssetsSynthesisOrder", new JSONObject());
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
     * 资产合成
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo assetsSynthesis(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123","vad":"100"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String vad = data.getString("vad");
            String sign = (String) data.get("sign");
            if (null != sign) {
                if (null != userStrId && !"".equals(userStrId)
                        && null != vad && !"".equals(vad) && null != sign && !"".equals(sign)) {
                    /*是否存在正在合成的订单*/
                    Map<String, Object> mapQueryUserAssetsSynthesisOrderCount = new HashMap<String, Object>();
                    mapQueryUserAssetsSynthesisOrderCount.put("userStrId", userStrId);
                    mapQueryUserAssetsSynthesisOrderCount.put("synthesisStatus", 2);//正在合成的
                    int orderCount = userUcWalletDao.selectUserAssetsSynthesisOrderCount(mapQueryUserAssetsSynthesisOrderCount);
                    if (orderCount < 1) {//有正在合成的订单
                        /*vad和deh是否足够*/
                        Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                        mapQueryUser.put("userStrId", userStrId);
                        List<MUser> listUser = userUcWalletDao.selectUserList(mapQueryUser);
                        if (listUser.size() > 0) {
                            MUser user = listUser.get(0);
                            double v = Double.parseDouble(vad);
                            double deh = v * 5;
                            if (v % 100 == 0 && user.getVad() >= v && user.getDeh() >= deh) {//合成VAD必须是100的倍数
                                /*新增资产合成订单*/
                                MUserAssetsSynthesisOrder order = new MUserAssetsSynthesisOrder();
                                order.setDeh(deh);
                                order.setEndTime(Utils.getNextDay(7));
                                order.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                order.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                order.setStartTime(Utils.getNowTimeForSeconds());
                                order.setStatus(1);
                                order.setStrId(Utils.getUUID());
                                order.setSynthesisAmount(v + deh);
                                order.setSynthesisStatus(2);//正在合成
                                order.setUserStrId(userStrId);
                                order.setVad(v);
                                userUcWalletDao.insertUserAssetsSynthesisOrder(order);
                            } else {
                                result = "-37"; //资金不足
                                message = "VAD count error or vad and deh not enough!";
                            }
                        } else {
                            result = "-13"; //用户不存在
                            message = "User is not exist!";
                        }
                    } else {
                        result = "-36"; //已存在合成的订单
                        message = "Assets synthesis order already";
                    }
                } else {
                    result = "-2";//参数错误
                    message = "Parameters error!";
                }
            }else {
                result = "-60";//当前版本不对
                message = "Please update to the latest version!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 提现
     *
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo cashWithdrawal(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","
        // tokenAddress":"htttp:www.baidu.com","amount":"999.00","typesOfWithdrawal":"1"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String amount = data.getString("amount");//提现金额
            String tokenAddress = data.getString("tokenAddress");//提现地址
            String typesOfWithdrawal = data.getString("typesOfWithdrawal");//提现类型
            String sign = (String) data.get("sign");
            if (null != sign) {
                if (null != userStrId && !"".equals(userStrId) && null != amount && !"".equals(amount)
                        && null != tokenAddress && !"".equals(tokenAddress)
                        && null != amount && !"".equals(typesOfWithdrawal)
                        && null != sign && !"".equals(sign)) {
                    //校验tokenAddress是否为用户的提币地址
                    Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                    mapQueryUser.put("userStrId", userStrId);
                    List<MUser> userList = userUcWalletDao.selectUserList(mapQueryUser);
                    if (userList.size() > 0) {
                        MUser user = userList.get(0);
                        double actualArrival = Double.valueOf(amount);
                  /*  double minerCost = 0; //矿工费：1000以下100VAD，1000以上收取5%
                    double actualArrival = 0 ; //实际到账金额
                    if (Double.parseDouble(amount) < 1000) {//待确定1000收取多少矿工费 TODO
                        minerCost = 100;
                        actualArrival = Double.parseDouble(amount) - minerCost;
                    } else {
                        minerCost = Double.parseDouble(amount) * 0.05;
                        actualArrival = Double.parseDouble(amount) - minerCost;
                    }*/
                        if ("1".equals(typesOfWithdrawal)) {//提VAD
                            if (user.getVad() >= actualArrival) {//可以提现VAD
                                try {
                                    //withdrawToken(tokenAddress,Double.valueOf(actualArrival));//提现到交易所平台然后给用户
                                    //修改用户信息表中的vad、freeze_amount
                                    Double beforeFreezeAmount = user.getFreezeAmount();
                                    Double afterAmount = user.getVad() - Double.valueOf(amount);
                                    Double afterFreezeAmount = beforeFreezeAmount + Double.valueOf(amount);
                                    Map<String, Object> modifyUserMap = new HashMap<String, Object>();
                                    modifyUserMap.put("userStrId", userStrId);
                                    modifyUserMap.put("afterAmount", afterAmount);
                                    modifyUserMap.put("afterFreezeAmount", afterFreezeAmount);
                                    userUcWalletDao.updateUser(modifyUserMap);
                                    //新增一条用户资金冻结订单
                                    MUserFreezeOrder freezeOrder = new MUserFreezeOrder();
                                    String freezeOrderStrId = Utils.getUUID();
                                    freezeOrder.setStrId(freezeOrderStrId);
                                    freezeOrder.setUserStrId(userStrId);
                                    freezeOrder.setFreezeType(1);
                                    freezeOrder.setFreezeAmount(Double.valueOf(amount));
                                    freezeOrder.setBeforeFreezeAmount(beforeFreezeAmount);
                                    freezeOrder.setAfterFreezeAmount(afterFreezeAmount);
                                    freezeOrder.setFreezeStatus(1);
                                    freezeOrder.setStatus(1);
                                    freezeOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                    freezeOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                    userUcWalletDao.insertFreezeOrder(freezeOrder);
                                    //新增一条用户提现订单
                                    MUserCashOutOrder cashOutOrder = new MUserCashOutOrder();
                                    String cashOutOrderStrId = Utils.getUUID();
                                    cashOutOrder.setStrId(cashOutOrderStrId);
                                    cashOutOrder.setUserStrId(userStrId);
                                    cashOutOrder.setFreezeOrderStrId(freezeOrderStrId);
                                    cashOutOrder.setAmount(Double.valueOf(amount));
                                    cashOutOrder.setCashOutStatus(1); //等待审核状态
                                    cashOutOrder.setRemarkMsg(user.getNickname() + "提取" + actualArrival + "VAD");
                                    cashOutOrder.setUserMobile(user.getMobile());//手机
                                    cashOutOrder.setUserEmail(user.getEmail());//邮箱
                                    cashOutOrder.setTokenAddress(tokenAddress);//提现地址
                                    cashOutOrder.setStatus(1);
                                    cashOutOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                    cashOutOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                    userUcWalletDao.insertCashOutOrder(cashOutOrder);
                                    //新增资金明细（提币记录）
                                    MUserFundDetailOrder theftOrder = new MUserFundDetailOrder();
                                    theftOrder.setAfterAmount(afterAmount);
                                    theftOrder.setAmount(actualArrival);//实际到账金额
                                    theftOrder.setBeforeAmount(user.getVad());
                                    theftOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                    theftOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                    theftOrder.setOrderCategory(6);//提币记录
                                    theftOrder.setOrderStatus(1);
                                    theftOrder.setRelationOrderStrId(cashOutOrderStrId);
                                    theftOrder.setRemarks("提取" + actualArrival + " VAD");
                                    theftOrder.setStatus(1);
                                    theftOrder.setStrId(Utils.getUUID());
                                    theftOrder.setTokenCategory(1);
                                    theftOrder.setUserStrId(userStrId);
                                    userUcWalletDao.insertUserFundDetailOrder(theftOrder);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    result = "-47";//提现失败
                                    message = "Failure to withdraw money!";
                                }
                            } else {
                                result = "-15";//VAD不足
                                message = "Vad is not enough!";
                            }
                        } else if ("2".equals(typesOfWithdrawal)) {//提DEH
                            if (user.getDeh() >= actualArrival) { //可以提取DEH
                                try {
                                    //withdrawToken(tokenAddress,Double.valueOf(actualArrival));//提现到交易所平台然后给用户
                                    //修改用户信息表中的vad、freeze_amount
                                    Double beforeFreezeAmount = user.getFreezeAmount();
                                    Double afterAmount = user.getDeh() - Double.valueOf(amount);
                                    Double afterFreezeAmount = beforeFreezeAmount + Double.valueOf(amount);
                                    Map<String, Object> modifyUserMap = new HashMap<String, Object>();
                                    modifyUserMap.put("userStrId", userStrId);
                                    modifyUserMap.put("afterAmountDeh", afterAmount);
                                    modifyUserMap.put("afterFreezeAmount", afterFreezeAmount);
                                    userUcWalletDao.updateUser(modifyUserMap);
                                    //新增一条用户资金冻结订单
                                    MUserFreezeOrder freezeOrder = new MUserFreezeOrder();
                                    String freezeOrderStrId = Utils.getUUID();
                                    freezeOrder.setStrId(freezeOrderStrId);
                                    freezeOrder.setUserStrId(userStrId);
                                    freezeOrder.setFreezeType(1);
                                    freezeOrder.setFreezeAmount(Double.valueOf(amount));
                                    freezeOrder.setBeforeFreezeAmount(beforeFreezeAmount);
                                    freezeOrder.setAfterFreezeAmount(afterFreezeAmount);
                                    freezeOrder.setFreezeStatus(1);
                                    freezeOrder.setStatus(1);
                                    freezeOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                    freezeOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                    userUcWalletDao.insertFreezeOrder(freezeOrder);
                                    //新增一条用户提现订单
                                    MUserCashOutOrder cashOutOrder = new MUserCashOutOrder();
                                    String cashOutOrderStrId = Utils.getUUID();
                                    cashOutOrder.setStrId(cashOutOrderStrId);
                                    cashOutOrder.setUserStrId(userStrId);
                                    cashOutOrder.setFreezeOrderStrId(freezeOrderStrId);
                                    cashOutOrder.setAmount(Double.valueOf(amount));
                                    cashOutOrder.setCashOutStatus(1); //等待审核状态
                                    cashOutOrder.setRemarkMsg(user.getNickname() + "提取" + actualArrival + "DEH");
                                    cashOutOrder.setUserMobile(user.getMobile());//手机
                                    cashOutOrder.setUserEmail(user.getEmail());//邮箱
                                    cashOutOrder.setTokenAddress(tokenAddress);//提现地址
                                    cashOutOrder.setStatus(1);
                                    cashOutOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                    cashOutOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                    userUcWalletDao.insertCashOutOrder(cashOutOrder);
                                    //新增资金明细（提币记录）
                                    MUserFundDetailOrder theftOrder = new MUserFundDetailOrder();
                                    theftOrder.setAfterAmount(afterAmount);
                                    theftOrder.setAmount(actualArrival);//实际到账金额
                                    theftOrder.setBeforeAmount(user.getVad());
                                    theftOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                    theftOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                    theftOrder.setOrderCategory(6);//提币记录
                                    theftOrder.setOrderStatus(1);
                                    theftOrder.setRelationOrderStrId(cashOutOrderStrId);
                                    theftOrder.setRemarks("提取" + actualArrival + "DEH");
                                    theftOrder.setStatus(1);
                                    theftOrder.setStrId(Utils.getUUID());
                                    theftOrder.setTokenCategory(1);
                                    theftOrder.setUserStrId(userStrId);
                                    userUcWalletDao.insertUserFundDetailOrder(theftOrder);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    result = "-47";//提现失败
                                    message = "Failure to withdraw money!";
                                }
                            } else {
                                result = "-40";//DEH不足
                                message = "Deh is not enough!";
                            }
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
                result = "-60";//当前版本不对
                message = "Please update to the latest version!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }
}
