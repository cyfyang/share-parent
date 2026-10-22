import request from '@/utils/request'

//新增充电宝
export function addPowerBank(data){
    return request({
        url: '/device/powerBank',
        method: 'post',
        data: data
    })
}
//修改充电宝
export function updatePowerBank(data){
    return request({
        url: '/device/powerBank',
        method: 'put',
        data: data
    })
}
//根据id查询详情
export function getpowerBank(id){
    return request({
        url: '/device/powerBank/' + id,
        method: 'get'
    })
}
//删除充电宝
export function deletePowerBank(id){
    return request({
        url: '/device/powerBank/' + id,
        method: 'delete'
    })

}
//查询充电宝列表
export function listPowerBank(query){
    return request({
        url: '/device/powerBank/list',
        method: 'get',
        params: query
    })
}