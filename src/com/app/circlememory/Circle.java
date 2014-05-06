package com.app.circlememory;

import android.os.Parcel;
import android.os.Parcelable;

public class Circle implements Parcelable{
	private float x;
	private float y;
	private float radius;
	
	
	public Circle(float x, float y, float radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public Circle() {
		super();
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeFloat(x);
		dest.writeFloat(y);
		dest.writeFloat(radius);	
	}
	
	public static final Parcelable.Creator<Circle> CREATOR = new Creator<Circle>() {

		@Override
		public Circle createFromParcel(Parcel source) {
			Circle circle = new Circle();
			circle.setX(source.readFloat());
			circle.setY(source.readFloat());
			circle.setRadius(source.readFloat());
			return circle;
		}

		@Override
		public Circle[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Circle[size];
		}
		
	};
	
}
