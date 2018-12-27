package cn.kanyun.cpa.controller.user;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.user.UserCollectService;
import cn.kanyun.cpa.util.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by KANYUN on 2017/10/24.
 */
@Api(value = "/api/userCollect",tags = "用户收藏模块")
@Controller
@RequestMapping("/api/userCollect")
public class UserCollectController {

    private static final Logger logger = LoggerFactory.getLogger(UserCollectController.class);

    @Resource(name = UserCollectService.SERVICE_NAME)
    private UserCollectService userCollectService;

    /**
     * @Author: kanyun
     * @Description: 试题收藏或取消收藏
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @ApiOperation(value = "/toggleCollect/{reId}",notes = "试题收藏或取消收藏",httpMethod = "GET",response = CpaResult.class,produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reId",value = "试题ID",paramType = "path",dataType = "Integer")
    })
    @RequestMapping("/toggleCollect/{reId}")
    @ResponseBody
    public CpaResult toggleCollect(@PathVariable("reId")Long reId, HttpServletRequest request) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            result.setData(userCollectService.toggleUserCollect(reId, user));
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("Error : /api/usercollect/toggleCollect " + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }
}
