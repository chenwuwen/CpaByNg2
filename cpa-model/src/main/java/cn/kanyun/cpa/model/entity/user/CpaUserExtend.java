package cn.kanyun.cpa.model.entity.user;

import java.time.LocalDateTime;


public class CpaUserExtend implements java.io.Serializable {
    private long id;
    private CpaUser cpaUser;
    private String shareChain;
    private String shareQrUrl;
    private Integer reapSignInDay;
    private LocalDateTime createDate;

    public CpaUserExtend() {
    }

    public CpaUserExtend(long id, CpaUser cpaUser, String shareChain, String shareQrUrl, Integer reapSignInDay, LocalDateTime createDate) {
        this.id = id;
        this.cpaUser = cpaUser;
        this.shareChain = shareChain;
        this.shareQrUrl = shareQrUrl;
        this.reapSignInDay = reapSignInDay;
        this.createDate = createDate;
    }


    public long getId() {
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


}
