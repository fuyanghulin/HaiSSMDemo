<template>
	<div class="safecard" ref="head">
	    <nav>
            <div>
                <Button type="primary" @click="open($event)" v-if="userType===1">添加</Button>
                
                <Tooltip content="删除所选安全卡" placement="bottom-start">
                    <Button type="error" @click="del" v-if="userType===1">删除</Button>
                </Tooltip>
            </div>
            <div>
                <Input placeholder="请输入安全卡名称..." style="width: 300px" v-model="searchText" @keyup.native.enter="search"></Input>
                <Button type="primary" shape="circle" icon="ios-search" @click="search"></Button>
                <Button type="primary" @click="refresh" style="margin-left:20px;">刷新</Button>
            </div>
        </nav>
    <Table border stripe :columns="columns" :data="data" @on-selection-all="chooseAll" @on-selection-change="sel_change"></Table>
    <Page :total="totalRecord" show-total show-elevator :current="page.current" @on-change="next"
          :page-size="page.pageNum"></Page>
    <Modal
       v-model="modal1"
       :closable="false"
    >
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
            <Row>
                <Form-Item label="安全卡名称" prop="safeCardNum">
                    <Input v-model="formValidate.safeCardNum" placeholder="请输入安全卡名称"></Input>
                </Form-Item>
                <Form-Item label="说明书名称" prop="introductionNum">
                    <Input v-model="formValidate.introductionNum" placeholder="请输入说明书名称"></Input>
                </Form-Item>
                <Form-Item label="上传安全卡">
                    <Upload
                            ref = "upload"
                            :before-upload = "handleUpload1"
                            :show-upload-list = "false"
                            :format = "['jpg','jpeg','png']"
                            :max-size = "2048"
                            :on-format-error = "handleFormatError"
                            :on-exceeded-size = "handleMaxSize">
                        <Button type = "ghost" icon="ios-cloud-upload-outline" >上传文件</Button>
                    </Upload>
                    <div v-if="formValidate.safeCardFile !== '' ">待上传文件：{{ formValidate.safeCardFile.name }}
                    </div>
                </Form-Item>
                <Form-Item label="上传安全说明书">
                    <Upload
                            ref = "upload"
                            :before-upload = "handleUpload2"
                            :show-upload-list = "false"
                            :max-size = "2048"
                            :format = "['pdf']"
                            :on-format-error = "handleFormatError"
                            :on-exceeded-size = "handleMaxSize"
                            multiple
                    >
                        <Button type="ghost" icon="ios-cloud-upload-outline">上传文件</Button>
                    </Upload>
                    <div v-if="formValidate.introductionFile !== '' ">待上传文件：{{ formValidate.introductionFile.name }}
                    </div>
                </Form-Item>
            </Row>
        </Form>
        <!--底部导航条-->
        <div slot="footer">
            <Button type="text"  style="width: 100px;" @click="cancel">取消</Button>
            <Button type="primary" @click="upMessage" :loading="loading" style="width: 100px;">确定</Button>
        </div>
    </Modal>
    <!--显示pdf-->
    <modal
       v-model="modal2"
       :closable="false"
       width="1000"
       scrollable
    >
    	<iframe  :src="showImg" align="center" frameborder="0" width="100%"
                                style="border-top: 1px solid #ddd;height: 700px;" v-show="show">
                            <p>您的浏览器不支持 iframe 标签。</p>
                        </iframe>
        <!-- <div id="showPDF" style=" height: 500px;" v-show="show"></div> -->
        <img :src="showImg" style=" height: auto;width:100%;background-size:100% 100%" v-show="!show">
    </modal>
    <Modal
        v-model="modal3"
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
	export default{
		name: 'SafeCard',
props:{
    indexloading: {
        type: Boolean,
        default: true
    }
},
		data:function(){
			return {
                userType:'',
                showImg:"",
                show:true,
                totalRecord: 0, /*总页数*/
                page: {
                    current: 1, /*当前页*/
                    pageNum: 20/*每页数据量*/
                },
                pdfSrc:'',
                formValidate: {
                    id:'',
                    safeCardName:'',
                    safeCardFile: '',
                    introductionName: '',
                    introductionFile: '',
                    introductionNum:'',
                    safeCardNum:'',
                    createTime:''
                },
                ruleValidate: {
                    safeCardNum: [
                        {required: true, message: '安全卡不能为空', trigger: 'blur'}
                    ],
                    introductionNum: [
                        {required: true, message: '说明书不能为空', trigger: 'blur'}
                    ]
                },
                modal2:false,
                modal1: false,
                modal3:false,
                columns: [
                    {
                        type: 'selection',
                        align: 'center'
                    },
                    {
                        title: '安全卡名称',
                        align: 'center',
                        key: 'safeCardNum',

                    },
                    {
                        title: '安全卡附件',
                        align: 'center',
                        key: 'safeCardFiles',
                        render: function (h, params) {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function () {
                                        	this.show=false;
                                        	this.showImg=dataUrl.dataUrl.safeCard.pdf + this.data[params.index].safeCardName+'?time='+Date.parse(new Date());
                                        	this.modal2 = true;
                                            console.log("执行click函数");
                                            
                                        	
                                        	
                                            
                                            /* new PDFObject({ url: dataUrl.dataUrl.safeCard.pdf + this.data[params.index].safeCardName+'?time='+Date.parse(new Date()) }).embed('showPDF'); */
                                        }.bind(this)
                                    }
                                },'查看')
                            ]);
                        }.bind(this)
                    },
                    {
                        title: '说明书名称',
                        align: 'center',
                        key: 'introductionNum'
                    },
                    {
                        title: '说明书附件',
                        align: 'center',
                        key: 'introductionFile',
                        render: function (h, params) {
                            return h('div', [

                                h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function () {
                                        	this.showImg=dataUrl.dataUrl.safeCard.pdf + this.data[params.index].introductionName+'?time='+Date.parse(new Date());
                                        	var isPDF=this.data[params.index].introductionName.split('.')[1];

                                            var isIE=(!!window.ActiveXObject || "ActiveXObject" in window);
                                            this.show=true;
                                            if (isIE&&(isPDF=='PDF'||isPDF=='pdf')) {
                                            	this.modal2 = false;
                                            } else {
                                            	this.modal2 = true;
                                            }
                                        }.bind(this)
                                    }
                                },'查看')
                            ]);
                        }.bind(this)
                    },
                    {
                        title: '创建时间',
                        align: 'center',
                        key: 'createTime',
                        sortable: true
                    },
                    {
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        render: function (h, params) {
                            if(this.userType===1){
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'error',
                                            size: 'small'
                                        },
                                        on: {
                                            click: function () {
                                                // this.delArr.push(this.data[params.index].id);
                                                // this.del();
                                                this.delone=this.data[params.index].id;
                                                this.del_index(this.data[params.index].id);
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
                                        on: {
                                            click: function () {
                                                this.showOne(params.index);
                                            }.bind(this)
                                        }
                                    }, '查看')
                                ]);
                            }
                        }.bind(this)
                    }
                ],
                data: [],
                openState: '',
                loading: false,
                file: null,
                delArr: [],
                searchText:'',
                delone:'',
                onedel:false
            }
		},
        methods: {
indexloading: function(){
    this.$emit('getloading',false);
},
            showOne:function(index) {
                this.$Modal.success({
                    title: '安全卡信息',
                    content: '安全卡名称：' + this.data[index].safeCardNum + '<br>' +
                    '说明书名称：' + this.data[index].introductionNum + '<br>' +
                    '创建时间：' + this.data[index].createTime
                })
            },
            search:function () {
                if (this.searchText.replace(/\s/g,'').length < 1) {
                    alert('搜索内容不可为空');
                } else {
                    var _self = this;
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.safeCard.search+encodeURI(_self.searchText.replace(/\s/g,'')),
                        cache: false,
                        success: function (data) {
                            if (typeof data == "object") {
                                _self.totalRecord = data.totalRecord;
                                _self.page.current = data.currentPage;
                                for (var key in data.dataList) {
                                    data.dataList[key].cratetime = _self.format(data.dataList[key].cratetime);
                                }
                                _self.data = data.dataList;
                            } else {
                                _self.data = [];
                            }
                        }
                    });
                }
            },
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
                    _self.modal3=true;
                }
                
            },
            del_index:function(n){
                var _self=this;
                _self.modal3=true;

                
            },
            ok_del:function(){
                var _self=this;
                if(_self.onedel){
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.safeCard.del+_self.delone,
                        cache: false,
                        success: function (data) {
                            _self.getData();
                            _self.$Message.info('刪除成功');
                        }
                    });
                    _self.delone='';
                    _self.onedel=false;
                }else{
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.safeCard.del+_self.delArr,
                        cache: false,
                        success: function (data) {
                            _self.delArr = [];
                            _self.getData();
                            _self.$Message.info('刪除成功');
                        }
                    });
                    _self.delArr=[];
                }
                
            },
            /*chooseAll:function(data) {
                var _self = this;
                for (var i in data) {
                    _self.delArr.push(data[i].id);
                }
            }*/
            cancel_del:function(){
                console.log("执行cancel_del函数");
                this.modal2=false;
                this.delone='';
                this.onedel=false;
                this.modal3=false;
                this.$refs['formValidate'].resetFields();
            },
            /*chooseAll:function(data) {
                var _self = this;
                if(_self.delArr.length){
                    _self.delArr=[];
                }else{
                   for (var i in data) {
                        _self.delArr.push(data[i].id);
                    } 
                }
                
            },*/
            chooseAll:function(data) {
                var _self = this;
                //console.log(this.data.selection);
                _self.delArr=[];
                if(data.length){
                    for (var i in data) {
                        _self.delArr.push(data[i].id);
                    }
                }
                
            },
            sel_change:function(data){
                var _self = this;
                //console.log(data);
                _self.delArr=[];
                if(data.length){

                    for (var i in data) {
                        _self.delArr.push(data[i].id);
                    }
                }
                
            },
            handleUpload1:function(file) {
                console.log("执行handleUpload1事件");
                this.formValidate.safeCardFile = file;
                return false;
            },
            handleUpload2:function(file) {
                this.formValidate.introductionFile = file;
                return false;
            },
            upload:function() {
                console.log("执行Upload事件");
                var _self = this;
                var formData = new FormData();
                formData.append('file', _self.formValidate.safeCardFile);
                formData.append('file', _self.formValidate.introductionFile);
                
                $.ajax({
                    url: dataUrl.dataUrl.safeCard.uploadFile,
                    type: 'post',
                    processData: false, //不对表单处理
                    contentType: false, //
                    data: formData,
                    cache:false,
                    success:function(data) {
                        _self.loading = false;
                        _self.formValidate.safeCardFile = data[0];
                        _self.formValidate.introductionFile = data[1];
                        _self.formValidate.safeCardName = data[2];
                        _self.formValidate.introductionName = data[3];
                        if (_self.openState == "添加") {
                            _self.postData(_self, dataUrl.dataUrl.safeCard.insert, JSON.stringify(_self.formValidate));
                        }
                        else if (_self.openState == "修改") {
                            _self.postData(_self, dataUrl.dataUrl.safeCard.upDate, JSON.stringify(_self.formValidate));
                        }
                    },
                    error:function() {
                        _self.loading = false;
                        _self.$Message.success('上传失败')
                    }
                });
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
            format:function(nS) {
                var time = new Date(parseInt(nS));
                var y = time.getFullYear();
                var m = time.getMonth() + 1;
                var d = time.getDate();
                return y + '-' + this.add0(m) + '-' + this.add0(d);
            },
            open:function(obj) {
                var _self = this;
                if ($.trim(obj.currentTarget.innerText) == "添加") {
                    _self.$refs['formValidate'].resetFields();
                    _self.openState = $.trim(obj.currentTarget.innerText);
                }
                this.modal1 = true;
            },
            getData:function() {
                var _self = this;
                _self.$Loading.start();
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.safeCard.all,
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
            upMessage:function() {
                console.log("执行upMessage事件");
                var _self = this;
                _self.loading = true;
                _self.$refs.formValidate.validate(function (valid) {
                    if (valid) {
                        if (_self.formValidate.safeCardFile == '' || _self.formValidate.introductionFile == '') {
                            _self.loading = false;
                            _self.$Message.error('文件不可为空');
                        } else {
                            _self.upload();
                        }
                    } else {
                        _self.loading = false;
                    }
                });
            },
            cancel:function() {
                this.modal1 = false;
                this.loading = false;
                this.modal3=false;
                this.$refs['formValidate'].resetFields();
            },
            show:function(index) {
                this.$Modal.confirm({
                    render: (function(h) {
                        return h('image', {
                            props: {src: "chrome-extension://laookkfknpbbblfpciffpaejjkokdgca/backgrounds/4ea1221c-78f0-4952-a3c6-f16acb33b57f.jpg"},
                            on: {input: function(val) {
                                return this.value = val;
                            }}
                        });
                    })
                });
            },
            postData:function(_self, url, data) {
                console.log("执行postData事件");
                $.ajax(url,{
                    dataType: "json",
                    type: 'post',
                    data: data,
                    cache:false,
                    contentType: "application/json ;charset=utf-8",
                    success:function() {
                        _self.$Message.info('上传成功');
                        _self.modal1 = false;
                        _self.getData();
                    },
                    error:function() {
                        _self.$Message.info('上传失败');
                    }
                });
            },
            refresh: function(){
                var _self=this;
                _self.searchText='';
                _self.getData();
                // var textState=JSON.parse(Cookies.get("state"));
                // if(textState!=null){
                //     if(textState.ID==0){
                //         window.location.href="../../state.html";
                //     }else if(textState.ID==1){
                //         if(textState.roleID==1){
                //             _self.userType=1;
                //         }else if(textState.roleID==2){
                //             _self.userType=2;
                //         }
                //         _self.getData();
                //     }
                // }else {
                //     window.location.href="../../state.html";
                // }
            }
        },
        created:function(){
        	this.getData();
        	this.userType=parseInt(this.$cookies.get("roleID"));
        },
        beforeDestroy: function(){
            this.$emit('getloading',true);
        },
        mounted: function(){
            
this.indexloading();
        }
	}
</script>

<style scoped>
nav {
    padding: 10px 50px;
    display: flex;
    justify-content: space-between;
    justify-items: center;
}

nav div:first-child button {
    margin: 0 10px;
}
/* 加载loading */
/*#tloading{
    width: 100vw;
    height: 100vh;
    position: relative;
}
#tloading img {
    position: absolute;
    top: 300px;
    left: 50%;
    width: 100px;
    height: 100px; 
}*/
/*去除表格中的padding*/
.ivu-table-cell{
    padding: 0;
}
</style>