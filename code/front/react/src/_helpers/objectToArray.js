export const objectToArray = (obj) => {
    var arr = [];
    for (var key in obj) {
        if (!obj.hasOwnProperty(key)) {
            continue;
        }
        var item = {};
        item[key] = obj[key];
        arr.push(item);
    }
    return arr;
};