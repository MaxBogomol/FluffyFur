package mod.maxbogomol.fluffy_fur.client.model.playerskin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class RabbitEarsModel extends EarsModel  {

    public RabbitEarsModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition model = head.addOrReplaceChild("model", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition right_ear = model.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.5f, -8f, -1f, 3f, 9f, 2f, new CubeDeformation(0)), PartPose.offsetAndRotation(-2.5f, -8f, 0, 0, (float) (Math.PI * 2f), 0));
        PartDefinition right_ear_layer = right_ear.addOrReplaceChild("right_ear_layer", CubeListBuilder.create().texOffs(0, 11)
                .addBox(-1.5f, -8f, -1f, 3f, 9f, 2f, new CubeDeformation(0.25f)), PartPose.ZERO);

        PartDefinition left_ear = model.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(10, 0)
                .addBox(-1.5f, -8f, -1f, 3f, 9f, 2f, new CubeDeformation(0)), PartPose.offsetAndRotation(2.5f, -8f, 0, 0, (float) (Math.PI * 2f), 0));
        PartDefinition left_ear_layer = left_ear.addOrReplaceChild("left_ear_layer", CubeListBuilder.create().texOffs(10, 11)
                .addBox(-1.5f, -8f, -1f, 3f, 9f, 2f, new CubeDeformation(0.25f)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 32, 32);
    }
}
