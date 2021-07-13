package com.gamingmesh.jobs.hooks.wildstacker;

import java.util.List;

import org.bukkit.entity.LivingEntity;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.bgsoftware.wildstacker.api.objects.StackedEntity;

public class WildStackerHandler {

	public boolean isStackedEntity(LivingEntity entity) {
		return WildStackerAPI.getStackedEntity(entity) != null;
	}

	public List<StackedEntity> getStackedEntities() {
		return WildStackerAPI.getWildStacker().getSystemManager().getStackedEntities();
	}
}
