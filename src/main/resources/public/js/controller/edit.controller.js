angular
    .module('xyApp')
    .controller('EditCtrl', ['$scope', '$window', 'HttpUtils', '$routeParams', '$location', 'StringUtils', '$route', function($scope, $window, HttpUtils, $routeParams, $location, StringUtils, $route){
    	var self = this;
    	var findRegister = findRegister;
    	
    	self.back = back;
    	self.save = save;
    	self.isEditionMode = isEditionMode;
    	self.disableButton = false;
    	
    	init();
    	
    	function init() {
    		var id;
    		
    		if (isEditionMode()) {
    			id = $routeParams.id;

    			findRegister('ws/poi/' + id);
    		}
    	}
    	
    	function back() {
    		$window.history.back();
    	}
    	
    	function save() {
    		if ($scope.editForm.$valid) {
    			self.disableButton = true;
    			
    			if (self.form.hasOwnProperty('id') && self.form.id) {
    				HttpUtils
						.put('ws/poi', self.form)
						.success(function() {
							StringUtils.showMsg('Registro atualizado com sucesso.');

							self.disableButton = false;
						})
						.error(function() {
							StringUtils.showMsgConfirm('Ocorreu ao atualizar as informações, tente novamente.');
							
							self.disableButton = false;
						});
    			} else {
    	    		HttpUtils
    					.post('ws/poi', self.form)
    					.success(function() {
    						StringUtils.showMsg('Novo ponto de interesse salvo com sucesso.');
    						
    						self.form = {};
    						
    						$scope.editForm.$setPristine();
    						$scope.editForm.$setValidity();
    						
    	    				self.disableButton = false;
    	    				
    	    				$route.reload();
    					})
    					.error(function(error) {
    						StringUtils.showMsgConfirm('Ocorreu um ero ao salvar, tente novamente.');
    						
    						self.disableButton = false;
    					});
    			}
    		}
    	}
    	
    	function isEditionMode() {
    		if ($routeParams.id || (self.form && self.form.id)) {
    			return true;
    		}
    		
    		return false;
    	}
    	
    	function findRegister(url, redirect) {
    		return HttpUtils
						.get(url)
						.success(function(data) {
							self.form = data;
						})
						.error(function(error) {
							$location.path('/').search({});
						});
    	}
    }]);