<template>
  <div id="hlindex">
    <!-- 当页面里的istate为false时，将显示hlindex -->
    <!-- 该页面还加载了hlnav组件，是路由入口 -->
    <!-- 路由出口也在改页面显示 -->
    <!-- 所以大概这里是一个中心枢纽 -->

    <div id="hlindex" class="layout">
      <div class="layout-content">

        <div class="header"><!-- 原来的hl-header -->
          <Menu mode="horizontal" theme="dark" active-name="1" >
            <div class="layout-logo"><img width="100%" src='../assets/logo_left.png' alt="海梁"></div>
            <div class="layout-ceiling-main">
                <a href="javascript:;" @click="exit" style="color: #2d8cf0;">退出</a>
            </div>
         </Menu>
        </div>

        <Row>
          <Col span="4">
            <hl-nav/>
          </Col>
          <Col span="20">
            <div class="layout-content-main" >
              <div id="hlmain" frameborder="0" width="100%"><!-- 这里删除了一个align="right"的属性 -->
                <transition :name='transitionName'>
                  <keep-alive><router-view :indexloading="indexloading" @getloading="chgloading" class="child-view" /></keep-alive><!-- </keep-alive> -->
                </transition>
                <div id="tloading" v-if="indexloading">
                  <img src="../assets/Spin-1.6s-200px.gif" alt="loading">
                </div>
              </div>
              <div class="layout-copy">
                  2016-2017 &copy; Zhejiang HaiLiang Technology Co., Ltd.
              </div>
          </div>           
          </Col>
        </Row>       
      </div>
     
    </div>

  </div>
</template>

<script>
import HlNav from '@/components/HlNav'

export default {
  name: 'HlIndex',
  data:function(){
    return {
      indexloading:true
    }
  },
  components:{
    HlNav
  },
  methods:{
    exit:function(){
      this.$cookies.remove("ID");
      this.$cookies.remove("companyID");
      this.$cookies.remove("companyName");
      this.$cookies.remove("roleID");
      this.$emit('isState',true);
    },
    chgloading:function(msg){
      this.indexloading=msg;
    }
  },  
  watch: {  
    '$route' (to, from) {  
      if(to.path == '/'){  
        this.transitionName = 'slide-right';  
      }else{  
        this.transitionName = 'slide-left';  
      }  
    }  
  }  
}
</script>

<style>
.layout {
  /* height: 100vh; */
  background: #f5f7f9;
}
.layout-content {
  overflow: hidden;
  background: #fff;
}
#hlmain{
  border-top: 1px solid #ddd;
  height: 820px;
}

.layout-copy {
    text-align: center;
    padding: 10px 0 20px;
    color: #9ea7b4;
    line-height: 3;
}

/*原来的hl-header样式*/
.header{
  background-color: #f02f23;
}
.layout-logo {
    width: 270px;
    height: 45px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 8px;
    left: 20px;
    /* background-image: url('../assets/logo_left.png'); */
}
.layout-ceiling-main {
    float: right;
    margin-right: 15px;
}

.layout-ceiling-main a {
    color: #9ba7b5;
}
.router-link-exact-active{
  color: #2D8CED;
  font-weight: bolder;
}

        /* 加载loading */
        #tloading{
            width: 100vw;
            height: 100vh;
            position: relative;
            z-index: 999;
        }
        #tloading img {
            position: absolute;
            top: 300px;
            left: 38%;
            width: 100px;
            height: 100px; 
        }

/*路由动画*/
  
.child-view {  
  position: absolute;  
  left: 0;  
  top: 0;  
  width: 100%;  
  height: 100%;  
  transition: all .5s cubic-bezier(.55,0,.1,1);  
}  
.slide-left-enter, .slide-right-leave-active {  
  opacity: 0;  
  /*-webkit-transform: translate(30px, 0);*/
  transform: translate(30px, 0);  
}  
.slide-left-leave-active, .slide-right-enter {  
  opacity: 0;  
  /*-webkit-transform: translate(-30px, 0);*/
  transform: translate(-30px, 0);  
} 

</style>
