<template>
<div class="trainedit">
    <nav>
        <div>
            <i-Button type="primary" @click="open($event)" v-if="userType==1">添加</i-Button>
            <i-Button type="primary" @click="del" v-if="userType==1">删除</i-Button>
        </div>
        <div>
            <i-Input placeholder="请输入培训主题..." style="width: 300px" v-model="searchText"></i-Input>
            <i-Button type="primary" shape="circle" icon="ios-search" @click="search"></i-Button>
        </div>
    </nav>
    <i-Table :columns="columns" :data="data" @on-selection-change="chooseAll"></i-Table>
    <Page :total="totalRecord"  show-total
          show-elevator :current="page.current" @on-change="next"
          :page-size="page.pageNum"></Page>
    <Modal
            v-model="modal1"
            width="60%"
            @on-ok="ok"
            :loading="loading"
            :closable="false"
            @on-cancel="cancel">

        <Row>
            <i-Col span="24">
                <i-Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                    <Form-Item label="培训主题" prop="name">
                        <i-Input v-model="formValidate.name" placeholder="请输入主题"></i-Input>
                    </Form-Item>
                    <Form-Item label="培训内容">
                        <vue-html5-editor :content="formValidate.theme" :height="300"
                                          :show-module-name="showModuleName"
                                          @change="updateData" ref="editor"
                        ></vue-html5-editor>

                    </Form-Item>
                    <Form-Item label="培训时长" prop="time" class="time_zindex">
                        <Time-Picker format="HH:mm:ss" type="time" placeholder="选择时间"
                                     v-model="formValidate.time"></Time-Picker>
                    </Form-Item>
                </i-Form>
            </i-Col>
        </Row>

    </Modal>
</div>
</template>

<script>
import dataUrl from '../../assets/config.js'
export default{
	name: 'TrainEdit',
        data: function () {
            return {
            	searchText:'',
                userType: '',
                showModuleName: false,
                modal1: false,
                totalRecord: 0,
                page: {
                    current: 1,
                    pageNum: 20
                },
                loadingStatus: false,
                loading: true,
                file: null,
                delArr: [],
                formValidate: {
                    name: '',
                    peixunTime: '',
                    theme: "",
                    createtime: '',
                    time: ''
                },
                ruleValidate: {
                    name: [
                        {required: true, message: '标题不能为空', trigger: 'blur'}
                    ],
                    time: [
                        {required: true, type: 'date', message: '请选择时间', trigger: 'change'}
                    ]
                },
                columns: [
                    {
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },
                    {
                        type: 'selection',
                        align: 'center',
                    },
                    {
                        title: '培训主题',
                        key: 'name',
                    },
                    {
                        title: '培训时间(s)',
                        key: 'peixunTime',
                        sortable: true
                    },
                    {
                        title: '创建时间',
                        key: 'createtime',
                        sortable: true
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
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
                                                this.show(params.index);
                                            }.bind(this)
                                        }
                                    }, '查看'),
                                    h('Button', {
                                        props: {
                                            type: 'error',
                                            size: 'small'
                                        },
                                        style: {
                                            marginRight: '5px'
                                        },
                                        on: {
                                            click: function () {
                                                this.delArr.push(this.data[params.index].id);
                                                this.del();
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
                            }

                        }.bind(this)
                    }
                ],
                data: []
            }

        },
        methods: {
        	search: function () {
                if (this.searchText.replace(/\s/g, '').length < 1) {
                    alert('搜索内容不可为空');
                } else {
                    var _self = this;
                    $.ajax({
                        type: 'GET',
                        url: dataUrl.dataUrl.trainEdit.search+encodeURI(_self.searchText.replace(/\s/g, '')),
                        cache: false,
                        success: function (data) {
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
            },
            updateData: function (data) {
                // sync content to component
                this.formValidate.theme = data;
            },
            fullScreen: function () {
                this.$refs.editor.enableFullScreen();
            },
            focus: function () {
                this.$refs.editor.focus();
            },
            show: function (index) {
                this.$Modal.info({
                    title: '培训内容',
                    content: '<br>' + this.data[index].theme
                })
            },
            del: function () {
                var _self = this;
                $.ajax({
                    type: 'POST',
                    url: dataUrl.dataUrl.trainEdit.del + _self.delArr,
                    cache: false,
                    success: function (data) {
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
            getData: function () {
                var _self = this;
                _self.$Loading.start();
                $.ajax({
                    type: 'GET',
                    url: dataUrl.dataUrl.trainEdit.all,
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
            },
            ok: function () {
                var _self = this;
                if (_self.openState == "添加") {
                    _self.formValidate.createtime = _self.format();
                    _self.formValidate.peixunTime = _self.formValidate.time.getHours() * 3600
                        + _self.formValidate.time.getMinutes() * 60
                        + _self.formValidate.time.getSeconds();
                    _self.postData(_self, dataUrl.dataUrl.trainEdit.insert, _self.formValidate);
                }
            },
            add0: function (m) {
                return m < 10 ? '0' + m : m
            },
            format: function () {
                var time = new Date();
                var y = time.getFullYear();
                var m = time.getMonth() + 1;
                var d = time.getDate();
                return y + '-' + this.add0(m) + '-' + this.add0(d);
            },
            postData: function (_self, url, data) {
                $.ajax({
                    type: 'POST',
                    url:url,
                    data:data,
                    dataType:'JSON',
                    cache: false,
                    success: function (data) {
                        _self.modal1 = false;
                        _self.formValidate.theme = '';
                        _self.getData();
                        _self.$refs['formValidate'].resetFields();
                        _self.$Message.info('培训课程上传成功');
                    },
                    error:function () {
                        _self.$Loading.finish();
                    }
                });
                /*$.post(url, data, function () {

                }, 'json');*/
            },
            cancel: function () {
                this.formValidate.theme = '';
                //this.reset();
                this.getData();
            },
            open: function (obj) {
                var _self = this;
                if ($.trim(obj.currentTarget.innerText) == "添加") {
                    _self.$refs['formValidate'].resetFields();
                    _self.openState = $.trim(obj.currentTarget.innerText);
                }
                this.modal1 = true;
            }
        },
        created: function () {
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
        mounted: function () {
            //this.$refs.head.style.display = 'block';
        }
}
</script>

<style scoped>
/*@import 'https://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css'*/
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

        .ivu-select-dropdown {
            z-index: 9999;
        }

/*事件被下面遮挡*/
.time_zindex{
	z-index: 100;
}
</style>