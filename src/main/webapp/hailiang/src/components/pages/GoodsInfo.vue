<template>
<div class="goodsinfo">
    <!--頭部區塊-->
    <nav>
        <div>
           <!--  <i-button type="primary" @click="open('添加',0)" v-if="userType===3">添加</i-button>
           <i-button type="error" style="margin-left: 20px;" @click="del" v-if="userType===3">删除</i-button> --><!-- moreDelete -->

            <Button type="primary" @click="open($event)">添加</Button>
            <Button type="primary" @click="open($event)">修改</Button>
            <Button type="primary" @click="open($event)">查看</Button>
            <!-- <Button type="primary" @click="">打印</Button> -->
            <Button type="error" @click="deldemo">删除</Button>
        </div>
        <div>
            <i-input placeholder="请输入名称..." style="width: 200px;" v-model="searchText" @keyup.native.enter="search"></i-input>
            <i-button type="primary" shape="circle" icon="ios-search" @click="search"></i-button>
            <i-Button type="primary" @click="getAll" style="margin-left:20px;">刷新</i-Button>
        </div>
    </nav>
    <!--数据区块-->
    <i-Table ref='table' border stripe :columns="columns" :data="data1" @on-selection-change="sel_change" @on-row-click="handleRow"></i-Table><!-- 有一个这个属性不知道是用来干嘛的ellipsis -->
    <!--\总页数-->
    <Page :total="totalRecord" @on-change="changePage"
           :page-size="page.pageNum" show-elevator
          show-total :current="page.current" size='small'></Page>
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
                    type: 'index',
                    width: 40,
                    align: 'center',
                    fixed: 'left'
                },
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
                    title: '操作',
                    key: 'action1',
                    align: 'center',
                    width: 100,
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
                                }, '查看')
                            ]);
                    }.bind(this)
                }
            ],
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
            delone: null,
            onedel:false,
            modal2: false,
            textState:{},
            openState: '',
            m_tit: '',
            m_msg: '',
            del_msg: false,
            model2: false
        }
    },
    activated: function () {//created:
        //document.body.removeChild(document.getElementById('tloading'));
        var _self = this;

        if(_self.$cookies.get("ID")==1){
        	if(_self.$cookies.get("roleID")==1){
        		_self.userType = 1;
        		_self.getAll();
                //this.columns[this.columns.length-1].width=0;//2018年2月23日13:24:18添加用于隐藏td
        	}else if(_self.$cookies.get("roleID")==2){
        		_self.userType = 2;
        		_self.getAll();
                //this.columns[this.columns.length-1].width=0;//2018年2月23日13:24:18添加用于隐藏td
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
        showOne: function (index) {//单独的一个参看modal，woc这样的modal挺好的，可以替代部分原来提示框

            var _self=this;
            if(typeof index==='number'){
                _self.$Modal.info({
                    title: '货物信息',
                    content: '编号：' + _self.data1[index].code + '<br>' +
                    '名称：' + _self.data1[index].name + '<br>' +
                    '货物类型：' + _self.data1[index].reserveTwo + '<br>' +
                    '货物安全卡：' + _self.data1[index].reserveThree + '<br>' +
                    '应急方案：' + _self.data1[index].reserveOne + '<br>' +
                    '备注：' + _self.data1[index].remark
                });                
            }else{
                 _self.$Modal.info({
                    title: '货物信息',
                    content: '编号：' + _self.delone.code + '<br>' +
                    '名称：' + _self.delone.name + '<br>' +
                    '货物类型：' + _self.delone.reserveTwo + '<br>' +
                    '货物安全卡：' + _self.delone.reserveThree + '<br>' +
                    '应急方案：' + _self.delone.reserveOne + '<br>' +
                    '备注：' + _self.delone.remark
                });
            }

        },
        // chooseAll:function(data) {
        //     var _self = this;
        //     //console.log(this.data.selection);
        //     _self.delArr=[];
        //     if(data.length){
        //         for (var i in data) {
        //             _self.delArr.push(data[i].id);
        //         }
        //     }
            
        // },
        sel_change:function(data){
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
                    // success: function (data) {
                    //     _self.getAll();
                    //     _self.$Message.info('刪除成功');

                    // },
                    success: function(){
                        _self.$Message.info("删除成功");
                        _self.delArr=[];
                        _self.delone=null;
                        _self.getAll();
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
                    url: dataUrl.dataUrl.goods.del+_self.delArr,
                    cache: false,
                    // success: function (data) {
                    //     _self.delArr = [];
                    //     _self.getAll();
                    //     _self.$Message.info('刪除成功');
                    // },
                    success: function(){
                        _self.$Message.info("删除成功");
                        _self.delArr=[];
                        _self.delone=null;
                        _self.getAll();
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
       /* open: function (name, op) {
            this.op = op;
            if (op == 0) {
                for (var key in this.goods) {
                    this.goods[key] = '';
                }
                this.modal1 = true;
            }
        },*/
        // 展开一个托运方信息
        open: function(event){
            var iText=null;
            var _self=this;
            console.log(_self.delone);
            console.log(_self.delone);
            //先默认添加，打开图片上传
            iText=event.currentTarget.innerText;
            console.log(iText);
            //添加
            if(iText=="添加"){
                _self.openState='添加';
                for(let key in _self.goods){
                    _self.goods[key]='';
                }
            //查看
            }else if(iText=="查看"){
                if(!_self.delone){
                    _self.m_tit="选项提示";
                    _self.m_msg="请选择一行数据进行查看，不可多选也不可不选";
                    _self.model2=true;//这里的这个model2就可以直接用showone里的modal代替
                    return ;    
                }
                _self.openState="查看";
                for(let key in _self.delone){
                    _self.goods[key]=_self.delone[key];
                }
                _self.showOne();
                return ;
                
            //修改
            }else if(iText=="修改"){
                if(!_self.delone){
                    _self.m_tit="选项提示";
                    _self.m_msg="请选择一行数据进行修改，不可多选也不可不选";
                    _self.model2=true;
                    return ;
                }
                _self.openState="修改";
                for(let key in _self.delone){
                    _self.goods[key]=_self.delone[key];
                }
            }
            _self.modal1=true;
        },
        changePage: function (cur) { //  跳转页面
            this.page.current = cur;
            this.getAll();
        },
        getAll: function () {
            console.log('执行getAll函数');
            var _self = this;
            _self.delArr=[];
            //_self.delonecar=null;
            _self.delone=null;
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
        },
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
        upMessage: function () {
            this.loading = true;
            var _self = this;
            this.$refs.goods.validate(function (valid) {
                if (valid) {
                    var url;
                    if (_self.openState=="添加") {
                        url = dataUrl.dataUrl.goods.insert;
                    } else if (_self.openState=="修改") {
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
                            // _self.loading = false;
                            // _self.$Message.info('上传成功');
                            // _self.modal1 = false;
                            // _self.getAll();
                            // console.log(idata);


                            _self.$Message.info("上传成功");
                            _self.delArr=[];
                            _self.delone=null;
                            _self.getAll();
                            _self.del_msg=false
                        },
                        error:function (err) {
                        	_self.$Message.info('上传失败');
                            console.log(err);
                            //_self.getAll();
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
        },
        handleRow: function(data,index){
            var _self=this;
            // console.log(data);
            // console.log(index);
            //_self.data1[index].checked=true;
            this.$refs.table.toggleSelect(index);
        }//当点击一行是触发该函数，同时会触发chooseAll
    }
}
</script>

<style scoped>
/*@import '../../common/someinfo.css'*/
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

    .ivu-modal {
        top: 50px;
    }

    /*去除表格中的padding*/
    .ivu-table-cell{
        padding: 0;
    }
 
</style>