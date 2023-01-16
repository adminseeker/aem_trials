const express = require("express");
const connectToMongoDB = require("./db/mongoose");
const app = express();
const Profile = require("./db/models/Profile");
connectToMongoDB();

PORT=3000

app.use(express.json())

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

app.get("/api/v1/profile/:id",async (req,res)=>{
    try {
        const profile=await Profile.findById(req.params.id)
        res.json(profile)
    } catch (error) {
        res.status(500).json({"msg":"server error"});
        console.log(error)
    }
})

app.get("/api/v1/profile",async (req,res)=>{
    try {
        const profile=await Profile.find({})
        res.json(profile)
    } catch (error) {
        res.status(500).json({"msg":"server error"});
        console.log(error)
    }
})

app.post("/api/v1/profile",async (req,res)=>{
    try {
        const profile = new Profile(req.body);
        await profile.save();
        res.json(profile)
    } catch (error) {
        res.status(500).json({"msg":"server error"});
        console.log(error)
    }
    
})

app.patch("/api/v1/profile/:id",async (req,res)=>{
    try {
        const updates=req.body;
        const profile = await Profile.findOneAndUpdate({"_id":req.params.id},updates,{new:true});
        res.json(profile);
    } catch (error) {
        res.status(500).json({"msg":"server error"});
        console.log(error)
    }
})

app.delete("/api/v1/profile/:id",async (req,res)=>{
    try {
    const profile = await Profile.deleteOne({"id":req.params.id});
    res.json({"msg":"Deleted Successfully","profile":profile})
    } catch (error) {
        res.status(500).json({"msg":"server error"});
        console.log(error)
    }

})

app.listen(PORT,()=>{
    console.log("Server Started on Port "+PORT);
})