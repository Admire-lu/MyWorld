package com.cly.mineworld.service.controller;

import com.cly.mineworld.service.service.UserUcWalletService;
import com.cly.mineworld.service.vo.common.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端-用户个人中心-我的钱包-控制器
 * @author william
 *
 */
@RestController
public class UserUcWalletController {

    @Autowired
    private UserUcWalletService userUcWalletService;


    /**
     * 收款人昵称查询
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/queryNickname.html")
    public ResultVo queryNickname(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.queryNickname(jsonData);
    }

    /**
     * 充值地址查询
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/coinChargingQuery.html")
    public ResultVo coinChargingQuery(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.coinChargingQuery(jsonData);
    }

    /**
     * 充币地址查询
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/rechargeRecord.html")
    public ResultVo rechargeRecord(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.rechargeRecord(jsonData);
    }

    /**
     * 资产互转
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/transferAccounts.html")
    public ResultVo transferAccounts(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.transferAccounts(jsonData);
    }

    /**
     * 提现
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/cashQuery.html")
    public ResultVo cashQuery(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.cashQuery(jsonData);
    }

    /**
     * 提现
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/cashWithdrawal.html")
    public ResultVo cashWithdrawal(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.cashWithdrawal(jsonData);
    }

    /**
     * 提现记录
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/withdrawalsRecord.html")
    public ResultVo withdrawalsRecord(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.withdrawalsRecord(jsonData);
    }


    /**
     * 资金明细
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/detailsOfFunds.html")
    public ResultVo detailsOfFunds(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.detailsOfFunds(jsonData);
    }

    /**
     * 转入明细
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/capitalDetails.html")
    public ResultVo capitalDetails(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.capitalDetails(jsonData);
    }

    /**
     * 转出明细
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/transferredEffluent.html")
    public ResultVo transferredEffluent(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.transferredEffluent(jsonData);
    }

    /**
     * 支出明细
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/expenditureDetails.html")
    public ResultVo expenditureDetails(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.expenditureDetails(jsonData);
    }

    /**
     * 奖励明细
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/bonusDetails.html")
    public ResultVo bonusDetails(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.bonusDetails(jsonData);
    }

    /**
     * 充币记录
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/coinageDetails.html")
    public ResultVo coinageDetails(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.coinageDetails(jsonData);
    }

    /**
     * 提币记录
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/currencyWithdrawalRecord.html")
    public ResultVo currencyWithdrawalRecord(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.currencyWithdrawalRecord(jsonData);
    }

    /**
     * 今日收益
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/todayProfit.html")
    public ResultVo todayProfit(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.todayProfit(jsonData);
    }

    /**
     * 我的钱包
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/myWallet.html")
    public ResultVo myWallet(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.myWallet(jsonData);
    }
    
    /**
     * 获取资产合成界面数据
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCenter/wallet/getAssetsSynthesisData.html")
    public ResultVo getAssetsSynthesisData(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.getAssetsSynthesisData(jsonData);
    }
    
    /**
     * 资产合成
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCenter/wallet/assetsSynthesis.html")
    public ResultVo assetsSynthesis(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcWalletService.assetsSynthesis(jsonData);
    }
}
