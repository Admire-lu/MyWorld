package com.cly.mineworld.service.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.GlobalStatic;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserRechargeDao;
import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.entity.MUserProfit;
import com.cly.mineworld.service.entity.MUserRechargeOrder;
import com.cly.mineworld.service.entity.MUserTeam;
import com.cly.mineworld.service.service.UserRechargeService;
import com.cly.mineworld.service.vo.userRecharge.TokenVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class UserRechargeServiceImpl implements UserRechargeService {

//	@Autowired
//	private UserRechargeDao userRechargeDao;
//
//	@Override
//	public void userRechargeTimerTask() {
//		String url = "https://api.etherscan.io/api";
//		//https://api.etherscan.io/api?module=account&action=txlist&address=0xaa19bd14832f0b2221f8c991dc0ae9f2298c5679&startblock=0&endblock=99999999&page=1&offset=100&sort=asc&apikey=YourApiKeyToken
//		Map<String,String> param = new HashMap<String,String>();
//		param.put("module", "account");
//		param.put("action", "txlist");
//		param.put("address", GlobalStatic.PLATFORM_TOKEN_ADDRESS);
//		param.put("startblock", "0");
//		param.put("endblock", "99999999");
//		param.put("page", "1");
//		param.put("offset", "100");
//		param.put("sort", "asc");
//		param.put("apikey", "YourApiKeyToken");
//		String resultStr = Utils.doGet(url, param);
//		if(!"".equals(resultStr)) {
//			userRecharge(resultStr);
//		}
//	}
//	
//	/**
//	 * 用户充值
//	 * @param jsonData
//	 */
//	private void userRecharge(String jsonData) {
//		if(null != jsonData && !"".equals(jsonData)) {
//			JSONObject jso = JSONObject.fromObject(jsonData);
//			if("1".equals(jso.getString("status"))) {
//				JSONArray results = jso.getJSONArray("result");
//				for(int i=0;i<results.size();i++) {
//					TokenVo tokenVo = (TokenVo)JSONObject.toBean(results.getJSONObject(i), TokenVo.class);
//					if(GlobalStatic.PLATFORM_TOKEN_ADDRESS.equals(tokenVo.getTo())) {//校验是否转账给平台主Token账号的订单
//						/*校验订单转账人是否游戏注册用户*/
//						Map<String,Object> mapQueryUser = new HashMap<String,Object>();
//						mapQueryUser.put("tokenAddress", tokenVo.getFrom());//转账人token地址
//						List<MUser> listUser = userRechargeDao.selectUserList(mapQueryUser);
//						if(listUser.size() > 0) {//游戏注册用户
//							MUser user = listUser.get(0);
//							/*校验Token订单id是否已经存在*/
//							Map<String,Object> mapQueryUserRechargeOrderCount = new HashMap<String,Object>();
//							mapQueryUserRechargeOrderCount.put("rechargeTokenOrderId", tokenVo.getHash());
//							int orderCount = userRechargeDao.selectUserRechargeOrderCount(mapQueryUserRechargeOrderCount);
//							if(orderCount < 1) {
//								double vad = (Double.parseDouble(tokenVo.getValue()) * 0.00000000000000001) * 7;//token转VAD
//								/*新增充值订单*/
//								MUserRechargeOrder order = new MUserRechargeOrder();
//								order.setCreateTime(new Long(Utils.getTimestamp()).intValue());
//								order.setModifyTime(new Long(Utils.getTimestamp()).intValue());
//								order.setRechargeAmount(Utils.formatDoubleForDouble(vad));
//								double fm = Double.parseDouble(tokenVo.getValue()) * 0.00000000000000001;
//								order.setRechargeTokenOrderAmount(Utils.formatDoubleForDouble(fm));
//								order.setRechargeTokenOrderId(tokenVo.getHash());
//								order.setRechargeTokenOrderTimestamp(Integer.parseInt(tokenVo.getTimeStamp()));
//								order.setRechargeTokenType(1);//USDT
//								order.setRechargeType(1);//VAD
//								order.setStatus(1);
//								order.setStrId(Utils.getUUID());
//								order.setUserStrId(user.getStrId());
//								order.setUserTokenAddress(tokenVo.getFrom());
//								userRechargeDao.insertUserRechargeOrder(order);
////								/*新增收益记录*/
////								MUserProfit userProfit = new MUserProfit();
////								userProfit.setAfterAmount(Utils.formatDoubleForDouble(user.getVad() + vad));
////								userProfit.setBeforeAmount(user.getVad());
////								userProfit.setCreateTime(new Long(Utils.getTimestamp()).intValue());
////								userProfit.setModifyTime(new Long(Utils.getTimestamp()).intValue());
////								userProfit.setProfitAmount(Utils.formatDoubleForDouble(vad));
////								userProfit.setProfitCategory("5");//用户充值
////								userProfit.setProfitRemarks("充值");
////								userProfit.setStatus(1);
////								userProfit.setStrId(Utils.getUUID());
////								userProfit.setUserStrId(user.getStrId());
////								userRechargeDao.insertUserProfit(userProfit);//新增收益
//								/*修改增加用户VAD数量*/
//								Map<String,Object> mapModifyUser = new HashMap<String,Object>();
//								mapModifyUser.put("userStrId", user.getStrId());
//								mapModifyUser.put("vad", Utils.formatDoubleForDouble(user.getVad() + vad));
//								userRechargeDao.updateUser(mapModifyUser);
//								/*用户团队*/
//								/*校验此用户是否有团队（除去自己团队）*/
//								List<MUserTeam> listUserTeam = userRechargeDao.selectUserTeamByUserStrId(user.getStrId());
//								if(listUserTeam.size() > 0) {//已经加入团队
//									MUserTeam userTeam = listUserTeam.get(0);
//									Map<String,Object> mapModifyUserTeam = new HashMap<String,Object>();
//									/*白银等级：团队充值金额>=20000.00*/
//									if(userTeam.getTeamRechargeAmount() + vad >= 20000.00) {
//										mapModifyUserTeam.put("teamLv", 2);//白银等级
//									}
//									/*团队升级*/
//									Map<String,Object> mapCheckUserTeamLv = new HashMap<String,Object>();//检查用户团队等级
//									mapCheckUserTeamLv.put("teamLv", 2);
//									mapCheckUserTeamLv.put("memberStrId", user.getStrId());
//									int countLv = userRechargeDao.checkUserTeamLv(mapCheckUserTeamLv);
//									if(countLv > 2) {//团队内有3个用户的团队为白银级别，本身则升级为黄金级别
//										mapModifyUserTeam.put("teamLv", 3);//黄金等级
//										mapCheckUserTeamLv.put("teamLv", 3);
//										countLv = userRechargeDao.checkUserTeamLv(mapCheckUserTeamLv);
//										if(countLv > 2) {//团队内有3个用户的团队为黄金级别，本身则升级为白金级别
//											mapModifyUserTeam.put("teamLv", 4);//白金等级
//											mapCheckUserTeamLv.put("teamLv", 4);
//											countLv = userRechargeDao.checkUserTeamLv(mapCheckUserTeamLv);
//											if(countLv > 2) {//团队内有3个用户的团队为白金级别，本身则升级为钻石级别
//												mapModifyUserTeam.put("teamLv", 5);//钻石等级
//												mapCheckUserTeamLv.put("teamLv", 5);
//												countLv = userRechargeDao.checkUserTeamLv(mapCheckUserTeamLv);
//												if(countLv > 2) {//团队内有3个用户的团队为钻石级别，本身则升级为王者级别
//													mapModifyUserTeam.put("teamLv", 6);//王者等级
//													mapCheckUserTeamLv.put("teamLv", 6);
//												}
//											}
//										}
//									}
//									/*修改增加团队总充值金额*/
//									mapModifyUserTeam.put("userTeamStrId", userTeam.getStrId());
//									mapModifyUserTeam.put("teamRechargeAmount", userTeam.getTeamRechargeAmount() + vad);
//									userRechargeDao.updateUserTeam(mapModifyUserTeam);
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//	}
}
