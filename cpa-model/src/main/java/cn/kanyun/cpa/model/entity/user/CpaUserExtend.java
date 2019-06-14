package cn.kanyun.cpa.model.entity.user;



import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kanyun
 * 用户扩展表
 */

public class CpaUserExtend implements Serializable {
    private Long id;
    /**
     * 用户(外键)
     */
    private CpaUser cpaUser;
    /**
     * 邀请人ID
     */
    private Long inviteUser;
    /**
     * 邀请链接
     */
    private String shareChain;
    /**
     * 邀请链接二维码图片地址
     */
    private String shareQrUrl;
    /**
     * 连续打卡天数
     */
    private Integer seriesSignInDay;

    /**
     * 签到总天数
     */
    private Integer totalSignInDay;
    /**
     * 此时间是创建分享链接的时间
     */
    private LocalDateTime createDate;

    public CpaUserExtend() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CpaUser getCpaUser() {
        return cpaUser;
    }

    public void setCpaUser(CpaUser cpaUser) {
        this.cpaUser = cpaUser;
    }

    public Long getInviteUser() {
        return inviteUser;
    }

    public void setInviteUser(Long inviteUser) {
        this.inviteUser = inviteUser;
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

    public Integer getSeriesSignInDay() {
        return seriesSignInDay;
    }

    public void setSeriesSignInDay(Integer seriesSignInDay) {
        this.seriesSignInDay = seriesSignInDay;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Integer getTotalSignInDay() {
        return totalSignInDay;
    }

    public void setTotalSignInDay(Integer totalSignInDay) {
        this.totalSignInDay = totalSignInDay;
    }
}
