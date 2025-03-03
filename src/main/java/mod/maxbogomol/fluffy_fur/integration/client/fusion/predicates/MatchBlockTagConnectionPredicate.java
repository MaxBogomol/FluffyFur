package mod.maxbogomol.fluffy_fur.integration.client.fusion.predicates;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.supermartijn642.fusion.api.predicate.ConnectionDirection;
import com.supermartijn642.fusion.api.predicate.ConnectionPredicate;
import com.supermartijn642.fusion.api.util.Serializer;
import com.supermartijn642.fusion.util.IdentifierUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MatchBlockTagConnectionPredicate implements ConnectionPredicate {

    public static final Serializer<MatchBlockTagConnectionPredicate> SERIALIZER = new Serializer<>() {
        @Override
        public MatchBlockTagConnectionPredicate deserialize(JsonObject json) throws JsonParseException {
            if (!json.has("block") || !json.get("block").isJsonPrimitive() || !json.getAsJsonPrimitive("block").isString())
                throw new JsonParseException("Match block predicate must have string property 'block'!");
            if (!IdentifierUtil.isValidIdentifier(json.get("block").getAsString()))
                throw new JsonParseException("Property 'block' must be a valid identifier!");
            ResourceLocation identifier = new ResourceLocation(json.get("block").getAsString());
            return new MatchBlockTagConnectionPredicate(TagKey.create(Registries.BLOCK, identifier));
        }

        @Override
        public JsonObject serialize(MatchBlockTagConnectionPredicate value) {
            JsonObject json = new JsonObject();
            json.addProperty("block", value.block.toString());
            return json;
        }
    };

    private final TagKey<Block> block;

    public MatchBlockTagConnectionPredicate(TagKey<Block> block) {
        this.block = block;
    }

    @Override
    public boolean shouldConnect(Direction side, @Nullable BlockState ownState, BlockState otherState, BlockState blockInFront, ConnectionDirection direction) {
        return otherState.is(block);
    }

    @Override
    public Serializer<? extends ConnectionPredicate> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchBlockTagConnectionPredicate that)) return false;

        return this.block.equals(that.block);
    }

    @Override
    public int hashCode() {
        return this.block.hashCode();
    }
}