'use strict';

angular.module('wasabi.controllers')
    .controller('ChangeRapidExperimentCtrl',
        ['$scope', '$modalInstance', 'experiment',
            function ($scope, $modalInstance, experiment) {

                $scope.experiment = experiment;
                $scope.formChangeRapidExperimentSubmitted = false;

                $scope.ok = function (isFormInvalid, form) {
                    if (form.maxRapidUsers.$viewValue.length == 0) {
                        form.maxRapidUsers.$setValidity('min', false);
                        $scope.formChangeRapidExperimentSubmitted = true;
                        return false;
                    }
                    if (!isFormInvalid) {
                        // Submit as normal
                        $modalInstance.close($scope.experiment);
                    } else {
                        $scope.formChangeRapidExperimentSubmitted = true;
                    }
                };

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }]);