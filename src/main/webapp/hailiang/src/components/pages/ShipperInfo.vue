<template>
<div class="shipperinfo">
    <nav>
        <div>
            <Button type="primary" @click="open($event)">添加</Button>
            <Button type="primary" @click="open($event)">修改</Button>
            <Button type="primary" @click="open($event)">查看</Button>
            <!-- <Button type="primary" @click="">打印</Button> -->
            <Button type="error" @click="delSiteBatch">删除</Button>
        </div>
        <div>
            <Button type="primary" @click="getAll" style="margin-left:20px;" :loading="loading">刷新</Button>
        </div>
    </nav>
    <!--数据区块-->
    <Table ref="table" border stripe :columns="columns" :data="data1" @on-selection-change="chooseAll" ellipsis @on-row-click="handleRow"></Table>
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
					<FormItem label="托运方" prop="siteName">
						<Input placeholder="请输入托运方" v-model='formValidate.siteName' v-bind:disabled="openState==='查看'"></Input>
					</FormItem>
					<FormItem label="企业联系人" prop="linkman">
						<Input placeholder="请输入企业联系人" v-model='formValidate.linkman' v-bind:disabled="openState==='查看'"></Input>
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
                        <Icon type="ios-cloud-upload" size="52" style="color: #3399ff;padding-top: 80px"
                          v-if="!formValidate.linkmanPhotoPath"></Icon>
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
            <Button type="primary" @click="open_ok" :loading="loading" style="width: 100px;">确定</Button>
        </div>
	</Modal>
    <Modal
        v-model="model2"
        :title="m_tit"
        :scrollable='false'
        @on-ok="m_ok"
        @on-cancel="m_cancel">
        <p>{{m_msg}}</p>
    </Modal><!-- m_tit:删除提示 选项提示   m_msg:是否删除所选数据 请确认选定要进行操作的数据   -->
</div>
</template>

<script>
import dataUrl from '../../assets/config.js'
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
	                    title: '托运方',
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
	                	title: '操作',
                        key: 'action',
                        width: 100,
                        align: 'center',
                        fixed: 'right',
                        render: function(h, params){
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function (e) {
                                        	e.stopPropagation();
                                        	var _self=this;
                                            //this.show(params.index);
                                            console.log(params)
                                            _self.openState="查看";
											// _self.theCity[0]=params.row.province;
											// _self.theCity[1]=params.row.city;
											// _self.theCity[2]=params.row.district;//theCity: ['浙江','宁波市','余姚市'],
											this.$set(_self.theCity,0,params.row.province);
											this.$set(_self.theCity,1,params.row.city);
											this.$set(_self.theCity,2,params.row.district);
											for(let key in params.row){
												//if(_self.hasOwnProperty(key)){
													_self.formValidate[key]=params.row[key];
												//}
											}
											_self.model1=true;
											//return false;
                                        }.bind(this)
                                    }
                                },'查看')
                            ]);
                        }.bind(this)
	                }],
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
            up_photo: '',//dataUrl.dataUrl.shipperinfo.upSitePhoto,//'http://localhost:8080/HaiSSMDemo/upSitePhoto.action',
            loading: false,
            m_tit: null,
            m_msg: null
		}

	},
	created: function(){
		this.userType=this.$cookies.get('roleID');
	},
	activated: function(){
        this.up_photo=dataUrl.dataUrl.shipperinfo.upSitePhoto;
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
            _self.loading=true;
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
                console.log("输出传入的参数");
                console.log(idata);
                $.ajax({
                    type: 'POST',
                    url: dataUrl.dataUrl.shipperinfo.selSiteByCompanyIdAndType,//'http://localhost:8080/HaiSSMDemo/selSiteByCompanyIdAndType.action',
                    data: idata,
                    cache: false,
                    success: function (data) {
                    	console.log("查看了ajax返回的数据");
                    	console.log(data);
                        _self.totalRecord = data.totalRecord;
                        _self.current = data.currentPage;
                        /*for (var i in data.dataList) {
                            data.dataList[i].linkmanPhotoPath = dataUrl.dataUrl.baseUrl.baseUrl + '/SitePhoto/' + data.dataList[i].linkmanPhotoPath;
                        }*/
                        _self.data1 = data.dataList;
                        _self.loading=false;
                        console.log(_self.data1);
                        
                    },
                    error: function(){
                    	_self.$Message.error("获取服务器数据失败");
                    	_self.loading=false;
                    }
                });
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
				_self.$set(_self.theCity,0,'浙江');
				_self.$set(_self.theCity,1,"宁波市");
				_self.$set(_self.theCity,2,"余姚市");
			//查看
			}else if(iText=="查看"){
				if(!_self.oneArr){
					_self.m_tit="选项提示";
					_self.m_msg="请选择一行数据进行查看，不可多选也不可不选";
					_self.model2=true;
					return ;	
				}
				_self.openState="查看";
				// _self.theCity[0]=_self.oneArr.province;
				// _self.theCity[1]=_self.oneArr.city;
				// _self.theCity[2]=_self.oneArr.district;//theCity: ['浙江','宁波市','余姚市'],
				_self.$set(_self.theCity,0,_self.oneArr.province);
				_self.$set(_self.theCity,1,_self.oneArr.city);
				_self.$set(_self.theCity,2,_self.oneArr.district);
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
					_self.m_tit="选项提示";
					_self.m_msg="请选择一行数据进行修改，不可多选也不可不选";
					_self.model2=true;
					return ;
				}
				_self.openState="修改";
				// _self.theCity[0]=_self.oneArr.province;
				// _self.theCity[1]=_self.oneArr.city;
				// _self.theCity[2]=_self.oneArr.district;
				_self.$set(_self.theCity,0,_self.oneArr.province);
				_self.$set(_self.theCity,1,_self.oneArr.city);
				_self.$set(_self.theCity,2,_self.oneArr.district);
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
            this.formValidate.linkmanPhotoPath = dataUrl.dataUrl.baseUrl.baseUrl + '/SitePhoto/' + data[0];
            this.$Message.success('照片上传成功');
        },
        error:function() {
            this.$Message.success('照片上传失败');
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
            _self.loading = true;
            console.log("进入open_ok");
            _self.$refs.formValidate.validate(function (valid) {
                if (_self.openState == "查看") {
                    //_self.loading = false;
                    //_self.model1 = false;
	        		//_self.$refs['formValidate'].resetFields();
	        		//_self.formValidate.linkmanPhotoPath=null;
	        		//_self.formValidate.remark=null;
                    //return ;
                    _self.loading=false;
                    _self.$refs['formValidate'].resetFields();
	        		_self.formValidate.linkmanPhotoPath=null;
	        		_self.formValidate.remark=null;
	        		_self.model1=false;
                }else if(_self.openState == "添加") {
                    if(valid) {
                    	_self.insertSite();
                    }else{
                    	_self.loading=false;
                    }
                }else{// if (_self.openState == "修改")
                	if(valid){
                		_self.updateSite();
                	}else{
                    	_self.loading=false;
                    }
                }
        		// _self.oneArr=null;
        		// _self.delArr=[];
        		
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
            _self.formValidate.type='0';
            console.log(_self.formValidate);
            _self.formValidate.id=null;
            $.ajax({
            	url: dataUrl.dataUrl.shipperinfo.insertSite,//'http://localhost:8080/HaiSSMDemo/insertSite.action',
            	data: _self.formValidate,
            	type: 'POST',
            	cache: false,
            	success: function(){
            		// _self.$refs['formValidate'].resetFields();
            		// _self.formValidate.linkmanPhotoPath=null;
            		// _self.formValidate.remark=null;
        			_self.$Message.info("完成添加");
        			_self.getAll();
        			//_self.model1=false;
        			_self.loading=false;
                    _self.$refs['formValidate'].resetFields();
	        		_self.formValidate.linkmanPhotoPath=null;
	        		_self.formValidate.remark=null;
	        		_self.model1=false;
            	},
            	error: function(){
            		_self.loading=false;
            		_self.$Message.error("添加失败");
            		//_self.model1=false;
            	}
            });
        },
        updateSite: function(){//更新逻辑
        	var _self=this;
        	_self.formValidate.companyId=parseInt(_self.$cookies.get("companyID"));
            _self.formValidate.province = _self.theCity[0];
            _self.formValidate.city = _self.theCity[1];
            _self.formValidate.district = _self.theCity[2];
            _self.formValidate.type='0';
        	$.ajax({
        		url: dataUrl.dataUrl.shipperinfo.updateSite,//'http://localhost:8080/HaiSSMDemo/updateSite.action',
        		data: _self.formValidate,
        		type: 'POST',
        		cache: false,
        		success: function(){
            		// _self.$refs['formValidate'].resetFields();
            		// _self.formValidate.linkmanPhotoPath=null;
            		// _self.formValidate.remark=null;
        			_self.$Message.info("完成修改");
        			_self.getAll();
        			//_self.model1=false;
        			_self.loading=false;
                    _self.$refs['formValidate'].resetFields();
	        		_self.formValidate.linkmanPhotoPath=null;
	        		_self.formValidate.remark=null;
	        		_self.model1=false;
        		},
        		error: function(){
            		_self.loading=false;
            		_self.$Message.error("更新失败");
            		//_self.model1=false;
        		}
        	});
        },
        delSiteBatch: function(){//删除逻辑
        	var _self=this;
        	console.log("进入删除逻辑");
        	console.log(_self.delArr);

        	if(_self.delArr.length>0){
        		_self.del_msg=true;
        		_self.m_tit="删除提示";
				_self.m_msg="请确认是否删除该"+_self.delArr.length+"条数据";
				_self.model2=true;
        		/*$.ajax({
        			url: dataUrl.dataUrl.shipperinfo.delSiteBatch,
        			traditional:true,
        			data: {arrays:_self.delArr},
        			type: 'POST',
        			cache: false,
        			success: function(){
        				_self.$Message.info("删除成功");
        				_self.delArr=[];
        				_self.getAll();
        				_self.del_msg=false
        			},
        			error: function(){
        				_self.$Message.info("删除失败");
        				_self.del_msg=false
        			}
        		});*/
        	}else{
				_self.m_tit="选项提示";
				_self.m_msg="请选择要删除的数据，至少选择一行数据";
				_self.model2=true;
        	}
        },
        /*postData: function(idata){
        	var _self=this;
        	consoel.log("在postData函数中");
        	$.ajax({
        		url: dataUrl.dataUrl.shipperinfo.insertSite,
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
        handleRow: function(data,index){
        	var _self=this;
        	// console.log(data);
        	// console.log(index);
        	//_self.data1[index].checked=true;
        	this.$refs.table.toggleSelect(index);
        },
        m_ok: function(){
        	var _self=this;
        	_self.model2=false;
        	_self.m_tit=null;
        	_self.m_msg=null;
        	if(_self.del_msg){
        		$.ajax({
        			url: dataUrl.dataUrl.shipperinfo.delSiteBatch,//'http://localhost:8080/HaiSSMDemo/delSiteBatch.action',
        			traditional:true,
        			data: {arrays:_self.delArr},
        			type: 'POST',
        			cache: false,
        			success: function(){
        				_self.$Message.info("删除成功");
        				_self.delArr=[];
                        _self.oneArr=null;
        				_self.getAll();
        				_self.del_msg=false
        			},
        			error: function(){
        				_self.$Message.info("删除失败");
        				_self.del_msg=false;
        			}
        		});
        	}
        },
        m_cancel: function(){
            var _self=this;
            _self.model2=false;
            _self.m_tit=null;
            _self.m_msg=null;
        }
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