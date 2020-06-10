package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-用户个人中心-我的钱包-调度服务
 * @author william
 *
 */
public interface UserUcWalletService {


    /**
     * 我的钱包
     * @param jsonData
     * @return
     */
    ResultVo myWallet(String jsonData);

    /**
     * 今日收益
     * @param jsonData
     * @return
     */
    ResultVo todayProfit(String jsonData);

    /**
     * 提现记录
     * @param jsonData
     * @return
     */
    ResultVo withdrawalsRecord(String jsonData);

    /**
     * 资产互转
     * @param jsonData
     * @return
     */
    ResultVo transferAccounts(String jsonData);

    /**
     * 提现
     * @param jsonData
     * @return
     */
    ResultVo cashWithdrawal(String jsonData);
    
    /**
     * 获取资产合成数据
     * @param jsonData
     * @return
     */
    ResultVo getAssetsSynthesisData(String jsonData);
    
    /**
     * 资产合成
     * @param jsonData
     * @return
     */
    ResultVo assetsSynthesis(String jsonData);

    /**
     * 充值记录
     * @param jsonData
     * @return
     */
    ResultVo rechargeRecord(String jsonData);

    /**
     * 资金明细
     * @param jsonData
     * @return
     */
    ResultVo detailsOfFunds(String jsonData);

    /**
     * 转入明细
     * @param jsonData
     * @return
     */
    ResultVo capitalDetails(String jsonData);

    /**
     * 转出明细
     * @param jsonData
     * @return
     */
    ResultVo transferredEffluent(String jsonData);

    /**
     * 支出明细
     * @param jsonData
     * @return
     */
    ResultVo expenditureDetails(String jsonData);

    /**
     * 奖励明细
     * @param jsonData
     * @return
     */
    ResultVo bonusDetails(String jsonData);

    /**
     * 充币记录
     * @param jsonData
     * @return
     */
    ResultVo coinageDetails(String jsonData);

    /**
     * 提币记录
     * @param jsonData
     * @return
     */
    ResultVo currencyWithdrawalRecord(String jsonData);

    /**
     * 提现数据查询
     * @param jsonData
     * @return
     */
    ResultVo cashQuery(String jsonData);

    /**
     * 充币地址查询
     * @param jsonData
     * @return
     */
    ResultVo coinChargingQuery(String jsonData);

    /**
     * 收款人昵称查询
     * @param jsonData
     * @return
     */
    ResultVo queryNickname(String jsonData);
}
