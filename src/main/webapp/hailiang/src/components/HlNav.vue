<template>
	<div class="hlnav">
		<Menu width="auto" style=" height:820px;" theme="light" accordion><!-- 原来是accordion theme="light" -->
		    <MenuItem name="0" class='navindex'><router-link tag="div" class="link_div" to='/'>首页</router-link></MenuItem>
			    <Submenu name="1" v-if="userType===1||userType===2">
			        <template slot="title">
			            <Icon type="ios-analytics"></Icon>
			            基础数据
			        </template>
			        <MenuItem name="1-1"><router-link tag="div" class="link_div" to='/goodstype'>货物类型信息</router-link></MenuItem>
			        <MenuItem name="1-2"><router-link tag="div" class="link_div" to='/safecard'>安全卡信息</router-link></MenuItem>
			    </Submenu>
		    <Submenu name="2">
		        <template slot="title">
		            <Icon type="ios-analytics"></Icon>
		            企业管理
		        </template>
		        <MenuItem name="2-1" v-if="userType===1||userType===2"><router-link tag="div" class="link_div" to='/compinfo'>承运企业信息</router-link></MenuItem>
		        <MenuItem name="2-2"><router-link tag="div" class="link_div" to='/personinfo'>人员信息</router-link></MenuItem><!-- <router-link tag="div" class="link_div" to='/personinfo'>人员信息</router-link> -->
		        <MenuItem name="2-3"><router-link tag="div" class="link_div" to='/carinfo'>车辆信息</router-link></MenuItem><!-- <router-link tag="div" class="link_div" to='/carinfo'>车辆信息</router-link> -->
		    </Submenu>
		    <Submenu name="4">
		        <template slot="title">
		            <Icon type="ios-analytics"></Icon>
		            货物管理
		        </template>
		        <MenuItem name="4-1"><router-link tag="div" class="link_div" to='/goodsinfo'>货物信息</router-link></MenuItem>
		    </Submenu>
		    <Submenu name="5">
		        <template slot="title">
		            <Icon type="ios-analytics"></Icon>
		            托运管理
		        </template>
		        <MenuItem name="5-1">
		        	<router-link tag="div" class="link_div" to='/eleorder'>电子运单信息</router-link>
		        </MenuItem>
		        <MenuItem name="5-2" v-if="userType!==1&&userType!==2"><!-- 这里因为发现userType的逻辑有待完善，所以先这样做取反判断 -->
		        	<router-link tag="div" class="link_div" to='/shipperinfo'>托运方</router-link>
		        </MenuItem>
		        <MenuItem name="5-3" v-if="userType!==1&&userType!==2">
		        	<router-link tag="div" class="link_div" to='/recaddress'>收货站点</router-link>
		        </MenuItem>
		    </Submenu>
		    <Submenu name="6">
		        <template slot="title">
		            <Icon type="ios-analytics"></Icon>
		            动态监控
		        </template>
		        <MenuItem name="6-1"><router-link tag="div" class="link_div" to='/gpsbreak'>GPS违章</router-link></MenuItem><!--  -->
		    </Submenu>
		    <Submenu name="7">
		        <template slot="title">
		            <Icon type="ios-analytics"></Icon>
		            报警中心
		        </template>
		        <MenuItem name="7-1"><router-link tag="div" class="link_div" to='/alarmrecord'>报警记录</router-link></MenuItem><!--  -->
		    </Submenu>
		    <Submenu name="9">
		        <template slot="title">
		            <Icon type="ios-analytics"></Icon>
		            培训中心
		        </template>
		        <MenuItem name="9-1"  v-if="userType===1||userType===2"><router-link tag="div" class="link_div" to='/trainedit'>培训编辑</router-link></MenuItem><!-- trainedit -->
		        <MenuItem name="9-2"><router-link tag="div" class="link_div" to='/trainlog'>培训状态</router-link></MenuItem><!--  -->
		    </Submenu>
		</Menu>
	</div>
</template>

<script>
	export default{
		name:'HlNav',
		data:function(){
			return {
				userType:null
			}
		},
		created:function(){
			this.userType=parseInt(this.$cookies.get("roleID")); 
			console.log(this.$cookies.get('roleID'))
			console.log(this.userType)
		}
	}
</script>

<style scoped>
.hlnav{
	background-color: green;
}
.navindex{
	text-align: center;
	font-size: 21px;
	font-weight: bold;
	border-bottom: 1px dashed #cfcfcf;
}

/*保证点击li到就点击到link跳转*/
.ivu-menu>.ivu-menu-item{
	padding-top: 0;
	padding-bottom: 0;
	padding-right: 0;
}
.link_div{
	padding-top: 14px;
	padding-bottom: 14px;
}

</style>