/**
 * client side utilities for SJTXE. Required Ext and Underscore libraries.
 * @param root
 * @param Ext
 * @param underscore
 * @returns
 * 
 * @author huguogang
 */
(function(root, Ext, underscore) {
    var root = root || window;
    var me = root;
    var Ext = Ext;
    var _ = underscore;
    
    var _hasAjaxFailure = false;
    var _hasExceptions = false;
    
    var onAjaxError = function(conn, response, options, eOpts) {        
        _hasAjaxFailure = true;
    };
    Ext.onReady(function() {
    	Ext.Ajax.on({
    		requestexception:   onAjaxError
    	});
    });

    var onError = function (msg, url, line) {
        _hasExceptions = true;
    };
    window.onerror = onError;
    
    //exported object
    var SJTXE = {
        VERSION: '0.0',
        hasAjaxFailure: function() {return _hasAjaxFailure},
        //if true, we have unfinished AJAX calls
        hasActiveAjaxCalls: function() {
            return Ext.Ajax.requests && !_.isEmpty(Ext.Ajax.requests);
        },
        //see sample code for how to examine the returned data
        getStoreData: function(storeID) {
            return _.pluck(Ext.StoreManager.get(storeID).getRange(), 'data');
        },
        hasExceptions: function() {
            return _hasExceptions;
        }
    };
    root.SJTXE = SJTXE;
    
    return SJTXE;
})(this, Ext, _);