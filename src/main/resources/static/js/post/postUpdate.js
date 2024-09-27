(function($, window) {
//----------------------------
    /** PostUpdate 객체 */
//----------------------------
    const PostUpdate = {
        name: 'PostUpdate',
        init: function() {
            this.bind();
        },
        bind: function() {
            $('#postUpdate_write')
                .on('click',this.fn.updateArticle)
        },
        fn : {
            updateArticle: function() {
                const subject = $('#postUpdate_subject').val(),
                      content = $('#postUpdate_content').val();
                const id = $('#postUpdate_write').data('id');
                const options = {
                    url: '/post/updateAtcl',
                    data: {subject: subject, content: content,id:id},
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
    window.postUpdate = PostUpdate;
//-------------------------
})(jQuery, window);