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
public final class CountBean implements Serializable {

    static final long serialCountUID = 1;

    private String count;

    /**
     * @return the count
     */
    public String getCount() {
        return count;
    }

    /**
     * @param pcount the count to set
     */
    public void setCount(String pcount) {
        this.count = pcount;
    }
}
