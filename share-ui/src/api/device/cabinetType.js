import request from '@/utils/request'

//查询柜机类型详情
export function getCabinetType(id){
    return request({
        url: '/device/cabinetType/' + id,
        method: 'get'
    })
}

//修改柜机类型
export function updateCabinetType(data){
    return request({
        url: '/device/cabinetType',
        method: 'put',
        data: data
    })
}


// 查询柜机类型列表  如果是requestBody传参 params就得改为data
export function listCabinetType(query){
    return request({
        url: '/device/cabinetType/list',
        method: 'get',
        params: query
    })
}

export function addCabinetType(data){
    return request({
        url: '/device/cabinetType',
        method: 'post',
        data: data
    })

}