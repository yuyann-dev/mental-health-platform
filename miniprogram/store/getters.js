const getters = {
  // 使用时直接：模板中：$store.getters.hasLogin，js中this.$store.getters.hasLogin
  hasLogin: state => state.user.hasLogin,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  userId: state => state.user.userId,
  roles: state => state.user.roles,
  baseInfo: baseInfo => state.baseInfo,
  permissions: state => state.user.permissions,
}
export default getters
