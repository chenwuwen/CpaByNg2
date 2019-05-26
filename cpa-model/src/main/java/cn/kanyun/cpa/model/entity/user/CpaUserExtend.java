package cn.kanyun.cpa.model.entity.user;


import cn.kanyun.cpa.model.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CpaUserExtend extends BaseEntity {
    private Long id;
    private CpaUser cpaUser;
    private Long inviteUser;
    private String shareChain;
    private String shareQrUrl;
    private Integer reapSignInDay;
    /**
     * 此时间是创建分享链接的时间
     */
    private LocalDateTime createDate;

    public CpaUserExtend() {
    }

    public CpaUserExtend(Long id, CpaUser cpaUser,Long inviteUser, String shareChain, String shareQrUrl, Integer reapSignInDay, LocalDateTime createDate) {
        this.id = id;
        this.cpaUser = cpaUser;
        this.inviteUser = inviteUser;
        this.shareChain = shareChain;
        this.shareQrUrl = shareQrUrl;
        this.reapSignInDay = reapSignInDay;
        this.createDate = createDate;
    }



}
