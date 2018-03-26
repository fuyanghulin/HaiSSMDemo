package cm.cn.test;

import org.junit.Test;

import cm.cn.util.GetCheckCode;

public class MesTest {

	@Test
	public void testMsg() {
		String phone = "17601510248";
		String check_code="987654";
		GetCheckCode.getCode(phone, check_code);
	}

}
