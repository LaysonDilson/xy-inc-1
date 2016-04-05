angular
    .module('xyApp')
    .config(['$mdThemingProvider', '$routeProvider', '$httpProvider', '$locationProvider', function($mdThemingProvider, $routeProvider, $httpProvider, $locationProvider) {
    	$mdThemingProvider
    		.theme('default')
    		.primaryPalette('brown')
    		.accentPalette('red');
    	
    	$routeProvider
	    	.when('/', {
	    		templateUrl: 'view/list.html',
	    		controller: 'ListCtrl',
	    		controllerAs: 'ctrl'
	    	})
        	.when('/poi/:id?', {
        		templateUrl: 'view/edit.html',
        		controller: 'EditCtrl',
        		controllerAs: 'ctrl'
        	})
        	.otherwise({
        		redirectTo:'/'
        	});
    	
    	$httpProvider.defaults.headers['Content-Type'] = 'application/json; charset=UTF-8';
    	//$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
    }]);