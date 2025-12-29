package mod.maxbogomol.fluffy_fur.common.network.spectator;

import mod.maxbogomol.fluffy_fur.client.spectator.SpectatorDimensionHandler;
import mod.maxbogomol.fluffy_fur.common.network.ClientNBTPacket;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SpectatorClientDimensionsPacket extends ClientNBTPacket {

    public SpectatorClientDimensionsPacket(CompoundTag data) {
        super(data);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        SpectatorDimensionHandler.levelDimensions.clear();
        SpectatorDimensionHandler.levelDimensionSaves.clear();
        if (nbt.contains("dimensions")) {
            ListTag dimensions = nbt.getList("dimensions", Tag.TAG_COMPOUND);
            for (int i = 0; i < dimensions.size(); i++) {
                CompoundTag dimensionTag = dimensions.getCompound(i);
                ResourceKey<Level> dimension = null;
                boolean save = false;

                if (dimensionTag.contains("name")) {
                    dimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(dimensionTag.getString("name")));
                }
                if (dimensionTag.contains("save")) {
                    save = dimensionTag.getBoolean("save");
                }

                if (dimension != null) {
                    SpectatorDimensionHandler.levelDimensions.add(dimension);
                    SpectatorDimensionHandler.levelDimensionSaves.put(dimension, save);
                }
            }
        }
        SpectatorDimensionHandler.sortDimensions();
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SpectatorClientDimensionsPacket.class, SpectatorClientDimensionsPacket::encode, SpectatorClientDimensionsPacket::decode, SpectatorClientDimensionsPacket::handle);
    }

    public static SpectatorClientDimensionsPacket decode(FriendlyByteBuf buf) {
        return decode(SpectatorClientDimensionsPacket::new, buf);
    }
}
