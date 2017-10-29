app.directive('accessLevel', function ($rootScope, Auth) {
    return {
        restrict: 'EA',
        link: function (scope, element, attrs) {
            if (!Auth.hasRole(attrs.accessLevel))
//                element.remove();
                element.hide();
        }
    };
});