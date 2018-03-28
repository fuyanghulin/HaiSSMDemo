package cm.cn.test;


import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import cm.cn.po.HlGoodstype;
import cm.cn.service.HlGoodsTypeService;

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
}
