$(document).ready(function() {
    $('input').keyup(function () {
        // I'm assuming that 1 letter will expand the input by 10 pixels
        var oneLetterWidth = 10;

        // I'm also assuming that input will resize when at least five characters
        // are typed
        var minCharacters = 5;
        var len = $(this).val().length;
        if (len > minCharacters) {
            // increase width
            $(this).height(len * oneLetterWidth);
        } else {
            // restore minimal width;
            $(this).width(50);
        }
    });
});