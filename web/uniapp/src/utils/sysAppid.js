// #ifdef MP-WEIXIN
const sysAppid = uni.getAccountInfoSync().miniProgram.appId
console.log('952', sysAppid)
export default {
	sysAppid
}
// #endif
