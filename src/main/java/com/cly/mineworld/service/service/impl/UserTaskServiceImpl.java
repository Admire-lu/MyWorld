package com.cly.mineworld.service.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import com.cly.mineworld.service.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserTaskDao;
import com.cly.mineworld.service.service.UserTaskService;
import com.cly.mineworld.service.vo.userTask.TaskHotelVo;

@Service
@Transactional
public class UserTaskServiceImpl implements UserTaskService {

	@Autowired
	private UserTaskDao userTaskDao;
	
	@Override
	public void userProfitCalculateForDay() {
	    String nowDate = Utils.getNowDateStr2();
		/*校验今天是否已经完成收益计算*/
		Map<String,Object> queryUserProfitTaskHistoryMap = new HashMap<String,Object>();
		queryUserProfitTaskHistoryMap.put("taskDate", nowDate);
		List<MUserProfitTaskHistory> listHistory = userTaskDao.selectUserProfitTaskHistoryList(queryUserProfitTaskHistoryMap);
		if(listHistory.size() < 1) {
			/*创建今天的任务历史*/
			MUserProfitTaskHistory history = new MUserProfitTaskHistory();
			history.setCreateTime(new Long(Utils.getTimestamp()).intValue());
			history.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			history.setRunStatus(1);
			history.setStatus(1);
			history.setStrId(Utils.getUUID());
			history.setTaskDate(nowDate);
			history.setTaskStartTime(Utils.getNowTimeForSeconds());
			/*每天检测用户酒店的管理团队是否到期*/
			checkUserHotelManagerTeamCycle();
			/*每天计算用户团队收益*/
			userTeamProfitCalculateForDay();
			profit();//计算收益
			history.setTaskEndTime(Utils.getNowTimeForSeconds());
			userTaskDao.insertUserProfitTaskHistory(history);
		}
	}
	
	private void checkUserHotelManagerTeamCycle() {
		System.out.println("检查酒店管理团队周期...");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nowDay", Utils.getNowDateStr2());
		/*修改超时的用户酒店工作状态*/
		userTaskDao.updateTimeOverUserHotelDoBusinessStatus(map);
		/*修改超时的用户酒店管理团队工作状态*/
		userTaskDao.updateTimeOverUserHotelManagerTeamWorkStatus(map);
	}

	private void userTeamProfitCalculateForDay() {
		System.out.println("计算用户团队收益...");
		/*查询所有团队*/
		List<MUserTeam> listUserTeam = userTaskDao.selectUserTeamList(new HashMap<String,Object>());
		for(MUserTeam ut : listUserTeam) {
			/*判断每个团队的等级*/
			if(ut.getTeamLv() > 1) {//2级以上才有收益
				/*查询团队所有人（除去团队长）*/
				Map<String,Object> mapQueryUserTeamMembers = new HashMap<String,Object>();
				mapQueryUserTeamMembers.put("teamStrId", ut.getStrId());
				mapQueryUserTeamMembers.put("notMemberStrId", ut.getTeamLeaderStrId());//不包括团队长
				List<MUserTeamMember> listUserTeamMember = userTaskDao.selectUserTeamMemberList(mapQueryUserTeamMembers);
				for(MUserTeamMember utm : listUserTeamMember) {
					/*遍历并统计团队每个会员的当天总收益*/
					Long startTimestamp = Utils.stringToTimeStamp(Utils.getNowDateStr2() + " 00:00:00");
					Long endTimestamp = Utils.stringToTimeStamp(Utils.getNowDateStr2() + " 23:59:59");
					Map<String,Object> mapSumProfit = new HashMap<String,Object>();
					mapSumProfit.put("userStrId", utm.getMemberStrId());
					mapSumProfit.put("startTime", startTimestamp / 1000);
					mapSumProfit.put("endTime", endTimestamp / 1000);
					double totalProfit = userTaskDao.sumUserTotalProfitForCurrentDay(mapSumProfit);
					/*增加团队长收益*/
					/*增加用户收益记录*/
					/*2级=3% ，3级=5%，4级=10%，5级=15%，6级=20%*/
					double percentage = 0.00;
					if(2 == ut.getTeamLv()) {
						percentage = 0.03;
					}else if(3 == ut.getTeamLv()) {
						percentage = 0.05;
					}else if(4 == ut.getTeamLv()) {
						percentage = 0.10;
					}else if(5 == ut.getTeamLv()) {
						percentage = 0.15;
					}else if(6 == ut.getTeamLv()) {
						percentage = 0.20;
					}
					/*查询团队长*/
					Map<String,Object> mapQueryUser = new HashMap<String,Object>();
					mapQueryUser.put("userStrId", ut.getTeamLeaderStrId());
					List<MUser> listUser = userTaskDao.selectUserList(mapQueryUser);
					MUser user = listUser.get(0);
					double vad = totalProfit * percentage;//收益VAD
					double afterUserVad = user.getVad() + vad;//增加后的用户收益VAD
					/*新增收益记录*/
					MUserProfit userProfit = new MUserProfit();
					userProfit.setAfterAmount(Utils.formatDoubleForDouble(afterUserVad));
					userProfit.setBeforeAmount(user.getVad());
					userProfit.setCreateTime(new Long(Utils.getTimestamp()).intValue());
					userProfit.setModifyTime(new Long(Utils.getTimestamp()).intValue());
					userProfit.setProfitAmount(vad);
					userProfit.setProfitCategory("4");//团队收益
					userProfit.setProfitRemarks("团队收益");
					userProfit.setStatus(1);
					String userProfitStrId = Utils.getUUID();
					userProfit.setStrId(userProfitStrId);
					userProfit.setUserStrId(user.getStrId());
					userTaskDao.insertUserProfit(userProfit);//新增收益
					/*新增资金明细订单记录(奖励明细)-2019-10-14*/
					MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
					detailOrder.setStrId(Utils.getUUID());
					detailOrder.setUserStrId(user.getStrId());
					detailOrder.setOrderCategory(4); //奖励明细
					detailOrder.setRelationOrderStrId(userProfitStrId);//关联收益记录
					detailOrder.setTokenCategory(1);//vad
					detailOrder.setAmount(vad);
					detailOrder.setBeforeAmount(user.getVad());
					detailOrder.setAfterAmount(afterUserVad);
					detailOrder.setRemarks("团队收益奖励");
					detailOrder.setOrderStatus(1);
					detailOrder.setStatus(1);
					detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
					detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
					userTaskDao.insertUserFundDetailOrder(detailOrder); //新增资金明细
					/*修改用户VAD*/
					Map<String,Object> mapModifyUser = new HashMap<String,Object>();
					mapModifyUser.put("userStrId", user.getStrId());
					mapModifyUser.put("vad", Utils.formatDoubleForDouble(afterUserVad));
					userTaskDao.updateUser(mapModifyUser);
				}
			}
		}
	}
	
	/**
	 * 计算收益
	 */
	private void profit() {
	    int pageNum = 1;    //当前页,从请求那边传过来。
	    int pageSize = 1000;    //每页显示的数据条数。
	    int totalRecord = 0;    //总的记录条数。查询数据库得到的数据
	    int totalPage = 0;    //总页数，通过totalRecord和pageSize计算可以得来
		Map<String,Object> mapQueryHotelVo = new HashMap<String,Object>();
		mapQueryHotelVo.put("lessWorkEndTime", Utils.getNowDateStr2());
		mapQueryHotelVo.put("workStatus", 1);
		/*分页查询*/
		totalRecord = userTaskDao.selectHotelVoCount(mapQueryHotelVo);//总数
        if(totalRecord%pageSize == 0){
            totalPage = totalRecord / pageSize;
        }else{
            totalPage = totalRecord / pageSize +1;
        }
        //System.out.println("totalPage:" + totalPage);
        for(int i=0;i<totalPage;i++) {
        	int startIndex = (pageNum - 1) * pageSize;//每页的开始数
        	//System.out.println("start:" + startIndex + ",limit:" + pageSize);
        	mapQueryHotelVo.put("start", startIndex);
        	mapQueryHotelVo.put("limit", pageSize);
    		List<TaskHotelVo> listHotelVo = userTaskDao.selectHotelVoList(mapQueryHotelVo);//查询在有团队工作状态的酒店信息
    		for(TaskHotelVo thv : listHotelVo) {
    			/*查询用户信息*/
    			Map<String,Object> mapQueryUser = new HashMap<String,Object>();
    			mapQueryUser.put("userStrId", thv.getUserStrId());
    			List<MUser> listUser = userTaskDao.selectUserList(mapQueryUser);
    			if(listUser.size() > 0) {
    	   			MUser user = listUser.get(0);
        			if(listUser.size() > 0) {
        				/*计算静态收益*/
        				staticProfit(thv,user);
        				/*计算直推收益*/
        				firstLevelProfit(thv,user);
        				/*计算非直推收入*/
        				levelProfit(thv,user);
        			}
    			}
    		}
        	pageNum ++;
        }
	}
	
	/**
	 * 计算静态收益
	 */
	private void staticProfit(TaskHotelVo thv,MUser user) {
		System.out.println("开始计算酒店静态收益...");
		/*计算静态收益*/
		//double vad = thv.getHotelDayProfit() - thv.getHotelDayPay();//收益VAD
		double vad = thv.getHotelDayProfit();//收益VAD
		double afterUserVad = user.getVad() + vad;//增加后的用户收益VAD
		/*新增收益记录*/
		MUserProfit userProfit = new MUserProfit();
		userProfit.setAfterAmount(afterUserVad);
		userProfit.setBeforeAmount(user.getVad());
		userProfit.setCreateTime(new Long(Utils.getTimestamp()).intValue());
		userProfit.setModifyTime(new Long(Utils.getTimestamp()).intValue());
		userProfit.setProfitAmount(vad);
		userProfit.setProfitCategory("1");//酒店收益
		userProfit.setProfitRemarks("酒店每天收益");
		userProfit.setStatus(1);
		userProfit.setStrId(Utils.getUUID());
		userProfit.setUserStrId(thv.getUserStrId());
		userTaskDao.insertUserProfit(userProfit);//新增收益
		/*新增用户酒店每天收益历史订单*/
		MUserHotelDayProfitOrder order = new MUserHotelDayProfitOrder();
		order.setCreateTime(new Long(Utils.getTimestamp()).intValue());
		order.setModifyTime(new Long(Utils.getTimestamp()).intValue());
		order.setProfitDate(Utils.getNowDateStr2());
		order.setStatus(1);
		String hotelDayProfitOrderStrId = Utils.getUUID();
		order.setStrId(hotelDayProfitOrderStrId);
		order.setUserHotelDeductProfit(0.0);
		order.setUserHotelFinalProfit(vad);
		order.setUserHotelId(thv.getUserHotelId());
		order.setUserHotelProfit(vad);
		userTaskDao.insertUserHotelDayProfitOrder(order);
		/*新增资金明细订单记录(奖励明细)-2019-10-14*/
		MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
		detailOrder.setStrId(Utils.getUUID());
		detailOrder.setUserStrId(user.getStrId());
		detailOrder.setOrderCategory(4); //奖励明细
		detailOrder.setRelationOrderStrId(hotelDayProfitOrderStrId);//关联酒店每天新增收益
		detailOrder.setTokenCategory(1);//vad
		detailOrder.setAmount(vad);
		detailOrder.setBeforeAmount(user.getVad());
		detailOrder.setAfterAmount(afterUserVad);
		detailOrder.setRemarks("酒店每天收益");
		detailOrder.setOrderStatus(1);
		detailOrder.setStatus(1);
		detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
		detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
		userTaskDao.insertUserFundDetailOrder(detailOrder); //新增资金明细
		/*查询用户酒店*/
		MUserHotel userHotel = userTaskDao.selectUserHotelByUserHotelId(thv.getUserHotelId());
		/*修改用户酒店总收益*/
		userHotel.setProfit(userHotel.getProfit() + vad);
		userHotel.setDoBusinessDays(userHotel.getDoBusinessDays() + 1);//总营业天数
		userTaskDao.updateUserHotel(userHotel);
		/*修改用户VAD*/
		Map<String,Object> mapModifyUser = new HashMap<String,Object>();
		mapModifyUser.put("userStrId", user.getStrId());
		mapModifyUser.put("vad", Utils.formatDoubleForDouble(afterUserVad));
		userTaskDao.updateUser(mapModifyUser);
	}
	
	/**
	 * 计算直推收益
	 */
	private void firstLevelProfit(TaskHotelVo thv,MUser user) {
		System.out.println("开始计算直推收益");
		double vad = thv.getHotelDayProfit() - thv.getHotelDayPay();//收益VAD
		int count = user.getUserLevelCode().indexOf("-");//检测是否有上级
		if(count > 0) {//有上级
			String userLevelCodesStr = user.getUserLevelCode().substring(0, user.getUserLevelCode().lastIndexOf("-"));//去掉自己的str_id
			String[] userLevelCodes = userLevelCodesStr.split("-");//所有上级的用户层级编码	
			/*直推层*/
			/*直推奖励收益100%*/
			/*查询用户信息*/
			int userId = Integer.parseInt(userLevelCodes[userLevelCodes.length - 1]);
			List<MUser> listUser2 = userTaskDao.selectUserForHotelDoBusinessByUserId(userId);//查询有酒店在工作的用户
			if(listUser2.size() > 0) {
				MUser user2 = listUser2.get(0);
				/*新增收益记录*/
				MUserProfit userProfit2 = new MUserProfit();
				userProfit2.setAfterAmount(user2.getVad() + vad);
				userProfit2.setBeforeAmount(user2.getVad());
				userProfit2.setCreateTime(new Long(Utils.getTimestamp()).intValue());
				userProfit2.setModifyTime(new Long(Utils.getTimestamp()).intValue());
				userProfit2.setProfitAmount(vad);
				userProfit2.setProfitCategory("2");//动态态收益
				userProfit2.setProfitRemarks("直推收益");
				userProfit2.setStatus(1);
				String userProfit2StrId  = Utils.getUUID();
				userProfit2.setStrId(userProfit2StrId);
				userProfit2.setUserStrId(user2.getStrId());
				userTaskDao.insertUserProfit(userProfit2);//新增收益
				/*新增资金明细订单记录(奖励明细)-2019-10-14*/
				MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
				detailOrder.setStrId(Utils.getUUID());
				detailOrder.setUserStrId(user2.getStrId());
				detailOrder.setOrderCategory(4); //奖励明细
				detailOrder.setRelationOrderStrId(userProfit2StrId);//关联直推收益
				detailOrder.setTokenCategory(1);//vad
				detailOrder.setAmount(vad);
				detailOrder.setBeforeAmount(user2.getVad());
				detailOrder.setAfterAmount(user2.getVad() + vad);
				detailOrder.setRemarks("直推收益");
				detailOrder.setOrderStatus(1);
				detailOrder.setStatus(1);
				detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
				detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
				userTaskDao.insertUserFundDetailOrder(detailOrder); //新增资金明细
				/*修改用户VAD*/
				Map<String,Object> mapModifyUser2 = new HashMap<String,Object>();
				mapModifyUser2.put("userStrId", user2.getStrId());
				mapModifyUser2.put("vad", Utils.formatDoubleForDouble(user2.getVad() + vad));
				userTaskDao.updateUser(mapModifyUser2);
			}
		}
	}
	
	/**
	 * 非直推收益
	 */
	private void levelProfit(TaskHotelVo thv, MUser user) {
		System.out.println("开始计算非直推收益...");
		double vad = thv.getHotelDayProfit() - thv.getHotelDayPay();//收益VAD
		String[] userIds = user.getUserLevelCode().split("-");
		if (userIds.length > 2) {//有2层以上才开始遍历
			//遍历所有上线
			for (int i = userIds.length - 3; i >= 0; i--) {//往上第二层开始
				Map<String, Object> mapQueryUser3 = new HashMap<String, Object>();
				mapQueryUser3.put("id", userIds[i]);
				MUser user_2 = userTaskDao.selectUser(mapQueryUser3);//推荐人
				/**查询直推多少人*/
				Map<String, Object> queryRecommendCount = new HashMap<String, Object>();
				queryRecommendCount.put("recommendUserStrId", user_2.getStrId());
				int recommendCount = userTaskDao.selectRecommendCount(queryRecommendCount);//有多少推荐人
				if (recommendCount >= 20) {
					Map<String, Object> mapQueryUser = new HashMap<String, Object>();
					mapQueryUser.put("userId", userIds[i]);
					List<MUserHotel> listUserHotel = userTaskDao.selectUserHotelJoinUser(mapQueryUser);
					if (listUserHotel.size() > 0 && listUserHotel.get(0).getDoBusinessStatus() < 2) {
						Map<String, Object> mapQueryUser_2 = new HashMap<String, Object>();
						mapQueryUser_2.put("id", userIds[i]);
						MUser u = userTaskDao.selectUser(mapQueryUser_2);
						/*新增收益记录*/
						MUserProfit userProfit = new MUserProfit();
						userProfit.setAfterAmount(u.getVad() + vad * 0.1);
						userProfit.setBeforeAmount(u.getVad());
						userProfit.setCreateTime(new Long(Utils.getTimestamp()).intValue());
						userProfit.setModifyTime(new Long(Utils.getTimestamp()).intValue());
						userProfit.setProfitAmount(vad * 0.1);
						userProfit.setProfitCategory("3");//非直推收益
						userProfit.setProfitRemarks("非直推收益");
						userProfit.setStatus(1);
						String userProfitStrId = Utils.getUUID();
						userProfit.setStrId(userProfitStrId);
						userProfit.setUserStrId(u.getStrId());
						userTaskDao.insertUserProfit(userProfit);//新增收益
						/*新增资金明细订单记录(奖励明细)-2019-10-14*/
						MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
						detailOrder.setStrId(Utils.getUUID());
						detailOrder.setUserStrId(u.getStrId());
						detailOrder.setOrderCategory(4); //奖励明细
						detailOrder.setRelationOrderStrId(userProfitStrId);//关联非直推收益
						detailOrder.setTokenCategory(1);//vad
						detailOrder.setAmount(vad);
						detailOrder.setBeforeAmount(u.getVad());
						detailOrder.setAfterAmount(u.getVad() + vad * 0.1);
						detailOrder.setRemarks("非直推收益");
						detailOrder.setOrderStatus(1);
						detailOrder.setStatus(1);
						detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
						detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
						userTaskDao.insertUserFundDetailOrder(detailOrder); //新增资金明细
						/*修改用户VAD*/
						Map<String, Object> mapModifyUser = new HashMap<String, Object>();
						mapModifyUser.put("userStrId", u.getStrId());
						mapModifyUser.put("vad", Utils.formatDoubleForDouble(u.getVad() + vad * 0.1));
						userTaskDao.updateUser(mapModifyUser);
					}
				}else if (recommendCount >= userIds.length - i - 1) {
					Map<String, Object> mapQueryUser = new HashMap<String, Object>();
					mapQueryUser.put("userId", userIds[i]);
					List<MUserHotel> listUserHotel = userTaskDao.selectUserHotelJoinUser(mapQueryUser);
					if (listUserHotel.size() > 0 && listUserHotel.get(0).getDoBusinessStatus() < 2) {
						Map<String, Object> mapQueryUser_2 = new HashMap<String, Object>();
						mapQueryUser_2.put("id", userIds[i]);
						MUser u = userTaskDao.selectUser(mapQueryUser_2);
						/*新增收益记录*/
						MUserProfit userProfit = new MUserProfit();
						userProfit.setAfterAmount(u.getVad() + vad * 0.1);
						userProfit.setBeforeAmount(u.getVad());
						userProfit.setCreateTime(new Long(Utils.getTimestamp()).intValue());
						userProfit.setModifyTime(new Long(Utils.getTimestamp()).intValue());
						userProfit.setProfitAmount(vad * 0.1);
						userProfit.setProfitCategory("3");//非直推收益
						userProfit.setProfitRemarks("非直推收益");
						userProfit.setStatus(1);
						String userProfitStrId = Utils.getUUID();
						userProfit.setStrId(userProfitStrId);
						userProfit.setUserStrId(u.getStrId());
						userTaskDao.insertUserProfit(userProfit);//新增收益
						/*新增资金明细订单记录(奖励明细)-2019-10-14*/
						MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
						detailOrder.setStrId(Utils.getUUID());
						detailOrder.setUserStrId(u.getStrId());
						detailOrder.setOrderCategory(4); //奖励明细
						detailOrder.setRelationOrderStrId(userProfitStrId);//关联非直推收益
						detailOrder.setTokenCategory(1);//vad
						detailOrder.setAmount(vad);
						detailOrder.setBeforeAmount(u.getVad());
						detailOrder.setAfterAmount(u.getVad() + vad * 0.1);
						detailOrder.setRemarks("非直推收益");
						detailOrder.setOrderStatus(1);
						detailOrder.setStatus(1);
						detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
						detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
						userTaskDao.insertUserFundDetailOrder(detailOrder); //新增资金明细
						/*修改用户VAD*/
						Map<String, Object> mapModifyUser = new HashMap<String, Object>();
						mapModifyUser.put("userStrId", u.getStrId());
						mapModifyUser.put("vad", Utils.formatDoubleForDouble(u.getVad() + vad * 0.1));
						userTaskDao.updateUser(mapModifyUser);
					}
				}
			}
		}
	}
}
