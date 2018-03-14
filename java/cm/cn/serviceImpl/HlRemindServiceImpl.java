package cm.cn.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.cn.mapper.HlRemindMapper;
import cm.cn.po.HlCompany;
import cm.cn.po.HlRemind;
import cm.cn.service.HlRemindService;
@Service
public class HlRemindServiceImpl implements HlRemindService {
    @Autowired
    private HlRemindMapper hlRemindMapper;
	@Override
	public HlRemind getRemind() {
		return hlRemindMapper.getRemind();
	}
	@Override
	public List<HlCompany> getCompanyRemindByBusiLic() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<HlCompany> getCompanyRemindByBusiCer() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
