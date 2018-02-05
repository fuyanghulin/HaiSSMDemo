package cm.cn.mapper;

public interface MyCarInfoMapper {
	public int delCarInfoBatch(int[] arrays);
	public String selDriver(String plateNo);
	public String selSafer(String plateNo);
}
