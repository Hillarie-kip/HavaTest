package com.hillarie.havatest.rides;

/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public class pojo {

    public int id ,car_year,duration ,cost;
    public double pickup_lat ,pickup_lng,dropoff_lat ,dropoff_lng,driver_rating,distance;
    public String status,request_date,pickup_location,dropoff_location,pickup_date,dropoff_date,type,driver_name,driver_pic,car_make,car_model,car_number,car_pic,duration_unit,distance_unit,cost_unit;


    public pojo(int id, int car_year, int duration, int cost, double pickup_lat, double pickup_lng, double dropoff_lat, double dropoff_lng, double driver_rating, double distance, String status, String request_date, String pickup_location, String dropoff_location, String pickup_date, String dropoff_date, String type, String driver_name, String driver_pic, String car_make, String car_model, String car_number, String car_pic, String duration_unit, String distance_unit, String cost_unit) {
        this.id = id;
        this.car_year = car_year;
        this.duration = duration;
        this.cost = cost;
        this.pickup_lat = pickup_lat;
        this.pickup_lng = pickup_lng;
        this.dropoff_lat = dropoff_lat;
        this.dropoff_lng = dropoff_lng;
        this.driver_rating = driver_rating;
        this.distance = distance;
        this.status = status;
        this.request_date = request_date;
        this.pickup_location = pickup_location;
        this.dropoff_location = dropoff_location;
        this.pickup_date = pickup_date;
        this.dropoff_date = dropoff_date;
        this.type = type;
        this.driver_name = driver_name;
        this.driver_pic = driver_pic;
        this.car_make = car_make;
        this.car_model = car_model;
        this.car_number = car_number;
        this.car_pic = car_pic;
        this.duration_unit = duration_unit;
        this.distance_unit = distance_unit;
        this.cost_unit = cost_unit;
    }

    public int getId() {
        return id;
    }

    public int getCar_year() {
        return car_year;
    }

    public int getDuration() {
        return duration;
    }

    public int getCost() {
        return cost;
    }

    public double getPickup_lat() {
        return pickup_lat;
    }

    public double getPickup_lng() {
        return pickup_lng;
    }

    public double getDropoff_lat() {
        return dropoff_lat;
    }

    public double getDropoff_lng() {
        return dropoff_lng;
    }

    public double getDriver_rating() {
        return driver_rating;
    }

    public double getDistance() {
        return distance;
    }

    public String getStatus() {
        return status;
    }

    public String getRequest_date() {
        return request_date;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public String getDropoff_location() {
        return dropoff_location;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public String getDropoff_date() {
        return dropoff_date;
    }

    public String getType() {
        return type;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public String getDriver_pic() {
        return driver_pic;
    }

    public String getCar_make() {
        return car_make;
    }

    public String getCar_model() {
        return car_model;
    }

    public String getCar_number() {
        return car_number;
    }

    public String getCar_pic() {
        return car_pic;
    }

    public String getDuration_unit() {
        return duration_unit;
    }

    public String getDistance_unit() {
        return distance_unit;
    }

    public String getCost_unit() {
        return cost_unit;
    }
}
