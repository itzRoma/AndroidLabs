package com.itzroma.kpi.semester6.lab3.model;

import java.util.Date;

public class SiteAutofillEntry {

    private Long id;
    private String site;
    private String username;
    private String password;
    private Date date;

    public SiteAutofillEntry(Long id, String site, String username, String password, Date date) {
        this.id = id;
        this.site = site;
        this.username = username;
        this.password = password;
        this.date = date;
    }

    public SiteAutofillEntry(String site, String username, String password) {
        this.site = site;
        this.username = username;
        this.password = password;
        date = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getSite() {
        return site;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDate() {
        return date;
    }
}
