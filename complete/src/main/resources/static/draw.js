var currentUserID;
var currentUser;
var temp2;


firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.



        var user = firebase.auth().currentUser;

        if (user != null) {

            console.log(user.uid)
            currentUserID = user.uid;
            currentUser = user.uid;
        }



        toggleListener();


    } else {
        // No user is signed in.


        var t = window.location.href;
        t = t.substring(0, t.lastIndexOf("/"));
        window.location.replace(t + "/logingin");




    }
});



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


function toggleListener() {

    var firebaseRef = firebase.database().ref();
    firebaseRef.child(currentUserID).child("currentSession").on('value', function(snapshot) {
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
    firebaseRef.child(currentUserID).child("currentSession").set(strokes);
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
//        window.open(canvas[0].toDataURL("image/png"));
        
        var d=canvas[0].toDataURL("image/png")

        var w=window.open('about:blank','image from canvas');
w.document.write("<img src='"+d+"' alt='from canvas'/>");
        

    });


    $('#logout-btn').click(function() {
//        window.open(canvas[0].toDataURL("image/png"));
       firebase.auth().signOut();
        

    });

    $('#undo-btn').click(function() {
        strokes.pop();
        redraw();
        updateFB();

    });

    $('#popupsave-btn').click(function() {

        $('#modal-wrapper2').css({
            display: "block"
        });


    });

    $('#popupload-btn').click(function() {

        if ($('.row').length)
        // use this if you are using class to check
        {
            // it exists

            $('.row').remove();
        }

        var firebaseRef = firebase.database().ref();
        firebaseRef.child(currentUser).once('value', function(snapshot) {

            var temp = [];

            temp = snapshot.val();

            temp2 = snapshot;




            snapshot.forEach(function(childSnapshot) {
                var key = childSnapshot.key; // "ada"
                console.log(key);

                if ((key.toString().trim() != "currentSession") && (key.toString().trim() != "imageURL")) {

                    var currentRow;

                    currentRow = document.createElement("div");
                    $(currentRow).addClass("row");

                    $("#listofprojects").append(currentRow);
                    //        }
                    var col = document.createElement("div");
                    var projectname = document.createElement("h4");
                    projectname.innerHTML = key;
                    $(col).append(projectname);
                    $(currentRow).append(col);
                }
            });




            if (temp == null) {
                alert('Project Not Found');
            } else {



            }



        });
        //


        $('#modal-wrapper3').css({
            display: "block"
        });


    });




    $('#saveproject-btn').click(function() {


        var saveName = $('#savenametext').val();
        saveName = saveName.trim()


        var firebaseRef = firebase.database().ref();
        firebaseRef.child(currentUser).child(saveName).set(strokes);
        console.log("pushing");
        alert('Project Saved');

    });


    $('#loadproject-btn').click(function() {




        var loadName = $('#loadnametext').val();
        loadName = loadName.trim()


        //  var firebaseRef = firebase.database().ref();
        //    firebaseRef.child(currentUser).child(loadName).set(strokes);
        //    console.log("pushing");



        var firebaseRef = firebase.database().ref();
        firebaseRef.child(currentUser).child(loadName).once('value', function(snapshot) {

            var temp = [];

            temp = snapshot.val();

            if (temp == null) {
                alert('Project Not Found');
            } else {

                strokes = snapshot.val();
                var firebaseRef = firebase.database().ref();
                firebaseRef.child(currentUserID).child("currentSession").set(strokes);
                console.log("pushing");

            }



        });




    });




    $('#clear-btn').click(function() {
        strokes = [];
        redraw();
        updateFB();

    });




    $('#join-btn').click(function() {


        $('#modal-wrapper').css({
            display: "block"
        });


    });


    $('#test3-btn').click(function() {


        var t = window.location.href;
        t = t.substring(0, t.lastIndexOf("/"));


        window.location.replace(t + "/viewimages");


    });

    $('#test4-btn').click(function() {

        var firebaseRef = firebase.storage().ref();

        var name = (new Date()).getTime() + ".png";

        var ref = firebaseRef.child(currentUser).child(name);



        canvas[0].toBlob(blob => {

            var task = ref.put(blob);
            task.on('state_changed', function(snapshot) {}, function(error) {
                console.error("Unable to save image.");
                console.error(error);
            }, function() {
                var url = task.snapshot.downloadURL;
                console.log("Saved to " + url);


                var firebaseDataRef = firebase.database().ref();
                firebaseDataRef.child(currentUser).child("imageURL").push({
                    caption: name,
                    url: url,
                    user: currentUser
                });
                console.log("pushingURL");

                //                $('#testImg').style.height = '400px';

                $("#testimg").attr("src", url);



            });
        });
        alert("Image Saved To Database Account");
        console.log("pushing");

    });


    $('#color-picker').on('input', function() {
        brush.color = this.value;
    });

    $('#brush-size').on('input', function() {
        brush.size = this.value;
    });
    
//    window.addEventListener('scroll', noscroll);
//    document.body.style.overflow = 'hidden';
    
    window.addEventListener('scroll', function() {
        
        
        
            canvas.attr({
        width: window.innerWidth + window.scrollX,
        height: window.innerHeight + window.scrollY,
    });
redraw();
    
    
    
    });

    window.addEventListener('resize', function(event){
//        console.log("test");
        
        
            canvas.attr({
        width: window.innerWidth + window.scrollX,
        height: window.innerHeight + window.scrollY,
    });
redraw();
});

}

$(init);

//$(toggleListener);

$('#joinroom-btn').click(function() {

    var roomID = $('#roomid').val();
    roomID = roomID.trim()
    var firebaseRef = firebase.database().ref();

    firebaseRef.child(currentUserID).child("currentSession").off();
    currentUserID = roomID;
    console.log("Room " + roomID);
    console.log("user " + currentUserID);



    toggleListener();

        alert('Room Joined');

});


function noscroll() {
  window.scrollTo( 0, 0 );
}