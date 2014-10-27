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
function zoom_in(button, element_to_zoom) {
    document.querySelector(button).addEventListener('click', function(event) {
        event.preventDefault();
        zoom.to({element: document.getElementById(element_to_zoom)});
    });
}
function TamanhoFonte(num, id) {
    document.getElementById(id).style.fontSize = num + 'px';
}
function setFonteSize(num, id) {
    TamanhoFonte(num, id);
    var filhos = $(id).find();
    var i = 0;
    for (i; i < filhos.length; i++) {
        aumentaFonte(num, filhos[i]);
    }
}
function sticky_footer() {
    var mFoo = $("footer");
    if ((($(document.body).height() + mFoo.outerHeight()) < $(window).height() && mFoo.css("position") === "fixed") || ($(document.body).height() < $(window).height() && mFoo.css("position") !== "fixed")) {
        mFoo.css({position: "fixed", bottom: "0px"});
    } else {
        mFoo.css({position: "static"});
    }
}

jQuery(document).ready(function($) {
    sticky_footer();
    $(window).scroll(sticky_footer);
    $(window).resize(sticky_footer);
    $(window).load(sticky_footer);
});

function googleSearch(id, tipo, seachID) {
    var myCallback = function() {
        if (document.readyState === 'complete') {
            // Document is ready when CSE element is initialized.
            // Render an element with both search box and search results in div with id 'test'.
            google.search.cse.element.render(
                    {
                        div: id,
                        tag: tipo
                    });
        } else {
            // Document is not ready yet, when CSE element is initialized.
            google.setOnLoadCallback(function() {
                // Render an element with both search box and search results in div with id 'test'.
                google.search.cse.element.render(
                        {
                            div: id,
                            tag: tipo
                        });
            }, true);
        }
    };

// Insert it before the CSE code snippet so that cse.js can take the script
// parameters, like parsetags, callbacks.
    window.__gcse = {
        parsetags: 'explicit',
        callback: myCallback
    };

    (function() {
        var cx = seachID;
        var gcse = document.createElement('script');
        gcse.type = 'text/javascript';
        gcse.async = true;
        gcse.src = (document.location.protocol === 'https:' ? 'https:' : 'http:') +
                '//www.google.com/cse/cse.js?cx=' + cx;
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(gcse, s);
    })();
}