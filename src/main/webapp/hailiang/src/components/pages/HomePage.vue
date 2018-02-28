<template>
	<div class="hlhome"><!-- 这里class换了 -->
		    <nav>
		        <div>
		            <Input placeholder="请输入公司名..." style="width: 300px" v-modal='searchText' @keyup.native.enter="search"></Input>
		            <Button type="primary" shape="circle" icon="ios-search" @click="search"></Button>
		        </div>
		    </nav>
		    <Row style="background:#eee;padding:20px;height: 948px;">
		        <Card :bordered="false" v-for="obj in card" style="margin: 20px 0">
		            <p slot="title">{{obj.title}}</p>
		            <Row v-for="objA in obj.content" style="margin: 10px 0">
		                <Col span="10" v-for="objB in objA">
		                    <Row type="flex" justify="start">
		                        <Col offset="1">
		                            <Icon type="ios-analytics"></Icon>
		                            {{objB.title}}(<span style="color: #ed3f14;">{{objB.number}}</span>)
		                        </Col>
		                        <Col offset="1">
		                            <span style="color: #ed3f14;cursor: pointer">查看</span>
		                        </Col>
		                    </Row>
		                </Col>
		            </Row>
		        </Card>
		    </Row>
	</div>
</template>

<script>
	export default{
		name:'HlHome',
        data:function() {
            return {
                searchText: '',
                card: [
                    {
                        title: '企业相关证件过期提示',
                        content: [
                            [
                                {
                                    title: '营业执照',
                                    number: 0
                                },
                                {
                                    title: '经营许可证',
                                    number: 0
                                }
                            ]
                        ]
                    },
                    {
                        title: '车辆相关证件过期提示',
                        content: [
                            [
                                {
                                    title: '强制保险',
                                    number: 0
                                },
                                {
                                    title: '第三责任险',
                                    number: 0
                                }
                            ],
                            [
                                {
                                    title: '承运人责任险',
                                    number: 0
                                },
                                {
                                    title: '车辆行驶证年审',
                                    number: 0
                                }
                            ],
                            [
                                {
                                    title: '道路运输证年审',
                                    number: 0
                                },
                                {
                                    title: '罐体年检',
                                    number: 0
                                }
                            ],
                            [
                                {
                                    title: '技术等级评定',
                                    number: 0
                                },
                                {
                                    title: '二级维护',
                                    number: 0
                                }
                            ]
                        ]
                    },
                    {
                        title: '员工相关证件过期提示',
                        content: [
                            [
                                {
                                    title: '驾驶证',
                                    number: 0
                                },
                                {
                                    title: '驾驶员资格证',
                                    number: 0
                                }
                            ],
                            [
                                {
                                    title: '押运从业资格证',
                                    number: 0
                                },
                                {
                                    title: '安管员资格从业证',
                                    number: 0
                                }
                            ],
                            [
                                {
                                    title: '主要负责人从业资格证',
                                    number: 0
                                },
                                {
                                    title: '装卸管理从业资格证',
                                    number: 0
                                }
                            ]
                        ]
                    }
                ]
            }
        },
        methods:{
            search:function() {
                if (this.searchText.replace(/\s/g, '').length < 1) {
                    alert('搜索内容不可为空');
                } else {
                    alert('正在搜索！！！');
                }
            }
        }
	}
</script>

<style scoped>
.hlhome{
    height: 1000px;
}
nav {/*这个地方可以都改一下，将这个搜索框与左侧‘首页’字样对齐*/
    padding: 10px 50px;
    display: flex;
    justify-content: flex-end;
    justify-items: center;
}

nav div:first-child button {
    margin: 0 10px;
}
</style>