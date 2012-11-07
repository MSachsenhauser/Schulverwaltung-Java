package Elements;

import java.util.Date;

public class Teacher extends Person{
	private Date birthday = new Date();
	private int roomId = -1;
	private double workhours = 0.00;
	
	public Date getBirthday() {
			return birthday;
		}

		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}

		public int getRoomId() {
			return roomId;
		}

		public void setRoomId(int roomId) {
			this.roomId = roomId;
		}

		public double getWorkhours() {
			return workhours;
		}

		public void setWorkhours(double workhours) {
			this.workhours = workhours;
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

}
