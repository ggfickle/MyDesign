package hfuu.examination.utils;

public enum LoginContact {
	SESSION_USER("user");
	private String key;
	LoginContact(String key) {
		this.key=key;
	}
	 /** 
     * 根据类型的名称，返回类型的枚举实例。 
     * 
     * @param typeName 类型名称 
     */  
    public static LoginContact fromKey(String key) {  
        for (LoginContact type : LoginContact.values()) {  
            if (type.getKey().equals(key)) {  
                return type;  
            }  
        }  
        return null;  
    }  

    public String getKey() {  
        return this.key;  
    }  
}
