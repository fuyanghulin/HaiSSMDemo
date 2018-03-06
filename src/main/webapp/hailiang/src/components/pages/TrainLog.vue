<template>
<div class="trainlog">
<nav>
        <!--<div>
            <i-Input placeholder="请输入培训主题..." style="width: 300px"></i-Input>
            <i-Button type="primary" shape="circle" icon="ios-search"></i-Button>
        </div>-->
    </nav>
    <i-Table :columns="columns" :data="data" border stripe  @on-row-click="handleRow" ref='table'></i-Table>
    <Page :total="totalRecord" show-total
          show-elevator :current="page.current" @on-change="next"
          :page-size="page.pageNum" size='small'></Page>
</div>
</template>

<script>
import dataUrl from '../../assets/config.js'
export default{
	name: 'TrainLog',
props:{
    indexloading: {
        type: Boolean,
        default: true
    }
},

        data:function() {
            return {
                userType: '',
                totalRecord: 0,
                page: {
                    current: 1,
                    pageNum: 20
                },
                loading: true,
                columns: [
                    {
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '培训主题',
                        key: 'peixunname'
                    },
                    {
                        title: '驾驶员',
                        key: 'driverpeoplename'
                    },
                    {
                        title: '培训状态',
                        key: 'state',
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
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
                                            this.show(params.index);
                                        }.bind(this)
                                    }
                                }, '查看')
                            ]);
                        }.bind(this)
                    }
                ],
                data: []
            }
        },
        methods: {
            indexloading: function(){
                this.$emit('getloading',false);
            },
            show:function(index) {
                this.$Modal.info({
                    title: '培训信息',
                    content: '培训主题：' + this.data[index].peixunname + '<br>驾驶员：' + this.data[index].driverpeoplename + '<br>培训状态：' + this.data[index].state
                })
            },
            next:function(data) {
                this.page.current = data;
                this.getData();
            },
            getData:function() {
                var _self = this;
                _self.$Loading.start();
                if (_self.userType === 3) {
                    //根据公司ID查询数据
                    _self.page.company_id=parseInt(_self.$cookies.get("companyID"));//JSON.parse(Cookies.get("state")).companyID;
                    $.ajax({
                        type: 'GET',
                        url:dataUrl.dataUrl.trainLog.getTrainLogByCompanyID,
                        data:_self.page,
                        cache: false,
                        success: function (data) {
                            _self.$Loading.finish();
                            if (typeof data == "object") {
                                _self.totalRecord = data.totalRecord;
                                _self.page.current = data.currentPage;
                                _self.data = data.dataList;
                            } else {
                                _self.data = [];
                            }
                        },
                        error:function () {
                            _self.$Loading.finish();
                        }
                    });
                } else {
                    $.ajax({
                        type: 'GET',
                        url:dataUrl.dataUrl.trainLog.all,
                        data:_self.page,
                        cache: false,
                        success: function (data) {
                            _self.$Loading.finish();
                            if (typeof data == "object") {
                                _self.totalRecord = data.totalRecord;
                                _self.page.current = data.currentPage;
                                _self.data = data.dataList;
                            } else {
                                _self.data = [];
                            }
                        },
                        error:function () {
                            _self.$Loading.finish();
                        }
                    });
                }
            },
            handleRow: function(data,index){
                var _self=this;
                // console.log(data);
                // console.log(index);
                //_self.data1[index].checked=true;
                this.$refs.table.toggleSelect(index);
            }//当点击一行是触发该函数，同时会触发chooseAll
        },
        activated: function() {//created:
        	var _self = this;
        
            if (_self.$cookies.get("ID") == 1) {
                if (_self.$cookies.get("roleID") == 1) {
                    _self.userType = 1;
                	_self.getData();
                } else if (_self.$cookies.get("roleID") == 2) {
                    _self.userType = 2;
                	_self.getData();
                } else {
                    _self.userType = 3;
                	_self.getData();
                }
            }
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

</style>