package hfuu.examination.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import hfuu.examination.utils.FlagContact;

/**
 * @ClassName: WebResult
 * @version 1.0
 * @Desc: WEB返回JSON结果
 * @history v1.0
 */
public class WebResult implements Serializable
{
    private static final long serialVersionUID = -4776437900752507269L;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回数据
     */
    private Object data;
    /**
     * 数据条数
     */
    private int count;
  
	private Map<?,?> map;
    
    private List<Map<String, Object>> list;

    public WebResult()
    {
    }

    public WebResult(String msg, String code)
    {
        super();
        this.msg = msg;
        this.code = code;
    }

    public WebResult(String msg, String code, Object data)
    {
        super();
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
    public WebResult(String msg, String code, Integer count, Object data)
    {
        super();
        this.msg = msg;
        this.code = code;
        this.count = count;
        this.data = data;
    }
    public int getCount() {
  		return count;
  	}

  	public void setCount(int count) {
  		this.count = count;
  	}

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
    
    public Map<?, ?> getMap()
    {
        return map;
    }

    public void setMap(Map<?, ?> map)
    {
        this.map = map;
    }

    public List<Map<String, Object>> getList()
    {
        return list;
    }

    public void setList(List<Map<String, Object>> list)
    {
        this.list = list;
    }

    @Override
    public String toString()
    {
        return "WebResult [msg=" + msg + ", code=" + code + ", data=" + data + "]";
    }
    
    /**
     * 初始失败方法
     *
     * @author cc HSSD0473
     * @see [类、类#方法、类#成员]
     */
    public void invokeFail(){
    	this.data = null;
    	this.code = FlagContact.BACK_FAIL.getCode();
    	this.msg = "操作失败";
    }
    
    public void invokeFail(String msg){
    	this.data = null;
    	this.code = FlagContact.BACK_FAIL.getCode();
    	if(msg != null && !msg.equals(""))
    	{
    		this.msg = msg;
    	}
    }
    
    public void invokeSuccess()
    {
    	this.code = FlagContact.BACK_SUCCESS.getCode();
    	this.msg = "操作成功";
    }
    
    public void invokeSuccess(String msg)
    {
    	if(msg != null && !msg.equals(""))
    	{
    		this.msg = msg;
    	}
    	this.code = FlagContact.BACK_SUCCESS.getCode();
    }
}
