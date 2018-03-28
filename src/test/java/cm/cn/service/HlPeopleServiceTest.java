package cm.cn.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cm.cn.po.HlPeople;
import cm.cn.service.HlPeopleService;

public class HlPeopleServiceTest extends BaseJunit4Test{
	
    @Autowired
    private HlPeopleService hlPeopleService;
    
    
    @Test
    public void selectPeopleById(){
    	int peopleId = 87;
    	HlPeople hlPeople = hlPeopleService.selectPeopleById(peopleId);
    	System.out.println(hlPeople.getAdresss());
    }
    
}
