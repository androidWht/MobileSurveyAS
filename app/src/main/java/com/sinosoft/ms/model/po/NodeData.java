package com.sinosoft.ms.model.po;

import java.io.Serializable;

public class NodeData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String taskId;//节点任务id
	private String preTaskId;//父节点id
	private String nodeId;//节点类型代码
	private String nodeCName;//节点类型名称
	private String state;//节点状态0.待处理 1.已处理 2.正在处理

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getPreTaskId() {
		return preTaskId;
	}

	public void setPreTaskId(String preTaskId) {
		this.preTaskId = preTaskId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeCName() {
		return nodeCName;
	}

	public void setNodeCName(String nodeCName) {
		this.nodeCName = nodeCName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
