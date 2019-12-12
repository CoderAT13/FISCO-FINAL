function encode16(str){
    str=str.toLowerCase();
    if (str.match(/^[-+]?\d*$/) == null){//非整数字符，对每一个字符都转换成16进制，然后拼接
        var s=str.split("");
        var temp="";
        for(var i=0;i<s.length;i++){
            s[i]=s[i].charCodeAt();//先转换成Unicode编码
            s[i]=s[i].toString(16);
            temp=temp+s[i];
        }
        return temp+"{"+1;//1代表字符
    }else{//数字直接转换成16进制
        str=parseInt(str).toString(16);
    }
    return str+"{"+0;//0代表纯数字
}
  
  
function produceRandom(n){
    var num=""; 
    for(var i=0;i<n;i++) 
    { 
        num+=Math.floor(Math.random()*10);
    } 
    return num;
}
  
//主加密函数
export function encrypt(str){
    var encryptStr="";//最终返回的加密后的字符串
    encryptStr+=produceRandom(3);//产生3位随机数
      
    var temp=encode16(str).split("{");//对要加密的字符转换成16进制
    var numLength=temp[0].length;//转换后的字符长度
    numLength=numLength.toString(16);//字符长度换算成16进制
    if(numLength.length==1){//如果是1，补一个0
        numLength="0"+numLength;
    }else if(numLength.length>2){//转换后的16进制字符长度如果大于2位数，则返回，不支持
        return "";
    }
    encryptStr+=numLength;
      
    if(temp[1]=="0"){
        encryptStr+=0;
    }else if(temp[1]=="1"){
        encryptStr+=1;
    }
      
    encryptStr+=temp[0];
      
    if(encryptStr.length<20){//如果小于20位，补上随机数
        var ran=produceRandom(20-encryptStr.length);
        encryptStr+=ran;
    }
    return encryptStr;
}
