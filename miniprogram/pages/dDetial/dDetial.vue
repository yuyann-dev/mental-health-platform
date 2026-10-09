<template>
	<view class="detail-container">
		<view class="article-card">
			<view class="article-header">
				<text class="article-title">守护心灵晴空</text>
				<text class="article-subtitle">抑郁症与焦虑症康复指南</text>
			</view>
			
			<view class="article-content">
				<view class="section">
					<text class="section-title">一、专业诊疗是康复基石</text>
					<view class="subsection">
						<text class="subsection-title">1. 规范医学干预</text>
						<text class="content-text">需在精神科医生指导下进行药物治疗，抗抑郁药（如 SSRIs）需足疗程服用（通常 6-12 个月），切勿擅自停药。合并焦虑症状者可辅以短期抗焦虑药物，定期复查肝肾功能及药物反应。</text>
					</view>
					<view class="subsection">
						<text class="subsection-title">2. 心理治疗介入</text>
						<text class="content-text">认知行为疗法（CBT）可帮助患者识别负性思维，正念减压训练（MBSR）能缓解焦虑情绪，团体心理治疗通过同伴支持重建社交信心，建议每周 1-2 次规律进行。</text>
					</view>
				</view>
				
				<view class="section">
					<text class="section-title">二、自我调节的「心理康复处方」</text>
					<view class="subsection">
						<text class="subsection-title">1. 建立「微小行动」清单</text>
						<text class="content-text">从简单事务（如整理床铺、散步 10 分钟）开始积累掌控感，抑郁症患者可尝试「每日三件小确幸」记录法，焦虑患者可通过「5-4-3-2-1 感官锚定法」应对急性焦虑发作。</text>
					</view>
					<view class="subsection">
						<text class="subsection-title">2. 生理心理联动调节</text>
						<text class="content-text">规律作息（固定睡眠/起床时间）、每日 30 分钟有氧运动（快走、瑜伽）可促进内啡肽分泌，配合腹式呼吸训练（每天 3 次，每次 5 分钟），从身体层面改善情绪循环。</text>
					</view>
				</view>
				
				<view class="section">
					<text class="section-title">三、家庭支持的「正向沟通法则」</text>
					<view class="subsection">
						<text class="subsection-title">1. 避免「纠错式」对话</text>
						<text class="content-text">不说「想开点就好」，改用「我注意到你今天很难过，需要我陪你坐一会儿吗？」给予无评判的陪伴。</text>
					</view>
					<view class="subsection">
						<text class="subsection-title">2. 构建安全表达空间</text>
						<text class="content-text">为患者准备「情绪日记本」，家属学习「积极倾听四步法」：复述内容→确认感受→有限建议→询问需求，避免过度保护或忽视症状。</text>
					</view>
				</view>
				
				<view class="section">
					<text class="section-title">四、社会支持系统搭建</text>
					<text class="content-text">关注医院心理科康复团体、线上互助社群（如 KnowYourself 抑郁互助小组），鼓励参与低压力社交活动（宠物陪伴、手工小组）。若出现自伤念头，立即联系 24 小时心理危机热线。</text>
				</view>
				
				<view class="conclusion">
					<text class="conclusion-text">心理康复是「螺旋式上升」过程，每一次小进步都值得被肯定。记住：及时求助不是软弱，而是走向康复的勇敢第一步。坚持专业治疗与自我关怀，心灵终将迎来多云转晴的时刻。</text>
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
