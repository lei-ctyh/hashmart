<template>
	<div class="sceditor">
		<Editor v-model="contentValue" :init="init" :disabled="disabled" :placeholder="placeholder" @onClick="onClick" />
	</div>

	<el-dialog title="选择资源" v-model="attachmentDialogVisible" :width="1200" destroy-on-close>
		<Attachment :select-num="999999" @selectedFiles="attachmentSelected"></Attachment>
	</el-dialog>
</template>

<script>

	import Attachment from '@/components/attachment'

	import API from "@/api";
	import Editor from '@tinymce/tinymce-vue'
	import tinymce from 'tinymce/tinymce'
	import 'tinymce/themes/silver'
	import 'tinymce/icons/default'
	import 'tinymce/models/dom'

	// 引入编辑器插件
	import 'tinymce/plugins/code'  //编辑源码
	import 'tinymce/plugins/link'  //超链接
	import 'tinymce/plugins/preview'//预览
	import 'tinymce/plugins/template'//模板
	import 'tinymce/plugins/table'  //表格
	import 'tinymce/plugins/pagebreak'  //分页
	import 'tinymce/plugins/lists'  //列
	import 'tinymce/plugins/advlist'  //列
	import 'tinymce/plugins/quickbars'  //快速工具条

	export default {
		components: {
			Editor,
			Attachment
		},
		props: {
			modelValue: {
				type: String,
				default: ""
			},
			placeholder: {
				type: String,
				default: ""
			},
			height: {
				type: Number,
				default: 300,
			},
			disabled: {
				type: Boolean,
				default: false
			},
			plugins: {
				type: [String, Array],
				default: 'code link table quickbars pagebreak lists advlist'
			},
			toolbar: {
				type: [String, Array],
				default: 'undo redo |  forecolor backcolor bold italic underline strikethrough link blocks fontfamily fontsize \
					alignleft aligncenter alignright alignjustify outdent indent numlist bullist \
					pagebreak table code selectall attachment'
			},
			templates: {
				type: Array,
				default: () => []
			}
		},
		data() {
			return {
				attachmentDialogVisible: false,
				init: {
					language_url: 'tinymce/langs/zh_CN.js',
					language: 'zh_CN',
					skin_url: 'tinymce/skins/ui/oxide',
          content_css: "tinymce/skins/content/default/content.css",
          convert_urls: false, // 解决本地图片翻译路径问题
					menubar: false,
					statusbar: true,
					plugins: this.plugins,
					toolbar: this.toolbar,
					toolbar_mode: 'sliding',
					font_size_formats: '12px 14px 16px 18px 22px 24px 36px 72px',
					height: this.height,
					placeholder: this.placeholder,
					branding: false,
					resize: true,
					elementpath: true,
					content_style: "",
					templates: this.templates,
					quickbars_selection_toolbar: 'forecolor backcolor bold italic underline strikethrough link',
					quickbars_image_toolbar: 'alignleft aligncenter alignright',
					quickbars_insert_toolbar: false,
					image_caption: true,
					image_advtab: true,
					images_upload_handler: function(blobInfo) {
						return new Promise((resolve, reject) => {
							const data = new FormData();
							data.append("file", blobInfo.blob() ,blobInfo.filename());
							API.common.upload.post(data).then((res) => {
								resolve(res.data.src)
							}).catch(() => {
								reject("Image upload failed")
							})
						})
					},
					setup: (editor) => {

						editor.on('init', function() {
							this.getBody().style.fontSize = '14px';
						})

						editor.on('OpenWindow', function(e) {
							//FIX 编辑器在el-drawer中，编辑器的弹框无法获得焦点
							var D = document.querySelector('.el-drawer.open')
							var E = e.target.editorContainer
							if(D && D.contains(E)){
								var nowDA = document.activeElement
								setTimeout(()=>{
									document.activeElement.blur()
									nowDA.focus()
								},0)
							}
						})

						editor.ui.registry.addButton('attachment', {
							text: '',
							icon: 'image',
							onAction: () => {
								this.attachmentDialogVisible = true
							}

						});
					}
				},
				contentValue: this.modelValue
			}
		},
		watch: {
			modelValue(val) {
				this.contentValue = val
			},
			contentValue(val){
				this.$emit('update:modelValue', val);
			}
		},
		mounted() {
			tinymce.init({})
		},
		methods: {
			onClick(e){
				this.$emit('onClick', e, tinymce)
			},
			// 选择图片
			attachmentSelected(e) {
				if (e.length) {
            let html = '';
            for (let i in e) {
                html += '<img src="' + e[i].url + '" style="max-width: 100%;">';
            }
            tinymce.activeEditor.insertContent(html);
        }

        this.attachmentDialogVisible = false;
			}
		}
	}
</script>

<style>
</style>
