var currentUserID;


$(document).ready(function(){
   

firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.



        var user = firebase.auth().currentUser;

        if (user != null) {

            console.log(user.uid)
            currentUserID = user.uid;
        }
        
        $('#currentroomid').text("Current Room ID: " + currentUserID);
        
queryDatabase();

    } else {
        // No user is signed in.
        window.location.replace("http://localhost:8080/logingin");


    }
});
 
    
});

function queryDatabase(){
//    var userId = currentUserID;
// firebase.database().ref(userId).once('value').then(function(snapshot) {
//  
//  
//  
//});
    
    
    var currentRow;
    for (var i = 0; i < 9; i ++){
        if(i %3 == 0){
            currentRow = document.createElement("div");
            $(currentRow).addClass("row");
            $("#contentHolder").append(currentRow);
        }
        var col = document.createElement("div");
        $(col).addClass("col-lg-4");
        var image = document.createElement("img");
        image.src = "https://cdn2.techadvisor.co.uk/cmsdata/features/3614881/Android_thumb800.jpg";
        $(image).addClass("contentImage");
        var p = document.createElement("p");
        $(p).html("Caption");
        $(p).addClass("contentCaption");
        $(col).append(image);
        $(col).append(p);
        $(currentRow).append(col);
    }
    
}




//});