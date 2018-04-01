package cm.cn.util;

import org.junit.Test;
import static org.junit.Assert.*;
public class GetCheckCodeTest {
    
	@Test
	public void getCode(){
		String phone = "17601510248";
		String check_code="123456";
		assertTrue("验证码出现问题",GetCheckCode.getCode(phone, check_code));
	}
	
	@Test
	public void sendMessage(){
		String phone = "17601510248";
		String message="你目前已经超速,你按照正常速度安全行驶";
		assertTrue("发送通知出现问题",GetCheckCode.sendMessage(phone, message));
	}
	
}

