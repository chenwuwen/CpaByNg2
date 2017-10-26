package cn.kanyun.cpa.controller.user;

import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.Page;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.service.user.IUserCollectService;
import cn.kanyun.cpa.service.user.IUserCommentService;
import cn.kanyun.cpa.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * Created by KANYUN on 2017/10/24.
 */
@Controller
@RequestMapping("/api/usercomment")
public class UserCommentController {

    private static final Logger logger = LoggerFactory.getLogger(UserCommentController.class);

    @Resource(name = IUserCommentService.SERVICE_NAME)
    private IUserCommentService userCommentService;

    /**
     * @Author: kanyun
     * @Description: 保存用户评论
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @RequestMapping("/saveComment")
    @ResponseBody
    public Integer saveComment(@RequestBody UserComment userComment, HttpServletRequest request) {
        Integer k = 0;
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            userComment.setCommentDate(new Timestamp(System.currentTimeMillis()));
            userComment.setUserId(user.getId());
            k = userCommentService.save(userComment);
        } catch (Exception e) {
            logger.error("Error : /api/usercomment/saveComment " + e);
        }
        return k;
    }

    /**
     * @Author: kanyun
     * @Description: 获取用户评论
     * @Date: 2017/8/16 17:02
     * @params:
     */
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
}
