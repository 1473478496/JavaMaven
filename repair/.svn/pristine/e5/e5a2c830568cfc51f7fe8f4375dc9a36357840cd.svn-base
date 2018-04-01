package cn.sunline.ws.foreignInterface.exception;

public class RepairException extends Exception {

	/**
	 * 对象版本编号
	 */
	private static final long serialVersionUID = 5759381550174495948L;

	/**
	 * 错误信息
	 */
	private String errorMessage = null;

	@SuppressWarnings("unused")
	private Throwable error = null;

	// **************************************************************************
	/**
	 * DMException构造函数
	 */
	// **************************************************************************
	public RepairException() {
		super();
	}

	// **************************************************************************
	/**
	 * DMException构造函数根据传递的异常信息
	 * 
	 * @param argMessage
	 */
	// **************************************************************************

	public RepairException(String argMessage) {
		super(argMessage);
		errorMessage = argMessage;
	}

	// **************************************************************************
	/**
	 * DMException构造函数通过传递异常对象
	 * 
	 * @param argThr
	 */
	// **************************************************************************

	public RepairException(Throwable argThr) {
		super(argThr);
		error = argThr;
		errorMessage = argThr.getLocalizedMessage();
	}

	// **************************************************************************
	/**
	 * DMException构造函数根据传递的异常信息
	 * 
	 * @param argMessage
	 * @param argThr
	 */
	// **************************************************************************
	public RepairException(String argMessage, Throwable argThr) {
		super(argMessage, argThr);
	}

	// **************************************************************************
	/**
	 * 当该异常被打印出来的时候执行
	 * 
	 * @return String
	 */
	// **************************************************************************
	public String toString() {
		StringBuffer sqlString = new StringBuffer("送修系统与理赔交互接口类异常： "
				+ "RepairException:");
		sqlString
				.append("\n**************BackToElecPinServiceException Start***********************");
		sqlString.append("msgError: 《");
		sqlString.append(errorMessage).append("》");
		sqlString
				.append("\n**************BackToElecPinServiceException End************************");
		return sqlString.toString();
	}

}
