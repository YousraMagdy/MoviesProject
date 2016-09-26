package com.thenewboston.movies_project;




public class Extra {
    String OverView;
    String Title;
   String image; // drawable reference id
    String Vote;
    String Date;
    String ID;
    public Extra(String vtitle, String image,String voverview,String vote,String date,String id)
    {
        this.OverView = voverview;
        this.Title = vtitle;
        this.image = image;
        this.Vote=vote;
        this.Date=date;
        this.ID=id;
    }

}