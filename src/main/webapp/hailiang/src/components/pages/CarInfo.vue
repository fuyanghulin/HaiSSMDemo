<template>
<div class="carinfo">
    <!--頭部區塊-->
    <nav>
        <div>
            <i-button type="primary" @click="open('1','2',$event)" v-if="userType==3">添加</i-button>
            
            <Tooltip content="删除所选车辆信息" placement="bottom-start">
                <i-button type="error" style="margin-left: 20px;" @click.stop="del" v-if="userType==3">删除</i-button>
            </Tooltip><!-- 原来是moredelete函数 -->
        </div>
        <div>
            <i-input placeholder="请输入车牌号" style="width: 200px;" v-model="searchText" @keyup.native.enter="search"></i-input>
            <i-button type="primary" shape="circle" icon="ios-search" @click="search"></i-button>
            <i-Button type="primary" @click="getAll" style="margin-left:20px;">刷新</i-Button>
        </div>
    </nav>
    <!--数据区块-->
    <i-Table border stripe :columns="columns" :data="data1" @on-selection-change="chooseAll" ellipsis></i-Table>
    <!--\总页数-->
    <Page :total="totalRecord" @on-change="changePage" :current="page.current" 
          :page-size="page.pageNum" show-total show-elevator></Page>
    <Modal
            v-model="model1"
            width="65%"
            :closable="false"
            :scrollable="true"
            @on-cancel="cancel"
    >
        <i-form :model="formValidate" :rules="ruleValidate" ref="formValidate">
            <Row>
                <i-Col span="11">
                    <Form-Item label="车牌号" prop="carNum">
                        <i-Input placeholder="请输入" v-model="formValidate.carNum" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="车牌颜色" prop="carnumColor">
                        <Radio-Group v-model="formValidate.carnumColor">
                            <Radio label="黄色" v-bind:disabled="openState==='查看'">黄色</Radio>
                            <Radio label="绿色" v-bind:disabled="openState==='查看'">绿色</Radio>
                        </Radio-Group>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="挂车牌号">
                        <i-Input placeholder="请输入挂车牌号" v-model="formValidate.guacarNum" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="车辆颜色">
                        <Radio-Group v-model="formValidate.carColor">
                            <Radio label="黄色" v-bind:disabled="openState==='查看'">黄色</Radio>
                            <Radio label="绿色" v-bind:disabled="openState==='查看'">绿色</Radio>
                        </Radio-Group>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="车辆类型">
                        <i-Select placeholder="请选择" v-model="formValidate.carType" v-bind:disabled="openState==='查看'">
                            <i-Option v-for="item in carType" :value="item">{{item}}</i-Option>
                        </i-Select>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="车辆长，宽，高(MM)">
                        <Row>
                            <i-Col span="6">
                                <i-Input placeholder="长" v-model="formValidate.len" v-bind:disabled="openState==='查看'"></i-Input>
                            </i-Col>
                            <i-col span="3"></i-col>
                            <i-Col span="6">
                                <i-Input placeholder="宽" v-model="formValidate.width" v-bind:disabled="openState==='查看'"></i-Input>
                            </i-Col>
                            <i-col span="3"></i-col>
                            <i-Col span="6">
                                <i-Input placeholder="高" v-model="formValidate.high" v-bind:disabled="openState==='查看'"></i-Input>
                            </i-Col>
                        </Row>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Row>
                        <i-Col span="11">
                            <Form-Item label="核定载重(kg)" prop="allowWeight">
                                <i-input placeholder="核定载重" v-model="formValidate.allowWeight" v-bind:disabled="openState==='查看'"></i-input>
                            </Form-Item>
                        </i-Col>
                        <i-col span="2"></i-col>
                        <i-Col span="11">
                            <Form-Item label="整备质量(kg)" prop="weigh">
                                <i-input placeholder="整备质量" v-model="formValidate.weigh" v-bind:disabled="openState==='查看'"></i-input>
                            </Form-Item>
                        </i-Col>
                    </Row>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="准牵引总质量或挂车总质量(Kg)" prop="totalWei">
                        <i-input placeholder="准牵引总质量或挂车总质量" v-model="formValidate.totalWei" v-bind:disabled="openState==='查看'"></i-input>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="企业名称" prop="companyName">
                        <i-input placeholder="企业名称" v-model="formValidate.companyName" disabled></i-input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="持有类型">
                        <i-Select placeholder="请选择" v-model="formValidate.ownType" v-bind:disabled="openState==='查看'">
                            <i-Option value="私有">私有</i-Option>
                            <i-Option value="挂靠">挂靠</i-Option>
                            <i-Option value="社会">社会</i-Option>
                        </i-Select>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="道路运输证号码" prop="cerNum">
                        <i-Input placeholder="请输入道路运输证号码" v-model="formValidate.cerNum" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="经营范围" prop="scope">
                        <i-Input placeholder="请输入经营范围" v-model="formValidate.scope" v-bind:disabled="openState==='查看'"></i-Input>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="交强险有效期">
                        <Date-Picker type="date" placeholder="选择日期" style="width: 100%"
                                     v-model="formValidate.insuranceDate" :value="formValidate.insuranceDate" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="第三方责任有效期">
                        <Date-Picker type="date" placeholder="选择日期" style="width: 100%"
                                     v-model="formValidate.dutyInsurancedate" :value="formValidate.dutyInsurancedate" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
            </Row><!-- 
                        <p>{{typeof formValidate.dutyInsurancedate}}</p>
                        <p>{{ formValidate.dutyInsurancedate}}</p> -->
            <Row>
                <i-Col span="11">
                    <Form-Item label="承运人责任险有效期">
                        <Date-Picker type="date" placeholder="选择日期" style="width: 100%"
                                     v-model="formValidate.dutypeoDate" :value="formValidate.dutypeoDate" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="卫星定位系统运营商">
                        <i-input placeholder="运营商" v-model="formValidate.satellite" v-bind:disabled="openState==='查看'"></i-input>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="道路运输证年审有效期">
                        <Date-Picker type="date" placeholder="选择日期" :value="formValidate.roadDate" v-model="formValidate.roadDate"
                                     style="width: 100%" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="行驶证年审有效期">
                        <Date-Picker type="date" placeholder="选择日期" :value="formValidate.certyearDate" v-model="formValidate.certyearDate"
                                     style="width: 100%" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="证件有效期">
                        <Date-Picker type="date" placeholder="选择日期" :value="formValidate.certDate" v-model="formValidate.certDate"
                                     style="width: 100%" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="证件有效期">
                        <Date-Picker type="date" placeholder="选择日期" :value="formValidate.certagrDate" v-model="formValidate.certagrDate"
                                     style="width: 100%" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="罐体年检有效期">
                        <Date-Picker type="date" placeholder="选择日期" :value="formValidate.yearcheckDate" style="width: 100%"
                                     v-model="formValidate.yearcheckDate" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Row>
                        <i-Col span="11">
                            <Form-Item label="罐体容量m3">
                                <i-input palaceholder="罐体容量" v-model="formValidate.volume" v-bind:disabled="openState==='查看'"></i-input>
                            </Form-Item>
                        </i-Col>
                        <i-Col span="11">
                            <Form-Item label="罐体材质">
                                <i-input palaceholder="罐体材质" v-model="formValidate.quality" v-bind:disabled="openState==='查看'"></i-input>
                            </Form-Item>
                        </i-Col>
                    </Row>
                </i-Col>
            </Row>
            <Row>
                <i-Col span="11">
                    <Form-Item label="技术等级评定有效期">
                        <Date-Picker type="date" placeholder="选择日期" :value="formValidate.tecDate" style="width: 100%"
                                     v-model="formValidate.tecDate" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="二级维护有效期">
                        <Date-Picker type="date" placeholder="选择日期" :value="formValidate.secImprovedate" style="width: 100%"
                                     v-model="formValidate.secImprovedate" v-bind:disabled="openState==='查看'"></Date-Picker>
                    </Form-Item>
                </i-Col>
            </Row>
            <row>
                <i-Col span="11">
                    <Form-Item label="驾驶员" prop="driverName">
                        <select placeholder="驾驶员" style="display: inline-block; width: 100%;height: 30px;"
                                @change="theDriver" v-model="dp" v-bind:disabled="openState==='查看'">
                            <option v-for="(item,index) in driverList" :value="index">{{item.name}}</option><!-- :value="index" -->
                        </select>
                    </Form-Item>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Form-Item label="押运员" prop="safer">
                        <select placeholder="押运员" style="display: inline-block; width: 100%;height: 30px;"
                                @change="theSafer" v-model="sp" v-bind:disabled="openState==='查看'">
                            <option v-for="(item,index) in safeList" :value="index">{{item.name}}</option>
                        </select>
                        <!-- <input type="text" v-model='saferName'> -->
                    </Form-Item>
                </i-Col>
            </row>
            <br>
            <row>
                <i-Col span="11">
                    <Row>
                        <i-Col span="11">
                            <Form-Item label="驾驶证号">
                                <i-input placeholder="驾驶证号" disabled v-model="driver.driveLicence"></i-input>
                            </Form-Item>
                        </i-Col>
                        <i-col span="2"></i-col>
                        <i-Col span="11">
                            <Form-Item label="联系电话">
                                <i-input placeholder="联系电话" disabled v-model="driver.phone"></i-input>
                            </Form-Item>
                        </i-Col>
                    </Row>
                </i-Col>
                <i-col span="2"></i-col>
                <i-Col span="11">
                    <Row>
                        <i-Col span="11">
                            <Form-Item label="从业资格证号">
                                <i-input placeholder="驾驶证号" disabled v-model="safer.drivezigezheng"></i-input>
                            </Form-Item>
                        </i-Col>
                        <i-col span="2"></i-col>
                        <i-Col span="11">
                            <Form-Item label="联系电话">
                                <i-input placeholder="联系电话" disabled v-model="safer.phone"></i-input>
                            </Form-Item>
                        </i-Col>
                    </Row>
                </i-Col>
            </row>
        </i-form>
        <div slot="footer">
            <i-button type="text" style="width: 100px;" @click="cancel">取消</i-button>
            <i-button type="primary" @click="upCarMessage" :loading="loading" style="width: 100px;">确定</i-button>
        </div>
    </Modal>
    <Modal
            v-model="model2"
            width="65%"
            @on-cancel='cancel_map'
    >
    	<p slot="header">
        </p>
        <div id="allmap" style="width: 100%;height: 500px;"></div>
        <div slot="footer">
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
import BMap from 'BMap'
export default{
	name: 'CarInfo',
props:{
    indexloading: {
        type: Boolean,
        default: true
    }
},
    data: function () {
        const validatePass = function(rule, value, callback){
            if (value === '') {
                callback(new Error('不能为空'));
            } else {
                callback();
            }
        };
        return {
            userType: '',
            openState: '',
            map:null,
            marker:null,
            new_point:null,
            columns: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center',
                    width: 120,
                    fixed: 'left'
                },
                {
                    title: '车牌号',
                    key: 'carNum',
                    align: 'center',
                    width: 120,
                    fixed: 'left'
                },
                {
                    title: '挂车牌号',
                    key: 'guacarNum',
                    align: 'center',
                    width: 120,
                    ellipsis: true
                },
                {
                    title: '车牌颜色',
                    key: 'carnumColor',
                    align: 'center',
                    width: 120,
                    ellipsis: true
                },
                {
                    title: '车辆类型',
                    key: 'carType',
                    align: 'center',
                    width: 150,
                    ellipsis: true
                },
                {
                    title: '载重',
                    key: 'allowWeight',
                    align: 'center',
                    width: 120,
                    ellipsis: true
                },
                {
                    title: '车辆归属',
                    key: 'ownType',
                    align: 'center',
                    width: 120,
                    ellipsis: true
                },
                {
                    title: '经营许可证',
                    key: 'cerNum',
                    align: 'center',
                    width: 130,
                    ellipsis: true
                },
                {
                    title: '驾驶员',
                    key: 'driverName',
                    align: 'center',
                    width: 120,
                    ellipsis: true
                },
                {
                    title: '押运员',
                    key: 'safer',
                    align: 'center',
                    width: 120,
                    ellipsis: true
                },
                {
                    title: '位置',
                    key: 'action',
                    width: 140,
                    align: 'center',
                    render: function (h, params) {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                on: {
                                    click: function () {  
                                        var _self=this;
                                        _self.$Notice.destroy();
                                        $.ajax({
                                            dataType:'JSON',
                                            type:'GET',
                                            url:dataUrl.dataUrl.carInfo.map+encodeURIComponent(params.row.carNum),
                                            cache:false,
                                            success:function(data){
                                                if(data.longitude==null){
                                                    
                                                    _self.$Notice.info({
                                                        title:'无法获取该车辆的当前定位',
                                                        });    
                                                }
                                                else{
                                                	console.log(data);
                                                    _self.model2=true;
                                                    setTimeout(function(){
                                                    	_self.new_point=new BMap.Point(data.longitude,data.latitude);
		                                                _self.map.centerAndZoom(_self.new_point, 16); 
		                                                var marker = new BMap.Marker(_self.new_point);        // 创建标注
		                                                //_self.map.addOverlay(marker);//需要手动删除标注点2018年2月7日09:25:51
		                                                _self.map.addOverlay(marker);                     // 将标注添加到地图中 
		                                                _self.map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
                                                    },500)
                                                   
                                                }  
                                            }
                                        });
                                    }.bind(this)
                                }
                            }, '查看')
                        ]);
                    }.bind(this)
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
                                            this.openState='查看';
                                            console.log('输出当行的人员信息');
                                            console.log(this.data1[params.index]);
                                            this.formValidate = JSON.parse(JSON.stringify(this.data1[params.index]));
                                            this.open(this.data1[params.index].driverName,this.data1[params.index].safer);
                                        }.bind(this)
                                    }
                                }, '查看'),
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
                                            this.$Message.info('打印功能尚未完善！');
                                        }.bind(this)
                                    }
                                }, '打印')
                            ]);
                    }.bind(this)
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    width: 150,
                    fixed: 'right',
                    render: function (h, params) {
                        if (this.userType === 3) {
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
                                             this.title = '修改货运信息';
                                            this.openState='修改';
                                            this.formValidate = JSON.parse(JSON.stringify(this.data1[params.index]));
                                            this.open(this.data1[params.index].driverName,this.data1[params.index].safer);
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
                                            console.log('想要删除一条数据');
                                            this.delone=this.data1[params.index].id;
                                            console.log(this.delone);
                                            this.del_index(this.data1[params.index].id);
                                            this.onedel=true;
                                            console.log(this.onedel);
                                        }.bind(this)
                                    }
                                }, '删除')
                            ]);
                        }
                    }.bind(this)
                }],
            data1: [],//this.columns[this.columns.length-1].width===>尝试通过将这个值修改为0来隐藏不需要显示的td
            showModal: false,
            allChecked: '',
            theChecked: [],
            goodType: [],
            totalRecord: 0,
            content: {},
            page: {
                current: 1,
                pageNum: 20
            },
            op: 0,
            title: '',
            model1: false,
            model2:false,
            position: 0,
            formValidate: {
                carNum: '',
                carnumColor: '黄色',
                guacarNum: '',
                carColor: '',
                carType: '栏板货车',
                len: '',
                wide: '',
                high: '',
                allowWeight: '',
                weigh: '',
                totalWei: '',
                companyName: '',
                ownType: '',
                certNum: '',
                scope: '',
                insuranceDate: '',
                dutyInsurancedate: '',
                dutypeoDate: '',
                satellite: '',
                roadDate: '',
                certDate: '',
                certyearDate: '',
                certagrDate: '',
                yearcheckDate: '',
                volume: '',
                quality: '',
                tecDate: '',
                secImprovedate: '',
                driverName: '',
                safer: ''
            },
            ruleValidate: {
                carNum: [{required: true, message: '车牌号不能为空', trigger: 'blur'}],
                carnumColor: [{required: true, message: '车牌颜色不能为空', trigger: 'blur'}],
                allowWeight: [{required: true, validator: validatePass, trigger: 'blur' }],
                weigh: [{required: true, validator: validatePass, trigger: 'blur' }],
                totalWei: [{required: true, validator: validatePass, trigger: 'blur' }],
                companyName: [{required: true, message: '企业名称不能为空', trigger: 'blur'}],
                certNum: [{required: true, message: '道路运输号码不能为空', trigger: 'blur'}],
                scope: [{required: true, message: '经营范围不能为空', trigger: 'blur'}]
            },
            carType: ['栏板货车', '中性厢式货车', '重型箱式半挂车', '厢式货车', '罐式货车', '仓栅式货车', '重型半挂牵引车', '重型仓栅式半挂车', '重型罐式半挂车'],
            driverList: [

            ],
            safeList: [],
            driver: {
                driveLicence:'',
                phone: 0
            },
            safer: {
                drivezigezheng:'',
                phone: 0
            },
            dp: 0, // 驾驶员
            sp: 0, // 安全员
            loading: false,
            searchText: '',
            onedel:false,
            delone:'',
            modal2:false,
            delArr:[],
            textState:{}
        }
    },
    created: function () {
        //document.body.removeChild(document.getElementById('tloading'));
        var _self = this;

        if(_self.$cookies.get("ID")==1){
        	_self.formValidate.companyName = _self.$cookies.get("companyName");
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
        //_self.getAll();
    },
    mounted: function () {
        //this.$refs.head.style.display = 'block';
        // 百度地图API功能
        this.map = new BMap.Map("allmap");
        this.map.centerAndZoom(new BMap.Point(116.331398,39.897445),16);
        this.map.enableScrollWheelZoom(true);
this.indexloading();
    },
        beforeDestroy: function(){
            this.$emit('getloading',true);
        },
    methods: {
        
     	//坐标转换完之后的回调函数
       	translateCallback: function (data){
         	if(data.status === 0) {
           		var marker = new BMap.Marker(data.points[0]);
           		_self.map.addOverlay(marker);//需要手动删除标注点2018年2月7日09:25:51
           		_self.map.setCenter(data.points[0]);
         	}
       	},
indexloading: function(){
    this.$emit('getloading',false);
},
        search: function () {
            if (this.searchText.replace(/\s/g, '').length < 1) {
                alert('搜索内容不可为空');
            } else {
                var _self = this;
                if(_self.userType==1||_self.userType==2){
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.carInfo.search+encodeURI(_self.searchText),
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
                    idata.companyId=parseInt(this.$cookies.get("companyID"));//Cookies.get("state")).companyID;
                    idata.carNum=_self.searchText;
                    console.log(idata);
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.carInfo.searchCompany,
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
        //取消allmap时触发的函数
        cancel_map:function(){
            //this.map.removeOverlay();
            this. map.clearOverlays();
        },
        theDriver: function () {
            
            this.driver = this.driverList[this.dp];
            this.formValidate.driverName = this.driverList[this.dp].name;
            console.log(this.driver);
        },
        theSafer: function () {
            this.safer = this.safeList[this.sp];
            this.formValidate.safer = this.safeList[this.sp].name;
        },
        upCarMessage: function () { // 提交信息
            var _self = this;
            _self.loading = true;
            _self.$refs.formValidate.validate(function (valid) {
                if (_self.openState === '查看') {
                    _self.loading = false;
                    _self.model1 = false;
                } else {
                    if (valid) { // 验证通过

                        _self.formValidate.insuranceDate = _self.format(_self.formValidate.insuranceDate);
                        _self.formValidate.dutyInsurancedate = _self.format(_self.formValidate.dutyInsurancedate);
                        _self.formValidate.dutypeoDate = _self.format(_self.formValidate.dutypeoDate);
                        _self.formValidate.roadDate = _self.format(_self.formValidate.roadDate);
                        _self.formValidate.certDate = _self.format(_self.formValidate.certDate);
                        _self.formValidate.certyearDate = _self.format(_self.formValidate.certyearDate);
                        _self.formValidate.certagrDate = _self.format(_self.formValidate.certagrDate);
                        _self.formValidate.yearcheckDate = _self.format(_self.formValidate.yearcheckDate);
                        _self.formValidate.tecDate = _self.format(_self.formValidate.tecDate);
                        _self.formValidate.secImprovedate = _self.format(_self.formValidate.secImprovedate);
                        var theData = {};
                        for (var key in _self.formValidate) {
                            //if (_self.formValidate[key] !==null) {
                                theData[key] = _self.formValidate[key];
                                
                            //}
                        }
                        theData.companyId=_self.textState.companyID;
                        console.log(_self.safer);
                        theData.saferId=_self.safer.id;
                        theData.driverId=_self.driver.id;
                        //theData.safer=_self.safer;
                        console.log('输出提交的信息');
                        console.log(theData);
                        var url = '';
                        if (_self.openState == "添加") {
                            url = dataUrl.dataUrl.carInfo.insert;
                        } else if(_self.openState == "修改"){
                            url = dataUrl.dataUrl.carInfo.upDate;
                        }
                        $.ajax({
                            type: 'POST',
                            url: url,
                            data:theData,
                            cache: false,
                            success: function (data) {
                                console.log('请求成功');
                                _self.loading = false;
                                _self.model1 = false;
                                _self.getAll();
                                for (var key in _self.formValidate) {
                                    _self.formValidate[key] = '';
                                }
                                _self.formValidate.carnumColor = '黄色';
                                _self.formValidate.carType = '栏板货车';
                            },
                            error:function () {
                                console.log('请求失败');
                                _self.loading = false;
                            }
                        });
                    } else {
                        _self.loading = false;
                    }
                }

            });
        },
        cancel: function () {
            this.model1 = false;
            for (var key in this.formValidate) {
                this.formValidate[key] = '';
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
        chooseAll: function (selection) { // 全选
            this.theChecked = [];
            for (var i = 0; i < selection.length; i++) {
                this.theChecked.push(selection[i].id);
            }
        },
        change: function (index) {
            this.title = '修改货运信息';
            this.formValidate = JSON.parse(JSON.stringify(this.data1[index]));
            this.model1 = true;
            this.op = 1;
        },
        time: function (times) {
            var d = new Date(parseInt(times));
            return d.getFullYear() + '/' + (d.getMonth() + 1) + '/' + d.getDate();
        },
        close: function (from) {
            this.model1 = false;
            if (from == 1) {
                this.getAll();
            }
        },
        add0:function(m) {
            return m < 10 ? '0' + m : m
        },
        format:function(nS) {
            if(typeof nS=="object"&&nS!==null||typeof nS=="number"){
                console.log(nS)
                var time = new Date(nS);
                var y = time.getFullYear();
                var m = time.getMonth() + 1;
                var d = time.getDate();
                console.log(y + '-' + this.add0(m) + '-' + this.add0(d));
                return y + '-' + this.add0(m) + '-' + this.add0(d);
            }else{
                return null;
            }
            
        },
        open: function (dd,ss,event) {
            console.log('执行open函数，会拿到公司最新人员信息');
            //console.log(event.currentTarget.innerText);
            this.model1 = true;
            if (event&&event.currentTarget.innerText == "添加") {
                console.log(event.currentTarget.innerText);
                this.$refs['formValidate'].resetFields();
                this.openState ='添加';
                if (this.formValidate.id != undefined){
                    this.formValidate.id = '';
                }
            }
            console.log(this.formValidate.id);
            ////请求最新驾驶员数据
            var _self = this;
            if(_self.userType===3){
                //根据公司ID查询数据
                _self.page.id=JSON.parse(_self.$cookies.get("companyID"));//Cookies.get("state")).companyID;
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.company.getCompanyPeople,
                    data:_self.page,
                    cache: false,
                    success: function (data) {
                        if (data.dataList != undefined) {
                            _self.driverList = [];
                            _self.safeList = [];
                            for (var i = 0; i < data.dataList.length; i++) {
                                if (data.dataList[i].peopleType == 1) {
                                    _self.driverList.push(data.dataList[i]);
                                } else if (data.dataList[i].peopleType == 2) {
                                    _self.safeList.push(data.dataList[i]);
                                }
                            }
                            console.log('输出最新的驾驶员名单');
                            console.log(_self.driverList);
                            if(_self.openState =='添加'){//初始化人员

                                //console.log(_self.driverList);
                                console.log('添加');
                                // 给司机驾驶员赋初值
                                _self.driver = _self.driverList[0];
                                _self.safer = _self.safeList[0];

                                _self.formValidate.driverName = _self.driverList[0].name;//_self.op
                                _self.formValidate.safer = _self.safeList[0].name;//_self.sp

                            }else{//将传入的人员信息渲染
                                for(var i=0;i<_self.driverList.length;i++){
                                    console.log(dd);
                                    if(_self.driverList[i].name==dd){
                                        _self.dp=i;
                                        _self.driver=_self.driverList[i];
                                        _self.formValidate.driverName = _self.driverList[i].name;
                                    }
                                }
                                for(var i=0;i<_self.safeList.length;i++){
                                    console.log(ss);
                                    if(_self.safeList[i].name==ss){
                                        _self.sp=i;
                                        console.log(_self.sp);
                                        _self.safer=_self.safeList[i];
                                        _self.formValidate.safer = _self.safeList[i].name;
                                    }
                                }
                            }

                        }
                    }
                });
            }else{
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.person.all,
                    data:_self.page,
                    cache: false,
                    success: function (data) {
                        if (data.dataList != undefined) {
                            _self.driverList = [];
                            _self.safeList = [];
                            for (var i = 0; i < data.dataList.length; i++) {
                                if (data.dataList[i].peopleType == 1) {
                                    _self.driverList.push(data.dataList[i]);
                                } else if (data.dataList[i].peopleType == 2) {
                                    _self.safeList.push(data.dataList[i]);
                                }
                            }
                            // 给司机驾驶员赋初值
                            for(var i=0;i<_self.driverList.length;i++){
                                    console.log(dd);
                                    if(_self.driverList[i].name==dd){
                                        _self.dp=i;
                                        _self.driver=_self.driverList[i];
                                        _self.formValidate.driverName = _self.driverList[i].name;
                                    }
                                }
                                for(var i=0;i<_self.safeList.length;i++){
                                    console.log(ss);
                                    if(_self.safeList[i].name==ss){
                                        _self.sp=i;
                                        console.log(_self.sp);
                                        _self.safer=_self.safeList[i];
                                        _self.formValidate.safer = _self.safeList[i].name;
                                    }
                                }
                        }
                    }
                });
            }

        },
        del:function() {
                var _self = this;
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
                        url: dataUrl.dataUrl.carInfo.del+_self.delone,
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
                        url: dataUrl.dataUrl.carInfo.del+_self.delArr,
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
            cancel_del:function(){
                this.modal2=false;
                this.delone='';
                this.onedel=false;
                this.$refs['formValidate'].resetFields();
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
        changePage: function (cur) {
            // 分页跳转
            this.page.current = cur;
            this.getAll();
        },
        getAll: function () {
            var _self = this;
            console.log(this.userType);
            if (_self.userType == 3) {
                //根据公司Name查询数据
                _self.page.company_name=_self.$cookies.get("companyName");//Cookies.get("state")).companyName;
                console.log(_self.page);
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.carInfo.getCarByCompanyName,
                    data:_self.page,
                    cache: false,
                    success: function (data) {
                        if (data != null) {
                        	console.log(data);
                            console.log('输出得到的公司信息');
                            console.log(data);
                            console.log(data.dataList);
                            _self.data1 = data.dataList;
                            _self.totalRecord = data.totalRecord;
                            _self.current = data.currentPage;
                            _self.theChecked = [];
                        } else {
                            _self.data1 = [];
                            console.log('1064ajax请求出错');
                        }
                    }
                });
            } else {
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.carInfo.all,
                    data:_self.page,
                    cache: false,
                    success: function (data) {
                        if (data != null) {
                            _self.data1 = data.dataList;
                            _self.totalRecord = data.totalRecord;
                            _self.current = data.currentPage;
                            _self.theChecked = [];
                        } else {
                            _self.data1 = [];
                        }
                    }
                });
            }
            _self.searchText='';
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

    .ivu-table td{
        white-space: nowrap;
    }
    /*去除表格中的padding*/
/*     .ivu-table-cell{
    padding: 0;
} */
/*去掉option定位问题*/
  	body .ivu-modal .ivu-select-dropdown{
		position: fixed !important;
     }
</style>