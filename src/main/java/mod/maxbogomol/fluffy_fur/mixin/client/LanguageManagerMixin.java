package mod.maxbogomol.fluffy_fur.mixin.client;

import com.google.common.collect.ImmutableMap;
import mod.maxbogomol.fluffy_fur.client.language.LanguageHandler;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.server.packs.PackResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Mixin(LanguageManager.class)
public abstract class LanguageManagerMixin {

    @Inject(at = @At("RETURN"), method = "extractLanguages", cancellable = true)
    private static void fluffy_fur$extractLanguages(Stream<PackResources> packResources, CallbackInfoReturnable<Map<String, LanguageInfo>> cir) {
        Map<String, LanguageInfo> map = new HashMap<>(cir.getReturnValue());
        map.putAll(LanguageHandler.getLanguages());
        cir.setReturnValue(ImmutableMap.copyOf(map));
    }
}
