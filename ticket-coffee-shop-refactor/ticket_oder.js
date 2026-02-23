
function t(o){
  var s=0; 
  var i=0;
  while(i<o.items.length){
    var p=o.items[i]; 
    var a=p.split("|");
    var it=a[0], z=a[1], q=parseInt(a[2]||"1",10), ex=(a[3]||"");
    var b=0;

    if(it=="coffee"){
      if(z=="S"){b=2.0}else if(z=="M"){b=2.5}else{b=3.0}
      if(o.happyHour===true){ b=b-(b*0.2) } // descuento solo base (aquí lo hace…)
    }else if(it=="tea"){
      if(z=="S"){b=1.5}else if(z=="M"){b=2.0}else{b=2.3}
    }else if(it=="muffin"){
      b=2.2
    }else{
      b=0 
    }

    var e=0;
    if(ex.length>0){
      var parts=ex.split(",");
      for(var k=0;k<parts.length;k++){
        if(parts[k]=="milk"){e+=0.2}
        else if(parts[k]=="shot"){e+=0.8}
        else if(parts[k]=="syrup"){e+=0.5}
        else{ e+=0 } 
      }
    }

    s = s + ( (b*q) + (e*q) );
    i++;
  }

  if(o.coupon && o.coupon!=""){
    if(o.coupon=="SAVE10"){
      s = s - (s*0.10);
    }else if(o.coupon=="FREEMUFFIN"){
      var j=0; var found=false;
      while(j<o.items.length && !found){
        if(o.items[j].indexOf("muffin|")===0){ found=true; }
        j++;
      }
      if(found){ s = s - 2.2; }
    }
  }

  if(o.vip==true){
    if(s>10){ s = s - 0.5; }
  }
  s = s + (s*0.10);
  return Math.round(s*100)/100;
}

function r(o){ // receipt
  var x="*** BYTE & BEAN ***\n";
  x += "VIP:"+(o.vip?"YES":"NO")+" | HAPPY:"+(o.happyHour?"YES":"NO")+"\n";
  for(var i=0;i<o.items.length;i++){
    var a=o.items[i].split("|");
    x += a[0]+" "+a[1]+" x"+a[2]+" extras:"+(a[3]||"")+"\n";
  }
  x += "COUPON:"+(o.coupon||"")+"\n";
  x += "TOTAL="+t(o)+" EUR\n";
  return x;
}

// ---- "tests" cutres pero útiles ----
const order1 = {
  items: ["coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"],
  coupon: "SAVE10",
  vip: true,
  happyHour: true
};

console.assert(t(order1)===9.60, "order1 total should be 9.60 but was "+t(order1));

const order2 = {
  items: ["muffin|L|2|", "coffee|S|1|syrup"],
  coupon: "FREEMUFFIN",
  vip: false,
  happyHour: false
};

console.assert(t(order2)===5.17, "order2 total should be 5.17 but was "+t(order2));

console.log(r(order1));
console.log("All assertions passed ✅");