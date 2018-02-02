package cn.kanyun.cpa.model.entity.user;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cpa_user_extend", schema = "cpa", catalog = "")
public class CpaUserExtend implements java.io.Serializable {
    private long id;
    private CpaUser cpaUser;
    private String shareChain;
    private String shareQrUrl;
    private Integer reapSignDay;
    private LocalDateTime createDate;

    public CpaUserExtend() {
    }

    public CpaUserExtend(long id, CpaUser cpaUser, String shareChain, String shareQrUrl, Integer reapSignDay, LocalDateTime createDate) {
        this.id = id;
        this.cpaUser = cpaUser;
        this.shareChain = shareChain;
        this.shareQrUrl = shareQrUrl;
        this.reapSignDay = reapSignDay;
        this.createDate = createDate;
    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "user_id")
    public CpaUser getCpaUser() {
        return cpaUser;
    }

    public void setCpaUser(CpaUser cpaUser) {
        this.cpaUser = cpaUser;
    }

    @Basic
    @Column(name = "share_chain")
    public String getShareChain() {
        return shareChain;
    }

    public void setShareChain(String shareChain) {
        this.shareChain = shareChain;
    }

    @Basic
    @Column(name = "share_qr_url")
    public String getShareQrUrl() {
        return shareQrUrl;
    }

    public void setShareQrUrl(String shareQrUrl) {
        this.shareQrUrl = shareQrUrl;
    }

    @Basic
    @Column(name = "reap_sign_day")
    public Integer getReapSignDay() {
        return reapSignDay;
    }

    public void setReapSignDay(Integer reapSignDay) {
        this.reapSignDay = reapSignDay;
    }

    @Basic
    @Column(name = "create_date")
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }


}
