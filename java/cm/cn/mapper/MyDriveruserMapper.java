package cm.cn.mapper;

import cm.cn.po.HlPeople;

public interface MyDriveruserMapper {
	//ɾ����Ա��Ϣ���м�ʻԱ��Ϣʱɾ�� driverUser������Ϣ
	public int delDriveruserBatch(int[] arrays);
	//���ݵ绰�����ѯ��ʻԱ��Ϣ
	public HlPeople selectDriverUserMsgByPhone(String phone);
	//���ݵ绰�����ѯ��Ӧ��˾��ϵ�˵ı����绰
	public String selectCompanyLinkManPhone(String phone);
}
