package com.cly.mineworld.service.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import com.cly.mineworld.service.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserItemShopDao;
import com.cly.mineworld.service.service.UserItemShopService;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;

@Service
@Transactional
public class UserItemShopServiceImpl implements UserItemShopService {

	@Autowired
	private UserItemShopDao userItemShopDao;
	
	@Override
	public ResultVo getItemCategorys(String jsonData) {
		//jsonData报文格式：{"data":{}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			List<MBaseItemCategory> listItemCategory = userItemShopDao.selectItemCategoryList(new HashMap<String,Object>());
			jData.put("itemCategorys", listItemCategory);
		} else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}

	@Override
	public ResultVo getItems(String jsonData) {
		//jsonData报文格式：{"data":{"itemCategoryStrId":"2"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String itemCategoryStrId = data.getString("itemCategoryStrId");
			if (null != itemCategoryStrId && !"".equals(itemCategoryStrId)) {
				Map<String,Object> mapQueryItems = new HashMap<String,Object>();
				mapQueryItems.put("itemCategoryStrId", itemCategoryStrId);
				List<MBaseItem> listItem = userItemShopDao.selectItemList(mapQueryItems);
				jData.put("items", listItem);
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

	@Override
	public ResultVo buyItem(String jsonData) {
		//jsonData报文格式：{"data":{"userStrId":"xxx","itemStrId":"xxx","sign":"xxx"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String userStrId = data.getString("userStrId");
			String itemStrId = data.getString("itemStrId");
			String sign = (String) data.get("sign");
			if (null != sign) {
				if (null != userStrId && !"".equals(userStrId)
						&& null != itemStrId && !"".equals(itemStrId)
						&& null != sign && !"".equals(sign)) {
					/*查询用户是否存在*/
					Map<String, Object> mapQueryUsers = new HashMap<String, Object>();
					mapQueryUsers.put("userStrId", userStrId);
					List<MUser> listUser = userItemShopDao.selectUserList(mapQueryUsers);
					if (listUser.size() > 0) {//用户存在
						MUser user = listUser.get(0);
						/*查询道具是否存在于商城*/
						Map<String, Object> mapQueryItems = new HashMap<String, Object>();
						mapQueryItems.put("itemStrId", itemStrId);
						List<MBaseItem> listBaseItem = userItemShopDao.selectItemList(mapQueryItems);
						if (listBaseItem.size() > 0) {//道具存在
							List<MItemTotalCount> itemTotalCount = userItemShopDao.selectItemTotalCountList(mapQueryItems);
							if (itemTotalCount.get(0).getItemCount() > 0) {//道具库存充足
								MBaseItem item = listBaseItem.get(0);
								/*用户VAD是否足够*/
								if (user.getVad() >= item.getItemPrice()) {//vad足够
									//查询用户拥有的酒店个数
									Map<String, Object> mapQueryHotels = new HashMap<String, Object>();
									mapQueryHotels.put("userStrId", userStrId);
									Integer hotelCount = userItemShopDao.selectUserHotelCount(mapQueryHotels);
									//查询用户当日购买道具的次数
									Map<String, Object> mapQueryUserByItemHistory = new HashMap<String, Object>();
									mapQueryUserByItemHistory.put("userStrId", userStrId);
									mapQueryUserByItemHistory.put("byDate", Utils.getNowDateStr2());//2019-10-23
									Integer byItemCount = userItemShopDao.selectUserByItemCount(mapQueryUserByItemHistory);
									if (byItemCount < hotelCount * 5) {//当日还能购买
										/*查询用户是否已经有此道具*/
										Map<String, Object> mapQueryUserItem = new HashMap<String, Object>();
										mapQueryUserItem.put("userStrId", userStrId);
										mapQueryUserItem.put("itemStrId", itemStrId);
										List<MUserItem> userItemList = userItemShopDao.selectUserItemList(mapQueryUserItem);
										if (userItemList.size() < 1) {//曾经没买过此道具
											/*没有-新增用户道具*/
											MUserItem userItem = new MUserItem();
											userItem.setCreateTime(new Long(Utils.getTimestamp()).intValue());
											userItem.setItemCount(1);//道具数量
											userItem.setItemLv(item.getItemLv());
											userItem.setItemStrId(item.getStrId());
											userItem.setItemUseCount(item.getItemUseCount());
											userItem.setModifyTime(new Long(Utils.getTimestamp()).intValue());
											userItem.setStatus(1);
											userItem.setStrId(Utils.getUUID());
											userItem.setUserStrId(userStrId);
											userItem.setItemCategoryStrId(item.getItemCategoryStrId());//道具类别
											userItemShopDao.insertUserItem(userItem);
											//jData.put("itemUseCount",item.getItemUseCount());
										} else {
											/*已有-修改用户此道具的数量和使用次数*/
											MUserItem userItem = userItemList.get(0);
											userItem.setItemCount(1);
											//userItem.setItemCount(userItem.getItemCount()+1);//在原有的数量上加1
											userItem.setItemUseCount(userItem.getItemUseCount() + item.getItemUseCount());
											userItemShopDao.updateUserItem(userItem);
											//jData.put("itemUseCount",userItem.getItemUseCount() + item.getItemUseCount());
										}
										//查询购买后的次数
										List<MBaseItem> items = userItemShopDao.selectItemList(mapQueryUserItem);
										jData.put("itemUseCount", items.get(0).getItemUseCount());
										/*新增用户购买道具历史 --2019-10-23*/
										MUserItemBuyHistory buyHistory = new MUserItemBuyHistory();
										buyHistory.setStrId(Utils.getUUID());
										buyHistory.setUserStrId(userStrId);
										buyHistory.setItemStrId(itemStrId);
										buyHistory.setItemCategoryStrId(item.getItemCategoryStrId());
										buyHistory.setItemUseCount(item.getItemUseCount());
										buyHistory.setBuyDate(Utils.getNowDateStr2());//2019-10-23
										buyHistory.setHistoryType(1);
										buyHistory.setStatus(1);
										buyHistory.setCreateTime(new Long(Utils.getTimestamp()).intValue());
										buyHistory.setModifyTime(new Long(Utils.getTimestamp()).intValue());
										userItemShopDao.insertUserItemBuyHistory(buyHistory);
										/*新增用户消费历史*/
										MUserConsumptionHistory history = new MUserConsumptionHistory();
										history.setAfterAmount(user.getVad() - item.getItemPrice());
										history.setAmount(item.getItemPrice());
										history.setBeforeAmount(user.getVad());
										history.setConsumptionCategoryStrId("ce503720a4b94af0a12ae3f95b54b0ee");//购买道具
										history.setCreateTime(new Long(Utils.getTimestamp()).intValue());
										history.setModifyTime(new Long(Utils.getTimestamp()).intValue());
										history.setStatus(1);
										history.setStrId(Utils.getUUID());
										history.setSubOrderStrId(item.getStrId());//道具strId作为订单id
										history.setUserStrId(userStrId);
										userItemShopDao.insertUserConsumptionHistory(history);
										/*新增资金明细订单（支出明细）*/
										MUserFundDetailOrder order = new MUserFundDetailOrder();
										order.setAfterAmount(user.getVad() - item.getItemPrice());
										order.setAmount(item.getItemPrice());
										order.setBeforeAmount(user.getVad());
										order.setCreateTime(new Long(Utils.getTimestamp()).intValue());
										order.setModifyTime(new Long(Utils.getTimestamp()).intValue());
										order.setOrderCategory(3);//支出明细
										order.setOrderStatus(1);
										order.setRelationOrderStrId(item.getStrId());//道具strId作为订单id
										if (item.getItemName().indexOf("小汽车") != -1) {
											order.setRemarks("购买" + item.getItemName().substring(0, 2) + "汽车道具，花费 " + item.getItemPrice() + " VAD");
										}
										if (item.getItemName().indexOf("飞行器") != -1) {
											order.setRemarks("买" + item.getItemName().substring(0, 2) + "飞行器道具，花费 " + item.getItemPrice() + " VAD");
										}
										if (item.getItemName().indexOf("钥匙") != -1) {
											order.setRemarks("购买" + item.getItemName().substring(0, 2) + "钥匙道具，花费 " + item.getItemPrice() + " VAD");
										}
										if (item.getItemName().indexOf("保险柜") != -1) {
											order.setRemarks("买" + item.getItemName().substring(0, 2) + "保险柜道具，花费 " + item.getItemPrice() + " VAD");
										}
										if (item.getItemName().indexOf("警犬") != -1) {
											order.setRemarks("购买" + item.getItemName().substring(0, 2) + "警犬道具，花费 " + item.getItemPrice() + " VAD");
										}
										//order.setRemarks("购买" + item.getItemName() + "道具，花费 " + item.getItemPrice() + " VAD");
										order.setStatus(1);
										order.setStrId(Utils.getUUID());
										order.setTokenCategory(1);//消耗VAD
										order.setUserStrId(userStrId);
										userItemShopDao.insertUserFundDetailOrder(order);
										/*扣除用户VAD*/
										user.setVad(user.getVad() - item.getItemPrice());
										userItemShopDao.updateUser(user);
										/*扣除道具库存*/
										Map<String, Object> modifyUserItems = new HashMap<String, Object>();
										modifyUserItems.put("itemStrId", itemStrId);
										modifyUserItems.put("itemCount", itemTotalCount.get(0).getItemCount() - 1);
										userItemShopDao.updateItemTotalCount(modifyUserItems);
									} else {//已达到今天购买上限
										result = "-55";
										message = "Today's purchase limit has been reached!";
									}
								} else {//VAD不足
									result = "-15";
									message = "Vad is not enough！";
								}
							} else {//该道具没有库存
								result = "-52";
								message = "The item is not in stock!";
							}
						} else {//道具不存在
							result = "-37";
							message = "Item is not exists!";
						}
					} else {//用户不存在
						result = "-13";
						message = "User is not exist!";
					}
				} else {
					result = "-2";//参数错误
					message = "Parameters error!";
				}
			}else {
				result = "-60";//当前版本不对
				message = "Please update to the latest version!";
			}
		} else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}
}
