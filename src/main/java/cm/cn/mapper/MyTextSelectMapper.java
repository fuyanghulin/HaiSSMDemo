package cm.cn.mapper;

import java.util.List;

import cm.cn.po.HlCheckText;
import cm.cn.po.HlDriverlogText;

public interface MyTextSelectMapper {
	//����  ID �����ȡ HlDriverlogText
	public List<HlDriverlogText> selectHlDriverlogTextByArray(int[] array);
	//���� ID �����ȡHlCheckText
	public List<HlCheckText> selectHlCheckTextByArray(int[] array);
}
