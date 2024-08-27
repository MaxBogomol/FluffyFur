package mod.maxbogomol.fluffy_fur.client.model.playerskin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class NanachiTailModel extends TailModel  {
    public ModelPart root, model, tail;

    public NanachiTailModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition model = body.addOrReplaceChild("model", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition tail = model.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-2f, 0, -2f, 4f, 12f, 4f, new CubeDeformation(0)), PartPose.offsetAndRotation(0f, 11f, 0.5f, 0, 0, 0));
        PartDefinition tail_layer = tail.addOrReplaceChild("tail_layer", CubeListBuilder.create().texOffs(16, 0)
                .addBox(-2f, 0, -2f, 4f, 12f, 4f, new CubeDeformation(0.25f)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 32, 32);
    }
}
