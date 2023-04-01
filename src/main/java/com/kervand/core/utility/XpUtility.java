package com.kervand.core.utility;

import com.kervand.core.CorePlugin;
import com.kervand.core.objects.CoreUser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class XpUtility {

    public void addXp(String userName, double xp) {

        CoreUser user = CorePlugin.getInstance().getCoreManager().getOrLoadUser(userName);

        if (user.getLevel() < 3) {
            user.addXP(xp, 30);
        } else if (user.getLevel() < 7) {
            user.addXP(xp, 50);
        } else if (user.getLevel() < 10) {
            user.addXP(xp, 75);
        } else if (user.getLevel() < 15) {
            user.addXP(xp, 100);
        } else if (user.getLevel() < 20) {
            user.addXP(xp, 125);
        } else if (user.getLevel() < 25) {
            user.addXP(xp, 150);
        } else if (user.getLevel() < 30) {
            user.addXP(xp, 175);
        } else {
            user.addXP(xp, 225);
        }

        CorePlugin.getInstance().getCoreManager().saveUser(user);

    }

}
