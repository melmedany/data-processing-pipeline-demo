(function(angular) {
  angular.module("demoApp.controllers").controller("DemoCtrl", function($scope, DemoService) {
    $scope.messages = [];
    $scope.message = "";
    $scope.parsed = true;
    $scope.sendBTN = document.getElementsByTagName("button")[0];
    $scope.loading = $scope.sendBTN.getElementsByTagName("span")[0];
    $scope.errorAlert = document.getElementsByClassName("alert-danger")[0];
    $scope.successAlert = document.getElementsByClassName("alert-success")[0];
    
    $scope.initErrors = function(){
		$scope.loading.className = 'fa fa-spinner fa-spin';
    	$scope.successAlert.className = $scope.successAlert.className.replace('show', 'hidden');
    	$scope.errorAlert.className = $scope.errorAlert.className.replace('show', 'hidden');
		$scope.errorAlert.innerHTML = '<strong>Error</strong> invalid Message';
    }
    
    $scope.beforeSend = function(){
    	$scope.sendBTN.disabled = true;
		$scope.loading.className = '';
		$scope.initErrors();
    }
    
    $scope.afterSend = function(){
		$scope.loading.className = '';
		$scope.loading.className = 'fa fa-send';
    	$scope.sendBTN.disabled = false;
    }
    
    $scope.send = function(){
    	$scope.beforeSend();
    	try {
    		if(typeof JSON.parse($scope.message) === 'object') {
    			$scope.parsed = true;
    	    	DemoService.send($scope.message).then(function(messages) { 
    	            $scope.afterSend();
    	            $scope.message = "";
    	        }, function(success) {
    	            $scope.afterSend();
    	        	if(success.status == '422') {
    		        	$scope.errorAlert.className = $scope.errorAlert.className.replace('hidden', 'show');
    	        	}
    	    	}, function(err) {
    	            $scope.afterSend();
    	        	$scope.errorAlert.className = $scope.errorAlert.className.replace('hidden', 'show');
    	    	});
    		} else {
                $scope.afterSend();
            	$scope.errorAlert.className = $scope.errorAlert.className.replace('hidden', 'show');
            	$scope.parsed = false;
    		}
        } catch(e) {
            $scope.afterSend();
        	$scope.errorAlert.className = $scope.errorAlert.className.replace('hidden', 'show');
        	$scope.parsed = false;
        }
    }
    
    DemoService.getAll().then(function(messages) {
    	$scope.messages = messages;
    }, function(err) {
		console.error('Error while fetching all messages');
	});
    
    DemoService.receive().then(null, null, function(message) {
    	data = JSON.parse(message);
    	if(data[0].toString().toLowerCase().includes('error')) {
    		$scope.errorAlert.innerHTML = '<strong>Error</strong> ' + data[0];
        	$scope.errorAlert.className = $scope.errorAlert.className.replace('hidden', 'show');
    	} else {
    		$scope.messages.unshift(data[0]);
        	$scope.successAlert.className = $scope.successAlert.className.replace('hidden', 'show');
    	}
    }, function(err) {
		console.error('Error receiving messages');
	});
  });
})(angular);