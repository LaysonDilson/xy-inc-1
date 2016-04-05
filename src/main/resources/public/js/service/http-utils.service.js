angular
    .module('xyApp')
    .service('HttpUtils', ['$http', function($http) {
    	var self = this;

    	self.get = get;
    	self.post = post;
    	self.put = put;
    	self.callDelete = callDelete;
    	
    	function get(url, httpConfig) {
    		if (!url) {
    			return;
    		}

    		if (!httpConfig) {
    			httpConfig = {};
    		}
    		
    		return $http.get(url, httpConfig);
    	}
    	
    	function post(url, requestContent, httpConfig) {
    		if (!httpConfig) {
    			httpConfig = {};
    		}
    		
    		return $http.post(url, requestContent, httpConfig);
    	}
    	
    	function put(url, requestContent, httpConfig) {
    		if (!httpConfig) {
    			httpConfig = {};
    		}
    		
    		return $http.put(url, requestContent, httpConfig);
    	}
    	
    	function callDelete(url, httpConfig) {
    		if (!url) {
    			return;
    		}

    		if (!httpConfig) {
    			httpConfig = {};
    		}
    		
    		return $http.delete(url, httpConfig);
    	}
    }]);