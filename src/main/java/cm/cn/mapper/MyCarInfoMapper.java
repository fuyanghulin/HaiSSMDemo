package cm.cn.mapper;

public interface MyCarInfoMapper {
	public int delCarInfoBatch(int[] arrays);
	public int selDriver(String plateNo);
	public int selSafer(String plateNo);
}
