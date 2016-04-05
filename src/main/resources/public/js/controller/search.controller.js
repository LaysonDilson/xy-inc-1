angular
    .module('xyApp')
    .controller('SearchCtrl', ['$scope', '$mdDialog', function($scope, $mdDialog){
    	var self = this;
    	
    	self.form = {};
    	self.cancel = cancel;
    	self.search = search;
    	
    	function cancel(){
    		$mdDialog.cancel();
    	}

    	function search(){
    		if ($scope.searchForm.$valid) {
    			$mdDialog.hide(self.form);
    		}
    	}
    }]);