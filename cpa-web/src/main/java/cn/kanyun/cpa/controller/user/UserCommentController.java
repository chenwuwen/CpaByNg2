package cn.kanyun.cpa.controller.user;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.Page;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.service.user.UserCommentService;
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
import java.time.LocalDateTime;

/**
 * Created by KANYUN on 2017/10/24.
 */
@Controller
@RequestMapping("/api/usercomment")
public class UserCommentController {

    private static final Logger logger = LoggerFactory.getLogger(UserCommentController.class);

    @Resource(name = UserCommentService.SERVICE_NAME)
    private UserCommentService userCommentService;

    /**
     * @Author: kanyun
     * @Description: 保存用户评论
     * @Date: 2017/8/16 17:02
     * @params:
     */
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
                userComment.setUsername(user.getUserName());
                userComment.setPetname(user.getPetName());
                result.setData(userCommentService.save(userComment));
            }
        } catch (Exception e) {
            logger.error("Error : /api/usercomment/saveComment " + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @Author: kanyun
     * @Description: 获取用户评论
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @RequestMapping("/getUserComment")
    @ResponseBody
    public CpaResult getUserComment(Integer pageNo, Integer pageSize, HttpServletRequest request) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            Page page = new Page();
            pageNo = pageNo == null || pageNo == 0 ? page.getTopPageNo() : pageNo;  //如果pageNo为0，则设置pageNo为1,否则为本身
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
     * @Description: 获取评论
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @RequestMapping("/getItemComment/{reId}")
    @ResponseBody
    public CpaResult getItemComment(@PathVariable("reId") Long reId, Integer pageNo, Integer pageSize) {
        CpaResult result = new CpaResult();
        try {
            Page page = new Page();
            pageNo = pageNo == null || pageNo == 0 ? page.getTopPageNo() : pageNo;  //如果pageNo为0，则设置pageNo为1,否则为本身
            pageSize = pageSize == null || pageSize == 0 ? page.getPageSize() : pageSize;
            Object[] params = {reId};
            String where = "o.reId =?";
            Long totalRecords = userCommentService.getTotalCount(where, params);
            Integer firstResult = page.countOffset(pageNo, pageSize);
            result = userCommentService.getUserComment(firstResult, pageSize, where, params);
        } catch (Exception e) {
            logger.error("Error : /api/usercomment/getUserComment " + e);
        }
        return result;
    }
}
