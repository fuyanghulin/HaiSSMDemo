<template>
<div class="shipperinfo">
    <nav>
        <div>
            <Button type="primary" @click="open">添加</Button>
            <Button type="primary" @click="">修改</Button>
            <Button type="primary" @click="">查看</Button>
            <Button type="primary" @click="">打印</Button>
            <Tooltip content="删除所选托运方信息" placement="bottom-start">
                <Button type="error" style="margin-left: 20px;" @click="">删除</Button>
            </Tooltip><!-- 原来是moredelete函数 -->
        </div>
        <div>
            <Input placeholder="请输入车牌号" style="width: 200px;" v-model="searchText" @keyup.native.enter=""></Input>
            <button type="primary" shape="circle" icon="ios-search" @click=""></button>
            <Button type="primary" @click="" style="margin-left:20px;">刷新</Button>
        </div>
    </nav>
    <!--数据区块-->
    <Table border stripe :columns="columns" :data="data1" @on-selection-change="chooseAll" ellipsis></Table>
    <!--\总页数-->
    <Page :total="totalRecord" @on-change="changePage" :current="page.current" 
          :page-size="page.pageNum" show-total show-elevator size='small'></Page>
    <Modal
    	v-model="model1"
        width="65%"
        :closable="false"
        :scrollable='false'
        @on-ok='open_ok'
        @on-cancel="cancel"
		>
		<Form :model="formValidate" :rules="ruleValidate" ref="formValidate">
			<Row>
				<Col span="11">
					<FormItem label="托运方" prop="site_name">
						<Input placeholder="请输入托运方" v-model='formValidate.site_name'></Input>
					</FormItem>
					<FormItem label="企业联系人" prop="site_name">
						<Input placeholder="请输入企业联系人" v-model='formValidate.linkman'></Input>
					</FormItem>
					<FormItem label="联系人电话" prop="site_name">
						<Input placeholder="请输入联系人电话" v-model='formValidate.linkman_phone'></Input>
					</FormItem>
					<FormItem label="地址">
						<Cascader :data="cityData" v-model="theCity" style="width: 100%" ></Cascader>
					</FormItem>
				</Col>
				<Col span="2"></Col>
				<Col span="11" style="display: flex;align-items: center;justify-content: center;">
                    <Upload
                            multiple
                            type="drag"
                            ref="upload"
                            :show-upload-list="false"
                            :format="['jpg','jpeg','png']"
                            :on-format-error="handleFormatError"
                            :max-size="2048"
                            :on-exceeded-size="handleMaxSize"
                            :on-success="success"
                            :on-error="error"
                            :action='up_photo'
                            style="width:200px;"
                            v-if="openState=='添加'">
                        <div style="width: 200px;height:230px;">
                            <img :src="formValidate.linkman_photo_path" v-show="formValidate.linkman_photo_path"
                                 style="background-position: 100%;height: 100%;width: 100%;" v-bind:disabled="openState==='查看'" />
                            <Icon type="ios-cloud-upload" size="52" style="color: #3399ff;padding-top: 80px"
                                  v-if="!formValidate.linkman_photo_path"></Icon>
                        </div>
                    </Upload>
                    <div v-else style="width: 200px;height:230px;">
                        <img :src="formValidate.linkman_photo_path" v-show="formValidate.linkman_photo_path"
                                 style="background-position: 100%;height: 100%;width: 100%;" />
                    </div>
                </Col>
            </Row>
            <Row>
            	<Col span="24">
					<FormItem label="详细地址" prop="address">
						<Input placeholder="请输入托运方详细地址" v-model='formValidate.address'></Input>
					</FormItem>
            	</Col>
            </Row>
			<Row>
            	<Col span="24">
					<FormItem label="备注" prop="remark">
						<Input v-model="formValidate.remark" placeholder="请输入备注" type="textarea" :rows="4"></Input>
					</FormItem>
            	</Col>
            </Row>
		</Form>
        <div slot="footer">
            <Button type="text" style="width: 100px;" @click="cancel">取消</Button>
            <Button type="primary" @click="open_ok"  style="width: 100px;">确定</Button>
        </div>
	</Modal>
</div>
</template>

<script>
import cityDatas from '../../assets/cityData.json'
export default{
	name: 'ShipperInfo',
	data: function(){
		return {
			userType:null,
			searchText:'',
			delArr: [],
			oneArr: null,
	        totalRecord: 0,
	        page: {
	            current: 1,
	            pageNum: 15
	        },
	        columns: [
	                {
	                    type: 'selection',
	                    align: 'center',
	                    width: 120,
	                    fixed: 'left'
	                },
	                {
	                    title: '托运方',
	                    key: 'site_name',
	                    align: 'center',
	                    width: 120,
	                    fixed: 'left'
	                },
	                {
	                    title: '省',
	                    key: 'province',
	                    align: 'center',
	                    width: 120,
	                    ellipsis: true
	                },
	                {
	                    title: '市',
	                    key: 'city',
	                    align: 'center',
	                    width: 120,
	                    ellipsis: true
	                },
	                {
	                    title: '区',
	                    key: 'district',
	                    align: 'center',
	                    width: 150,
	                    ellipsis: true
	                },
	                {
	                    title: '详细地址',
	                    key: 'address',
	                    align: 'center',
	                    width: 120,
	                    ellipsis: true
	                },
	                {
	                    title: '企业联系人',
	                    key: 'linkman',
	                    align: 'center',
	                    width: 120,
	                    ellipsis: true
	                },
	                {
	                    title: '联系人电话',
	                    key: 'linkman_phone',
	                    align: 'center',
	                    width: 130,
	                    ellipsis: true
	                }],
	        data1: [],
	        formValidate: {
                site_name: '',
                // province: '',
                // city: '',
                // district: '',
                address: '',
                linkman: '',
                linkman_phone: '',
                linkman_photo_path: '',
                company_id: null,
                remark: ''
            },
            ruleValidate: {
                site_name: [{required: true, message: '托运方名称不能为空', trigger: 'blur'}],
                // province: [{required: true, message: '地址不能为空', trigger: 'blur'}],
                // city: [{required: true, validator: "地址不能为空", trigger: 'blur' }],
                district: [{required: true, validator: "地址不能为空", trigger: 'blur' }],
                address: [{required: true, validator: "详细地址不能为空", trigger: 'blur' }],
                linkman: [{required: true, message: '企业联系人不能为空', trigger: 'blur'}],
                linkman_phone: [{required: true, message: '联系人电话不能为空', trigger: 'blur'}],
                linkman_photo_path: [{required: true, message: '图片不能为空', trigger: 'blur'}]
            },
	        model1: false,
	        theCity: ['浙江','宁波市','余姚市'],
	        openState: '添加',
            pictureName: '',
            pictureUrl: '',
            up_photo: 'http://localhost:8080/HaiSSMDemo/upSitePhoto.action'
		}

	},
	created: function(){
		this.userType=this.$cookies.get('roleID');
	},
	activated: function(){
		this.cityData=cityDatas;
        this.userType=this.$cookies.get("roleID");
        console.log("在activate的：过程中");
        this.getAll();
	},
	methods: {
		// table选择发生变化
        chooseAll:function(data) {
            var _self = this;
            //console.log(this.data.selection);
            _self.delArr=[];
            _self.oneArr=null;
            if(data.length){
                for (var i in data) {
                    _self.delArr.push(data[i].id); 
                }
            }
            if(data.length==1){
                _self.oneArr=data[i].id;
            }
        },
        changePage: function (cur) {
            // 分页跳转
            var _self=this;
            _self.page.current = cur;
            _self.getAll();
		},
		// 获取该公司下所有托运方
		getAll: function(){
            var _self = this;
            console.log(_self.userType);
            console.log("进入getall函数");
            //console.log(this.userType);  后台接口 int current,int pageNum,int companyId,String type
            //if (_self.userType == 3) {
                //根据公司Name查询数据
                var idata={};
                idata.current=_self.page.current;
                idata.pageNum=_self.page.pageNum;
                idata.companyId=parseInt(_self.$cookies.get("companyID"));//Cookies.get("state")).companyName;
                idata.type='0';
                console.log(idata);
                console.log(typeof idata.current);
                console.log(typeof idata.pageNum);
                console.log(typeof idata.companyId);
                console.log(typeof idata.type);
                // $.ajax({
                // 	url: 'http://localhost:8080/HaiSSMDemo/selSiteByCompanyIdAndType.action',
                // 	data: "current=" + idata.current + "&pageNum=" + idata.pageNum+ "&companyId=" + idata.companyId+ "&type=" + idata.type,
                // 	type: 'post',
                // 	success: function(data){
                // 		console.log("输出data");
                // 		console.log(data);
                // 	}
                // });
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/HaiSSMDemo/selSiteByCompanyIdAndType.action',
                    data: idata,
                    cache: false,
                    success: function (data) {
                    	console.log("查看了ajax返回的数据");
                    	console.log(data);
                        _self.totalRecord = data.totalRecord;
                        _self.current = data.currentPage;
                        for (var i in data.dataList) {
                            data.dataList[i].linkman_photo_path = "http://localhost:8080" + '/SitePhoto/' + data.dataList[i].linkman_photo_path;
                        }
                        _self.data1 = data.dataList;
                        console.log(_self.data1);
                        
                    }
                });
            //}
            _self.searchText='';
		},
		// 展开一个托运方信息
		open: function(){
			var _self=this;
			_self.model1=true;
			//先默认添加，打开图片上传
			_self.openState='添加'
		},
        handleFormatError:function(file) {
            this.$Notice.warning({
                title: '文件格式不正确',
                desc: '文件 ' + file.name + ' 格式不正确，请上传 jpg 或 png 格式的图片。'
            });
        },
        handleMaxSize:function(file) {
            this.$Notice.warning({
                title: '超出文件大小限制',
                desc: '文件 ' + file.name + ' 太大，不能超过 2M。'
            });
        },
        success:function(data) {
            this.formValidate.pictureName = data[0];
            this.formValidate.pictureUrl = data[1];
            this.loadingStatus = false;
            this.formValidate.linkman_photo_path = "http://localhost:8080" + '/SitePhoto/' + this.formValidate.pictureName;
            this.$Message.success('上传成功');
        },
        error:function() {
            this.$Message.success('上传失败');
        },
        cancel:function(){
        	var _self=this;
            _self.model1=false;
            _self.$refs['formValidate'].resetFields();
            console.log("在cancel函数中")
        },
        open_ok: function(){
            var _self = this;
            //_self.loading = true;
            console.log("进入open_ok");
            _self.$refs.formValidate.validate(function (valid) {
                if (_self.openState == "查看") {
                    //_self.loading = false;
                    _self.model1 = false;
                } else {
                    if (valid) {
                        console.log(_self.formValidate);
                        if(_self.openState=="添加"){
                        	_self.formValidate.id=parseInt(_self.$cookies.get("companyID"));
	                        _self.formValidate.province = _self.theCity[0];
	                        _self.formValidate.city = _self.theCity[1];
	                        _self.formValidate.county = _self.theCity[2];
	                        console.log("即将进入postData");
	                        console.log(_self.formValidate);
                        	_self.postData(_self.formValidate);
                        }
                    }
                }
            });
        },
        postData: function(data){
        	var _self=this;
        	$.ajax({
        		url: 'http://localhost:8080/HaiSSMDemo/insertSite.action',
        		data: data,
        		type: 'post',
        		cache: false,
        		success: function(data){
        			_self.$Message.info("完成添加");
        		}
        	});
        }
	}
}
</script>

<style scoped>
    nav {
        padding: 10px 34px 10px 16px;
        display: flex;
        justify-content: space-between;
        justify-items: center;
    }

    nav div:first-child button {
        margin: 0 10px;
    }
    .ivu-formitem-content {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .ivu-row {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .ivu-modal {
        top: 0px;
        margin: 50px auto;
    }

    .ivu-table td{
        white-space: nowrap;
    }
</style>