package cm.cn.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.cn.datasource.CustomerContextHolder;
import cm.cn.mapper.HlSiteMapper;
import cm.cn.mapper.MySiteMapper;
import cm.cn.po.HlSite;
import cm.cn.po.HlSiteExample;
import cm.cn.po.HlSiteExample.Criteria;
import cm.cn.service.HlSiteService;
@Service
public class HlSiteServiceImpl implements HlSiteService {
    @Autowired
    private HlSiteMapper hlSiteMapper;
    @Autowired
    private MySiteMapper MySiteMapper;
	@Override
	public int insertSite(HlSite hlSite) {
		return hlSiteMapper.insertSelective(hlSite);
	}
	@Override
	public List<HlSite> selSiteByCompanyIdAndType(int companyId, String type) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlSiteExample example = new HlSiteExample();
		Criteria criteria = example.createCriteria();
		criteria.andCompanyIdEqualTo(companyId);
		criteria.andTypeEqualTo(type);
		return hlSiteMapper.selectByExample(example);
	}
	@Override
	public int delSiteBatch(int[] arrays) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return MySiteMapper.delSiteBatch(arrays);
	}
	@Override
	public int updateSite(HlSite hlSite) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return hlSiteMapper.updateByPrimaryKeySelective(hlSite);
	}


}
