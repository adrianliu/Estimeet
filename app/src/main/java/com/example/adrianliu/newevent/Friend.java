package com.example.adrianliu.newevent;

/**
 * Created by adrianliu on 18/10/14.
 */
public class Friend {

    private int _id;
    private String _name;
    private byte[] _image;

    public Friend(int id,byte[] image,String name){
        _id=id;
        _name=name;
        _image=image;
    }

    public int getID(){
        return this._id;
    }
    public void setID(int id){
        this._id=id;
    }

    public String getName(){
        return this._name;
    }
    public void setName(String name){
        this._name=name;
    }

    public byte[] getImage(){
        return this._image;
    }
    public void setImage(byte[] image){
        this._image=image;
    }

}
