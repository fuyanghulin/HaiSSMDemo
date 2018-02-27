<template>
	<div class="goodstype" ref='head'>	
	    <nav>
	        <div class="first_btn">
	            <Button type="primary" @click="open($event)" v-if="userType===1">添加</Button>
	            <Tooltip content="删除所选货物信息" placement="bottom-start">
	                <Button type="error" @click="del" v-if="userType===1">删除</Button>
	            </Tooltip>
	            
	        </div>
	        <div>
	            <Input placeholder="请输入名称..." style="width: 300px" v-model="searchText" @keyup.native.enter="search"></Input>
	            <Button type="primary" shape="circle" icon="ios-search" @click="search"></Button>
	            <Button type="primary" @click="refresh" style="margin-left:20px;">刷新</Button>
	        </div>
	    </nav>
	    <Table border stripe :columns="columns" :data="data" @on-selection-all="chooseAll" @on-selection-change="sel_change" @on-row-click="row_cli"></Table>
	    <Page 
	        :total="totalRecord"  
	        show-total 
	        show-elevator 
	        :current="page.current"
	         @on-change="next"
	          :page-size="page.pageNum"
	          ></Page>
	    <Modal
	            v-model="modal1"
	            :loading="loading"
	            :closable="false"
	            @on-cancel="cancel">
	        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
	            <Row>
	                <Form-Item label="名称" prop="name">
	                    <Input v-model="formValidate.name" placeholder="请输入名称"></Input>
	                </Form-Item>
	                <Form-Item label="国际名称" prop="nationName">
	                    <Input v-model="formValidate.nationName" placeholder="请输入国际名称"></Input>
	                </Form-Item>
	                <Form-Item label="备注" prop="remark">
	                    <Input v-model="formValidate.remark" placeholder="请输入备注" type="textarea" :rows="4"></Input>
	                </Form-Item>
	            </Row>
	        </Form>
	        <!--底部导航条-->
	        <div slot="footer">
	            <Button type="text" style="width: 100px;" @click="cancel">取消</Button>
	            <Button type="primary" @click="upMessage" :loading="loading" style="width: 100px;">确定</Button>
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
	export default{
		name:'GoodsType',
props:{
    indexloading: {
        type: Boolean,
        default: true
    }
},
		data:function(){
			return {
                userType: '',
                totalRecord: 0, /*总记录数*/
                page: {
                    current: 1, /*当前页*/
                    pageNum: 15/*每页数据量*/
                },
                formValidate: {
                    id: '',
                    name: '',
                    nationName: '',
                    remark: '',
                    cratetime: ''
                },
                ruleValidate: {
                    name: [
                        {required: true, message: '名称不能为空', trigger: 'blur'}
                    ],
                    nationName: [
                        {required: true, message: '国际名称不能为空', trigger: 'blur'}
                    ],
                    remark: [
                        {required: true, message: '备注不能为空', trigger: 'blur'}
                    ]
                },
                onedel:false,
                modal1: false,
                modal2: false,
                searchText: '',
                columns: [
                    {
                        type: 'selection',
                        align: 'center'
                    },
                    {
                        title: '名称',
                        align: 'center',
                        key: 'name'
                    },
                    {
                        title: '国际名称',
                        align: 'center',
                        key: 'nationName'
                    },
                    {
                        title: '备注',
                        align: 'center',
                        key: 'remark'
                    },
                    {
                        title: '创建时间',
                        align: 'center',
                        key: 'cratetime',
                        sortable: true
                    },
                    {
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        fixed: 'right',
                        render: function (h, params) {
                            if (this.userType === 1) {
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
                                                this.openState = "修改";
                                                this.formValidate = JSON.parse(JSON.stringify(this.data[params.index]));
                                                this.modal1 = true;
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
                                                /*this.delArr.push(this.data[params.index].id);*/
                                                //this.delone.splice(0,1,this.data[params.index].id);
                                                //console.log(this.data[params.index].id);
                                                this.delone=this.data[params.index].id;
                                                this.del_index(this.data[params.index].id);
                                                this.onedel=true;
                                            }.bind(this)
                                        }
                                    }, '删除')
                                ]);
                            } else {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'primary',
                                            size: 'small'
                                        },
                                        on: {
                                            click: function () {
                                                this.show(params.index);
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
                delone: ''
            }
		},
        methods: {
indexloading: function(){
    this.$emit('getloading',false);
},
            show:function(index) {
                this.$Modal.success({
                    title: '货物类型信息',
                    content: '名称：' + this.data[index].name + '<br>' +
                    '国际名称：' + this.data[index].nationName + '<br>' +
                    '备注：' + this.data[index].remark + '<br>' +
                    '创建时间：' + this.data[index].cratetime
                })
            },
            del:function() {
                var _self = this;
                if(_self.delArr.length>0){//kk加了一个判断
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
                        url: dataUrl.dataUrl.goodsType.del+_self.delone,
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
                        url: dataUrl.dataUrl.goodsType.del+_self.delArr,
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
            cancel_del:function(){
                this.modal2=false;
                this.delone='';
                this.onedel=false;
            },
            row_cli:function(data){
                console.log(data);
            },
            search:function() {
                if (this.searchText.replace(/\s/g, '').length < 1) {
                    alert('搜索内容不可为空');
                } else {
                    var _self = this;
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.goodsType.search+encodeURI(_self.searchText.replace(/\s/g, '')),
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
                if ($.trim(obj.currentTarget.innerText)== "添加") {
                    _self.$refs['formValidate'].resetFields();
                    _self.openState =$.trim(obj.currentTarget.innerText);
                }
                this.modal1 = true;
            },
            getData:function() {
                var _self = this;
                _self.$Loading.start();
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.goodsType.all,
                    cache: false,
                    data: _self.page,
                    success: function (data) {
                        _self.$Loading.finish();
                        if (typeof data == "object") {
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            _self.page.pageNum = data.pageSize;
                            for (var key in data.dataList) {
                                data.dataList[key].cratetime = _self.format(data.dataList[key].cratetime);
                            }
                            _self.data = data.dataList;
                        } else {
                            _self.data = [];
                        }
                    }
                });
            },
            upMessage:function() {
                var _self = this;
                _self.loading = true;
                _self.$refs.formValidate.validate(function (valid) {

                    if (valid) {
                        if (_self.openState == "添加") {
                        	//_self.formValidate.id=null;
                            _self.postData(_self, dataUrl.dataUrl.goodsType.insert, JSON.stringify(_self.formValidate));
                        }
                        else if (_self.openState == "修改") {
                        	_self.formValidate.cratetime=Date.parse(_self.formValidate.cratetime);
                            _self.postData(_self, dataUrl.dataUrl.goodsType.upDate, JSON.stringify(_self.formValidate));
                            _self.formValidate.id='';
                        }
                    } else {
                        _self.loading = false;
                    }
                });
               // _self.$refs['formValidate'].resetFields();//kk添加
            },
            cancel:function() {
                this.modal1 = false;
                this.$refs['formValidate'].resetFields();
            },
            postData:function(_self, url, data) {
                $.ajax(url, {
                    type: 'post',
                    data: data,
                    cache:false,
                    contentType: "application/json ;charset=utf-8",
                    success:function() {
                        _self.modal1 = false;
                        _self.loading = false;
                        _self.$Message.info('上传成功');
                        _self.getData();
                    },
                    error:function() {
                        _self.$Message.info('上传失败');
                    }
                });
            },
            refresh:function(){
                var _self = this;
                _self.searchText='';
                _self.getData();//没想明白怎么处理，先放置吧
                /*var textState = JSON.parse(Cookies.get("state"));
                if (textState != null) {
                    if (textState.ID == 0) {
                        window.location.href = "../../state.html";
                    } else if (textState.ID == 1) {
                        if (textState.roleID == 1) {
                            _self.userType = 1;
                        } else if (textState.roleID == 2) {
                            _self.userType = 2;
                        }
                        _self.getData();
                    }
                } else {
                    window.location.href = "../../state.html";
                }*/
            }
        },
        created:function() {
            var _self = this;
            //var textState = this.$cookies.get("roleID");
            //不知道是否得未登录的用户，先放置着吧
            _self.userType=parseInt(this.$cookies.get("roleID"));
            _self.getData();
        },
        mounted: function(){

this.indexloading();
        },
        beforeDestroy: function(){
            this.$emit('getloading',true);
        }
	}
</script>

<style scoped>
.goodstype{
    height: 1000px;
}
nav {/*这个地方可以都改一下，将这个搜索框与左侧‘首页’字样对齐*/
    padding: 10px 50px;
    display: flex;
    justify-content: space-between;
    justify-items: center;
}

 nav div:first-child button {
    margin: 0 10px;
}/*
.first_btn{
	margin-left: 10px;!import;
}*/
</style>