package com.mrbysco.groomba.client.model;

import com.mrbysco.groomba.entity.Groomba;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.phys.Vec3;

public class GroombaModel extends HierarchicalModel<Groomba> {
	private final ModelPart root;
	private final ModelPart wheel1, wheel2, wheel3;

	public GroombaModel(ModelPart root) {
		this.root = root;
		ModelPart wheels = root.getChild("wheels");
		this.wheel1 = wheels.getChild("wheel1");
		this.wheel2 = wheels.getChild("wheel2");
		this.wheel3 = wheels.getChild("wheel3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().create()
						.texOffs(0, 0).addBox(-6.0F, -1.0F, -8.0F, 12.0F, 2.0F, 16.0F)
						.texOffs(16, 20).addBox(-8.0F, -1.0F, -6.0F, 2.0F, 2.0F, 12.0F)
						.texOffs(0, 18).addBox(6.0F, -1.0F, -6.0F, 2.0F, 2.0F, 12.0F),
				PartPose.offset(0.0F, 22.0F, 0.0F));

		PartDefinition wheels = partdefinition.addOrReplaceChild("wheels", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition wheel1 = wheels.addOrReplaceChild("wheel1", CubeListBuilder.create()
						.texOffs(6, 2).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
				PartPose.offset(-5.0F, -1.0F, 5.0F));

		PartDefinition wheel2 = wheels.addOrReplaceChild("wheel2", CubeListBuilder.create()
						.texOffs(0, 4).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
				PartPose.offset(5.0F, -1.0F, 5.0F));

		PartDefinition wheel3 = wheels.addOrReplaceChild("wheel3", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
				PartPose.offset(0.0F, -1.0F, -4.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Groomba groomba, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		Vec3 delta = groomba.getDeltaMovement();
		if (delta.x() != 0 || delta.z() != 0) {
			this.wheel1.xRot = ageInTicks * 0.75F;
			this.wheel2.xRot = ageInTicks * 0.75F;
			this.wheel3.xRot = ageInTicks * 0.75F;
		} else {
			this.wheel1.xRot = 0;
			this.wheel2.xRot = 0;
			this.wheel3.xRot = 0;
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}