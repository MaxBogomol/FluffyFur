package mod.maxbogomol.fluffy_fur.registry.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.shader.VertexAttributeHandler;
import mod.maxbogomol.fluffy_fur.client.shader.VertexAttributeHolder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FluffyFurVertexFormats {
    public static final VertexFormatElement UV_CAP_DISTORTED = new VertexFormatElement(0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 4);
    public static final VertexFormatElement TIME_DISTORTED = new VertexFormatElement(1, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 1);
    public static final VertexFormatElement TIME_OFFSET_DISTORTED = new VertexFormatElement(2, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 1);
    public static final VertexFormatElement AMPLIFIER_DISTORTED = new VertexFormatElement(3, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 1);
    public static final VertexFormatElement OFFSET_DISTORTED = new VertexFormatElement(4, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 1);

    public static final VertexFormat TRANSLUCENT = new VertexFormat(ImmutableMap.<String, VertexFormatElement>builder()
            .put("Position", DefaultVertexFormat.ELEMENT_POSITION).put("Color", DefaultVertexFormat.ELEMENT_COLOR).put("UV2", DefaultVertexFormat.ELEMENT_UV2).build());

    public static final VertexFormat ADDITIVE_DISTORTED = new VertexFormat(ImmutableMap.<String, VertexFormatElement>builder()
            .put("Position", DefaultVertexFormat.ELEMENT_POSITION).put("UV0", DefaultVertexFormat.ELEMENT_UV0).put("Color", DefaultVertexFormat.ELEMENT_COLOR)
            .put("UVCap", UV_CAP_DISTORTED).put("Time", TIME_DISTORTED).put("TimeOffset", TIME_OFFSET_DISTORTED).put("Amplifier", AMPLIFIER_DISTORTED).put("Offset", OFFSET_DISTORTED).build());

    public static final VertexFormat TRANSLUCENT_DISTORTED = new VertexFormat(ImmutableMap.<String, VertexFormatElement>builder()
            .put("Position", DefaultVertexFormat.ELEMENT_POSITION).put("UV0", DefaultVertexFormat.ELEMENT_UV0).put("Color", DefaultVertexFormat.ELEMENT_COLOR).put("UV2", DefaultVertexFormat.ELEMENT_UV2)
            .put("UVCap", UV_CAP_DISTORTED).put("Time", TIME_DISTORTED).put("TimeOffset", TIME_OFFSET_DISTORTED).put("Amplifier", AMPLIFIER_DISTORTED).put("Offset", OFFSET_DISTORTED).build());

    public static VertexAttributeHolder.UV UV_CAP_DISTORTED_HOLDER = VertexAttributeHolder.UV.create(0.0f, 0.0f, 1.0f, 1.0f);
    public static VertexAttributeHolder.Float TIME_DISTORTED_HOLDER = VertexAttributeHolder.Float.create(600.0f);
    public static VertexAttributeHolder.Float TIME_OFFSET_DISTORTED_HOLDER = VertexAttributeHolder.Float.create(0.0f);
    public static VertexAttributeHolder.Float AMPLIFIER_DISTORTED_HOLDER = VertexAttributeHolder.Float.create(1.0f);
    public static VertexAttributeHolder.Float OFFSET_DISTORTED_HOLDER = VertexAttributeHolder.Float.create(0.02f);

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerVertexFormats(FMLClientSetupEvent event) {
            VertexAttributeHandler.register(UV_CAP_DISTORTED, UV_CAP_DISTORTED_HOLDER);
            VertexAttributeHandler.register(TIME_DISTORTED, TIME_DISTORTED_HOLDER);
            VertexAttributeHandler.register(TIME_OFFSET_DISTORTED, TIME_OFFSET_DISTORTED_HOLDER);
            VertexAttributeHandler.register(AMPLIFIER_DISTORTED, AMPLIFIER_DISTORTED_HOLDER);
            VertexAttributeHandler.register(OFFSET_DISTORTED, OFFSET_DISTORTED_HOLDER);
        }
    }
}
