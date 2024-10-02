(function($, window) {
//----------------------------
    /** PostReg 객체 */
//----------------------------
    const PostReg = {
        name: 'PostReg',
        init: function() {
            this.bind();
        },
        bind: function() {
            $('#postReg_write')
                .on('click',this.fn.insertArticle)
        },
        fn : {
            insertArticle: function() {
                const subjectObj = document.getElementById('postReg_subject'),
                      subject = subjectObj.value,
                      contentObj = document.getElementById('postReg_content');
                let content = contentObj.value;

                if(subject.trim() === '') {
                    alert('제목을 입력하세요.');
                    return;
                }
                if(content.trim() === '') {
                    alert('내용을 입력하세요.');
                    return;
                }
                content = PostReg.fn.sanitizeInput(content);
                const options = {
                    url: '/post/insertPost',
                    data: {subject: subject, content: content},
                    type: 'post',
                    success: function (res, statusText) {
                        if(res == 'ok') location.href='/post/postList';
                    }
                };
                $.ajax(options);
            },
            sanitizeInput: function (content) {
                // 1. script 태그를 포함한 모든 HTML 태그 제거
                const noHTMLTags = content.replace(/<[^>]*>?/gm, '');

                // 2. 엔터는 <br>로 변환
                const withLineBreaks = noHTMLTags.replace(/\n/g, '<br>');

                // 3. 연속된 공백도 그대로 유지
                const withPreservedSpaces = withLineBreaks.replace(/ /g, '&nbsp;');

                return withPreservedSpaces;
            }
        },
        folderTree : {
        }
    };
    window.postReg = PostReg;
//-------------------------
})(jQuery, window);