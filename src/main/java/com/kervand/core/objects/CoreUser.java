package com.kervand.core.objects;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CoreUser {

    private final @Getter String name;

    private String ignoreList;

    private @Getter @Setter int lastDailyRewardLevel;
    private @Getter @Setter @Nullable String lastDailyRewardClaimed;

    private @Getter @Setter int reportsConfirmed;

    private @Getter @Setter int localBooster;

    private @Getter @Setter int level;
    private @Getter double xp;

    public CoreUser(String name) {

        this.name = name;
        this.ignoreList = "";

        this.lastDailyRewardClaimed = null;
        this.lastDailyRewardLevel = 0;

        this.reportsConfirmed = 0;
        this.localBooster = 1;
        this.level = 0;
        this.xp = 0.0;

    }

    public CoreUser(String name, String ignoreList, int lastDailyRewardLevel, @Nullable String lastDailyRewardClaimed, int reportsConfirmed, int level, double xp) {

        this.name = name;
        this.ignoreList = ignoreList;

        this.lastDailyRewardLevel = lastDailyRewardLevel;
        this.lastDailyRewardClaimed = lastDailyRewardClaimed;

        this.reportsConfirmed = reportsConfirmed;
        this.localBooster = 1;

        this.level = level;
        this.xp = xp;

    }

    public String getIgnoreListAsString() {
        return ignoreList;
    }

    public Set<String> getIgnoreList() {

        if (ignoreList.isEmpty()) return new HashSet<>();

        return new HashSet<>(Arrays.asList(ignoreList.split(";")));

    }

    public void addIgnore(String user) {

        Set<String> ignore = getIgnoreList();
        if (ignore.contains(user)) return;

        ignoreList += ";" + user;

    }

    public void removeIgnore(String user) {

        Set<String> ignore = getIgnoreList();
        if (!ignore.contains(user)) return;

        ignore.remove(user);
        ignoreList = String.join(";", ignore);

    }

    public void addLevel(int level) {
        this.level += level;
    }

    public void takeLevel(int level) {
        this.level -= level;
    }

    public void addXP(double xp, double inLevel) {

        while (xp >= inLevel) {
            level++;
            xp -= inLevel;
        }

    }

    public void takeXP(double xp) {
        this.xp -= xp;
    }

}
