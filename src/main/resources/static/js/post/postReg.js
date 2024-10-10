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
                content = cmm.util.makeContentForm(content);
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
        },
        folderTree : {
        }
    };
    window.postReg = PostReg;
//-------------------------
})(jQuery, window);