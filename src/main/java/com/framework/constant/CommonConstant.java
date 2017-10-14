package com.framework.constant;

public class CommonConstant {
	public static final String LOGIN_USER_INFO = "loginUserInfo";
	
	public static final String CHECK_CODE = "checkCode";

	public static final int COOKIE_MAX_AGE = 1*24*60*60;//1天

	public static final String COOKIE_USER_INFO_ID = "userId";

	public static final String COOKIE_PERSON_CODE = "personCode";

	public static final Object LOGIN_USER_MENU = "loginUserMenu";

	public static final Object LOGIN_USER_INFO_JSON = "loginUserInfoJson";
	
	/**
	 * 文件业务类型
	 * @author heshaowei
	 * @date 2016年8月28日 上午2:04:19
	 * 描述：
	 */
	public enum FileBusinessType{
		/**
		 * 用户照片
		 */
		USER_PHOTO("100");
		private String type;
		private FileBusinessType(String type){
			this.type = type;
		}
		public String type(){
			return this.type;
		}
	}
	
	/**
	 * 文件类型
	 * @author heshaowei
	 * @date 2016年8月28日 上午2:07:06
	 * 描述：
	 */
	public enum FileType{
		/**
		 * 图片
		 */
		IMAGE("image");
		private String type;
		private FileType(String type){
			this.type = type;
		}
		public String type(){
			return this.type;
		}
	}
}
