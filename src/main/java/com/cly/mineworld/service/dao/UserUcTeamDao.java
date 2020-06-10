package com.cly.mineworld.service.dao;

import com.cly.mineworld.service.entity.MUserTeam;
import com.cly.mineworld.service.vo.userInformation.UserInformationVo;
import com.cly.mineworld.service.vo.userRank.UserRankVo;
import com.cly.mineworld.service.vo.userManagerTeam.UserManagerTeamVo;
import com.cly.mineworld.service.vo.userTeams.UserTeamsVo;

import java.util.List;
import java.util.Map;

public interface UserUcTeamDao {


    /**
     * 根据id查询
     * @param map
     * @return
     */
    List<UserInformationVo> findUserById(Map<String, Object> map);

    /**
     * 我的管理团队
     * @param map
     * @return
     */
    List<UserManagerTeamVo> findUserManagerTeam(Map<String, Object> map);

    /**
     * 我的等级
     * @param map
     * @return
     */
    List<UserRankVo> selectMyRank(Map<String, Object> map);

    /**
     * 我的团队
     * @param map
     * @return
     */
    List<UserTeamsVo> findUserTeams(Map<String, Object> map);

    /**
     * 查询我的好友个人信息
     * @param map
     * @return
     */
    List<UserTeamsVo> findUserFriendOne(Map<String, Object> map);

    /**
     * 查询是否有团队
     * @param map
     * @return
     */
    List<MUserTeam> selectMyTeam(Map<String, Object> map);
}
