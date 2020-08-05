package hfuu.examination.utils;

public enum FlagContact {
	BACK_SUCCESS("0"),BACK_ERROR("500"),BACK_FAIL("1");
	private String code;
	FlagContact(String code) {
		this.code=code;
	}
	 /** 
     * 根据类型的名称，返回类型的枚举实例。 
     * 
     * @param typeName 类型名称 
     */  
    public static FlagContact fromTypeName(String code) {  
        for (FlagContact type : FlagContact.values()) {  
            if (type.getCode().equals(code)) {  
                return type;  
            }  
        }  
        return null;  
    }  

    public String getCode() {  
        return this.code;  
    }  

}
