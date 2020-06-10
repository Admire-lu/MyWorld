package com.cly.mineworld.service.dao.impl;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcWalletDao;
import com.cly.mineworld.service.entity.*;
import com.cly.mineworld.service.vo.userCapitalDetails.CapitalDetailsVo;
import com.cly.mineworld.service.vo.userProfit.UserProfitVo;
import com.cly.mineworld.service.vo.userWallet.UserWalletVo;
import com.cly.mineworld.service.vo.userWithdrawals.UserWithdrawalsVo;
import com.cly.mineworld.service.vo.userWithdrawalsRecord.WithdrawalsRecordVo;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class UserUcWalletDaoImpl implements UserUcWalletDao {

    @PersistenceContext
    private EntityManager em;
    private Query query = null;

    @SuppressWarnings("unchecked")
    @Override
    public List<MActivity> UserActivity(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MActivity a ");
        hql.append(" where a.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and a.userStrId = '" + map.get("userStrId").toString() + "' ");
        }
        query = em.createQuery(hql.toString());
        return query.getResultList();
    }

    @Override
    public void insertUserActivity(MActivity activity) {
        em.persist(activity);
    }

    /**
     *新增转账明细记录
     * @param transferOrder
     */
    @Override
    public void insertTransferOrder(MUserTransferOrder transferOrder) {
        em.persist(transferOrder);
    }

    /**
     * 我的钱包
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserWalletVo> findMyWallet(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select *  from ");
        sql.append(" (select ");
        sql.append(" str_id as userStrId, ");
        sql.append(" vad as accountVad, ");
        sql.append(" deh as accountDeh ");
        sql.append(" from ");
        sql.append(" m_user ");
        sql.append(" where status = 1");
        if (null != map.get("userStrId")) {
            sql.append(" and str_id = '" + map.get("userStrId").toString() + "') a");
        }
        sql.append(" join");
        sql.append(" (select ");
        sql.append(" amount ");
        sql.append(" from m_activity_20191016");
        sql.append(" where status = 1");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "') b");
        }
        List<UserWalletVo> listVo = new ArrayList<UserWalletVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserWalletVo vo = new UserWalletVo();
            vo.setUserStrId(rowArray[0].toString());
            vo.setAccountVad(Double.valueOf(rowArray[1].toString()));
            vo.setAccountDeh(Double.valueOf(rowArray[2].toString()));
            vo.setDirectionalVad(Double.valueOf(rowArray[3].toString()));
            vo.setToBeReleasedVad(Double.valueOf("0.00"));
            vo.setToBeReleasedDeh(Double.valueOf("0.00"));
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 今日收益
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserProfitVo> findTodayProfit(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        //sql.append(" select *  from ");
        sql.append(" select ");
        sql.append(" user_str_id as userStrId, ");
        sql.append(" sum(profit_amount)  as hotelProfit  ");
        sql.append(" from ");
        sql.append(" m_user_profit ");
        sql.append(" where profit_category='1' ");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        sql.append(" and create_time >= unix_timestamp('" + map.get("startTime").toString() + "') and create_time <=  unix_timestamp('"+ map.get("endTime").toString() +"')");
       /* if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "') ap ");
        }*/
        List<UserProfitVo> listVo = new ArrayList<UserProfitVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        if (listResult.size() > 0) {
            for (Object row : listResult) {
                Object[] rowArray = (Object[]) row;
                UserProfitVo vo = new UserProfitVo();
                if (null != rowArray[0]) {
                    vo.setUserStrId(rowArray[0].toString());
                } else {
                    vo.setUserStrId(map.get("userStrId").toString());
                }
                Double hotelProfit = 0.00;
                if (null != rowArray[1]) {//酒店收益--今日
                    hotelProfit = Double.valueOf(rowArray[1].toString());
                }
                vo.setHotelProfit(hotelProfit);
                Double invitationProfit = selectInvitationProfit(map);//邀请收益--今日
                vo.setInvitationProfit(invitationProfit);
                Double theftProfit = selectTheftProfit(map);  //盗窃收益
                vo.setTheftProfit(theftProfit);
                Double beiTheftProfit = selectBeiTheftProfit(map);//被盗窃收益
                vo.setBeiTheftProfit(beiTheftProfit);
                //Double teamProfit = selectTeamProfit(map);//团队收益（今日总收益）
                Double teamProfit = hotelProfit + invitationProfit  + theftProfit;//酒店、邀请、盗窃
                vo.setTeamProfit(teamProfit);
                Double totalProfit = selectTotalProfit(map) + selectTotalTheftProfit(map);//总收益（累计）/盗窃
                vo.setTotalProfit(totalProfit);
                listVo.add(vo);
            }
        } else {
            UserProfitVo vo = new UserProfitVo();
            Double hotelProfit = 0.00;
            vo.setHotelProfit(hotelProfit);
            vo.setUserStrId(map.get("userStrId").toString());
            Double invitationProfit = selectInvitationProfit(map);//邀请收益
            vo.setInvitationProfit(invitationProfit);
            Double theftProfit = selectTheftProfit(map);  //盗窃收益
            vo.setTheftProfit(theftProfit);
            Double beiTheftProfit = selectBeiTheftProfit(map);//被盗窃收益
            vo.setBeiTheftProfit(beiTheftProfit);
            //Double teamProfit = selectTeamProfit(map);//团队收益（今日总收益）
            Double teamProfit = hotelProfit + invitationProfit  + theftProfit;//酒店、邀请、盗窃
            vo.setTeamProfit(teamProfit);
            Double totalProfit = selectTotalProfit(map) + selectTotalTheftProfit(map);//总收益（累计）/盗窃
            vo.setTotalProfit(totalProfit);
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     *总收益（累计）
     * @param map
     * @return
     */
    private Double selectTotalProfit(Map<String, Object> map){
        StringBuffer hql = new StringBuffer();
        hql.append(" select id, sum(profit_amount) as teamProfit from  m_user_profit ");
        hql.append(" where status = 1 ");
        hql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        Double totalProfit = 0.00;
        List<Object> listResult = em.createNativeQuery(hql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            if (null != rowArray[1]) {
                totalProfit = Double.valueOf(rowArray[1].toString());
            }
        }
        return totalProfit;
    }


    /**
     *盗窃收益--总计
     * @param map
     * @return
     */
    private Double selectTotalTheftProfit(Map<String, Object> map){
        StringBuffer hql = new StringBuffer();
        hql.append(" select id, sum(amount) from  m_user_item_use_history ");
        hql.append(" where status = 1 and history_type = 1 ");
        hql.append(" and use_user_str_id = '" + map.get("userStrId").toString() + "'");
        Double invitationProfit = 0.00;
        List<Object> listResult = em.createNativeQuery(hql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            if (null != rowArray[1]) {
                invitationProfit = Double.valueOf(rowArray[1].toString());
            }
        }
        return invitationProfit;
    }

    /**
     *被盗窃收益--今日
     * @param map
     * @return
     */
    private Double selectBeiTheftProfit(Map<String, Object> map){
        StringBuffer hql = new StringBuffer();
        hql.append(" select id, sum(amount) from  m_user_item_use_history ");
        hql.append(" where status = 1 and history_type = 2  ");
        hql.append(" and be_used_user_str_id = '" + map.get("userStrId").toString() + "'");
        hql.append(" and create_time >= unix_timestamp('" + map.get("startTime").toString() + "') and create_time <=  unix_timestamp('"+ map.get("endTime").toString() +"')");
        Double invitationProfit = 0.00;
        List<Object> listResult = em.createNativeQuery(hql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            if (null != rowArray[1]) {
                invitationProfit = Double.valueOf(rowArray[1].toString());
            }
        }
        return invitationProfit;
    }

    /**
     *盗窃收益--今日
     * @param map
     * @return
     */
    private Double selectTheftProfit(Map<String, Object> map){
        StringBuffer hql = new StringBuffer();
        hql.append(" select id, sum(amount) from  m_user_item_use_history ");
        hql.append(" where status = 1 and history_type = 1 ");
        hql.append(" and use_user_str_id = '" + map.get("userStrId").toString() + "'");
        hql.append(" and create_time >= unix_timestamp('" + map.get("startTime").toString() + "') and create_time <=  unix_timestamp('"+ map.get("endTime").toString() +"')");
        Double invitationProfit = 0.00;
        List<Object> listResult = em.createNativeQuery(hql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            if (null != rowArray[1]) {
                invitationProfit = Double.valueOf(rowArray[1].toString());
            }
        }
        return invitationProfit;
    }

    /**
     *团队收益
     * @param map
     * @return
     */
    private Double selectTeamProfit(Map<String, Object> map){
        StringBuffer hql = new StringBuffer();
        hql.append(" select id, sum(profit_amount) as teamProfit from  m_user_profit ");
        hql.append(" where status = 1 and profit_category='4' ");
        hql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        Double invitationProfit = 0.00;
        List<Object> listResult = em.createNativeQuery(hql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            if (null != rowArray[1]) {
                invitationProfit = Double.valueOf(rowArray[1].toString());
            }
        }
        return invitationProfit;
    }

    /**
     *邀请收益--今日
     * @param map
     * @return
     */
    private Double selectInvitationProfit(Map<String, Object> map){
        StringBuffer hql = new StringBuffer();
        hql.append(" select id,sum(profit_amount)  as invitationProfit from  m_user_profit p ");
        hql.append(" where status = 1 and ( profit_category='2' or profit_category='3' )");
        hql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        hql.append(" and create_time >= unix_timestamp('" + map.get("startTime").toString() + "') and create_time <=  unix_timestamp('"+ map.get("endTime").toString() +"')");
        Double invitationProfit = 0.00;
        List<Object> listResult = em.createNativeQuery(hql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            if (null != rowArray[1]) {
                invitationProfit = Double.valueOf(rowArray[1].toString());
            }
        }
        return invitationProfit;
    }

    /**
     * 支出明细
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CapitalDetailsVo> expenditureDetails(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select *  from ");
        sql.append(" ( select ");
        sql.append(" user_str_id as userStrId, ");
        sql.append(" consumption_category_str_id as categoryStrId, ");
        sql.append(" amount, ");
        sql.append(" create_time as createTime ");
        sql.append(" from ");
        sql.append(" m_user_consumption_history ");
        sql.append(" where status = 1 ");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "') ch ");
        }
        sql.append(" join ");
        sql.append(" ( select ");
        sql.append(" category_name as categoryName, ");
        sql.append(" category_desc as categoryDesc ");
        sql.append(" from ");
        sql.append(" m_user_consumption_category ");
        sql.append(" where str_id in ");
        sql.append(" ( select ");
        sql.append(" consumption_category_str_id as categoryStrId ");
        sql.append(" from ");
        sql.append(" m_user_consumption_history ");
        sql.append(" where status = 1 ");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "')) cc ");
        }
        List<CapitalDetailsVo> listVo = new ArrayList<CapitalDetailsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            CapitalDetailsVo vo = new CapitalDetailsVo();
            String date = Utils.timeStampToDate(Long.valueOf(rowArray[3].toString()));
            vo.setUserStrId(rowArray[0].toString());
            vo.setDate(date);
            vo.setAmountOfMoney(Double.valueOf(rowArray[2].toString()));
            vo.setType(rowArray[4].toString());
            vo.setRemarks(rowArray[5].toString());
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 保存用户冻结金额订单
     * @param freezeOrder
     */
    @Override
    public void insertFreezeOrder(MUserFreezeOrder freezeOrder) {
        em.persist(freezeOrder);
    }

    /**
     * 保存提现订单
     * @param cashOutOrder
     */
    @Override
    public void insertCashOutOrder(MUserCashOutOrder cashOutOrder) {
        em.persist(cashOutOrder);
    }

    /**
     * 奖励明细
     * @param map
     * @return
     */
    @Override
    public List<CapitalDetailsVo> bonusDetails(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" user_str_id as userStrId, ");
        sql.append(" profit_category as profitCategory, ");
        sql.append(" profit_amount as profitAmount, ");
        sql.append(" profit_remarks as profitRemarks, ");
        sql.append(" create_time as createTime ");
        sql.append(" from ");
        sql.append(" m_user_profit ");
        sql.append(" where  profit_category = '2' ");
        sql.append("  or  profit_category = '3' ");
        sql.append("  and status = 1  ");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        List<CapitalDetailsVo> listVo = new ArrayList<CapitalDetailsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            CapitalDetailsVo vo = new CapitalDetailsVo();
            String date = Utils.timeStampToDate(Long.valueOf(rowArray[4].toString()));
            vo.setUserStrId(rowArray[0].toString());
            vo.setType("增加");
            vo.setAmountOfMoney(Double.valueOf(rowArray[2].toString()));
            vo.setRemarks(rowArray[3].toString());
            vo.setDate(date);
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 资金明细
     * @param map
     * @return
     */
    @Override
    public List<CapitalDetailsVo> detailsOfFunds(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" user_str_id as userStrId, ");
        sql.append(" amount as rechargeAmount, ");
        sql.append(" remarks, ");
        sql.append(" order_category as orderCategory, ");
        sql.append(" modify_time as modifyTime, ");
        sql.append(" create_time as createTime ");
        sql.append(" from ");
        sql.append(" m_user_fund_detail_order ");
        sql.append(" where status = 1 ");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        sql.append(" order by create_time  DESC ");
        List<CapitalDetailsVo> listVo = new ArrayList<CapitalDetailsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            CapitalDetailsVo vo = new CapitalDetailsVo();
            String date = Utils.timeStampToDate(Long.valueOf(rowArray[5].toString()));
            vo.setUserStrId(rowArray[0].toString());
            vo.setAmountOfMoney(Double.valueOf(rowArray[1].toString()));
            vo.setDate(date);
            vo.setRemarks(rowArray[2].toString());
            if ("1".equals(rowArray[3].toString())) {
                vo.setType("1");//增加
            }else if ("2".equals(rowArray[3].toString())){
                vo.setType("0");//减少
            }else if ("3".equals(rowArray[3].toString())){
                vo.setType("0");//减少
            }else if ("4".equals(rowArray[3].toString())){
                vo.setType("1");//增加
            }else if ("5".equals(rowArray[3].toString())){
                vo.setType("1");//增加
            }else if ("6".equals(rowArray[3].toString())){
                vo.setType("0");//减少
            }
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 转入明细
     * @param map
     * @return
     */
    @Override
    public List<CapitalDetailsVo> transferDetails(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" user_str_id as userStrId, ");
        sql.append(" amount as rechargeAmount, ");
        sql.append(" remarks, ");
        sql.append(" order_status as orderStatus, ");
        sql.append(" modify_time as modifyTime, ");
        sql.append(" create_time as createTime ");
        sql.append(" from ");
        sql.append(" m_user_fund_detail_order ");
        sql.append(" where status = 1 ");
        sql.append(" and order_category = 1");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        sql.append(" order by create_time  DESC ");
        List<CapitalDetailsVo> listVo = new ArrayList<CapitalDetailsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            CapitalDetailsVo vo = new CapitalDetailsVo();
            String date = Utils.timeStampToDate(Long.valueOf(rowArray[5].toString()));
            vo.setUserStrId(rowArray[0].toString());
            vo.setAmountOfMoney(Double.valueOf(rowArray[1].toString()));
            vo.setDate(date);
            vo.setRemarks(rowArray[2].toString());
            if ("1".equals(rowArray[3].toString())){
                vo.setType("完成");
            }else {
                vo.setType("失败");
            }
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 转出明细
     * @param map
     * @return
     */
    @Override
    public List<CapitalDetailsVo> rollOutDetails(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" user_str_id as userStrId, ");
        sql.append(" amount as rechargeAmount, ");
        sql.append(" remarks, ");
        sql.append(" order_status as orderStatus, ");
        sql.append(" modify_time as modifyTime, ");
        sql.append(" create_time as createTime ");
        sql.append(" from ");
        sql.append(" m_user_fund_detail_order ");
        sql.append(" where status = 1 ");
        sql.append(" and order_category = 2");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        sql.append(" order by create_time  DESC ");
        List<CapitalDetailsVo> listVo = new ArrayList<CapitalDetailsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            CapitalDetailsVo vo = new CapitalDetailsVo();
            String date = Utils.timeStampToDate(Long.valueOf(rowArray[5].toString()));
            vo.setUserStrId(rowArray[0].toString());
            vo.setAmountOfMoney(Double.valueOf(rowArray[1].toString()));
            vo.setDate(date);
            vo.setRemarks(rowArray[2].toString());
            if ("1".equals(rowArray[3].toString())) {
                vo.setType("完成");
            } else {
                vo.setType("失败");
            }
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 提现记录
     * @param map
     * @return
     */
    @Override
    public List<WithdrawalsRecordVo> withdrawalsRecord(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" user_str_id as userStrId, ");
        sql.append(" amount, ");
        sql.append(" cash_out_status as cashOutStatus, ");
        sql.append(" modify_time as modifyTime, ");
        sql.append(" create_time as createTime ");
        sql.append(" from ");
        sql.append(" m_user_cash_out_order ");
        sql.append(" where status = 1 ");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        List<WithdrawalsRecordVo> listVo = new ArrayList<WithdrawalsRecordVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            WithdrawalsRecordVo vo = new WithdrawalsRecordVo();
            String date = Utils.timeStampToDate(Long.valueOf(rowArray[4].toString()));
            vo.setUserStrId(rowArray[0].toString());
            vo.setAmountOfMoney(Double.valueOf(rowArray[1].toString()));
            vo.setDate(date);
            String status = rowArray[2].toString();
            if ("1".equals(status)) {
                vo.setCashOutStatus("审核中");
            }
            if ("2".equals(status)) {
                vo.setCashOutStatus("已完成");
            }
            if ("3".equals(status)) {
                vo.setCashOutStatus("不通过");
            }
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 设置vad、freeze_amount
     * @param map
     */
    @SuppressWarnings("unchecked")
    @Override
    public void updateUser(Map<String, Object> map) {
        LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUser u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and u.strId = '" + map.get("userStrId").toString() + "' ");
        }
        if (null != map.get("mobile")) {
            hql.append(" and u.mobile = '" + map.get("mobile").toString() + "' ");
        }
        if (null != map.get("email")) {
            hql.append(" and u.email = '" + map.get("email").toString() + "' ");
        }
        List<MUser> list = em.createQuery(hql.toString(), MUser.class).getResultList();
        if (list.size() > 0) {
            MUser user = em.find(MUser.class, list.get(0).getId(), lmt);
            if (null != map.get("afterUserVad")) {
                user.setVad(Double.valueOf(map.get("afterUserVad").toString()));
            }
            if (null != map.get("afterFriendVad")) {
                user.setVad(Double.valueOf(map.get("afterFriendVad").toString()));
            }
            if (null != map.get("afterUserDeh")) {
                user.setDeh(Double.valueOf(map.get("afterUserDeh").toString()));
            }
            if (null != map.get("afterFriendDeh")) {
                user.setDeh(Double.valueOf(map.get("afterFriendDeh").toString()));
            }
            if (null != map.get("afterAmount")) {
                user.setVad(Double.valueOf(map.get("afterAmount").toString()));
            }  if (null != map.get("afterAmountDeh")) {
                user.setDeh(Double.valueOf(map.get("afterAmountDeh").toString()));
            }
            if (null != map.get("afterFreezeAmount")) {
                user.setFreezeAmount(Double.valueOf(map.get("afterFreezeAmount").toString()));
            }
            user.setModifyTime(new Long(Utils.getTimestamp()).intValue());
            em.merge(user);
        }
    }

    /**
     * /获取用户信息
     * @param map
     * @return
     */
    @Override
    public List<MUser> selectUserList(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUser u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and u.strId = '" + map.get("userStrId").toString() + "' ");
        }
        if (null != map.get("mobile")) {
            hql.append(" and u.mobile = '" + map.get("mobile").toString() + "' ");
        }
        if (null != map.get("email")) {
            hql.append(" and u.email = '" + map.get("email").toString() + "' ");
        }
        return em.createQuery(hql.toString(), MUser.class).getResultList();
    }


    /**
     * 获取当前用户下好友邀请人数的集合
     * @param userId
     * @return
     */
    private List<Integer> selectTeamNumber(String userId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" str_id,");
        sql.append(" user_level_code,");
        sql.append(" id");
        sql.append(" from m_user");
        sql.append(" where status = 1 ");
        List<Integer> listCount = new ArrayList<Integer>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            String userLevelCode = rowArray[1].toString();
            String[] userLevelCodes = userLevelCode.split("-");
            List<String> userLevelCodeList = Arrays.asList(userLevelCodes);
            if (userLevelCodeList.contains(userId)){
                Integer count = selectUserFriendCount(rowArray[0].toString());
                listCount.add(count);
            }
        }
        return listCount;
    }

    /**
     * 查询用户Id
     * @param map
     * @return
     */
    private String selectUserId(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" select id from  MUser m ");
        hql.append(" where m.status = 1 ");
        hql.append(" and m.strId = '" + map.get("userStrId") + "'");
        return em.createQuery(hql.toString()).getSingleResult().toString();
    }

    /**
     * 获取用户的好友的邀请下级人数
     * @param friendUserStrId
     * @return
     */
    private int selectUserFriendCount(String friendUserStrId) {
        StringBuffer hql = new StringBuffer();
        hql.append(" select count(*) from  MUserRecommend r ");
        hql.append(" where r.status = 1 ");
        hql.append(" and r.recommendUserStrId = '" + friendUserStrId + "'");
        return Integer.parseInt(em.createQuery(hql.toString()).getSingleResult().toString());
    }

	@Override
	public List<MUserAssetsSynthesisOrder> selectUserAssetsSynthesisOrderList(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUserAssetsSynthesisOrder o ");
        hql.append(" where o.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and o.userStrId = '" + map.get("userStrId").toString() + "' ");
        }
        if (null != map.get("synthesisStatus")) {
            hql.append(" and o.synthesisStatus = " + map.get("synthesisStatus").toString() + " ");
        }
        return em.createQuery(hql.toString(), MUserAssetsSynthesisOrder.class).getResultList();
	}

	@Override
	public void insertUserAssetsSynthesisOrder(MUserAssetsSynthesisOrder order) {
		em.persist(order);
	}

	@Override
	public int selectUserAssetsSynthesisOrderCount(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from MUserAssetsSynthesisOrder o ");
        hql.append(" where o.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and o.userStrId = '" + map.get("userStrId").toString() + "' ");
        }
        if (null != map.get("synthesisStatus")) {
            hql.append(" and o.synthesisStatus = " + map.get("synthesisStatus").toString() + " ");
        }
        return Integer.parseInt(em.createQuery(hql.toString()).getSingleResult().toString());
	}

    /**
     * 充值记录
     * @param map
     * @return
     */
    @Override
    public List<CapitalDetailsVo> rechargeRecord(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from  m_user_recharge_order ");
        sql.append(" where status = 1 ");
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "' ");
        }
        if (null != map.get("rechargeType")) {
            sql.append(" and recharge_type = " + map.get("rechargeType").toString() + " ");
        }
        sql.append(" order by create_time  DESC ");
        List<CapitalDetailsVo> listVo = new ArrayList<CapitalDetailsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            /*MUserRechargeOrder vo = new MUserRechargeOrder();
            vo.setId(Integer.valueOf(rowArray[0].toString()));
            vo.setStrId(rowArray[1].toString());
            vo.setUserStrId(rowArray[2].toString());
            vo.setUserTokenAddress(rowArray[3].toString());
            vo.setRechargeType(Integer.valueOf(rowArray[4].toString()));
            vo.setRechargeTokenType(Integer.valueOf(rowArray[5].toString()));
            vo.setRechargeTokenOrderId(rowArray[6].toString());
            vo.setRechargeTokenOrderTimestamp(Integer.valueOf(rowArray[7].toString()));
            vo.setRechargeTokenOrderAmount(Double.valueOf(rowArray[8].toString()));
            vo.setRechargeAmount(Double.valueOf(rowArray[9].toString()));
            vo.setRechargeCategory(Integer.valueOf(rowArray[10].toString()));
            vo.setStatus(Integer.valueOf(rowArray[11].toString()));
            vo.setModifyTime(Integer.valueOf(rowArray[12].toString()));
            vo.setCreateTime(Integer.valueOf(rowArray[13].toString()));*/
            CapitalDetailsVo vo = new CapitalDetailsVo();
            String date = Utils.timeStampToDate(Long.valueOf(rowArray[13].toString()));
            vo.setUserStrId(rowArray[2].toString());
            vo.setAmountOfMoney(Double.valueOf(rowArray[9].toString()));
            vo.setDate(date);
            if ("1".equals(rowArray[10].toString())) {
                vo.setRemarks("用户自主充值");
            } else {
                vo.setRemarks("后台代充");
            }
            if ("1".equals(rowArray[11].toString())) {
                vo.setType("完成");
            } else {
                vo.setType("失败");
            }
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 增加一条资金明细信息
     * @param order
     */
    @Override
    public void insertDetailOrder(MUserFundDetailOrder order) {
        em.persist(order);
    }

    /**
     * 各种明细、记录
     * @param map
     * @return
     */
    @Override
    public List<CapitalDetailsVo> variousDetails(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" user_str_id as userStrId, ");
        sql.append(" amount as rechargeAmount, ");
        sql.append(" remarks, ");
        sql.append(" order_status as orderStatus, ");
        sql.append(" modify_time as modifyTime, ");
        sql.append(" create_time as createTime ");
        sql.append(" from ");
        sql.append(" m_user_fund_detail_order ");
        sql.append(" where status = 1 ");
        if (null != map.get("recordType")) {
            sql.append(" and order_category = '" + map.get("recordType").toString() + "'");
        }
        if (null != map.get("userStrId")) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        sql.append(" order by create_time  DESC ");
        List<CapitalDetailsVo> listVo = new ArrayList<CapitalDetailsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            CapitalDetailsVo vo = new CapitalDetailsVo();
            String date = Utils.timeStampToDate(Long.valueOf(rowArray[5].toString()));
            vo.setUserStrId(rowArray[0].toString());
            vo.setAmountOfMoney(Double.valueOf(rowArray[1].toString()));
            vo.setDate(date);
            vo.setRemarks(rowArray[2].toString());
            if ("1".equals(rowArray[3].toString())) {
                vo.setType("完成");
            } else {
                vo.setType("失败");
            }
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 查询转账用户是否在同一推荐线上
     * @param map
     * @return
     */
    @Override
    public Boolean selectUserRecommend(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" str_id,");
        sql.append(" user_level_code,");
        sql.append(" id");
        sql.append(" from m_user");
        sql.append(" where status = 1 ");
        if (null != map.get("userId").toString()&& null != map.get("friendId")){
            if (Integer.parseInt(map.get("userId").toString()) < Integer.parseInt(map.get("friendId").toString())){
                sql.append("and id = " + map.get("friendId").toString());//向下转账
            }else {
                sql.append("and id = " + map.get("userId").toString());//向上转账
            }
        }
        Boolean isItCorrect = true;
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            String userLevelCode = rowArray[1].toString();
            String[] userLevelCodes = userLevelCode.split("-");
            List<String> userLevelCodeList = Arrays.asList(userLevelCodes);//获取的是每个用户user_level_code集合
            if (userLevelCodeList.contains(map.get("userId").toString())
                    && userLevelCodeList.contains(map.get("friendId").toString())){//同一个user_level_code有这个两个
                isItCorrect = true ;
            }else {
                isItCorrect = false;
            }
        }
        return isItCorrect;
    }

    /**
     * 提现信息
     * @param map
     * @return
     */
    @Override
    public List<UserWithdrawalsVo> selectCashInformation(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from m_user ");
        sql.append(" where status = 1 ");
        if (null != map.get("userStrId")) {
            sql.append(" and str_id = '" + map.get("userStrId").toString() + "' ");
        }
        List<UserWithdrawalsVo> listVo = new ArrayList<UserWithdrawalsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserWithdrawalsVo vo = new UserWithdrawalsVo();
            vo.setUserStrId(rowArray[1].toString());
            if ("1".equals(map.get("typesOfWithdrawal"))){
                vo.setAccountVad(Double.valueOf(rowArray[11].toString()));
                vo.setTypesOfWithdrawal(1);
                vo.setLeastAccount(0.00);
                vo.setMostAccount(00.00);
                vo.setMinerCost(0.00);
            }
            if ("2".equals(map.get("typesOfWithdrawal"))){
                vo.setAccountVad(Double.valueOf(rowArray[12].toString()));
                vo.setTypesOfWithdrawal(2);
                vo.setLeastAccount(0.00);
                vo.setMostAccount(0.00);
                vo.setMinerCost(0.00);
            }
            vo.setActualArrival(Double.valueOf("0"));
            listVo.add(vo);
        }
        return listVo;
    }

    @Override
    public void insertUserFundDetailOrder(MUserFundDetailOrder theftOrder) {
        em.persist(theftOrder);
    }

    /**
     * 查询用户定向vad金额
     * @param map
     * @return
     */
    @Override
    public List<MActivity> selectDirectionalVADList(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MActivity a ");
        hql.append(" where a.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and a.userStrId = '" + map.get("userStrId").toString() + "' ");
        }
        if (null != map.get("friendStrId")) {
            hql.append(" and a.userStrId = '" + map.get("friendStrId").toString() + "' ");
        }
        query = em.createQuery(hql.toString());
        return query.getResultList();
    }

    /**
     * 更新用户定向vad
     * @param map
     */
    @Override
    public void updateUserActivity(Map<String, Object> map) {
        LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
        StringBuffer hql = new StringBuffer();
        hql.append(" from MActivity m ");
        hql.append(" where m.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and m.userStrId = '" + map.get("userStrId").toString() + "' ");
        } if (null != map.get("friendStrId")) {
            hql.append(" and m.userStrId = '" + map.get("friendStrId").toString() + "' ");
        }
        List<MActivity> list = em.createQuery(hql.toString(), MActivity.class).getResultList();
        if (list.size() > 0) {
            MActivity user = em.find(MActivity.class, list.get(0).getId(), lmt);
            if (null != map.get("afterUserVad")) {
                user.setAmount(Double.valueOf(map.get("afterUserVad").toString()));
            }
            if (null != map.get("afterFriendVad")) {
                user.setAmount(Double.valueOf(map.get("afterFriendVad").toString()));
            }
            user.setModifyTime(new Long(Utils.getTimestamp()).intValue());
            em.merge(user);
        }
    }

}
