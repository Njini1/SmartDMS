import {
	ClassicEditor,
	Autosave,
	Essentials,
	Paragraph,
	Link,
	ImageToolbar,
	BlockQuote,
	Bold,
	Mention,
	Heading,
	Indent,
	IndentBlock,
	ImageInline,
	Italic,
	Underline,
	Table,
	TableToolbar,
	PlainTableOutput,
	TableCaption,
	Strikethrough,
	Alignment,
	List,
	TodoList,
	Autoformat,
	TextTransformation
} from 'ckeditor5';

const LICENSE_KEY = 'GPL'; // or <YOUR_LICENSE_KEY>.

const editorConfig = {
	toolbar: {
		items: [
			'undo', 'redo',
			'|',
			'heading',
			'|',
			'bold', 'italic', 'underline', 'strikethrough',
			'|',
			'link', 'insertTable', 'blockQuote',
			'|',
			'alignment',
			'|',
			'bulletedList', 'numberedList', 'todoList',
			'outdent', 'indent'
		],
		shouldNotGroupWhenFull: false
	},
	plugins: [
		Alignment, Autoformat, Autosave, BlockQuote, Bold, Essentials, Heading,
		ImageInline, ImageToolbar, Indent, IndentBlock, Italic, Link, List,
		Mention, Paragraph, PlainTableOutput, Strikethrough, Table, TableCaption,
		TableToolbar, TextTransformation, TodoList, Underline
	],
	heading: {
		options: [
			{ model: 'paragraph', title: '글자크기', class: 'ck-heading_paragraph' },
			{ model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
			{ model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
			{ model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
			{ model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
			{ model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' }
		]
	},
	image: { toolbar: [] },
	licenseKey: LICENSE_KEY,
	link: {
		addTargetToExternalLinks: true,
		defaultProtocol: 'https://',
		decorators: {
			toggleDownloadable: {
				mode: 'manual',
				label: 'Downloadable',
				attributes: { download: 'file' }
			}
		}
	},
	mention: { feeds: [ { marker: '@', feed: [] } ] },
	placeholder: 'Type or paste your content here!',
	table: { contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells'] }
};

const initialHtml = window.__initialHtml || '';

if (initialHtml) {
	editorConfig.initialData = initialHtml;
} else {
	// 문서 생성 기본 템플릿 원하면 여기 넣기 (없으면 빈 문서)
	editorConfig.initialData = '';
}

ClassicEditor
	.create(document.querySelector('#editor'), editorConfig)
	.then(editor => {
		// 전역으로 노출 (폼 submit 때 사용)
		window.editorInstance = editor;

		// 폼 submit 시 hidden input에 HTML 저장
		const form = document.querySelector('form#docForm');
		if (form) {
			form.addEventListener('submit', () => {
				const hidden = document.querySelector('#contentHtml');
				if (hidden) hidden.value = editor.getData();
			});
		}
	})
	.catch(console.error);
