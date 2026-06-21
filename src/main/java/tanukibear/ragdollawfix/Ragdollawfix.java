package tanukibear.ragdollawfix;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Ragdollawfix.MODID)
public class Ragdollawfix {
    public static final String MODID = "ragdollawfix";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Ragdollawfix(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.debug("Loaded Ragdoll Armourer's Workshop compatibility fix");
    }
}
