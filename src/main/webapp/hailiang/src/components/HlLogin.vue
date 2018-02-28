<template>
<div id="hllogin">
    <div class="top_blank"></div>
    <div class="login_box">
        <Row type="flex" justify="center" align="middle" class="inner_box">
            <Col>
                <Card :bordered="false" class="inner_card">
                    <p slot="title">海梁</p>
                    <Form ref="formInline" :model="formInline" :rules="ruleInline">
                        <Form-Item prop="name">
                            <Input type="text"  v-model="formInline.name" placeholder="用户名" size="large">
                            </Input>
                        </Form-Item>
                        <Form-Item prop="pass">
                            <Input type="password" v-model="formInline.pass" placeholder="密码" size="large">
                            </Input>
                        </Form-Item>
                        <Form-Item>
                            <row :gutter="16">
                                <Col span="14">
                                    <Input v-model="inputCode" placeholder="验证码" @on-enter="login" size="large">
                                    </Input>
                                </Col>
                                <Col span="10">
                                    <div @click="newCode" class="login_code">
                                        {{code}}
                                    </div>
                                </Col>
                            </row>
                        </Form-Item>
                        <Form-Item>
                            <Button type="info" class="login_btn" size="large" @click='login'>登录</Button>
                        </Form-Item>
                    </Form>
                </Card>
            </Col>
        </Row>
    </div>
</div>

</template>

<script>
import dataUrl from '@/assets/config.js'
	export default{
		name:'HlLogin',
		props:['istate'],
		data:function(){
			 return {
                logininfo:{},
                model1: '',
                code: '',
                inputCode: '',
                formInline: {
                    name: '',
                    pass: ''
                },
                isLogin:false,
                ruleInline: {
                    name: [{
                        required: true,
                        message: '请填写用户名',
                        trigger: 'blur'
                    }],
                    pass: [{
                        required: true,
                        message: '请填写密码',
                        trigger: 'blur'
                    },
                        {
                            type: 'string',
                            min: 6,
                            message: '密码长度不能小于6位',
                            trigger: 'blur'
                        }
                    ]
                }
            }
		},
		methods:{
			setState:function(info){//需要做的是在这里做一些判断以及_self.$cookies的处理
				this.$emit('isState',info);
			},
            newCode:function() {
                var arr = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
                this.code = '';
                for (var i = 0; i < 4; i++)
                    this.code += '' + arr[Math.floor(Math.random() * arr.length)];
            },
            getCompanyName:function (state,companyId) {
                var _self=this;
                $.get(dataUrl.dataUrl.company.getName+companyId,function (obj,status) {
                    if(status==='success'){
                        state.companyName=obj.name;
                        _self.$cookies.set("ID",state.ID,60*60*24*30);
                        _self.$cookies.set("companyID",state.companyID,60*60*24*30);
                        _self.$cookies.set("companyName",state.companyName,60*60*24*30);
                        _self.$cookies.set("roleID",state.roleID,60*60*24*30);
                        if(_self.$cookies.get("companyID")!==undefined){
                            _self.$Message.success('登录成功！');
                            //_self.setUserType(state.roleID);
                            _self.logininfo.istate=false;
                            _self.logininfo.userType=state.roleID;//这个roleID表示客户类型，我是这样判断的
                            _self.setState(_self.logininfo);//kk
                        }else{
                            _self.$Message.success('登录失败！');
                        }
                    }else{
                        _self.$Message.success('登录失败！');
                    }
                });
            },
            login:function() {//用户点击登录，用户名以及密码和验证码的判断交给iview
                var _self=this;
                var state={};
                
                if (_self.code == _self.inputCode) {
                    _self.newCode();
                    $.get(dataUrl.dataUrl.login.login,_self.formInline,function(data,status){//这个需post
                        if(status==='success'){
                            if(data[3]){//密码错误，后台会返回类似{3:"密码不正确"}的json
                                _self.$Message.error('账号密码错误！');
                                return;
                            }else{
                                state.ID=1;
                                state.roleID=data[2].roleId;
                                state.companyID=data[2].companyId;//回头得考虑是不是要data全部属性赋值给state
                            }

                            if(state.companyID!==undefined&&state.companyID!==null){
                                _self.getCompanyName(state,data[2].companyId);//判断是企业登录
                            }else {

                                _self.$cookies.set("ID",state.ID,60*60*24*30);//cookies保存一个月
                                _self.$cookies.set("companyID",state.companyID,60*60*24*30);
                                _self.$cookies.set("roleID",state.roleID,60*60*24*30);
                                
                                if(_self.$cookies.get("roleID")!==undefined&&_self.$cookies.get("roleID")!==null){//这里的这些判断都该改进
                                    _self.$Message.success('登录成功！');
                                    _self.logininfo.istate=false;
                                    _self.logininfo.userType=state.roleID;//这个roleID表示客户类型，我是这样判断的
                                    _self.setState(_self.logininfo);//kk
                                }
                            }
                        }else{
                            self.$Message.success('登录失败！');                            
                        }                          
                    });
                }else{
                    this.$Message.error('验证码错误！');
                }
            }
		},
        created:function() {
            var _self=this;
            _self.newCode();//进入页面刷新验证码，该验证码可能不是很安全，需要改进
            if(_self.$cookies.get("ID")!==null&&_self.$cookies.get("ID")!==undefined){//判断是否有cookies中的ID，作为判断,可能有问题
                var ID = _self.$cookies.get("ID");
                if (ID == 1) {
                    _self.logininfo.istate=false;
                    _self.logininfo.userType=_self.$cookies.get("roleID");//这个roleID表示客户类型，我是这样判断的
                    _self.setState(_self.logininfo);//kk//通过setState函数改变istate，从而进入index页面，这个逻辑不确定是否完全可行，可能需要改进
                }
            }
        }
	}
</script>

<style scoped>
	.top_blank{
        background: #73777a;
        height: 38vh;
    }
    .login_box{
        background: #eee;
        height: 62vh;
    }
    .inner_box{
        position: absolute;
        top: -60px;
        left: 0;
        right: 0;
        bottom: 0;
        margin: auto;
    }
    .inner_card{
        width: 400px;
    }
    .login_code{
        border: 1px solid #EEEEEE;
        text-align: center;
        font-size: 26px;
        cursor: pointer;
    }
    .login_btn{
        width: 100%;
    }
</style>