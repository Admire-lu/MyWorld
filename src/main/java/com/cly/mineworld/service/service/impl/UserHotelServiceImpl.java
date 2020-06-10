package com.cly.mineworld.service.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import com.cly.mineworld.service.entity.*;
import com.cly.mineworld.service.vo.userHoterTeam.UserHotelTeamVo;
import com.cly.mineworld.service.vo.userManagerTeam.UserManagerTeamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserHotelDao;
import com.cly.mineworld.service.service.UserHotelService;
import com.cly.mineworld.service.vo.common.ResultVo;
import com.cly.mineworld.service.vo.userHotel.UserHotelVo;
import net.sf.json.JSONObject;

@Service
@Transactional
public class UserHotelServiceImpl implements UserHotelService {

	@Autowired
	private UserHotelDao userHotelDao;


	/**
	 * 我的酒店
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo getMyHotels(String jsonData) {
		//jsonData报文格式：{"data":{"userStrId":"640380603b3c4b83b7465525cc570ba2"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String userStrId = data.getString("userStrId");
			if (null != userStrId && !"".equals(userStrId)) {
				Map<String, Object> mapQueryMyHotels = new HashMap<String, Object>();
				mapQueryMyHotels.put("userStrId", userStrId);
				List<UserHotelVo> listHotel = userHotelDao.selectHotelJoinMyHotel(mapQueryMyHotels);//查询用户酒店VO
				jData.put("hotels", listHotel);
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
	 * 选择酒店
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo chooseTheHotel(String jsonData) {
		//jsonData报文格式：{"data":{"userStrId":"640380603b3c4b83b7465525cc570ba2","teamCategory":"2"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String userStrId = data.getString("userStrId");
			String teamCategory = data.getString("teamCategory");
			if (null != userStrId && !"".equals(userStrId) && null != teamCategory && !"".equals(teamCategory)) {
				Map<String, Object> findHotelMap = new HashMap<String, Object>();
				findHotelMap.put("userStrId", userStrId);
				findHotelMap.put("teamCategory", teamCategory);
				List<MHotel> hotelList = userHotelDao.selectHotelList(findHotelMap);
				findHotelMap.put("hotelStrId",hotelList.get(0).getStrId());
				//findHotelMap.put("doBusinessStatus",2);
				List<MUserHotel> userHotelList = userHotelDao.selectUserHotelList(findHotelMap);
				List<UserManagerTeamVo> userHotels = new ArrayList<UserManagerTeamVo>();
				for (MUserHotel userHotel : userHotelList) {
					UserManagerTeamVo vo = new UserManagerTeamVo();
					vo.setHotelCustomName(userHotel.getHotelCustomName());
					vo.setUserHotelStrId(userHotel.getStrId());
					if ("1".equals(userHotel.getDoBusinessStatus().toString())){//营业中的酒店
						findHotelMap.put("userHotelStrId",userHotel.getStrId());
						String surplusManagerTime = userHotelDao.findSurplusManagerTime(findHotelMap);
						vo.setSurplusManagerTime(surplusManagerTime);
					}else {
                        vo.setSurplusManagerTime("0");
					}
					userHotels.add(vo);
				}
				jData.put("userHotels",userHotels);
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
	 * 修改用户酒店名称
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo updateUserHotelCustomName(String jsonData) {
		//jsonData报文格式：{"data":{"userHotelStrId":"640380603b3c4b83b7465525cc570ba2","hotelCustomName":"夜巴黎"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String userHotelStrId = data.getString("userHotelStrId");
			String hotelCustomName = data.getString("hotelCustomName");
			if (null != userHotelStrId && !"".equals(userHotelStrId)
					&& null != hotelCustomName && !"".equals(hotelCustomName)) {
				Map<String, Object> updateMap = new HashMap<String, Object>();
				updateMap.put("userHotelStrId", userHotelStrId);
				updateMap.put("hotelCustomName", hotelCustomName);
				userHotelDao.updateUserHotel(updateMap);
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
	 * 获取所有酒店类型
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo getHotels(String jsonData) {
		//jsonData报文格式：{"data":{"":""}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			List<MHotel> listHotel = userHotelDao.selectHotelList(new HashMap<String, Object>());
			jData.put("hotels", listHotel);
		} else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}

	/**
	 * 购买酒店
	 * @param jsonData
	 * @return
	 */
	@Override
    public ResultVo buyHotel(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123","hotelStrId":"123",
        // "scenicSpotStrId":"123456","hotelCustomName":"夜巴黎","directionalVAD","xxx"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String hotelStrId = data.getString("hotelStrId");
            String scenicSpotStrId = data.getString("scenicSpotStrId");
            String hotelCustomName = data.getString("hotelCustomName");
            String directionalVAD = data.getString("directionalVAD");
            if (null != userStrId && !"".equals(userStrId)
                    && null != hotelStrId && !"".equals(hotelStrId)
                    && null != scenicSpotStrId && !"".equals(scenicSpotStrId)
                    && null != hotelCustomName && !"".equals(hotelCustomName)
                    && null != directionalVAD && !"".equals(directionalVAD)) {
                /*校验景点是否存在*/
                Map<String, Object> queryScenicSpotCount = new HashMap<String, Object>();
                queryScenicSpotCount.put("scenicSpotStrId", scenicSpotStrId);
                if (userHotelDao.selectScenicSpotCount(queryScenicSpotCount) > 0) {
                    /*校验用户是否存在*/
                    Map<String, Object> queryUserListMap = new HashMap<String, Object>();
                    queryUserListMap.put("userStrId", userStrId);
                    List<MUser> userList = userHotelDao.selectUserList(queryUserListMap);
                    if (userList.size() > 0) {//用户存在
                        MUser user = userList.get(0);//用户信息
                        /*获取要购买的酒店信息*/
                        Map<String, Object> queryHotelListMap = new HashMap<String, Object>();
                        queryHotelListMap.put("hotelStrId", hotelStrId);
                        List<MHotel> listHotel = userHotelDao.selectHotelList(queryHotelListMap);
                        if (listHotel.size() > 0) {//酒店存在
                            MHotel hotel = listHotel.get(0);
                            if (Integer.parseInt(directionalVAD) > 0) {//使用定向vad
                                //获取用户定向VAD信息
                                Map<String, Object> queryUserDirectionalVADMap = new HashMap<String, Object>();
                                queryUserDirectionalVADMap.put("userStrId", userStrId);
                                List<MActivity> activityList = userHotelDao.selectDirectionalVADList(queryUserDirectionalVADMap);
                                if (activityList.size() > 0) {//用户有定向vad数量
                                    MActivity activity = activityList.get(0);
                                    String ActivityStartTime = "2019-10-16 00:00:00"; //活动开始时间
                                    //String ActivityStartTime = "2019-08-01 00:00:00"; //活动开始时间
                                    String nowTime = Utils.getNowTimeForSeconds();
                                    Long activityHierarchy = Utils.getDaysBetween(ActivityStartTime, nowTime);//处于哪个活动阶段
                                    Double startCost = hotel.getStartCost();//开设酒所需要的费用
                                    if (0 <= activityHierarchy && activityHierarchy <= 30) {//活动第一阶段
                                        /*校验用户vad和用户定向Vad是否足够开此酒店*/
                                        //开设酒店比例5：5， 开设酒店所用费用正常VAD扣50%，定向VAD扣50%
                                        Double needDeductionDirectionalVAD = startCost * 0.5; //需要扣除的定向vad
                                        Double needDeductionNormalVAD = startCost * 0.5;//需要扣除的正常vad
										//用户的定向vad够此次扣款，并且需要输入与需扣定向vad相同的vad才可扣款
                                        if (activity.getAmount() >= needDeductionDirectionalVAD && Double.valueOf(directionalVAD).equals(needDeductionDirectionalVAD)) {
                                            if (user.getVad() >= needDeductionNormalVAD) {
                                                //用户开设酒店--需要同时更新活动定向vad表
                                                Double afterAmount = activity.getAmount() - needDeductionDirectionalVAD;
                                                Double afterUserAmount = user.getVad() - needDeductionNormalVAD;
                                                usersOpenHotels(user, hotel, hotelCustomName, userStrId, scenicSpotStrId, jData, afterAmount,afterUserAmount);
                                            } else {
                                                result = "-15";//vad不足
                                                message = "Vad is not enough";
                                            }
                                        } else {
                                            result = "-46";//用户定向vad不足
                                            message = "DirectionalVAD is Insufficient";
                                        }
                                    } else if (31 <= activityHierarchy && activityHierarchy <= 60) {//活动第二阶段
                                        //开设酒店比例7：3， 开设酒店所用费用正常VAD扣70%，定向VAD扣30%
                                        Double needDeductionDirectionalVAD = startCost * 0.3; //需要扣除的定向vad
                                        Double needDeductionNormalVAD = startCost * 0.7;//需要扣除的正常vad
                                        if (activity.getAmount() >= needDeductionDirectionalVAD && Double.valueOf(directionalVAD).equals(needDeductionDirectionalVAD)) {
											//用户的定向vad够此次扣款，并且需要输入与需扣定向vad相同的vad才可扣款
                                            if (user.getVad() >= needDeductionNormalVAD ) {
                                                //用户开设酒店--需要同时更新活动定向vad表
                                                Double afterAmount = activity.getAmount() - needDeductionDirectionalVAD;
                                                Double afterUserAmount = user.getVad() - needDeductionNormalVAD;
                                                usersOpenHotels(user, hotel, hotelCustomName, userStrId, scenicSpotStrId, jData, afterAmount,afterUserAmount);
                                            } else {
                                                result = "-15";//vad不足
                                                message = "Vad is not enough";
                                            }
                                        } else {
                                            result = "-46";//用户定向vad不足
                                            message = "DirectionalVAD is Insufficient";
                                        }
                                    } else if (61 <= activityHierarchy && activityHierarchy <= 90) {//活动第三阶段
                                        //开设酒店比例8：2， 开设酒店所用费用正常VAD扣80%，定向VAD扣20%
                                        Double needDeductionDirectionalVAD = startCost * 0.2; //需要扣除的定向vad
                                        Double needDeductionNormalVAD = startCost * 0.8;//需要扣除的正常vad
										//用户的定向vad够此次扣款，并且需要输入与需扣定向vad相同的vad才可扣款
                                        if (activity.getAmount() >= needDeductionDirectionalVAD && Double.valueOf(directionalVAD).equals(needDeductionDirectionalVAD)) {
                                            if (user.getVad() >= needDeductionNormalVAD) {
                                                //用户开设酒店--需要同时更新活动定向vad表
                                                Double afterAmount = activity.getAmount() - needDeductionDirectionalVAD;
                                                Double afterUserAmount = user.getVad() - needDeductionNormalVAD;
                                                usersOpenHotels(user, hotel, hotelCustomName, userStrId, scenicSpotStrId, jData, afterAmount,afterUserAmount);
                                            } else {
                                                result = "-15";//vad不足
                                                message = "Vad is not enough!";
                                            }
                                        } else {
                                            result = "-46";//用户定向vad不足
                                            message = "DirectionalVAD is Insufficient!";
                                        }
                                    }else {
                                        result = "-49";//定向vad失效
                                        message = "Directional Currency Failure!";
                                    }
                                } else {
                                    result = "-46";//用户定向vad不足
                                    message = "DirectionalVAD is Insufficient";
                                }
                            } else { //不使用定向vad
                                /*校验用户vad是否足够开此酒店*/
                                if (user.getVad() >= hotel.getStartCost()) {//vad足够
                                    //获取用户定向VAD信息
                                    Map<String, Object> queryUserDirectionalVADMap = new HashMap<String, Object>();
                                    queryUserDirectionalVADMap.put("userStrId", userStrId);
                                    List<MActivity> activityList = userHotelDao.selectDirectionalVADList(queryUserDirectionalVADMap);
                                    Double afterAmount = 0.00;
                                    Double afterUserAmount = user.getVad() - hotel.getStartCost();
                                    if (activityList.size() > 0) {
                                        MActivity activity = activityList.get(0);
                                        afterAmount = activity.getAmount();
                                    }
                                    usersOpenHotels(user, hotel, hotelCustomName, userStrId, scenicSpotStrId, jData, afterAmount,afterUserAmount);
                                } else {
                                    result = "-15";//vad不足
                                    message = "Vad is not enough";
                                }
                            }
                        } else {
                            result = "-14";//酒店不存在
                            message = "Hotel is not exist!";
                        }
                    } else {//用户不存在
                        result = "-13";//用户不存在
                        message = "User is not exist!";
                    }
                } else {
                    result = "-16";//景点不存在
                    message = "ScenicSpot is not exist!";
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
     * 用户开设酒店
     */
    private void usersOpenHotels(MUser user, MHotel hotel, String hotelCustomName, String userStrId, String scenicSpotStrId, JSONObject jData, Double afterAmount,Double afterUserAmount) {
        /*新增用户酒店*/
        MUserHotel userHotel = new MUserHotel();
        userHotel.setCreateTime(new Long(Utils.getTimestamp()).intValue());
        userHotel.setDoBusinessDays(0);
        userHotel.setDoBusinessStatus(2);//默认停业中
        //需要再接收一个参数 hotelCustomName ;set进去即可  TODO
        //userHotel.setHotelCustomName(user.getNickname() + "-" + hotel.getHotelName());
        userHotel.setHotelCustomName(hotelCustomName);
        userHotel.setHotelStrId(hotel.getStrId());
        userHotel.setModifyTime(new Long(Utils.getTimestamp()).intValue());
        userHotel.setProfit(0.00);
        userHotel.setScenicSpotStrId(scenicSpotStrId);
        userHotel.setStatus(1);
        userHotel.setStopBusinessDays(0);
        String userHotelStrId = Utils.getUUID();
        jData.put("userHotelStrId", userHotelStrId);
        userHotel.setStrId(userHotelStrId);
        userHotel.setUserStrId(userStrId);
        userHotelDao.insertUserHotel(userHotel);
        /*新增用户消费记录*/
        MUserConsumptionHistory history = new MUserConsumptionHistory();
        history.setAfterAmount(user.getVad() - hotel.getStartCost());
        history.setAmount(afterUserAmount);//todo 减去应该消费正常的vad
        history.setBeforeAmount(user.getVad());
        history.setConsumptionCategoryStrId("a7ffd63bf6134b0dbf0daedda08462b9");//购买酒店
        history.setCreateTime(new Long(Utils.getTimestamp()).intValue());
        history.setModifyTime(new Long(Utils.getTimestamp()).intValue());
        history.setStatus(1);
        history.setStrId(Utils.getUUID());
        history.setUserStrId(userStrId);
        history.setSubOrderStrId(userHotel.getStrId());//关联订单ID
        userHotelDao.insertUserConsumptionHistory(history);
        /*新增用户资金明细记录（支出明细）*/
        MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
        detailOrder.setStrId(Utils.getUUID());
        detailOrder.setUserStrId(userStrId);
        detailOrder.setOrderCategory(3);//支出明细
        detailOrder.setRelationOrderStrId(userHotel.getStrId());//关联订单ID
        detailOrder.setTokenCategory(1);
        detailOrder.setAmount(hotel.getStartCost());
        detailOrder.setBeforeAmount(user.getVad());
        detailOrder.setAfterAmount(afterUserAmount);//todo 减去应该消费正常的vad
        detailOrder.setRemarks("购买酒店");
        detailOrder.setOrderStatus(1);
        detailOrder.setStatus(1);
        detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
        detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
        userHotelDao.insertUserFundDetailOrder(detailOrder);
        /*减少用户vad*/
        Map<String, Object> modifyUserMap = new HashMap<String, Object>();
        modifyUserMap.put("userStrId", userStrId);
        modifyUserMap.put("vad", afterUserAmount);//todo 减去应该消费正常的vad
        userHotelDao.updateUser(modifyUserMap);
        //减少用户定向vad
        Map<String, Object> modifyUserActivityMap = new HashMap<String, Object>();
        modifyUserActivityMap.put("userStrId", userStrId);
        modifyUserActivityMap.put("vad", afterAmount);
        userHotelDao.updateUserActivity(modifyUserActivityMap);
    }

    /**
	 * 获取用户所有酒店
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo getUserHotels(String jsonData) {
		//jsonData报文格式：{"data":{"userStrId":"123456","scenicSpotStrId":"123456","pageNum":"0","pageSize":"20"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String scenicSpotStrId = data.getString("scenicSpotStrId");
			String userStrId = data.getString("userStrId");
			String pageNum = data.getString("pageNum");
			String pageSize = data.getString("pageSize");
			if (null != scenicSpotStrId && !"".equals(scenicSpotStrId)
					&& null != userStrId && !"".equals(userStrId)
			        && null != pageNum && !"".equals(pageNum)
					&& null != pageSize && !"".equals(pageSize)) {
				Map<String, Object> mapQueryUserHotels = new HashMap<String, Object>();
				//分页查询
				mapQueryUserHotels.put("scenicSpotStrId", scenicSpotStrId);
				mapQueryUserHotels.put("userStrId", userStrId);
				mapQueryUserHotels.put("pageNum",pageNum);
				mapQueryUserHotels.put("pageSize",pageSize);
                Integer totalNumber = userHotelDao.selectUserHotels(mapQueryUserHotels);
                Integer totalPage = totalNumber % Integer.parseInt(pageSize);
                if (totalNumber.equals(totalPage)) {
                    totalPage = 1;
                } else if (0 == totalPage) {
                    totalPage = totalNumber / Integer.parseInt(pageSize);
                } else {
                    totalPage = totalNumber / Integer.parseInt(pageSize) + 1;
                }
                jData.put("totalNumber", totalNumber);
                jData.put("totalPage", totalPage);
                List<UserHotelVo> listHotel = userHotelDao.selectHotelJoinUserHotel(mapQueryUserHotels);//查询用户酒店VO
				jData.put("hotels", listHotel);
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
	 * 获取所有酒店管理团队类型
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo getHotelManagerTeams(String jsonData) {
		//jsonData报文格式：{"data":{"":""}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			List<MHotelManagerTeam> listHotelManagerTeam = userHotelDao.selectHotelManagerTeamList(new HashMap<String, Object>());
			jData.put("hotalManagerTeams", listHotelManagerTeam);
		} else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}

	/**
	 * 为酒店管理团队补充时间
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo timeForHotelManagementTeam(String jsonData) {
		//jsonData报文格式：{"data":{"userStrId":"123123","userHotelStrId":"123456","hotelManagerTeamStrId":"123123","price":"28"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String userStrId = data.getString("userStrId");
			String userHotelStrId = data.getString("userHotelStrId");//唯一标识
			String hotelManagerTeamStrId = data.getString("hotelManagerTeamStrId");
			String price = data.getString("price"); //增加团队的价格
			if (null != userHotelStrId && !"".equals(userHotelStrId)
					&& null != hotelManagerTeamStrId && !"".equals(hotelManagerTeamStrId)
					&& null != userStrId && !"".equals(userStrId)
					&& null != price && !"".equals(price)) {
				/*校验用户管理团队是否存在*/
				Map<String, Object> queryUserHotelManagerTeamMap = new HashMap<String, Object>();
				queryUserHotelManagerTeamMap.put("userHotelStrId", userHotelStrId);
				/*2019-10-30日修改 查询出用户这个酒店是否有管理团队，不考虑营业状态*/
				List< MUserHotelManagerTeam> listUserHotelManagerTeam = userHotelDao.selectUserHotelManagerTeamList(queryUserHotelManagerTeamMap);
				if (listUserHotelManagerTeam.size() > 0) {//用户管理团队
					//有两种情况 1.正在营业，在原来的运行时间上给该酒店增加7天营业时间  2.已停业，给用户再续上7天营业时间
					MUserHotelManagerTeam managerTeam = listUserHotelManagerTeam.get(0);
					if (1 == managerTeam.getWorkStatus()){ //1.正在营业，在原来的运行时间上给该酒店增加7天营业时间
						//判断用户是否有那么多vad
						Map<String, Object> queryUserMap = new HashMap<String, Object>();
						queryUserMap.put("userStrId", userStrId);
						List<MUser> userList = userHotelDao.selectUserList(queryUserMap);
						if (userList.size()>0){
							MUser user = userList.get(0);
							if (user.getVad() >= Double.valueOf(price)) {//资金足够
								/*为用户酒店管理团队增加时间*/
								managerTeam.setWorkStatus(1);//开始工作
								//managerTeam.setWorkStartTime(Utils.getNowDateStr2());//不再需要设开始时间
								//String workEndTime = Utils.getDayNextDay(Utils.getNowDateStr2(), 7);
								String workEndTime = Utils.getDayNextDay(managerTeam.getWorkEndTime(), 7);//结束时间加上7天
								managerTeam.setWorkEndTime(workEndTime);//结束时间
								managerTeam.setModifyTime(new Long(Utils.getTimestamp()).intValue());
								userHotelDao.updateHotelManagerTeam(managerTeam);
								/*用户酒店开始运营*/
								Map<String, Object> modifyUserHotelMap = new HashMap<String, Object>();
								modifyUserHotelMap.put("userHotelStrId", userHotelStrId);
								modifyUserHotelMap.put("doBusinessStatus", 1);//营业中
								userHotelDao.updateUserHotel(modifyUserHotelMap);
								//减少用户vad --2019-10-16
								Double beforeVad = user.getVad();
								Double afterVad = beforeVad - Double.valueOf(price);
								Map<String, Object> modifyUserMap = new HashMap<String, Object>();
								modifyUserMap.put("userStrId", userStrId);
								modifyUserMap.put("vad", afterVad);
								userHotelDao.updateUser(modifyUserMap);
								//增加资金明细表(支出明细) --2019-10-16
								MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
								detailOrder.setStrId(Utils.getUUID());
								detailOrder.setUserStrId(userStrId);
								detailOrder.setOrderCategory(3);//支出明细
								detailOrder.setRelationOrderStrId("");//关联订单ID
								detailOrder.setTokenCategory(1);
								detailOrder.setAmount(Double.valueOf(price));
								detailOrder.setBeforeAmount(user.getVad());
								detailOrder.setAfterAmount(afterVad);//
								detailOrder.setRemarks("购买管理团队");
								detailOrder.setOrderStatus(1);
								detailOrder.setStatus(1);
								detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
								detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
								userHotelDao.insertUserFundDetailOrder(detailOrder);
							} else {
								result = "-15";//vad不足
								message = "Vad is not enough";
							}
						} else {
							result = "-13";//用户不存在
							message = "User is not exist!";
						}
					}else { //2.已停业，给用户再续上7天营业时间
						//判断用户是否有那么多vad
						Map<String, Object> queryUserMap = new HashMap<String, Object>();
						queryUserMap.put("userStrId", userStrId);
						List<MUser> userList = userHotelDao.selectUserList(queryUserMap);
						if (userList.size()>0){
							MUser user = userList.get(0);
							if (user.getVad() >= Double.valueOf(price)) {//资金足够
								/*为用户酒店管理团队增加时间*/
								managerTeam.setWorkStatus(1);//开始工作
								managerTeam.setWorkStartTime(Utils.getNowDateStr2());//开始时间
								String workEndTime = Utils.getDayNextDay(Utils.getNowDateStr2(), 7);
								managerTeam.setWorkEndTime(workEndTime);//结束时间
								managerTeam.setModifyTime(new Long(Utils.getTimestamp()).intValue());
								userHotelDao.updateHotelManagerTeam(managerTeam);
								/*用户酒店开始运营*/
								Map<String, Object> modifyUserHotelMap = new HashMap<String, Object>();
								modifyUserHotelMap.put("userHotelStrId", userHotelStrId);
								modifyUserHotelMap.put("doBusinessStatus", 1);//营业中
								userHotelDao.updateUserHotel(modifyUserHotelMap);
								//减少用户vad --2019-10-16
								Double beforeVad = user.getVad();
								Double afterVad = beforeVad - Double.valueOf(price);
								Map<String, Object> modifyUserMap = new HashMap<String, Object>();
								modifyUserMap.put("userStrId", userStrId);
								modifyUserMap.put("vad", afterVad);
								userHotelDao.updateUser(modifyUserMap);
								//增加资金明细表(支出明细) --2019-10-16
								MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
								detailOrder.setStrId(Utils.getUUID());
								detailOrder.setUserStrId(userStrId);
								detailOrder.setOrderCategory(3);//支出明细
								detailOrder.setRelationOrderStrId("");//关联订单ID
								detailOrder.setTokenCategory(1);
								detailOrder.setAmount(Double.valueOf(price));
								detailOrder.setBeforeAmount(user.getVad());
								detailOrder.setAfterAmount(afterVad);//
								detailOrder.setRemarks("购买管理团队");
								detailOrder.setOrderStatus(1);
								detailOrder.setStatus(1);
								detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
								detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
								userHotelDao.insertUserFundDetailOrder(detailOrder);
							} else {
								result = "-15";//vad不足
								message = "Vad is not enough";
							}
						} else {
							result = "-13";//用户不存在
							message = "User is not exist!";
						}
					}
				} else {//用户管理团队不存在 TOdO  少状态码
					result = "-61";
					message = "User management team does not exist!";
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
	 * 为酒店增加管理团队
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo addManagerTeamForHotel(String jsonData) {
		//jsonData报文格式：{"data":{"userStrId":"123123","userHotelStrId":"123456","hotelManagerTeamStrId":"123123","price":"28"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String userStrId = data.getString("userStrId");
			String userHotelStrId = data.getString("userHotelStrId");
			String hotelManagerTeamStrId = data.getString("hotelManagerTeamStrId");
			String price = data.getString("price"); //增加团队的价格
			if (null != userHotelStrId && !"".equals(userHotelStrId)
					&& null != hotelManagerTeamStrId && !"".equals(hotelManagerTeamStrId)
					&& null != userStrId && !"".equals(userStrId)
					&& null != price && !"".equals(price)) {
				/*校验用户酒店是否存在*/
				Map<String, Object> queryUserHotelMap = new HashMap<String, Object>();
				queryUserHotelMap.put("userStrId", userStrId);
				queryUserHotelMap.put("userHotelStrId", userHotelStrId);
				List<MUserHotel> listUserHotel = userHotelDao.selectUserHotelList(queryUserHotelMap);
				if (listUserHotel.size() > 0) {//用户酒店存在
					/*校验用户酒店是否已经有管理团队在工作*/
					Map<String, Object> queryUserHotelManagerTeam = new HashMap<String, Object>();
					queryUserHotelManagerTeam.put("userHotelStrId", userHotelStrId);
					//queryUserHotelManagerTeam.put("workStatus", 1);//正在工作的
					/*2019-10-30日修改 查询出用户这个酒店是否有管理团队，不考虑营业状态*/
					List<MUserHotelManagerTeam> listUserHotelManagerTeam = userHotelDao.selectUserHotelManagerTeamList(queryUserHotelManagerTeam);
					if (listUserHotelManagerTeam.size()>0){
						//有两种情况 1.正在营业，在原来的运行时间上给该酒店增加7天营业时间  2.已停业，给用户再续上7天营业时间
						MUserHotelManagerTeam managerTeam = listUserHotelManagerTeam.get(0);
						if (1 == managerTeam.getWorkStatus()) { //1.正在营业，在原来的运行时间上给该酒店增加7天营业时间
							Long remainingTime = Utils.getDaysBetween(Utils.getNowDateStr2(), managerTeam.getWorkEndTime());
							if (1 == remainingTime || 0 == remainingTime) { //只有0和1才能够买
								//判断用户是否有那么多vad
								Map<String, Object> queryUserMap = new HashMap<String, Object>();
								queryUserMap.put("userStrId", userStrId);
								List<MUser> userList = userHotelDao.selectUserList(queryUserMap);
								if (userList.size() > 0) {
									MUser user = userList.get(0);
									if (user.getVad() >= Double.valueOf(price)) {//资金足够
										/*为用户酒店管理团队增加时间*/
										managerTeam.setWorkStatus(1);//开始工作
										//managerTeam.setWorkStartTime(Utils.getNowDateStr2());//不再需要设开始时间
										//String workEndTime = Utils.getDayNextDay(Utils.getNowDateStr2(), 7);
										String workEndTime = Utils.getDayNextDay(managerTeam.getWorkEndTime(), 7);//结束时间加上7天
										managerTeam.setWorkEndTime(workEndTime);//结束时间
										managerTeam.setModifyTime(new Long(Utils.getTimestamp()).intValue());
										userHotelDao.updateHotelManagerTeam(managerTeam);
										/*用户酒店开始运营*/
										Map<String, Object> modifyUserHotelMap = new HashMap<String, Object>();
										modifyUserHotelMap.put("userHotelStrId", userHotelStrId);
										modifyUserHotelMap.put("doBusinessStatus", 1);//营业中
										userHotelDao.updateUserHotel(modifyUserHotelMap);
										//减少用户vad --2019-10-16
										Double beforeVad = user.getVad();
										Double afterVad = beforeVad - Double.valueOf(price);
										Map<String, Object> modifyUserMap = new HashMap<String, Object>();
										modifyUserMap.put("userStrId", userStrId);
										modifyUserMap.put("vad", afterVad);
										userHotelDao.updateUser(modifyUserMap);
										//增加资金明细表(支出明细) --2019-10-16
										MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
										detailOrder.setStrId(Utils.getUUID());
										detailOrder.setUserStrId(userStrId);
										detailOrder.setOrderCategory(3);//支出明细
										detailOrder.setRelationOrderStrId("");//关联订单ID
										detailOrder.setTokenCategory(1);
										detailOrder.setAmount(Double.valueOf(price));
										detailOrder.setBeforeAmount(user.getVad());
										detailOrder.setAfterAmount(afterVad);//
										detailOrder.setRemarks("购买管理团队");
										detailOrder.setOrderStatus(1);
										detailOrder.setStatus(1);
										detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
										detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
										userHotelDao.insertUserFundDetailOrder(detailOrder);
									} else {
										result = "-15";//vad不足
										message = "Vad is not enough";
									}
								} else {
									result = "-13";//用户不存在
									message = "User is not exist!";
								}
							}else {//此酒店已经有管理团队在工作了
								result = "-18";//已经有管理团队在工作了
								message = "Hotel manager team is working!";
							}
						}else { //2.已停业，给用户再续上7天营业时间
								//判断用户是否有那么多vad
								Map<String, Object> queryUserMap = new HashMap<String, Object>();
								queryUserMap.put("userStrId", userStrId);
								List<MUser> userList = userHotelDao.selectUserList(queryUserMap);
								if (userList.size()>0){
									MUser user = userList.get(0);
									if (user.getVad() >= Double.valueOf(price)) {//资金足够
										/*为用户酒店管理团队增加时间*/
										managerTeam.setWorkStatus(1);//开始工作
										managerTeam.setWorkStartTime(Utils.getNowDateStr2());//开始时间
										String workEndTime = Utils.getDayNextDay(Utils.getNowDateStr2(), 7);
										managerTeam.setWorkEndTime(workEndTime);//结束时间
										managerTeam.setModifyTime(new Long(Utils.getTimestamp()).intValue());
										userHotelDao.updateHotelManagerTeam(managerTeam);
										/*用户酒店开始运营*/
										Map<String, Object> modifyUserHotelMap = new HashMap<String, Object>();
										modifyUserHotelMap.put("userHotelStrId", userHotelStrId);
										modifyUserHotelMap.put("doBusinessStatus", 1);//营业中
										userHotelDao.updateUserHotel(modifyUserHotelMap);
										//减少用户vad --2019-10-16
										Double beforeVad = user.getVad();
										Double afterVad = beforeVad - Double.valueOf(price);
										Map<String, Object> modifyUserMap = new HashMap<String, Object>();
										modifyUserMap.put("userStrId", userStrId);
										modifyUserMap.put("vad", afterVad);
										userHotelDao.updateUser(modifyUserMap);
										//增加资金明细表(支出明细) --2019-10-16
										MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
										detailOrder.setStrId(Utils.getUUID());
										detailOrder.setUserStrId(userStrId);
										detailOrder.setOrderCategory(3);//支出明细
										detailOrder.setRelationOrderStrId("");//关联订单ID
										detailOrder.setTokenCategory(1);
										detailOrder.setAmount(Double.valueOf(price));
										detailOrder.setBeforeAmount(user.getVad());
										detailOrder.setAfterAmount(afterVad);//
										detailOrder.setRemarks("购买管理团队");
										detailOrder.setOrderStatus(1);
										detailOrder.setStatus(1);
										detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
										detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
										userHotelDao.insertUserFundDetailOrder(detailOrder);
									} else {
										result = "-15";//vad不足
										message = "Vad is not enough";
									}
								} else {
									result = "-13";//用户不存在
									message = "User is not exist!";
								}
						}
					}else {
						//此用户的此酒店没有购买过管理团队，给该酒店添加一个为期7天的管理团队
						Map<String, Object> queryUserMap = new HashMap<String, Object>();
						queryUserMap.put("userStrId", userStrId);
						List<MUser> userList = userHotelDao.selectUserList(queryUserMap);
						if (userList.size()>0){
							MUser user = userList.get(0);
							if (user.getVad() >= Double.valueOf(price)) {//资金足够---判断用户是否有那么多vad
								/*为用户酒店增加管理团队*/
								MUserHotelManagerTeam userHotelManagerTeam = new MUserHotelManagerTeam();
								userHotelManagerTeam.setCreateTime(new Long(Utils.getTimestamp()).intValue());
								userHotelManagerTeam.setHotelManagerTeamStrId(hotelManagerTeamStrId);
								userHotelManagerTeam.setModifyTime(new Long(Utils.getTimestamp()).intValue());
								userHotelManagerTeam.setStatus(1);
								userHotelManagerTeam.setUserHotelStrId(userHotelStrId);
								String workEndTime = Utils.getDayNextDay(Utils.getNowDateStr2(), 7);
								userHotelManagerTeam.setWorkEndTime(workEndTime);
								userHotelManagerTeam.setWorkStartTime(Utils.getNowDateStr2());
								userHotelManagerTeam.setWorkStatus(1);//开始工作
								userHotelManagerTeam.setStrId(Utils.getUUID());
								userHotelManagerTeam.setUserStrId(userStrId);
								userHotelDao.insertUserHotelManagerTeam(userHotelManagerTeam);
								/*用户酒店开始运营*/
								Map<String, Object> modifyUserHotelMap = new HashMap<String, Object>();
								modifyUserHotelMap.put("userHotelStrId", userHotelStrId);
								modifyUserHotelMap.put("doBusinessStatus", 1);//营业中
								userHotelDao.updateUserHotel(modifyUserHotelMap);
								//减少用户vad --2019-10-16
								Double beforeVad = user.getVad();
								Double afterVad = beforeVad - Double.valueOf(price);
								Map<String, Object> modifyUserMap = new HashMap<String, Object>();
								modifyUserMap.put("userStrId", userStrId);
								modifyUserMap.put("vad", afterVad);
								userHotelDao.updateUser(modifyUserMap);
								//增加资金明细表(支出明细) --2019-10-16
								MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
								detailOrder.setStrId(Utils.getUUID());
								detailOrder.setUserStrId(userStrId);
								detailOrder.setOrderCategory(3);//支出明细
								detailOrder.setRelationOrderStrId("");//关联订单ID
								detailOrder.setTokenCategory(1);
								detailOrder.setAmount(Double.valueOf(price));
								detailOrder.setBeforeAmount(user.getVad());
								detailOrder.setAfterAmount(afterVad);
								detailOrder.setRemarks("购买管理团队");
								detailOrder.setOrderStatus(1);
								detailOrder.setStatus(1);
								detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
								detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
								userHotelDao.insertUserFundDetailOrder(detailOrder);
							} else {
								result = "-15";//vad不足
								message = "Vad is not enough";
							}
						} else {
							result = "-13";//用户不存在
							message = "User is not exist!";
						}
					}

					/*if (listUserHotelManagerTeam.size() < 1) {//没有在工作的管理团队//TODO
					} else {//此酒店已经有管理团队在工作了
						result = "-18";//已经有管理团队在工作了
						message = "Hotel manager team is working!";
					}*/
				} else {//用户酒店不存在
					result = "-17";//用户酒店不存在
					message = "User hotel is not exist!";
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
	 * 查询用户-酒店-管理团队
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo getUserHotelManagerTeams(String jsonData) {
		// TODO Auto-generated method stub
		//jsonData报文格式：{{"data":{"userHotelStrId":"123"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String userHotelStrId = data.getString("userHotelStrId");
			if (null != userHotelStrId && !"".equals(userHotelStrId)) {
				Map<String, Object> mapQueryUserHotelManagerTeams = new HashMap<String, Object>();
				mapQueryUserHotelManagerTeams.put("userHotelStrId", userHotelStrId);
				mapQueryUserHotelManagerTeams.put("workStatus", 1);//正在工作的
				List<UserHotelTeamVo> listTeams = userHotelDao.selectHotelManagerTeamJoinUserHotelManagerTeam(mapQueryUserHotelManagerTeams);//查询用户酒店团队VO
				jData.put("hotels", listTeams);
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
}
