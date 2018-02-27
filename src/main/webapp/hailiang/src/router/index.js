import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/components/pages/HomePage'
import GoodsType from '@/components/pages/GoodsType'
import SafeCard from '@/components/pages/SafeCard'
import CompInfo from '@/components/pages/CompInfo'
import PersonInfo from '@/components/pages/PersonInfo'
import CarInfo from '@/components/pages/CarInfo'
import GoodsInfo from '@/components/pages/GoodsInfo'
import EleOrder from '@/components/pages/EleOrder'
import GpsBreak from '@/components/pages/GpsBreak'
import AlarmRecord from '@/components/pages/AlarmRecord'
import TrainEdit from '@/components/pages/TrainEdit'
import TrainLog from '@/components/pages/TrainLog'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HomePage',
      component: HomePage
    },
    {
    	path: '/goodstype',
    	name: 'GoodsType',
    	component: GoodsType
    },
    {
    	path: '/safecard',
    	name: 'SafeCard',
    	component: SafeCard
    },
    {
    	path: '/compinfo',
    	name: 'CompInfo',
    	component: CompInfo
    },
    {
    	path: '/personinfo',
    	name: 'PersonInfo',
    	component: PersonInfo
    },
    {
    	path: '/carinfo',
    	name: 'CarInfo',
    	component: CarInfo
    },
    {
    	path: '/goodsinfo',
    	name: 'GoodsInfo',
    	component: GoodsInfo
    },
    {
    	path: '/eleorder',
    	name: 'EleOrder',
    	component: EleOrder
    },
    {
    	path: '/gpsbreak',
    	name: 'GpsBreak',
    	component: GpsBreak
    },
    {
    	path: '/alarmrecord',
    	name: 'AlarmRecord',
    	component: AlarmRecord
    },
    {
    	path: '/trainedit',
    	name: 'TrainEdit',
    	component: TrainEdit
    },
    {
    	path: '/trainlog',
    	name: 'TrainLog',
    	component: TrainLog
    }
  ]
})
