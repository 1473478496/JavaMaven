package com.vivebest.mall.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.vivebest.mall.core.constants.ResultConstant;

/**
 * 自定义异常
 * 
 * @author jiang.hl
 * @date 2015-07-16 15:30:32
 */
public class ServiceException extends Exception 
{

    /**
     * 错误码
     */
    private String errorCode = ResultConstant.RESULT_CODE_FAIL;
    
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 异常栈信息
     */
    private String errorStackTrace;

    public ServiceException()
    {
        super();
        setErrorStackTrace();
    }

    public ServiceException(String message) 
    {
        super(message);
        this.errorMsg = message;
        setErrorStackTrace();
    }

    public ServiceException(Throwable cause) 
    {
        super(cause);
        setErrorStackTrace();
    }

    public ServiceException(String message, Throwable cause) 
    {
        super(message, cause);
        this.errorMsg = message;
        setErrorStackTrace();
    }
    
    public ServiceException(String errorCode,String message) 
    {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = message;
        setErrorStackTrace();
    }

    public void setErrorStackTrace() 
    {
    	//1. 取栈信息
		StringWriter writer = new StringWriter();
		PrintWriter p = new PrintWriter(writer);
		this.printStackTrace(p);
		errorStackTrace = writer.getBuffer().toString();
		
		//2. 关闭流
		p.close();
	}
    
    
    
    
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorStackTrace() {
		return errorStackTrace;
	}

	public void setErrorStackTrace(String errorStackTrace) {
		this.errorStackTrace = errorStackTrace;
	}

    
    

}
