package com.springbootdemo.springbootdemo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootdemo.springbootdemo.entities.Profile;
import com.springbootdemo.springbootdemo.services.ProfileService;

import net.minidev.json.JSONObject;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ProfilesController {
    
    @Autowired
    ProfileService profileService;

    @PostMapping("/profile")
    public ResponseEntity<?> save(@RequestBody Profile profile){
        try {
        return new ResponseEntity<Profile>(profileService.addProfile(profile),HttpStatus.CREATED);
            
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<List<Profile>>(profileService.getProfiles(),HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable String id){
        try {
            Profile profile = profileService.getProfileById(id); 
            return new ResponseEntity<Profile>(profile,HttpStatus.OK);
        } catch (Exception e) {
            JSONObject err = new JSONObject();
            err.put("msg",e.getMessage());
            return new ResponseEntity<JSONObject>(err,HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/profile/{id}")
    public ResponseEntity<?> updateProfile(@RequestBody Profile profile, @PathVariable String id){
        try{
            Profile empdb = profileService.updateProfileById(profile, id);
            return new ResponseEntity<Profile>(empdb,HttpStatus.OK);
        }
        catch(Exception e){
            JSONObject err = new JSONObject();
            err.put("msg",e.getMessage());
            return new ResponseEntity<JSONObject>(err,HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity<?> DeleteProfile(@PathVariable String id){
        try {
            Profile profile = profileService.DeleteProfileById(id); 
            return new ResponseEntity<Profile>(profile,HttpStatus.OK);
        } catch (Exception e) {
            JSONObject err = new JSONObject();
            err.put("msg",e.getMessage());
            return new ResponseEntity<JSONObject>(err,HttpStatus.NOT_FOUND);
        }
    }
}
