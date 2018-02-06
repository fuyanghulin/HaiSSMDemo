package cm.cn.serviceImpl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.cn.datasource.CustomerContextHolder;
import cm.cn.mapper.HlGoodstypeMapper;
import cm.cn.mapper.HlPeopleMapper;
import cm.cn.mapper.HlSafecardMapper;
import cm.cn.mapper.MyCarInfoMapper;
import cm.cn.mapper.MyGoodsInfoMapper;
import cm.cn.mapper.MyGpsMapper;
import cm.cn.po.GpsMsg;
import cm.cn.po.Gpsinfo;
import cm.cn.po.HlPeople;
import cm.cn.po.HlPeopleExample;
import cm.cn.service.HlGpsService;
import cm.cn.util.GpsStatus;
@Service
public class HlGpsServiceImpl  implements HlGpsService {
    @Autowired
    private MyGpsMapper myGpsMapper;
    @Autowired 
    private MyCarInfoMapper myCarInfoMapper;
    @Autowired
    private HlPeopleMapper hlPeopleMapper;
    @Autowired
    private MyGoodsInfoMapper myGoodsInfoMapper;
    @Autowired
    private HlGoodstypeMapper hlGoodstypeMapper;
    @Autowired
    private HlSafecardMapper hlSafecardMapper;
	@Override
	public Gpsinfo selectGpsByPlateNo(String PlateNo) {
		//CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GPS);
		//GpsinfoExample example = new GpsinfoExample();
		//GpsinfoExample.Criteria criteria =  example.createCriteria();
		//criteria.andPlatenoEqualTo(PlateNo);
		//return gpsinfoMapper.selectByExample(example);
		//List<Gpsinfo> list;
		Gpsinfo gpsinfo = new Gpsinfo();
		Connection con;
		//驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		   //URL指向要访问的数据库名mydata
		String url = "jdbc:mysql://localhost:3306/gps_hisdata?useUnicode=true&characterEncoding=utf8";
		  //MySQL配置时的用户名
		String user = "root";
		 //MySQL配置时的密码
	    String password = "root";
		          //遍历查询结果集
		          try {
		              //加载驱动程序
		             Class.forName(driver);
		             //1.getConnection()方法，连接MySQL数据库！！
		              con = DriverManager.getConnection(url,user,password);
		              if(!con.isClosed()){
		                  
		                  }else{
		                  System.out.println("数据库连接失败");  
		                  }
		              //2.创建statement类对象，用来执行SQL语句！！
		              Statement statement = con.createStatement();
		              //要执行的SQL语句
		              Date date=new Date();
		   		      DateFormat format=new SimpleDateFormat("yyyyMMdd");
		   		      String time=format.format(date);
		              String sql = "select *from gpsinfo"+time+ " where plateNo='"+PlateNo+"' order by createDate desc limit 1;";
		              //3.ResultSet类，用来存放获取的结果集！！
		              System.out.println(sql);
		              ResultSet rs = statement.executeQuery(sql); 
		              while(rs.next()){
		                  //获取stuname这列数据
		            	  gpsinfo.setGpsid(rs.getInt("gpsId"));
		            	  gpsinfo.setCreatedate(rs.getDate("createDate"));
		                  gpsinfo.setLatitude(rs.getDouble("latitude"));
		                  gpsinfo.setLongitude(rs.getDouble("longitude"));		              
		            }
		              statement.close();
		              rs.close();
		              con.close();
		         } catch(ClassNotFoundException e) {   
		              //数据库驱动类异常处理
		              System.out.println("Sorry,can`t find the Driver!");   
		              e.printStackTrace();   
		              } catch(SQLException e) {
		              //数据库连接失败异常处理
		           e.printStackTrace();  
		            }catch (Exception e) {
		             // TODO: handle exception
		             e.printStackTrace();
		         }finally{
		              //System.out.println("数据库数据成功获取！！");
		         }
		          return gpsinfo;
	}

	@Override
	public GpsMsg selectGpsByPlate(String plateNo,int goods_id) {
		Gpsinfo gpsinfo = new Gpsinfo();
		GpsMsg gpsMsg = new GpsMsg();
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GPS);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMdd");
		String tableName="gpsinfo"+format.format(date);
		//System.out.println(myGpsMapper.selectMaxVelocity(plateNo));
		//System.out.println(myGpsMapper.selectOverSpeedTimes(plateNo));
		gpsinfo = myGpsMapper.selectGpsByPlateNo(tableName,plateNo);
		if(gpsinfo!=null){
		gpsMsg.setMaxVelocity(myGpsMapper.selectMaxVelocity(tableName,plateNo));
		gpsMsg.setOverSpeedVelocityTimes(myGpsMapper.selectOverSpeedTimes(tableName,plateNo));
		gpsMsg.setGpsinfo(gpsinfo);
		Integer status = gpsinfo.getStatus();
		gpsMsg.setStatus(GpsStatus.statusdeal(status));
		
		
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		int driverId = myCarInfoMapper.selDriver(plateNo);
		int saferId = myCarInfoMapper.selSafer(plateNo);
		List<HlPeople> list = new ArrayList<>(2);
		list.add(hlPeopleMapper.selectByPrimaryKey(driverId));
		list.add(hlPeopleMapper.selectByPrimaryKey(saferId));
		gpsMsg.setHlPeople(list);
		}
		int goodsTypeId = myGoodsInfoMapper.selGoodsTypeIdByGoodsId(goods_id);
		int safeCardid = myGoodsInfoMapper.selSafeCardIdByGoodsId(goods_id);
		gpsMsg.setHlGoodstype(hlGoodstypeMapper.selectByPrimaryKey(goodsTypeId));
		gpsMsg.setHlSafecard(hlSafecardMapper.selectByPrimaryKey(safeCardid));
		return gpsMsg;
	}
 }


