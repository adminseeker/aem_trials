console.log("profile.js loaded")
$(".profilelisting").removeClass("d-none");
$(".profiledetails").addClass("d-none");
$(".addprofile").addClass("d-none");
let resourcepath=$("#profiledetailsresourcepath").val()
console.log("resourcepath",resourcepath)

function FormDataToJson($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function FormDataToJsonString($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return JSON.stringify(indexed_array);
}

$(".btn.btn-back").click(()=>{
    $(".profiledetails").addClass("d-none");
    $(".profilelisting").removeClass("d-none");
    $(".addprofile").addClass("d-none");
})


$(".btn.btn-name").click((e)=>{
    let profileId=e.target.getAttribute('data-profileid');
    $.ajax({
        method: 'GET',    
        url: resourcepath+'.profile.json' ,
        async: false,
        cache: false,
        data: 'id='+ profileId,
        success: function(data, status, xhr){
            if(data != undefined){
                $(".profiledetails").removeClass("d-none");
                $(".profilelisting").addClass("d-none");
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
})

$(".btn.btn-add").click(()=>{
    $(".profiledetails").addClass("d-none");
    $(".profilelisting").addClass("d-none");
    $(".addprofile").removeClass("d-none");
})


$(".btn.btn-submit").click(()=>{
    const data = new FormData();
    $.ajax({
        method: 'POST',    
        url: resourcepath+'.profile.json' ,
        async: false,
        data: FormDataToJsonString($("#addProfile")),
        contentType: 'application/json',
        success: function(data, status, xhr){

             if(data!=null){
                window.location.reload();
                console.log("Added")
            }	         	
        else{
             console.log("No data")    
             }
                  
         },
           error: function(xhr, status, errorThrown){
            console.log("Data",$("#addProfile").serialize())

             console.log(errorThrown)
            }

     });
})

$(".btn.btn-delete").click((e)=>{
    let profileId=e.target.getAttribute('data-profileid');
    $.ajax({
        method: 'DELETE',    
        url: resourcepath+'.profile.json?id='+ profileId ,
        async: false,
        success: function(data, status, xhr){

             if(data!=null){
                console.log(data)
                console.log("Deleted")
                window.location.reload()
            }	         	
        else{
             console.log("No data")    
             }
                  
         },
           error: function(xhr, status, errorThrown){
             console.log(errorThrown)
            }

     });
})