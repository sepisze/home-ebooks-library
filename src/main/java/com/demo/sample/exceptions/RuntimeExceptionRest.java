package com.demo.sample.exceptions;

public class RuntimeExceptionRest extends RuntimeException {

	private String requestId;
	private String instanceId;

	public RuntimeExceptionRest(String message, String instanceId, String requestId) {
		super(message);

		this.instanceId = instanceId;
		this.requestId = requestId;
	}

	public RuntimeExceptionRest(Throwable cause, String instanceId, String requestId) {
		super(cause);

		this.instanceId = instanceId;
		this.requestId = requestId;
	}
  
	public String getRequestId() {
		return requestId;
	}

	public String getInstanceId() {
		return instanceId;
	}

}
