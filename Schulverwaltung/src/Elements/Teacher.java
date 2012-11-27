package Elements;

import java.util.Date;

public class Teacher extends Person<Teacher>{
	private Date birthday = new Date();
	private int roomId = -1;
	private double workhours = 0.00;
	
	public Date getBirthday() {
			return birthday;
		}

		public Teacher setBirthday(Date birthday) {
			this.birthday = birthday;
			return this;
		}

		public int getRoomId() {
			return roomId;
		}

		public Teacher setRoomId(int roomId) {
			this.roomId = roomId;
			return this;
		}

		public double getWorkhours() {
			return workhours;
		}

		public Teacher setWorkhours(double workhours) {
			this.workhours = workhours;
			return this;
		}

	@Override
	public void addToDb() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromDb() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Teacher load() {
		// TODO Auto-generated method stub
		return this;
	}

}
