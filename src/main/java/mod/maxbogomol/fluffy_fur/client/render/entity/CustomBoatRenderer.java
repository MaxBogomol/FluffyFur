package mod.maxbogomol.fluffy_fur.client.render.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;

public class CustomBoatRenderer extends BoatRenderer {
    private final ResourceLocation boatTexture;
    private ListModel<Boat> boatModel;

    public CustomBoatRenderer(EntityRendererProvider.Context context, ResourceLocation boatTexture, boolean chest, boolean raft) {
        super(context, chest);
        this.boatTexture = boatTexture;
        createModels(chest, raft);
    }

    public CustomBoatRenderer(EntityRendererProvider.Context context, String modId, String texture, boolean chest, boolean raft) {
        super(context, chest);
        String path = chest ? "textures/entity/chest_boat/" + texture + ".png" : "textures/entity/boat/" + texture + ".png";;
        this.boatTexture = new ResourceLocation(modId, path);
        createModels(chest, raft);
    }

    public void createModels(boolean chest, boolean raft) {
        if (raft) {
            this.boatModel = chest ? new ChestRaftModel(ChestRaftModel.createBodyModel().bakeRoot()) : new RaftModel(RaftModel.createBodyModel().bakeRoot());
        } else {
            this.boatModel = chest ? new ChestBoatModel(ChestBoatModel.createBodyModel().bakeRoot()) : new BoatModel(BoatModel.createBodyModel().bakeRoot());
        }
    }

    @Override
    @NotNull
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(@NotNull Boat boat) {
        return Pair.of(boatTexture, boatModel);
    }
}
