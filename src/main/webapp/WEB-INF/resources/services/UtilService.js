angular.module('myChoresApp').factory('utilService', [
    function () {
        'use strict';

        function getDayOfWeek(num) {
            switch (num) {
                case 0:
                    return 'Sunday';
                case 1:
                    return 'Monday';
                case 2:
                    return 'Tuesday';
                case 3:
                    return 'Wednesday';
                case 4:
                    return 'Thursday';
                case 5:
                    return 'Friday';
                case 6:
                    return 'Saturday';
            }
        }

        return {
            toDateObj: function (date) {
                if (date) {
                    return new Date(date.year, date.monthValue - 1, date.dayOfMonth, date.hour, date.minute, date.second, date.nano / 1000000);
                } else {
                    return null;
                }
            },
            toRelativeDate: function (date) {
                if(!date) {
                    return "-";
                }
                var startOfToday = new Date();
                startOfToday.setHours(0);
                startOfToday.setMinutes(0);
                var millisFromNow = date - startOfToday;
                var millisInDay = 1000 * 60 * 60 * 24;
                if (millisFromNow > 60 * millisInDay) {
                    return date.getMonth() + "/" + date.getDate() + "/" + date.getFullYear();
                } else if (millisFromNow > 30 * millisInDay) {
                    return "1-2 months away";
                } else if (millisFromNow > 14 * millisInDay) {
                    return "A few weeks away";
                } else if (millisFromNow > 7 * millisInDay) {
                    return "Next " + getDayOfWeek(date.getDay());
                } else if (millisFromNow > 2 * millisInDay) {
                    return "This " + getDayOfWeek(date.getDay());
                } else if (millisFromNow > 1 * millisInDay) {
                    return "Tomorrow";                    
                } else if (millisFromNow > 0) {
                    return "Today";
                } else if (millisFromNow > -1 *  millisInDay) {
                    return "Yesterday";
                } else if (millisFromNow > -1 * 7 * millisInDay) {
                    return "Last " + getDayOfWeek(date.getDay());
                } else if (millisFromNow > -1 * 14 * millisInDay) {
                    return "A few weeks ago";
                } else {
                    return date.getMonth() + "/" + date.getDate() + "/" + date.getFullYear();                    
                }
            }
        };
    }]);