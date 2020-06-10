package com.cly.mineworld.service.dao.impl;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcTeamDao;
import com.cly.mineworld.service.entity.MUserAssetsSynthesisOrder;
import com.cly.mineworld.service.entity.MUserTeam;
import com.cly.mineworld.service.vo.userInformation.UserInformationVo;
import com.cly.mineworld.service.vo.userRank.UserRankVo;
import com.cly.mineworld.service.vo.userManagerTeam.UserManagerTeamVo;
import com.cly.mineworld.service.vo.userTeams.UserTeamsVo;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public class UserUcTeamDaoImpl implements UserUcTeamDao {

    @PersistenceContext
    private EntityManager em;
    private Query query = null;


    /**
     * 查询个人信息加团队等级
     * @param map
     * @return
     */
    @Override
    public List<UserInformationVo> findUserById(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select *  from ");
        sql.append(" m_user ");
        sql.append(" where ");
        sql.append(" status = 1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and str_id = '" + map.get("userStrId").toString() + "'");
        }
        Map<String, Object> userTeamLvMap = selectUserTeamLv(map);
        List<UserInformationVo> listVo = new ArrayList<UserInformationVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserInformationVo vo = new UserInformationVo();
            vo.setId(Integer.valueOf(rowArray[0].toString()));
            vo.setStrId(rowArray[1].toString());
            vo.setNickname(rowArray[2].toString());
            vo.setPsw(rowArray[3].toString());
            vo.setMobile(rowArray[4].toString());
            vo.setEmail(rowArray[5].toString());
            vo.setHeadImage(rowArray[6].toString());
            vo.setTransactionPsw(rowArray[7].toString());
            vo.setUserType(rowArray[8].toString());
            vo.setExp(Integer.valueOf(rowArray[9].toString()));
            vo.setLv(Integer.valueOf(rowArray[10].toString()));
            vo.setVad(Double.valueOf(rowArray[11].toString()));
            vo.setDeh(Double.valueOf(rowArray[12].toString()));
            vo.setUserLevelCode(rowArray[13].toString());
            vo.setTokenAddress(rowArray[14].toString());
            vo.setFreezeAmount(Double.valueOf(rowArray[15].toString()));
            vo.setStatus(Integer.valueOf(rowArray[16].toString()));
            vo.setModifyTime(Integer.valueOf(rowArray[17].toString()));
            vo.setCreateTime(Integer.valueOf(rowArray[18].toString()));
            if (null != userTeamLvMap.get("userTeamLv")) {
                String userTeamLv = userTeamLvMap.get("userTeamLv").toString();
                if ("1".equals(userTeamLv)) {
                    vo.setUserTeamLv("青铜");
                }
                if ("2".equals(userTeamLv)) {
                    vo.setUserTeamLv("白银");
                }
                if ("3".equals(userTeamLv)) {
                    vo.setUserTeamLv("黄金");
                }
                if ("4".equals(userTeamLv)) {
                    vo.setUserTeamLv("白金");
                }
                if ("5".equals(userTeamLv)) {
                    vo.setUserTeamLv("钻石");
                }
                if ("6".equals(userTeamLv)) {
                    vo.setUserTeamLv("王者");
                }
            } else {
                vo.setUserTeamLv("普通玩家");
            }
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 查询用户团队等级\团队收益
     * @param map
     * @return
     */
    private Map<String, Object> selectUserTeamLv(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" team_recharge_amount as teamPerformance,");
        sql.append(" team_lv as userTeamLv ");
        sql.append(" from m_user_team ");
        sql.append(" where status = 1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and team_leader_str_id = '" + map.get("userStrId").toString() + "'");
        }
        HashMap<String, Object> userTeamLvMap = new HashMap<String, Object>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            userTeamLvMap.put("teamPerformance", rowArray[0].toString());
            userTeamLvMap.put("userTeamLv", rowArray[1].toString());
        }
        return userTeamLvMap;
    }

    /**
     * 查找我的管理团队信息
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserManagerTeamVo> findUserManagerTeam(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" mt.user_str_id as userStrId, ");
        sql.append(" mt.user_hotel_str_id as userHotelStrId, ");
        sql.append(" mt.hotel_manager_team_str_id as hmtStrId, ");
        sql.append(" mt.work_start_time as startTime, ");
        sql.append(" mt.work_end_time as endTime, ");
        sql.append(" uh.hotel_custom_name as hotelCustomName ");
        sql.append(" from m_user_hotel_manager_team mt ");
        sql.append(" right join m_user_hotel uh ");
        sql.append(" on mt.user_hotel_str_id = uh.str_id ");
        sql.append(" where ");
        //sql.append(" mt.work_status in ( 1, 2 ) ");
       // sql.append(" and uh.status= 1 ");
        sql.append(" uh.status= 1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and mt.user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        sql.append(" and");
        sql.append(" uh.str_id in  ");
        sql.append(" (select user_hotel_str_id from  m_user_hotel_manager_team where ");
        if (map.get("userStrId") != null) {
            sql.append("  user_str_id = '" + map.get("userStrId").toString() + "')");
        }
        List<UserManagerTeamVo> listVo = new ArrayList<UserManagerTeamVo>();
        //查询管理团队的名称详情
        List<UserManagerTeamVo> managerTeams = selectManagerTeamName(map);
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserManagerTeamVo vo = new UserManagerTeamVo();
            String nowTime = Utils.getNowDateStr2(); //yyyy-MM-dd
            String workEndTime = rowArray[4].toString();
            Long surplusManagerTime = Utils.getDaysBetween(nowTime, workEndTime);
            if (surplusManagerTime < 0) {//剩余时间小于0时
                vo.setSurplusManagerTime("0");
            } else {
                vo.setSurplusManagerTime(String.valueOf(surplusManagerTime));
            }
            vo.setUserStrId(rowArray[0].toString());
            vo.setUserHotelStrId(rowArray[1].toString());
            vo.setHotelManagerTeamStrId(rowArray[2].toString());
            vo.setWorkStartTime(rowArray[3].toString());
            vo.setWorkEndTime(workEndTime);
            vo.setHotelCustomName(rowArray[5].toString());
            for (UserManagerTeamVo managerTeam : managerTeams) {
                if (rowArray[1].toString().equals(managerTeam.getUserHotelStrId())){
                    vo.setTeamName(managerTeam.getTeamName());
                    vo.setTeamCategory(managerTeam.getTeamCategory());
                    vo.setUserHotelName(rowArray[5].toString()+managerTeam.getTeamDesc()+"酒店");
                }
            }
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 查询管理团队的名称详情
     * @param map
     * @return
     */
    private List<UserManagerTeamVo> selectManagerTeamName(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" mt.user_hotel_str_id as userHotelStrId, ");
        sql.append(" mh.team_name as teamName, ");
        sql.append(" mh.team_category as teamCategory, ");
        sql.append(" mh.team_desc as teamDesc ");
        sql.append(" from  m_hotel_manager_team mh ");
        sql.append(" right join m_user_hotel_manager_team mt  ");
        sql.append(" on mt.hotel_manager_team_str_id = mh.str_id ");
        sql.append(" where ");
        //sql.append(" mt.work_status = 1 ");
        //sql.append(" and mh.status = 1 ");
        sql.append(" mh.status = 1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and mt.user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        sql.append(" and");
        sql.append(" mh.str_id in ");
        sql.append(" (select hotel_manager_team_str_id from  m_user_hotel_manager_team where ");
        if (map.get("userStrId") != null) {
            sql.append("  user_str_id = '" + map.get("userStrId").toString() + "')");
        }
        List<UserManagerTeamVo> listVo = new ArrayList<UserManagerTeamVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserManagerTeamVo vo = new UserManagerTeamVo();
            vo.setUserHotelStrId(rowArray[0].toString());
            vo.setTeamName(rowArray[1].toString());
            vo.setTeamCategory(rowArray[2].toString());
            vo.setTeamDesc(rowArray[3].toString());
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 我的等级
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserRankVo> selectMyRank(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" ut.team_leader_str_id as userStrId, ");
        sql.append(" ut.team_lv as teamLv, ");
        sql.append(" ut.team_desc as teamDesc, ");
        sql.append(" ut.team_count as teamCount, ");
        sql.append(" ut.team_recharge_amount as teamAmountCount, ");
        sql.append(" count(ur.recommend_user_str_id) as recommendCount ");
        sql.append(" from m_user_team ut ");
        sql.append(" right join m_user_recommend ur ");
        sql.append(" on ut.team_leader_str_id = ur.recommend_user_str_id ");
        sql.append(" where ");
        sql.append(" ut.status = 1 ");
        sql.append(" and ur.status = 1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and ut.team_leader_str_id  = '" + map.get("userStrId").toString() + "'");
        }
        List<UserRankVo> listVo = new ArrayList<UserRankVo>();
        String userId = selectUserId(map); //获取自身的Id
        List<Map<String, Object>> friendTeamLvList = selectTeamLv(userId);//获取下面的团队分类数量（包含自己）
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserRankVo vo = new UserRankVo();
            if (null != rowArray[0]) {
                vo.setUserStrId(rowArray[0].toString());
                vo.setTeamLv(Integer.valueOf(rowArray[1].toString()));
                vo.setTeamCount(Long.valueOf(rowArray[3].toString()));
                vo.setTeamAmountCount(Double.valueOf(rowArray[4].toString()));
                for (Map<String, Object> friendTeamLvMap : friendTeamLvList) {
                    //预留不包含自己的判断
                    if ("1".equals(rowArray[1].toString())) {//青铜人数
                        vo.setBronzeCount(Long.valueOf(friendTeamLvMap.get("bronzeCount").toString()));
                    }else {
                        vo.setBronzeCount(Long.valueOf(friendTeamLvMap.get("bronzeCount").toString()));
                    }
                    if ("2".equals(rowArray[1].toString())) {//白银人数
                        vo.setSilverCount(Long.valueOf(friendTeamLvMap.get("silverCount").toString()));
                    }else {
                        vo.setSilverCount(Long.valueOf(friendTeamLvMap.get("silverCount").toString()) );
                    }
                    if ("3".equals(rowArray[1].toString())) {//黄金人数
                        vo.setGoldCount(Long.valueOf(friendTeamLvMap.get("goldCount").toString()) );
                    }else {
                        vo.setGoldCount(Long.valueOf(friendTeamLvMap.get("goldCount").toString()) );
                    }
                    if ("4".equals(rowArray[1].toString())) {//白金人数
                        vo.setPlatinumCount(Long.valueOf(friendTeamLvMap.get("platinumCount").toString()));
                    }else {
                        vo.setPlatinumCount(Long.valueOf(friendTeamLvMap.get("platinumCount").toString()));
                    }
                    if ("5".equals(rowArray[1].toString())) {//钻石人数
                        vo.setDiamondsCount(Long.valueOf(friendTeamLvMap.get("diamondsCount").toString()));
                    }else {
                        vo.setDiamondsCount(Long.valueOf(friendTeamLvMap.get("diamondsCount").toString()));
                    }
                    if ("6".equals(rowArray[1].toString())) {//王者人数
                        vo.setTheKingCount(Long.valueOf(friendTeamLvMap.get("theKingCount").toString()));
                    }else {
                        vo.setTheKingCount(Long.valueOf(friendTeamLvMap.get("theKingCount").toString()));
                    }
                }
            } else {//用户没有团队
                vo.setUserStrId(map.get("userStrId").toString());
                vo.setTeamLv(0);
                vo.setTeamCount(Long.valueOf(0));
                //获取到的好友团队统计是多少就展示多少
                for (Map<String, Object> friendTeamLvMap : friendTeamLvList) {
                    //青铜人数
                    vo.setBronzeCount(Long.valueOf(friendTeamLvMap.get("bronzeCount").toString()));
                    //白银人数
                    vo.setSilverCount(Long.valueOf(friendTeamLvMap.get("silverCount").toString()));
                    //黄金人数
                    vo.setGoldCount(Long.valueOf(friendTeamLvMap.get("goldCount").toString()));
                    //白金人数
                    vo.setPlatinumCount(Long.valueOf(friendTeamLvMap.get("platinumCount").toString()));
                    //钻石人数
                    vo.setDiamondsCount(Long.valueOf(friendTeamLvMap.get("diamondsCount").toString()));
                    //王者人数
                    vo.setTheKingCount(Long.valueOf(friendTeamLvMap.get("theKingCount").toString()));
                }
            }
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 获取当前用户下好友邀请的用户的团队等级
     *
     * @param userId
     * @return
     */
    private List<Map<String, Object>> selectTeamLv(String userId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" str_id,");
        sql.append(" user_level_code,");
        sql.append(" id");
        sql.append(" from m_user");
        sql.append(" where status = 1 ");
        List<Map<String, Object>> friendTeamLvList = new ArrayList<Map<String, Object>>();
        Integer bronzeCount = 0;//统计青铜人数
        Integer silverCount = 0;//统计白银人数
        Integer goldCount = 0;//统计黄金人数
        Integer platinumCount = 0;//统计白金人数
        Integer diamondsCount = 0;//统计钻石人数
        Integer theKingCount = 0;//统计王者人数
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            String userLevelCode = rowArray[1].toString();
            String[] userLevelCodes = userLevelCode.split("-");
            List<String> userLevelCodeList = Arrays.asList(userLevelCodes);
            if (userLevelCodeList.contains(userId)) {//此用户下所有的下级也包含自己
                //获取好友下级的团队的等级
                String friendTeamLv = selectUserFriendTeamLv(rowArray[0].toString());
                if ("1".equals(friendTeamLv)) {//青铜人数
                    bronzeCount += 1;
                }
                if ("2".equals(friendTeamLv)) {//白银人数
                    silverCount += 1;
                }
                if ("3".equals(friendTeamLv)) {//黄金人数
                    goldCount += 1;
                }
                if ("4".equals(friendTeamLv)) {//白金人数
                    platinumCount += 1;
                }
                if ("5".equals(friendTeamLv)) {//钻石人数
                    diamondsCount += 1;
                }
                if ("6".equals(friendTeamLv)) {//王者人数
                    theKingCount += 1;
                }
            }
        }
        Map<String, Object> friendTeamLvMap = new HashMap<>();
        friendTeamLvMap.put("bronzeCount", bronzeCount);
        friendTeamLvMap.put("silverCount", silverCount);
        friendTeamLvMap.put("goldCount", goldCount);
        friendTeamLvMap.put("platinumCount", platinumCount);
        friendTeamLvMap.put("diamondsCount", diamondsCount);
        friendTeamLvMap.put("theKingCount", theKingCount);
        friendTeamLvList.add(friendTeamLvMap);
        return friendTeamLvList;
    }

    /**
     * 获取用户的好友的邀请下级的团队等级
     * @param friendUserStrId
     * @return
     */
    @SuppressWarnings("unchecked")
    private String selectUserFriendTeamLv(String friendUserStrId) {
        StringBuffer hql = new StringBuffer();
        hql.append(" select t.teamLv from  MUserTeam t ");
        hql.append(" where t.status = 1 ");
        hql.append(" and t.teamLeaderStrId = '" + friendUserStrId + "'");
        List list = em.createQuery(hql.toString()).getResultList();
        if (list.size()>0){
            return list.get(0).toString();
        }else {
            return "0";
        }
    }

    /**
     * 我的团队
     * @param map
     * @return
     */
    @Override
    public List<UserTeamsVo> findUserTeams(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" recommend_user_str_id as userStrId, ");
        sql.append("  count(*) as friendTotal ");
        sql.append(" from ");
        sql.append("  m_user_recommend ");
        sql.append(" where status = 1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and recommend_user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        //获取用户信息 //获取团队信息  结果拼接起来
        List<Map<String, Object>> userTeamList = selectUserTeam(map);
        String userId = selectUserId(map); //获取用户id
        //获取当前用户下好友邀请人数的集合
        List<Integer> listCount = selectTeamNumber(userId);
        List<UserTeamsVo> listVo = new ArrayList<UserTeamsVo>();
        List<UserTeamsVo> listVo1 = new ArrayList<UserTeamsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserTeamsVo vo1 = new UserTeamsVo();
            if (!"0".equals(rowArray[1].toString())) {//好友数量不为零时
                vo1.setUserStrId(rowArray[0].toString());
                vo1.setFriendTotal(Integer.valueOf(rowArray[1].toString()));
                Integer count = 1;
                for (Integer number : listCount) {
                    count += number;
                }
                vo1.setTeamTotal(count);
                listVo1.add(vo1);
            } else {//好友数量为零时
                vo1.setUserStrId(map.get("userStrId").toString());
                vo1.setFriendTotal(Integer.valueOf(rowArray[1].toString()));
                vo1.setTeamTotal(Integer.valueOf(rowArray[1].toString()));
                listVo1.add(vo1);
                return listVo1;
            }
        }
        UserTeamsVo userTeamsVo = listVo1.get(0);
        for (Map<String, Object> userTeamMap : userTeamList) {
            UserTeamsVo vo = new UserTeamsVo();
            vo.setUserStrId(userTeamsVo.getUserStrId());
            vo.setFriendTotal(userTeamsVo.getFriendTotal());
            vo.setTeamTotal(userTeamsVo.getTeamTotal());
            int hisFriend = selectUserFriendCount(userTeamMap.get("userStrId").toString());//好友邀请的人数
            vo.setHisFriend(hisFriend);
            vo.setFriendId(Long.valueOf(userTeamMap.get("friendId").toString()));
            vo.setNickName(userTeamMap.get("nickName").toString());
            vo.setMobile(userTeamMap.get("mobile").toString());
            vo.setEmail(userTeamMap.get("email").toString());
            vo.setTeamPerformance(Double.valueOf(userTeamMap.get("teamPerformance").toString()));
            vo.setRegistrationTime(Utils.timeStampToDate(Long.valueOf(userTeamMap.get("registrationTime").toString())));
            vo.setMembershipLevel(userTeamMap.get("membershipLevel").toString());
            listVo.add(vo);
        }
        return listVo;
    }

    /**
     * 获取当前用户下好友邀请人数的集合
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
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
            if (userLevelCodeList.contains(userId)){//包含
                Integer count = selectUserFriendCount(rowArray[0].toString());//说明是这个好友家族的人
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    private int selectUserFriendCount(String friendUserStrId) {
        StringBuffer hql = new StringBuffer();
        hql.append(" select count(*) from  MUserRecommend r ");
        hql.append(" where r.status = 1 ");
        hql.append(" and r.recommendUserStrId = '" + friendUserStrId + "'");
        return Integer.parseInt(em.createQuery(hql.toString()).getSingleResult().toString());
    }

    /**
     * 获取用户团队信息
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> selectUserTeam(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" id as friendId, ");
        sql.append(" str_id as userStrId, ");
        sql.append(" nickname as nickName, ");
        sql.append(" mobile, email, ");
        sql.append(" create_time as registrationTime ");
        sql.append(" from m_user ");
        sql.append(" where status =1 ");
        sql.append(" and str_id in ");
        sql.append(" ( select recommended_user_str_id as userStrId from m_user_recommend where status = 1");
        if (map.get("userStrId") != null) {
            sql.append(" and recommend_user_str_id = '" + map.get("userStrId").toString() + "')");
        }
        List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            Map<String, Object> userTeamMap = new HashMap<String, Object>();
            userTeamMap.put("friendId", rowArray[0].toString());
            userTeamMap.put("userStrId", rowArray[1].toString());
            userTeamMap.put("nickName", rowArray[2].toString());
            userTeamMap.put("mobile", rowArray[3].toString());
            userTeamMap.put("email", rowArray[4].toString());
            userTeamMap.put("registrationTime", rowArray[5].toString());
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("userStrId", rowArray[1].toString());
            Map<String, Object> teamDataMap = selectUserTeamLv(dataMap);
            if (null != teamDataMap.get("userTeamLv") && null != teamDataMap.get("teamPerformance")) {
                userTeamMap.put("teamPerformance", teamDataMap.get("teamPerformance").toString());
                String membershipLevel = teamDataMap.get("userTeamLv").toString();
                if ("1".equals(membershipLevel)) {
                    userTeamMap.put("membershipLevel", "青铜");
                }
                if ("2".equals(membershipLevel)) {
                    userTeamMap.put("membershipLevel", "白银");
                }
                if ("3".equals(membershipLevel)) {
                    userTeamMap.put("membershipLevel", "黄金");
                }
                if ("4".equals(membershipLevel)) {
                    userTeamMap.put("membershipLevel", "白金");
                }
                if ("5".equals(membershipLevel)) {
                    userTeamMap.put("membershipLevel", "钻石");
                }
                if ("6".equals(membershipLevel)) {
                    userTeamMap.put("membershipLevel", "王者");
                }
            } else {
                userTeamMap.put("membershipLevel", "普通玩家");
                userTeamMap.put("teamPerformance", 0.00);
            }
            listVo.add(userTeamMap);
        }
        return listVo;
    }

    /**
     * 查询好友信息
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserTeamsVo> findUserFriendOne(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select *  from ");
        sql.append(" ( select ");
        sql.append(" id as friendId, ");
        sql.append(" str_id as friendStrId, ");
        sql.append(" nickname as nickName, ");
        sql.append(" mobile, email, ");
        sql.append(" create_time as registrationTime ");
        sql.append(" from ");
        sql.append(" m_user ");
        sql.append(" where status = 1 ");
        if (map.get("friendMobile") != null) {
            sql.append(" and mobile = '" + map.get("friendMobile").toString() + "') aa ");
        }
        if (map.get("friendEmail") != null) {
            sql.append(" and email = '" + map.get("friendEmail").toString() + "') aa ");
        }
        sql.append(" join ");
        sql.append(" ( select ");
        sql.append(" count(*) as hisFriend ");
        sql.append(" from ");
        sql.append(" m_user_recommend ");
        sql.append(" where status = 1 ");
        sql.append(" and recommend_user_str_id in ");
        sql.append(" (select  str_id as userStrId  from m_user where status = 1  ");
        if (map.get("friendMobile") != null) {
            sql.append(" and mobile = '" + map.get("friendMobile").toString() + "')) cc ");
        }
        if (map.get("friendEmail") != null) {
            sql.append(" and email = '" + map.get("friendEmail").toString() + "')) cc ");
        }
        //只能查询团队中的好友信息,需要点击请求的时候再传入团长的str_id
        List<String> friendStrIdList = selectUserFriendStrId(map);
        List<UserTeamsVo> listVo = new ArrayList<UserTeamsVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserTeamsVo vo = new UserTeamsVo();
            // 好友账号在此人团队中
            if (friendStrIdList.contains(rowArray[1].toString())) {
                vo.setFriendId(Long.valueOf(rowArray[0].toString()));
                vo.setNickName(rowArray[2].toString());
                vo.setMobile(rowArray[3].toString());
                vo.setEmail(rowArray[4].toString());
                vo.setRegistrationTime(Utils.timeStampToDate(Long.valueOf(rowArray[5].toString())));
                vo.setHisFriend(Integer.valueOf(rowArray[6].toString()));
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("userStrId", rowArray[1].toString());
                Map<String, Object> teamDataMap = selectUserTeamLv(dataMap);
                if (null != teamDataMap.get("userTeamLv") && null != teamDataMap.get("teamPerformance")) {
                    vo.setTeamPerformance(Double.valueOf(teamDataMap.get("teamPerformance").toString()));
                    String membershipLevel = teamDataMap.get("userTeamLv").toString();
                    if ("1".equals(membershipLevel)) {
                        vo.setMembershipLevel("青铜");
                    }
                    if ("2".equals(membershipLevel)) {
                        vo.setMembershipLevel("白银");
                    }
                    if ("3".equals(membershipLevel)) {
                        vo.setMembershipLevel("黄金");
                    }
                    if ("4".equals(membershipLevel)) {
                        vo.setMembershipLevel("白金");
                    }
                    if ("5".equals(membershipLevel)) {
                        vo.setMembershipLevel("钻石");
                    }
                    if ("6".equals(membershipLevel)) {
                        vo.setMembershipLevel("王者");
                    }
                } else {
                    vo.setMembershipLevel("普通玩家");
                    vo.setTeamPerformance(0.00);
                }
            } else {//此人不是您的好友
                vo.setNickName("0");
            }
            listVo.add(vo);
        }
        return listVo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MUserTeam> selectMyTeam(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUserTeam t ");
        hql.append(" where t.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and t.teamLeaderStrId = '" + map.get("userStrId").toString() + "' ");
        }
        return em.createQuery(hql.toString(), MUserTeam.class).getResultList();
    }

    /**
     * 获取用户团队下的好友strId
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<String> selectUserFriendStrId(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" id ,");
        sql.append(" recommended_user_str_id as userStrId ");
        sql.append(" from m_user_recommend ");
        sql.append(" where status =1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and recommend_user_str_id = '" + map.get("userStrId").toString() + "'");
        }
        List<String> listVo = new ArrayList<String>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            listVo.add(rowArray[1].toString());
        }
        return listVo;
    }
}
