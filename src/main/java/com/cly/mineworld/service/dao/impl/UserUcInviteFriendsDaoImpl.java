package com.cly.mineworld.service.dao.impl;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcInviteFriendsDao;
import com.cly.mineworld.service.vo.userFriend.UserFriendVo;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class UserUcInviteFriendsDaoImpl implements UserUcInviteFriendsDao {

    @PersistenceContext
    private EntityManager em;
    private Query query = null;


    /**
     * 查询邀请人数、收益
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserFriendVo> findUserFriend(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select *  from ");
        sql.append(" ( select ");
        sql.append(" recommend_user_str_id as userStrId, ");
        sql.append(" count(1) as inviteTotalNumber ");
        sql.append(" from m_user_recommend ");
        sql.append(" where ");
        sql.append(" status = 1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and recommend_user_str_id = '" + map.get("userStrId").toString() + "' ) ur");
        }
        sql.append(" join ");
        sql.append(" ( select ");
        sql.append("  sum(profit_amount)  as totalProfit ");
        sql.append(" from m_user_profit ");
        sql.append(" where ");
        sql.append(" status = 1 ");
        sql.append(" and profit_category='2'");
        if (map.get("userStrId") != null) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "' ) up");
        }
        List<UserFriendVo> listVo = new ArrayList<UserFriendVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserFriendVo vo = new UserFriendVo();
            // 当表中没有数据时应该加入判断返回默认值
            if (!"null".equals(rowArray[0] + "")) {
                vo.setUserStrId(rowArray[0].toString());
                vo.setInviteTotalNumber(Long.valueOf(rowArray[1].toString()));
                if ("null".equals(rowArray[2] + "")) {
                    vo.setTotalProfit(Double.valueOf("0.00"));
                } else {
                    vo.setTotalProfit(Utils.formatDoubleForDouble(Double.valueOf(rowArray[2].toString())));
                }
            } else {
                vo.setUserStrId(map.get("userStrId").toString());
                vo.setInviteTotalNumber(Long.valueOf("0"));
                vo.setTotalProfit(Double.valueOf("0.00"));
            }
            listVo.add(vo);
        }
        return listVo;
    }
}
