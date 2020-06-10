package com.cly.mineworld.service.dao;


import com.cly.mineworld.service.vo.userFriend.UserFriendVo;
import java.util.List;
import java.util.Map;

public interface UserUcInviteFriendsDao {


    /**
     * 邀请好友
     * @param map
     * @return
     */
    List<UserFriendVo> findUserFriend(Map<String, Object> map);
}
