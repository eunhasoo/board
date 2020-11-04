$('#username').blur(function() {
    const userId = $('#username').val();
    if (userId.length > 0) {
        if (userId.length < 6 || userId.length > 20) {
            $('#usernameError').text('6자 이상 20자 이내로 작성해주세요.');
        } else {
            $.ajax({
                url: '/member/check/username/' + userId,
                type: 'get',
                success: function(result) {
                    if (result == 1) {
                        $('#usernameError').siblings().remove();
                        $('#usernameError').text('이미 사용중인 아이디입니다.');
                    } else {
                        $('#usernameError').siblings().empty();
                        $('#usernameError').empty();
                    }
                }
            });
        }
    }
});

$('#email').blur(function() {
    const email = $('#email').val();
    if (email.length > 0) {
        $.ajax({
            url: '/member/check/email/' + email,
            type: 'get',
            success: function (result) {
                if (result == 1) {
                    $('#emailError').siblings().remove();
                    $('#emailError').text('이미 사용중인 이메일 주소입니다.');
                } else {
                    $('#emailError').siblings().empty();
                    $('#emailError').empty();
                }
            }
        });
    }
});

$('#name').blur(function() {
    const name = $('#name').val();
    if (name.length > 0) {
        if (name.length < 2 || name.length > 12) {
            $('#nameError').text('2자 이상 12자 이내로 입력해주세요.');
        } else {
            $.ajax({
                url: '/member/check/name/' + name,
                type: 'get',
                success: function (result) {
                    if (result == 1) {
                        $('#nameError').siblings().remove();
                        $('#nameError').text('이미 사용중인 닉네임입니다.');
                    } else {
                        $('#nameError').siblings().empty();
                        $('#nameError').empty();
                    }
                }
            });
        }
    }
});
