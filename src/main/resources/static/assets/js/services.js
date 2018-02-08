(function(angular, SockJS, Stomp, _, undefined) {
  angular.module("demoApp.services").service("DemoService", function($q, $timeout, $http) {
    
    var service = {}, listener = $q.defer(), socket = {
      client: null,
      stomp: null
    };
    
    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/demo-socket";
    service.TOPIC = "/topic/messages";
    
	service.getAll = function() {	
		return $http.get('/endpoint').then(
			function(response){
				return response.data;
			}, 
			function(err){
				return $q.reject(err);
			}
		)
	};
	
    service.send = function(message) {
     	return $http.post('/endpoint', message, {headers : {'Content-Type': 'application/json'}}).then(
			function(response){
			}, 
			function(err){
				return $q.reject(err);
			}
		)
    };
    
    service.receive = function() {
      return listener.promise;
    };
    
    var reconnect = function() {
      $timeout(function() {
        initialize();
      }, this.RECONNECT_TIMEOUT);
    };
    
    var startListener = function() {
      socket.stomp.subscribe(service.TOPIC, function(data) {
        listener.notify(data.body);
      });
    };
    
    var initialize = function() {
      socket.client = new SockJS(service.SOCKET_URL);
      socket.stomp = Stomp.over(socket.client);
      socket.stomp.connect({}, startListener);
      socket.stomp.onclose = reconnect;
    };
    
    initialize();
    return service;
  });
})(angular, SockJS, Stomp);