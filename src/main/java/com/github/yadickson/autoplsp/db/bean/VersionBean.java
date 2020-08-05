/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yadickson.autoplsp.db.bean;

import java.io.Serializable;

/**
 *
 * @author Yadickson Soto
 */
public final class VersionBean implements Serializable {

    static final long serialVersionUID = 1;

    private String version;

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param pversion the version to set
     */
    public void setVersion(String pversion) {
        this.version = pversion;
    }
}
