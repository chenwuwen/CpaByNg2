package cn.kanyun.cpa.controller.user;

import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.service.user.IUserCollectService;
import cn.kanyun.cpa.service.user.IUserCommentService;
import cn.kanyun.cpa.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
    public Integer saveComment(Integer reId, String comment, HttpServletRequest request) {
        Integer k = 1;
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            UserComment userComment = new UserComment();
            userComment.setComment(comment);
            userComment.setReId(reId);
            userComment.setCommentDate(new Timestamp(System.currentTimeMillis()));
            userCommentService.save(userComment);
        } catch (Exception e) {
            logger.error("Error : /api/usercomment/saveComment " + e);
            k = 0;
        }
        return k;
    }
}
