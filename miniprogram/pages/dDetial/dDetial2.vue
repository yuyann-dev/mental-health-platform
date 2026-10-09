<template>
	<view class="detail-container">
		<view class="article-card">
			<view class="article-header">
				<text class="article-title">激活心能</text>
				<text class="article-subtitle">心理康复训练指南</text>
			</view>
			
			<view class="article-content">
				<view class="section">
					<text class="section-title">一、认知重塑训练（打破思维僵局）</text>
					<view class="subsection">
						<text class="subsection-title">1. 思维捕捉练习</text>
						<text class="content-text">准备「思维监控表」，当出现负面想法（如「我什么都做不好」）时，立即记录「触发场景→具体想法→身体反应」，每天 3 次。随后用「证据检验法」自问：「有什么事实支持/反驳这个想法？」（例：反驳「上周按时完成了家务」）</text>
					</view>
					<view class="subsection">
						<text class="subsection-title">2. 未来情景预演</text>
						<text class="content-text">每天花 5 分钟想象「理想中的自己」：焦虑患者可预演「在会议上发言」的成功细节（如「声音平稳、观众点头」），抑郁症患者聚焦「完成散步后轻松的心情」，通过具象化正性场景重塑自我认知。</text>
					</view>
				</view>
				
				<view class="section">
					<text class="section-title">二、情绪着陆训练（阻断焦虑循环）</text>
					<view class="subsection">
						<text class="subsection-title">1. 5-3-7 呼吸法</text>
						<text class="content-text">焦虑发作时启动：
❶ 5 秒深吸气（用腹部而非胸部）
❷ 屏息 3 秒（感受空气充盈）
❸ 7 秒缓慢呼气（想象压力随气流排出）
重复 3 轮，快速降低心率（实测可使心率下降 10-15 次/分钟）。</text>
					</view>
					<view class="subsection">
						<text class="subsection-title">2. 感官锚定练习</text>
						<text class="content-text">每天进行「3 分钟感官扫描」：说出「看到的 3 种颜色」「听到的 2 种声音」「触摸到的 1 种质感」，通过聚焦当下细节，将注意力从反刍思维拉回现实。</text>
					</view>
				</view>
				
				<view class="section">
					<text class="section-title">三、行为激活训练（对抗意志减退）</text>
					<view class="subsection">
						<text class="subsection-title">1. 「0.5 级行动」法则</text>
						<text class="content-text">将目标拆解至「不可能失败」的微小步骤：
・抑郁患者：从「坐在床边 5 分钟」开始启动晨间活动
・焦虑患者：将「社交」转化为「向店员说谢谢」的简单互动
每完成一项就在「成就清单」贴星星，通过视觉化进步积累掌控感。</text>
					</view>
					<view class="subsection">
						<text class="subsection-title">2. 节律重建计划</text>
						<text class="content-text">制定「90 分钟活动周期表」：
6:30 起床（固定时间）→ 7:00 早餐（搭配喜欢的音乐）→ 8:30 阳光照射（窗前站立 10 分钟）→ 10:00 轻度运动（八段锦/拉伸）
通过规律的身体节律调节，修复紊乱的生物钟（研究显示 6 周规律作息可提升 5-HT 受体敏感性）。</text>
					</view>
				</view>
				
				<view class="section">
					<text class="section-title">四、身心整合训练（双向调节机制）</text>
					<view class="subsection">
						<text class="subsection-title">1. 身体叙事疗法</text>
						<text class="content-text">每天进行「情绪具象化」：焦虑患者用红色画笔在纸上随意涂抹，边画边说「这是胸口的紧绷感」；抑郁患者用蓝色颜料晕染，表达「低落像沉在水里」。通过肢体动作外化情绪，打破「思维反刍→情绪恶化」的闭环。</text>
					</view>
					<view class="subsection">
						<text class="subsection-title">2. 渐进式肌肉放松</text>
						<text class="content-text">睡前进行「从脚趾到额头」的 16 组肌肉群收缩-放松训练，每个部位紧绷 5 秒后突然释放，配合「沉重/温暖」的自我暗示（如「我的手臂越来越沉，烦恼正在掉落」），快速降低皮质醇水平。</text>
					</view>
				</view>
				
				<view class="conclusion">
					<text class="conclusion-text">康复训练的关键在于「小步持续」—— 不必追求完美执行，哪怕每天完成 1 项基础训练，累积的神经可塑性变化（如前额叶皮层活跃度提升）都会推动状态改善。建议从 2-3 项训练开始，逐步建立属于自己的「心理康复工具箱」。每一次主动尝试，都是心灵复苏的涟漪。</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style>
.detail-container {
	padding: 30rpx;
	background-color: #f8f9fa;
	min-height: 100vh;
}

.article-card {
	background-color: #ffffff;
	border-radius: 20rpx;
	padding: 40rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.05);
}

.article-header {
	margin-bottom: 40rpx;
	text-align: center;
	border-bottom: 2rpx solid #f0f0f0;
	padding-bottom: 30rpx;
}

.article-title {
	font-size: 40rpx;
	font-weight: bold;
	color: #333;
	margin-bottom: 16rpx;
	display: block;
}

.article-subtitle {
	font-size: 28rpx;
	color: #666;
	display: block;
}

.section {
	margin-bottom: 40rpx;
}

.section-title {
	font-size: 34rpx;
	font-weight: bold;
	color: #2b81e2;
	margin-bottom: 24rpx;
	display: block;
}

.subsection {
	margin-bottom: 24rpx;
	background-color: #f8f9fa;
	padding: 24rpx;
	border-radius: 12rpx;
}

.subsection-title {
	font-size: 30rpx;
	font-weight: 500;
	color: #444;
	margin-bottom: 16rpx;
	display: block;
}

.content-text {
	font-size: 28rpx;
	color: #666;
	line-height: 1.6;
	display: block;
	white-space: pre-wrap;
}

.conclusion {
	margin-top: 60rpx;
	padding: 30rpx;
	background: linear-gradient(135deg, #e8f0fe, #ffffff);
	border-radius: 16rpx;
	border-left: 6rpx solid #2b81e2;
}

.conclusion-text {
	font-size: 28rpx;
	color: #444;
	line-height: 1.6;
	font-style: italic;
}
</style> 