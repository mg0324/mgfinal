MG = {
    version: "1.0",
    rootPath: ""
};
//判断空
function isEmpty(v){
	if(v == undefined){
		return true;
	}
	if(v.length <= 0){
		return true;
	}
	return false;
}
//格式化时间
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
String.prototype.startWith = function(A) {
    var B = new RegExp("^" + A);
    return B.test(this)
};
String.prototype.endWith = function(A) {
    var B = new RegExp(A + "$");
    return B.test(this)
};
MG.command = function(B, A) {
    this.action = B;
    if (A == null) {
        this.method = "POST"
    } else {
        this.method = A
    }
    this.type = "index";
    this.params = {};
    this.returns = null
};
//异步
MG.command.prototype = {
    setParameter: function(B, A) {
        this.params[B] = A
    },
    execute: function(C, G, CALLBACK) {
        this.type = C;
        if (G == null) {
            G = true
        }
        if (this.action == null) {
            var B = {};
            B.s = "fail";
            B.s = "请输入处理路径！";
            return B
        }
        if (this.type == null) {
            var B = {};
            B.s = "fail";
            B.s = "请输入处理方法！";
            return B
        }
        var F = {};
        var D = this.action;
        var D = this.action;
        if (this.action.startWith("http:") || this.action.startWith("https:")) {
            D = this.action + "/" + this.type ;
        } else {
            D = MG.rootPath + this.action + "/" + this.type;
        }
        if (this.params != null) {
            F = MG.encode(this.params)
        }
      //拼接参数
        var param_index = 0;
        for (var E in this.params) {
        	if(param_index == 0){
        		D += "?" + E + "=" + this.params[E];
        	}else{
        		D += "&" + E + "=" + this.params[E];
        	}
        	param_index ++;
        }
        var A = {
            type: this.method,
            async: !G,
            url: D,
            data: F,
            dataType: "json",
            success: function(H) {
            	if(CALLBACK == null){
            		if (this.target) {
                        this.target.returns = H
                    }
            	}else{
            		CALLBACK(H);
            	}
            	
            },
            error: function(I, K, J) {
                if (this.target) {
                    var H = {};
                    H.state = 0;
                    H.message = K;
                    this.target.returns = H;
                    this.target.error = H.message
                }
            },
            target: this
        };
        $.ajax(A);
        if (G) {
            if (this.returns == null) {
                var E = {};
                E.state = 0;
                E.message = "获取服务器端数据为空，请稍后再试！";
                this.returns = E;
                this.error = E.message
            }
            return this.returns
        }
    }
};
MG.encode = function(o) {
    if (typeof(JSON) == "object" && JSON.stringify) {
        return JSON.stringify(o)
    }
    var type = typeof(o);
    if (o === null) {
        return "null"
    }
    if (type == "undefined") {
        return undefined
    }
    if (type == "number" || type == "boolean") {
        return o + ""
    }
    if (type == "string") {
        return $.quoteString(o)
    }
    if (type == "object") {
        if (typeof o.toJSON == "function") {
            return $.toJSON(o.toJSON())
        }
        if (o.constructor === Date) {
            var month = o.getUTCMonth() + 1;
            if (month < 10) {
                month = "0" + month
            }
            var day = o.getUTCDate();
            if (day < 10) {
                day = "0" + day
            }
            var year = o.getUTCFullYear();
            var hours = o.getUTCHours();
            if (hours < 10) {
                hours = "0" + hours
            }
            var minutes = o.getUTCMinutes();
            if (minutes < 10) {
                minutes = "0" + minutes
            }
            var seconds = o.getUTCSeconds();
            if (seconds < 10) {
                seconds = "0" + seconds
            }
            var milli = o.getUTCMilliseconds();
            if (milli < 100) {
                milli = "0" + milli
            }
            if (milli < 10) {
                milli = "0" + milli
            }
            return '"' + year + "-" + month + "-" + day + "T" + hours + ":" + minutes + ":" + seconds + "." + milli + 'Z"'
        }
        if (o.constructor === Array) {
            var ret = [];
            for (var i = 0; i < o.length; i++) {
                ret.push($.toJSON(o[i]) || "null")
            }
            return "[" + ret.join(",") + "]"
        }
        var pairs = [];
        for (var k in o) {
            var name;
            var type = typeof k;
            if (type == "number") {
                name = '"' + k + '"'
            } else {
                if (type == "string") {
                    name = $.quoteString(k)
                } else {
                    continue
                }
            }
            if (typeof o[k] == "function") {
                continue
            }
            var val = $.toJSON(o[k]);
            pairs.push(name + ":" + val)
        }
        return "{" + pairs.join(", ") + "}"
    }
};
MG.submit = function(A, B) {
    this.formId = A;
    this.action = B;
    this.method = "POST";
    this.type = "index";
    this.params = {};
    this.returns = null
};
MG.submit.prototype = {
    setParameter: function(B, A) {
        this.params[B] = A
    },
    execute: function(C, G) {
        this.type = C;
        if (G == null) {
            G = true
        }
        if (this.action == null) {
            var B = {};
            B.s = "fail";
            B.s = "请输入处理路径！";
            return B
        }
        if (this.type == null) {
            var B = {};
            B.s = "fail";
            B.s = "请输入处理方法！";
            return B
        }
        //拼接路径
        var F = MG.rootPath;
        if (this.action.startWith("http:") || this.action.startWith("https:")) {
        	 F = this.action + "/" + this.type + ".do";
        } else {
        	F = F + this.action + "/" + this.type + ".do";
        }
        //拼接参数
        var param_index = 0;
        for (var E in this.params) {
        	if(param_index == 0){
        		F += "?" + E + "=" + this.params[E];
        	}else{
        		F += "&" + E + "=" + this.params[E];
        	}
        	param_index ++;
        }
        var A = {
            type: this.method,
            async: !G,
            url: F,
            data: $("#" + this.formId).serialize(),
            success: function(H) {
                if (this.target) {
                    this.target.returns = H
                }
            },
            error: function(I, K, J) {
                if (this.target) {
                    var H = {};
                    H.state = 0;
                    H.message = K;
                    this.target.returns = H;
                    this.target.error = H.message
                }
            },
            target: this
        };
        $.ajax(A);
        if (G) {
            if (this.returns == null) {
                var D = {};
                D.state = 0;
                D.message = "获取服务器端数据为空，请稍后再试！";
                this.returns = D;
                this.error = D.message
            }
            return this.returns
        }
    }
};