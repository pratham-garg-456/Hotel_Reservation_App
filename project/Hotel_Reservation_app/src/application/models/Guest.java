package application.models;

public class Guest {
    private int m_guestID;
   
    private String m_firstName;
    private String m_lastName;
    private String m_address;
    private String m_phone;
    private String m_email;

    // Constructors, getters, and setters
    public Guest ( String firstName, String lastName, String address, String phone, String email){
    	
    	
    	m_firstName = firstName;
    	m_lastName = lastName;
    	m_address = address;
    	m_phone = phone;
    	m_email= email;
    }

//	public Guest() {
//		// TODO Auto-generated constructor stub
//	}

	public int getGuestID() {
		return m_guestID;
	}

	public void setGuestID(int m_guestID) {
		this.m_guestID = m_guestID;
	}

	public String getFirstName() {
		return m_firstName;
	}

	public void setFirstName(String m_firstName) {
		this.m_firstName = m_firstName;
	}

	public String getLastName() {
		return m_lastName;
	}

	public void setLastName(String m_lastName) {
		this.m_lastName = m_lastName;
	}

	public String getAddress() {
		return m_address;
	}

	public void setAddress(String m_address) {
		this.m_address = m_address;
	}

	public String getPhone() {
		return m_phone;
	}

	public void setPhone(String m_phone) {
		this.m_phone = m_phone;
	}

	public String getEmail() {
		return m_email;
	}

	public void setEmail(String m_email) {
		this.m_email = m_email;
	}
    
    
}
