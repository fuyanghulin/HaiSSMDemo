package cm.cn.test;


import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import cm.cn.po.HlGoodstype;
import cm.cn.service.HlGoodsTypeService;
import cm.cn.util.Base64;

import org.junit.Test;


public class OtherTest {
	@Resource
	private HlGoodsTypeService hlGoodsTypeService;
	
	@Test
	public void allUser(){
		List<HlGoodstype> list = hlGoodsTypeService.selectAllGoodsType();
		Iterator<HlGoodstype> it = list.iterator();
		while(it.hasNext()){
		    System.out.println(it.next());
		}
	}
	@Test
	public void testBase() throws UnsupportedEncodingException{
		String pass = "123456";
		pass = Base64.encode(("*nbyy"+pass+"hlkj*").getBytes());
		System.out.println(pass);
		String srt2=new String(Base64.decode(pass),"UTF-8");
		System.out.println(srt2);
	}
}
