package com.kervand.core.utility;

import com.kervand.core.CorePlugin;
import com.kervand.core.objects.CoreUser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class XpUtility {

    public void addXp(String userName, double xp) {

        CoreUser user = CorePlugin.getInstance().getCoreManager().getOrLoadUser(userName);
        user.addXP(xp, getXpRequired(user.getLevel() + 1));

        CorePlugin.getInstance().getCoreManager().saveUser(user);

    }

    public int getXpRequired(int level) {

        if (level < 3) {
            return 30;
        } else if (level < 7) {
            return 50;
        } else if (level < 10) {
            return 75;
        } else if (level < 15) {
            return 100;
        } else if (level < 20) {
            return 125;
        } else if (level < 25) {
            return 150;
        } else if (level < 30) {
            return 175;
        } else {
            return 225;
        }

    }

}
