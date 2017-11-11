/**
 * Created by Y on 2017/6/16 0016.
 */
// 字典工具类
var DICT = {
    getName: function (code,num) {
        var dict = getDict();
        var name = dict[code + num];
        if (BladeTool.isEmpty(name)) {
            $.ajax({
                type: "POST",
                url: BladeApp.ctxPath+"/cache/getDictName",
                dataType : 'json',
                async: false,
                success: function (result) {
                    if (result.code === 0) {
                        dict = result.data;
                        console.log(dict);
                        setDict(dict);
                        name = dict[code + num] || "未知";
                    } else {
                        layer_alert("数据加载失败", "error");
                    }
                }
            });
        }
        return name;
    },
    getNameList: function (code) {
        var dict = getDict();
        var list = [];
        for(var key in dict){
            if(key.indexOf(code)==0&&key.lastIndexOf('0')!=key.length-1){
                list.push({text:dict[key],value:key});
            }
        }
        if (list==null) {
            $.ajax({
                type: "POST",
                url: BladeApp.ctxPath+"/cache/getDictName",
                dataType : 'json',
                async: false,
                success: function (result) {
                    if (result.code === 0) {
                        dict = result.data;
                        console.log(dict);
                        setDict(dict);
                        var temp = dict || [];
                        var list = [];
                        for(var key in temp){
                            if(key.indexOf(code)==0&&key.lastIndexOf('0')!=key.length-1){
                                list.push({text:temp[key],value:key});
                            }
                        }
                    } else {
                        layer_alert("数据加载失败", "error");
                    }
                }
            });
        }
        return list;
    }
};

function getDict() {
    var dict = sessionStorage.getItem('dictList');
    if (BladeTool.isEmpty(dict)) {
        return {};
    }
    try {
        // 此处是可能产生例外的语句
        return JSON.parse(dict);
    } catch(error) {
        return {};
        // 此处是负责例外处理的语句
    }

}

function setDict(dict) {
    sessionStorage.setItem('dictList', JSON.stringify(dict));
}