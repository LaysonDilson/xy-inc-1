angular
    .module('xyApp')
    .controller('ListCtrl', ['StringUtils', 'HttpUtils', '$location', '$mdDialog', function(StringUtils, HttpUtils, $location, $mdDialog) {
    	var self = this;
    	var showLoading = showLoading;
    	var hideLoading = hideLoading;
    	var loadAll = loadAll
    	var applySearch = applySearch;
    	var deleteFromVirtualList = deleteFromVirtualList;
    	
    	self.getIconColor = getIconColor;
    	self.editRow = editRow;
    	self.deleteRow = deleteRow;
    	self.pois = [];
    	self.isLoading = true;
    	
    	init();
    	
    	function init() {
    		var param = {};

    		if ($location.search().x && $location.search().y && $location.search().dMax) {
    			param.x = $location.search().x;
    			param.y = $location.search().y;
    			param.dMax = $location.search().dMax;
    			
    			applySearch(param);
    		} else {
    			loadAll();
    		}
    	}
    	
    	function loadAll() {
    		showLoading();
    		
    		HttpUtils
    			.get('ws/poi')
    			.success(function(data) {
    				if (data) {
    					self.pois = data;
    				}
    				
    				hideLoading();
    			})
    			.error(function(error) {
    				hideLoading();
    			});
    	}
    	
    	function applySearch(param) {
    		showLoading();

    		var data = {};
    		data.params = param;
    		
    		HttpUtils
				.get('ws/poi/search', data)
				.success(function(data) {
					if (data) {
						self.pois = data;
					}
					
					hideLoading();
				})
				.error(function(error) {
					hideLoading();
				});
    	}
    	
    	function showLoading() {
    		self.isLoading = true;
    	}
    	
    	function hideLoading() {
    		self.isLoading = false;
    	}
    	
    	function getIconColor(txt) {
    		var color = StringUtils.stringToColor(txt);
    		
    		return {'background-color': color}; 
    	}
    	
    	function editRow(id) {
    		$location.path('/poi/' + id);
    	}
    	
    	function deleteRow(event, id) {
    		 var confirm = $mdDialog
    		 				.confirm()
    		 				.title('Deseja realmente apagar o ponto de interesse?')
    		 				.textContent('')
    		 				.targetEvent(event)
    		 				.ok('Sim')
    		 				.cancel('Não');
    		 
    		 $mdDialog.show(confirm).then(function() {
    			 HttpUtils
	 				.callDelete('ws/poi/' + id)
	 				.success(function(data) {
	 					deleteFromVirtualList(id);
	 				})
	 				.error(function(error) {
	
	 				});
    		 });
    	}
    	
    	function deleteFromVirtualList(id) {
    		var index = self.pois.indexOf(
    				self.pois.filter(function(t) {
						return t.id === id;
					})[0]);

			if (index !== -1) {
				self.pois.splice(index, 1);
			}
    	}
    }]);