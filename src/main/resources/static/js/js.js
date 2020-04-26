$(document).ready(function () {
    $('.item').on('click', 'li', function(){
        $('.item li').removeClass('active');
        $(this).addClass('active');
    });
});