// 应用全局配置
const config = {
	// baseUrl: '/mental/api', // 使用相对路径，通过nginx代理访问后端
	baseUrl: 'http://202.115.17.253:52531/mental/api', // 服务器公共地址，必须包含/api，因为Nginx配置要求/mental/api/开头的路径
	url: "http://202.115.17.253:52531"
	// baseUrl: 'http://192.168.13.5:8096/autoeeprovueapp',
}
export default config