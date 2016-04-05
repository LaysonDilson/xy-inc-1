angular
    .module('xyApp')
    .controller('POICtrl', ['$scope', '$location', '$mdSidenav', '$mdMedia', '$mdDialog', function($scope, $location, $mdSidenav, $mdMedia, $mdDialog) {
    	var self = this;

    	self.toggleMenu = toggleMenu;
    	self.showAll = showAll;
    	self.showAdd = showAdd;
    	self.showSearch = showSearch;
    	
    	$scope.popupFullscreen = $mdMedia('xs') || $mdMedia('sm');
    	
    	function toggleMenu() {
    		$mdSidenav('menu').toggle();
    	}
    	
    	function showAll() {
    		$location.path('/').search({});
    	}
    	
    	function showAdd() {
    		$location.path('/poi');
    	}
    	
    	function showSearch(event) {
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

			$mdDialog
				.show({
					controller: 'SearchCtrl',
					controllerAs: 'ctrl',
					templateUrl: 'view/search-popup.html',
					parent: angular.element(document.body),
					targetEvent: event,
					clickOutsideToClose: true,
					fullscreen: useFullScreen
				})
				.then(function(searchParams) {
					$location.path('/').search(searchParams);
				});

			$scope.$watch(function() {
				return $mdMedia('xs') || $mdMedia('sm');
			}, function(fullScreen) {
				$scope.popupFullscreen = (fullScreen === true);
			});
    	}
    }]);