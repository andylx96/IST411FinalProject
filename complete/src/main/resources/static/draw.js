var jsonData;
  var postPic;
  var newPic;
  var myJson;

var canvas, ctx,
    brush = {
        x: 0,
        y: 0,
        color: '#000000',
        size: 10,
        down: false,
    },
    strokes = [],
    currentStroke = null;

function redraw () {
    ctx.clearRect(0, 0, canvas.width(), canvas.height());
    ctx.lineCap = 'round';
    for (var i = 0; i < strokes.length; i++) {
        var s = strokes[i];
        ctx.strokeStyle = s.color;
        ctx.lineWidth = s.size;
        ctx.beginPath();
        ctx.moveTo(s.points[0].x, s.points[0].y);
        for (var j = 0; j < s.points.length; j++) {
            var p = s.points[j];
            ctx.lineTo(p.x, p.y);
        }
        ctx.stroke();
    }
}

function init () {
    canvas = $('#draw');
    canvas.attr({
        width: window.innerWidth,
        height: window.innerHeight,
    });
    ctx = canvas[0].getContext('2d');

    function mouseEvent (e) {
        brush.x = e.pageX;
        brush.y = e.pageY;

        currentStroke.points.push({
            x: brush.x,
            y: brush.y,
        });

        redraw();
    }

    canvas.mousedown(function (e) {
        brush.down = true;

        currentStroke = {
            color: brush.color,
            size: brush.size,
            points: [],
        };

        strokes.push(currentStroke);
        
        mouseEvent(e);
    }).mouseup(function (e) {
        brush.down = false;

        mouseEvent(e);

        currentStroke = null;
    }).mousemove(function (e) {
        if (brush.down)
            mouseEvent(e);
    });
    
//    
//  $.ajax({
//      type: 'POST',
//      url: '/jsonUpdate',
//      data: JSON.stringify(strokes),    
//      contentType: "application/json",
////      reponseText: respText,
//      success: function(newPic) {
////          postPic2 = postPic;
//          console.log(postPic);
//          strokes = [];
//          strokes = JSON.parse(newPic);
////          console.log(newPic);
//            jsonData = JSON.parse(newPic);
//          redraw();
//      },
//      error: function(){
//          alert('error');
//          
//      }
//      
//  });
    

    $('#save-btn').click(function () {
        window.open(canvas[0].toDataURL());
    });

    $('#undo-btn').click(function () {
        strokes.pop();
        redraw();
    });
    $('#test-btn').click(function () {
//         window.open(strokes..toDataURL());


    strokes = [];
    console.log(jsonData);
    strokes = jsonData;
    redraw();
    });

    $('#clear-btn').click(function () {
        strokes = [];
        redraw();
    });
    $('#test2-btn').click(function () {
  postPic = strokes;
//  var postPic = strokes;
  $.ajax({
      type: 'POST',
      url: '/jsonUpdate',
      data: JSON.stringify(postPic),    
      contentType: "application/json",
//      reponseText: respText,
      success: function(newPic) {
//          postPic2 = postPic;
          console.log(postPic);
          strokes = [];
          strokes = JSON.parse(newPic);
//          console.log(newPic);
            jsonData = JSON.parse(newPic);
          redraw();
      },
      error: function(){
          alert('error');
          
      }
      
  });
  
  
  
    });

    $('#color-picker').on('input', function () {
        brush.color = this.value;
    });

    $('#brush-size').on('input', function () {
        brush.size = this.value;
    });
}


myJson = JSON.stringify(strokes);


$(function (){
    
    $.ajax({
        type: 'GET',
        url: 'test.json',
        success: function(data) {
            console.log('sucess', data);
            
       jsonData = data;
       strokes = jsonData;
        }
        
    });
    
});


$(init);
setInterval(function(){ 
$(function (){
    
    $.ajax({
        type: 'GET',
        url: 'test.json',
        success: function(data) {
            console.log('sucess', data);
            
       jsonData = data;
       strokes = jsonData;
        redraw();
        }
        
    });
    
});
}, 3000);
