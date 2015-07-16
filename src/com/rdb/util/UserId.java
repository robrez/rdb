package com.rdb.util;

import java.io.Serializable;

/**
 *
 * @author rdb generated entity
 */
public class UserId implements Serializable {

    private String useridEmail;
    private String useridName;
    private Integer useridUid;

    public void setUseridEmail(String useridEmail) {
        this.useridEmail = useridEmail;
    }

    public String getUseridEmail() {
        return useridEmail;
    }

    public void setUseridName(String useridName) {
        this.useridName = useridName;
    }

    public String getUseridName() {
        return useridName;
    }

    public void setUseridUid(Integer useridUid) {
        this.useridUid = useridUid;
    }

    public Integer getUseridUid() {
        return useridUid;
    }
}