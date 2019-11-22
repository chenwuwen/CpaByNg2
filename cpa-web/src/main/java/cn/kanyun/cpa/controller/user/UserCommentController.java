package cn.kanyun.cpa.controller.user;

import cn.kanyun.cpa.elasticsearch.controller.CreateHandler;
import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.Page;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.model.event.CpaEvent;
import cn.kanyun.cpa.model.event.UserCommentEvent;
import cn.kanyun.cpa.service.user.UserCommentService;
import cn.kanyun.cpa.util.WebUtil;
import com.google.common.eventbus.EventBus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 *
 * @author KANYUN
 * @date 2017/10/24
 */
@Api(value = "/api/userComment",tags = "试题评论模块",produces="application/json",consumes="application/json")
@Controller
@RequestMapping("/api/userComment")
public class UserCommentController {

    private static final Logger logger = LoggerFactory.getLogger(UserCommentController.class);

    @Resource(name = UserCommentService.SERVICE_NAME)
    private UserCommentService userCommentService;

    @Autowired
    private CreateHandler createHandler;

    /**
     * @Author: kanyun
     * @Description:
     * 保存用户评论
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @ApiOperation(value = "/saveComment",notes = "保存用户评论",httpMethod = "POST",response = CpaResult.class,produces="application/json")
    @RequestMapping("/saveComment")
    @ResponseBody
    public CpaResult saveComment(@RequestBody UserComment userComment, HttpServletRequest request) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            if (userComment.getComment().isEmpty()) {
                result.setState(CpaConstants.OPERATION_ERROR);
                result.setMsg("评论内容不能为空");
            } else {
                userComment.setCommentDate(LocalDateTime.now());
                userComment.setUserId(user.getId());
                userComment.setUserName(user.getUserName());
                userComment.setNickName(user.getNickName());
                result.setData(userCommentService.save(userComment));
                sendUserCommentEvent(userComment);
            }
        } catch (Exception e) {
            logger.error("Error : /api/userComment/saveComment " + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }



    /**
     * @Author: kanyun
     * @Description:
     * 获取用户评论列表[根据用户查询]
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @ApiOperation(value = "/getUserComment",notes = "获取用户评论列表[根据用户查询]",httpMethod = "GET",response = CpaResult.class,produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",dataType = "int",paramType = "query",example = "1"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量",dataType = "int",paramType = "query",example = "10")
    })
    @RequestMapping("/getUserComment")
    @ResponseBody
    public CpaResult getUserComment(Integer pageNo, Integer pageSize, HttpServletRequest request) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            Page page = new Page();
//            如果pageNo为0，则设置pageNo为1,否则为本身
            pageNo = pageNo == null || pageNo == 0 ? page.getTopPageNo() : pageNo;
            pageSize = pageSize == null || pageSize == 0 ? page.getPageSize() : pageSize;
            Object[] params = {user.getId()};
            String where = "o.userId =?";
            Long totalRecords = userCommentService.getTotalCount(where, params);
            Integer firstResult = page.countOffset(pageNo, pageSize);
            result = userCommentService.getUserComment(firstResult, pageSize, where, params);
        } catch (Exception e) {
            logger.error("Error : /api/usercomment/getUserComment " + e);
        }
        return result;
    }

    /**
     * @Author: kanyun
     * @Description:
     * 获取评论[根据试题查询]
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @ApiOperation(value = "/getItemComment/{reId}",notes = "获取评论[根据试题查询]",response = CpaResult.class,httpMethod = "GET",produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reId",value = "试题ID",required=true,paramType="path"),
            @ApiImplicitParam(name = "pageNo",value = "页码",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量",paramType = "query")
    })
    @RequestMapping("/getItemComment/{reId}")
    @ResponseBody
    public CpaResult getItemComment(@PathVariable("reId") Long reId, Integer pageNo, Integer pageSize) {
        CpaResult result = new CpaResult();
        try {
            Page page = new Page();
//            如果pageNo为0，则设置pageNo为1,否则为本身
            pageNo = pageNo == null || pageNo == 0 ? page.getTopPageNo() : pageNo;
            pageSize = pageSize == null || pageSize == 0 ? page.getPageSize() : pageSize;
            Object[] params = {reId};
            String where = "o.reId =?";
            Long totalRecords = userCommentService.getTotalCount(where, params);
            Integer firstResult = page.countOffset(pageNo, pageSize);
            result = userCommentService.getUserComment(firstResult, pageSize, where, params);
        } catch (Exception e) {
            logger.error("Error : /api/userComment/getUserComment " + e);
        }
        return result;
    }

    /**
     * 发送用户评论数据变化事件
     * @param userComment
     */
    private void sendUserCommentEvent(UserComment userComment) {
        EventBus eventBus = new EventBus();
        eventBus.register(createHandler);
        CpaEvent<UserComment> event = new UserCommentEvent(userComment);
        eventBus.post(event);
    }
}
