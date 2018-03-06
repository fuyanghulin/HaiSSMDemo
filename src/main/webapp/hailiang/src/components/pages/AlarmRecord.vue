<template>
<div class="alarmrecord">
    <nav>
        <div>
            <i-Button type="primary" @click="del" v-if="userType==1" disabled>删除</i-Button>
        </div>
        <div>
            <i-Input placeholder="请输入报警人姓名..." style="width: 300px"></i-Input>
            <i-Button type="primary" shape="circle" icon="ios-search"></i-Button>
        </div>
    </nav>
    <i-Table ref='table' border stripe :columns="columns" :data="data" @on-selection-change="chooseAll" @on-row-click="handleRow"></i-Table>
    <Page :total="totalRecord"
          show-total show-elevator :current="page.current" @on-change="next"
          :page-size="page.pageNum" size='small'></Page>
</div>
</template>

<script>
import dataUrl from '../../assets/config.js'
export default{
	name: 'AlarmRecord',
    props:{
        indexloading: {
            type: Boolean,
            default: true
        }
    },
        data: function () {
            return {
                userType: '',
                totalRecord: 0, /*总页数*/
                page: {
                    current: 1,
                    pageNum: 20
                },
                delArr: [],
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
                    },
                    {
                        title: '报警电话',
                        key: 'alarmPhone',
                    },
                    {
                        title: '报警地点',
                        key: 'alarmPlace',
                    }
                ],
                data: [],
            }
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
            del: function () {
                var _self = this;
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.goodsType.del+_self.delArr,
                    cache: false,
                    success: function () {
                        _self.delArr = [];
                        _self.getData();
                        _self.$Message.info('刪除成功');
                    }
                });
            },
            chooseAll: function (data) {
                var _self = this;
                for (var i in data) {
                    _self.delArr.push(data[i].id);
                }
            },
            next: function (data) {
                this.page.current = data;
                this.getData();
            },
            add0: function (m) {
                return m < 10 ? '0' + m : m
            },
            format: function (nS) {
                var time = new Date(nS);
                var y = time.getFullYear();
                var m = time.getMonth() + 1;
                var d = time.getDate();
                return y + '-' + this.add0(m) + '-' + this.add0(d);
            },
            getData: function () {
                var _self = this;
                _self.$Loading.start();
                if (_self.userType === 3) {
                    //根据公司ID查询数据
                    _self.page.company_id =_self.$cookies.get("companyID");// JSON.parse(Cookies.get("state")).companyID;
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.alarm.getAlarmByCompanyID,
                        cache: false,
                        data: _self.page,
                        success: function (data) {
                            _self.$Loading.finish();
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            _self.data = data.dataList;
                        }
                    });
                } else {
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.alarm.all,
                        cache: false,
                        data: _self.page,
                        success: function (data) {
                            _self.$Loading.finish();
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            _self.data = data.dataList;
                        }
                    });
                }
            }
        },
        activated: function () {//created:
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
        handleRow: function(data,index){
            var _self=this;
            // console.log(data);
            // console.log(index);
            //_self.data1[index].checked=true;
            this.$refs.table.toggleSelect(index);
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

</style>