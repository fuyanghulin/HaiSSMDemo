<template>
<div class="gpsbreak">
    <nav>
        <div>
            <i-Button type="primary" @click="del" v-if="userType==1">删除</i-Button>
        </div>
        <div>
            <i-Input placeholder="请输入报警人..." style="width: 300px"></i-Input>
            <i-Button type="primary" shape="circle" icon="ios-search"></i-Button>
        </div>
    </nav>
    <i-Table ref='table' :columns="columns" :data="data" @on-selection-change="chooseAll" border stripe @on-row-click="handleRow"></i-Table>
    <Page :total="totalPage"  show-total
          show-elevator :current="page.current" @on-change="next"
          :page-size="page.pageNum" size='small'></Page>
</div>
</template>

<script>
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
                totalPage: 0, /*总页数*/
                page: {
                    current: 1, /*当前页*/
                    pageNum: 15/*每页数据量*/
                },
                columns: [
                    {
                        type: 'selection',
                        align: 'center',
                    },
                    {
                        title: '报警人ID',
                        key: 'driverId',
                    },
                    {
                        title: '报警时间',
                        key: 'alarmTime',
                        sortable: true
                    },
                    {
                        title: '报警电话',
                        key: 'alarmPhone',
                    },
                    {
                        title: '报警地点',
                        key: 'alarmPlace',
                    },

                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
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
                                            this.show(params.index)
                                        }.bind(this)
                                    }
                                }, '查看'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function () {
                                            this.delArr.push(this.data[params.index].id);
                                            this.del();
                                        }.bind(this)
                                    }
                                }, '删除')
                            ]);
                        }.bind(this)
                    }
                ],
                data: [],
            }
        },
        methods: {
indexloading: function(){
    this.$emit('getloading',false);
},
            del:function() {
                var _self = this;
                $.get(dataUrl.dataUrl.person.del + _self.delArr,
                    function (data, status) {
                        console.log(status);
                        if (status == 'success') {
                            _self.getData();
                            _self.$Message.info('刪除成功');
                        }
                        else {
                            _self.$Message.info('刪除失败');
                        }
                    }
                );
            },
            chooseAll:function(data) {
                var _self = this;
                for (var i in data) {
                    _self.delArr.push(data[i].id);
                }
            },
            next:function(data) {
                this.page.current = data;
                this.getData();
            },
            format: function(nS) {
                var time = new Date(parseInt(nS));
                var y = time.getFullYear();
                var m = time.getMonth() + 1;
                var d = time.getDate();
                return y + '-' + this.add0(m) + '-' + this.add0(d);
            },
            add0: function(m) {
                return m < 10 ? '0' + m : m
            },
            getData:function() {
                var _self = this;
                _self.$Loading.start();
                $.ajax({
                    url: dataUrl.dataUrl.baseUrl.baseUrl+'/HaiSSMDemo/allPeople.action',
                    type: 'get',
                    data: _self.page,
                    success:function(data) {
                        console.log('获取数据');
                        console.log(data);
                        _self.$Loading.finish();
                        _self.totalPage = data.totalPage;
                        _self.page.current = data.currentPage;
                        _self.page.pageNum = data.pageSize;
                        for (var i in data.dataList) {
                            data.dataList[i].birthday = _self.format(data.dataList[i].birthday);
                            data.dataList[i].driLicenceTime = _self.format(data.dataList[i].driLicenceTime);
                            data.dataList[i].driverzigezhengTime = _self.format(data.dataList[i].driverzigezhengTime);
                        }

                        _self.data = data.dataList;
                        console.log(_self.data);

                    },
                    error:function() {
                        _self.$Loading.error();
                    }
                });
            },
            handleRow: function(data,index){
                var _self=this;
                // console.log(data);
                // console.log(index);
                //_self.data1[index].checked=true;
                this.$refs.table.toggleSelect(index);
            }//当点击一行是触发该函数，同时会触发chooseAll
        },
        mounted: function () {
            //this.$refs.head.style.display = 'block';
this.indexloading();
        },
        beforeDestroy: function(){
            this.$emit('getloading',true);
        },
        activated: function() {//created:
            //document.body.removeChild(document.getElementById('tloading'));
            //this.getData();
        	var _self = this;
        
			/*if (_self.$cookies.get("ID") == 1) {
				if (_self.$cookies.get("roleID") == 1) {
				    _self.userType = 1;
					_self.getData();
				} else if (_self.$cookies.get("roleID") == 2) {
				    _self.userType = 2;
					_self.getData();
				} else if (_self.$cookies.get("roleID") == 3) {
				    _self.userType = 3;
					_self.getData();
				}
			}*/
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

        .ivu-form-item-content {
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
            height: 100%;
            top: 0;
            bottom: 0;
        }

        .ivu-modal-content {
            border-radius: 0px;
        }

        .ivu-modal-body {
            margin-bottom: 64px;
        }

        .ivu-modal-footer {
            z-index: 9999;
            background: #FFFFFF;
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            height: 64px;
        }
        /* 加载loading */
        #tloading{
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
      }
</style>