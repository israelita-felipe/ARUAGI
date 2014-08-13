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