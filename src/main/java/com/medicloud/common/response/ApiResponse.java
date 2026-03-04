package com.medicloud.common.response;

public class ApiResponse<T> {

	private boolean success;
	private String message;
	private T data;
	private long timestamp;

	public ApiResponse() {
		this.timestamp = System.currentTimeMillis();
	}

	public ApiResponse(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = System.currentTimeMillis();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(true, message, data);
	}

	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(false, message, null);
	}

	@Override
	public String toString() {
		return "ApiResponse{" +
				"success=" + success +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}
}