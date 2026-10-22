import request from '@/utils/request'

export function getAllFeeRuleList(){
    return request({
        url: '/rule/feeRule/getAllFeeRuleList',
        method: 'get'
    })
}