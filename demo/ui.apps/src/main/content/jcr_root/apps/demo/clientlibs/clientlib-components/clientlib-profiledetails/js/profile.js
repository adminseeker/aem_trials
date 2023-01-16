console.log("profile.js loaded")
let resourcepath=$("#profiledetailsresourcepath").val()
const params = new URLSearchParams(document.location.search);
profileId=params.get("id");
console.log("resourcepath",resourcepath)
$.ajax({
    type: 'GET',    
    url: resourcepath+'.profile.json' ,
    async: false,
    cache: false,
    data: 'id='+ profileId,
    success: function(data, status, xhr){
        if(data != undefined){
            $("#name").text(data.name);
            $("#age").text(data.age);
            $("#dob").text(data.dob);
            $("#title").text(data.title);
            $("#company").text(data.company);
            $("#job").text(data.job);
            console.log("data",data)
        } else {
            console.log("Data error")
        }
               
     },
       error: function(xhr, status, errorThrown){
        console.log("ajax error")
    }

    });