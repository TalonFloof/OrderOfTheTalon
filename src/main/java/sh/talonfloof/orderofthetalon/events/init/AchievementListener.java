package sh.talonfloof.orderofthetalon.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.achievement.Achievement;
import net.modificationstation.stationapi.api.event.achievement.AchievementRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class AchievementListener {

    public static Achievement achievement;
    public static Achievement achievement2;

    @Entrypoint.Namespace
    private Namespace namespace;

    @EventListener
    public void registerAchievements(AchievementRegisterEvent event) {

    }
}
