/**
 * Created by Only on 2017/6/13.
 */

/**
 *
 * @param mobile
 * @returns {*}
 */
function replaceMobile(mobile) {
    var text;
    if (mobile) {
        text = mobile.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
    } else {
        text = mobile;
    }
    return text;
}

function filterId(id) {
    var text;
    if (id) {
        text = id.replace("@", "+");
    } else {
        text = id;
    }
    return text;
}

function isUserHeadGray() {

}