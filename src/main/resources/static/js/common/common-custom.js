(function() {
    window.cmm = function() {

    }
    cmm.string = {
        /**
         * 주어진 문자열이 null 이나 "" 인지 확인
         * @param 비교할 문자열
         * @return
         * 		false : null 이나 "", , 그밖에 true
         */
        isNotEmpty : function(str) {
            return (str == null || str == "" || str == "undefined") ? false: true;
        },

        /**
         * 주어진 문자열이 null 이나 "" 인지 확인
         * @param 비교할 문자열
         * @return
         * 		true : null 이나 "", , 그밖에 false
         */
        isEmpty : function(str) {
            return (str == null || str == "" || str == "undefined") ? true: false;
        },
    }

    cmm.util = {
        /**
         * XSS 방지 및 내용으로 치환하는 메서드
         * @param value
         * @returns {*}
         */
        makeContentForm : function(value) {
            // 1. script 태그를 포함한 모든 HTML 태그 제거
            const noHTMLTags = value
                .replace(/</g, '&lt;')
                .replace(/>/g, '&gt;');

            // 2. 엔터는 <br>로 변환
            const withLineBreaks = noHTMLTags.replace(/\n/g, '<br>');

            // 3. 연속된 공백도 그대로 유지
            const withPreservedSpaces = withLineBreaks.replace(/ /g, '&nbsp;');

            return withPreservedSpaces;
        },
        replaceContentToView : function (str) {
        // <br>을 줄바꿈(\n)으로 치환
        let result = str.replaceAll("<br>", "\n");
        // &nbsp;을 공백으로 치환
        result = result.replaceAll("&nbsp;", " ");
        return result;
    }
    }
})();