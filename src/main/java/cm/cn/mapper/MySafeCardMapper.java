package cm.cn.mapper;

public interface MySafeCardMapper {
	//根据 id 批量删除数据
	public int delSafeCardBatch(int[] arrays);
	public String[] selSafeCardFilename(int[] arrays);
	public String[] selIntroFilename(int[] arrays);
}
