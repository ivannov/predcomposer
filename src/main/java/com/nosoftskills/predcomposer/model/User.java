package com.nosoftskills.predcomposer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@XmlRootElement
@Table(name = "Users")
@NamedQueries({
        @NamedQuery(name = "findUserByNameAndPassword", query = "SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password"),
        @NamedQuery(name = "getAllUsers", query = "SELECT u FROM User u")
})
public class User implements Serializable {

	private static final long serialVersionUID = -3718072367022295097L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private int version;

	@Column(nullable = false)
	private String userName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	private String firstName;

	private String lastName;

	@Column(nullable = false)
	private Boolean isAdmin = false;

	@Lob
	private byte[] avatar;

	@OneToMany(mappedBy = "byUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Prediction> predictions = new HashSet<>();

	public User() {
	}

	public User(String userName, String password, String email) {
		this(userName, password, email, null, null, false);
	}

	public User(String userName, String password, String email,
			String firstName, String lastName, boolean isAdmin) {
		this(userName, password, email, firstName, lastName, isAdmin, null);
	}

	public User(String userName, String password, String email,
			String firstName, String lastName, boolean admin, byte[] avatar) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isAdmin = admin;
		this.avatar = avatar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @XmlTransient
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    @XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean admin) {
		this.isAdmin = admin;
	}

    @XmlTransient
    public Set<Prediction> getPredictions() {
        return this.predictions;
    }

    public void setPredictions(final Set<Prediction> predictions) {
        this.predictions = predictions;
    }

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		User user = (User) o;
		return Objects.equals(userName, user.userName)
				&& Objects.equals(password, user.password)
				&& Objects.equals(email, user.email)
				&& Objects.equals(isAdmin, user.isAdmin);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName, password, email, isAdmin);
	}
}