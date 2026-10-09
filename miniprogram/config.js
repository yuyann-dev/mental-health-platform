// 应用全局配置
// 部署到云服务器时，请将下方地址修改为你的服务器实际地址
const config = {
	// baseUrl: '/mental/api', // 使用相对路径，通过nginx代理访问后端
	baseUrl: 'http://localhost:9090/mental/api', // 本地开发地址，部署时修改为服务器地址
	url: "http://localhost:9090" // 服务器基础地址，部署时修改
	// baseUrl: 'http://192.168.13.5:8096/autoeeprovueapp',
}
export default config
