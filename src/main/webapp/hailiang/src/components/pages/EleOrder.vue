<template>
<div class="eleorder">
    <nav>
        <div>
            <i-Button type="primary" @click="open($event)" v-if="userType==3">添加</i-Button>
            
            <Tooltip content="删除所选货物信息" placement="bottom-start">
                <i-Button type="error" @click="del" v-if="userType===1">删除</i-Button>
            </Tooltip>
        </div>
        <div>
            <i-Input placeholder="请输入托运方..." style="width: 300px" v-model="searchText" @keyup.native.enter="search"></i-Input>
            <i-Button type="primary" shape="circle" icon="ios-search" @click="search"></i-Button>
            <i-Button type="primary" @click="getData" style="margin-left:20px;">刷新</i-Button>
        </div>
    </nav>
    <i-Table border :columns="columns" :data="data" @on-selection-all="chooseAll" @on-selection-change="sel_change" @on-row-dblclick='print_open'></i-Table>
    <Page :total="totalRecord"  show-total
          show-elevator :current="page.current" @on-change="next" :page-size="page.pageNum"></Page>

    <Modal
            width="70%"
            v-model="modal1"
            :closable="false"
            @on-cancel="cancel">
        <i-form ref="formValidate" :model="formValidate" :rules="ruleValidate" id='printhere'>
            <Row>
                <i-Col span="11">
                    <Form-Item label="托运方" prop="shipper">
                        <i-Input placeholder="请输入托运方" v-model="formValidate.shipper" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="装货时间">
                        <Date-Picker type="date" placeholder="选择日期" style="width: 100%"
                                     v-model="formValidate.consignDate" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="装货时间">
                        <Date-Picker type="date" placeholder="选择日期" style="width: 100%"
                                     v-model="formValidate.loadingDate" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="卸货时间">
                        <Date-Picker type="date" placeholder="选择日期" style="width: 100%"
                                     v-model="formValidate.uploadDate" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
            </Row>
            <!--发货基本信息-->
            <Row>
                <i-Col span="11">
                    <Form-Item label="发货点">
                        <i-Input placeholder="请输入发货点" v-model="formValidate.sendPlace" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="发货省市">
                        <Cascader :data="cityData" v-model="theSendCity" style="width: 100%" v-bind:disabled="openState==='查看'"></Cascader>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="6">
                    <form-Item label="装货地点">
                        <i-Input placeholder="请输入装货地点" v-model="formValidate.loadPlace" v-bind:disabled="openState==='查看'"></i-Input>
                    </form-Item>
                </i-Col>
                <i-col span="3"></i-col>
                <i-Col span="6">
                    <form-Item label="联系人">
                        <i-Input placeholder="请输入联系人" v-model="formValidate.loadLinkman" v-bind:disabled="openState==='查看'"></i-Input>
                    </form-Item>
                </i-Col>
                <i-col span="3"></i-col>
                <i-Col span="6">
                    <form-Item label="电话">
                        <i-Input placeholder="请输入联系人电话" v-model="formValidate.loadLinkmanPhone" v-bind:disabled="openState==='查看'"></i-Input>
                    </form-Item>
                </i-Col>
            </Row>
            <!--收货基本信息-->
            <Row>
                <i-Col span="11">
                    <Form-Item label="收货点">
                        <i-Input placeholder="请输入收货点" v-model="formValidate.receivePlace" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="收货省市">
                        <Cascader :data="cityData" v-model="theReceiveCity" style="width: 100%" v-bind:disabled="openState==='查看'"></Cascader>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="6">
                    <form-Item label="收货地点">
                        <i-Input placeholder="请输入收货地点" v-model="formValidate.uploadAdress" v-bind:disabled="openState==='查看'"></i-Input>
                    </form-Item>
                </i-Col>
                <i-col span="3"></i-col>
                <i-Col span="6">
                    <form-Item label="联系人">
                        <i-Input placeholder="请输入联系人" v-model="formValidate.uploadLinkman" v-bind:disabled="openState==='查看'"></i-Input>
                    </form-Item>
                </i-Col>
                <i-col span="3"></i-col>
                <i-Col span="6">
                    <form-Item label="电话">
                        <i-Input placeholder="请输入联系人电话" v-model="formValidate.uploadLinkmanPhone" v-bind:disabled="openState==='查看'"></i-Input>
                    </form-Item>
                </i-Col>
            </Row>
            <!--货物信息-->
            <Row>
                <i-Col span="11">
                    <form-Item label="货物名称">
                        <i-select  placeholder="请选择" @on-change="goodsChange"
                                v-model="goodsPosition" v-bind:disabled="openState==='查看'">
                            <i-option v-for="(item,index) in goodsList" :value="index">{{item.name}}</i-option>
                        </i-select>
                    </form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="编号">
                        <i-input v-model="formValidate.nationCode" disabled></i-input>
                    </Form-Item>
                </i-Col>

            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="货物类型">
                        <i-input v-model="formValidate.code" disabled></i-input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="件数">
                        <i-input v-model="formValidate.total" v-bind:disabled="openState==='查看'"></i-input>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="6">
                    <Form-Item label="重量">
                        <i-input v-model="formValidate.weight" v-bind:disabled="openState==='查看'"></i-input>
                    </Form-Item>
                </i-Col>
                <i-col span="3"></i-col>
                <i-Col span="6">
                    <Form-Item label="体积">
                        <i-input v-model="formValidate.volume" v-bind:disabled="openState==='查看'"></i-input>
                    </Form-Item>
                </i-Col>
                <i-col span="3"></i-col>
                <i-Col span="6">
                    <Form-Item label="运费">
                        <i-input v-model="formValidate.freight" v-bind:disabled="openState==='查看'"></i-input>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="车牌">
                        <i-select placeholder="选择车辆"
                                v-model="carPosition" @on-change="carChange" v-bind:disabled="openState==='查看'">
                            <i-option v-for="(item,index) in carList" :value="index">{{item.carNum}}</i-option>
                        </i-select>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <form-Item label="挂车车牌">
                        <i-input disabled v-model="carMessage.guacarNum" ></i-input>
                    </form-Item>
                </i-Col>

            </Row>
            <Row>
                <i-Col span="11">
                    <form-Item label="车辆类型">
                        <i-input disabled v-model="carMessage.carType"></i-input>
                    </form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <form-Item label="载质量(kg)">
                        <i-input disabled v-model="carMessage.allowWeight"></i-input>
                    </form-Item>
                </i-Col>
            </Row>
            <!---->
            <Row style="border-bottom: 1px dashed #EEEEEE;padding-bottom: 10px;">
                <i-Col span="11">
                    驾驶员
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    押运员
                </i-Col>
            </Row>
            <!--驾驶员和押运员-->
            <Row style="padding-top: 10px">
                <!--驾驶员-->
                <i-Col span="12" >
                    <i-Col span="12">
                        <Row>
                            <form-Item label="姓名">
                                <i-input disabled v-model="carMessage.driverName"></i-input>
                            </form-Item>
                        </Row>
                        <Row>
                            <form-Item label="驾驶证">
                                <i-input disabled v-model="carMessage.driveLicence"></i-input>
                            </form-Item>
                        </Row>
                        <Row>
                            <form-Item label="资格证">
                                <i-input disabled v-model="carMessage.drivezigezheng"></i-input>
                            </form-Item>
                        </Row>
                        <Row>
                            <form-Item label="电话">
                                <i-input disabled v-model="carMessage.driverPhone"></i-input>
                            </form-Item>
                        </Row>
                        <Row>
                            <form-Item label="身份证">
                                <i-input disabled v-model="carMessage.driveridNum"></i-input>
                            </form-Item>
                        </Row>
                    </i-Col>
                    <i-Col span="12" style="display: flex;justify-content: center;align-items: center">
                        <img :src="carMessage.driverPictureUrl" style="width: 200px;height:230px;min-width: 100px;min-height: 130px"/>
                    </i-Col>
                </i-Col>
                <!--押运员-->
                <i-Col span="12">
                    <i-Col span="12">
                        <Row>
                            <form-Item label="姓名">
                                <i-input disabled v-model="carMessage.safer"></i-input>
                            </form-Item>
                        </Row>
                        <Row>
                            <form-Item label="资格证">
                                <i-input disabled v-model="carMessage.saferzigezheng"></i-input>
                            </form-Item>
                        </Row>
                        <Row>
                            <form-Item label="电话">
                                <i-input disabled v-model="carMessage.saferPhone"></i-input>
                            </form-Item>
                        </Row>
                        <Row>
                            <form-Item label="驾驶证">
                                <i-input disabled></i-input>
                            </form-Item>
                        </Row>
                        <Row>
                            <form-Item label="身份证">
                                <i-input disabled v-model="carMessage.saferidNum"></i-input>
                            </form-Item>
                        </Row>
                    </i-Col>
                    <i-Col span="12" style="display: flex;justify-content: center;align-items: center">
                        <img :src="carMessage.saferPictureUrl" style="width: 200px;height:230px;min-width: 100px;min-height: 130px"/>
                    </i-Col>
                </i-Col>
            </Row>
        </i-form>
        <!--自定义-->
        <div slot="footer" class="ivu-modal-footer1">
            <i-button type="text" style="width: 100px;" @click="cancel">取消</i-button>
            <i-button type="primary" @click="upMessage" :loading="loading" style="width: 100px;">确定</i-button>
        </div>
    </Modal>
    <!--显示订单状态-->
    <Modal
            width="60%"
            v-model="modal2"
            :closable="false"
            :styles="{top: '20px'}">
        <div class="theMsgBox">
            <div class="oneheadBox">订单状态</div>
            <div style="height: 40px; line-height: 40px; text-align: center">
                {{waybillMsg.waybillState}}
            </div>
            <div class="oneheadBox">行车日志</div>
            <ul>
                <li v-for="item in theDriverLog">
                    <Icon type="checkmark-round" style="color: #2d8cf0"></Icon>
                    {{item.name}}
                </li>
            </ul>
            <div class="oneheadBox">车况检查</div>
            <ul>
                <li v-for="item in theCarChecked">
                    <Icon type="checkmark-round" style="color: #2d8cf0"></Icon>
                    {{item.name}}
                </li>
            </ul>
        </div>
    </Modal>
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
import cityDatas from '../../assets/cityData.json'
export default{
	name: 'EleOrder',
    data: function() {
        return {
            searchText: '',
            userType: '',
            totalRecord: 0,
            /*总页数*/
            page: {
                current: 1,
                /*当前页*/
                pageNum: 15 /*每页数据量*/
            },
            formValidate: {
                shipper: '',
                consignDate: '',
                loadingDate: '',
                uploadDate: '',
                sendPlace: '',
                sendCity: '',
                loadPlace: '',
                loadLinkman: '',
                loadLinkmanPhone: '',
                receivePlace: '',
                receiveCity: '',
                uploadAdress: '',
                uploadLinkman: '',
                uploadLinkmanPhone: '',
                goodName: '',
                goodsId: '',
                nationCode: '',
                code: '',
                total: '',
                weight: '',
                volume: '',
                freight: '',
                carNum: '',
                reserveOne: '',

            },
            ruleValidate: {
                shipper: [{
                    required: true,
                    message: '托运方不能为空',
                    trigger: 'blur'
                }]
            },
            modal1: false,
            columns: [{
                type: 'selection',
                width: 60,
                align: 'center',
                fixed: 'left'
            }, {
                width: 100,
                align: 'center',
                title: '托运方',
                key: 'shipper'
            }, {
                width: 120,
                align: 'center',
                title: '托运日期',
                key: 'realConsignDate',
                sortable: true
            }, {
                width: 120,
                align: 'center',
                title: '装货时间',
                key: 'realLoadingDate',
                sortable: true
            }, {
                width: 120,
                align: 'center',
                title: '卸货时间',
                key: 'realUploadDate',
                sortable: true
            }, {
                width: 100,
                align: 'center',
                title: '发货点',
                key: 'sendPlace'
            }, {
                width: 150,
                align: 'center',
                title: '发货城市',
                key: 'sendCity'
            }, {
                width: 150,
                align: 'center',
                title: '装货地址',
                key: 'loadPlace'
            }, {
                width: 100,
                align: 'center',
                title: '装货联系人',
                key: 'loadLinkman'
            }, {
                width: 120,
                align: 'center',
                title: '装货联系人电话',
                key: 'loadLinkmanPhone'
            }, {
                width: 100,
                align: 'center',
                title: '收货点',
                key: 'receivePlace'
            }, {
                width: 150,
                align: 'center',
                title: '收货城市',
                key: 'receiveCity'
            }, {
                width: 150,
                align: 'center',
                title: '卸货地址',
                key: 'uploadAdress'
            }, {
                width: 100,
                align: 'center',
                title: '卸货联系人',
                key: 'uploadLinkman'
            }, {
                width: 100,
                align: 'center',
                title: '卸货联系人电话',
                key: 'uploadLinkmanPhone'
            }, {
                width: 150,
                align: 'center',
                title: '货物名称',
                key: 'goodName'
            }, {
                width: 100,
                align: 'center',
                title: '商品总数',
                key: 'total',
                sortable: true
            }, {
                width: 100,
                align: 'center',
                title: '重量',
                key: 'weight',
                sortable: true
            }, {
                width: 100,
                align: 'center',
                title: '体积',
                key: 'volume',
                sortable: true
            }, {
                width: 100,
                align: 'center',
                title: '运费',
                key: 'freight',
                sortable: true
            }, {
                width: 100,
                align: 'center',
                title: '车牌号',
                key: 'carNum'
            }, {
                width: 100,
                align: 'center',
                title: '运单状态',
                key: 'waybillState',
                render: function(h, params) {
                    return h('div', [
                        h('Button', {
                            props: {
                                type: 'text',
                                size: 'small'
                            },
                            on: {
                                click: function() {
                                    this.modal2 = true;
                                    var _self = this;
                                    this.theCarChecked = [];
                                    this.theDriverLog = [];
                                    $.ajax({
                                        type: 'GET',
                                        url: dataUrl.dataUrl.waybill.selectCheck + _self.data[params.index].id,
                                        cache: false,
                                        success: function(data) {
                                            _self.waybillMsg = data[0];
                                            /// 获得车辆检查数据
                                            if (_self.waybillMsg.checkcarArray) {
                                                $.ajax({
                                                    type: 'GET',
                                                    url: dataUrl.dataUrl.waybill.selectCheckText,
                                                    cache: false,
                                                    data: _self.waybillMsg.checkcarArray,
                                                    success: function(data) {
                                                        _self.theCarChecked = data;
                                                    }
                                                });
                                            }
                                            /// 获得行车日志
                                            if (_self.waybillMsg.driverlogArray) {
                                                $.ajax({
                                                    type: 'GET',
                                                    url: dataUrl.dataUrl.waybill.selectDriverLog,
                                                    cache: false,
                                                    data: _self.waybillMsg.driverlogArray,
                                                    success: function(data) {
                                                        _self.theDriverLog = data;
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }.bind(this)
                            }
                        }, '查看')
                    ]);
                }.bind(this)
            }, {
                width: 150,
                title: '查看打印',
                key: 'action1',
                align: 'center',
                fixed: 'right',
                render: function(h, params) {
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
                                    click: function() {
                                        this.openState = "查看"; //需要在这里添加一步，修改拿到的货物和车辆信息
                                        var _self = this;
                                        this.btnclick(this.data[params.index], params);
                                        this.modal1 = true;
                                    }.bind(this)
                                }
                            }, '查看'),
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                on: {
                                    click: function() {
                                        this.$message.info('打印功能尚未完善！');
                                    }.bind(this)
                                }
                            }, '打印')
                        ]);
                }.bind(this)
            }, {
                width: 150,
                title: '操作',
                key: 'action',
                align: 'center',
                fixed: 'right',
                render: function(h, params) {
                    if (this.userType == 1) {
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
                                    click: function() {
                                        this.openState = "修改";
                                        var _self = this;
                                        var click_obj = this.data[params.index]; //把点击的这个信息保存下

                                        _self.btnclick(_self.data[params.index], params); //fengzhuan                                        
                                    }.bind(this)
                                }
                            }, '修改'),
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: function() {
                                        this.delone = this.data[params.index].id;
                                        this.del_index(this.data[params.index].id);
                                        this.onedel = true;
                                    }.bind(this)
                                }
                            }, '删除')
                        ]);
                    } else if (this.userType == 3){
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
                                    click: function() {
                                        this.openState = "修改";
                                        var _self = this;
                                        var click_obj = this.data[params.index]; //把点击的这个信息保存下

                                        _self.btnclick(_self.data[params.index], params); //fengzhuan                                        
                                    }.bind(this)
                                }
                            }, '修改')
                        ]);
                    }else if(this.userType == 2){
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
                                    click: function() {
                                        this.openState = "查看"; //需要在这里添加一步，修改拿到的货物和车辆信息
                                        var _self = this;
                                        this.btnclick(this.data[params.index], params);
                                        this.modal1 = true;
                                    }.bind(this)
                                }
                            }, '不可修改删除')
                        ]);
                    }

                }.bind(this)
            }],
            modal2: false,
            data: [],
            waybillMsg: {},
            cityData: [],
            theSendCity: [],
            theReceiveCity: [],
            openState: '',
            loading: false,
            file: null,
            delArr: [],
            goodsList: [],
            carList: [],
            goodsPosition: 0,
            carPosition: 0,

            carMessage: {
                driverPictureUrl: '',
                saferPictureUrl: '',
                idNum: 0
            },

            driverLog: [],
            carChecked: [],
            theDriverLog: [],
            theCarChecked: [],
            delone: '',
            onedel: false,
            modal3: false,
            modalp: false,
            printInfo: {
                hlCarinfo: {
                    companyName: '',
                    carNum: '',
                    carType: '',
                    totalWei: ''
                },
                gpsinfo: {
                    createdate: '',
                    velocity: '',
                    location: ''
                },
                status: '',
                driver: {
                    name: '',
                    idNum: '',
                    phone: '',
                    drivezigezheng: '',
                    pictureUrl: ''
                },
                overSpeedVelocityTimes: '',
                safer: {
                    name: '',
                    idNum: '',
                    phone: '',
                    drivezigezheng: '',
                    pictureUrl: ''
                },
                hlSafecard: {
                    safeCardName: ''
                },
                hlGoodsinfo: {
                    name: ''
                }
            }
        }
    },
    created: function() {
        var _self = this;
        
             if (_self.$cookies.get("ID") == 1) {
                if (_self.$cookies.get("roleID") == 1) {
                    _self.userType = 1;
                } else if (_self.$cookies.get("roleID") == 2) {
                    _self.userType = 2;
                    this.columns[this.columns.length-1].width=0;//2018年2月23日13:24:18添加用于隐藏td
                } else {
                    _self.userType = 3;
                    this.columns[this.columns.length-1].width=0;//2018年2月23日13:24:18添加用于隐藏td
                }
                _self.getData();
            }
        
        //_self.getData();写在上面可以使得没有cookies时没有数据

        /// 获得货物名称
        //企业
        //console.log('即将进入if语段');
        if (_self.userType == '3') {
            //console.log('进入该if语段');
            var idata = {};
            for (var key in _self.page) {
                idata[key] = _self.page[key];
            }
            idata.companyId = parseInt(_self.$cookies.get("companyID"));//JSON.parse(Cookies.get("state")).companyID;
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.goods.company,
                data: idata,
                cache: false,
                success: function(data) {
                    console.log('输出返回的货物信息列表');
                    console.log(data);
                    if (data != null && data.dataList != undefined) {
                        _self.goodsList = data.dataList;
                        _self.formValidate.goodName = _self.goodsList[0].name;
                        _self.formValidate.goodsId = _self.goodsList[0].id;
                        _self.formValidate.nationCode = _self.goodsList[0].code;
                        _self.formValidate.code = _self.goodsList[0].reserveTwo;
                    }
                }
            });
        } else {
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.waybill.goodInfo, //获取全部的电子运单
                cache: false,
                success: function(data) {
                    if (data.length > 0) {
                        _self.goodsList = data;
                        _self.formValidate.goodName = _self.goodsList[0].name;
                        _self.formValidate.goodsId = _self.goodsList[0].id;
                        _self.formValidate.nationCode = _self.goodsList[0].code;
                        _self.formValidate.code = _self.goodsList[0].reserveTwo;
                        //console.log(data);
                    }
                }
            });
        }
        /////获取车辆列表
        if (_self.userType == 3) {
            //根据公司Name查询数据
            //console.log('看下cookiees');
            //console.log(JSON.parse(Cookies.get("state")));
            idata = {};
            idata.current = _self.page.current;
            idata.pageNum = _self.page.pageNum;
            idata.company_id =parseInt(_self.$cookies.get("companyID"));// JSON.parse(Cookies.get("state")).companyID;
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.carInfo.selectCarInfoByCompanyId, //selectCarInfoByCompanyId
                data: idata,
                cache: false,
                success: function(data) {
                    if (data.dataList.length > 0) {
                        _self.carList = data.dataList;
                        _self.formValidate.carNum = data.dataList[0].carNum;
                        _self.carMessage.allowWeight = data.dataList[0].allowWeight;
                        _self.carMessage.guacarNum = data.dataList[0].guacarNum;
                        _self.carMessage.carType = data.dataList[0].carType;
                        _self.carMessage.driverName = data.dataList[0].driverName;
                        _self.carMessage.safer = data.dataList[0].safer;

                        console.log(data);
                    }
                }
            });
        } else {
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.carInfo.all,
                data: _self.page,
                cache: false,
                success: function(data) {
                    if (data.dataList.length > 0) {
                        _self.carList = data.dataList;
                        _self.formValidate.carNum = data.dataList[0].carNum;
                        _self.carMessage.allowWeight = data.dataList[0].allowWeight;
                        _self.carMessage.guacarNum = data.dataList[0].guacarNum;
                        _self.carMessage.carType = data.dataList[0].carType;
                        _self.carMessage.driverName = data.dataList[0].driverName;
                        _self.carMessage.safer = data.dataList[0].safer;
                    }
                }
            });
        }
        _self.cityData=cityDatas;

    },
    mounted: function() {
        //this.$refs.headd.style.display = 'block';
        console.log(this.userType);
    },
    methods: {
        //btnclick函数是在用户点击查看、修改时触发，将点击一行的数据传入，
        //主要是通过公司id获取数据，当然查看的时候其实不需要做这些处理，这里为了简便就一起处理了
        //企业添加的逻辑还是用原来的，暂时未做修改
        btnclick: function(obj, paramss) {
            var _self = this;
            var idata = {}; //ajax请求参数
            // for(var key in _self.page){
            //     idata[key]=_self.page[key];
            // }
            idata.current = _self.page.current;
            idata.pageNum = _self.page.pageNum;
            idata.companyId = obj.compayId;


            _self.goodsList = [];
            $.ajax({ //先请求拿到货物信息
                type: 'GET',
                url: dataUrl.dataUrl.goods.company,
                data: idata,
                cache: false,
                success: function(data_ajax) {
                    if (data_ajax != null && data_ajax.dataList != undefined) {
                        _self.data1 = data_ajax.dataList;
                        _self.totalRecord = data_ajax.totalRecord;
                        _self.page.current = data_ajax.currentPage;
                        _self.theChecked = [];
                    } else {
                        _self.data1 = [];
                    }
                    if (data_ajax != null && data_ajax.dataList != undefined) {
                        _self.goodsList = data_ajax.dataList;
                    }


                    _self.carList = []; //在请求成功后请求车辆信息
                    delete idata.companyId;
                    idata.company_id = obj.compayId
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.carInfo.selectCarInfoByCompanyId,
                        data: idata,
                        cache: false,
                        success: function(data222) {
                            if (data222.dataList.length > 0) {
                                _self.carList = data222.dataList;
                            }

                            //两个请求都成功后可以做下一步操作
                            //_self.btnclick(click_obj,params);

                            _self.formValidate = JSON.parse(JSON.stringify(obj));
                            _self.theSendCity = obj.sendCity.split('/');
                            _self.theReceiveCity = obj.receiveCity.split('/');
                            if (!_self.formValidate.goodName && _self.goodsList.length > 0) { //改成id判断
                                _self.formValidate.goodName = _self.goodsList[0].name;
                                _self.formValidate.goodsId = _self.goodsList[0].id;
                                _self.formValidate.nationCode = _self.goodsList[0].code;
                                _self.formValidate.code = _self.goodsList[0].reserveTwo;
                            }
                            if (!_self.formValidate.carNum && _self.carList.length > 0) {
                                _self.formValidate.carNum = _self.carList[0].carNum;
                                _self.carMessage.allowWeight = _self.carList[0].allowWeight;
                                _self.carMessage.guacarNum = _self.carList[0].guacarNum;
                                _self.carMessage.carType = _self.carList[0].carType;
                                _self.carMessage.driverName = _self.carList[0].driverName;
                                _self.carMessage.safer = _self.carList[0].safer;
                            }
                            //console.log(_self.carList);
                            // if (_self.carList[paramss.index]) {
                            //     _self.carPosition = paramss.index;

                            // }
                            var new_car = obj.carNum;
                            _self.checkCar(new_car);
                            console.log(_self.carMessage);
                            _self.carChange(_self.formValidate.carNum);

                            var new_goods = obj.goodName; //改成id
                            _self.checkGoods(new_goods);


                            _self.modal1 = true;

                        }

                    });

                }
            });



        },
        search: function() {
            if (this.searchText.replace(/\s/g, '').length < 1) {
                alert('搜索内容不可为空');
            } else {

                var _self = this;

                if (_self.userType == 1 || _self.userType == 2) {
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.waybill.search + encodeURI(_self.searchText.replace(/\s/g, '')),
                        cache: false,
                        success: function(data) {
                            if (typeof data == "object") {
                                _self.totalRecord = data.totalRecord;
                                _self.page.current = data.currentPage;
                                _self.data = data.dataList;
                            } else {
                                _self.data = [];
                            }
                        }
                    });
                } else {
                    var idata = {};
                    idata.current = _self.page.current;
                    idata.pageNum = _self.page.pageNum;
                    //console.log(JSON.parse(Cookies.get("state")));    searchCompany
                    idata.companyId = parseInt(_self.$cookies.get("companyID"));//JSON.parse(Cookies.get("state")).companyID;
                    idata.Shipper = _self.searchText;
                    console.log(idata);
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.waybill.searchCompany,
                        data: idata,
                        cache: false,
                        success: function(data) {
                            if (typeof data == "object") {
                                _self.totalRecord = data.totalRecord;
                                _self.page.current = data.currentPage;
                                _self.data = data.dataList;
                            } else {
                                _self.data = [];
                            }
                        }
                    });
                }

            }
        },
        thegoodsInfo: function(_id) { //企业传入 JSON.parse(Cookies.get("state")).companyID,管理员传入得到的id值
            var _self = this;
            var idata = {};
            for (var key in _self.page) {
                idata[key] = _self.page[key];
            }
            idata.companyId = _id;
            console.log(_id);
            console.log('输出请求参数idata');
            console.log(idata);
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.goods.company,
                data: idata,
                cache: false,
                success: function(data) {
                    console.log('输出返回的货物信息列表');
                    console.log(data);
                    if (data != null && data.dataList != undefined) {
                        _self.goodsList = data.dataList;
                        _self.formValidate.goodName = _self.goodsList[0].name;
                        _self.formValidate.goodsId = _self.goodsList[0].id;
                        _self.formValidate.nationCode = _self.goodsList[0].code;
                        _self.formValidate.code = _self.goodsList[0].reserveTwo;
                    }

                }
            });
        },
        thecarInfo: function(company_id) { //JSON.parse(Cookies.get("state")).companyId,,企业换成companyId
            var _self = this;

            var idata = {};
            for (var key in _self.page) {
                idata[key] = _self.page[key];
            }
            idata.company_id = company_id;

            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.carInfo.getCarByCompanyId,
                data: idata,
                cache: false,
                success: function(data) {
                    //console.log(data);
                    if (data.dataList.length > 0) {
                        _self.carList = data.dataList;
                        _self.formValidate.carNum = data.dataList[0].carNum;
                        _self.carMessage.allowWeight = data.dataList[0].allowWeight;
                        _self.carMessage.guacarNum = data.dataList[0].guacarNum;
                        _self.carMessage.carType = data.dataList[0].carType;
                        _self.carMessage.driverName = data.dataList[0].driverName;
                        _self.carMessage.safer = data.dataList[0].safer;

                        //console.log(data);
                    }
                }
            });
        },

        upMessage: function() {
            var _self = this;
            console.log('输出upmessage信息');
            console.log(_self.formValidate);
            _self.loading = true;
            _self.$refs.formValidate.validate(function(valid) {
                if (_self.openState == "查看") {
                    _self.loading = false;
                    _self.modal1 = false;
                } else {
                    if (valid) {
                        // console.log(_self.formValidate);
                        _self.formValidate.sendCity = _self.theSendCity.join('/');
                        _self.formValidate.receiveCity = _self.theReceiveCity.join('/');
                        /////////
                        console.log('_self.formValidate' + _self.formValidate);
                        if (_self.formValidate.consignDate instanceof Date) {
                            _self.formValidate.consignDate = _self.month(_self.formValidate.consignDate);
                        }
                        if (_self.formValidate.loadingDate instanceof Date) {
                            _self.formValidate.loadingDate = _self.month(_self.formValidate.loadingDate);
                        }
                        if (_self.formValidate.uploadDate instanceof Date) {
                            _self.formValidate.uploadDate = _self.month(_self.formValidate.uploadDate);
                        }
                        if (_self.openState == "添加") {
                            _self.formValidate.reserveOne = "未执行";
                            //上传时添加一个货物id

                            _self.formValidate.goodsId = _self.goodsList[_self.goodsPosition].id;
                            console.log(_self.formValidate);
                            _self.formValidate.compayId = parseInt(_self.$cookies.get("companyID"));//JSON.parse(Cookies.get("state")).companyID;

                            _self.postData(_self, dataUrl.dataUrl.waybill.insert, _self.formValidate);
                        } else if (_self.openState == "修改") {
                            _self.formValidate.goodsId = _self.goodsList[_self.goodsPosition].id;
                            _self.postData(_self, dataUrl.dataUrl.waybill.upDate, _self.formValidate);
                        }
                    } else {
                        _self.loading = false;
                    }
                }
            });

            _self.$refs['formValidate'].resetFields();
            //console.log('清空内容');
        },
        month: function(date) {
            var m = '';
            if (date.getMonth() + 1 < 10) {
                m = "0" + (date.getMonth() + 1);
            } else {
                m = date.getMonth() + 1;
            }
            return date.getFullYear() + '-' + m + '-' + date.getDate();
        },
        time: function(times) {
            var d = new Date(parseInt(times));
            return d.getFullYear() + '/' + (d.getMonth() + 1) + '/' + d.getDate();
        },
        cancel: function() {
            var _self = this;
            _self.modal1 = false;
            _self.$refs['formValidate'].resetFields();
            console.log('清空内容');
            _self.loading = false; //其他页面还没加这个
        },
        del: function() {
            var _self = this;
            //console.log("所选个数："+_self.delArr.length);

            if (_self.delArr.length > 0) { //kk加了一个判断
                //console.log("进入modal2");
                _self.modal3 = true;
                /*if(ensure_del){
                    ok_del();
                }*/

            }

        },
        del_index: function(n) {
            var _self = this;
            _self.modal3 = true;


        },
        ok_del: function() {
            var _self = this;
            if (_self.onedel) {
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.waybill.del + _self.delone,
                    cache: false,
                    success: function(data) {
                        _self.getData();
                        _self.$Message.info('刪除成功');
                    }
                });
                _self.delone = '';
                _self.onedel = false;
            } else {
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.waybill.del + _self.delArr,
                    cache: false,
                    success: function(data) {
                        _self.delArr = [];
                        _self.getData();
                        _self.$Message.info('刪除成功');
                    }
                });
                _self.delArr = [];
            }

        },
        cancel_del: function() {
            this.modal3 = false;
            this.delone = '';
            this.onedel = false;
        },
        chooseAll: function(data) {
            var _self = this;
            //console.log(this.data.selection);
            _self.delArr = [];
            if (data.length) {
                for (var i in data) {
                    _self.delArr.push(data[i].id);
                }
            }

        },
        sel_change: function(data) {
            var _self = this;
            //console.log(data);
            _self.delArr = [];
            if (data.length) {

                for (var i in data) {
                    _self.delArr.push(data[i].id);
                }
            }

        },
        carChange: function(num) {
            console.log('输出车辆信息');
            var _self = this;
            console.log(_self.carList[_self.carPosition])

            if (num) {
                // console.log(num);
                for (var i = 0; i < this.carList.length; i++) {
                    if (this.carList[i].carNum === num) {
                        this.carPosition = i;
                        break;
                    }
                }
            }
            this.checkCar();
            if (this.carList.length > 0) {
                this.carMessage.allowWeight = this.carList[this.carPosition].allowWeight;
                this.carMessage.guacarNum = this.carList[this.carPosition].guacarNum;
                this.carMessage.carType = this.carList[this.carPosition].carType;
                this.carMessage.driverName = this.carList[this.carPosition].driverName;
                this.carMessage.safer = this.carList[this.carPosition].safer;
                this.formValidate.carNum = this.carList[this.carPosition].carNum;
                var _self = this;
                // 驾驶员
                var idata1 = {};
                idata1.id = _self.carList[_self.carPosition].driverId
                $.ajax({ /////////////////////getPeople//////////////////////////////////////////////////////////////////////////////
                    type: 'GET',
                    url: dataUrl.dataUrl.company.getPeople, //+encodeURI(_self.carList[_self.carPosition].driverName),
                    data: idata1,
                    cache: false,
                    success: function(data) {
                        console.log(data);
                        if (data.id) {
                            _self.carMessage.driveLicence = data.driveLicence;
                            _self.carMessage.drivezigezheng = data.drivezigezheng;
                            _self.carMessage.driverPhone = data.phone;
                            _self.carMessage.driverPictureUrl = 'http://localhost:8080' + '/pic/' + data.pictureName;
                            _self.carMessage.driveridNum = data.idNum;
                        }
                    }
                });
                // 押运员
                console.log(_self.carList[_self.carPosition]);
                var idata2 = {};
                idata2.id = _self.carList[_self.carPosition].saferId
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.company.getPeople, //+encodeURI(this.carList[this.carPosition].safer),
                    cache: false,
                    data: idata2,
                    success: function(data) {
                        //console.log(data);
                        if (data.id) {
                            _self.carMessage.saferzigezheng = data.drivezigezheng;
                            _self.carMessage.saferPhone = data.phone;
                            _self.carMessage.saferPictureUrl = 'http://localhost:8080' + '/pic/' + data.pictureName;
                            _self.carMessage.saferidNum = data.idNum;
                        }
                    }
                });

            }
        },
        checkCar: function(_num) {
            var _self = this;
            var flag = 0;
            for (var i in this.carList) {
                //console.log(i.name);
                if (_self.carList[i].carNum == _num) {
                    _self.carPosition = flag;
                    break; //跳出循环

                }
                flag++;
            }

        },
        checkGoods: function(_name) { //这里应该要改成id
            var _self = this;
            //.goodName;
            //console.log(_name);
            //console.log(_self.goodsList);
            var flag = 0;
            //console.log(_self.goodsList);//这里为什么是空的？？？？？？
            for (var i in this.goodsList) {
                //console.log(i.name);
                if (_self.goodsList[i].name == _name) {
                    /*_self.formValidate.goodName = _self.goodsList[i].name;
                    _self.formValidate.code = _self.goodsList[i].reserveTwo;
                    _self.formValidate.nationCode = _self.goodsList[i].code;*/
                    _self.goodsPosition = flag;
                    console.log(flag);
                    break; //跳出循环

                }
                flag++;
            }
        },
        goodsChange: function() {


            var _self = this;
            /*if(num){
                _self.formValidate.goodName = _self.goodsList[num].name;
                _self.formValidate.code = _self.goodsList[num].reserveTwo;
                _self.formValidate.nationCode = _self.goodsList[num].code;

            }*/
            if (_self.goodsList.length > 0) {
                this.formValidate.goodName = this.goodsList[this.goodsPosition].name;
                this.formValidate.goodsId = this.goodsList[0].id;

                this.formValidate.code = this.goodsList[this.goodsPosition].reserveTwo;
                this.formValidate.nationCode = this.goodsList[this.goodsPosition].code;
            }
            console.log('******************940行，输出formValidate***********************');
            console.log(this.formValidate);

        },
        handleUpload1: function(file) {
            this.formValidate.safeCardFile = file;
            return false;
        },
        handleUpload2: function(file) {
            this.formValidate.introductionFile = file;
            return false;
        },
        next: function(data) {
            this.page.current = data;
            this.getData();
        },
        add0: function(m) {
            return m < 10 ? '0' + m : m
        },
        format: function(nS) {
            var time = new Date(parseInt(nS));
            var y = time.getFullYear();
            var m = time.getMonth() + 1;
            var d = time.getDate();
            return y + '-' + this.add0(m) + '-' + this.add0(d);
        },
        open: function(obj) {
            var _self = this;
            if ($.trim(obj.currentTarget.innerText) == "添加") {
                //_self.$refs['formValidate'].resetFields();
                for (var key in this.formValidate) {
                    this.formValidate[key] = '';
                }
                // 初始化货物类型信息
                if (this.goodsList.length > 0) {
                    this.formValidate.goodName = this.goodsList[0].name;
                    _self.formValidate.goodsId = _self.goodsList[0].id;
                    this.formValidate.nationCode = this.goodsList[0].code;
                    this.formValidate.code = this.goodsList[0].reserveTwo;
                    //this.goodsChange();//kk
                }
                // 初始化车辆类型信息
                if (this.carList.length > 0) {
                    this.formValidate.carNum = this.carList[0].carNum;
                    this.carMessage.allowWeight = this.carList[0].allowWeight;
                    this.carMessage.guacarNum = this.carList[0].guacarNum;
                    this.carMessage.carType = this.carList[0].carType;
                    this.carMessage.driverName = this.carList[0].driverName;
                    this.carMessage.safer = this.carList[0].safer;
                    //this.carPosition = 0;
                    //this.carChange();
                }
                this.openState = $.trim(obj.currentTarget.innerText);
            }
            //this.$refs['formValidate'].resetFields();
            this.carChange();
            this.goodsChange();
            this.modal1 = true;

        },
        print_open: function(ddata) { //双击时打开打印预览
            var _self = this;
            //alert('您双击了改行');
            console.log(ddata);
            var idata = {};
            idata.plateNo = ddata.carNum;
            idata.goods_id = ddata.goodsId;
            $.ajax({
                type: 'GET',
                url: dataUrl.dataUrl.waybill.dbselect,
                data: idata,
                cache: false,
                success: function(data) {
                    //console.log(data);
                    _self.printInfo = data;
                    _self.printInfo.driver.pictureName = 'http://localhost:8080' + '/pic/' + data.driver.pictureName;
                    _self.printInfo.safer.pictureName = 'http://localhost:8080' + '/pic/' + data.safer.pictureName;
                    _self.printInfo.hlSafecard.safeCardName = 'http://localhost:8080' + '/SafeCard/' + data.hlSafecard.safeCardName;
                    console.log(_self.printInfo);
                }
            });
            _self.modal1 = true;
            _self.print_ok();

        },
        print_ok: function() {
            //setTimeout(function(){$("#printhere").jqprint();},2000);

            $("#printhere").jqprint();
            setTimeout(function() {
                this.modal1 = false;;
            }, 400);
        },
        print_cancel: function() {
            console.log('退出');
            this.modal1 = false;
        },
        mark_cli: function() {
            this.modalp = false;
        },
        getData: function() {
            var _self = this;
            _self.$Loading.start();
            if (_self.userType === 3) {
                //根据公司ID查询数据
                _self.page.company_id = parseInt(_self.$cookies.get("companyID"));//JSON.parse(Cookies.get("state")).companyID;
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.waybill.getWaybillByCompanyID,
                    data: _self.page,
                    cache: false,
                    success: function(data) {
                        _self.$Loading.finish();
                        if (typeof data == "object") {
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            _self.data = data.dataList;
                            //console.log(data.dataList);
                            // 循环对时间进行处理
                            for (var i = 0; i < _self.data.length; i++) {
                                if (_self.data[i].consignDate) {
                                    _self.data[i].realConsignDate = _self.time(_self.data[i].consignDate);
                                    _self.data[i].consignDate = new Date(_self.data[i].consignDate);
                                }
                                if (_self.data[i].loadingDate) {
                                    _self.data[i].realLoadingDate = _self.time(_self.data[i].loadingDate);
                                    _self.data[i].loadingDate = new Date(_self.data[i].loadingDate);
                                }
                                if (_self.data[i].uploadDate) {
                                    _self.data[i].realUploadDate = _self.time(_self.data[i].uploadDate);
                                    _self.data[i].uploadDate = new Date(_self.data[i].uploadDate);
                                }
                            }
                        } else {
                            _self.data = [];
                        }
                    }
                });

            } else {
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.waybill.all,
                    data: _self.page,
                    cache: false,
                    success: function(data) {
                        _self.$Loading.finish();
                        if (typeof data == "object") {
                            _self.totalRecord = data.totalRecord;
                            _self.page.current = data.currentPage;
                            _self.data = data.dataList;
                            // 循环对时间进行处理
                            for (var i = 0; i < _self.data.length; i++) {
                                if (_self.data[i].consignDate) {
                                    //console.log(_self.data[i]);
                                    _self.data[i].realConsignDate = _self.time(_self.data[i].consignDate);
                                    _self.data[i].consignDate = new Date(_self.data[i].consignDate);
                                }
                                if (_self.data[i].loadingDate) {
                                    _self.data[i].realLoadingDate = _self.time(_self.data[i].loadingDate);
                                    _self.data[i].loadingDate = new Date(_self.data[i].loadingDate);
                                }
                                if (_self.data[i].uploadDate) {
                                    _self.data[i].realUploadDate = _self.time(_self.data[i].uploadDate);
                                    _self.data[i].uploadDate = new Date(_self.data[i].uploadDate);
                                }
                            }
                        } else {
                            _self.data = [];
                        }
                    }
                });
            }
            _self.searchText = '';
        },
        postData: function(_self, url, data) {
            $.ajax({
                type: 'POST',
                url: url,
                data: data,
                cache: false,
                success: function(data) {
                    _self.loading = false;
                    _self.$Message.info('上传成功');
                    _self.modal1 = false;
                    _self.getData();

                }
            });
        },
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
    top: 0px;
    margin: 50px auto;
}
/* 加载loading */
#tloading{
    width: 100vw;
    height: 100vh;
    position: relative;
    z-index: 999999;
    background-color: #fff;
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
}
#tloading img {
	position: absolute;
	top: 300px;
	left: 50%;
	width: 100px;
	height: 100px; 
}

/*参照网上解决iview 的select下拉框选项错位的问题*/
 body .ivu-modal .ivu-select-dropdown{
    position: fixed !important;
} 
/*去除表格中的padding*/
.ivu-table-cell{
	padding: 0;
}
table td{
    padding:5px;
}

</style>