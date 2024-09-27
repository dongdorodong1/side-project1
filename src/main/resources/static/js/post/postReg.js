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
                const subject = $('#postReg_subject').val(),
                      content = $('#postReg_content').val();
                const options = {
                    url: '/post/insertPost',
                    data: {subject: subject, content: content},
                    type: 'post',
                    success: function (res, statusText) {
                        if(res == 'ok') location.href='/post/postList';
                    }
                };
                $.ajax(options);
            }
        },
        folderTree : {
        }
    };
    window.postReg = PostReg;
//-------------------------
})(jQuery, window);