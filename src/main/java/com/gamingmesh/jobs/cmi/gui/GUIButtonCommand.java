package com.gamingmesh.jobs.cmi.gui;

class GUIButtonCommand {
    private String command;
    private GUIManager.CommandType vis = GUIManager.CommandType.gui;

    public GUIButtonCommand(String command) {
	this.command = command;
    }

    public GUIButtonCommand(String command, GUIManager.CommandType vis) {
	this.command = command;
	this.vis = vis;
    }

    public String getCommand() {
	return command;
    }

    public void setCommand(String command) {
	this.command = command;
    }

    public GUIManager.CommandType getCommandType() {
	return vis;
    }

    public void setCommandType(GUIManager.CommandType vis) {
	this.vis = vis;
    }

}
