package cn.kanyun.cpa.model.entity.user;

import cn.kanyun.cpa.model.entity.BaseEntity;

import java.time.LocalDateTime;


public class CpaUserExtend extends BaseEntity {
    private Long id;
    private CpaUser cpaUser;
    private Long inviteUser;
    private String shareChain;
    private String shareQrUrl;
    private Integer reapSignInDay;
    private LocalDateTime createDate; //此时间是创建分享链接的时间

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


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public CpaUser getCpaUser() {
        return cpaUser;
    }

    public void setCpaUser(CpaUser cpaUser) {
        this.cpaUser = cpaUser;
    }


    public String getShareChain() {
        return shareChain;
    }

    public void setShareChain(String shareChain) {
        this.shareChain = shareChain;
    }


    public String getShareQrUrl() {
        return shareQrUrl;
    }

    public void setShareQrUrl(String shareQrUrl) {
        this.shareQrUrl = shareQrUrl;
    }


    public Integer getReapSignInDay() {
        return reapSignInDay;
    }

    public void setReapSignInDay(Integer reapSignDay) {
        this.reapSignInDay = reapSignInDay;
    }


    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getInviteUser() {
        return inviteUser;
    }

    public void setInviteUser(Long inviteUser) {
        this.inviteUser = inviteUser;
    }
}
