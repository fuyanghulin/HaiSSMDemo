# hailiang

> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).



###功能

		该webapp主要是用于海梁单页面开发，以取代原先不合理的框架设计，（readme文档写乱了_(:з」∠)_）

###创建时间
		2018年2月24日21:34:32

###作者
		qdhjkw@qq.com

###规划
		先将原先功能全部移植到该单页面项目中
		登录的设置，暂时考虑用v-if之类的来做
		路由的设置，
		原先使用ajax异步请求的地方，暂时全部改成使用axios（2018年3月4日16:31:06：改为jq）
		原先部分页面未实现结构样式功能的分离，在移植过程中需要注意
		对部分重复的js逻辑进行封装

###注意
		该项目由原HaiSSMDemo拷贝而来，重命名为HaiSSMDemo2（2018年3月4日16:31:25：现已替代HaiSSMDemo）
		前一个文件夹“HaiLiangpc”是之前做SPA的尝试
		由于项目进度的特殊性以及个人能力的问题，该SPA开发采用npm run build打包后放在tomcat中运行测试（滑稽摊手）

		需要做修改的地方：引入dataUrl并修改dataUrl.dataUrl、修改this.$cookies、手动添加userType

###事件
		|2018年2月24日21:41:55|先完成了服务器的配置，可以在8080访问到打包完的vue页面|
		|2018年2月25日09:58:26|在考虑使用axios的时候发现原先jq的ajax请求使用很多，暂时决定使用jq|
		|2018年2月25日11:03:47|引入vue-cookies|
		|2018年2月25日13:42:11|完成登录界面|
		|2018年2月27日10:47:30|基本完成单页面改造|
		|2018年2月27日14:24:51|将demo2放置在了原项目中，原项目中的webapp移到E盘下重要文件中保存|
		|2018年2月27日14:49:12|cnpm install font-awesome|
		|2018年2月27日14:50:14|cnpm install vue-html5-editor --save-dev|

***

###疑问
		1.为什么在tomcat访问的路径是"http://localhost:8080/HaiSSMDemo2/index.html#/"，多个"#"
		2.为什么在router-link外面写keep-alive会导致页面不跳转？
		3.路由下的组件如何通信？

***
####需要改进

		0.请求过多
		1.刷新、搜索的时候，可以做优化
		2.封装js函数
		3.点击现路由link实现路由部分的刷新
		4.eleOrder等页面渲染过慢，急需优化
		5.添加、修改等功能按钮统一放置在上部nav处
		6.每个路由组件中的userType可以修改为从父组件中传入
		7.当失去cookies的时候，页面刷新需要让它转到'/'路由，而不是失去cookies时候的路由
		8.路由重定向
		9.更改登录的设置，使得普通用户可以浏览基本信息
***


###2018年3月4日16:25:53
##这之前已经把项目搬到SPA了
***
	接下来的东西用来记录新的功能开发以及vue使用中遇到的问题