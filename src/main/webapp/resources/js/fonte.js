/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function zoom(acao) {

    // aumentando a fonte
    if (acao === '+') {
        var size = $("body").css('font-size');
        size = size.replace('px', '');
        size = parseInt(size) + 1.4;
        $("body").animate({'font-size': size + 'px'});
    } else if (acao === '-') {
        var size = $("body").css('font-size');
        size = size.replace('px', '');
        size = parseInt(size) - 1.4;
        $("body").animate({'font-size': size + 'px'});
    }else{
     $("body").animate({'font-size': '10px'});   
    }

});