<template>
  <div id="app">
    <hl-login v-if='istate' @isState='getLoginInfo' :istate='istate'/>
    <template v-else>
      <hl-index @isState='getState' />
    </template>
  </div>
</template>

<script>
import HlLogin from '@/components/HlLogin'
import HlIndex from '@/components/HlIndex'
export default {
  name: 'App',
  data:function(){
    return {
      istate: true,//如果用户没有cookies，通过设置state，返回到登录页面即可，不考虑vuex，是否需要在每个router页面传入state进行判断？或者增加一个页面用于显示没有cookies的情况
      userType:null//用户类型，由login页面传入
    }
  },
  components:{
    HlLogin,
    HlIndex
  },
  methods: {
    getState:function(msg){
      this.istate=msg;
    },
    getLoginInfo:function(msg){
      this.istate=msg.istate;
      this.userType=msg.userType;
    }
  }
}
</script>

<style>

</style>
