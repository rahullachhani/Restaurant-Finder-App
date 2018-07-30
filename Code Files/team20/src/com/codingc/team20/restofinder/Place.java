package com.codingc.team20.restofinder;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class Place {
    public String vicinity;
    public String latitude;
    public String longitude;
   
    public double distance;
    String cuisine;
    
    String type;
    public String id;
    public String name;
    public String rating;
    public String reference;
    public String[] types;
    public String open_now;
    public String place_id;
    double current_lat;
    double current_lng;
    double latitude1;
    double longitude1;
   
String address;

    public Configuration config;
    public int index;
    
    
    
    public Place(int i, Double lat, Double lng,String cuisine,String type) {
        // TODO Auto-generated constructor stub
    this.index=i;
        current_lat=lat;
    current_lng=lng;
    this.cuisine=cuisine;
    this.type=type;
    }

    
    public void setAttributes(Context c)
    {
        
        config=Configuration.load(c);
        
        this.open_now =config.getAttribute(index,"opening_hours","open_now");
        this.vicinity = config.getAttribute(index,"vicinity");
        this.latitude = (config.getAttribute(index,"geometry","location","lat"));
        this.longitude = (config.getAttribute(index,"geometry","location","lng"));
        this.id = config.getAttribute(index,"id");
        this.name = config.getAttribute(index,"name");
        this.place_id = config.getAttribute(index,"place_id");
        //this.rating = config.getAttribute(index,"rating");
        this.reference = config.getAttribute(index,"reference");
        this.types = config.getTypes(index);
        this.address=config.getAttribute(index,"formatted_address");
        latitude1=Double.parseDouble(latitude);
        longitude1=Double.parseDouble(longitude);
        //this.distance="600";
        double dist=distance(current_lat,current_lng,latitude1,longitude1);
        int dis=(int) dist;
        distance=dis;
         
              
    }

    public String getAddress() {
    return address;
}

    public String getCuisine() {
        return cuisine;
    }

    public String getType() {
        return type;
    }

    public String getOpen_now() {
        return open_now;
    }

     

    public String getVicinity() {
        return vicinity;
    }

    

    public String getLatitude() {
        return latitude;
    }

   
    public String getLongitude() {
        return longitude;
    } 

    
    
    
    public String getId() {
        return id;
    }

   

    public String getName() {
        return name;
    }

    

    String getRating() {
        return rating;
    }

    

    public String getReference() {
        return reference;
    }

    

    public String[] getTypes() {
        return types;
    }
    public String getPlace_id() {
        return place_id;
    }
    public double getDistance() {
        return distance;
    }
    
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // haversine great circle distance approximation, returns meters
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60; // 60 nautical miles per degree of seperation
        dist = dist * 1852; // 1852 meters per nautical mile
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    
   

    
}