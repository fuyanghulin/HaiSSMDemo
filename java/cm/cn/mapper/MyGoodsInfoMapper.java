package cm.cn.mapper;

public interface MyGoodsInfoMapper {
	//���ݻ�����Ϣ��������ɾ��
	public int delGoodsInfoBatch(int[] arrays);
	//���ݰ�ȫ�� ID ����ɾ��
	public int delGoodsInfoBatchBySafeCardID(int[] arrays);
	//���ݻ������� ID ����ɾ��
	public int delGoodsInfoBatchByGoodsTypeId(int[] arrays);
}
