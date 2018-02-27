<template>
	<div class="compinfo" ref="head">
		<nav>
	        <div>
	            <Button type="primary" @click="open($event)" v-if="userType===1">添加</Button>
	            <Tooltip content="删除所选企业信息" placement="bottom-start">
	                <Button type="error" style="margin-left: 20px;" @click="del" v-if="userType===1">删除</Button>
	            </Tooltip>
	            
	        </div>
	        <div>
	            <Input placeholder="请输入企业名称搜索" style="width: 200px;" v-model="searchText" @keyup.native.enter="search"></Input>
	            <Button type="primary" shape="circle" icon="ios-search" @click="search"></Button>
	            <Button type="primary" @click="getAll" style="margin-left:20px;">刷新</Button>
	        </div>
	    </nav>
	    <!--数据区块-->
	    <Table border stripe :columns="columns" :data="data1" @on-selection-all="chooseAll" @on-selection-change="sel_change" ellipsis></Table>
	    <!--\总页数-->
	    <Page :total="totalRecord" @on-change="changePage"
	          :page-size="page.pageNum" show-total show-elevator></Page>
	    <!-- 弹出层 --><!-- 有一点问题，之前这个50%是相对于自己页面的，现在变成整个窗口页面了 -->
    <Modal
            width="50%"
            v-model="modal1"
            :closable="false"
            @on-cancel="cancel"
    >
        <Steps :current="current" style="border-bottom: 1px dashed #e4e5e7;padding-bottom: 10px" v-if="userType===1">
            <Step title="上传企业信息" icon="person-add"></Step>
            <Step title="添加企业账户" icon="person-stalker"></Step>
        </Steps>
        <i-form ref="company" :model="company" :rules="ruleValidate" v-show="state" style="padding-top: 10px">
                    <!-- <FormItem label="企业名称" prop="name" span="24">
                        <Input placeholder="请输入企业名称" v-model="company.name" v-bind:disabled="openState==='查看'"></Input>
                    </FormItem> -->
            <Row>
                <Col span="24" id="nihao">
                    <FormItem label="企业名称" prop="name" span="24">
                        <Input placeholder="请输入企业名称" v-model="company.name" v-bind:disabled="openState==='查看'"></Input>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <i-col span="11">
                    <Form-Item label="营业执照" prop="businessLicence">
                        <i-Input placeholder="请输入营业执照" v-model="company.businessLicence" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-col>
                <i-col span="2" style='height:1px;'></i-col>
                <i-col span="11">
                    <Form-Item label="营业执照有效期">
                        <Date-Picker type="date" placeholder="选择日期" style="width: 100%"
                                     v-model="company.businLicenceTime" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="11">
                    <Form-Item label="经营许可证" prop="businessCertificate">
                        <i-Input placeholder="请输入经营许可证" v-model="company.businessCertificate" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-col>
                <i-col span="2" style='height:1px;'></i-col>
                <i-col span="11">
                    <Form-Item label="经营许证可有效期">
                        <Date-Picker type="date" placeholder="选择日期" style="width: 100%"
                                     v-model="company.businCertificateTime" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="11">
                    <Form-Item label="企业联系人" prop="linkman">
                        <i-Input placeholder="请输入企业联系人" v-model="company.linkman" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-col>
                <i-col span="2" style='height:1px;'></i-col>
                <i-col span="11">
                    <Form-Item label="企业联系人电话" prop="linkPhone">
                        <i-Input placeholder="请输入企业联系人电话" v-model="company.linkmanPhone" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="11">
                    <Form-Item label="企业法人代表" prop="represent">
                        <i-Input placeholder="请输入企业法人代表" v-model="company.represent" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-col>
                <i-col span="2" style='height:1px;'></i-col>
                <i-col span="11">
                    <Form-Item label="法人代表电话" prop="representPhone">
                        <i-Input placeholder="请输入企业法人代表电话" v-model="company.representPhone" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="24">
                    <Form-Item label="区域">
                        <Cascader :data="cityData" v-model="theCity" style="width: 100%" ></Cascader>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="24">
                    <Form-Item label="范围" prop="businessScope">
                        <i-Input v-model="company.businessScope" placeholder="请输入经营范围" type="textarea"
                                 :rows="4" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-col>
            </Row>
        </i-form>
        <i-Form ref="formCustom" :model="formCustom" :rules="ruleCustom" v-show="!state" style="padding-top: 10px">
            <Row>
                <i-col span="24">
                    <Form-Item label="公司账户" prop="name">
                        <i-Input type="text" v-model="formCustom.name" ></i-Input>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="24">
                    <Form-Item label="密码" prop="password">
                        <i-Input type="password" v-model="formCustom.password"></i-Input>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="24">
                    <Form-Item label="确认密码" prop="passCheck">
                        <i-Input type="password" v-model="formCustom.passCheck"></i-Input>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="24">
                    <Form-Item>
                        <i-Button type="primary" @click="submit('formCustom')">提交</i-Button>
                    </Form-Item>
                </i-col>
            </Row>
            
        </i-Form>
        <!--自定义-->
        <div slot="footer" v-show="state">
            <i-button type="text" style="width: 100px;" @click="cancel">取消</i-button>
            <i-button type="primary" @click="upMessage" :loading="loading" style="width: 100px;">{{openmodal2}}</i-button>
        </div>
    </Modal>
	    <Modal
	        v-model="modal2"
	        title="删除提示"
	        :del_arr='delArr'
	        @on-ok="ok_del"
	        @on-cancel="cancel_del">
	        <p v-if="onedel">是否删除该条数据</p>
	        <p v-else>是否确认删除{{delArr.length}}条数据</p>
	    </Modal>
	</div>
</template>

<script>
import dataUrl from '../../assets/config.js'
import cityDatas from '../../assets/cityData.json'
export default{
	name: 'CompInfo',
props:{
    indexloading: {
        type: Boolean,
        default: true
    }
},
    data:function() {
        var validatePass = function(rule, value, callback){
        	if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.formCustom.passCheck !== '') {
                    // 对第二个密码框单独验证
                    this.$refs.formCustom.validateField('passCheck');
                }
                callback();
            }
        };
        var validatePassCheck = function(rule, value, callback){
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.formCustom.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        var validateName = function(rule, value, callback) {
            if (!value) {
                callback(new Error('企业账户不能为空'));
            }else{
                //检查账号是否重复
                //console.log('检查账号是否重复');
                //var redata=1;
                $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.company.checkName,
                        data: {"name":value},
                        cache: false,
                        success: function (data) {
                            /*_self.$refs['formCustom'].resetFields();
                            _self.$Message.success('提交成功!');
                            _self.state = true;
                            _self.modal1 = false;
                            _self.getAll();*/
                            //redata=data;
                            //console.log('ajax请求');
                            //console.log(typeof data);
                            if(data[1]){
                                //console.log("判断:1");
                                callback();
                            }else{
                                //console.log("判断:0");
                                callback(new Error('账号已经存在！'));
                            }
                        }
                    });
            }
        };

        return {
            userType:'',
            formCustom: {
                companyId:'',
                password: '',
                passCheck: '',
                name: ''
            },
            ruleCustom: {
                password: [
                    { validator: validatePass.bind(this), trigger: 'blur' }
                ],
                passCheck: [
                    { validator: validatePassCheck.bind(this), trigger: 'blur' }
                ],
                name: [
                    { validator: validateName.bind(this), trigger: 'blur' }
                    
                ]
            },
            current: 0,/*添加企业信息步骤*/
            state:true,/*步骤状态*/
            columns: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center',
                    fixed: 'left'
                },
                {
                    title: '企业名称',
                    key: 'name',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '营业执照编号',
                    key: 'businessLicence',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '营业执照有效期',
                    key: 'businLicenceTime',/*realLinceTime*/
                    width: 200,
                    align: 'center',
                    sortable: true
                },
                {
                    title: '经营许可证',
                    key: 'businessCertificate',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '经营许可证有效期',
                    key: 'businCertificateTime',/*realCertTime这里使用了两个新值来代替原来的time，kk将其改成了html中的值*/
                    width: 200,
                    align: 'center',
                    sortable: true
                },
                {
                    title: '经营范围',
                    key: 'businessScope',
                    width: 200,
                    align: 'center',
                    ellipsis: true
                },
                {
                    title: '法人代表',
                    key: 'represent',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '法人代表联系电话',
                    key: 'representPhone',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '企业联系人',
                    key: 'linkman',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '企业联系人电话',
                    key: 'represent',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '省',
                    key: 'province',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '市',
                    key: 'city',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '区',
                    key: 'county',
                    width: 200,
                    align: 'center'
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 150,
                    fixed: 'right',
                    render: function (h, params) {
                        if(this.userType===1){
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: function () {
                                            this.change(params.index);
                                            this.openmodal2='确认';
                                        }.bind(this)
                                    }
                                }, '修改'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function () {
                                            //this.deleteOne(params.index);
                                            //console.log('删除程序');
                                            this.delone=this.data1[params.index].id;
                                            this.del_index(this.data1[params.index].id);
                                            this.onedel=true;
                                        }.bind(this)
                                    }
                                }, '删除')
                            ]);
                        }else{
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: function () {
                                            this.openState = "查看";
                                            this.company = JSON.parse(JSON.stringify(this.data1[params.index]));
                                            this.modal1 = true;
                                        }.bind(this)
                                    }
                                }, '查看')
                            ]);
                        }

                    }.bind(this)
                }],
            company: {
                name: '',
                businessLicence: '',
                businLicenceTime: '',
                businessCertificate: '',
                businCertificateTime: '',
                linkman: '',
                linkPhone: '',
                represent: '',
                representPhone: '',
                province: '',
                city: '',
                county: '',
                businessScope: ''
            },
            ruleValidate: {
                name: [
                    {required: true, message: '公司名称不能为空', trigger: 'blur'}
                ],
                businessLicence: [
                    {required: true, message: '营业执照不能为空', trigger: 'blur'}
                ]
            },
            theCity: ['浙江','杭州市','江干区'],
            data1: [],
            allChecked: '',
            theChecked: [],
            goodType: [],
            totalRecord: 0,
            content: {},
            that: this,
            page: {
                current: 1,
                pageNum: 20
            },
            op: 0,
            title: '',
            modal1: false,
            position: 0,
            searchText: '',
            loading: false,
            openState: '',
            cityData: [],
            modal2: false,
            onedel:false,
            delone:'',
            delArr:[],
            openmodal2:'下一步'
        }
    },
    created: function () {
        //document.body.removeChild(document.getElementById('tloading'));
        var _self = this;
        _self.getAll();
        _self.userType=parseInt(this.$cookies.get("roleID"));
        /*var textState=JSON.parse(_self.$cookies.get("state"));
        if(textState!=null){
            if(textState.ID==0){
                window.location.href="../../state.html";
            }else if(textState.ID==1){
                if(textState.roleID==1){
                    _self.userType=1;
                }else if(textState.roleID==2){
                    _self.userType=2;
                }
                _self.getAll();
            }
        }else {
            window.location.href="../../state.html";
        }*/
        /// 获得地区
        // $.getJSON('../../cityData.json',function (data) {
        //     _self.cityData = data;
        // });
        _self.cityData=cityDatas;
        //console.log(_self.cityData);
    },
    mounted: function () {
        //this.$refs.head.style.display = 'block';
        //console.log(this.$refs.company);
        //console.log(this.company);
        //console.log(this.ruleValidate);
this.indexloading();
    },
        beforeDestroy: function(){
            this.$emit('getloading',true);
        },
    methods: {
indexloading: function(){
    this.$emit('getloading',false);
},
        submit: function (name) {
            var _self=this;
            _self.$refs[name].validate(function (valid) {
                if (valid) {
                    _self.postData(_self, dataUrl.dataUrl.carrier.insert, _self.company);
                } else {
                    _self.$Message.error('表单验证失败!');
                }
            });
        },
        del:function() {
            var _self = this;
            //console.log("所选个数："+_self.delArr.length);

            if(_self.delArr.length>0){//kk加了一个判断
                //console.log("进入modal2");
                _self.modal2=true;
                /*if(ensure_del){
                    ok_del();
                }*/
                
            }
            
        },
        del_index:function(n){
            var _self=this;
            _self.modal2=true;

            
        },
        ok_del:function(){
            var _self=this;
            if(_self.onedel){
                //console.log('要删除一条');
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.carrier.del+_self.delone,
                    cache: false,
                    success: function (data) {
                        _self.getAll();
                        _self.$Message.info('刪除成功');
                    }
                });
                _self.delone='';
                _self.onedel=false;
            }else{
                //console.log('想删除多条');
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.carrier.del+_self.delArr,
                    cache: false,
                    success: function (data) {
                        _self.delArr = [];
                        _self.getAll();
                        _self.$Message.info('刪除成功');
                    }
                });
                _self.delArr=[];
            }
            
        },
            getData:function() {
                var _self = this;
                _self.$Loading.start();
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.carrier.all,
                    cache: false,
                    data: _self.page,
                    success: function (data) {
                        _self.$Loading.finish();
                        if (typeof data == "object") {
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            for (var key in data.dataList) {
                                data.dataList[key].createTime = _self.format(data.dataList[key].createTime);
                            }
                            _self.data = data.dataList;
                        } else {
                            _self.data = [];
                        }
                    }
                });
            },
            add0:function(m) {
                return m < 10 ? '0' + m : m
            },
            format:function(nS) {
                var time = new Date(parseInt(nS));
                var y = time.getFullYear();
                var m = time.getMonth() + 1;
                var d = time.getDate();
                return y + '-' + this.add0(m) + '-' + this.add0(d);
            },
       /* moreDelete: function () {
            var _self = this;
            if (this.theChecked.length > 0) {
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.carrier.del+_self.theChecked,
                    cache: false,
                    success: function (data) {
                        alert('删除'+_self.theChecked.length+'条数据');
                        _self.getAll();
                    }
                });
            }
        },*/
        /*deleteOne: function (index) {
            var arr = [this.data1[index].id];
            var _self = this;
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.carrier.del + arr,
                cache: false,
                success: function (data) {
                    alert('删除一条数据');
                    _self.getAll();
                }
            });
        },*/
        cancel_del:function(){
            this.modal2=false;
            this.delone='';
            this.onedel=false;
            this.current=0;
        },


        reset: function (name) {
            this.$refs.name.resetFields();
        },
        search: function() { // 查询
            var _self = this;
            if (this.searchText.replace(/\s/g,'').length < 1) {
                    alert('搜索内容不可为空');
            } else {
	            $.ajax({
	                type: 'GET',
	                url: dataUrl.dataUrl.carrier.search + encodeURI(this.searchText),
	                cache: false,
	                success: function (data) {
	                    if (data != null && data.dataList != undefined) {
	                        
	                        _self.data1 = data.dataList;
	                        _self.totalRecord = data.totalRecord;
	                        _self.theChecked = [];
	                        for (var i = 0; i < _self.data1.length; i++) {
	                            /*d = new Date(data.dataList[i].businLicenceTime);
	                            _self.data1[i].realLinceTime = d.getFullYear()+'-'+(d.getMonth()+ 1)+'-'+ (d.getDate());
	                            d = new Date(data.dataList[i].businCertificateTime);
	                            _self.data1[i].realCertTime = d.getFullYear()+'-'+(d.getMonth()+ 1)+'-'+ (d.getDate());*/

	                            _self.data1[i].businCertificateTime= _self.format(_self.data1[i].businCertificateTime);
	                            _self.data1[i].businLicenceTime= _self.format(_self.data1[i].businLicenceTime);
	                        }
	                    }else{
	                        _self.data1 = [];//
	                    }
	                }
	            });
        	}
        },
        upMessage: function () {
            var _self = this;
            _self.loading = true;
            _self.$refs.company.validate( function(valid){
                if(_self.openState==="查看"){
                    _self.loading = false;
                    _self.modal1 = false;
                }else {
                    if (valid) {
                        _self.company.province = _self.theCity[0];
                        _self.company.city = _self.theCity[1];
                        _self.company.county = _self.theCity[2];
                        //if (_self.company.businLicenceTime instanceof Date) {
                         _self.company.businLicenceTime = _self.format(_self.company.businLicenceTime);
                        
                        _self.company.businCertificateTime = _self.format(_self.company.businCertificateTime);
                        
                        if (_self.openState == "添加") {
                            //_self.postData(_self, dataUrl.dataUrl.carrier.insert, _self.company);
                            _self.state=false;//下一步，不提交
                        }
                        else if (_self.openState == "修改") {
                            _self.postData(_self, dataUrl.dataUrl.carrier.upDate, _self.company);
                        }
                    } else {
                        _self.loading = false;
                    }
                }
            });
            _self.current=0;
        },
        add0:function(m) {
            return m < 10 ? '0' + m : m
        },
        format:function(nS) {/*
            var time = new Date(nS);
            var y = time.getFullYear();
            var m = time.getMonth() + 1;
            var d = time.getDate();
            return y + '-' + this.add0(m) + '-' + this.add0(d);*/
            if(typeof nS=="object"&&nS!==null||typeof nS=="number"){
                //console.log(nS)
                var time = new Date(nS);
                var y = time.getFullYear();
                var m = time.getMonth() + 1;
                var d = time.getDate();
                //console.log(y + '-' + this.add0(m) + '-' + this.add0(d));
                return y + '-' + this.add0(m) + '-' + this.add0(d);
            }/*else if(typeof nS=="number"){
                return nS;
            }*/else{
                return null;
            }
        },
        month: function (date) {
            var m ='';
            if (date.getMonth()+1 < 10) {
                m = "0"+(date.getMonth()+1);
            } else {
                m = date.getMonth()+1;
            }
            return date.getFullYear() +'-'+ m + '-' + date.getDate();
        },
        cancel: function () {
 			if(this.$refs.company!==undefined){
 				this.$refs.company.resetFields();
 			}
            if(this.$refs.company!==undefined){
 				this.$refs.formCustom.resetFields();
 			}
            this.modal1 = false;
            this.loading = false;
            this.state=true;
        },
        open: function(obj) {
            this.openmodal2='下一步';
            if ($.trim(obj.currentTarget.innerText)== "添加") {
                for (var key in this.company) {
                    this.company[key] = '';
                }
                this.openState = $.trim(obj.currentTarget.innerText);
                //console.log(this.openState);
            }
            //this.$refs['formValidate'].resetFields();
            this.modal1 = true;
        },
        /*chooseAll: function (selection) { // 全选
            this.theChecked = [];
            for (var i = 0 ; i < selection.length; i++) {
                this.theChecked.push(selection[i].id);
            }
        },*/
        change: function (index) {
            this.openState = "修改";
            this.company = JSON.parse(JSON.stringify(this.data1[index]));
            this.modal1 = true;
            // this.op = 1;
        },
        chooseAll:function(data) {
            var _self = this;
            _self.delArr=[];
            if(data.length){
                for (var i in data) {
                    _self.delArr.push(data[i].id);
                }
            }
            
        },
        sel_change:function(data){
            var _self = this;
            _self.delArr=[];
            if(data.length){

                for (var i in data) {
                    _self.delArr.push(data[i].id);
                }
            }
            
        },
        time: function (times) {
            var d = new Date(parseInt(times));
            return d.getFullYear()+'/'+(d.getMonth()+1)+'/'+d.getDate();
        },
        changePage: function (cur) {
            //  跳转页面
            // 分页跳转
            this.page.current = cur;
            this.getAll();
        },
        getAll: function () {
            var _self = this;
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.carrier.all,
                data:_self.page,
                cache: false,
                success: function (data) {
                    if (data != null && data.dataList != undefined) {
                        var d;
                        _self.data1 = data.dataList;
                        _self.totalRecord = data.totalRecord;
                        _self.theChecked = [];
                        for (var i = 0; i < _self.data1.length; i++) {
                            _self.data1[i].realLinceTime = _self.format(data.dataList[i].realLinceTime);
                            _self.data1[i].realCertTime = _self.format(data.dataList[i].realCertTime);
                            _self.data1[i].businCertificateTime= _self.format(data.dataList[i].businCertificateTime);
                            _self.data1[i].businLicenceTime= _self.format(data.dataList[i].businLicenceTime);
                        }
                    } else {
                        _self.data1 = [];
                    }
                }
            });
            _self.searchText='';
        },
        callback: function (data) {
            this.page.current = data;
            this.getAll();
        },
        postData: function(_self, url, data) {
            $.ajax({
                type: 'POST',
                url: url,
                data:data,
                cache: false,
                success: function (data) {
                    if(_self.openState == "添加"){
                        _self.state=false;
                        _self.loading = false;
                        _self.current += 1;
                        $.get(dataUrl.dataUrl.company.getID+_self.company.name,function (data) {
                            _self.formCustom.companyId=data.dataList[0].id;
                            $.ajax({
                                type: 'GET',
                                url: dataUrl.dataUrl.company.add,
                                data:_self.formCustom,
                                cache: false,
                                success: function (data) {	
    					            /*if(this.$refs.formCustom!==undefined){
    					 				_self.$refs.formCustom.resetFields();
    					 			}
                                    _self.$Message.success('提交成功!');
                                    _self.state = true;
                                    _self.modal1 = false;
                                    _self.getAll();*/

                                    _self.$refs['formCustom'].resetFields();
                                    _self.$Message.success('提交成功!');
                                    _self.state = true;
                                    _self.modal1 = false;
                                    _self.getAll();
                                }
                            });
                        });


                    }else{
                        _self.loading = false;
                        _self.$Message.info('上传成功');
                        _self.modal1 = false;
                        _self.getAll();
                    }
                    _self.current=0;
                }
            });
        }
    }
	}
</script>

<style scoped>
/*@import '../../common/someinfo.css'好像也有吧*/
nav {
    padding: 10px 50px;
    display: flex;
    justify-content: space-between;
    justify-items: center;
}

nav div:first-child button {
    margin: 0 10px;
}
.ivu-table-cell{
    padding: 0;
}
/*不明白下面这个样式为什么不起作用，所以放到resetcss里了，这样很不好*/
/*   .compinfo .ivu-modal-content {
        margin-top: 20px;
    }

    .compinfo .ivu-form-item-content {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .compinfo .ivu-row {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }*/

/*不明白怎么td高度就不一样高了，*/
.ivu-table td, .ivu-table th{
	height: 48px;
}
</style>