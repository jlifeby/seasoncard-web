'use strict';

app.filter('regex', function () {
    return function (items, field, regex, modifiers) {

        var inverse = false;
        if (modifiers.indexOf('!') != -1) {
            inverse = true;
            modifiers = modifiers.replace(/!/, '');
        }

        var out = [];

        if (items) {
            var pattern = new RegExp(regex, modifiers);
            for (var i = 0; i < items.length; i++) {
                var test = pattern.test(items[i][field]);
                if (inverse ? !test : test) {
                    out.push(items[i]);
                }
            }
        }

        return out;
    };
});