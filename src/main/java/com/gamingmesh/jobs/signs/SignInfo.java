package com.gamingmesh.jobs.signs;

import java.util.ArrayList;
import java.util.List;

public class SignInfo {

    private final List<JobsSign> allSigns = new ArrayList<>();

    public void setAllSigns(List<JobsSign> allSigns) {
	this.allSigns.clear();

	if (allSigns != null)
	    this.allSigns.addAll(allSigns);
    }

    public List<JobsSign> getAllSigns() {
	return allSigns;
    }

    public void removeSign(JobsSign sign) {
	this.allSigns.remove(sign);
    }

    public void addSign(JobsSign sign) {
	this.allSigns.add(sign);
    }
}
