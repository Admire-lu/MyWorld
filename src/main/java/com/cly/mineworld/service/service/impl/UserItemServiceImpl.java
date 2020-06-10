package com.cly.mineworld.service.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;

import com.cly.mineworld.service.entity.*;
import com.cly.mineworld.service.vo.userItem.UserItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserItemDao;
import com.cly.mineworld.service.service.UserItemService;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;

@Service
@Transactional
public class UserItemServiceImpl implements UserItemService {

	@Autowired
	private UserItemDao userItemDao;


	/**
	 * 获取用户道具
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo getUserItems(String jsonData) {
		//jsonData报文格式：{"data":{"userStrId":"xxx","itemCategoryStrId":"2"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String userStrId = data.getString("userStrId");
			String itemCategoryStrId = data.getString("itemCategoryStrId");
			if (null != userStrId && !"".equals(userStrId) && null != itemCategoryStrId && !"".equals(itemCategoryStrId)) {
				/*查询用户是否存在*/
				Map<String,Object> mapQueryUsers = new HashMap<String,Object>();
				mapQueryUsers.put("userStrId", userStrId);
				List<MUser> listUser = userItemDao.selectUserList(mapQueryUsers);
				if(listUser.size() > 0) {//用户存在
					Map<String,Object> mapQueryUserItems = new HashMap<String,Object>();
					mapQueryUserItems.put("userStrId", userStrId);
					mapQueryUserItems.put("itemCategoryStrId", itemCategoryStrId);
					//获取当前分类下所有等级道具的strId
					//List<MBaseItem> items = userItemDao.selectItemList(mapQueryUserItems);
					List<String> itemStrIds = userItemDao.selectItemStrIds(mapQueryUserItems);
					List<UserItemVo> userItemVos = new ArrayList<UserItemVo>();//返回数据
					for (String itemStrId : itemStrIds) {
						UserItemVo vo = new UserItemVo();
						vo.setItemStrId(itemStrId);
						mapQueryUserItems.put("itemStrId", itemStrId);
						List<MUserItem> listUserItem = userItemDao.selectUserItemList(mapQueryUserItems);//获取剩余使用次数
						Double itemPrice = userItemDao.selectItemPrice(mapQueryUserItems);//获取道具价格
						vo.setItemPrice(itemPrice);
						String itemName = userItemDao.selectItemName(mapQueryUserItems);
						vo.setItemName(itemName);
						for (MUserItem userItem : listUserItem) {
							vo.setUserStrId(userItem.getUserStrId());
							vo.setItemCategoryStrId(userItem.getItemCategoryStrId());
							vo.setItemUseCount(String.valueOf(userItem.getItemUseCount()));
						}
						userItemVos.add(vo);
					}
					jData.put("userItems", userItemVos);
				}else {//用户不存在
					result = "-13";
					message = "User is not exist!";
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
	 * 使用道具
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo userUseItem(String jsonData) {
		//jsonData报文格式：{"data":{"userStrId":"xxx","itemStrId":"xxx","scenicSpotStrId":"4b9b991686094c47b196f73e1098bf21"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String userStrId = data.getString("userStrId");
			String itemStrId = data.getString("itemStrId");
			String scenicSpotStrId = data.getString("scenicSpotStrId");
            String sign = (String) data.get("sign");
            if (null != sign) {
                if (null != userStrId && !"".equals(userStrId)
                        && null != itemStrId && !"".equals(itemStrId)
                        && null != scenicSpotStrId && !"".equals(scenicSpotStrId)
                        && null != sign && !"".equals(sign)) {
                    /*查询用户是否存在*/
                    Map<String, Object> mapQueryUsers = new HashMap<String, Object>();
                    mapQueryUsers.put("userStrId", userStrId);
                    List<MUser> listUser = userItemDao.selectUserList(mapQueryUsers);
                    if (listUser.size() > 0) {//用户存在
                        MUser user = listUser.get(0);
                        /*查询道具是否存在于商城*/
                        Map<String, Object> mapQueryItems = new HashMap<String, Object>();
                        mapQueryItems.put("itemStrId", itemStrId);
                        List<MBaseItem> listBaseItem = userItemDao.selectItemList(mapQueryItems);
                        if (listBaseItem.size() > 0 && listBaseItem.get(0).getItemType() == 1) {//道具存在，并且是偷窃类道具
                            MBaseItem item = listBaseItem.get(0);
                            /*查询用户道具是否存在*/
                            Map<String, Object> mapQueryUserItem = new HashMap<String, Object>();
                            mapQueryUserItem.put("userStrId", userStrId);
                            mapQueryUserItem.put("itemStrId", itemStrId);
                            List<MUserItem> userItemList = userItemDao.selectUserItemList(mapQueryUserItem);
                            if (userItemList.size() > 0) {//用户有此道具
                                MUserItem userItem = userItemList.get(0);
                                /*用户道具使用次数是否足够*/
                                if (userItem.getItemUseCount() > 0) {//有剩余可使用次数
                                    double theftVad = 0.0;//偷窃到的VAD
                                    /*执行道具效果函数*/
                                    if ("1".equals(itemStrId)
                                            || "2".equals(itemStrId)
                                            || "3".equals(itemStrId)
                                            || "4".equals(itemStrId)
                                            || "5".equals(itemStrId)) {//小汽车
                                        theftVad = car(item, userStrId, userItem, itemStrId, scenicSpotStrId, user);
                                    } else if ("6".equals(itemStrId)
                                            || "7".equals(itemStrId)
                                            || "8".equals(itemStrId)
                                            || "9".equals(itemStrId)
                                            || "10".equals(itemStrId)) {//飞行器
                                        theftVad = aerobat(item, userStrId, userItem, itemStrId, user);
                                    } else if ("11".equals(itemStrId)
                                            || "12".equals(itemStrId)
                                            || "13".equals(itemStrId)
                                            || "14".equals(itemStrId)
                                            || "15".equals(itemStrId)) {//万能钥匙
                                        theftVad = okKey(item, userStrId, userItem, itemStrId, user);
                                    }
                                    if (theftVad > 0) {//偷窃数量大于0才减少道具使用次数
                                        /*修改用户道具使用次数*/
                                        userItem.setItemUseCount(userItem.getItemUseCount() - 1);
                                        userItemDao.updateUserItem(userItem);
                                    }
                                    jData.put("theftVad", theftVad);//返回偷窃到的VAD数量
                                    //增加用户道具使用历史-2019-10-23
                                    MUserItemUseHistory itemUseHistory = new MUserItemUseHistory();
                                    itemUseHistory.setStrId(Utils.getUUID());
                                    itemUseHistory.setItemStrId(itemStrId);
                                    itemUseHistory.setUseUserStrId(userStrId);//使用者Id
                                    itemUseHistory.setBeUsedUserStrId("");//被偷窃者
                                    itemUseHistory.setUseDesc("偷窃到" + theftVad);
                                    itemUseHistory.setAmount(theftVad);
                                    itemUseHistory.setStatus(1);
                                    itemUseHistory.setHistoryType(1);//1=盗窃，2=被盗窃',
                                    itemUseHistory.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                                    itemUseHistory.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                                    userItemDao.insertUserItemUseHistory(itemUseHistory);
                                } else {//没有可使用的次数
                                    result = "-39";
                                    message = "Item use count is not enough!";
                                }
                            } else {//用户没有此道具
                                result = "-38";
                                message = "User have not this item!";
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
	
	/**
	 * 小汽车
	 */
	private double car(MBaseItem item,String thefUserStrId,MUserItem userItem,String itemId,String scenicSpotStrId,MUser user) {
		double theftVad = 0.0;//偷窃到的VAD
		//随机100-150  2019-11-4修改
		Random rand = new Random();
		int randCount = rand.nextInt(50) + 100;
		double theftAmount = (item.getItemPrice() / 5) * (randCount * 0.01);//道具每次的使用价格 * 100%到150%
		//查询今天红包池剩余金额是否足够
		Map<String,Object> mapQueryLuckyMoneyPools = new HashMap<String,Object>();
		mapQueryLuckyMoneyPools.put("poolDate",Utils.getNowDateStr2());
		List<MLuckyMoneyPoolDay> listLuckyMoneyPool = userItemDao.selectLuckyMoneyPoolDayList(mapQueryLuckyMoneyPools);
		if(listLuckyMoneyPool.size() > 0) {
			MLuckyMoneyPoolDay pool = listLuckyMoneyPool.get(0);
			if(pool.getPoolAmout() >= theftAmount && pool.getCount() > 0 ) {//红包池金额足够 并且红包个数大于0
				theftVad = theftAmount;
				//减少红包池金额
				pool.setPoolAmout(pool.getPoolAmout() - theftAmount);
				userItemDao.updateLuckyMoneyPoolDay(pool);
				//增加偷窃者的VAD
				user.setVad(user.getVad() + theftVad);
				userItemDao.updateUser(user);
				//修改当天红包池个数
				pool.setCount(pool.getCount() - 1);
				userItemDao.updateLuckyMoneyPoolDay(pool);
			}
		}
		return theftVad;
	}

	/**
	 * 飞行器
	 * @param item
	 * @param thefUserStrId
	 * @param userItem
	 * @return
	 */
	private double aerobat(MBaseItem item,String thefUserStrId,MUserItem userItem,String itemId,MUser user) {
		double theftVad = 0.0;//偷窃到的VAD
		//随机100-150  2019-11-4修改
		Random rand = new Random();
		int randCount = rand.nextInt(50) + 100;
		double theftAmount = (item.getItemPrice() / 5) * (randCount * 0.01);//道具每次的使用价格 * 100%到150%
		//查询今天红包池剩余金额是否足够
		Map<String,Object> mapQueryLuckyMoneyPools = new HashMap<String,Object>();
		mapQueryLuckyMoneyPools.put("poolDate",Utils.getNowDateStr2());
		List<MLuckyMoneyPoolDay> listLuckyMoneyPool = userItemDao.selectLuckyMoneyPoolDayList(mapQueryLuckyMoneyPools);
		if(listLuckyMoneyPool.size() > 0) {
			MLuckyMoneyPoolDay pool = listLuckyMoneyPool.get(0);
			if(pool.getPoolAmout() >= theftAmount && pool.getCount() > 0 ) {//红包池金额足够 并且红包个数大于0
				theftVad = theftAmount;
				//减少红包池金额
				pool.setPoolAmout(pool.getPoolAmout() - theftAmount);
				userItemDao.updateLuckyMoneyPoolDay(pool);
				//增加偷窃者的VAD
				user.setVad(user.getVad() + theftVad);
				userItemDao.updateUser(user);
				//修改当天红包池个数
				pool.setCount(pool.getCount() - 1);
				userItemDao.updateLuckyMoneyPoolDay(pool);
			}
		}
		return theftVad;
	}
	
	/**
	 * 万能钥匙
	 * @param item
	 * @param thefUserStrId
	 * @param userItem
	 * @return
	 */
	private double okKey(MBaseItem item,String thefUserStrId,MUserItem userItem,String itemId,MUser user) {
		double theftVad = 0.0;//偷窃到的VAD
		//随机100-150  2019-11-4修改
		Random rand = new Random();
		int randCount = rand.nextInt(50) + 100;
		double theftAmount = (item.getItemPrice() / 5) * (randCount * 0.01);//道具每次的使用价格 * 100%到150%
		//查询今天红包池剩余金额是否足够
		Map<String,Object> mapQueryLuckyMoneyPools = new HashMap<String,Object>();
		mapQueryLuckyMoneyPools.put("poolDate",Utils.getNowDateStr2());
		List<MLuckyMoneyPoolDay> listLuckyMoneyPool = userItemDao.selectLuckyMoneyPoolDayList(mapQueryLuckyMoneyPools);
		if(listLuckyMoneyPool.size() > 0) {
			MLuckyMoneyPoolDay pool = listLuckyMoneyPool.get(0);
			if(pool.getPoolAmout() >= theftAmount && pool.getCount() > 0 ) {//红包池金额足够 并且红包个数大于0
				theftVad = theftAmount;
				//减少红包池金额
				pool.setPoolAmout(pool.getPoolAmout() - theftAmount);
				userItemDao.updateLuckyMoneyPoolDay(pool);
				//增加偷窃者的VAD
				user.setVad(user.getVad() + theftVad);
				userItemDao.updateUser(user);
				//修改当天红包池个数
				pool.setCount(pool.getCount() - 1);
				userItemDao.updateLuckyMoneyPoolDay(pool);
			}
		}
		return theftVad;
	}
}
