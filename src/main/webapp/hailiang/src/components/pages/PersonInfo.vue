<template>
<div class="personinfo">	
    <nav>
        <div>
            <!-- <i-Button type="primary" @click="open($event)" v-if="userType===3">添加</i-Button>
            <Tooltip content="删除所选人员信息" placement="bottom-start">
                <i-Button type="error" @click="del" v-if="userType===3">删除</i-Button>
            </Tooltip> -->


            <Button type="primary" @click="open($event)">添加</Button>
            <Button type="primary" @click="open($event)">修改</Button>
            <Button type="primary" @click="open($event)">查看</Button>
            <!-- <Button type="primary" @click="">打印</Button> -->
            <Button type="error" @click="deldemo">删除</Button>
            
        </div>
        <div>
            <i-Input placeholder="请输入姓名..." style="width: 300px" v-model.trim="searchText" @keyup.native.enter="search"></i-Input>
            <i-Button type="primary" shape="circle" icon="ios-search" @click="search"></i-Button>
            <i-Button type="primary" @click="getData" style="margin-left:20px;">刷新</i-Button>
        </div>
    </nav>
    <i-Table ref='table' border stripe :columns="columns" :data="data" @on-selection-change="chooseAll" @on-row-click="handleRow"></i-Table>
    <Page :total="totalRecord" show-total
          show-elevator :current="page.current" @on-change="next"
          :page-size="page.pageNum" size='small'></Page>
    <Modal
            v-model="modal1"
            width="50%"
            :closable="false"
            @on-cancel="cancel"
    > <!-- 添加一个on-cancel事件 -->
        <i-Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
            <Row>
                <i-Col span="11">
                    <Form-Item label="姓名" prop="name">
                        <i-Input v-model="formValidate.name" placeholder="请输入姓名"
                                 v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                    <Form-Item label="性别" prop="sex">
                        <Radio-Group v-model="formValidate.sex">
                            <Radio label="男" v-bind:disabled="openState==='查看'">男</Radio>
                            <Radio label="女" v-bind:disabled="openState==='查看'">女</Radio>
                        </Radio-Group>
                    </Form-Item>
                    <Form-Item label="身份证" prop="idNum">
                        <i-Input placeholder="请输入身份证" v-model="formValidate.idNum" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                    <Form-Item label="出生日期" prop="birthday">
                        <Date-Picker type="date" placeholder="选择日期" :value='formValidate.birthday' v-model="formValidate.birthday"
                                     v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                    <Form-Item label="联系电话" prop="phone">
                        <i-Input placeholder="请输入联系电话" v-model="formValidate.phone"
                                 v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                    <Form-Item label="所在单位" prop="compayId">
                        <i-Select placeholder="请选择单位" v-model="formValidate.compayId" label-in-value
                                  @on-change="companyChange" v-bind:disabled="openState==='查看'">
                            <i-Option v-for="obj in companyList" :value="obj.id">{{obj.name}}</i-Option>
                        </i-Select>
                    </Form-Item>
                    <Form-Item label="人员状态" prop="peopleStatus">
                        <i-Select placeholder="请选择人员状态" v-model="formValidate.peopleStatus"
                                  v-bind:disabled="openState==='查看'">
                            <i-Option value="1">正式人员</i-Option>
                            <i-Option value="2">实习人员</i-Option>
                            <i-Option value="3">离职人员</i-Option>
                        </i-Select>
                    </Form-Item>
                    <Form-Item label="人员类型" prop="peopleType">
                        <i-Select placeholder="请选择人员类型" v-model="formValidate.peopleType" @on-change="changeType"
                                  v-bind:disabled="openState==='查看'">
                            <i-Option value="1">驾驶员</i-Option>
                            <i-Option value="2">押运员</i-Option>
                        </i-Select>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11" style="display: flex;align-items: center;justify-content: center;">
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
                            :action="uploadUrl"
                            style="width:200px;" v-bind:disabled="openState==='查看'"
                            v-if="openState!=='查看'">
                        <div style="width: 200px;height:230px;">
                            <img :src="formValidate.picUser" v-show="formValidate.picUser"
                                 style="background-position: 100%;height: 100%;width: 100%;" v-bind:disabled="openState==='查看'">
                            <Icon type="ios-cloud-upload" size="52" style="color: #3399ff;padding-top: 80px"
                                  v-if="!formValidate.picUser"></Icon>
                        </div>
                    </Upload>
                    <div v-else style="width: 200px;height:230px;">
                        <img :src="formValidate.picUser" v-show="formValidate.picUser"
                                 style="background-position: 100%;height: 100%;width: 100%;">
                    </div>
                </i-Col>
            </Row>
            <Row v-show="isDriver">
                <i-Col span="11">
                    <Form-Item label="驾驶证号" prop="driveLicence">
                        <i-Input placeholder="输入驾驶证" v-model="formValidate.driveLicence"
                                 v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="资格证有效期" prop="driLicenceTime">
                        <Date-Picker type="date" placeholder="选择日期" :value="formValidate.driLicenceTime" v-model="formValidate.driLicenceTime"
                                     v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="从业资格证" prop="drivezigezheng">
                        <i-Input placeholder="请输入从业资格证" v-model="formValidate.drivezigezheng"
                                 v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="有效期" prop="driverzigezhengTime">
                        <Date-Picker type="date" placeholder="选择日期"
                                     :value="formValidate.driverzigezhengTime"
                                     v-model="formValidate.driverzigezhengTime"
                                     v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="24">
                    <Form-Item label="准驾类型" prop="driverType">
                        <i-Select placeholder="请选择" v-model="formValidate.driverType" v-bind:disabled="openState==='查看'">
                            <i-Option value="A">A</i-Option>
                            <i-Option value="B">B</i-Option>
                            <i-Option value="C">C</i-Option>
                        </i-Select>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="24">
                    <Form-Item label="现住地址" prop="adresss">
                        <i-Input type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入现住地址..."
                                 v-model="formValidate.adresss" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
            </Row>
        </i-Form>
        <div slot="footer">
            <i-button type="text" style="width: 100px;" @click="cancel">取消</i-button>
            <i-button type="primary" @click="upMessage" :loading="loading" style="width: 100px;">确定</i-button>
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
    <Modal
        v-model="model2"
        :title="m_tit"
        :scrollable='false'
        @on-ok="m_ok"
        @on-cancel="m_cancel">
        <p>{{m_msg}}</p>
    </Modal>
</div>
</template>

<script>
import dataUrl from '../../assets/config.js'
export default{
	naem: 'PersonInfo',
props:{
    indexloading: {
        type: Boolean,
        default: true
    }
},
    data:function() {
        var checkidNum=function(rule, value, callback){
            if(value.length!=18){
                callback(new Error('请确认身份证为18位'));
            }else{
                callback();
            }
        }
        return {
            userType: '',
            uploadUrl:dataUrl.dataUrl.person.upImg,
            companyList: [],
            totalRecord: 0,
            page: {
                current: 1, /*当前页*/
                pageNum: 15/*每页数据量*/,
            },
            isDriver: false, // 判断是否为司机
            formValidate: {
                id: '',
                name: '',
                idNum: '',
                sex: '',
                birthday: '',
                phone: '',
                compayId: '',
                peopleStatus: '',
                peopleType: '',
                driveLicence: '',
                driLicenceTime: '',
                drivezigezheng: '',
                driverzigezhengTime: '',
                driverType: '',
                pictureName: '',
                pictureUrl: '',
                adresss: '',
                picUser: ''
            },
            ruleValidate: {
                name: [
                    {required: true, message: '姓名不能为空', trigger: 'blur'}
                ],
                sex: [
                    {required: true, message: '请选择性别', trigger: 'change'}
                ],
                // idNum: [
                //     {required: true, message: '请输入身份证号码', trigger: 'blur'},
                //     {validator: checkidNum.bind(this) ,trigger: 'blur'}
                // ],
                idNum: [
                    {required: true, message: '请输入身份证号码', trigger: 'blur'},
                    {validator: checkidNum.bind(this) ,trigger: 'blur'}
                ],
                phone: [
                    {required: true, message: '手机号不能为空', trigger: 'blur'},
                    { type: 'number', message: '请输入数字格式', trigger: 'blur', transform(value) {
                        return Number(value);
                    }}
                ],
                compayId: [
                    {required: true, message: '请选择单位', trigger: 'change', type: 'number'}
                ],
                peopleStatus: [
                    {required: true, message: '请选择状态', trigger: 'change'}
                ],
                peopleType: [
                    {required: true, message: '请选择类型', trigger: 'change'}
                ],
                drivezigezheng: [
                    {required: true, message: '从业资格证不能为空', trigger: 'blur'}
                ],
                driverType: [
                    {required: true, message: '请选择类型', trigger: 'change'}
                ],
                adresss: [
                    {required: true, message: '地区不能为空', trigger: 'blur'}
                ]
            },
            modal1: false,
            columns: [
                {
                    type: 'index',
                    width: 40,
                    align: 'center',
                    fixed: 'left'
                },
                {
                    type: 'selection',
                    width: 60,
                    align: 'center',
                    fixed: 'left'
                },
                {
                    title: '姓名',
                    key: 'name',
                    align: 'center',
                    width: 100
                },
                {
                    title: '性别',
                    key: 'sex',
                    align: 'center',
                    width: 100
                },
                {
                    title: '出生日期',
                    key: 'birthday',
                    align: 'center',
                    sortable: true,
                    width: 120
                },
                {
                    title: '所在单位',
                    align: 'center',
                    key: 'reserveOne',
                    width: 200
                },
                {
                    title: '身份证',
                    key: 'idNum',
                    align: 'center',
                    width: 200
                },
                {
                    title: '联系电话',
                    key: 'phone',
                    align: 'center',
                    width: 120
                },
                {
                    title: '类别',
                    key: 'type',
                    align: 'center',
                    width: 100
                },
                {
                    title: '驾驶证',
                    key: 'driveLicence',
                    align: 'center',
                    width: 100
                },
                {
                    title: '有效期',
                    key: 'driLicenceTime',
                    sortable: true,
                    align: 'center',
                    width: 100
                },
                {
                    title: '从业资格证',
                    key: 'drivezigezheng',
                    align: 'center',
                    width: 120
                },
                {
                    title: '有效期',
                    key: 'driverzigezhengTime',
                    sortable: true,
                    align: 'center',
                    width: 100
                },
                {
                    title: '地址',
                    align: 'center',
                    key: 'adresss',
                    width: 200
                },
                {
                    title: '操作',
                    key: 'action1',
                    width: 100,
                    align: 'center',
                    fixed: 'right',
                    render: function (h, params) {
                        
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
                                            console.log(this.data[params.index]);
                                            this.formValidate = JSON.parse(JSON.stringify(this.data[params.index]));
                                            this.modal1 = true;
                                        }.bind(this)
                                    }
                                }, '查看')
                            ]);
                    }.bind(this)                        
                }
            ],
            data: [],
            openState: '',
            loading: false,
            delArr: [],
            searchText: '',
            onedel:false,//这个就不需要了
            delone:null,
            modal2:false,
            spinShow:true,
            model2: false,
            m_tit: '',
            m_msg: '',
            del_msg: false
        }
    },
    methods: {
        indexloading: function(){
            this.$emit('getloading',false);
        },
        search:function() {
            var _self = this;
            if (_self.searchText.replace(/\s/g, '').length < 1) {
                alert('搜索内容不可为空');
                //getData();
            } else {
                if(_self.userType===1||_self.userType===2){//JSON.parse(Cookies.get("state")).roleID
                       $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.person.search + encodeURI(this.searchText.replace(/\s/g, '')),
                        cache: false,
                        success: function (data) {
                            if (typeof data == "object") {
                                _self.totalRecord = data.totalRecord;
                                _self.page.current = data.currentPage;
                                for (var i in data.dataList) {
                                    if (data.dataList[i].peopleType == 1) {
                                        data.dataList[i].type = '驾驶员';
                                    } else if (data.dataList[i].peopleType == 2) {
                                        data.dataList[i].type = '押运员';
                                    }
                                    data.dataList[i].picUser = "http://localhost:8080" + '/pic/' + data.dataList[i].pictureName;
                                    data.dataList[i].birthday = _self.format(data.dataList[i].birthday);
                                    data.dataList[i].driLicenceTime = _self.format(data.dataList[i].driLicenceTime);
                                    data.dataList[i].driverzigezhengTime = _self.format(data.dataList[i].driverzigezhengTime);
                                }
                                _self.data = data.dataList;
                            } else {
                                _self.data = [];
                            }
                        }
                    }); 
                }else{
                    var idata={};
                    console.log(_self.page);
                    //delete _self.page.id;
                    for(var key in _self.page){
                        if(key!=='id'){
                            idata[key]=_self.page[key];
                            console.log(key);
                        }

                    }
                    //console.log(JSON.parse(Cookies.get("state")));
                    idata.companyId=_self.$cookies.get("companyID");//JSON.parse(Cookies.get("state")).companyID;
                    idata.name=_self.searchText;
                    //console.log(idata);
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.person.searchCompany,
                        cache: false,
                        data:idata,
                        success: function (data) {
                            if (typeof data == "object") {
                                _self.totalRecord = data.totalRecord;
                                _self.page.current = data.currentPage;
                                for (var i in data.dataList) {
                                    if (data.dataList[i].peopleType == 1) {
                                        data.dataList[i].type = '驾驶员';
                                    } else if (data.dataList[i].peopleType == 2) {
                                        data.dataList[i].type = '押运员';
                                    }
                                    data.dataList[i].picUser = "http://localhost:8080" + '/pic/' + data.dataList[i].pictureName;
                                    data.dataList[i].birthday = _self.format(data.dataList[i].birthday);
                                    data.dataList[i].driLicenceTime = _self.format(data.dataList[i].driLicenceTime);
                                    data.dataList[i].driverzigezhengTime = _self.format(data.dataList[i].driverzigezhengTime);
                                }
                                _self.data = data.dataList;
                            } else {
                                _self.data = [];
                            }
                        }
                    });
                }
                
            }
        },
        getRightDate:function(obj){
            for(var i in obj){
                obj[i].birthday = _self.format(obj[i].birthday);
                obj[i].driLicenceTime = _self.format(obj[i].driLicenceTime);
                obj[i].driverzigezhengTime = _self.format(obj[i].driverzigezhengTime);
            }
        },
        // 公司名称
        companyChange:function(company) {
            this.formValidate.reserveOne = company.label;
        },
        changeType:function(index) {
            if (index == 1) {
                this.isDriver = true;
            } else {
                this.isDriver = false;
                this.formValidate.driveLicence = '';
                this.formValidate.driveLicenceTime = '';
            }
        },/*
        del:function() {
            var _self = this;
            if(_self.delArr.length>0){
                $.ajax({
                    type: 'GET',
                    url: dataUrl.person.del + _self.delArr,
                    cache: false,
                    success: function (data) {
                        _self.delArr = [];
                        _self.getData();
                        _self.$Message.info('刪除成功');
                    }
                });
                //_self.delArr = [];
            }
            
        },*/
            del:function() {
                var _self = this;
                /*if(_self.delArr.length>0){
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.safeCard.del+_self.delArr,
                        cache: false,
                        success: function (data) {
                            _self.delArr = [];
                            _self.getData();
                            _self.$Message.info('刪除成功');
                        }
                    });
                    _self.delArr=[];
                }*/
                if(_self.delArr.length>0){
                    _self.modal2=true;
                }
                
            },
            del_index:function(n){
                var _self=this;
                _self.modal2=true;

                
            },
            ok_del:function(){
                var _self=this;
                if(_self.onedel){
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.person.del+_self.delone,
                        cache: false,
                        /*success: function (data) {
                            _self.getData();
                            _self.$Message.info('刪除成功');
                        },*/
                        success: function(){
                            _self.$Message.info("删除成功");
                            _self.delArr=[];
                            _self.delone=null;
                            _self.getData();
                            _self.del_msg=false
                        },
                        error: function(){
                            _self.$Message.info("删除失败");
                            _self.del_msg=false;
                        }
                    });
                    _self.delone='';
                    _self.onedel=false;
                }else{
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.person.del+_self.delArr,
                        cache: false,
                        /*success: function (data) {
                            _self.delArr = [];
                            _self.getData();
                            _self.$Message.info('刪除成功');
                        }*/
                        success: function(){
                            _self.$Message.info("删除成功");
                            _self.delArr=[];
                            _self.delone=null;
                            _self.getData();
                            _self.del_msg=false
                        },
                        error: function(){
                            _self.$Message.info("删除失败");
                            _self.del_msg=false;
                        }
                    });
                    _self.delArr=[];
                }
                
            },
            cancel_del:function(){
                this.modal2=false;
                this.delone='';
                this.onedel=false;
                this.$refs['formValidate'].resetFields();
            },
            chooseAll:function(data) {
                var _self = this;
                console.log(data);
                _self.delArr=[];//放置所以被选择行的id
                _self.delone=null;//放置单独的一行的所有信息
                if(data.length){
                    for (var i in data) {
                        _self.delArr.push(data[i].id); 
                    }
                }
                if(data.length==1){
                    _self.delone=data[0];
                }
                
            },
            /*sel_change:function(data){
                var _self = this;
                //console.log(data);
                _self.delArr=[];
                if(data.length){

                    for (var i in data) {
                        _self.delArr.push(data[i].id);
                    }
                }
                
            },*/
        success:function(data) {
            this.formValidate.pictureName = data[0];
            this.formValidate.pictureUrl = data[1];
            this.loadingStatus = false;
            this.formValidate.picUser = "http://localhost:8080" + '/pic/' + this.formValidate.pictureName;
            this.$Message.success('上传成功');
        },
        error:function() {
            this.$Message.success('上传失败');
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
        next:function(data) {
            this.page.current = data;
            this.getData();
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
                console.log(nS)
                var time = new Date(nS);
                var y = time.getFullYear();
                var m = time.getMonth() + 1;
                var d = time.getDate();
                console.log(y + '-' + this.add0(m) + '-' + this.add0(d));
                return y + '-' + this.add0(m) + '-' + this.add0(d);
            }/*else if(typeof nS=="number"){
                return nS;
            }*/else{
                return null;
            }
        },
        open:function(obj) {
            var iText=null;
            var _self=this;
            console.log(_self.delone);
            console.log(_self.delone);
            //先默认添加，打开图片上传
            iText=event.currentTarget.innerText;
            if (iText == "添加") {
                this.$refs['formValidate'].resetFields();
                this.formValidate.picUser = null;
                this.openState = "添加";
                if (this.formValidate.id != undefined)
                    this.formValidate.id = '';
            }else if(iText=="查看"){
                if(!_self.delone){
                    _self.m_tit="选项提示";
                    _self.m_msg="请选择一行数据进行查看，不可多选也不可不选";
                    _self.model2=true;
                    return ;    
                }

                _self.openState="查看";
                for(let key in _self.delone){
                    _self.formValidate[key]=_self.delone[key];
                }
                _self.formValidate.picUser = "http://localhost:8080" + '/pic/' + _self.delone.pictureName;
            }else if(iText=="修改"){
                if(!_self.delone){
                    _self.m_tit="选项提示";
                    _self.m_msg="请选择一行数据进行修改，不可多选也不可不选";
                    _self.model2=true;
                    return ;
                }
                _self.openState="修改";
                for(let key in _self.delone){
                    _self.formValidate[key]=_self.delone[key];
                }
                _self.formValidate.picUser = "http://localhost:8080" + '/pic/' + _self.delone.pictureName;
            }
            this.modal1 = true;
        },
        getData:function() {
            var _self = this;
            _self.delArr=[];
            _self.delone=null;
            _self.$Loading.start();
            if (_self.userType === 3) {
                //根据公司ID查询数据
                _self.page.id=_self.$cookies.get("companyID");//JSON.parse(Cookies.get("state")).companyID;
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.company.getCompanyPeople,
                    data:_self.page,
                    cache: false,
                    success: function (data) {
                        _self.$Loading.finish();
                        console.log(data);
                        if (typeof data == "object") {
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            for (var i in data.dataList) {
                                if (data.dataList[i].peopleType == 1) {
                                    data.dataList[i].type = '驾驶员';
                                } else if (data.dataList[i].peopleType == 2) {
                                    data.dataList[i].type = '押运员';
                                }
                                data.dataList[i].picUser = "http://localhost:8080" + '/pic/' + data.dataList[i].pictureName;


                                console.log("执行了转换");
                                data.dataList[i].birthday = _self.format(data.dataList[i].birthday);
                                data.dataList[i].driLicenceTime = _self.format(data.dataList[i].driLicenceTime);
                                data.dataList[i].driverzigezhengTime = _self.format(data.dataList[i].driverzigezhengTime);
                            }
                            _self.data = data.dataList;
                            console.log('输出显示数据');
                            console.log(_self.data);
                        } else {
                            _self.data = [];
                        }
                    }
                });
            } else {

                $.ajax({
                    type:'GET',
                    url:dataUrl.dataUrl.person.all,
                    cache:false,
                    data: _self.page,
                    success: function(data){
                        _self.$Loading.finish();
                        if (typeof data == "object") {
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            for (var i in data.dataList) {

                                if (data.dataList[i].peopleType == 1) {
                                    data.dataList[i].type = '驾驶员';
                                } else if (data.dataList[i].peopleType == 2) {
                                    data.dataList[i].type = '押运员';
                                }
                                data.dataList[i].picUser = 'http://localhost:8080' + '/pic/' + data.dataList[i].pictureName;
                                data.dataList[i].birthday = _self.format(data.dataList[i].birthday);
                                data.dataList[i].driLicenceTime = _self.format(data.dataList[i].driLicenceTime);
                                data.dataList[i].driverzigezhengTime = _self.format(data.dataList[i].driverzigezhengTime);
                            }
                            _self.data = data.dataList;
                        } else {
                            _self.data = [];
                        }
                    }
                })
            }
            _self.searchText="";
        },
        upMessage:function() {
            var _self = this;
            _self.loading = true;
            _self.$refs.formValidate.validate(function (valid) {
                if (_self.openState == "查看") {
                    _self.loading = false;
                    _self.modal1 = false;
                } else {
                    if (valid) {
                        //delete  _self.formValidate.picUser;
                        _self.formValidate.birthday = _self.format(_self.formValidate.birthday);
                        _self.formValidate.driLicenceTime = _self.format(_self.formValidate.driLicenceTime);
                        _self.formValidate.driverzigezhengTime = _self.format(_self.formValidate.driverzigezhengTime);
                        if (_self.openState == "添加") {
                            _self.postData(_self, dataUrl.dataUrl.person.insert,_self.formValidate);
                        }
                        else if (_self.openState == "修改") {
                            _self.postData(_self, dataUrl.dataUrl.person.upDate,_self.formValidate);
                        }
                        console.log('输出请求信息');
                        console.log(_self.formValidate);
                    } else {
                        _self.loading = false;
                    }
                }
            });
        },
        cancel:function() {
            console.log("重置一下就好");
            this.$refs['formValidate'].resetFields();
            this.modal1 = false;
            this.loading = false;
        },
        postData:function(_self, url, data) {
            $.ajax({
                type: 'POST',
                url: url,
                data:data,
                cache: false,
                success: function (data) {
                    _self.loading = false;
                    _self.modal1 = false;
                    _self.$Message.info('上传成功');
                    _self.getData();
                },
                error:function () {
                    _self.$Message.info('上传失败');
                }
            });
        },
        handleRow: function(data,index){
            var _self=this;
            // console.log(data);
            // console.log(index);
            //_self.data1[index].checked=true;
            this.$refs.table.toggleSelect(index);
        },//当点击一行是触发该函数，同时会触发chooseAll
        m_ok: function(){
            var _self=this;
            _self.model2=false;
            _self.m_tit=null;
            _self.m_msg=null;
            if(_self.del_msg){
                _self.ok_del();
            }
        },
        m_cancel: function(){
            var _self=this;
            _self.model2=false;
            _self.m_tit=null;
            _self.m_msg=null;
        },
        deldemo: function(){//删除逻辑
            var _self=this;
            console.log("进入删除逻辑");
            console.log(_self.delArr);

            if(_self.delArr.length>0){
                _self.del_msg=true;
                _self.m_tit="删除提示";
                _self.m_msg="请确认是否删除该"+_self.delArr.length+"条数据";
                _self.model2=true;
            }else{
                _self.m_tit="选项提示";
                _self.m_msg="请选择要删除的数据，至少选择一行数据";
                _self.model2=true;
            }
        }
    },
    activated: function() {//created:
        var _self = this;
        _self.userType=parseInt(_self.$cookies.get("roleID"));//parseInt(this.$cookies.get("roleID"));
        //this.columns.splice(1,1);
        if(_self.userType==1){
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.person.companyList,
                cache: false,
                success: function (data) {
                    for (var i in data) {
                        _self.companyList.push({id: data[i].id, name: data[i].name});
                    }
                }
            });
            //_self.columns[this.columns.length-1].width=0;//2018年2月23日13:24:18添加用于隐藏td
        }else if(_self.userType==2){
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.person.companyList,
                cache: false,
                success: function (data) {
                    for (var i in data) {
                        _self.companyList.push({id: data[i].id, name: data[i].name});
                    }
                }
            });
            //this.columns[this.columns.length-1].width=0;//2018年2月23日13:24:18添加用于隐藏td
        } else {
            _self.userType = 3;
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.company.getName + _self.$cookies.get("companyID"),//textState.companyID,
                cache: false,
                success: function (data) {
                    _self.companyList.push({id: data.id, name: data.name});
                }
            });
        }
        _self.getData();
    },
    beforeDestroy: function(){
        this.$emit('getloading',true);
    },
    mounted: function () {
        //this.$refs.head.style.display = 'block';
        this.indexloading();
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

        .ivu-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .ivu-form-item-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .ivu-form-item-content {
		    position: relative;
		    line-height: 32px;
		    font-size: 12px;
		    display: flex;
		    justify-content: space-between;
		    align-items: center;
		}

        .ivu-modal {
            top: 50px;
        }
        /*去除表格中的padding*/
/*        .ivu-table-cell{
            padding: 0;
        }*/
</style>