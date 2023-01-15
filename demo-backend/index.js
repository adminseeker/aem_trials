const express = require("express");

const app = express();

PORT=3000

const profile={
    "name":"Aravind A",
    "age":"23",
    "dob":"19-12-1999",
    "title":"adminseeker",
    "company":"Epsilon",
    "job":"Software Developer"
};

app.get("/api/v1",(req,res)=>{
    res.json({"msg":"Created By Adminseeker"});
})

app.get("/api/v1/profile/:id",(req,res)=>{
    if(req.params.id=="1"){
        res.json(profile);
    }else{
        res.json({})
    }
})

app.listen(PORT,()=>{
    console.log("Server Started on Port "+PORT);
})