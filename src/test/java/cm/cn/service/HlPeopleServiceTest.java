package cm.cn.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cm.cn.po.HlPeople;
import cm.cn.service.HlPeopleService;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
public class HlPeopleServiceTest extends BaseJunit4Test{
	
    @Autowired
    private HlPeopleService hlPeopleService;
    
    
    @Test
    public void selectPeopleById(){
    	int peopleId = 87;
    	HlPeople hlPeople = hlPeopleService.selectPeopleById(peopleId);
    	assertThat(peopleId, equalTo(hlPeople.getId()));
    }
    
}
