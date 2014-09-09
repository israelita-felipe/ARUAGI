function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}

function showDialog(dialog) {
    PF(dialog).show();
}

function hideDialog(dialog) {
    PF(dialog).hide();
}

function fb_root(document, s, id) {
    var js, fjs = document.getElementsByTagName(s)[0];
    if (document.getElementById(id))
        return;
    js = document.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/pt_BR/sdk.js#xfbml=1&version=v2.0";
    fjs.parentNode.insertBefore(js, fjs);
}

function requestFocus(id) {
    document.getElementById(id).focus();
}

function getPosCursor(element) {
    var value = 0;
    if (typeof (element.selectionStart) !== "undefined") {
        value = element.selectionStart;
    }
    else if (document.selection) {
        var range = document.selection.createRange();
        var storedRange = range.duplicate();
        storedRange.moveToElementText(element);
        storedRange.setEndPoint("EndToEnd", range);
        value = storedRange.text.length - range.text.length;
    }
    return value;
}