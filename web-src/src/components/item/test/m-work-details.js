export default {
	data() {
		return {
			form: {
				name: '123',
				region: '',
				date1: '',
				date2: '',
				delivery: false,
				type: [],
				resource: '',
				desc: '',
				why: ''
			},
			activeNames: ['1'],
			dialogImageUrl: '',
			dialogVisible: false,
			data: [{
				label: '一级 1',
				children: [{
					label: '二级 1-1',
					children: [{
						label: '三级 1-1-1'
					}]
				}]
			}, {
				label: '一级 2',
				children: [{
					label: '二级 2-1',
					children: [{
						label: '三级 2-1-1'
					}]
				}, {
					label: '二级 2-2',
					children: [{
						label: '三级 2-2-1'
					}]
				}]
			}, {
				label: '一级 3',
				children: [{
					label: '二级 3-1',
					children: [{
						label: '三级 3-1-1'
					}]
				}, {
					label: '二级 3-2',
					children: [{
						label: '三级 3-2-1'
					}]
				}]
			}],
			asideData: [],
			main: {},
			allData: {
				projectPhase: [{
					"label": "全部阶段",
					"children": [{
						"label": "合同",
						"children": [{
							"label": "HT 100 BJ"
						}, {
							"label": "请款"
						}]
					}, {
						"label": "合同",
						"children": [{
							"label": "HT 100 BJ"
						}, {
							"label": "请款"
						}]
					}]
				}],
				main: {
					n1: "2019年石景山区通信架空线应急抢修工程",
					n2: "HT 1000BJ",
					n3: 456,
					n4: 456,
					n5: 456,
					n6: 456,
					n7: 456,
					list: [{
							title: '一级复核',
							list: [{
								content: '意见意见意见意见意见意见意见意见意见意见意见意见意见意见意见意见意见1',
								name: '邵然',
								time: '2019-04-12',
							}, {
								content: '意见2',
								name: '欧阳赵四刘能',
								time: '2019-04-14',
							}, {
								content: '通过',
								name: '邵然',
								time: '2019-04-16',
							}]
						},
						{
							title: '二级复核',
							list: [{
								content: '意见1',
								name: '欧阳邵然',
								time: '2019-04-12',
							}, {
								content: '意见2',
								name: '邵然',
								time: '2019-04-14',
							}, {
								content: '通过',
								name: '邵然',
								time: '2019-04-16',
							}]
						},
						{
							title: '三级复核',
							list: [{
								content: '意见1',
								name: '邵然',
								time: '2019-04-12',
							}, {
								content: '意见2',
								name: '邵然',
								time: '2019-04-14',
							}, {
								content: '通过',
								name: '邵然',
								time: '2019-04-16',
							}]
						},
						{
							title: '签发',
							list: [{
								content: '意见1',
								name: '邵然',
								time: '2019-04-12',
							}, {
								content: '意见2',
								name: '邵然2',
								time: '2019-04-14',
							}, {
								content: '通过',
								name: '邵然',
								time: '2019-04-16',
							}]
						}
					],
					fileList: [{
						name: 'food.jpeg',
						url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
					}, {
						name: 'food2.jpeg',
						url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
					}]
				}
			}
		}
	}
}
