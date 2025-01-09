package mod.maxbogomol.fluffy_fur.mixin.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ForgeHooks.class)
public class ForgeHooksMixin {

    @Inject(method = "loadLootTable", at = @At("HEAD"), cancellable = true, remap = false)
    private static void fluffy_fur$loadLootTable(Gson gson, ResourceLocation name, JsonElement data, boolean custom, CallbackInfoReturnable<LootTable> cir) {
        JsonObject json = data.getAsJsonObject();
        if (json.has(FluffyFur.MOD_ID + ":conditions")) {
            if (!CraftingHelper.processConditions(GsonHelper.getAsJsonArray(json, FluffyFur.MOD_ID + ":conditions"), ICondition.IContext.EMPTY)) {
                cir.setReturnValue(null);
            }
        }
    }
}