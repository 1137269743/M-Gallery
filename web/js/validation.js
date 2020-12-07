function switchValid(onOff, input, errSelector, message) {
    if (!onOff) {
        $(errSelector).text(message);
        $(input).addClass("error_input");
        $(errSelector).addClass("error_message");
    } else {
        $(errSelector).text("")
        $(input).removeClass("error_input");
        $(errSelector).removeClass("error_message");
    }
}
/**
 * 检查是否为空
 * @param input
 * @param errSelector
 * @returns {boolean}
 */
function checkEmpty(input, errSelector) {
    let val = $(input).val();
    if ($.trim(val) == "") {
        switchValid(false, input, errSelector, "请输入内容");
        return false;
    } else {
        switchValid(true, input, errSelector);
        return true;
    }
}

function checkCategory(input, errSelector) {
    let val = $(input).val();
    if (val == -1) {
        switchValid(false, input, errSelector, "请选择油画类型");
        return false;
    } else {
        switchValid(true, input, errSelector);
        return true;
    }
}

function checkPrice(input, errSelector) {
    let val = $(input).val();
    let regex = /^[1-9][0-9]*$/;
    if (!regex.test(val)) {
        switchValid(false, input, errSelector, "无效的价格");
        return false;
    } else {
        switchValid(true, input, errSelector);
        return true;
    }
}

function checkFile(input, errSelector) {
    if (!checkEmpty(input, errSelector)) {
        return false;
    }

    let val = $(input).val().toLocaleLowerCase();

    if (val.length < 4) {
        switchValid(false, input, errSelector, "请选择有效的图片");
        return false;
    }

    let suffix = val.substring(val.length - 3);
    if (suffix == "jpg" || suffix == "png" || suffix == "gif") {
        switchValid(true, input, errSelector);
        return true;
    } else {
        switchValid(false, input, errSelector, "请选择有效的图片");
        return false;
    }
}