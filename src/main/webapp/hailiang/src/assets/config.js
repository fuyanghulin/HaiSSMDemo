/**
 * Created by Admin on 2017/10/9.
 */
var baseUrl = 'http://localhost:8080';
var dataUrl = {
    login:{
        login:baseUrl+'/HaiSSMDemo2/companyUserLogin.action',
        logout:baseUrl+'/HaiSSMDemo2/logout.action',
        ensuresession:baseUrl+'/HaiSSMDemo2/checkSession.action'
    },
    company:{
        checkName:baseUrl+'/HaiSSMDemo2/checkAccount.action',
        add:baseUrl+'/HaiSSMDemo2/insertCompanyUserAndPass.action',
        getID:baseUrl+'/HaiSSMDemo2/selectCompanyByName.action?current=1&pageNum=1000&name=',
        getName:baseUrl+'/HaiSSMDemo2/selectCompanyByID.action?id=',
        getPeople:baseUrl+'/HaiSSMDemo2/selectPeopleById.action',
        getCompanyPeople:baseUrl+'/HaiSSMDemo2/selectPeopleByCompanyId.action'
    },
    alarm: {
        /*del: baseUrl + '/HaiSSMDemo2/delPeopleBatch.action?arrays=',*/
        all: baseUrl + '/HaiSSMDemo2/selectAllAlarm.action',
        getAlarmByCompanyID:baseUrl+'/HaiSSMDemo2/selectAlarmByCompanyId.action',
    },
    goodsType: {
        del: baseUrl + '/HaiSSMDemo2/delGoodsTypeBatch.action?arrays=',
        search: baseUrl + '/HaiSSMDemo2/selectGoodsTypeByName.action?current=1&pageNum=1000&name=',
        all: baseUrl + '/HaiSSMDemo2/allGoodsType.action',
        insert: baseUrl + '/HaiSSMDemo2/insertGoodsType.action',
        upDate: baseUrl + '/HaiSSMDemo2/updateGoodsType.action',
    },
    safeCard: {
        pdf: baseUrl + '/SafeCard/',
        search: baseUrl + '/HaiSSMDemo2/selectBySafeCardName.action?current=1&pageNum=1000&safeCardName=',
        del: baseUrl + '/HaiSSMDemo2/delSafeCardBatch.action?arrays=',
        uploadFile: baseUrl + '/HaiSSMDemo2/upfiles.action',
        insert: baseUrl + '/HaiSSMDemo2/insertSafeCard.action',
        upDate: baseUrl + '/HaiSSMDemo2/updateSafeCard.action',
        all: baseUrl + '/HaiSSMDemo2/allSafeCards.action',
    },
    carInfo: {
        getCarByCompanyName:baseUrl+'/HaiSSMDemo2/selectCarInfoByCompanyName.action',
        selectCarInfoByCompanyId:baseUrl+'/HaiSSMDemo2/selectCarInfoByCompanyId.action',
        allPeople: baseUrl + '/HaiSSMDemo2/allPeople.action?current=1&pageNum=1000',
        search: baseUrl + '/HaiSSMDemo2/selectCarByCarLicense.action?current=1&pageNum=1000&carLicense=',
        searchCompany:baseUrl+'/HaiSSMDemo2/selectCarInfoByCompanyIdAndCarNum.action',
        insert: baseUrl + '/HaiSSMDemo2/insertCarInfo.action',
        upDate: baseUrl + '/HaiSSMDemo2/updateCarInfo.action',
        del: baseUrl + '/HaiSSMDemo2/delCarInfoBatch.action?arrays=',
        all: baseUrl + '/HaiSSMDemo2/allCarInfo.action',
        map:baseUrl+'/HaiSSMDemo2/selectGpsByPlateNO.action?plateNo=',
    },
    carrier: {
        search: baseUrl + '/HaiSSMDemo2/selectCompanyByName.action?current=1&pageNum=1000&name=',
        insert: baseUrl + '/HaiSSMDemo2/insertCompany.action',
        upDate: baseUrl + '/HaiSSMDemo2/updateCompany.action',
        del: baseUrl + '/HaiSSMDemo2/delCompanyBatch.action?arrays=',
        all: baseUrl + '/HaiSSMDemo2/allCompany.action',
    },
    person: {
        search: baseUrl + '/HaiSSMDemo2/selectPeopleByName.action?current=1&pageNum=1000&name=',
        searchCompany:baseUrl + '/HaiSSMDemo2/selectPeopleByCompanyIdAndName.action',
        del: baseUrl + '/HaiSSMDemo2/delPeopleBatch.action?arrays=',
        upPicture: baseUrl + '/HaiSSMDemo2/upPicture.action',
        all: baseUrl + '/HaiSSMDemo2/allPeople.action?time=',
        insert: baseUrl + '/HaiSSMDemo2/insertPeople.action',
        upDate: baseUrl + '/HaiSSMDemo2/updatePeople.action',
        companyList: baseUrl + '/HaiSSMDemo2/getCompanyList.action',
        upImg:baseUrl+'/HaiSSMDemo2/upPicture.action',
    },
    goods: {
        goodsType: baseUrl + '/HaiSSMDemo2/getGoodsTypeList.action',
        safeCard: baseUrl + '/HaiSSMDemo2/getSafeCardsList.action',
        insert: baseUrl + '/HaiSSMDemo2/insertGoodsInfo.action',
        upDate: baseUrl + '/HaiSSMDemo2/updateGoodsInfo.action',
        search: baseUrl + '/HaiSSMDemo2/selectGoodsInfoByName.action?current=1&pageNum=1000&name=',
        searchCompany:baseUrl + '/HaiSSMDemo2/selectGoodsInfoByCompanyIdAndName.action',
        del: baseUrl + '/HaiSSMDemo2/delGoodsInfoBatch.action?arrays=',
        all: baseUrl + '/HaiSSMDemo2/allGoodsInfo.action',
        company:baseUrl + '/HaiSSMDemo2/selectGoodsInfoByCompanyId.action'
    },
    waybill: {
        getWaybillByCompanyID:baseUrl+'/HaiSSMDemo2/selWaybillByCompanyId.action',
        selectCheck: baseUrl + '/HaiSSMDemo2/selectHlCheckWaybillBywaybillId.action?waybillId=',
        selectCheckText: baseUrl + '/HaiSSMDemo2/selCheckTextArray.action?array=',
        selectDriverLog: baseUrl + '/HaiSSMDemo2/selDriverLogTextArray.action?array=',
        goodInfo: baseUrl + '/HaiSSMDemo2/selectGoodsInfoList.action',
        carInfo: baseUrl + '/HaiSSMDemo2/getCarInfoList.action',
        search: baseUrl + '/HaiSSMDemo2/selWaybillByShipper.action?current=1&pageNum=1000&Shipper=',
        searchCompany:baseUrl + '/HaiSSMDemo2/selWaybillByCompanyIdAndShipper.action',
        insert: baseUrl + '/HaiSSMDemo2/insertWaybill.action',
        upDate: baseUrl + '/HaiSSMDemo2/updateWaybill.action',
        people: baseUrl + '/HaiSSMDemo2/selectPeopleByName.action?current=1&pageNum=1&name=',
        del: baseUrl + '/HaiSSMDemo2/delWaybillBatch.action?arrays=',
        all: baseUrl + '/HaiSSMDemo2/allWaybill.action',
        dbselect: baseUrl+'/HaiSSMDemo2/selWaybillDetail.action'
    },
    trainEdit: {
        del: baseUrl + '/HaiSSMDemo2/delPeixu.action?array=',
        all: baseUrl + '/HaiSSMDemo2/selectPeixu.action',
        insert: baseUrl + '/HaiSSMDemo2/insertPeixu.action',
        search:baseUrl+'/HaiSSMDemo2/selectPeixuByName.action?current=1&pageNum=1&name=',
    },
    trainLog: {
        all: baseUrl + '/HaiSSMDemo2/selectPeixustate.action',
        getTrainLogByCompanyID:baseUrl+'/HaiSSMDemo2/selectPeixunstateByCompanyId.action',
    }
}

export default{dataUrl}