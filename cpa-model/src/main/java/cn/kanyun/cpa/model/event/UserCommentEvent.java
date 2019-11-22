package cn.kanyun.cpa.model.event;

import cn.kanyun.cpa.model.entity.user.UserComment;

/**
 * 用户评论改变事件
 */
public class UserCommentEvent extends CpaEvent<UserComment> {
    public UserCommentEvent(UserComment getSource) {
        super(getSource);
    }
}
