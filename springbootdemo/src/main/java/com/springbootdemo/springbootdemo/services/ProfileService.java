package com.springbootdemo.springbootdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootdemo.springbootdemo.entities.Profile;
import com.springbootdemo.springbootdemo.exceptions.ResourceNotFound;
import com.springbootdemo.springbootdemo.repository.ProfileRepo;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {
    
    @Autowired
    ProfileRepo repo;

    public Profile addProfile(Profile profile){
        UUID uid = UUID.randomUUID();
        String id = uid.toString();
        profile.set_id(id);
        return repo.save(profile);
    }

    public List<Profile> getProfiles(){
        return repo.findAll();
    }

    public Profile getProfileById(String id){
        Profile profile = repo.findBy_id(id).orElseThrow(()-> new ResourceNotFound("Profile Not Found!"));
        return profile;
        
    }

    public Profile updateProfileById(Profile profile,String id) throws Exception{
        
        Profile empdb = repo.findBy_id(id).orElseThrow(()->new ResourceNotFound("Profile Not Found!"));
        

        if(profile.getName()!=null){
            empdb.setName(profile.getName());
        }

        if(profile.getAge()!=null){
            empdb.setAge(profile.getAge());
        }

        if(profile.getDob()!=null){
            empdb.setDob(profile.getDob());
        }

        if(profile.getTitle()!=null){
            empdb.setTitle(profile.getTitle());
        }

        if(profile.getCompany()!=null){
            empdb.setCompany(profile.getCompany());
        }

        if(profile.getJob()!=null){
            empdb.setJob(profile.getJob());
        }
    
        if(profile.getName()==null && profile.getAge()==null &&profile.getDob()==null &&profile.getTitle()==null &&profile.getCompany()==null &&profile.getJob()==null) throw new Exception("Nothing to update!");
        
        return repo.save(empdb);
        
    }

    public Profile DeleteProfileById(String id){
        Profile profile = repo.findBy_id(id).orElseThrow(()-> new ResourceNotFound("Profile Not Found!"));
        repo.delete(profile);
        return profile;        
    }
}
