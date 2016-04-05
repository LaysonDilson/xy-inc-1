angular
    .module('xyApp')
    .service('StringUtils', ['$mdToast', function($mdToast) {
    	var self = this;
    	
    	self.stringToColor = stringToColor;
    	self.showMsgConfirm = showMsgConfirm;
    	self.showMsg = showMsg;
    	
    	function stringToColor(str) {
		    var hash = 0;
		    var color = '#';
		    
		    if (!str) {
		    	color += 'BEBEBE';

		    	return color;
		    }
		    
		    for (var i = 0; i < str.length; i++) {
		        hash = str.charCodeAt(i) + ((hash << 5) - hash);
		    }

		    for (var i = 0; i < 3; i++) {
		        var value = (hash >> (i * 8)) & 0xFF;

		        color += ('00' + value.toString(16)).substr(-2);
		    }

		    return color;
    	}
    	
    	function showMsgConfirm(msg) {
    		var toast = $mdToast
    						.simple()
    						.textContent(msg)
    						.action('OK')
    						.highlightAction(false)
    						.position('bottom right');
    		
    		$mdToast.show(toast);
    	}
    	
    	function showMsg(msg) {
    		$mdToast.show(
		      $mdToast.simple()
		        .textContent(msg)
		        .position('bottom right')
		        .hideDelay(3000)
		    );
    	}
    	
    }]);