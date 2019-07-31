var calculatorApp = angular.module('calculator', []);

calculatorApp.controller('mainController', function ($scope) {
}).controller('calculationController', function ($scope, $http, $rootScope) {
    $rootScope.getCalculation = function () {
        $http.get('/calculator?action=findAllCalculation').then(function (response) {
            $scope.calculations = response.data;
        }, function (error) {
            alert("Error search calculations")
        });
    };
    $rootScope.getCalculation();
    $scope.getExpressionByCalculation = function (calculation) {
        $http.get('/calculator?action=findExpressions&calculationId=' + calculation.id)
            .then(function (response) {
                $rootScope.$emit('dataForExpressions', {expressions: response.data, calculation: calculation});
                $rootScope.$emit('showFup', false);
            }, function (error) {
                alert("Error search expressions")
            })
    }
}).controller('expressionsController', function ($scope, $rootScope, $http) {
    $rootScope.$on('dataForExpressions', function (event, data) {
        $scope.calcExpressions = data.expressions;
        $scope.calculation = data.calculation;
        console.log($scope.calcExpressions)
    });

    $scope.cancelAction = function () {
        $scope.calcExpressions = null;
        $rootScope.$emit('showFup', true);
    };

    $scope.deleteCalculation = function (calculation) {
        $http.get('/calculator?action=deleteCalculation&calculationId=' + calculation.id)
            .then(function (value) {
                $scope.calcExpressions = null;
                $rootScope.getCalculation();
                alert("Calculation deleted successfully!")
            }, function (error) {
                alert("Error deleting expressions")
            })
    }

    $scope.saveCalculation = function (expressions) {
        $http.post("/calculator?action=save", expressions)
            .then(function (response) {
                    $rootScope.$emit('dataForExpressions', {expressions: null})
                    $rootScope.getCalculation();
                    alert("Calculation saved successfully!")
                },
                function (error) {
                    alert("Error saving calculation!")
                })
    }
}).controller('fupController', function ($scope, $rootScope, $http) {
    $rootScope.isShow = true;
    $rootScope.$on('showFup', function (event, data) {
        $rootScope.isShow = data;
    });
    $scope.getFileDetails = function (e) {

        $scope.files = [];
        $scope.$apply(function () {
            for (var i = 0; i < e.files.length; i++) {
                $scope.files.push(e.files[i])
            }

        });
    };
    $scope.uploadFiles = function () {
        var data = new FormData();
        for (var i in $scope.files) {
            data.append("uploadedFile", $scope.files[i]);
        }
        $http.post("/calculator?action=uploadFile", data, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(
            function (response) {
                $rootScope.$emit('dataForExpressions', {expressions: response.data},
                    function (error) {
                        alert("Error upload file! Perhaps the file contains values in the wrong format!")
                    })
            })
    }
});