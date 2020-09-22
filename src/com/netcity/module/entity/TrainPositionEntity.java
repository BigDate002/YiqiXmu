package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class TrainPositionEntity extends BaseEntity {
	private static final long serialVersionUID = 27L;
	private Long positionId;
	private Long trainId;

	public Long getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getTrainId() {
		return this.trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}
}