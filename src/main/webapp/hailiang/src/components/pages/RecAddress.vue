<template>
<div class="recaddress">
    <nav>
        <div>
            <Button type="primary" @click="open($event)">添加</Button>
            <Button type="primary" @click="open($event)">修改</Button>
            <Button type="primary" @click="open($event)">查看</Button>
            <Button type="primary" @click="">打印</Button>
            <Tooltip content="删除所选收货站点信息" placement="bottom-start">
                <Button type="error" style="margin-left: 20px;" @click="delSiteBatch">删除</Button>
            </Tooltip><!-- 原来是moredelete函数 -->
        </div>
        <div>
            <Button type="primary" @click="getAll" style="margin-left:20px;">刷新</Button>
        </div>
    </nav>
    <!--数据区块-->
    <Table border stripe :columns="columns" :data="data1" @on-selection-change="chooseAll" ellipsis></Table>
    <!--\总页数-->
    <Page :total="totalRecord" @on-change="changePage" :current="page.current" 
          :page-size="page.pageNum" show-total show-elevator size='small'></Page>
    <Modal
    	v-model="model1"
        width="50%"
        :closable="false"
        :scrollable='false'
        @on-ok='open_ok'
        @on-cancel="cancel"
		>
		<Form :model="formValidate" :rules="ruleValidate" ref="formValidate">
			<Row>
				<Col span="11">
					<FormItem label="收货站点" prop="siteName">
						<Input placeholder="请输入收货站点" v-model='formValidate.siteName' v-bind:disabled="openState==='查看'"></Input>
					</FormItem>
					<FormItem label="收货联系人" prop="linkman">
						<Input placeholder="请输入收货联系人" v-model='formValidate.linkman' v-bind:disabled="openState==='查看'"></Input>
					</FormItem>
					<FormItem label="联系人电话" prop="linkmanPhone">
						<Input placeholder="请输入联系人电话" v-model='formValidate.linkmanPhone' v-bind:disabled="openState==='查看'"></Input>
					</FormItem>
					<FormItem label="地址">
						<Cascader :data="cityData" v-model="theCity" style="width: 100%" v-bind:disabled="openState==='查看'"></Cascader>
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
                            v-if="openState=='添加'||openState=='修改'">
                        <div style="width: 200px;height:230px;">
                            <img :src="formValidate.linkmanPhotoPath" v-show="formValidate.linkmanPhotoPath"
                                 style="background-position: 100%;height: 100%;width: 100%;" v-bind:disabled="openState==='查看'" />
                            <Icon type="ios-cloud-upload" size="52" style="color: #3399ff;padding-top: 80px"
                                  v-if="!formValidate.linkmanPhotoPath"></Icon>
                        </div>
                    </Upload>
                    <div v-else style="width: 200px;height:230px;">
                        <img :src="formValidate.linkmanPhotoPath" v-show="formValidate.linkmanPhotoPath"
                                 style="background-position: 100%;height: 100%;width: 100%;" />
                    </div>
                </Col>
            </Row>
            <Row><Col span='24' style="height:6px;"></Col></Row>
            <Row>
            	<Col span="24">
					<FormItem label="详细地址" prop="address">
						<Input placeholder="请输入托运方详细地址" v-model='formValidate.address' v-bind:disabled="openState==='查看'"></Input>
					</FormItem>
            	</Col>
            </Row>
			<Row>
            	<Col span="24">
					<FormItem label="备注">
						<Input v-model="formValidate.remark" placeholder="请输入备注" type="textarea" :rows="4" v-bind:disabled="openState==='查看'"></Input>
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
	                    width: 60,
	                    fixed: 'left'
	                },
	                {
	                    title: '收货站点',
	                    key: 'siteName',
	                    align: 'center',
	                    //width: 120,
	                },
	                {
	                    title: '省',
	                    key: 'province',
	                    align: 'center',
	                    //width: 120,
	                    ellipsis: true
	                },
	                {
	                    title: '市',
	                    key: 'city',
	                    align: 'center',
	                    //width: 120,
	                    ellipsis: true
	                },
	                {
	                    title: '区',
	                    key: 'district',
	                    align: 'center',
	                    //width: 150,
	                    ellipsis: true
	                },
	                {
	                    title: '详细地址',
	                    key: 'address',
	                    align: 'center',
	                    //width: 120,
	                    ellipsis: true
	                },
	                {
	                    title: '企业联系人',
	                    key: 'linkman',
	                    align: 'center',
	                    //width: 120,
	                    ellipsis: true
	                },
	                {
	                    title: '联系人电话',
	                    key: 'linkmanPhone',
	                    align: 'center',
	                    //width: 130,
	                    ellipsis: true
	                },
	                {///留一个一个查看按钮，其余全部在上方nav处显示

	                } ],
	        data1: [],
	        formValidate: {
                siteName: '',
                // province: '',
                // city: '',
                // district: '',
                address: '',
                linkman: '',
                linkmanPhone: '',
                linkmanPhotoPath: '',
                id: null,
                remark: null,
                companyId: null,
                type: '',

            },
            ruleValidate: {
                //siteName: [{required: true, message: '托运方名称不能为空', trigger: 'blur'}],
                // province: [{required: true, message: '地址不能为空', trigger: 'blur'}],
                // city: [{required: true, validator: "地址不能为空", trigger: 'blur' }],
                //district: [{required: true, validator: "地址不能为空", trigger: 'blur' }],
                address: [{required: true, message: "详细地址不能为空", trigger: 'blur' }],
                linkman: [{required: true, message: '企业联系人不能为空', trigger: 'blur'}],
                linkmanPhone: [{required: true, message: '联系人电话不能为空', trigger: 'blur'}],
                siteName: [{required: true, message: '托运方名称不能为空', trigger: 'blur'}]
                //linkmanPhone: [{required: true, message: '联系人电话不能为空', trigger: 'blur'}],
                //linkmanPhotoPath: [{required: true, message: '图片不能为空', trigger: 'blur'}]
            },
	        model1: false,
	        theCity: ['浙江','宁波市','余姚市'],
	        cityData: [],
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
            console.log(data);
            _self.delArr=[];//放置所以被选择行的id
            _self.oneArr=null;//放置单独的一行的所有信息
            if(data.length){
                for (var i in data) {
                    _self.delArr.push(data[i].id); 
                }
            }
            if(data.length==1){
                _self.oneArr=data[0];
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
                idata.type='1';
                console.log("输出传入的参数");
                console.log(idata);
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
                        /*for (var i in data.dataList) {
                            data.dataList[i].linkmanPhotoPath = "http://localhost:8080" + '/SitePhoto/' + data.dataList[i].linkmanPhotoPath;
                        }*/
                        _self.data1 = data.dataList;
                        console.log(_self.data1);
                        
                    }
                });
            //}
            _self.searchText='';
		},
		// 展开一个托运方信息
		open: function(event){
			var iText=null;
			var _self=this;
			console.log(_self.oneArr);
			console.log(_self.delArr);
			//先默认添加，打开图片上传
			iText=event.currentTarget.innerText;
			console.log(iText);
			//添加
			if(iText=="添加"){
				_self.openState='添加';
			//查看
			}else if(iText=="查看"){
				if(!_self.oneArr){
					window.alert("请选择要查看的数据");
					return ;	
				}
				_self.openState="查看";
				_self.theCity[0]=_self.oneArr.province;
				_self.theCity[1]=_self.oneArr.city;
				_self.theCity[2]=_self.oneArr.district;//theCity: ['浙江','宁波市','余姚市'],
				for(let key in _self.oneArr){
					//if(_self.hasOwnProperty(key)){
						_self.formValidate[key]=_self.oneArr[key];
					//}
				}
				console.log(_self.formValidate===_self.oneArr)
				console.log("将oneArr赋值给formValidate");
				console.log(_self.formValidate)
				//_self.formValidate.linkmanPhotoPath="http://localhost:8080" + '/SitePhoto/' +
				console.log("地址")
				console.log(_self.theCity)
			//修改
			}else if(iText=="修改"){
				if(!_self.oneArr){
					window.alert("请选择要修改的数据");
					return ;
				}
				_self.openState="修改";
				_self.theCity[0]=_self.oneArr.province;
				_self.theCity[1]=_self.oneArr.city;
				_self.theCity[2]=_self.oneArr.district;
				for(let key in _self.oneArr){
					//if(_self.hasOwnProperty(key)){
						_self.formValidate[key]=_self.oneArr[key];
					//}
				}
				console.log("将oneArr赋值给formValidate");
				console.log(_self.formValidate===_self.oneArr)
				console.log(_self.formValidate)
				//_self.formValidate.linkmanPhotoPath="http://localhost:8080" + '/SitePhoto/' +
			}
			//event.currentTarget.innerText
			_self.model1=true;
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
            //this.formValidate.pictureName = data[0];
            //this.formValidate.pictureUrl = data[1];
            this.loadingStatus = false;
            this.formValidate.linkmanPhotoPath = "http://localhost:8080" + '/SitePhoto/' + data[0];
            this.$Message.success('上传成功');
        },
        error:function() {
            this.$Message.success('上传失败');
        },
        cancel:function(){
        	var _self=this;
            _self.model1=false;
            //if(_self.openState=="修改"||_self.openState=="添加"){
	            _self.$refs['formValidate'].resetFields();
	            //this.$refs['formValidate'].resetFields();
	    		_self.formValidate.linkmanPhotoPath=null;
	    		_self.formValidate.remark=null;
	    		//_self.theCity=['浙江','宁波市','余姚市'];
            //}
            
            console.log("在cancel函数中")
            // _self.oneArr=null;
            // _self.delArr=[];
        },
        open_ok: function(){
            var _self = this;
            //_self.loading = true;
            console.log("进入open_ok");
            _self.$refs.formValidate.validate(function (valid) {
                if (_self.openState == "查看") {
                    //_self.loading = false;
                    //_self.model1 = false;
	        		//_self.$refs['formValidate'].resetFields();
	        		//_self.formValidate.linkmanPhotoPath=null;
	        		//_self.formValidate.remark=null;
                    //return ;
                } else if (_self.openState == "添加") {
                    if (valid) {
                    	_self.insertSite();
                    }
                } else{// if (_self.openState == "修改")
                	if(valid){
                		_self.updateSite();
                	}
                }
        		// _self.oneArr=null;
        		// _self.delArr=[];
        		_self.$refs['formValidate'].resetFields();
        		_self.formValidate.linkmanPhotoPath=null;
        		_self.formValidate.remark=null;
        		_self.model1=false;
        		//_self.theCity=['浙江','宁波市','余姚市'];
            });
        },
        insertSite: function(){//添加逻辑
        	var _self=this;
        	console.log(_self.formValidate);
        	_self.formValidate.companyId=parseInt(_self.$cookies.get("companyID"));
            _self.formValidate.province = _self.theCity[0];
            _self.formValidate.city = _self.theCity[1];
            _self.formValidate.district = _self.theCity[2];
            _self.formValidate.type='1';
            console.log(_self.formValidate);
            _self.formValidate.id=null;
            $.ajax({
            	url: 'http://localhost:8080/HaiSSMDemo/insertSite.action',
            	data: _self.formValidate,
            	type: 'POST',
            	cache: false,
            	success: function(){
            		// _self.$refs['formValidate'].resetFields();
            		// _self.formValidate.linkmanPhotoPath=null;
            		// _self.formValidate.remark=null;
        			_self.$Message.info("完成添加");
        			_self.getAll();
        			_self.model1=false;
            	}
            });
        },
        updateSite: function(){//更新逻辑
        	var _self=this;
        	_self.formValidate.companyId=parseInt(_self.$cookies.get("companyID"));
            _self.formValidate.province = _self.theCity[0];
            _self.formValidate.city = _self.theCity[1];
            _self.formValidate.district = _self.theCity[2];
            _self.formValidate.type='1';
        	$.ajax({
        		url: 'http://localhost:8080/HaiSSMDemo/updateSite.action',
        		data: _self.formValidate,
        		type: 'POST',
        		cache: false,
        		success: function(){
            		// _self.$refs['formValidate'].resetFields();
            		// _self.formValidate.linkmanPhotoPath=null;
            		// _self.formValidate.remark=null;
        			_self.$Message.info("完成修改");
        			_self.getAll();
        			_self.model1=false;
        		}
        	});
        },
        delSiteBatch: function(){//删除逻辑
        	var _self=this;
        	console.log("进入删除逻辑");
        	console.log(_self.delArr);

        	if(_self.delArr.length>0){
        		$.ajax({
        			url: 'http://localhost:8080/HaiSSMDemo/delSiteBatch.action',
        			traditional:true,
        			data: {arrays:_self.delArr},
        			type: 'POST',
        			cache: false,
        			success: function(){
        				_self.$Message.info("删除成功");
        				_self.delArr=[];
        				_self.getAll();
        			}
        		});
        	}else{
        		alert("请选择要删除的数据")
        	}
        }
        /*postData: function(idata){
        	var _self=this;
        	consoel.log("在postData函数中");
        	$.ajax({
        		url: 'http://localhost:8080/HaiSSMDemo/insertSite.action',
        		data: idata,
        		type: 'post',
        		cache: false,
        		success: function(data){
        			 _self.$refs['formValidate'].resetFields();
        			_self.$Message.info("完成添加");
        			_self.model1=false;
        		}
        	});
        }*/
	}//methods结束
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