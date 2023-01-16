const mongoose = require("mongoose");
const ProfileSchema = new mongoose.Schema({
    name:{
        type:String,
        require:true
    },
    age:{
        type:String,
        require:true
    },
    dob:{
        type:String,
        require:true
    },
    title:{
        type:String,
        require:true
    },
    company:{
        type:String,
        require:true
    },
    job:{
        type:String,
        require:true
    },
    
});

module.exports = mongoose.model("Profile",ProfileSchema);