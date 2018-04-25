var currentUserID;

firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.



        var user = firebase.auth().currentUser;

        if (user != null) {

            console.log(user.uid)
            currentUserID = user.uid;
        }
        
        
        
toggleListener();
        

    } else {
        // No user is signed in.
        window.location.replace("http://localhost:8080/logingin");


    }
});


//$('#join-btn').click(function(event) {
//    $('#modal-wrapper').style.display = 'block';
//    if (event.target == modal) {
//        modal.style.display = "none";
//    }
//});


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

function redraw() {
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


function toggleListener(){
    
     var firebaseRef = firebase.database().ref();
        firebaseRef.child(currentUserID).on('value', function(snapshot) {
            strokes = [];
            strokes = snapshot.val();

            if (strokes == null) {
                strokes = [];
            }


            redraw();
        });
        
        $('#currentroomid').text("Current Room ID: " + currentUserID);
}

function updateFB() {
    //
    var firebaseRef = firebase.database().ref();
    firebaseRef.child(currentUserID).set(strokes);
    console.log("pushing");
}

function init() {
    canvas = $('#draw');
    canvas.attr({
        width: window.innerWidth,
        height: window.innerHeight,
    });
    ctx = canvas[0].getContext('2d');

    function mouseEvent(e) {
        brush.x = e.pageX;
        brush.y = e.pageY;

        currentStroke.points.push({
            x: brush.x,
            y: brush.y,
        });

        redraw();


    }

    canvas.mousedown(function(e) {
        brush.down = true;

        currentStroke = {
            color: brush.color,
            size: brush.size,
            points: [],
        };

        strokes.push(currentStroke);

        mouseEvent(e);
    }).mouseup(function(e) {
        brush.down = false;

        mouseEvent(e);

        currentStroke = null;
        updateFB();


    }).mousemove(function(e) {
        if (brush.down)
            mouseEvent(e);
    });

    $('#save-btn').click(function() {
        window.open(canvas[0].toDataURL("image/png"));
        
//        canvas[0].toBlob(function(blob) {
//    saveAs(blob, "pretty image.png");
//});
//        canvas.toDataURL;
    });

    $('#undo-btn').click(function() {
        strokes.pop();
        redraw();
        updateFB();

    });
    $('#test-btn').click(function() {



        var firebaseRef = firebase.database().ref();
        firebaseRef.child(currentUserID).on('value', function(snapshot) {
            strokes = [];
            strokes = snapshot.val();

            if (strokes == null) {
                strokes = [];
            }


            redraw();
        });
    });

    $('#clear-btn').click(function() {
        strokes = [];
        redraw();
        updateFB();

    });
    
    
 
    
    
    $('#join-btn').click(function() {


$('#modal-wrapper').css({ display: "block" });


    });


    $('#test3-btn').click(function() {

        var firebaseRef = firebase.database().ref();
        firebaseRef.child(currentUserID).set(strokes);
        console.log("pushing");
    });
    $('#test4-btn').click(function() {

        var firebaseRef = firebase.storage().ref();

        var name = (new Date()).getTime() + ".png";

        var ref = firebaseRef.child(currentUserID).child(name);




        canvas[0].toBlob(blob => {

            var task = ref.put(blob);
            task.on('state_changed', function(snapshot) {}, function(error) {
                console.error("Unable to save image.");
                console.error(error);
            }, function() {
                var url = task.snapshot.downloadURL;
                console.log("Saved to " + url);


//                $('#testImg').style.height = '400px';

                $("#testimg").attr("src",url);

            });
        });
        alert("File Saved");
        console.log("pushing");

    });


    $('#color-picker').on('input', function() {
        brush.color = this.value;
    });

    $('#brush-size').on('input', function() {
        brush.size = this.value;
    });

}

$(init);

//$(toggleListener);

   $('#joinroom-btn').click(function() {
   
   var roomID = $('#roomid').val();
   roomID = roomID.trim()
    var firebaseRef = firebase.database().ref();
       
firebaseRef.child(currentUserID).off();
   currentUserID = roomID;
   console.log("Room " +roomID);
   console.log("user " +currentUserID);
   
   

toggleListener();



    });