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
                .on('click',this.fn.insertPost)
        },
        fn : {
            insertPost: function() {
debugger;
                const subjectObj = document.getElementById('postReg_subject'),
                      subject = subjectObj.value,
                      contentObj = document.getElementById('postReg_content');
                let content = contentObj.value;

                if(!PostReg.fn.validation()) {
                    return false;
                }

                // 파일 업로드가 완료된 후 게시물 등록 처리
                PostReg.fn.uploadFile(function(fileIds) {
                    const postData = {
                        subject: subject,
                        content: content,
                        fileIds: fileIds // 첨부된 파일이 있으면 파일 ID 리스트 포함
                    };
                });

                content = cmm.util.makeContentForm(content);
                const options = {
                    url: '/post/insertPost',
                    data: {subject: subject, content: content},
                    type: 'post',
                    success: function (res, statusText) {
                        debugger;
                        if(res == 'ok') location.href='/post/postList';
                    }
                };
                $.ajax(options);
            },
            validation: function() {
                const subjectObj = document.getElementById('postReg_subject');
                const subject = subjectObj.value;
                const contentObj = document.getElementById('postReg_content');
                const content = contentObj.value;

                if(cmm.string.isEmpty(subject)) {
                    alert('제목을 입력하세요.');
                    return false;
                }
                if(cmm.string.isEmpty(content)) {
                    alert('내용을 입력하세요.');
                    return false;
                }
                return true;
            },
            uploadFile: function(callback) {
                debugger;
                const fileInput = document.getElementById('postReg_file');
                if (fileInput.files.length > 0) {
                    const formData = new FormData();
                    for (let i = 0; i < fileInput.files.length; i++) {
                        formData.append('files', fileInput.files[i]);
                    }
                debugger;
                    fetch('/common/uploadFile',{
                        method: 'POST',
                        body: formData
                    })
                        .then(response => response.json())
                        .then(data => {
                            debugger;
                            console.log('Success', data)
                        })
                        .catch((error) => {
                            console.error('Error', error)
                        });

                   /* $.ajax({
                        url: '/post/uploadFile',
                        data: formData,
                        type: 'post',
                        processData: false,
                        contentType: false,
                        success: function (res) {
                            if (res.success) {
                                callback(res.fileIds); // 파일 업로드 성공 시 파일 ID 반환
                            }
                        }
                    });*/
                } else {
                    callback([]); // 파일이 없으면 빈 배열 반환
                }
            }
        },
        folderTree : {
        }
    };
    window.postReg = PostReg;
//-------------------------
})(jQuery, window);