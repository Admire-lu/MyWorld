package com.cly.mineworld.service.dao;

import com.cly.mineworld.service.entity.*;
import com.cly.mineworld.service.vo.userCapitalDetails.CapitalDetailsVo;
import com.cly.mineworld.service.vo.userProfit.UserProfitVo;
import com.cly.mineworld.service.vo.userWallet.UserWalletVo;
import com.cly.mineworld.service.vo.userWithdrawals.UserWithdrawalsVo;
import com.cly.mineworld.service.vo.userWithdrawalsRecord.WithdrawalsRecordVo;
import java.util.List;
import java.util.Map;

public interface UserUcWalletDao {


    /**
     * 我的钱包
     * @param map
     * @return
     */
    List<UserWalletVo> findMyWallet(Map<String, Object> map);

    /**
     * 今日收益
     * @param map
     * @return
     */
    List<UserProfitVo> findTodayProfit(Map<String, Object> map);

    /**
     * 支出明细
     * @param map
     * @return
     */
    List<CapitalDetailsVo> expenditureDetails(Map<String, Object> map);

    /**
     * 增加资金冻结订单
     * @param freezeOrder
     */
    void insertFreezeOrder(MUserFreezeOrder freezeOrder);

    /**
     * 增加用户提现订单
     * @param cashOutOrder
     */
    void insertCashOutOrder(MUserCashOutOrder cashOutOrder);

    /**
     * 奖励明细
     * @param map
     * @return
     */
    List<CapitalDetailsVo> bonusDetails(Map<String, Object> map);

    /**
     * 提现记录
     * @param map
     * @return
     */
    List<WithdrawalsRecordVo> withdrawalsRecord(Map<String, Object> map);

    /**
     * 资金明细
     * @param map
     * @return
     */
    List<CapitalDetailsVo> detailsOfFunds(Map<String, Object> map);

    /**
     * 转入明细
     * @param map
     * @return
     */
    List<CapitalDetailsVo> transferDetails(Map<String, Object> map);

    /**
     * 转出明细
     * @param map
     * @return
     */
    List<CapitalDetailsVo> rollOutDetails(Map<String, Object> map);

    /**
     * 获取用户信息
     * @param map
     * @return
     */
    List<MUser> selectUserList(Map<String, Object> map);

    /**
     * 设置vad、freeze_amount
     * @param map
     */
    void updateUser(Map<String, Object> map);
    
    /**
     * 查询用户资产合成订单数组
     * @param map
     * @return
     */
    List<MUserAssetsSynthesisOrder> selectUserAssetsSynthesisOrderList(Map<String,Object> map);
    
    /**
     * 新增用户资产合成订单
     * @param order
     */
    void insertUserAssetsSynthesisOrder(MUserAssetsSynthesisOrder order);
    
    /**
     * 查询用户资产合成订单总数
     * @param map
     * @return
     */
    int selectUserAssetsSynthesisOrderCount(Map<String,Object> map);

    /**
     * 充值记录
     * @param map
     * @return
     */
    List<CapitalDetailsVo> rechargeRecord(Map<String, Object> map);

    /**
     * 资产互转
     * @param order
     */
    void insertDetailOrder(MUserFundDetailOrder order);

    /**
     * 各种明细
     * @param map
     * @return
     */
    List<CapitalDetailsVo> variousDetails(Map<String, Object> map);

    /**
     * 查询是否在同意推荐线上
     * @param map
     * @return
     */
    Boolean selectUserRecommend(Map<String, Object> map);

    /**
     * 查询提现信息
     * @param map
     * @return
     */
    List<UserWithdrawalsVo> selectCashInformation(Map<String, Object> map);

    /**
     * 新增资金明细记录
     * @param theftOrder
     */
    void insertUserFundDetailOrder(MUserFundDetailOrder theftOrder);

    /**
     * 查询用户定向vad金额
     * @param map
     * @return
     */
    List<MActivity> selectDirectionalVADList(Map<String, Object> map);

    /**
     * 更新用户定向vad金额
     * @param map
     */
    void updateUserActivity(Map<String, Object> map);

    /**
     *  //查询定向币
     * @param map
     * @return
     */
    List<MActivity> UserActivity(Map<String, Object> map);

    /**
     * 新增定向币
     * @param activity
     */
    void insertUserActivity(MActivity activity);

    /**
     * 新增转账明细记录
     * @param transferOrder
     */
    void insertTransferOrder(MUserTransferOrder transferOrder);
}
