package com.cly.mineworld.service.service.impl;

import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcSettingDao;
import com.cly.mineworld.service.entity.MUserFeedback;
import com.cly.mineworld.service.service.UserUcSettingService;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


/**
 * 用户端-用户个人中心-设置-调度服务
 *
 * @author william
 */
@Service
@Transactional
public class UserUcSettingServiceImpl implements UserUcSettingService {

    @Autowired
    private UserUcSettingDao ucSettingDao;


    /**
     * 意见反馈
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo addFeedback(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21",
        // "feedbackTitle":"开服第一天","feedbackType":"1","feedbackContent":"欢迎来到我的世界"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String feedbackTitle = data.getString("feedbackTitle");
            String feedbackType = data.getString("feedbackType");
            String feedbackContent = data.getString("feedbackContent");
            if (null != userStrId && !"".equals(userStrId)
                    && null != feedbackTitle && !"".equals(feedbackTitle)
                    && null != feedbackType && !"".equals(feedbackType)
                    && null != feedbackContent && !"".equals(feedbackContent)) {
                MUserFeedback userFeedback = new MUserFeedback();
                userFeedback.setStrId(Utils.getUUID());
                userFeedback.setUserStrId(userStrId);
                userFeedback.setFeedbackTitle(feedbackTitle);
                userFeedback.setFeedbackContent(feedbackContent);
                userFeedback.setFeedbackType(Integer.valueOf(feedbackType));
                userFeedback.setReplyContent("");
                userFeedback.setManagerReadStatus(2);
                userFeedback.setReplyManagerStrId("");
                userFeedback.setReplyStatus(2);
                userFeedback.setStatus(1);
                userFeedback.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                userFeedback.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                ucSettingDao.insertFeedback(userFeedback);
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
