package cn.kanyun.cpa.controller.user;

import cn.kanyun.cpa.model.entity.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.user.IUserCollectService;
import cn.kanyun.cpa.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by KANYUN on 2017/10/24.
 */
@Controller
@RequestMapping("/api/usercollect")
public class UserCollectController {

    private static final Logger logger = LoggerFactory.getLogger(UserCollectController.class);

    @Resource(name = IUserCollectService.SERVICE_NAME)
    private IUserCollectService userCollectService;

    /**
     * @Author: kanyun
     * @Description: 试题收藏或取消收藏
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @RequestMapping("/toggleCollect/{reId}")
    @ResponseBody
    public CpaResult toggleCollect(@PathVariable("reId")Integer reId, HttpServletRequest request) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            if (null == user){
                result.setStatus(CpaConstants.USER_NOT_LOGIN);
            }
            result.setData(userCollectService.toggleUserCollect(reId, user));
        } catch (Exception e) {
            logger.error("Error : /api/usercollect/toggleCollect " + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }
}
