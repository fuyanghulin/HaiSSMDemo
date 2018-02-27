<template>
<div class="goodsinfo">
    <!--頭部區塊-->
    <nav>
        <div>
            <i-button type="primary" @click="open('添加',0)" v-if="userType===3">添加</i-button>
            <i-button type="error" style="margin-left: 20px;" @click="del" v-if="userType===3">删除</i-button><!-- moreDelete -->
        </div>
        <div>
            <i-input placeholder="请输入名称..." style="width: 200px;" v-model="searchText" @keyup.native.enter="search"></i-input>
            <i-button type="primary" shape="circle" icon="ios-search" @click="search"></i-button>
            <i-Button type="primary" @click="getAll" style="margin-left:20px;">刷新</i-Button>
        </div>
    </nav>
    <!--数据区块-->
    <i-Table border stripe :columns="columns" :data="data1" @on-selection-all="chooseAll" @on-selection-change="sel_change" ></i-Table><!-- 有一个这个属性不知道是用来干嘛的ellipsis -->
    <!--\总页数-->
    <Page :total="totalRecord" @on-change="changePage"
           :page-size="page.pageNum" show-elevator
          show-total :current="page.current"></Page>
    <!--弹出组件-->
    <modal
            v-model="modal1"
            :closable="false"        
            @on-cancel="cancel"
    >
        <i-form :model="goods" :rules="ruleValidate" ref="goods">
            <Row>
                <i-col span="11">
                    <Form-Item label="编码" prop="code">
                        <i-Input placeholder="请输入货物编码" v-model="goods.code"></i-Input>
                    </Form-Item>
                </i-col>
                <i-col span="2"></i-col>
                <i-col span="11">
                    <Form-Item label="名称" prop="name">
                        <i-Input placeholder="请输入货物名称" v-model="goods.name"></i-Input>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="11">
                    <Form-Item label="货物类型" prop="goodstyleId">
                        <i-select v-model="goods.goodstyleId" label-in-value @on-change="getTheGoods">
                            <i-option v-for="item in TypeList" :value="item.id" :key="item.id">{{ item.name }}
                            </i-option>
                        </i-select>
                    </Form-Item>
                </i-col>
                <i-col span="2"></i-col>
                <i-col span="11">
                    <Form-Item label="安全卡" prop="safecardId">
                        <i-select v-model="goods.safecardId" label-in-value @on-change="getTheSafe">
                            <i-option v-for="item in cardList" :value="item.id" :key="item.id">{{ item.safeCardNum }}
                            </i-option>
                        </i-select>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="24">
                    <Form-Item label="应急方案" prop="reserveOne">
                        <i-input type="textarea" :rows="4" placeholder="请输入应急方案" v-model="goods.reserveOne"></i-input>
                    </Form-Item>
                </i-col>
            </Row>
            <Row>
                <i-col span="24">
                    <Form-Item label="备注">
                        <i-input type="textarea" :rows="4" placeholder="请输入备注" v-model="goods.remark"></i-input>
                    </Form-Item>
                </i-col>
            </Row>
        </i-form>
        <div slot="footer">
            <i-button type="text" style="width: 100px;" @click="cancel">取消</i-button>
            <i-button type="primary" @click="upMessage" :loading="loading" style="width: 100px;">确定</i-button>
        </div>
    </modal>
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
	name: 'GoodsInfo',
props:{
    indexloading: {
        type: Boolean,
        default: true
    }
},
    data:function() {
        return {
            userType: '',
            columns: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '编码',
                    key: 'code',
                    align: 'center',
                    //width: 140,
                    ellipsis: true
                },
                {
                    title: '名称',
                    key: 'name',
                    align: 'center',
                    //width: 140,
                    ellipsis: true
                },
                {
                    title: '货物类型',
                    key: 'reserveTwo',
                    align: 'center',
                    //width: 140,
                    ellipsis: true
                },
                {
                    title: '货物安全卡',
                    key: 'reserveThree',
                    align: 'center',
                    //width: 140,
                    ellipsis: true
                },
                {
                    title: '应急方案',
                    key: 'reserveOne',
                    align: 'center',
                    //width: 140,
                    ellipsis: true
                },
                {
                    title: '查看打印',
                    key: 'action1',
                    align: 'center',
                    width: 150,
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
                                            this.showOne(params.index);
                                        }.bind(this)
                                    }
                                }, '查看'),
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function () {
                                            //this.deleteOne(params.index);
                                            this.$Message.info('打印功能尚未完善！');
                                        }.bind(this)
                                    }
                                }, '打印')
                            ]);
                    }.bind(this)
                }
                ,{
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 150,
                    fixed: 'right',
                    render: function (h, params) {
                        if(this.userType===3){
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
                                            this.delone=this.data1[params.index].id;
                                            this.del_index(this.data1[params.index].id);
                                            this.onedel=true;
                                        }.bind(this)
                                    }
                                }, '删除')
                            ]);
                        }else{//这个地方可以选择
                            //全部渲染，并通过函数改变指定td宽度；主要问题是非指定用户可以通过修改宽度来改变功能，
                            //但是通过浏览器测试发现指定为0后，浏览器并不会渲染这个td，所以可行
                            
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
                                            this.showOne(params.index);
                                        }.bind(this)
                                    }
                                }, '不可修改')
                            ]);
                        }

                    }.bind(this)
                }],
            data1: [],
            modal1: false,
            allChecked: '',
            theChecked: [],
            goodType: [],
            totalRecord: 0,
            content: {},
            page: {
                current: 1,
                pageNum: 15
            },
            op: 0,
            title: '',
            shade: false,
            position: 0,
            loading: false,
            searchText: '',
            sureName: '确定',
            used: false,
            goods: {
                name: '',
                goodstyleId: '',
                safecardId: '',
                code: '',
                reserveOne: '',
                reserveTwo: '',
                reserveThree: '',
                remark: ''
            },
            ruleValidate: {
                name: [
                    {required: true, message: '货物名称不能为空', trigger: 'blur'}
                ],
                code: [
                    {required: true, message: '货物编号不能为空', trigger: 'blur'}
                ],
                goodstyleId: [
                    {required: true, message: '请选择货物类型', trigger: 'blur', type: 'number'}
                ],
                safecardId: [
                    {required: true, message: '请选择安全卡', trigger: 'blur', type: 'number'}
                ],
                reserveOne: [
                    {required: true, message: '请填写应急方案', trigger: 'blur'}
                ]
            },
            TypeList: [],
            cardList: [],
            delArr: [],
            delone: '',
            onedel:false,
            modal2: false,
            textState:{}
        }
    },
    created: function () {
        //document.body.removeChild(document.getElementById('tloading'));
        var _self = this;

        if(_self.$cookies.get("ID")==1){
        	if(_self.$cookies.get("roleID")==1){
        		_self.userType = 1;
        		_self.getAll();
                this.columns[this.columns.length-1].width=0;//2018年2月23日13:24:18添加用于隐藏td
        	}else if(_self.$cookies.get("roleID")==2){
        		_self.userType = 2;
        		_self.getAll();
                this.columns[this.columns.length-1].width=0;//2018年2月23日13:24:18添加用于隐藏td
        	}else{
                _self.userType = 3;
        		_self.getAll();
        	}
        }
        // _self.getAll();
        $.ajax({
            type: 'GET',
            url: dataUrl.dataUrl.goods.goodsType,
            cache: false,
            success: function (data) {
                _self.TypeList = data;
            }
        });
        // 获得所有安全卡类型
        $.ajax({
            type: 'GET',
            url: dataUrl.dataUrl.goods.safeCard,
            cache: false,
            success: function (data) {
                _self.cardList = data;
            }
        });
    },
    mounted: function () {
        //this.$refs.head.style.display = 'block';
this.indexloading();
    },
        beforeDestroy: function(){
            this.$emit('getloading',true);
        },
    methods: {
indexloading: function(){
    this.$emit('getloading',false);
},
        search: function () {
            var _self=this;
            if (this.searchText.replace(/\s/g, '').length < 1) {
                alert('搜索内容不可为空');
            } else {
                if(_self.userType==1||_self.userType==2){
                    var _self = this;
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.goods.search + encodeURI(this.searchText),
                        cache: false,
                        success: function (data) {
                            if (typeof data == "object") {
                                _self.totalRecord = data.totalRecord;
                                _self.page.current = data.currentPage;
                                _self.data1 = data.dataList;
                            } else {
                                _self.data1 = [];
                            }
                        }
                    });
                }else{
                    var idata={};
                    idata.current=_self.page.current;
                    idata.pageNum=_self.page.pageNum;
                    //console.log(JSON.parse(Cookies.get("state")));    searchCompany
                    idata.companyId=_self.$cookies.get("companyID");//JSON.parse(Cookies.get("state")).companyID;
                    idata.name=_self.searchText;
                    console.log(idata);

                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.goods.searchCompany,
                        data:idata,
                        cache: false,
                        success: function (data) {
                            if (typeof data == "object") {
                                _self.totalRecord = data.totalRecord;
                                _self.page.current = data.currentPage;
                                _self.data1 = data.dataList;
                            } else {
                                _self.data1 = [];
                            }
                        }
                    });

                }
            }
        },
        showOne: function (index) {
            this.$Modal.info({
                title: '货物信息',
                content: '编号：' + this.data1[index].code + '<br>' +
                '名称：' + this.data1[index].name + '<br>' +
                '货物类型：' + this.data1[index].reserveTwo + '<br>' +
                '货物安全卡：' + this.data1[index].reserveThree + '<br>' +
                '应急方案：' + this.data1[index].reserveOne + '<br>' +
                '备注：' + this.data1[index].remark
            });
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
        change: function (index) {
            this.goods = JSON.parse(JSON.stringify(this.data1[index]));
            this.modal1 = true;
            this.op = 1;
        },
        del:function() {
            var _self = this;
            //console.log("所选个数："+_self.delArr.length);

            if(_self.delArr.length>0){//kk加了一个判断
                //console.log("进入modal2");
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
                    url: dataUrl.dataUrl.goods.del+_self.delone,
                    cache: false,
                    success: function (data) {
                        _self.getAll();
                        _self.$Message.info('刪除成功');
                    }
                });
                _self.delone='';
                _self.onedel=false;
            }else{
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.goods.del+_self.delArr,
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
        time: function (times) {
            // console.log(times);
            var d = new Date(parseInt(times));
            return d.getFullYear() + '/' + (d.getMonth() + 1) + '/' + d.getDate();
        },
        cancel: function () {
            this.modal1 = false;
            //this.modal2 false;
            this.$refs['goods'].resetFields();
        },
        cancel_del:function(){
            this.modal2=false;
            this.delone='';
            this.onedel=false;
            this.$refs['goods'].resetFields();
        },
        open: function (name, op) {
            this.op = op;
            if (op == 0) {
                for (var key in this.goods) {
                    this.goods[key] = '';
                }
                this.modal1 = true;
            }
        },
        changePage: function (cur) { //  跳转页面
            this.page.current = cur;
            this.getAll();
        },
        getAll: function () {
            console.log('执行getAll函数');
            var _self = this;
            if(_self.userType=='3'){
                var idata={};
                for(var key in _self.page){
                    idata[key]=_self.page[key];
                }
                idata.companyId=_self.$cookies.get("companyID");//_self.textState.companyID;
                console.log('输出请求参数idata');
                console.log(idata);
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.goods.company,
                    data:idata,
                    cache: false,
                    success: function(data){
                        if (data != null && data.dataList != undefined) {
                            //var d;
                            _self.data1 = data.dataList;
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            _self.theChecked = [];
                        } else {
                            _self.data1 = [];
                        }
                    }
                });
            }else{
                 $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.goods.all,
                    data:_self.page,
                    cache: false,
                    success: function (data) {
                        if (data != null && data.dataList != undefined) {
                            //var d;
                            _self.data1 = data.dataList;
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            _self.theChecked = [];
                        } else {
                            _self.data1 = [];
                        }
                    }
                });
            }
            console.log(idata);
            _self.searchText="";
        },
        upMessage: function () {
            this.loading = true;
            var _self = this;
            this.$refs.goods.validate(function (valid) {
                if (valid) {
                    var url;
                    if (_self.op == 0) {
                        url = dataUrl.dataUrl.goods.insert;
                    } else if (_self.op == 1) {
                        url = dataUrl.dataUrl.goods.upDate;
                    }
                    // 发出请求
                    var idata={};
                    for(var key in _self.goods){
                        idata[key]=_self.goods[key];
                    }
                    idata.companyId=_self.$cookies.get("companyID");//_self.textState.companyID;
                    console.log('上传信息输出：');
                    console.log(idata);
                    $.ajax({
                        type: 'POST',
                        url: url,
                        dataType: 'json',
                        contentType: "application/json",
                        data:JSON.stringify(idata),
                        cache: false,
                        success: function (data) {
                            _self.loading = false;
                            _self.$Message.info('上传成功');
                            _self.modal1 = false;
                            _self.getAll();
                            console.log(idata);
                        },
                        error:function (err) {
                        	_self.$Message.info('上传失败');
                            console.log(err);
                            _self.loading = false;
                        }
                    });
                } else {
                    _self.loading = false;
                }
            });
        },
        getTheGoods: function (data) {
            this.goods.reserveTwo = data.label;
        },
        getTheSafe: function (data) {
            this.goods.reserveThree = data.label;
        }
    }
}
</script>

<style scoped>
/*@import '../../common/someinfo.css'*/
        nav {
            padding: 10px 50px;
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

    .ivu-modal {
        top: 50px;
    }

    /*去除表格中的padding*/
    .ivu-table-cell{
        padding: 0;
    }
 
</style>