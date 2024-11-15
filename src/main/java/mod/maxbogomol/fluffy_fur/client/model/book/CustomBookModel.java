package mod.maxbogomol.fluffy_fur.client.model.book;

import mod.maxbogomol.fluffy_fur.client.model.block.CustomBlockModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class CustomBookModel extends CustomBlockModel {
    public ModelPart leftLid;
    public ModelPart rightLid;
    public ModelPart leftPages;
    public ModelPart rightPages;
    public ModelPart flipPage1;
    public ModelPart flipPage2;

    public CustomBookModel(ModelPart root) {
        super(root);
        this.leftLid = root.getChild("left_lid");
        this.rightLid = root.getChild("right_lid");
        this.leftPages = root.getChild("left_pages");
        this.rightPages = root.getChild("right_pages");
        this.flipPage1 = root.getChild("flip_page1");
        this.flipPage2 = root.getChild("flip_page2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("left_lid", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -6.5F, 0.0F, 9.0F, 13.0F, 0F), PartPose.offset(0.0F, 0.0F, -2.0F));
        partdefinition.addOrReplaceChild("right_lid", CubeListBuilder.create().texOffs(26, 0).addBox(0.0F, -6.5F, 0.0F, 9.0F, 13.0F, 0F), PartPose.offset(0.0F, 0.0F, 2.0F));
        partdefinition.addOrReplaceChild("seam", CubeListBuilder.create().texOffs(18, 0).addBox(-2.0F, -6.5F, 0.0F, 4.0F, 13.0F, 0F), PartPose.rotation(0.0F, ((float)Math.PI / 2F), 0.0F));
        partdefinition.addOrReplaceChild("left_pages", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -5.5F, -1.99F, 8.0F, 11.0F, 2.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_pages", CubeListBuilder.create().texOffs(20, 13).addBox(0.0F, -5.5F, -0.01F, 8.0F, 11.0F, 2.0F), PartPose.ZERO);
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(40, 13).addBox(0.0F, -5.5F, 0.0F, 8.0F, 11.0F, 0F);
        partdefinition.addOrReplaceChild("flip_page1", cubelistbuilder, PartPose.ZERO);
        partdefinition.addOrReplaceChild("flip_page2", cubelistbuilder, PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void setupAnim(float time, float rightPageFlipAmount, float leftPageFlipAmount, float bookOpenAmount) {
        float f = (Mth.sin(time * 0.02F) * 0.1F + 1.25F) * bookOpenAmount;
        this.leftLid.yRot = (float)Math.PI + f;
        this.rightLid.yRot = -f;
        this.leftPages.yRot = f;
        this.rightPages.yRot = -f;
        this.flipPage1.yRot = f - f * 2.0F * rightPageFlipAmount;
        this.flipPage2.yRot = f - f * 2.0F * leftPageFlipAmount;
        this.leftPages.x = Mth.sin(f) * 2f + 0.01f;
        this.rightPages.x = Mth.sin(f) * 2f + 0.01f;
        this.flipPage1.x = Mth.sin(f) * 2.05f + 0.01f;
        this.flipPage2.x = Mth.sin(f) * 2.05f + 0.01f;
    }
}
