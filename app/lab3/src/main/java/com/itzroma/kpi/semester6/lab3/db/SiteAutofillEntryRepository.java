package com.itzroma.kpi.semester6.lab3.db;

import com.itzroma.kpi.semester6.lab3.model.SiteAutofillEntry;

import java.util.List;

public interface SiteAutofillEntryRepository {
    void createSiteAutofillEntry(SiteAutofillEntry siteAutofillEntry);

    List<SiteAutofillEntry> getAllSiteAutofillEntries();

    void deleteAllSiteAutofillEntries();

    boolean existsBySiteAndUsername(String site, String username);
}
