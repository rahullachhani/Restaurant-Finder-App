package com.codingc.team20.restofinder;

import android.content.Context;

public class Place_Details  {
	
	//String[] address_components;
	//String[] events;
	String formatted_address;
	String formatted_phone;
	double rating;
	String[][] reviews;
	String url;
	double price_level;
	String[] photoreferences;
	String website;
	String place_id;
	 int likes,dislikes;
	    int likes_user,dislikes_user;

	    Place_Details()
	    {
	    	likes=0;
	        dislikes=0;

	    }
	
	
	String open_hours;
	
	 public DetailedConfiguration config;
	 
	
	 
	 public void setAttributes(Context c,String id)
	    {
	    	
	    	config=DetailedConfiguration.load(c,id);
	    	this.formatted_address=config.getAttribute("formatted_address");
	    	this.formatted_phone=config.getAttribute("formatted_phone_number");
	    	if(config.getAttribute("rating")!=null)
	    	this.rating=Double.parseDouble(config.getAttribute("rating"));
	    	else
	    	{	if(config.getAttribute("user_ratings_total")!=null)
	    		this.rating=Double.parseDouble(config.getAttribute("user_ratings_total"));
	    	}
	    	this.url=config.getAttribute("url");
	    	if(config.getAttribute("price_level")!=null)
	    	this.price_level=Double.parseDouble(config.getAttribute("price_level"));
	    	this.website=config.getAttribute("website");
	    	this.place_id=config.getAttribute("place_id");
	    	this.reviews=config.getObjects("reviews","author_name","rating","text");
	    	this.photoreferences=config.getPhotoReferences();
	    	this.open_hours=config.getOpenHours();
	    	
	    	 likes=0;
	            dislikes=0;
			
			
	    }
	 public String getFormatted_address() {
			return formatted_address;
		}

		public String getFormatted_phone() {
			return formatted_phone;
		}

		public double getRating() {
			return rating;
		}

		public String[][] getReviews() {
			return reviews;
		}

		public String getUrl() {
			return url;
		}

		public double getPrice_level() {
			return price_level;
		}

		public String[] getPhotoreferences() {
			return photoreferences;
		}

		public String getWebsite() {
			return website;
		}

		public String getPlace_id() {
			return place_id;
		}

		public String getOpen_hours() {
			return open_hours;
		}
		public int retLikes(){
	        return likes;
	    }
	    public int retdisLikes(){
	        return dislikes;
	    }
	    public int getLikes_user(){
	        return likes_user;
	    }
	    public int getdisLikes_user(){
	        return dislikes_user;
	    }

		
		 public void setLD(int l, int d,int likes_user,int dislikes_user) {
		        // TODO Auto-generated method stub
		        likes=l;
		        dislikes=d;
		    this.likes_user=likes_user;
		    this.dislikes_user=dislikes_user;
		    
		    }


}
