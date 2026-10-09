/**
* 显示消息提示框
* @param content 提示的标题
*/
export function toast(content) {
  uni.showToast({
    icon: 'none',
    title: content,
    duration: 3000
  })
}

/**
* 显示模态弹窗
* @param content 提示的标题
*/
export function showConfirm(content) {
  return new Promise((resolve, reject) => {
    uni.showModal({
      title: '提示',
      content: content,
      cancelText: '取消',
      confirmText: '确定',
      success: function (res) {
        resolve(res)
      }
    })
  })
}

/**
* 参数处理
* @param params 参数
*/
export function tansParams(params) {
  let result = ''
  for (const propName of Object.keys(params)) {
    const value = params[propName]
    var part = encodeURIComponent(propName) + "="
    if (value !== null && value !== "" && typeof (value) !== "undefined") {
      if (typeof value === 'object') {
        for (const key of Object.keys(value)) {
          if (value[key] !== null && value[key] !== "" && typeof (value[key]) !== 'undefined') {
            let params = propName + '[' + key + ']'
            var subPart = encodeURIComponent(params) + "="
            result += subPart + encodeURIComponent(value[key]) + "&"
          }
        }
      } else {
        result += part + encodeURIComponent(value) + "&"
      }
    }
  }
  return result
}
/**
* 获取完整的文件访问URL
* @param filePath 文件路径（可能是相对路径或完整路径）
* @returns 完整的可访问URL
*/
export function getFileUrl(filePath) {
  if (!filePath) {
    return ''
  }

  // 如果已经是完整的http/https URL，直接返回
  if (filePath.startsWith('http://') || filePath.startsWith('https://')) {
    return filePath
  }

  // 如果是以 /mental/files 开头的路径，需要加上服务>>
  if (filePath.startsWith('/mental/files')) {
    return 'http://202.115.17.253:52531' + filePath
  }

  // 其他情况，假设是相对路径，返回原值（本地静态资源）
  return filePath
}

/**
 * 格式化概率JSON字符串
 * @param {string} jsonStr JSON字符串
 * @returns {string} 格式化后的字符串
 */
export function formatProbabilities(jsonStr) {
  if (!jsonStr) return ''
  try {
    const obj = JSON.parse(jsonStr)
    return Object.entries(obj)
      .map(([key, value]) => `${key}: ${(value * 100).toFixed(2)}%`)
      .join(', ')
  } catch (e) {
    return jsonStr
  }
}
