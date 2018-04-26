var currentUserID;
var dataload;
var temp;
var temp2;

$(document).ready(function(){
   
  $('#back-btn').click(function() {
    
    
var t =window.location.href;
t = t.substring(0, t.lastIndexOf("/") );
        window.location.replace(t+"/drawing");

    });
       
   
   

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
var t =window.location.href;
t = t.substring(0, t.lastIndexOf("/") );
        window.location.replace(t+"/logingin");



    }
});
 
    
});

function queryDatabase(){
  
  
      
       var firebaseRef = firebase.database().ref();
        firebaseRef.child(currentUserID).child("imageURL").once('value', function(snapshot) {
            
      
      var PostObject = snapshot.val();
      console.log(PostObject);
      
      if(PostObject != null){
      var keys = Object.keys(PostObject);
      
    var currentRow;
    for (var i = 0; i < keys.length; i ++){
        var currentObject = PostObject[keys[i]];
       
        if(i %3 == 0){
            currentRow = document.createElement("div");
            $(currentRow).addClass("row");
            $("#contentHolder").append(currentRow);
        }
        var col = document.createElement("div");
        $(col).addClass("col-lg-4");
        var image = document.createElement("img");
        image.src = currentObject.url;
        $(image).addClass("contentImage");
        var p = document.createElement("p");
        $(p).html(currentObject.caption);
        $(p).addClass("contentCaption");
        $(col).append(image);
        $(col).append(p);
        $(currentRow).append(col);
        
              }
          }
          if(PostObject == null){
              alert("No Saved Images To View");
          }
        });

}




//});